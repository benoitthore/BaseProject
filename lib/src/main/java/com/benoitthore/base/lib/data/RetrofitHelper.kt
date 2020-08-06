package com.benoitthore.base.lib.data

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

fun ignoreUnknownObjectMapper(): ObjectMapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
val jacksonFactory: JacksonConverterFactory = JacksonConverterFactory.create(ignoreUnknownObjectMapper())

inline fun <reified S> createService(url: String, block: Retrofit.Builder.() -> Unit = {}) = Retrofit.Builder().apply {
    baseUrl(url)
    block()
    client(OkHttpClient())
    addConverterFactory(jacksonFactory)
}.build().create(S::class.java)
