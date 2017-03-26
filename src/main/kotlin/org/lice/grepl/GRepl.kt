package org.lice.grepl

import org.lice.compiler.model.Ast
import org.lice.compiler.model.ValueNode
import org.lice.compiler.parse.buildNode
import org.lice.compiler.parse.mapAst
import org.lice.compiler.util.SymbolList
import org.lice.compiler.util.println
import org.lice.compiler.util.serr
import org.lice.repl.VERSION_CODE
import java.util.*
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @version 1.0.0
 */

class GRepl(val symbolList: SymbolList = SymbolList(true)) {

    val stackTrace: Deque<Throwable> = LinkedList()

    init {
        symbolList.defineFunction("exit", { ln, ls ->
            "Good bye!".println()
            System.exit(0)
            ValueNode(Unit, ln)
        })

        symbolList.defineFunction("pst", { ln, ls ->
            if (stackTrace.isNotEmpty()) stackTrace.peek().printStackTrace()
            else "No stack trace.\n".println()

            ValueNode(Unit, ln)
        })

        symbolList.defineFunction("help", { ln, ls ->
            """
                |This is the repl for lice language.

                |You have 4 special commands which you cannot use in the language but the repl:

                |exit: exit the repl
                |pst: print the most recent stack trace
                |help: print this doc
                |version: check the version
                """.stripMargin()
                    .println()
            ValueNode(Unit, ln)
        })

        symbolList.defineFunction("version", { ln, ls ->
            """Lice language interpreter $VERSION_CODE
                |GRepl $Version
            """.stripMargin().println()

            ValueNode(Unit, ln)
        })
    }

    fun handle(
            str: String) {
        try {
            val ast = Ast(mapAst(
                    node = buildNode(str),
                    symbolList = symbolList)
            ).root
            ast.eval()
        } catch (e: Throwable) {
            stackTrace.push(e)
            serr(e.message ?: "")
        }

    }

    companion object {

        val Version: String = "v1.0.0 beta"
        val message: String = """Glavo's Lice language repl $Version
            |see: https://github.com/ice1000/lice

            |for help please input: help
        """.stripMargin()

    }
}