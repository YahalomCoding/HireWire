package com.hire_wire_application.models.db_models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.hire_wire_application.GLOBAL_REQUESTS_LAST_UPDATED_KEY
import com.hire_wire_application.MyApplication

enum class HireRequestStatus {
  PENDING,
  ACCEPTED,
  REJECTED,
}

@Entity
data class HireRequest(
    @PrimaryKey val id: String,
    val serviceId: String,
    val requesterId: String,
    val providerId: String,
    val status: HireRequestStatus = HireRequestStatus.PENDING,
    var lastUpdated: Long? = null,
) {
  companion object {
    var lastUpdated: Long
      get() {
        return MyApplication.Globals.appContext
            ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            ?.getLong(GLOBAL_REQUESTS_LAST_UPDATED_KEY, 0) ?: 0L
      }
      set(value) {
        MyApplication.Globals.appContext
            ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            ?.edit()
            ?.putLong(GLOBAL_REQUESTS_LAST_UPDATED_KEY, value)
            ?.apply()
      }

    const val ID_KEY = "id"
    const val SERVICE_ID_KEY = "serviceId"
    const val REQUESTER_ID_KEY = "requesterId"
    const val PROVIDER_ID_KEY = "providerId"
    const val STATUS_KEY = "status"
    const val LAST_UPDATED_KEY = "lastUpdated"

    fun fromJson(json: Map<String, Any?>): HireRequest {
      val id = json[ID_KEY] as String
      val serviceId = json[SERVICE_ID_KEY] as String
      val requesterId = json[REQUESTER_ID_KEY] as String
      val providerId = json[PROVIDER_ID_KEY] as String
      val statusString = json[STATUS_KEY] as? String ?: HireRequestStatus.PENDING.name
      val status =
          try {
            HireRequestStatus.valueOf(statusString)
          } catch (e: Exception) {
            HireRequestStatus.PENDING
          }
      val lastUpdated = json[LAST_UPDATED_KEY] as? Timestamp
      val lastUpdatedLong = lastUpdated?.toDate()?.time

      return HireRequest(
          id = id,
          serviceId = serviceId,
          requesterId = requesterId,
          providerId = providerId,
          status = status,
          lastUpdated = lastUpdatedLong,
      )
    }
  }

  val toJson: Map<String, Any?>
    get() =
        hashMapOf(
            ID_KEY to id,
            SERVICE_ID_KEY to serviceId,
            REQUESTER_ID_KEY to requesterId,
            PROVIDER_ID_KEY to providerId,
            STATUS_KEY to status.name,
            LAST_UPDATED_KEY to FieldValue.serverTimestamp(),
        )
}
