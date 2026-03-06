package com.hire_wire_application.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hire_wire_application.models.Service

@Dao
interface ServiceDao {
  @Query("SELECT * FROM Service WHERE providerId != :loggedInUserId")
  fun getHomeFeedServices(loggedInUserId: String): List<Service>

  @Insert(onConflict = OnConflictStrategy.ABORT) fun insertService(vararg service: Service)
}
