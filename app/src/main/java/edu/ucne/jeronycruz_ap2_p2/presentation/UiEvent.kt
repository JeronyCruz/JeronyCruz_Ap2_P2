package edu.ucne.jeronycruz_ap2_p2.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateUp : UiEvent()
}