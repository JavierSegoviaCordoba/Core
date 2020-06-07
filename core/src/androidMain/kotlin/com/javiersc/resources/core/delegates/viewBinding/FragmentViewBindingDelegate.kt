package com.javiersc.resources.core.delegates.viewBinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Usage:
 * MyFragment : Fragment(R.my_fragment) {
 *     private val binding by viewBinding(MyFragmentBinding::bind)
 * }
 * Notes:
 * As you can see, it is necessary to pass the layout as Fragment argument.
 *
 * Source: https://github.com/Zhuinden/ViewBindingExample/blob/master/app/src/main/java/com/zhuinden/viewbindingexample/FragmentViewBindingDelegate.kt
 */
class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val binds: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var _binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = _binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException(
                "Should not attempt to get bindings when Fragment views are destroyed."
            )
        }

        return binds(thisRef.requireView()).also { _binding = it }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(binds: (View) -> T): FragmentViewBindingDelegate<T> {
    return FragmentViewBindingDelegate(this, binds)
}
