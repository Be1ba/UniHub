package com.example.unihub.presentation.home.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.post.PostsResponse
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.users.UpdateUserProfileRequest
import com.example.unihub.data.model.users.UserProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class PostsViewModel(): ViewModel() {

    private var _getPostListResponse: MutableLiveData<PostsResponse> = MutableLiveData()
    val getPostListResponse: LiveData<PostsResponse> = _getPostListResponse

    private var _getMyPostsListResponse: MutableLiveData<PostsResponse> = MutableLiveData()
    val getMyPostsListResponse: LiveData<PostsResponse> = _getMyPostsListResponse

    private val _getPostDetailsResponse: MutableLiveData<PostsResponseItem> = MutableLiveData()
    val getPostDetailsResponse: LiveData<PostsResponseItem> = _getPostDetailsResponse

    private val _likePostResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val likePostResponse: LiveData<MessageResponse> = _likePostResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun getPostsList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getPosts(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _getPostListResponse.postValue(it)
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

    fun getMyPostsList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getMyPosts(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _getMyPostsListResponse.postValue(it)
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

    fun getPostDetails(token: String, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getPostDetails(
                    token = token,
                    postId = postId
                )
            }.fold(
                onSuccess = {
                    _getPostDetailsResponse.postValue(it)
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

    fun likePost(token: String, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.likePost(
                    token = token,
                    postId = postId
                )
            }.fold(
                onSuccess = {
                    _likePostResponse.postValue(it)
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