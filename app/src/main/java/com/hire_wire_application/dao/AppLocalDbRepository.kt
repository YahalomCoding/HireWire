package com.hire_wire_application.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hire_wire_application.models.db_models.HireRequest
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

@Database(entities = [Service::class, User::class, HireRequest::class], version = 8)
@TypeConverters(Converters::class)
abstract class AppLocalDbRepository : RoomDatabase() {
  abstract val serviceDao: ServiceDao
  abstract val userDao: UserDao
  abstract val hireRequestDao: HireRequestDao
}
