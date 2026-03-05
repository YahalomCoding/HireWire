package com.hire_wire_application.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hire_wire_application.models.Service

@Database(entities = [Service::class], version = 4)
abstract class AppLocalDbRepository : RoomDatabase() {
  abstract val serviceDao: ServiceDao
}
