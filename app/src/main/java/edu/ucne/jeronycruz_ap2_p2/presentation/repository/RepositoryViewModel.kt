package edu.ucne.jeronycruz_ap2_p2.presentation.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jeronycruz_ap2_p2.data.remote.Resource
import edu.ucne.jeronycruz_ap2_p2.data.repository.RepositoryRepository
import edu.ucne.jeronycruz_ap2_p2.presentation.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repositoryRepository: RepositoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoryUiState())
    val uiState = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        getRepository("enelramon")
    }

    fun getRepository(username: String) {
        viewModelScope.launch {
            repositoryRepository.getRepositories(username).collectLatest { getting ->
                when (getting) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true)}
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                repository = getting.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}