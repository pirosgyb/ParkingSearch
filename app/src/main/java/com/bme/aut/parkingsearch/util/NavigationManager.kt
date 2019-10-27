package com.bme.aut.parkingsearch.util

import androidx.navigation.NavController
import com.bme.aut.parkingsearch.R

object NavigationManager {

    fun navigateToAddParking(navController: NavController) {
        navController.navigate(R.id.addParkingFragment)
    }

    fun navigateToDetails(navController: NavController) {
        navController.navigate(R.id.parkingDetailsFragment)
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate(R.id.homeFragment)
    }

}