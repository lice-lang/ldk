package org.lice.grepl

import jline.console.ConsoleReader
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.lice.core.SymbolList
import org.lice.lang.DefineResult
import org.lice.repl.Main.interpret
import java.io.File

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

	@JvmStatic
	fun repl() {
		val console = ConsoleReader()

		val repl = GRepl(SymbolList(true))
		console.addCompleter(repl.completer)

		println(GRepl.message)

		while (true) {
			val res = repl.handle(console.readLine((repl.hint + " ").purple))

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