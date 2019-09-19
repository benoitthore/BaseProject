package com.benoitthore.words.features.utils

import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TextViewDelegate(private val tv: TextView) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String = tv.text.toString()


    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        tv.text = value
    }

}

fun TextView.delegate() =  TextViewDelegate(this)