package com.benoitthore.words.utils.tree

import java.lang.StringBuilder

class Tree<T>(rootData: T) {
    val root: Node<T> = Node(data = rootData)

    class Node<T>(val data: T,
                  var parent: Node<T>? = null) {

        private val _children: MutableList<Node<T>> = mutableListOf()
        val children: List<Node<T>> get() = _children

        fun addChild(child: Node<T>) {
            _children += child
        }

    }

}

// Print

fun <T> Tree<T>.visualPrint(printData: T.() -> String = { toString() }) =
        root.visualPrint(printData = printData).toString()

fun <T> Tree.Node<T>.visualPrint(
        indent: Int = 0,
        sb: StringBuilder = StringBuilder(),
        printData: T.() -> String
): StringBuilder {
    (0 until indent).forEach {
        sb.append("\t")
    }
    sb.append(data.printData())
    sb.append("\n")
    children.forEach { it.visualPrint(indent + 1, sb, printData) }
    return sb
}