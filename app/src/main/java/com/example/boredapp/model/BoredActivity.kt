package com.example.boredapp.model

import androidx.room.Entity


@Entity
data class BoredActivity (
    val key: Long,
    val activity: String,
    val accessibility: Number,
    val type: String,
    val participants: Number,
    val price: Number
)
