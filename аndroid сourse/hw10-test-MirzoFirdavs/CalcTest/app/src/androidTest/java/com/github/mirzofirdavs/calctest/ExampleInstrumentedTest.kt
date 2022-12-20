package com.github.mirzofirdavs.calctest

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.uiautomator.UiDevice

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private var device: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.github.mirzofirdavs.calctest", appContext.packageName)
    }

    @Test
    fun test0() {
        Espresso.onView(withId(R.id.buttonZero)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("0")))
    }

    @Test
    fun test1() {
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test2() {
        Espresso.onView(withId(R.id.buttonTwo)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("2")))
    }

    @Test
    fun test3() {
        Espresso.onView(withId(R.id.buttonThree)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("3")))
    }

    @Test
    fun test4() {
        Espresso.onView(withId(R.id.buttonFour)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("4")))
    }

    @Test
    fun test5() {
        Espresso.onView(withId(R.id.buttonFive)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("5")))
    }

    @Test
    fun test6() {
        Espresso.onView(withId(R.id.buttonSix)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("6")))
    }

    @Test
    fun test7() {
        Espresso.onView(withId(R.id.buttonSeven)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("7")))
    }

    @Test
    fun test8() {
        Espresso.onView(withId(R.id.buttonEight)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("8")))
    }

    @Test
    fun test9() {
        Espresso.onView(withId(R.id.buttonNine)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("9")))
    }

    @Test
    fun test10() {
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonZero)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("10")))
    }

    @Test
    fun testUnary() {
        Espresso.onView(withId(R.id.buttonSubtract)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonZero)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("-10")))
    }

    @Test
    fun testAdd() {
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonAdd)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonTwo)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("3")))
    }


    @Test
    fun testMultiply() {
        Espresso.onView(withId(R.id.buttonThree)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonMultiply)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonNine)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("27")))
    }

    @Test
    fun testMultiplyWithUnary() {
        Espresso.onView(withId(R.id.buttonSubtract)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonThree)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonMultiply)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonNine)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("-27")))
    }

    @Test
    fun testSubtract() {
        Espresso.onView(withId(R.id.buttonFive)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonSubtract)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonSix)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("-1")))
    }

    @Test
    fun testDivide() {
        Espresso.onView(withId(R.id.buttonEight)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonDivide)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonFour)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("2")))
    }

    @Test
    fun testLand() {
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonZero)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonAdd)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonFive)).perform(ViewActions.click())
        device.setOrientationLeft()
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("15")))
    }

    @Test
    fun testTwiceLand() {
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonZero)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonAdd)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonFive)).perform(ViewActions.click())
        device.setOrientationLeft()
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        device.setOrientationLeft()
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("15")))
        device.setOrientationNatural()
    }

    @Test
    fun tooManyRotates() {
        Espresso.onView(withId(R.id.buttonNine)).perform(ViewActions.click())
        device.setOrientationLeft()
        Espresso.onView(withId(R.id.buttonAdd)).perform(ViewActions.click())
        device.setOrientationNatural()
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        device.setOrientationRight()
        Espresso.onView(withId(R.id.buttonSubtract)).perform(ViewActions.click())
        device.setOrientationNatural()
        Espresso.onView(withId(R.id.buttonFive)).perform(ViewActions.click())
        device.setOrientationLeft()
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("5")))
    }

    @Test
    fun testDivisionByZero() {
        Espresso.onView(withId(R.id.buttonNine)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonDivide)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonZero)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonEquals)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("∞")))
    }

    @Test
    fun dotTest() {
        Espresso.onView(withId(R.id.buttonSeven)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonDot)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.buttonSeven)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textResult))
            .check(ViewAssertions.matches(ViewMatchers.withText("7.7")))
    }
}