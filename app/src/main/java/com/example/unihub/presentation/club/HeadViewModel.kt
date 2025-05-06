package com.example.unihub.presentation.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.club.ClubDetailsResponse
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.data.model.head.CreateEventRequest
import com.example.unihub.data.model.head.CreateEventsListReponse
import com.example.unihub.data.model.head.CreatePosterRequest
import com.example.unihub.data.model.head.HeadProfileResponse
import com.example.unihub.data.model.post.CreatePostRequest
import com.example.unihub.data.model.post.PostsResponse
import com.example.unihub.data.model.poster.PosterDetailsResponse
import com.example.unihub.data.model.poster.PostersByClubResponse
import com.example.unihub.data.model.poster.PostersResponse
import com.example.unihub.data.model.ticket.BookTicketRequest
import com.example.unihub.data.model.users.UserProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class HeadViewModel(): ViewModel() {
    private var _createPosterResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val createPosterResponse: LiveData<MessageResponse> = _createPosterResponse

    private var _createEventResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val createEventResponse: LiveData<MessageResponse> = _createEventResponse

    private var _createEventsListResponse: MutableLiveData<CreateEventsListReponse> = MutableLiveData()
    val createEventsListResponse: LiveData<CreateEventsListReponse> = _createEventsListResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun createPoster(
        token: String,
        description: String,
        eventDate: String,
        eventId: Int,
        eventTitle: String,
        image: String,
        location: String,
        price: Int,
        seats: Int,
        time: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.createPoster(
                    token = token,
                    poster = CreatePosterRequest(
                        description = description,
                        eventDate = eventDate,
                        eventId = eventId,
                        eventTitle = eventTitle,
                        image = image,
                        location = location,
                        price = price,
                        seats = seats,
                        time = time
                    )
                )
            }.fold(
                onSuccess = {
                    _createPosterResponse.postValue(it)
                },
                onFailure = { throwable ->
                    val errorMessage = if (throwable is HttpException) {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        try {
                            val json = JSONObject(errorBody ?: "")
                            json.getString("message")
                        } catch (e: Exception) {
                            "Something went wrong."
                        }
                    } else {
                        throwable.message ?: "An unknown error occurred."
                    }
                    _errorMessage.postValue(MessageResponse(errorMessage))
                }
            )
        }
    }

    fun createEvent(
        token: String,
        clubHead: String,
        comment: String,
        eventDate: String,
        eventName: String,
        goal: String,
        location: String,
        organizers: String,
        phone: String,
        schedule: String,
        shortDescription: String,
        sponsorship: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.createEventRequest(
                    token = token,
                    eventRequest = CreateEventRequest(
                        clubHead = clubHead,
                        comment = comment,
                        eventDate = eventDate,
                        eventName = eventName,
                        goal = goal,
                        location = location,
                        organizers = organizers,
                        phone = phone,
                        schedule = schedule,
                        shortDescription = shortDescription,
                        sponsorship = sponsorship
                    )
                )
            }.fold(
                onSuccess = {
                    _createEventResponse.postValue(it)
                },
                onFailure = { throwable ->
                    val errorMessage = if (throwable is HttpException) {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        try {
                            val json = JSONObject(errorBody ?: "")
                            json.getString("message")
                        } catch (e: Exception) {
                            "Something went wrong."
                        }
                    } else {
                        throwable.message ?: "An unknown error occurred."
                    }
                    _errorMessage.postValue(MessageResponse(errorMessage))
                }
            )
        }
    }

    fun getCreateEventsList(
        token: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getMyEventRequests(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _createEventsListResponse.postValue(it)
                },
                onFailure = { throwable ->
                    val errorMessage = if (throwable is HttpException) {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        try {
                            val json = JSONObject(errorBody ?: "")
                            json.getString("message")
                        } catch (e: Exception) {
                            "Something went wrong."
                        }
                    } else {
                        throwable.message ?: "An unknown error occurred."
                    }
                    _errorMessage.postValue(MessageResponse(errorMessage))
                }
            )
        }
    }
}