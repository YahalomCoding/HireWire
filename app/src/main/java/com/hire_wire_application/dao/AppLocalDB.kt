package com.hire_wire_application.dao

import androidx.room.Room
import com.hire_wire_application.MyApplication

object AppLocalDB {

  val db: AppLocalDbRepository by lazy {
    val context = MyApplication.appContext ?: throw IllegalStateException("Context Is Null.")

    Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "hire-wire.db",
        )
        .fallbackToDestructiveMigration(true)
        .build()
  }
}
