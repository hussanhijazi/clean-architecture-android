package br.com.hussan.cleanarch.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.hussan.cleanarch.R
import br.com.hussan.cleanarch.extensions.checkTextSize
import br.com.hussan.cleanarch.ui.main.FactsActivity
import br.com.hussan.cleanarch.views.TagLayout
import com.google.android.material.chip.Chip
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FactsActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<FactsActivity> = ActivityTestRule(FactsActivity::class.java)

    companion object {
        const val UNCATEGORIZED = "Uncategorized"
    }

    @Before
    fun launchActivity() {
        ActivityScenario.launch(FactsActivity::class.java)
    }


    @Test
    fun checkTextSizeAndUncategorizedRecyclerView() {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvFacts)

        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.edtSearch))
            .perform(typeText("car"), pressImeActionButton())

        onView(withId(R.id.rvFacts)).check(matches((isDisplayed())))

        for (position in 0 until (recyclerView.adapter?.itemCount ?: 0)) {
            onView(withId(R.id.rvFacts)).perform(scrollToPosition<RecyclerView.ViewHolder>(position))
            onView(withId(R.id.rvFacts)).check(matches(checkTextSize(position, R.id.txtFact)))
            onView(withId(R.id.rvFacts)).check(matches(checkUncategorized(position, R.id.lytFactCategory)))
        }

    }

    private fun checkUncategorized(
        position: Int,
        targetViewId: Int
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("checkUncategorized $position")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val adapter = recyclerView.adapter
                adapter?.let {
                    recyclerView.findViewHolderForAdapterPosition(position)?.run {
                        val targetView = itemView.findViewById<TagLayout>(targetViewId)
                        val item = targetView.getItem(0)
                        if (targetView.size == 1)
                            return when (item) {
                                UNCATEGORIZED -> {
                                    targetView.findViewById<Chip>(R.id.lytFact).text == UNCATEGORIZED
                                }
                                else -> {
                                    targetView.findViewById<Chip>(R.id.lytFact).text != UNCATEGORIZED
                                }
                            }
                        return false
                    }
                }
                return false
            }
        }
    }

    private fun checkTextSize(
        position: Int,
        targetViewId: Int
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("The text size correct")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val adapter = recyclerView.adapter
                var checkTextSize = false
                adapter?.let {
                    recyclerView.findViewHolderForAdapterPosition(position)?.run {
                        val targetView = itemView.findViewById<TextView>(targetViewId)
                        checkTextSize = targetView.checkTextSize()
                    }
                    return checkTextSize
                }
                return checkTextSize
            }
        }
    }

}
