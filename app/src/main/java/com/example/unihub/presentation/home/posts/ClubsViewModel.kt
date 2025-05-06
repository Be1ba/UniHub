package com.example.unihub.presentation.home.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.post.PostsResponse
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.users.UpdateUserProfileRequest
import com.example.unihub.data.model.users.UserProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class ClubsViewModel(): ViewModel() {

    private var _getClubListResponse: MutableLiveData<ClubsResponse> = MutableLiveData()
    val getClubListResponse: LiveData<ClubsResponse> = _getClubListResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun getClubList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getClubs(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _getClubListResponse.postValue(it)
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