package edu.ucne.jeronycruz_ap2_p2.presentation.contributors

import edu.ucne.jeronycruz_ap2_p2.data.remote.dto.ContributorDto
import edu.ucne.jeronycruz_ap2_p2.data.remote.dto.RepositoryDto

data class ContributorUiState (
    val login: String = "",
    val id: Int = 0,
    val contribution: String = "",
    val isLoading: Boolean = false,
    val contributor: List<ContributorDto> = emptyList(),
    val errorMessage: String? = null,
)