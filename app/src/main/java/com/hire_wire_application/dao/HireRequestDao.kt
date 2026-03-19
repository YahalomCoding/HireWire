package com.hire_wire_application.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hire_wire_application.models.HireRequestWithDetails
import com.hire_wire_application.models.db_models.HireRequest

@Dao
interface HireRequestDao {
  @Query("SELECT * FROM HireRequest WHERE requesterId = :userId OR providerId = :userId")
  fun getRequestsByMe(userId: String): LiveData<List<HireRequest>>

  @Query(
      """
      SELECT HireRequest.*, Service.title as serviceName, User.name as requesterName 
      FROM HireRequest 
      JOIN Service ON HireRequest.serviceId = Service.id 
      JOIN User ON HireRequest.requesterId = User.id 
      WHERE HireRequest.providerId = :userId
  """
  )
  fun getRequestsToMeWithDetails(userId: String): LiveData<List<HireRequestWithDetails>>

  @Query(
      "SELECT * FROM HireRequest WHERE requesterId = :requesterId AND serviceId = :serviceId AND status = 'PENDING' LIMIT 1"
  )
  fun getPendingRequestByMe(requesterId: String, serviceId: String): LiveData<HireRequest?>

  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertRequest(vararg request: HireRequest)
}
