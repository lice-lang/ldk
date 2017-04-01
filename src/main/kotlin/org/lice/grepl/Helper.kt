package org.lice.grepl

import org.lice.compiler.util.SymbolList
import java.util.*

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @version 1.0.0
 */

@JvmOverloads

inline fun SymbolList.getSymbolList(): MutableList<String> {
	val list: MutableList<String> = ArrayList()
	list.addAll(this.functions.keys)
	list.addAll(this.variables.keys)
	return list
}

