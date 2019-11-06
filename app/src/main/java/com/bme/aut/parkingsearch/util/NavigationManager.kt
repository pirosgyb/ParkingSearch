package com.bme.aut.parkingsearch.util

import android.location.Address
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.ui.fragment.AddParkingFragment

object NavigationManager {

    fun navigateToAddParking(address: Address?, navController: NavController) {
        val bundle = bundleOf(AddParkingFragment.ARG_KEY to address)

        val transitions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.exit_to_left)
            .setPopEnterAnim(R.anim.enter_from_left)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()

        navController.navigate(R.id.addParkingFragment, bundle, transitions)
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