package com.hire_wire_application.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hire_wire_application.models.db_models.Service

@Dao
interface ServiceDao {
  @Query("SELECT * FROM Service WHERE providerId != :loggedInUserId AND isDeleted != 1")
  fun getHomeFeedServices(loggedInUserId: String): LiveData<List<Service>>

  @Query("SELECT * FROM Service WHERE providerId = :loggedInUserId AND isDeleted != 1")
  fun getMyServices(loggedInUserId: String): LiveData<List<Service>>

  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertService(vararg service: Service)
}
