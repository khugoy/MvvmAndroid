package com.sapient.demoapp.presentation.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.snackBar(message: String) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun View.showProgressBar(isLoading: Boolean = false) {
    this.visibility =
        if (isLoading) VISIBLE else GONE
}