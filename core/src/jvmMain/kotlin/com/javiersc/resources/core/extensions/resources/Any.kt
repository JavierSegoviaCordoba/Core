package com.javiersc.resources.core.extensions.resources

actual fun Any.readResource(jsonFileName: String): String {
    return this::class.java.classLoader!!.getResource(jsonFileName)!!.readText()
}
