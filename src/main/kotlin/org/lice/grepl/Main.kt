package org.lice.grepl

import jline.console.ConsoleReader
import org.fusesource.jansi.AnsiConsole
import org.lice.core.SymbolList
import org.lice.lang.Echoer
import org.lice.repl.Main
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
		AnsiConsole.systemInstall()
		val console = ConsoleReader()

		if (args.isEmpty()) {
			val repl = GRepl(SymbolList(true))
			console.addCompleter(repl.completer)

			println(GRepl.message)

			while (true) {
				val res = repl.handle(console.readLine((repl.hint + " ").purple))

				if (res != null && res.o  != Unit) {
					val n = "res${repl.count++}"

					repl.symbolList.provideFunction(n) { res.o }

					println("${n.blue}: ${res.type.name.green} = ${res.o.toString()}")
				}
				println()
			}

		} else Main.interpret(File(args[0]).apply {
			if (!exists()) Echoer.echoErr("file not found: ${args[0]}")
		})
	}
}