package se.lovef.log

/**
 * Date: 2016-05-29
 * @author Love
 */
object DefaultLogger: LoggerInterface {
    override fun info(tag: String, message: String) {
        System.out.println("I $tag: $message")
    }

    override fun verbose(tag: String, message: String) {
        System.out.println("V $tag: $message")
    }

    override fun error(tag: String, message: String) {
        System.err.println("E $tag: $message")
    }

    override fun warning(tag: String, message: String) {
        System.err.println("W $tag: $message")
    }
}