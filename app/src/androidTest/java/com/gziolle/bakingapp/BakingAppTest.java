/*
 * BakingApp
 * Created by Guilherme Ziolle on 02/07/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */

package com.gziolle.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Hosts all UI tests for BakingApp.
 * The tests were written using the Espresso framework.
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    /**
     * Test to check the recipe list's scrolling.
     */
    @Test
    public void scrollRecipeRecyclerViewTest() {
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(4));
    }

    /**
     * Test to check if the recipe's details were loaded properly.
     */
    @Test
    public void clickRecipeAndCheckStepsAndIngredientsTest() {
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tvRecipeIngredientsTitle))
                .check(matches(
                        withText(mMainActivityActivityTestRule
                                .getActivity().getString(R.string.recipe_ingredients))));
        onView(withId(R.id.tvRecipeStepsTitle))
                .check(matches(
                        withText(mMainActivityActivityTestRule
                                .getActivity().getString(R.string.recipe_steps))));

    }

    /**
     * Test to check the step list's scrolling.
     */
    @Test
    public void scrollStepRecyclerViewTest() {
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(4));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(5));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(6));
    }

    /**
     * Test to check if the step's details were loaded properly.
     */
    @Test
    public void checkStepDetailsTest() {
        accessStepDetails(0, 2);
        onView(withId(R.id.tvStepProgress)).check(matches(withText("3 / 7")));
    }

    /**
     * Test to check if the "previous" button was hidden properly.
     */
    @Test
    public void checkFirstStepPreviousButtonTest() {
        accessStepDetails(0, 0);
        onView(withId(R.id.previousButton)).check(matches(not(isDisplayed())));
    }

    /**
     * Test to check if the "next" button was hidden properly.
     */
    @Test
    public void checkLastStepNextButtonTest() {
        accessStepDetails(0, 6);
        onView(withId(R.id.nextButton)).check(matches(not(isDisplayed())));
    }

    /**
     * Test to check if it is possible to go to the next step.
     */
    @Test
    public void clickNextStepTest() {
        accessStepDetails(0, 0);
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.tvStepProgress)).check(matches(withText("2 / 7")));
    }

    /**
     * Test to check if it is possible to go to the previous step.
     */
    @Test
    public void clickPreviousStepTest() {
        accessStepDetails(0, 4);
        onView(withId(R.id.previousButton)).perform(click());
        onView(withId(R.id.tvStepProgress)).check(matches(withText("4 / 7")));
    }

    /**
     * Helper method to access a particular step's details
     *
     * @param recipePosition The recipe's position at the list.
     * @param stepPosition   The step's position at the list
     */
    public void accessStepDetails(int recipePosition, int stepPosition) {
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(stepPosition, click()));
    }
}
