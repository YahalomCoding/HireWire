package com.hire_wire_application.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.hire_wire_application.dao.AppLocalDB
import com.hire_wire_application.dao.AppLocalDbRepository
import java.util.concurrent.Executors

class Model private constructor() {
  private val executor = Executors.newSingleThreadExecutor()
  private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
  private val database: AppLocalDbRepository = AppLocalDB.db

  companion object {
    val shared = Model()
  }

  fun getAllServices(completion: (List<Service>) -> Unit) {
    executor.execute {
      val services = database.serviceDao.getAllServices()
      mainHandler.post { completion(services) }
    }
  }

  fun addService(service: Service, completion: () -> Unit) {
    executor.execute {
      database.serviceDao.insertService(service)
      mainHandler.post { completion() }
    }
  }
}
