package com.hire_wire_application.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hire_wire_application.models.db_models.User

@Dao
interface UserDao {
  @Query("SELECT * FROM User WHERE id = :userId") fun getUserById(userId: String): LiveData<User>

  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUser(user: User)
}
