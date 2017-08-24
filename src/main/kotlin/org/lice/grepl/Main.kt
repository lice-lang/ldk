package org.lice.grepl

import jline.console.ConsoleReader
import org.lice.compiler.util.println
import org.lice.core.SymbolList
import org.lice.lang.Echoer
import org.lice.repl.Main
import org.lice.repl.Repl
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
		Echoer.repl = true

		val console = ConsoleReader()

		GRepl.message.println()

		if (args.isEmpty()) {
			val gRepl = GRepl(SymbolList(true))
			console.addCompleter(gRepl.completer)

			while (true) {
				gRepl.handle(console.readLine(Repl.HINT))
			}

		} else Main.interpret(File(args[0]).apply {
			if (!exists()) Echoer.echoErr("file not found: ${args[0]}")
		})
	}
}