package com.example.boredapp.ui.main

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.example.boredapp.model.BoredActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val dbActivityList: Flow<List<BoredActivity>> =
        mainRepository.getAllActivitiesFromDB()

    var activityUiState: ActivityUiState by mutableStateOf(ActivityUiState.Loading)
        private set

    init {
        getRandomActivity()
    }

    fun getRandomActivity() {
        viewModelScope.launch {
            activityUiState = try {
                val res: BoredActivity = mainRepository.getRandomActivity()!!
                ActivityUiState.Success(res)
            } catch (e: IOException) {
                ActivityUiState.Error
            } catch (e: HttpException) {
                ActivityUiState.Error
            }
        }
    }

    fun addToDB(activity: BoredActivity) {
        viewModelScope.launch {
            mainRepository.addActivity(activity)
        }
    }
}

sealed interface ActivityUiState {
    data class Success(val result: BoredActivity): ActivityUiState
    object Error: ActivityUiState
    object Loading: ActivityUiState
}
