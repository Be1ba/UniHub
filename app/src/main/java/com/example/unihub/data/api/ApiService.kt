package com.example.unihub.data.api

import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.auth.LoginRequest
import com.example.unihub.data.model.auth.LoginResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.auth.SignUpRequest
import com.example.unihub.data.model.club.ClubDetailsResponse
import com.example.unihub.data.model.club.ClubEventsResponse
import com.example.unihub.data.model.club_request.CreateClubRequest
import com.example.unihub.data.model.club_request.MyCreateClubResponse
import com.example.unihub.data.model.event.EventDetailsResponse
import com.example.unihub.data.model.event.EventsResponse
import com.example.unihub.data.model.post.CreatePostRequest
import com.example.unihub.data.model.post.PostsResponse
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.poster.PosterDetailsResponse
import com.example.unihub.data.model.poster.PostersByClubResponse
import com.example.unihub.data.model.poster.PostersResponse
import com.example.unihub.data.model.ticket.BookTicketRequest
import com.example.unihub.data.model.ticket.MyTicketsResponse
import com.example.unihub.data.model.users.UpdateUserProfileRequest
import com.example.unihub.data.model.users.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: SignUpRequest
    ): LoginResponse

    @PUT("auth/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordBody: PasswordChangeRequest
    ): MessageResponse




    @GET("clubs")
    suspend fun getClubs(
        @Header("Authorization") token: String
    ): ClubsResponse

    @GET("clubs/{clubId}")
    suspend fun getClubDetails(
        @Header("Authorization") token: String,
        @Path("clubId") clubId: Int
    ): ClubDetailsResponse




    @GET("events")
    suspend fun getEvents(
        @Header("Authorization") token: String
    ): EventsResponse

    @GET("events/{eventId}")
    suspend fun getEventDetails(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Int
    ): EventDetailsResponse

    @GET("events/club/{clubId}")
    suspend fun getEventsByClub(
        @Header("Authorization") token: String,
        @Path("clubId") clubId: Int
    ): ClubEventsResponse




    @GET("posts")
    suspend fun getPosts(
        @Header("Authorization") token: String,
        @Query("clubId") clubId: Int? = null,
    ): PostsResponse

    @GET("posts/{postId}")
    suspend fun getPostDetails(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): PostsResponseItem

    @POST("posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body post: CreatePostRequest
    ): MessageResponse

    @PUT("posts/{postId}")
    suspend fun updatePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int,
        @Body post: CreatePostRequest
    ): MessageResponse

    @DELETE("posts/{postId}")
    suspend fun deletePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): MessageResponse

    @GET("posts/my-posts")
    suspend fun getMyPosts(
        @Header("Authorization") token: String,
    ): PostsResponse

    @POST("posts/{postId}/like")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): MessageResponse




    @GET("posters")
    suspend fun getPosters(
        @Header("Authorization") token: String
    ): PostersResponse

    @GET("posters/{posterId}")
    suspend fun getPosterDetails(
        @Header("Authorization") token: String,
        @Path("posterId") posterId: Int
    ): PosterDetailsResponse

    @GET("posters/club/{clubId}")
    suspend fun getPostersByClub(
        @Header("Authorization") token: String,
        @Path("clubId") clubId: Int
    ): PostersByClubResponse




    @POST("tickets")
    suspend fun createTicket(
        @Header("Authorization") token: String,
        @Body ticket: BookTicketRequest
    ): MessageResponse

    @GET("tickets")
    suspend fun getTickets(
        @Header("Authorization") token: String
    ): MyTicketsResponse




    @GET("users/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse

    @PUT("users/profile")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userProfile: UpdateUserProfileRequest
    ): MessageResponse




    @POST("club-requests")
    suspend fun createClubRequest(
        @Header("Authorization") token: String,
        @Body newClub: CreateClubRequest
    ): MessageResponse

    @GET("club-requests/my-requests")
    suspend fun getMyClubRequests(
        @Header("Authorization") token: String
    ): MyCreateClubResponse
}