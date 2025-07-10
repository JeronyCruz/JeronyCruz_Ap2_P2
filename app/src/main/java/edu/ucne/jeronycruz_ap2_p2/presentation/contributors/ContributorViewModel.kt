package edu.ucne.jeronycruz_ap2_p2.presentation.contributors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jeronycruz_ap2_p2.data.remote.Resource
import edu.ucne.jeronycruz_ap2_p2.data.remote.dto.ContributorDto
import edu.ucne.jeronycruz_ap2_p2.data.repository.RepositoryRepository
import edu.ucne.jeronycruz_ap2_p2.presentation.repository.RepositoryEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributorViewModel @Inject constructor(
    private val repositoryRepository: RepositoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContributorUiState())
    val uiState = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<ContributorDto>>(emptyList())
    val searchResults: StateFlow<List<ContributorDto>> = _searchResults.asStateFlow()

//    init {
//        getContributor("enelramon")
//    }

    fun OnEvent(event: ContributorEvent){
        when(event){
            is ContributorEvent.GetContributors -> getContributor(event.repoPath)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }


    fun getContributor(repoPath: String) {
        val (owner, repo) = repoPath.split("/")
        viewModelScope.launch {
            repositoryRepository.getContributors(owner, repo).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                contributor = resource.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.message
                            )
                        }
                    }
                }
            }
        }
    }
}