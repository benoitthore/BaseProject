package com.benoitthore

class Test {

    inline fun abc(block: () -> Unit) {
        block()
    }

    fun test() {
        abc{
            var a = 1
            println(a)
        }
        abc{
            var b = 1
            println(b)
        }
    }
}