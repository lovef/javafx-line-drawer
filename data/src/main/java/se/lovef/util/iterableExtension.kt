package se.lovef.util

import java.util.*

/*
 * Date: 2017-04-12
 * @author Love
 */

/** Reduce each adjacent pair of elements in a list into new elements in a new list */
inline fun <T, R> Iterable<T>.reduceAdjacentElements(transform: (T, T) -> R): List<R> {
    val iterator = iterator()
    if (!iterator.hasNext()) {
        return emptyList()
    }
    val result = ArrayList<R>(if(this is Collection) this.size - 1 else 10)
    var a = iterator.next()
    while (iterator.hasNext()) {
        val b = iterator.next()
        result.add(transform.invoke(a, b))
        a = b
    }
    return result
}
