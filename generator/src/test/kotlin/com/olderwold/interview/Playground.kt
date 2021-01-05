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

    @Test
    fun `should be 2`() {
        // ((2 + 2) + 3) - 5
        /**
        val input = """
            {
              "operator": "-",
              "members": ["5"],
              "node": {
                "operator": "+",
                "members": ["3"],
                "node": {
                  "operator": "+",
                  "members": ["2", "2"]
                }
              }
            }
        """.trimIndent()
        **/
        // iterate through string, extract numbers, operator

        val nestedNode = Data(
            operator = "+",
            members = listOf("3"),
            node = NodeImpl(
                operator = "+",
                members = listOf("2", "2"),
            )
        )
        val data = Data(
            operator = "-",
            members = listOf("5"),
            node = nestedNode,
        )

        compute(root = data) shouldBeEqualTo 2
    }

    @Test
    fun `should be -2`() {
        // ((2 + 2) + 3) - 5
        /**
        val input = """
        {
        "operator": "-",
        "members": ["5"],
        "node": {
        "operator": "+",
        "members": ["3"],
        "node": {
        "operator": "-",
        "members": ["2", "2"]
        }
        }
        }
        """.trimIndent()
         **/
        // iterate through string, extract numbers, operator

        val nestedNode = Data(
            operator = "+",
            members = listOf("3"),
            node = NodeImpl(
                operator = "-",
                members = listOf("2", "2"),
            )
        )
        val data = Data(
            operator = "-",
            members = listOf("5"),
            node = nestedNode,
        )

        compute(root = data) shouldBeEqualTo -2
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

    private fun combineResult(root: Node, input: Int): Int {
        if (root.members.isEmpty()) {
            return input
        } else {
            if (root.members.size >= 2) {
                val integers = root.members.map { it.toInt() }
                val outcome = when {
                    root.operator == "+" -> {
                        integers.sum()
                    }
                    root.operator == "-" -> {
                        integers.reduceRight { num, acc -> acc - num }
                    }
                    else -> 0
                }
                return input + outcome
            } else {
                val member = root.members.first().toInt()
                return when {
                    root.operator == "+" -> {
                        member + input
                    }
                    root.operator == "-" -> {
                        input - member
                    }
                    else -> 0
                }
            }
        }
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
