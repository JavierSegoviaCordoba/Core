package com.javiersc.resources.core.extensions.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun AppCompatActivity.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launch(block = block)
}