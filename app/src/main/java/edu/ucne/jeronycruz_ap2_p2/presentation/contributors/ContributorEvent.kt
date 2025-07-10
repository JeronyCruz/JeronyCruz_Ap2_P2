package edu.ucne.jeronycruz_ap2_p2.presentation.contributors


sealed interface ContributorEvent {
    data class GetContributors(val repoPath: String) : ContributorEvent
}