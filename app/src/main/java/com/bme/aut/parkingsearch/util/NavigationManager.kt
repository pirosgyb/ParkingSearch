package com.bme.aut.parkingsearch.util

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bme.aut.parkingsearch.R

object NavigationManager {

    fun navigateToAddParking(navController: NavController) {
        val transitions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.exit_to_left)
            .setPopEnterAnim(R.anim.enter_from_left)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()

        navController.navigate(R.id.addParkingFragment, null, transitions)
    }

    fun navigateToDetails(navController: NavController) {
        val transitions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.exit_to_left)
            .setPopEnterAnim(R.anim.enter_from_left)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()

        navController.navigate(R.id.parkingDetailsFragment, null, transitions)
    }

    fun navigateToHome(navController: NavController) {
        val transitions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_left)
            .setExitAnim(R.anim.exit_to_right)
            .setPopEnterAnim(R.anim.enter_from_right)
            .setPopExitAnim(R.anim.exit_to_left)
            .build()

        navController.navigate(R.id.homeFragment, null, transitions)
    }

}