package org.lice.tools

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertTrue
import org.lice.core.SymbolList


/**
 * Created by ice1000 on 2017/4/24.

 * @author ice1000
 */

object ReplHelperSpec : Spek({
	given("a symbol list") {
		val sl = SymbolList()
		on("check symbols") {
			it("should contain plus") {
				assertTrue("+" in sl.variables.keys)
			}
		}
	}
})

