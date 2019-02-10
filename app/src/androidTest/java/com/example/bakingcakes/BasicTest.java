package com.example.bakingcakes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingcakes.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BasicTest {
    public static final String CAKE_NAME = "Cheesecake";
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void browniesSecondInRecipeList() {
        // When the app loads, is Brownies the second cake card in the MaimActivity?
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions
                        .scrollToPosition(1));

        onView(withText("Brownies")).check(matches(isDisplayed()));
    }



    @Test
    public void clickCheesecakeCard() {
        //1. taping Cheesecake Card in the MainActivity
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(3, click()));

        //does it start to load ingredients list?
        onView(withId(R.id.servings)).check(matches(withText(startsWith("Yield"))));

        //2. taping on listItem "Recipe Introduction" on DetailActivity
        onView(withId(R.id.steps_item_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));

        //opens StepFragment and shows Step1:
        onView(withId(R.id.description)).check(matches(withText(startsWith("Recipe Introduction"))));

        //3. taping on "Next Step" button
        onView(withId(R.id.forward)).perform(click());

        //opens the next step which is Step 1?
        onView(withId(R.id.description)).check(matches(withText(startsWith("Step 1"))));

        //4. taping on "Next Step" button again
        onView(withId(R.id.forward)).perform(click());

        // moves to Step 2?
        onView(withId(R.id.description)).check(matches(withText(startsWith("Step 2"))));

        //5.taping on "Previous Step" button
        onView(withId(R.id.back)).perform(click());

        //returns me back to Step 1?
        onView(withId(R.id.description)).check(matches(withText(startsWith("Step 1"))));
    }
}
