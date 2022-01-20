package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WordInfoViewModel @Inject constructor(private val getWordInfo: GetWordInfo) : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            //simulation for fetching data from network
            delay(4000)
            _isLoading.value = false
        }
    }
    private val _searchQuery = mutableStateOf("")
    val searchQuery : State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state : State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob : Job? = null

    fun onSearch(query:String){
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfo(query).onEach {result->
                when(result){
                    is Resource.Success ->{
                      _state.value = state.value.copy(
                          wordInfoItem = result.data?: emptyList(),
                          isLoading = false
                      )
                    }
                    is Resource.Error ->{
                        _state.value = state.value.copy(
                            wordInfoItem = result.data?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UiEvent.showSnackBar(
                            result.message ?: "Unknown Error"
                        ))
                    }
                    is Resource.Loading ->{
                        _state.value = state.value.copy(
                            wordInfoItem = result.data?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UiEvent{
        data class showSnackBar(val message:String):UiEvent()
    }
}