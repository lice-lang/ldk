package org.lice.util

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

/**
 * Created by Glavo on 17-8-26.
 *
 * @author Glavo
 * @since 0.1.0
 */

val context: MathContext = MathContext.DECIMAL128

fun Number.toBigInt(): BigInteger =
		when (this) {
			is Byte, is Short, is Int, is Long, is Float, is Double ->
				BigInteger.valueOf(this.toLong())
			is BigInteger -> this
			is BigDecimal -> this.toBigInteger()
			else -> throw UnsupportedOperationException()
		}

fun Number.toBigDecimal(): BigDecimal =
		when(this) {
			is Byte, is Short, is Int, is Long -> BigDecimal(this.toLong(), context)
			is Float, is Double -> BigDecimal(this.toDouble(), context)
			is BigInteger -> BigDecimal(this, context)
			is BigDecimal -> this
			else -> throw UnsupportedOperationException()
		}

operator fun Number.plus(other: Number): Number =
		when (this) {
			is Byte ->
				when (other) {
					is Byte -> this + other
					is Short -> this + other
					is Int -> this + other
					is Long -> this + other
					is Float -> this + other
					is Double -> this + other
					is BigInteger -> this.toBigInt().add(other)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Short ->
				when (other) {
					is Byte -> this + other
					is Short -> this + other
					is Int -> this + other
					is Long -> this + other
					is Float -> this + other
					is Double -> this + other
					is BigInteger -> this.toBigInt().add(other)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Int ->
				when (other) {
					is Byte -> this + other
					is Short -> this + other
					is Int -> this + other
					is Long -> this + other
					is Float -> this + other
					is Double -> this + other
					is BigInteger -> this.toBigInt().add(other)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Long ->
				when (other) {
					is Byte -> this + other
					is Short -> this + other
					is Int -> this + other
					is Long -> this + other
					is Float -> this + other
					is Double -> this + other
					is BigInteger -> this.toBigInt().add(other)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Float ->
				when (other) {
					is Byte -> this + other
					is Short -> this + other
					is Int -> this + other
					is Long -> this + other
					is Float -> this + other
					is Double -> this + other
					is BigInteger -> this.toBigDecimal().add(other.toBigDecimal(), context)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Double ->
				when (other) {
					is Byte -> this + other
					is Short -> this + other
					is Int -> this + other
					is Long -> this + other
					is Float -> this + other
					is Double -> this + other
					is BigInteger -> this.toBigDecimal().add(other.toBigDecimal(), context)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is BigInteger ->
				when (other) {
					is Byte -> this.add(other.toBigInt())
					is Short -> this.add(other.toBigInt())
					is Int -> this.add(other.toBigInt())
					is Long -> this.add(other.toBigInt())
					is Float -> this.toBigDecimal().add(other.toBigDecimal(), context)
					is Double -> this.toBigDecimal().add(other.toBigDecimal(), context)
					is BigInteger -> this.add(other)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is BigDecimal ->
				this.add(other.toBigDecimal(), context)
			else ->
				throw UnsupportedOperationException()
		}

operator fun Number.minus(other: Number): Number =
		when (this) {
			is Byte ->
				when (other) {
					is Byte -> this - other
					is Short -> this - other
					is Int -> this - other
					is Long -> this - other
					is Float -> this - other
					is Double -> this - other
					is BigInteger -> this.toBigInt().subtract(other)
					is BigDecimal -> this.toBigDecimal().subtract(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Short ->
				when (other) {
					is Byte -> this - other
					is Short -> this - other
					is Int -> this - other
					is Long -> this - other
					is Float -> this - other
					is Double -> this - other
					is BigInteger -> this.toBigInt().subtract(other)
					is BigDecimal -> this.toBigDecimal().subtract(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Int ->
				when (other) {
					is Byte -> this - other
					is Short -> this - other
					is Int -> this - other
					is Long -> this - other
					is Float -> this - other
					is Double -> this - other
					is BigInteger -> this.toBigInt().subtract(other)
					is BigDecimal -> this.toBigDecimal().subtract(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Long ->
				when (other) {
					is Byte -> this - other
					is Short -> this - other
					is Int -> this - other
					is Long -> this - other
					is Float -> this - other
					is Double -> this - other
					is BigInteger -> this.toBigInt().subtract(other)
					is BigDecimal -> this.toBigDecimal().subtract(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Float ->
				when (other) {
					is Byte -> this - other
					is Short -> this - other
					is Int -> this - other
					is Long -> this - other
					is Float -> this - other
					is Double -> this - other
					is BigInteger -> this.toBigDecimal().subtract(other.toBigDecimal(), context)
					is BigDecimal -> this.toBigDecimal().subtract(other, context)
					else -> throw UnsupportedOperationException()
				}
			is Double ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> throw UnsupportedOperationException()
				}
			is BigInteger ->
				when (other) {
					is Byte -> this.add(other.toBigInt())
					is Short -> this.add(other.toBigInt())
					is Int -> this.add(other.toBigInt())
					is Long -> this.add(other.toBigInt())
					is Float -> this.toBigDecimal().add(other.toBigDecimal(), context)
					is Double -> this.toBigDecimal().add(other.toBigDecimal(), context)
					is BigInteger -> this.add(other)
					is BigDecimal -> this.toBigDecimal().add(other, context)
					else -> throw UnsupportedOperationException()
				}
			is BigDecimal ->
				this.add(other.toBigDecimal(), context)
			else ->
				throw UnsupportedOperationException()
		}

operator fun Number.times(other: Number): Number =
		when (this) {
			is Byte ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Short ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Int ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Long ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Float ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Double ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is BigInteger ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is BigDecimal ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			else ->
				TODO("")
		}

operator fun Number.div(other: Number): Number =
		when (this) {
			is Byte ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Short ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Int ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Long ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Float ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is Double ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is BigInteger ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			is BigDecimal ->
				when (other) {
					is Byte -> TODO("")
					is Short -> TODO("")
					is Int -> TODO("")
					is Long -> TODO("")
					is Float -> TODO("")
					is Double -> TODO("")
					is BigInteger -> TODO("")
					is BigDecimal -> TODO("")
					else -> TODO("")
				}
			else ->
				TODO("")
		}