package org.lice.grepl

import jline.console.ConsoleReader
import org.lice.core.SymbolList
import org.lice.compiler.util.println
import org.lice.compiler.util.serr
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
		val console = ConsoleReader()

		GRepl.message.println()

		if (args.isEmpty()) {
			val sl = SymbolList(true)
			val gRepl = GRepl(sl)

			while (true) {
				console.addCompleter { s, i, list ->
					if (s.isNotEmpty()) {
						val arr = s.split(' ', '(', ')', ',', '）', '（', '，')
						if (arr.isNotEmpty()) {
							list.addAll(sl.getSymbolList().filter {
								it.startsWith(arr.last())
							})

							i - arr.last().length
						} else i
					} else i
				}
				gRepl.handle(console.readLine("\nLice > "))
			}

		} else Main.interpret(File(args[0]).apply {
			if (!exists()) serr("file not found: ${args[0]}")
		})
	}
}