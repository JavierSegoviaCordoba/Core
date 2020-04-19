package com.javiersc.resources.core.extensions.activity

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Activity.color(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)

fun Activity.string(@StringRes res: Int): String = getString(res)