package se.lovef.assert

import junit.framework.AssertionFailedError
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertTrue
import kotlin.reflect.KClass

/*
 * Date: 2017-03-31
 * @author Love
 */

infix fun <T : Throwable> (() -> Any?).throws(throwableType: KClass<T>): T {
    var thrown: T? = null
    try {
        invoke()
    } catch (t: T) {
        thrown = t
    }
    if (thrown == null) {
        throw NotThrownError("${throwableType.java.simpleName} was never thrown")
    }
    try {
        thrown typeIs throwableType
        return thrown
    } catch (error: Error) {
        throw NotThrownError("Expected ${throwableType.java.simpleName} to be thrown " +
                "but caught ${thrown.javaClass.simpleName}")
    }
}

infix fun <T : Any, R : Any> T?.typeIs(type: KClass<R>) = apply {
    assertThat("$this type is ${type.java.simpleName}", this, IsInstanceOf(type.java))
}

infix fun <T : Any, R : Any> T?.instanceOf(type: KClass<R>) = this typeIs type

class NotThrownError(message: String) : AssertionFailedError(message)

infix fun <T : Any?> T.isEqualTo(other: T): T {
    assertTrue("\n" +
            "Expected: $other\n" +
            "Got:      $this", this == other)
    return other
}

infix fun <T : Any> T?.isNotEqualTo(other: T?): T? {
    assertThat("\n" +
            "Expected not: $other\n" +
            "Got:          $this", this != other)
    return other
}

infix fun CharSequence?.doesContain(other: CharSequence) = isNotNull().apply {
    assertThat("'$this' does contain '$other'", this.contains(other))
}

infix fun CharSequence?.doesNotContain(other: CharSequence) = apply {
    assertThat("'$this' does not contain '$other'", this == null || !this.contains(other))
}

fun <T : Any> T?.isNull() = apply { this isEqualTo null }

fun <T : Any> T?.isNotNull() = apply { this isNotEqualTo null }!!

fun Boolean.isTrue() = apply { this isEqualTo true }

fun Boolean.isFalse() = apply { this isEqualTo false }

infix fun <T : Any> T?.referenceIsEqualTo(other: T?) = apply {
    assertTrue("\n" +
            "Expected:          $this\n" +
            "Same reference as: $other", this === other)
}

infix fun <T : Any> T?.referenceIsNotEqualTo(other: T?): T? {
    assertTrue("\n" +
            "Expected:              $this\n" +
            "Not same reference as: $other", this !== other)
    return other
}

infix fun <T : Comparable<T>> T.isLessThan(other: T): T {
    assertTrue("$this < $other", this < other)
    return other
}

infix fun <T : Comparable<T>> T.isLessOrEqualTo(other: T): T {
    assertTrue("$this <= $other", this <= other)
    return other
}

infix fun <T : Comparable<T>> T.isGreaterThan(other: T): T {
    assertTrue("$this > $other", this > other)
    return other
}

infix fun <T : Comparable<T>> T.isGreaterOrEqualTo(other: T): T {
    assertTrue("$this >= $other", this >= other)
    return other
}

infix fun <T : Number, R : Number> T.isCloseTo(other: R) = Difference(this, other)

class Difference<out T : Number, out R : Number>(val a: T, val b: R) {
    infix fun tolerance(t: Number) {
        val difference = Math.abs(a.toDouble() - b.toDouble())
        assertTrue("To large difference between $a and $b: $difference > $t", difference <= t.toDouble())
    }
}

infix fun String.proof(proof: () -> Unit) = proof()
