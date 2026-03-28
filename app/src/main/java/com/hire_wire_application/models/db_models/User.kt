package com.hire_wire_application.models.db_models

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.hire_wire_application.GLOBAL_USERS_LAST_UPDATED_KEY
import com.hire_wire_application.MyApplication

@Entity
data class User(
    @PrimaryKey val id: String,
    val imageUrl: String = "Initial Value",
    val name: String,
    val bio: String,
    var lastUpdated: Long? = null,
) {
  companion object {
    var lastUpdated: Long
      get() {
        return MyApplication.appContext
            ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            ?.getLong(GLOBAL_USERS_LAST_UPDATED_KEY, 0) ?: 0L
      }
      @SuppressLint("UseKtx")
      set(value) {
        MyApplication.appContext
            ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
            ?.edit()
            ?.putLong(GLOBAL_USERS_LAST_UPDATED_KEY, value)
            ?.apply()
      }

    const val ID_KEY = "id"
    const val IMAGE_URL_KEY = "imageUrl"
    const val NAME_KEY = "name"
    const val BIO_KEY = "bio"
    const val LAST_UPDATED_KEY = "lastUpdated"

    fun fromJson(json: Map<String, Any?>): User {
      val id = json[ID_KEY] as String
      val imageUrl = json[IMAGE_URL_KEY] as String
      val name = json[NAME_KEY] as String
      val bio = json[BIO_KEY] as String
      val lastUpdated = json[LAST_UPDATED_KEY] as? Timestamp
      val lastUpdatedLong = lastUpdated?.toDate()?.time

      return User(
          id = id,
          imageUrl = imageUrl,
          name = name,
          bio = bio,
          lastUpdated = lastUpdatedLong,
      )
    }
  }

  val toJson: Map<String, Any?>
    get() =
        hashMapOf(
            ID_KEY to id,
            IMAGE_URL_KEY to imageUrl,
            NAME_KEY to name,
            BIO_KEY to bio,
            LAST_UPDATED_KEY to FieldValue.serverTimestamp(),
        )
}
