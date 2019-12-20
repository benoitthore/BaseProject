package com.mobile.core.repo

typealias Mapper<I, O> = (I) -> O

operator fun <I, O> Mapper<I, O>.invoke(input: List<I>): List<O> = input.map(this)
