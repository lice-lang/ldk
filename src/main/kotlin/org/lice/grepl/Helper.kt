@file:Suppress("NOTHING_TO_INLINE")

package org.lice.grepl

import org.lice.core.SymbolList
import org.fusesource.jansi.Ansi.*
import org.fusesource.jansi.Ansi.Color.*

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @author ice1000
 * @version 1.0.0
 */

inline fun SymbolList.getSymbolList(): MutableList<String> {
	return functions.keys.toMutableList()
}

enum class OS {
	Windows,
	Linux {
		override fun green(str: String): String =
				"\u001b[32;1m$str\u001b[0m"

		override fun purple(str: String): String =
			"\u001b[35m$str\u001b[0m"

		override fun blue(str: String): String =
			"\u001b[34;1m$str\u001b[0m"

	},
	Other;

	open fun green(str: String): String = str

	open fun purple(str: String): String = str

	open fun blue(str: String): String = str
}

val listOfSplitters = charArrayOf(' ', '(', ')', ',', '）', '（', '，')

val system = System.getProperty("os.name").run {
	when {
		this.toLowerCase().startsWith("win") -> OS.Windows
		this.toLowerCase().contains("linux") -> OS.Linux
		else -> OS.Other
	}
}

val String.green: String
	get() = system.green(this)

val String.blue: String
	get() = system.blue(this)

val String.purple: String
	get() = system.purple(this)



