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

class ClubViewModel(): ViewModel() {
    private var _profileResponse: MutableLiveData<HeadProfileResponse> = MutableLiveData()
    val profileResponse: LiveData<HeadProfileResponse> = _profileResponse

    private var _clubResponse: MutableLiveData<ClubDetailsResponse> = MutableLiveData()
    val clubResponse: LiveData<ClubDetailsResponse> = _clubResponse

    private var _createPostResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val createPostResponse: LiveData<MessageResponse> = _createPostResponse

    private var _clubsResponse: MutableLiveData<ClubsResponse> = MutableLiveData()
    val clubsResponse: LiveData<ClubsResponse> = _clubsResponse

    private var _myPostsResponse: MutableLiveData<PostsResponse> = MutableLiveData()
    val myPostsResponse: LiveData<PostsResponse> = _myPostsResponse

    private var _postersByClubResponse: MutableLiveData<PostersByClubResponse> = MutableLiveData()
    val postersByClubResponse: LiveData<PostersByClubResponse> = _postersByClubResponse

    private var _posterResponse: MutableLiveData<PosterDetailsResponse> = MutableLiveData()
    val posterResponse: LiveData<PosterDetailsResponse> = _posterResponse

    private var _buyTicketResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val buyTicketResponse: LiveData<MessageResponse> = _buyTicketResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun getClubDetails(token: String, clubId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getClubDetails(
                    token = token,
                    clubId = clubId
                )
            }.fold(
                onSuccess = {
                    _clubResponse.postValue(it)
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

    fun createPost(token: String, content: String, image: String, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.createPost(
                    token = token,
                    post = CreatePostRequest(
                        content = content,
                        image = image,
                        title = title
                    )
                )
            }.fold(
                onSuccess = {
                    _createPostResponse.postValue(it)
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

    fun getPosterByClub(token: String, clubId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getPostersByClub(
                    token = token,
                    clubId = clubId
                )
            }.fold(
                onSuccess = {
                    _postersByClubResponse.postValue(it)
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

    fun getPosterDetails(token: String, posterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getPosterDetails(
                    token = token,
                    posterId = posterId
                )
            }.fold(
                onSuccess = {
                    _posterResponse.postValue(it)
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

    fun buyTicket(token: String, posterId: Int, numberOfPersons: Int, paymentProof: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.createTicket(
                    token = token,
                    ticket = BookTicketRequest(
                        numberOfPersons = numberOfPersons,
                        paymentProof = paymentProof,
                        posterId = posterId
                    )
                )
            }.fold(
                onSuccess = {
                    _buyTicketResponse.postValue(it)
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

    fun getMyPosts(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getMyPostsList(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _myPostsResponse.postValue(it)
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

    fun getHeadProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getHeadProfile(token = token)
            }.fold(
                onSuccess = {
                    _profileResponse.postValue(it)
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