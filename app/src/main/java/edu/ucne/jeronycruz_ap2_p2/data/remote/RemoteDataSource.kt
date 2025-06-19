package edu.ucne.jeronycruz_ap2_p2.data.remote


import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubApi: GitHubApi
) {
    suspend fun getRepository(username: String) = gitHubApi.listRepos(username)
}