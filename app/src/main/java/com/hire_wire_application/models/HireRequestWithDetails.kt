package com.hire_wire_application.models

import androidx.room.Embedded
import com.hire_wire_application.models.db_models.HireRequest

data class HireRequestWithDetails(
    @Embedded val request: HireRequest,
    val serviceName: String,
    val requesterName: String?,
)
