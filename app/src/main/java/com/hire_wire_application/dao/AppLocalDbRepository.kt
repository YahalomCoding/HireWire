package com.hire_wire_application.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

@Database(entities = [Service::class, User::class], version = 6)
abstract class AppLocalDbRepository : RoomDatabase() {
  abstract val serviceDao: ServiceDao
  abstract val userDao: UserDao
}
