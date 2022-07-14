package com.example.movieapp.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.movieapp.BuildConfig
import com.example.movieapp.R
import com.google.android.material.textfield.TextInputLayout


fun Context.showToastMessage(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun View.toggleVisibility(status: Boolean) {
    if(status) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun TextInputLayout.setEmptyErrorMessage(message: String) {
    if(this.editText?.text.toString().isEmpty()) {
        this.error = message
    }
}

fun TextInputLayout.setTextWatcher() {
    this.editText?.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            this@setTextWatcher.error = ""
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    })
}

fun AppCompatImageView.setImgWithGlide(imgCode: String) {
    Glide.with(this.context)
        .applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.ic_movieapp_logo)
        )
        .load("${BuildConfig.BASE_URL_IMG}$imgCode")
        .into(this)
}

fun Double.toRatingFloat(): Float {
    return (this.div(10) * 5).toFloat()
}