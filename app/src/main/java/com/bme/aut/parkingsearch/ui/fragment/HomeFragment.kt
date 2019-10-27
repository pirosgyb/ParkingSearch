package com.bme.aut.parkingsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.util.NavigationManager
import com.bme.aut.parkingsearch.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        toolbarView?.bind(ToolbarModel(type = ToolbarType.SEARCH))

        addButton.setOnClickListener {
            NavigationManager.navigateToAddParking(findNavController())
        }

        detailsButton.setOnClickListener {
            NavigationManager.navigateToDetails(findNavController())
        }
    }

}
