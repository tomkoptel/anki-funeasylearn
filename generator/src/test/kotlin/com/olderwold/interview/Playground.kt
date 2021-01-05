package com.olderwold.interview

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class Playground {
    // file format(e.g. Json)
    // parse input and calculate the result
    @Test
    fun `should be 7`() {
        // iterate through string, extract numbers, operator
        /**
            {
              "operator": "+",
              "members": ["3"],
              "node": {
                "operator": "+",
                "members": ["2", "2"]
              }
            }
        **/

        val data: Node = Data(
            operator = "+",
            members = listOf("3"),
            node = NodeImpl(
                operator = "+",
                members = listOf("2", "2"),
            )
        )

        compute(root = data) shouldBeEqualTo 7
    }

    private fun compute(root: Node, output: Int = 0): Int {
        val nestedNode = root.node
        return if (nestedNode == null) {
            combineResult(root, output)
        } else {
            val computed = compute(nestedNode, output)
            combineResult(root, computed)
        }
    }

    private fun combineResult(root: Node, output: Int): Int {
        val integers = root.members.map { it.toInt() }
        val outcome = when {
            root.operator == "+" -> {
                integers.sum()
            }
            root.operator == "-" -> {
                integers.reduceRight { num, acc -> num - acc }
            }
            else -> 0
        }
        return output + outcome
    }
}

data class Data(
    override val operator: String,
    override val members: List<String>,
    override val node: Node,
) : Node

data class NodeImpl(
    override val operator: String,
    override val members: List<String>,
    override val node: Node? = null,
) : Node

interface Node {
    val operator: String
    val members: List<String>
    val node: Node?
}
