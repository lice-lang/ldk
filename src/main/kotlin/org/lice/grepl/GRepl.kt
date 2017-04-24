package org.lice.grepl

import org.lice.compiler.parse.buildNode
import org.lice.compiler.parse.mapAst
import org.lice.compiler.util.println
import org.lice.core.SymbolList
import org.lice.lang.Echoer
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
		symbolList.provideFunction("exit", {
			"Good bye! :)".println()
			System.exit(0)
		})

		symbolList.provideFunction("pst", {
			if (stackTrace.isNotEmpty()) stackTrace.peek().printStackTrace()
			else Echoer.echoErr("No stack trace.\n")
			null
		})

		symbolList.provideFunction("help", {
			"""This is the repl for lice language.

			|You have 4 special commands which you cannot use in the language but the repl:

			|exit: exit the repl
			|pst: print the most recent stack trace
			|help: print this doc
			|version: check the version""".trimMargin()
		})

		symbolList.provideFunction("version", {
			"""Lice language interpreter $VERSION_CODE
			|GRepl $Version""".trimMargin()
		})
	}

	fun handle(str: String) = try {
		mapAst(buildNode(str), symbolList).eval()
	} catch (e: Throwable) {
		stackTrace.push(e)
		Echoer.echolnErr(e.message ?: "")
	}

	companion object HelpMessage {

		val Version = "v1.2"
		val message = """Glavo's Lice language repl $Version
			|see: https://github.com/lice-lang/lice-repl
			|see also: https://github.com/lice-lang/lice

			|剑未佩妥，出门已是江湖。千帆过尽，归来仍是少年。
			|for help please input: help""".trimMargin()
	}
}