package com.hire_wire_application.models.db_models

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.hire_wire_application.GLOBAL_SERVICES_LAST_UPDATED_KEY
import com.hire_wire_application.MyApplication

@Entity
data class Service(
    @PrimaryKey val id: String,
    val providerId: String,
    val imageUrl: String = "Initial Value",
    val title: String,
    val description: String,
    val price: Long,
    var lastUpdated: Long? = null,
    val isDeleted: Boolean? = false,
) {

  companion object {
    var lastUpdated: Long
      get() {
        return MyApplication.appContext
            ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            ?.getLong(GLOBAL_SERVICES_LAST_UPDATED_KEY, 0) ?: 0L
      }
      @SuppressLint("UseKtx")
      set(value) {
        MyApplication.appContext
            ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            ?.edit()
            ?.putLong(GLOBAL_SERVICES_LAST_UPDATED_KEY, value)
            ?.apply()
      }

    const val ID_KEY = "id"
    const val PROVIDER_ID_KEY = "providerId"
    const val IMAGE_URL_KEY = "imageUrl"
    const val TITLE_KEY = "title"
    const val DESCRIPTION_KEY = "description"
    const val PRICE_KEY = "price"
    const val LAST_UPDATED_KEY = "lastUpdated"
    const val IS_DELETED_KEY = "isDeleted"

    fun fromJson(json: Map<String, Any?>): Service {
      val id = json[ID_KEY] as String
      val providerId = json[PROVIDER_ID_KEY] as String
      val imageUrl = json[IMAGE_URL_KEY] as String
      val title = json[TITLE_KEY] as String
      val description = json[DESCRIPTION_KEY] as String
      val price = json[PRICE_KEY] as Long
      val lastUpdated = json[LAST_UPDATED_KEY] as? Timestamp
      val lastUpdatedLong = lastUpdated?.toDate()?.time
      val isDeleted = json[IS_DELETED_KEY] as? Boolean ?: false

      return Service(
          id = id,
          providerId = providerId,
          imageUrl = imageUrl,
          title = title,
          description = description,
          price = price,
          lastUpdated = lastUpdatedLong,
          isDeleted = isDeleted,
      )
    }
  }

  val toJson: Map<String, Any?>
    get() =
        hashMapOf(
            ID_KEY to id,
            PROVIDER_ID_KEY to providerId,
            IMAGE_URL_KEY to imageUrl,
            TITLE_KEY to title,
            DESCRIPTION_KEY to description,
            PRICE_KEY to price,
            LAST_UPDATED_KEY to FieldValue.serverTimestamp(),
            IS_DELETED_KEY to isDeleted,
        )
}
