package com.hire_wire_application.models

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import com.hire_wire_application.Completion
import com.hire_wire_application.dao.AppLocalDB
import com.hire_wire_application.dao.AppLocalDbRepository
import com.hire_wire_application.models.db_models.HireRequest
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User
import java.util.concurrent.Executors

class LocalStorageModel {
  private val executor = Executors.newSingleThreadExecutor()
  private val mainHandler = Handler.createAsync(Looper.getMainLooper())
  private val database: AppLocalDbRepository = AppLocalDB.db

  fun getHomeFeedServices(
      loggedInUserId: String,
  ): LiveData<List<Service>> {
    return database.serviceDao.getHomeFeedServices(loggedInUserId)
  }

  fun getMyServices(
      loggedInUserId: String,
  ): LiveData<List<Service>> {
    return database.serviceDao.getMyServices(loggedInUserId)
  }

  fun insertService(service: Service, completion: Completion? = null) {
    executor.execute {
      database.serviceDao.insertService(service)
      completion?.let { mainHandler.post { it() } }
    }
  }

  fun getUserById(userId: String): LiveData<User> {
    return database.userDao.getUserById(userId)
  }

  fun insertUser(user: User, completion: Completion? = null) {
    executor.execute {
      database.userDao.insertUser(user)
      completion?.let { mainHandler.post { it() } }
    }
  }

  fun getRequestsByMe(userId: String): LiveData<List<HireRequest>> {
    return database.hireRequestDao.getRequestsByMe(userId)
  }

  fun getRequestsToMe(userId: String): LiveData<List<HireRequestWithDetails>> {
    return database.hireRequestDao.getRequestsToMeWithDetails(userId)
  }

  fun getPendingRequestByMe(requesterId: String, serviceId: String): LiveData<HireRequest?> {
    return database.hireRequestDao.getPendingRequestByMe(requesterId, serviceId)
  }

  fun insertRequest(request: HireRequest, completion: Completion? = null) {
    executor.execute {
      database.hireRequestDao.insertRequest(request)
      completion?.let { mainHandler.post { it() } }
    }
  }
}
