package com.javiersc.resources.core.extensions.okhttp

import com.javiersc.resources.core.extensions.resources.readResource
import okhttp3.mockwebserver.MockResponse

infix fun String?.mockResponseWith(code: Int): MockResponse {
    return MockResponse().apply {
        setResponseCode(code)
        this@mockResponseWith?.let { setBody(readResource(it)) }
        setHeader("Content-Type", "application/json")
    }
}
