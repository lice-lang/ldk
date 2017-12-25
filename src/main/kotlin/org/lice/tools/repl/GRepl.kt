@file:Suppress("MemberVisibilityCanPrivate")

package org.lice.tools.repl

import jline.console.ConsoleReader
import jline.console.completer.Completer
import org.lice.VERSION
import org.lice.core.SymbolList
import org.lice.model.LazyValueNode
import org.lice.model.ValueNode
import org.lice.parse.buildNode
import org.lice.parse.mapAst
import org.lice.util.className
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

	var hint: String = "lice>"

	val console = ConsoleReader()

	var count: Int = 0

	init {
		symbolList.defineVariable(":help", ValueNode(
				"""This is the repl for lice language.

You have 4 special commands which you cannot use in the language but the repl:

:exit: exit the repl
:pst: print the most recent stack trace
:help: print this doc
:version: check the version"""))

		symbolList.defineVariable(":version", ValueNode("Lice language interpreter $VERSION\nGRepl $Version"))
		symbolList.provideFunction("print") { print(it.joinToString(" ")) }
		symbolList.provideFunction("println") { println(it.joinToString(" ")) }
		symbolList.defineVariable(":exit", LazyValueNode({ System.exit(0) }))
	}

	val completer: Completer
		get() = Completer { s, i, list ->
			if (s.isNotEmpty()) {
				val arr = s.substring(0, i).split(*listOfSplitters)
				if (arr.isNotEmpty()) {
					list.addAll(symbolList.symbols.filter { it.startsWith(arr.last()) })
					i - arr.last().length
				} else i
			} else i
		}


	fun handle(str: String): Any? = try {
		mapAst(buildNode(str), symbolList).eval()
	} catch (e: Throwable) {
		stackTrace.push(e)
		System.err.println(e.message ?: "")
		System.err.println()
		null
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun scanf(str: String, stage: LinkedList<Char>): Boolean {
		var instr = false

		str.forEach {
			if (instr) when {
				it == '\\' -> {
					if (stage.isNotEmpty() && stage.first == '\\')
						stage.pop()
					else stage.push('\\')
				}
				it == '"' && stage.isNotEmpty() && stage.first == '\\' -> instr = false
				it == '"' -> instr = false
				(it in "bnrf'") && stage.isNotEmpty() && stage.first == '\\' -> stage.pop()
				it == 'u' && stage.isNotEmpty() && stage.first == '\\' -> {
					stage.pop()
					stage.push('0')
				}
				(it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F') &&
						stage.isNotEmpty() && stage.first in '0'..'3' -> {
					if (stage.first == '3') stage.pop()
					else stage.push(stage.pop() + 1)
				}
			}
			else when (it) {
				'(', '（' -> stage.push('(')
				')', '）' -> {
					if (stage.isEmpty())
						throw StageNotMatchException("${"error: ".red}Braces not match: Unexpected ')'.")
					if (stage.pop() != '(')
						throw StageNotMatchException("${"error: ".red}unclosed string literal")
				}
				'"' -> instr = true
			}
		}
		if (instr) throw StageNotMatchException("${"error: ".red}unclosed string literal")

		return stage.isEmpty()
	}

	fun runRepl() {
		console.addCompleter(this.completer)
		println(GRepl.message)
		val stage = LinkedList<Char>()

		loop@ while (true) {
			val sb = StringBuilder()
			var first = true
			val sss = CharArray(this.hint.length - 1) { ' ' }.run {
				String(this)
			} + "| "
			var elcount = 0
			try {
				do {
					if (elcount >= 2) {
						println("You typed two blank lines.  Starting a new command.")
						println()
						continue@loop
					}
					val s = console.readLine((if (first) {
						first = false
						this.hint + " "
					} else sss).purple)
					if (s.isEmpty()) elcount++
					sb.append(s)
				} while (!scanf(s, stage))
			} catch (s: StageNotMatchException) {
				System.err.println(s.message)
				println()
				continue@loop
			} finally {
				stage.clear()
			}


			val res = this.handle(sb.toString())
			when (res) {
				Unit, null -> Unit
				else -> {
					val n = "res${this.count++}"
					this.symbolList.defineVariable(n, ValueNode(res))
					println("${n.blue}: ${res.className().green} = $res")
				}
			}
			println()
		}

	}

	companion object HelpMessage {

		val Version = "v2.1"
		val message = """Welcome to Lice REPL $Version  (Lice $VERSION, Java ${System.getProperty("java.version")})
see: ${"https://github.com/lice-lang/lice-repl".underline}
see also: ${"https://github.com/lice-lang/lice".underline}

Type in expressions for evaluation. Or try :help.
"""
	}
}