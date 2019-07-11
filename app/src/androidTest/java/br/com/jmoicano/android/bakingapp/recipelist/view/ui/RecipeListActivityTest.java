package br.com.jmoicano.android.bakingapp.recipelist.view.ui;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.jmoicano.android.bakingapp.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RecipeListActivityTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> rule = new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void setUp() {
        rule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void test_RecipesShowed() {
        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));
    }

    @Test
    public void test_enabledRecipe() {
        onView(withId(R.id.rv_recipes)).check(matches(isEnabled()));
    }
    
}