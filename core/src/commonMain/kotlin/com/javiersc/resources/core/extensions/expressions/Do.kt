package com.javiersc.resources.core.extensions.expressions

object Do {
    inline infix fun <reified T> exhaustive(any: T): T = any
}
