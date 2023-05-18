package com.example.boredapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boredapp.model.BoredActivity
import com.example.boredapp.ui.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    fun getActivityDetails(key: Long): Flow<BoredActivity> = repository.getActivityFromDB(key)

    fun removeActivity(activity: BoredActivity) = viewModelScope.launch {
        repository.removeActivity(activity)
    }

    fun updateBoredActivity(activity: BoredActivity) = viewModelScope.launch {
        repository.updateActivity(activity)
    }
}
