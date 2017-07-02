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
 * BakingApp
 * Created by Guilherme Ziolle on 02/07/2017.
 * gziolle@gmail.com
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void scrollRecipeRecyclerViewTest() {
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.scrollToPosition(4));
    }

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

    @Test
    public void checkStepDetailsTest() {
        accessStepDetails(0, 2);
        onView(withId(R.id.tvStepProgress)).check(matches(withText("3 / 7")));
    }

    @Test
    public void checkFirstStepPreviousButtonTest() {
        accessStepDetails(0, 0);
        onView(withId(R.id.previousButton)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkLastStepNextButtonTest() {
        accessStepDetails(0, 6);
        onView(withId(R.id.nextButton)).check(matches(not(isDisplayed())));
    }

    @Test
    public void clickNextStepTest() {
        accessStepDetails(0, 0);
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.tvStepProgress)).check(matches(withText("2 / 7")));
    }

    @Test
    public void clickPreviousStepTest() {
        accessStepDetails(0, 4);
        onView(withId(R.id.previousButton)).perform(click());
        onView(withId(R.id.tvStepProgress)).check(matches(withText("4 / 7")));
    }

    public void accessStepDetails(int recipePosition, int stepPosition) {
        onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(stepPosition, click()));
    }
}
