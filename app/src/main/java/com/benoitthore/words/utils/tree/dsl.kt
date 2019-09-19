package com.benoitthore.words.utils.tree

fun <T> Tree<T>.buildFromRoot(block: TreeBuilder<T>.() -> Unit) {
    TreeBuilder(root).block()
}


class TreeBuilder<T>(val lastNode: Tree.Node<T>) {

    operator fun T.invoke(block: TreeBuilder<T>.() -> Unit): Tree.Node<T> {
        val newNode = toNode()
        TreeBuilder(newNode).block()
        return newNode
    }

    operator fun T.unaryPlus() {
        +toNode()
    }

    operator fun Tree.Node<T>.unaryPlus() {
        parent = lastNode
        lastNode.addChild(this)
    }

    fun T.toNode() = Tree.Node(this, parent = lastNode)
}

