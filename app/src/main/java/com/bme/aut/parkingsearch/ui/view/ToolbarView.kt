package com.bme.aut.parkingsearch.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.ui.activity.MainActivity
import com.bme.aut.parkingsearch.util.gone
import com.bme.aut.parkingsearch.util.toast
import com.bme.aut.parkingsearch.util.visible
import kotlinx.android.synthetic.main.view_toolbar.view.*

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    init {
        View.inflate(context, R.layout.view_toolbar, this)
    }

    fun bind(toolbarModel: ToolbarModel) {

        when (toolbarModel.type) {
            ToolbarType.NAVIGATE_BACK -> {
                searchEditText.gone()
                searchIconImageView.gone()

                backIconImageView.visible()
                backIconWrapper.setOnClickListener {
                    (context as MainActivity).onBackPressed()
                }
            }
            ToolbarType.SEARCH -> {
                searchIconWrapper?.setOnClickListener {
                    context.toast("Not implemented yet")
                }
            }
        }

    }
}