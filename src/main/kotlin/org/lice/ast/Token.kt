@file:Suppress("MemberVisibilityCanPrivate")

package org.lice.ast

/**
 * Created by Glavo on 17-8-26.
 *
 * @author Glavo
 * @since 0.1.0
 */

abstract class Token {
	abstract val beginLine: Int?
	abstract val beginColumn: Int?
	abstract val endLine: Int?
	abstract val endColumn: Int?
}

data class StringToken(val value: String,
                       override val beginLine: Int? = null,
                       override val beginColumn: Int? = null,
                       override val endLine: Int? = null,
                       override val endColumn: Int? = null) : Token() {
	class Builder(val beginLine: Int? = null, val beginColumn: Int? = null) {
		val builder: StringBuilder = StringBuilder()

		fun get(endLine: Int? = null, endColumn: Int? = null): StringToken =
				StringToken(builder.toString(), beginLine, beginColumn, endLine, endColumn)
	}
}


data class NumberToken(val value: Number,
                       override val beginLine: Int? = null,
                       override val beginColumn: Int? = null,
                       override val endLine: Int? = null,
                       override val endColumn: Int? = null) : Token() {
	companion object {
		val integer = Regex("[1-9][0-9]*|0")


	}

	class Builder(val beginLine: Int? = null, val beginColumn: Int? = null) {
		val builder: StringBuilder = StringBuilder()

		fun get(endLine: Int? = null, endColumn: Int? = null): StringToken =
				StringToken(builder.toString(), beginLine, beginColumn, endLine, endColumn)
	}
}