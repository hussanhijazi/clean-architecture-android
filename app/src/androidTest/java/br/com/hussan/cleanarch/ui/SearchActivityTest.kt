package br.com.hussan.cleanarch.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.ui.search.SearchActivity
import br.com.hussan.cleanarch.ui.search.SearchAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<SearchActivity> = ActivityTestRule(SearchActivity::class.java)

    @Before
    fun launchActivity() {
        ActivityScenario.launch(SearchActivity::class.java)
    }

    @Test
    fun checkDuplicatesSearches() {
        onView(withId(R.id.rvSearches)).check(matches((isDisplayed())))
        onView(withId(R.id.rvSearches)).check(matches(checkDuplicatedText()))

    }

    private fun checkDuplicatedText(
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("No duplicated texts")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val adapter = recyclerView.adapter as SearchAdapter
                return adapter.getSearches() == adapter.getSearches().distinct()
            }
        }
    }
}

