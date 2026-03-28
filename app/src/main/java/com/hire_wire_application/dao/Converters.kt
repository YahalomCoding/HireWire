package com.hire_wire_application.dao

import androidx.room.TypeConverter
import com.hire_wire_application.models.db_models.HireRequestStatus

class Converters {
  @TypeConverter
  fun fromStatus(status: HireRequestStatus): String {
    return status.name
  }

  @TypeConverter
  fun toStatus(status: String): HireRequestStatus {
    return try {
      HireRequestStatus.valueOf(status)
    } catch (_: Exception) {
      HireRequestStatus.PENDING
    }
  }
}
