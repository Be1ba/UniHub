package com.example.unihub.utils

interface NavigationHostProvider {
    fun hideBottomNavigationBar(visible: Boolean)

    fun setupBottomNavForRole(isHead: Boolean)
//    fun fullScreenMode(visible: Boolean)
}