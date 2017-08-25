@file:Suppress("MemberVisibilityCanPrivate")

package org.lice.grepl

import jline.console.completer.Completer
import org.lice.compiler.parse.buildNode
import org.lice.compiler.parse.mapAst
import org.lice.compiler.util.println
import org.lice.core.SymbolList
import org.lice.lang.Echoer
import org.lice.VERSION
import org.lice.compiler.model.*
import org.lice.compiler.util.InterpretException
import org.lice.lang.DefineResult
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

	var count: Int = 0

	init {
		val defFunc = { name: String, params: List<String>, block: (Node) -> Node, body: Node ->
			symbolList.defineFunction(name) { ln, args ->
				val backup = params.map { symbolList.getFunction(it) }
				if (args.size != params.size)
					InterpretException.numberOfArgumentNotMatch(params.size, args.size, ln)
				args
						.map(block)
						.forEachIndexed { index, obj ->
							//						val obj = obj.process()
							if (obj is SymbolNode)
								symbolList.defineFunction(params[index], obj.function())
							else symbolList.defineFunction(params[index], { _, _ -> obj })
						}
				val ret = ValueNode(body.eval().o ?: Value.Nullptr, ln)
				backup.forEachIndexed { index, node ->
					if (node != null) symbolList.defineFunction(params[index], node)
					else symbolList.removeFunction(params[index])
				}
				ret
			}
		}
		val definer = { funName: String, block: (Node) -> Node ->
			symbolList.defineFunction(funName) { meta, ls ->
				if (ls.size < 2) InterpretException.tooFewArgument(2, ls.size, meta)
				val name = (ls.first() as SymbolNode).name
				val body = ls.last()
				val params = ls
						.subList(1, ls.size - 1)
						.map { (it as? SymbolNode)?.name ?: InterpretException.notSymbol(meta) }
				val override = symbolList.isFunctionDefined(name)
				defFunc(name, params, block, body)
				return@defineFunction ValueNode(DefineResult(
						"${if (override) "override".green else ""}${"defined".green} $name"))
			}
		}

		symbolList.provideFunction(":exit") {
			"Good bye! :)".println()
			System.exit(0)
		}

		symbolList.provideFunction(":pst") {
			if (stackTrace.isNotEmpty()) stackTrace.peek().printStackTrace()
			else Echoer.echoErr("No stack trace.\n")
			null
		}

		symbolList.provideFunction(":help") {
			"""This is the repl for lice language.

You have 4 special commands which you cannot use in the language but the repl:

:exit: exit the repl
:pst: print the most recent stack trace
:help: print this doc
:version: check the version"""
		}

		symbolList.provideFunction(":version") {
			"""Lice language interpreter $VERSION
			|GRepl $Version""".trimMargin()
		}

		symbolList.provideFunction("debug") {
			throw UnsupportedOperationException()
		}

		symbolList.provideFunction("print") { ls ->
			print(ls.joinToString(" "))
		}

		symbolList.provideFunction("println") { ls ->
			println(ls.joinToString(" "))
		}

		definer("def") { node -> ValueNode(node.eval().o ?: Value.Nullptr) }
		definer("deflazy") { node -> LazyValueNode({ node.eval() }) }
		definer("defexpr") { it }

	}

	val completer: Completer
		get() = Completer { s, i, list ->
			if (s.isNotEmpty()) {
				val arr = s.substring(0, i).split(*listOfSplitters)
				if (arr.isNotEmpty()) {
					list.addAll(symbolList
							.getSymbolList()
							.filter { it.startsWith(arr.last()) })

					i - arr.last().length
				} else i
			} else i
		}


	fun handle(str: String): Value? =
			try {
				mapAst(buildNode(str), symbolList).eval()
			} catch (e: Throwable) {
				stackTrace.push(e)
				System.err.println(e.message ?: "")
				System.err.println()
				null
			}

	companion object HelpMessage {

		val Version = "v1.3"
		val message = """Welcome to Lice REPL $Version  (Lice $Version, Java ${System.getProperty("java.version")})
see: ${"https://github.com/lice-lang/lice-repl".underline}
see also: ${"https://github.com/lice-lang/lice".underline}

Type in expressions for evaluation. Or try :help."""
	}
}