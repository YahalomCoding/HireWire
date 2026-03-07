package com.hire_wire_application.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Service(
    @PrimaryKey val id: String,
    val providerId: String,
    val imageUrl: String = "Initial Value",
    val title: String,
    val description: String,
    val price: Long,
) {

  companion object {
    const val ID_KEY = "id"
    const val PROVIDER_ID_KEY = "providerId"
    const val IMAGE_URL_KEY = "imageUrl"
    const val TITLE_KEY = "title"
    const val DESCRIPTION_KEY = "description"
    const val PRICE_KEY = "price"

    fun fromJson(json: Map<String, Any?>): Service {
      val id = json[ID_KEY] as String
      val providerId = json[PROVIDER_ID_KEY] as String
      val imageUrl = json[IMAGE_URL_KEY] as String
      val title = json[TITLE_KEY] as String
      val description = json[DESCRIPTION_KEY] as String
      val price = json[PRICE_KEY] as Long

      return Service(
          id = id,
          providerId = providerId,
          imageUrl = imageUrl,
          title = title,
          description = description,
          price = price,
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
        )
}
