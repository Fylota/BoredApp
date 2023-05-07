package com.example.boredapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "activities")
data class BoredActivity (
    @PrimaryKey(autoGenerate = true) val key: Long = 0,
    @ColumnInfo(name = "activity") val activity: String,
    val accessibility: Double,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String
) {
    companion object {

        fun mock() = BoredActivity(
                key = 3943506,
                activity = "Learn Express.js",
                accessibility = 0.25,
                type = "education",
                participants = 1,
                price = 0.1,
                link = "https://expressjs.com/"
        )
    }
}
