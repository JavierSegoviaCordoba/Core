package com.javiersc.resources.core.delegates.viewBinding

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val activity: AppCompatActivity,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<AppCompatActivity, T> {

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        return viewBindingFactory(activity.findViewById(android.R.id.content))
    }
}

fun <T : ViewBinding> AppCompatActivity.viewBinding(
    viewBindingFactory: (View) -> T
): ActivityViewBindingDelegate<T> {
    return ActivityViewBindingDelegate(this, viewBindingFactory)
}
