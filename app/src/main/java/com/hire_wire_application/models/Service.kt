package com.hire_wire_application.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Service(
    @PrimaryKey val id: String,
    val imageUrl: String?,
    val title: String,
    val description: String,
    val price: Int,
)
