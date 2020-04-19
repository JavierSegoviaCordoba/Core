package com.javiersc.resources.core.extensions.viewBinding

import android.content.Context
import androidx.viewbinding.ViewBinding

val ViewBinding.context: Context get() = this.root.context
