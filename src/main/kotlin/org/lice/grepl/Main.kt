package org.lice.grepl

import jline.console.ConsoleReader
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.lice.core.SymbolList
import org.lice.lang.DefineResult
import org.lice.repl.Main.interpret
import java.io.File
import java.util.*

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @author ice1000
 * @version 1.0.1
 */

object Main {

	@JvmStatic
	fun main(args: Array<String>) {
		if (args.isEmpty()) repl()
		else {
			val opts = Options()
			opts.addOption("?", "help", false, "Print this usage message")

			val help = "lice [-options] [file]"

			val formatter = HelpFormatter()
			val parser = DefaultParser()

			try {
				val cl = DefaultParser().parse(opts, args)
				if (cl.hasOption("?")) {
					formatter.printHelp(help, opts)
					return@main
				}

				val arg = cl.args
				when {
					arg.isEmpty() -> repl()
					args.size > 1 -> throw Exception()
					else -> interpret(File(arg[0]).apply {
						if (!exists()) {
							System.err.println("file not found: ${arg[0]}")
							return@main
						}
					}
					)
				}


			} catch (t: Throwable) {
				formatter.printHelp(help, "", opts, "")
			}
		}
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

	@JvmStatic
	fun repl() {
		val console = ConsoleReader()

		val repl = GRepl(SymbolList(true))
		console.addCompleter(repl.completer)

		println(GRepl.message)

		val stage = LinkedList<Char>()

		loop@
		while (true) {
			val sb = StringBuilder()

			var first = true

			val sss = CharArray(repl.hint.length - 1) { ' ' }.run {
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
						repl.hint + " "
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


			val res = repl.handle(sb.toString())
			when (res?.o) {
				Unit, null -> Unit
				is DefineResult -> println((res.o as DefineResult).res)

				else -> {
					val n = "res${repl.count++}"

					repl.symbolList.provideFunction(n) { res.o }

					println("${n.blue}: ${res.type.name.green} = ${res.o.toString()}")
				}
			}
			println()
		}

	}
}