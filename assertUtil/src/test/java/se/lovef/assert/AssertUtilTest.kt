package se.lovef.assert

import junit.framework.TestCase.fail
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import java.util.*

/**
 * Date: 2017-03-31
 * @author Love
 */
class AssertUtilTest {

    @Test fun `throws throw exception if nothing is thrown`() {
        try {
            { } throws Throwable::class
            fail("No exception was thrown")
        } catch (thrown: NotThrownError) {
            MatcherAssert.assertThat(thrown, IsInstanceOf(NotThrownError::class.java))
        }
    }

    class SpecialException(val specialValue: String = "special value"): Exception()

    @Test fun `throws`() {
        val exception = SpecialException();
        { throw exception } throws SpecialException::class referenceIsEqualTo exception
        { throw exception }.throws(SpecialException::class).specialValue isEqualTo exception.specialValue
        { throw exception } throws Throwable::class

        { { } throws Error::class } throws NotThrownError::class

        { { 1 } throws Error::class } throws NotThrownError::class

        { { throw exception } throws Error::class } throws NotThrownError::class
        { { throw Error() } throws Exception::class } throws NotThrownError::class
    }

    @Test fun `type is`() {
        this typeIs AssertUtilTest::class
        this instanceOf AssertUtilTest::class

        this typeIs Any::class
        this instanceOf Any::class

        {this typeIs Int::class } throws Error::class
        {this instanceOf Int::class } throws Error::class
    }

    val OBJECT_A = Date(0)
    val OBJECT_B = Date(0)

    @Test fun `reference is equal to`() {
        OBJECT_A referenceIsEqualTo OBJECT_A
        { OBJECT_A referenceIsEqualTo OBJECT_B } throws Error::class
        OBJECT_A referenceIsEqualTo OBJECT_A referenceIsEqualTo OBJECT_A
        { OBJECT_A referenceIsEqualTo OBJECT_B referenceIsEqualTo OBJECT_B } throws Error::class
        { OBJECT_A referenceIsEqualTo OBJECT_A referenceIsEqualTo OBJECT_B } throws Error::class
    }

    @Test fun `reference is not equal to`() {
        OBJECT_A referenceIsNotEqualTo OBJECT_B;
        { OBJECT_A referenceIsNotEqualTo OBJECT_A } throws Error::class
        (OBJECT_A referenceIsNotEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
    }

    @Test fun `is equal to`() {
        1 isEqualTo 1
        { 1 isEqualTo 2 } throws Error::class
        (OBJECT_A isEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isEqualTo 1 isEqualTo 1
        { 1 isEqualTo 2 isEqualTo 2 } throws Error::class
        { 1 isEqualTo 1 isEqualTo 2 } throws Error::class
        "123".isEqualTo("123").length isEqualTo 3
        null isEqualTo null
        { null isEqualTo 1 } throws Error::class
        { 1 isEqualTo null } throws Error::class
    }

    @Test fun `is not equal to`() {
        1 isNotEqualTo 2
        { 1 isNotEqualTo 1 } throws Error::class
        (Any() isNotEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isNotEqualTo 2 isNotEqualTo 1
        { 1 isNotEqualTo 1 isNotEqualTo 2 } throws Error::class
        { 1 isNotEqualTo 2 isNotEqualTo 2 } throws Error::class
        null isNotEqualTo 1
        1 isNotEqualTo null
        { null isNotEqualTo null } throws Error::class
    }

    @Test fun `does contain`() {
        "my awesome string" as CharSequence doesContain "awesome" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesContain "shit" } throws Error::class
        { "my awesome string" doesContain "we.+me" } throws Error::class

        "my awesome string" doesContain "my" doesContain "awesome" doesContain "string"

        { null doesContain "shit" } throws Error::class
    }

    @Test fun `does not contain`() {
        "my awesome string" as CharSequence doesNotContain "shit" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotContain "awesome" } throws Error::class

        null doesNotContain "awesome"
    }

    @Test fun `does match`() {
        "my awesome string" as CharSequence doesMatch "we.+me" as CharSequence referenceIsEqualTo "my awesome string"
        "my awesome string" as CharSequence doesMatch "we.+me".toRegex() referenceIsEqualTo "my awesome string"
        { "my awesome string" doesMatch "shit" } throws Error::class

        "my awesome string" doesMatch "^my" doesMatch "we.+me" doesMatch "string$"
        "my awesome string" doesMatch "^my".toRegex() doesMatch "we.+me".toRegex() doesMatch "string$".toRegex();

        { null doesMatch "shit" } throws Error::class
    }

    @Test fun `does not match`() {
        "my awesome string" as CharSequence doesNotMatch "shit" as CharSequence referenceIsEqualTo "my awesome string"
        "my awesome string" as CharSequence doesNotMatch "shit".toRegex() referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotMatch "we.+me" } throws Error::class
        { "my awesome string" doesNotMatch "we.+me".toRegex() } throws Error::class

        null doesNotMatch "shit"
        null doesNotMatch "shit".toRegex()
    }

    @Test fun `does start with`() {
        "my awesome string" as CharSequence doesStartWith "my" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesStartWith "awesome" } throws Error::class

        { null doesStartWith "shit" } throws Error::class
    }

    @Test fun `does not start with`() {
        "my awesome string" as CharSequence doesNotStartWith "awesome" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotStartWith "my" } throws Error::class

        null doesNotStartWith "shit"
    }

    @Test fun `does end with`() {
        "my awesome string" as CharSequence doesEndWith "string" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesEndWith "awesome" } throws Error::class

        { null doesEndWith "shit" } throws Error::class
    }

    @Test fun `does not end with`() {
        "my awesome string" as CharSequence doesNotEndWith "awesome" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotEndWith "string" } throws Error::class

        null doesNotEndWith "shit"
    }

    @Test fun `is null`() {
        null.isNull()
        null.isNull() referenceIsEqualTo null
        { OBJECT_A.isNull() } throws Error::class
    }

    @Test fun `is not null`() {
        OBJECT_A.isNotNull()
        OBJECT_A.isNotNull() referenceIsEqualTo OBJECT_A
        { null.isNotNull() } throws Error::class
        "123".isNotNull().length isEqualTo 3
    }

    @Test fun `is true`() {
        true.isTrue()
        true.isTrue() referenceIsEqualTo true
        { false.isTrue() } throws Error::class
    }

    @Test fun `is false`() {
        false.isFalse()
        false.isFalse() referenceIsEqualTo false
        { true.isFalse() } throws Error::class
    }

    @Test fun `is less than`() {
        1 isLessThan 2
        { 1 isLessThan 1 } throws Error::class
        (1 isLessThan 2) isEqualTo 2
        1 isLessThan 2 isLessThan 3
        { 1 isLessThan 1 isLessThan 3 } throws Error::class
        { 1 isLessThan 2 isLessThan 2 } throws Error::class
    }

    @Test fun `is less or equal to`() {
        1 isLessOrEqualTo 1
        1 isLessOrEqualTo 2
        { 1 isLessOrEqualTo 0 } throws Error::class
        (OBJECT_A isLessOrEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isLessOrEqualTo 1 isLessOrEqualTo 1
        { 1 isLessOrEqualTo 0 isLessOrEqualTo 0 } throws Error::class
        { 1 isLessOrEqualTo 1 isLessOrEqualTo 0 } throws Error::class
    }

    @Test fun `is greater than`() {
        2 isGreaterThan 1
        { 1 isGreaterThan 1 } throws Error::class
        (2 isGreaterThan 1) isEqualTo 1
        3 isGreaterThan 2 isGreaterThan 1
        { 3 isGreaterThan 3 isGreaterThan 1 } throws Error::class
        { 3 isGreaterThan 2 isGreaterThan 2 } throws Error::class
    }

    @Test fun `is greater or equal to`() {
        1 isGreaterOrEqualTo 1
        2 isGreaterOrEqualTo 1
        { 1 isGreaterOrEqualTo 2 } throws Error::class
        (OBJECT_A isGreaterOrEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isGreaterOrEqualTo 1 isGreaterOrEqualTo 1
        { 1 isGreaterOrEqualTo 2 isGreaterOrEqualTo 2 } throws Error::class
        { 1 isGreaterOrEqualTo 1 isGreaterOrEqualTo 2 } throws Error::class
    }

    @Test fun `is close to`() {
        1 isCloseTo 1 tolerance 0
        { 1 isCloseTo 2 tolerance 0 } throws Error::class
        1 isCloseTo 2 tolerance 1
        { 1 isCloseTo 2 tolerance 0 } throws Error::class
        1 isCloseTo 0 tolerance 1
        { 1 isCloseTo 0 tolerance 0 } throws Error::class

        1 isCloseTo 1.1 tolerance 0.2
        { 1 isCloseTo 1.1 tolerance 0.05 } throws Error::class
        1 isCloseTo 0.9 tolerance 0.2
        { 1 isCloseTo 0.9 tolerance 0.05 } throws Error::class
    }

    @Test fun `proof`() {
        "We can describe our tests" proof {
            1 isEqualTo 1
        }
        {
            "Test description" proof {
                1 isEqualTo 2
            }
        }.throws(Error::class).message isEqualTo "Test description"
        {
            "Test description" proof {
                1 isEqualTo 1
                1 isEqualTo 2
            }
        }.throws(Error::class).message isEqualTo "Test description"
    }

    @Test fun `proof in a expression body`() = "A proof can be done in a expression body" proof {
        1 isEqualTo 1
    }
}
