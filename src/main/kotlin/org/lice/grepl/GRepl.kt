package org.lice.grepl

import org.lice.compiler.model.EmptyNode
import org.lice.compiler.parse.buildNode
import org.lice.compiler.parse.mapAst
import org.lice.core.SymbolList
import org.lice.compiler.util.println
import org.lice.compiler.util.serr
import org.lice.repl.VERSION_CODE
import java.util.*

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @version 1.0.0
 */

class GRepl
@JvmOverloads
constructor(val symbolList: SymbolList = SymbolList(true)) {

	val stackTrace: Deque<Throwable> = LinkedList()

	init {
		symbolList.defineFunction("exit", { ln, _ ->
			"Good bye! :)".println()

			System.exit(0)
			EmptyNode(ln)
		})

		symbolList.defineFunction("pst", { ln, _ ->
			if (stackTrace.isNotEmpty()) stackTrace.peek().printStackTrace()
			else "No stack trace.\n".println()

			EmptyNode(ln)
		})

		symbolList.defineFunction("help", { ln, _ ->
			"""This is the repl for lice language.

		|You have 4 special commands which you cannot use in the language but the repl:

		|exit: exit the repl
		|pst: print the most recent stack trace
		|help: print this doc
		|version: check the version"""
					.trimMargin()
					.println()
			EmptyNode(ln)
		})

		symbolList.defineFunction("version", { ln, _ ->
			"""Lice language interpreter $VERSION_CODE
		|GRepl $Version"""
					.trimMargin()
					.println()

			EmptyNode(ln)
		})
	}

	fun handle(str: String) = try {
		mapAst(buildNode(str), symbolList).eval()
	} catch (e: Throwable) {
		stackTrace.push(e)
		serr(e.message ?: "")
	}

	companion object HelpMessage {

		val Version = "v1.1"
		val message = """Glavo's Lice language repl $Version
		|see: https://github.com/lice-lang/lice-repl
		|see also: https://github.com/lice-lang/lice

		|for help please input: help
""".trimMargin()

	}
}