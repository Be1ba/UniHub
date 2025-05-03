package com.example.unihub.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

class SharedProvider(private val context: Context) {
    private val sharedToken = "AccessToken"
    private val email = "Email"
    private val isAuthorized = "isAuthorized"

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    private val systemPreferences: SharedPreferences
        get() = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    fun isAuthorized(): Boolean {
        return preferences.getBoolean(isAuthorized, false)
    }

    fun saveEmail(_email: String) {
        preferences.edit() { putString(email, _email) }
    }

    fun saveToken(token: String) {
        preferences.edit() { putString(sharedToken, token) }
    }

    fun getEmail(): String {
        return preferences.getString(email, "without_email").toString()
    }

    fun getToken():String {
        return preferences.getString(sharedToken, "without_token").toString()
    }

    fun saveName(name: String) {
        preferences.edit() { putString("name", name) }
    }

    fun getName(): String {
        return preferences.getString("name", "without_name").toString()
    }

    fun saveSurname(surname: String) {
        preferences.edit() { putString("surname", surname) }
    }

    fun getSurname(): String {
        return preferences.getString("surname", "without_surname").toString()
    }

    fun savePhone(phone: String) {
        preferences.edit() { putString("phone", phone) }
    }

    fun getPhone(): String {
        return preferences.getString("phone", "without_phone").toString()
    }

    fun saveGender(gender: String) {
        preferences.edit() { putString("gender", gender) }
    }

    fun getGender(): String {
        return preferences.getString("gender", "without_gender").toString()
    }

    fun saveBirthDate(birthDate: String) {
        preferences.edit() { putString("birth_date", birthDate) }
    }

    fun getBirthDate(): String {
        return preferences.getString("birth_date", "without_birth_date").toString()
    }

    fun saveRole(role: String) {
        preferences.edit() { putString("role", role) }
    }

    fun getRole(): String {
        return preferences.getString("role", "without_role").toString()
    }

    fun saveID(id: Int) {
        preferences.edit() { putInt("id", id) }
    }

    fun getID(): Int {
        return preferences.getInt("id", 0)
    }

    fun saveIsAuthorized(_isAuthorized: Boolean) {
        preferences.edit() { putBoolean(isAuthorized, _isAuthorized) }
    }

    fun getIsAuthorized(): Boolean {
        return preferences.getBoolean(isAuthorized, false)
    }

    fun setToken(token: String) {
        preferences.edit() {
            putString(sharedToken, token)
            Log.d("AAA", "setToken: $token")
        }
    }

    fun clearShared() {
        preferences.edit() { clear() }
    }

}