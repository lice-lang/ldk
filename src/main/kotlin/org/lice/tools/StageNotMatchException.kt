package org.lice.tools

/**
 * Created by Glavo on 17-8-25.
 *
 * @author Glavo
 * @since 0.1.0
 */
data class StageNotMatchException(override val message: String): Exception() {
	override fun fillInStackTrace(): Throwable = this
}