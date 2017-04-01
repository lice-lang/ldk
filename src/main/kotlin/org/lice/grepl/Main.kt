package org.lice.grepl

import jline.console.ConsoleReader
import org.lice.compiler.util.SymbolList
import org.lice.compiler.util.println
import org.lice.compiler.util.serr
import org.lice.repl.Main
import java.io.File

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @version 1.0.0
 */

object Main {
	@JvmStatic
	fun main(args: Array<String>) {
		val console: ConsoleReader = ConsoleReader()


		GRepl.message.println()

		if (args.isEmpty()) {
			val sl = SymbolList(true)
			val grepl = GRepl(sl)

			while (true) {
				console.addCompleter { s, i, list ->
					if (s.isNotEmpty()) {
						val arr = s.split(' ', '(', ')', ',')
						if (arr.isNotEmpty()) {
							list.addAll(sl.getSymbolList().filter {
								it.startsWith(arr.last())
							})

							i - arr.last().length
						} else i
					} else i
				}
				grepl.handle(console.readLine("\nLice >"))
			}

		} else {
			Main.interpret(File(args[0]).apply {
				if (!exists()) serr("file not found: ${args[0]}")
			})
		}
	}
}