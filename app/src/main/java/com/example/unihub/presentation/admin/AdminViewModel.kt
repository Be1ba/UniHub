package com.example.unihub.presentation.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.auth.LoginRequest
import com.example.unihub.data.model.auth.LoginResponse
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.admin.CreateClubAdminResponse
import com.example.unihub.data.model.admin.CreateClubAdminResponseItem
import com.example.unihub.data.model.admin.CreateEventAdminDetailsResponse
import com.example.unihub.data.model.admin.CreateEventAdminResponse
import com.example.unihub.data.model.admin.UsersListResponse
import com.example.unihub.data.model.auth.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class AdminViewModel(): ViewModel() {
    private var _getRequestsListResponse: MutableLiveData<CreateClubAdminResponse> = MutableLiveData()
    val getRequestsListResponse: LiveData<CreateClubAdminResponse> = _getRequestsListResponse

    private var _getClubRequestDetailsResponse: MutableLiveData<CreateClubAdminResponseItem> = MutableLiveData()
    val getClubRequestDetailsResponse: LiveData<CreateClubAdminResponseItem> = _getClubRequestDetailsResponse

    private var _approveClubRequestResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val approveClubRequestResponse: LiveData<MessageResponse> = _approveClubRequestResponse

    private var _rejectClubRequestResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val rejectClubRequestResponse: LiveData<MessageResponse> = _rejectClubRequestResponse

    private var _getUsersListResponse: MutableLiveData<UsersListResponse> = MutableLiveData()
    val getUsersListResponse: LiveData<UsersListResponse> = _getUsersListResponse

    private var _getEventsListResponse: MutableLiveData<CreateEventAdminResponse> = MutableLiveData()
    val getEventsListResponse: LiveData<CreateEventAdminResponse> = _getEventsListResponse

    private var _getEventsDetailResponse: MutableLiveData<CreateEventAdminDetailsResponse> = MutableLiveData()
    val getEventsDetailResponse: LiveData<CreateEventAdminDetailsResponse> = _getEventsDetailResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun getRequestDetails(token: String, requestId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getClubRequestDetails(token = token, requestId = requestId)
            }.fold(
                onSuccess = {
                    _getClubRequestDetailsResponse.postValue(it)
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

    fun getRequestsList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getCreateClubRequests(token = token)
            }.fold(
                onSuccess = {
                    _getRequestsListResponse.postValue(it)
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

    fun approveClubRequest(token: String, requestId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.approveClubRequest(token = token, requestId = requestId)
            }.fold(
                onSuccess = {
                    _approveClubRequestResponse.postValue(it)
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

    fun rejectClubRequest(token: String, requestId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.rejectClubRequest(token = token, requestId = requestId)
            }.fold(
                onSuccess = {
                    _rejectClubRequestResponse.postValue(it)
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

    fun getUsersList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getUsers(token = token)
            }.fold(
                onSuccess = {
                    _getUsersListResponse.postValue(it)
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

    fun getEventsList(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getEventRequests(token = token)
            }.fold(
                onSuccess = {
                    _getEventsListResponse.postValue(it)
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

    fun getEventByID(token: String, eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getEventRequestDetails(token = token, requestId = eventId)
            }.fold(
                onSuccess = {
                    _getEventsDetailResponse.postValue(it)
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