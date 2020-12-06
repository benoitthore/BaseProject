package com.benoitthore.sonoff

fun String.replaceAll(old: String, new: String): String {
    var ret = this
    while (ret.contains(old)) {
        ret = ret.replace(old,new)
    }
    return ret
}
