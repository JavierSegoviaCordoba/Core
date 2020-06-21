package com.javiersc.resources.core.extensions.serialization

import com.javiersc.resources.core.extensions.resources.readResource
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json

inline infix fun <reified T> DeserializationStrategy<T>.fromFile(path: String): T {
    return Json.parse(this, readResource(path))
}
