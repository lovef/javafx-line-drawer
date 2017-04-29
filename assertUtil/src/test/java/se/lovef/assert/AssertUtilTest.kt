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

    @Test fun `throws`() {
        { throw Exception() } throws Exception::class
        { throw Exception() } throws Throwable::class

        { { } throws Error::class } throws NotThrownError::class

        { { 1 } throws Error::class } throws NotThrownError::class

        { { throw Exception() } throws Error::class } throws NotThrownError::class
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

    @Test fun `is null`() {
        null.isNull();
        { OBJECT_A.isNull() } throws Error::class
        (null.isNull()) referenceIsEqualTo null
    }

    @Test fun `is not null`() {
        OBJECT_A.isNotNull();
        { null.isNotNull() } throws Error::class
        (OBJECT_A.isNotNull()) referenceIsEqualTo OBJECT_A
        "123".isNotNull().length isEqualTo 3
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
        } throws Error::class
        {
            "Test description" proof {
                1 isEqualTo 1
                1 isEqualTo 2
            }
        } throws Error::class
    }

    @Test fun `proof in a expression body`() = "A proof can be done in a expression body" proof {
        1 isEqualTo 1
    }
}
