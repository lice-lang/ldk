@file:Suppress("NOTHING_TO_INLINE")

package org.lice.grepl

import org.lice.core.SymbolList

/**
 * Created by glavo on 17-3-26.
 *
 * @author Glavo
 * @author ice1000
 * @version 1.0.0
 */

inline fun SymbolList.getSymbolList(): MutableList<String> {
	return functions.keys.toMutableList()
}

