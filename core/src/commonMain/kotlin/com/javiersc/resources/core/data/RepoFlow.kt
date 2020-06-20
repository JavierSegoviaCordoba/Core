package com.javiersc.resources.core.data

import com.javiersc.resources.resource.Resource
import com.javiersc.resources.resource.extensions.ifError
import com.javiersc.resources.resource.extensions.ifSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

inline fun <reified R, reified E> repoFlow(
    crossinline getFromRemote: () -> Flow<Resource<R, E>>,
    crossinline getFromLocal: () -> Flow<Resource<R, E>>,
    crossinline saveToLocal: suspend (R) -> Unit,
    localOnStart: Boolean = false,
    loadingOnStart: Boolean = true,
): Flow<Resource<R, E>> = flow {
    if (localOnStart) emitAll(getFromLocal())

    if (loadingOnStart) emit(Resource.Loading)

    getFromRemote().collect { resource ->
        resource.apply {
            ifError { emit(resource) }
            ifSuccess { data -> saveToLocal(data) }
        }
    }
    if (!localOnStart) emitAll(getFromLocal())
}
