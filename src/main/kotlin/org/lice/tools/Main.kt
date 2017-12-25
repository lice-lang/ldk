package org.lice.tools

import org.apache.commons.cli.*
import org.lice.core.SymbolList
import org.lice.repl.Main.interpret
import org.lice.tools.repl.GRepl
import java.io.File

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @author ice1000
 * @version 1.0.1
 */

object Main {

	@Suppress("MemberVisibilityCanPrivate")
	@JvmStatic
	fun main(args: Array<String>) {

		if (args.isEmpty()) {
			GRepl(SymbolList(true)).runRepl()
			return@main
		}

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
				arg.isEmpty() -> GRepl(SymbolList(true)).runRepl()
				args.size > 1 -> throw Exception()
				else -> interpret(File(arg[0]).apply {
					if (!exists()) {
						System.err.println("file not found: ${arg[0]}")
						return@main
					}
				})
			}
		} catch (t: Throwable) {
			formatter.printHelp(help, "", opts, "")
		}

	}


}