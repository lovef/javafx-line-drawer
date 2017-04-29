package se.lovef.util

/*
 * Date: 2017-04-12
 * @author Love
 */
infix fun <T: Comparable<T>> T?.atLeast(min: T) =
        if (this != null && min < this) this else min

infix fun <T: Comparable<T>> T?.atLeastOrNull(min: T?) =
        min?.let { this.atLeast(it) } ?: this

infix fun <T: Comparable<T>> T?.atMost(max: T) =
        if (this != null && this < max) this else max

infix fun <T: Comparable<T>> T?.atMostOrNull(max: T?) =
        if(max != null) this.atMost(max) else this
