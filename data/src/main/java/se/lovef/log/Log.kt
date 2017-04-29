package se.lovef.log

import se.lovef.log.StackTraceHelper.getCallerTag
import se.lovef.log.StackTraceHelper.getStackTrace
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern

/**
 * Date: 2016-05-29
 * @author Love
 */
var logger: LoggerInterface = DefaultLogger

private const val UNKNOWN_CALLER_STACK_LENGTH = 5;
private const val UNKNOWN_CALLER_STACK_LENGTH_STRING = "UNKNOWN CALLER, STACK SIZE < 5";
private const val UNKNOWN_CALLER_NAME_LENGTH_STRING = "UNKNOWN CLASS, CLASS NAME LENGTH <= 0";

/* 03-21 13:22:53.028 I/(TrafficLabApiHelper.java:59)( 4558): ...*/
private val logPattern = Pattern.compile("((\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\.(\\d{3}) " +
        "([[A-Z]])/(.*?)\\(\\s*?(\\d+)\\):)" +
        "(.*)")

fun log(tag: String, message: String) {
    logger.info(tag, message)
}

fun log(message: String, skipStacks: Int) {
    log(getCallerTag(skipStacks + 1), message)
}

fun log(message: String) {
    log(message, 1)
}

fun logVerbose(tag: String, message: String) {
    logger.verbose(tag, message)
}

fun logVerbose(message: String, skipStacks: Int) {
    logVerbose(getCallerTag(skipStacks + 1), message)
}

fun logVerbose(message: String) {
    logVerbose(message, 1)
}

fun logError(tag: String, message: String) {
    logger.error(tag, message)
}

fun logError(message: String) {
    logError(getCallerTag(1), message)
}

fun logError(message: String, exception: Exception, skipStacks: Int) {
    logError(getCallerTag(skipStacks + 1), message + ": " + getStackTrace(exception))
}

fun logError(message: String, exception: Exception) {
    logError(message, exception, 1)
}

fun logWarning(tag: String, message: String) {
    logger.warning(tag, message)
}

fun logWarning(message: String) {
    logWarning(getCallerTag(1), message)
}

fun logWarning(message: String, exception: Exception) {
    logWarning(getCallerTag(1), message + ": " + getStackTrace(exception))
}

object StackTraceHelper {
    fun getCallerTag(skipStacks: Int): String {
        var callerName = UNKNOWN_CALLER_STACK_LENGTH_STRING
        val stack = Thread.currentThread().stackTrace
        val start = 1 + stack.indexOfFirst { it.className == javaClass.name }

        if (stack.size >= UNKNOWN_CALLER_STACK_LENGTH) {
            val callerElement = stack[start + skipStacks]
            val callerPropertiesArray = callerElement.className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (callerPropertiesArray.size > 0) {
                // Create tag that Intellij will linkify
                callerName = StringBuilder("(")
                        .append(callerElement.fileName)
                        .append(":")
                        .append(callerElement.lineNumber)
                        .append(")").toString()
            } else {
                callerName = UNKNOWN_CALLER_NAME_LENGTH_STRING
            }
        }
        return callerName
    }

    /**
     * Get the stack trace to print
     * @param aThrowable
     * *
     * @return
     */
    fun getStackTrace(aThrowable: Throwable): String {
        val result = StringWriter()
        val printWriter = PrintWriter(result)
        aThrowable.printStackTrace(printWriter)
        val stackTrace = result.toString()
        val stackTraceLength = stackTrace.length
        return stackTrace.substring(0, stackTraceLength)
    }
}
