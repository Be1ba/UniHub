package com.example.unihub.presentation.home.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.club.ClubsResponseItem

class ClubViewModel(): ViewModel() {
    private var _clubResponse: MutableLiveData<ClubsResponseItem> = MutableLiveData()
    val clubResponse: LiveData<ClubsResponseItem> = _clubResponse

    private var _clubsResponse: MutableLiveData<ClubsResponse> = MutableLiveData()
    val clubsResponse: LiveData<ClubsResponse> = _clubsResponse

    private var _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> = _errorMessage

}