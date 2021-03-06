package com.example.fole;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class) public class MainActivityTest {

  @Rule public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class);

  private String ellipsisPlaceholder = "...";

  @Test public void testMaxLinesMode() {
    int times = 2;

    for (int i = 0; i < times; i++) {
      assertTextHasOnlyTwoLines();
      assertEllipsisPlaceholderIsVisible();
      assertToggleViewHasShowMore();

      clickToggleView();

      assertFullTextIsVisible();
      assertEllipsisPlaceholderIsNotVisible();
      assertToggleViewHasShowLess();

      clickToggleView();
    }
  }

  @Test public void testMaxCharsMode() {
    openMenu();
    clickMaxCharsView();

    int times = 2;

    for (int i = 0; i < times; i++) {
      assertTextHasOnlySixtyTwoChars();
      assertEllipsisPlaceholderIsVisible();
      assertToggleViewIsNotVisible();

      clickTextView();

      assertFullTextWithAppendedToggleTextIsVisible();
      assertEllipsisPlaceholderIsNotVisible();
      assertToggleViewIsNotVisible();

      clickTextView();
    }
  }

  private void clickToggleView() {
    onToggleView().perform(click());
  }

  private void clickTextView() {
    onTextView().perform(click());
  }

  private void clickMaxCharsView() {
    onMaxCharsView().perform(click());
  }

  private void assertTextHasOnlyTwoLines() {
    TextView textView = activityTestRule.getActivity().findViewById(R.id.text_view);
    assertThat(textView.getLineCount()).isEqualTo(2);
  }

  private void assertTextHasOnlySixtyTwoChars() {
    String excerpt = MainActivity.TEXT.substring(0, 62);
    onTextView().check(matches(withText(excerpt + ellipsisPlaceholder + " show more")));
  }

  private void assertFullTextIsVisible() {
    onTextView().check(matches(withText(MainActivity.TEXT)));
  }

  private void assertFullTextWithAppendedToggleTextIsVisible() {
    onTextView().check(matches(withText(MainActivity.TEXT + " show less")));
  }

  private void assertEllipsisPlaceholderIsVisible() {
    onTextView().check(matches(withText(containsString(ellipsisPlaceholder))));
  }

  private void assertEllipsisPlaceholderIsNotVisible() {
    onTextView().check(matches(not(withText(containsString(ellipsisPlaceholder)))));
  }

  private void assertToggleViewHasShowMore() {
    onToggleView().check(matches(withText("Show More")));
  }

  private void assertToggleViewHasShowLess() {
    onToggleView().check(matches(withText("Show Less")));
  }

  private void assertToggleViewIsNotVisible() {
    onToggleView().check(matches(not(isDisplayed())));
  }

  private void openMenu() {
    openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
  }

  private ViewInteraction onToggleView() {
    return onView(withId(R.id.toggle_view));
  }

  private ViewInteraction onTextView() {
    return onView(withId(R.id.text_view));
  }

  private ViewInteraction onMaxCharsView() {
    return onView(withText(R.string.menu_max_chars));
  }
}
