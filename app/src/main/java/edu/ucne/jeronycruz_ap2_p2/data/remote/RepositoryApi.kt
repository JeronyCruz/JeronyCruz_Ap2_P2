package edu.ucne.jeronycruz_ap2_p2.data.remote

import edu.ucne.jeronycruz_ap2_p2.data.remote.dto.RepositoryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): List<RepositoryDto>
}