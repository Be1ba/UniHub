package com.example.unihub.data.api

import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.admin.CreateClubAdminResponse
import com.example.unihub.data.model.admin.CreateClubAdminResponseItem
import com.example.unihub.data.model.admin.CreateEventAdminDetailsResponse
import com.example.unihub.data.model.admin.CreateEventAdminResponse
import com.example.unihub.data.model.admin.UsersListResponse
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.auth.LoginRequest
import com.example.unihub.data.model.auth.LoginResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.auth.SignUpRequest
import com.example.unihub.data.model.club.ClubDetailsResponse
import com.example.unihub.data.model.club.ClubEventsResponse
import com.example.unihub.data.model.club_request.CreateClubRequest
import com.example.unihub.data.model.club_request.MyCreateClubResponse
import com.example.unihub.data.model.club_request.MyCreateClubResponseItem
import com.example.unihub.data.model.event.EventDetailsResponse
import com.example.unihub.data.model.event.EventsResponse
import com.example.unihub.data.model.head.CreateEventsListReponse
import com.example.unihub.data.model.head.CreateEventRequest
import com.example.unihub.data.model.head.CreatePosterRequest
import com.example.unihub.data.model.head.HeadProfileResponse
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

    @POST("clubs/{id}/subscribe")
    suspend fun subscribeToClub(
        @Header("Authorization") token: String,
        @Path("id") clubId: Int
    ): MessageResponse

    @POST("clubs/{id}/unsubscribe")
    suspend fun unsubscribeFromClub(
        @Header("Authorization") token: String,
        @Path("id") clubId: Int
    ): MessageResponse

//    @GET("clubs/my-subscriptions")
//    suspend fun getMyClubs(
//        @Header("Authorization") token: String
//    ): ClubsResponse




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

    @GET("club-requests/{requestId}")
    suspend fun getRequestDetails(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): MyCreateClubResponseItem











    @GET("club-requests")
    suspend fun getCreateClubRequests(
        @Header("Authorization") token: String
    ): CreateClubAdminResponse

    @GET("club-requests/{requestId}")
    suspend fun getClubRequestDetails(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): CreateClubAdminResponseItem

    @PUT("club-requests/{requestId}/approve")
    suspend fun approveClubRequest(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): MessageResponse

    @PUT("club-requests/{requestId}/reject")
    suspend fun rejectClubRequest(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): MessageResponse

    @PUT("event-requests/{requestId}/approve")
    suspend fun approveEventRequest(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): MessageResponse

    @PUT("event-requests/{requestId}/reject")
    suspend fun rejectEventRequest(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): MessageResponse

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String
    ): UsersListResponse

    @GET("event-requests")
    suspend fun getEventRequests(
        @Header("Authorization") token: String
    ): CreateEventAdminResponse















    @GET("users/profile")
    suspend fun getHeadProfile(
        @Header("Authorization") token: String
    ): HeadProfileResponse

    @GET("posts/my-posts")
    suspend fun getMyPostsList(
        @Header("Authorization") token: String
    ): PostsResponse

    @POST("event-requests")
    suspend fun createEventRequest(
        @Header("Authorization") token: String,
        @Body eventRequest: CreateEventRequest
    ): MessageResponse

    @POST("posters")
    suspend fun createPoster(
        @Header("Authorization") token: String,
        @Body poster: CreatePosterRequest
    ): MessageResponse

    @GET("event-requests/my-requests")
    suspend fun getMyEventRequests(
        @Header("Authorization") token: String
    ): CreateEventsListReponse

    @GET("event-requests/{requestId}")
    suspend fun getEventRequestDetails(
        @Header("Authorization") token: String,
        @Path("requestId") requestId: Int
    ): CreateEventAdminDetailsResponse
}