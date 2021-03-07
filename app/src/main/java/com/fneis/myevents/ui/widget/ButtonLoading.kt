package com.fneis.myevents.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.fneis.myevents.R
import com.fneis.myevents.databinding.ButtonLoadingBinding

class ButtonLoading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ButtonLoadingBinding.inflate(LayoutInflater.from(context), this)
    private var text = ""
    init {
        val customAttrs = context.obtainStyledAttributes(attrs, R.styleable.ButtonLoading, defStyleAttr, defStyleRes)
        try {
            text = customAttrs.getString(R.styleable.ButtonLoading_text).toString()
            binding.button.text = text
        } finally {
            customAttrs.recycle()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) { binding.button.setOnClickListener(l) }

    fun showLoading() {
        binding.button.apply {
            text = ""
            alpha = .5f
            isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        binding.button.apply {
            text = this.text
            alpha = 1.0f
            isEnabled = true
            binding.progressBar.visibility = View.GONE
        }
    }
}
