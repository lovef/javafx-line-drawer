package se.lovef.log


/**
 * Date: 2016-05-29
 * @author Love
 */
interface LoggerInterface {
    fun info(tag: String, message: String)
    fun verbose(tag: String, message: String)
    fun error(tag: String, message: String)
    fun warning(tag: String, message: String)
}