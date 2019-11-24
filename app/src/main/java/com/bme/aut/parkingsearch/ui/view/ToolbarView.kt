package com.bme.aut.parkingsearch.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.events.SearchClickedEvent
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.ui.activity.MainActivity
import com.bme.aut.parkingsearch.util.NavigationManager
import com.bme.aut.parkingsearch.util.gone
import com.bme.aut.parkingsearch.util.hideSoftKeyboard
import com.bme.aut.parkingsearch.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import org.greenrobot.eventbus.EventBus

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : BaseView(context, attrs, defStyle) {

    init {
        View.inflate(context, R.layout.view_toolbar, this)
    }

    fun bind(model: ToolbarModel) {

        when (model.type) {
            ToolbarType.NAVIGATE_BACK -> {
                searchEditText.gone()
                searchIconImageView.gone()

                titleTextView.visible()
                titleTextView.text = model.title
                backIconImageView.visible()
                backIconWrapper.setOnClickListener {
                    NavigationManager.onBackPressed((context as MainActivity).navHostFragment.findNavController())
                }
            }
            ToolbarType.SEARCH -> {
                searchIconWrapper?.setOnClickListener {
                    EventBus.getDefault()
                        .post(SearchClickedEvent(address = searchEditText.text.toString()))
                    this.hideSoftKeyboard()
                    (context as MainActivity).currentFocus?.clearFocus()
                }
            }
        }

    }
}