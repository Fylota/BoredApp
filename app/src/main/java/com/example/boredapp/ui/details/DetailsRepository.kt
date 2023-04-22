package com.example.boredapp.ui.details

import com.example.boredapp.persistence.BoredActivityDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val boredActivityDao: BoredActivityDao
) {
}