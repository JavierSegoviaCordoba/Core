package com.javiersc.resources.core.delegates.navigation

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Usage:
 * class MyActivity : AppCompatActivity(R.layout.my_activity) {
 *     private val navController by navController()
 * }
 * Notes:
 * Solves a won't fix bug with FragmentContainerView
 * https://issuetracker.google.com/issues/142847973
 */
class ActivityNavControllerDelegate : ReadOnlyProperty<AppCompatActivity, NavController> {
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): NavController {
        val view: View = thisRef.findViewById(android.R.id.content)
        val viewId: Int = view.getAllViews().firstOrNull { it is FragmentContainerView }?.id
            ?: throw IllegalStateException("There isn't a FragmentContainerView in your layout")
        val navHostFragment: Fragment = thisRef.supportFragmentManager.findFragmentById(viewId)!!
        return navHostFragment.findNavController()
    }
}

private fun View.getAllViews(): List<View> {
    return if (this !is ViewGroup || childCount == 0) listOf(this)
    else children.toList().flatMap { it.getAllViews() }.plus(this as View)
}

fun AppCompatActivity.navController(): ActivityNavControllerDelegate {
    return ActivityNavControllerDelegate()
}
