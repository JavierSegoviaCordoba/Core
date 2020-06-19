package com.javiersc.resources.core.data

import com.javiersc.resources.networkResponse.NetworkResponse
import com.javiersc.resources.resource.Resource
import com.javiersc.resources.resource.extensions.ifError
import com.javiersc.resources.resource.extensions.ifSuccess
import com.javiersc.resources.resource.extensions.toResourceSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified D, reified E, reified NR, reified NE, reified En> merger(
    crossinline remote: () -> NetworkResponse<NR, NE>,
    crossinline local: () -> En,
    crossinline save: (En) -> Unit,
    crossinline remoteToDomain: (NetworkResponse<NR, NE>) -> Resource<D, E>,
    crossinline domainToLocal: (D) -> En,
    crossinline localToDomain: (En) -> D,
    localOnStart: Boolean = false,
    loadingOnStart: Boolean = true,
): Flow<Resource<D, E>> = flow {
    if (localOnStart) emit(localToDomain(local()).toResourceSuccess())

    if (loadingOnStart) emit(Resource.Loading)

    val resource: Resource<D, E> = remoteToDomain(remote())
    resource.ifError { emit(resource) }
    resource.ifSuccess { domainObject: D -> save(domainToLocal(domainObject)) }

    emit(localToDomain(local()).toResourceSuccess())
}
