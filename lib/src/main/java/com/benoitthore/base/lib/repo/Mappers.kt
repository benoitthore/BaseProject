package com.benoitthore.base.lib.repo

typealias Mapper<I, O> = (I) -> O

operator fun <I, O> Mapper<I, O>.invoke(input: Iterable<I>): List<O> = input.map(this)
