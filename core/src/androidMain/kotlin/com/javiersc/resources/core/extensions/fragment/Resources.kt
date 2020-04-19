package com.javiersc.resources.core.extensions.fragment

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.color(@ColorRes res: Int): Int = ContextCompat.getColor(requireContext(), res)

fun Fragment.string(@StringRes res: Int): String = getString(res)
