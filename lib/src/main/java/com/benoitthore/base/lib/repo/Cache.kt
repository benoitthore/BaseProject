package com.benoitthore.base.lib.repo

interface Cache<T> {
    val cachedValue: T?
    fun clear()
}

interface NetworkCache<T> : Cache<ApiResponse<T>> {
    val serviceCall: suspend () -> ApiResponse<T>
    suspend fun get(): ApiResponse<T>
}

class InMemoryNetworkCache<T>(
        override val serviceCall: suspend () -> ApiResponse<T>
) : NetworkCache<T> {

    override var cachedValue: ApiResponse<T>? = null
        private set

    override fun clear() {
        cachedValue = null
    }

    override suspend fun get(): ApiResponse<T> =
            cachedValue ?: serviceCall()
                    .also {
                        when (it) {
                            is ApiResponse.Success -> cachedValue = it
                            else -> {
                                // Only store success
                            }
                        }
                    }
}
