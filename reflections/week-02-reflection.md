# Week 2 Reflection

**Name: Diego Godinez**
**Date: 5/28/26**

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link: https://github.com/dgodinez227/media-tracker-android/commit/a98e4393aaa827884d45b001267e745313a678e3**

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed: Ryan ** *(pod mate's name)*
**Link to my review:https://github.com/oppenrhymer/media-tracker-android/pull/2#pullrequestreview-4395721600**

### What I Looked At

<!-- Walk through the code you reviewed. What was the PR trying to do? Which files or
     functions did you focus on? -->
First we were given prompts that we had to debug, some files we had to look at were inside the navigation file in BottomNavBar.kt or the Routes one. The process was run the app, try to recreate the bug, use logcat to check for any error message, then search the code and fix it.

### What I Noticed

<!-- Be specific. Did you spot a potential bug? A pattern that could cause problems? Something
     done well that you want to call out? "I looked at the ViewModel and everything seemed fine"
     is not specific enough. Name the thing you noticed and explain why it matters. -->
Well one specific bug I found was little buffer time when clicking off of the library tab, I looked into the LibraryViewModel and from java I recognized the sleep() method, and immediately suspected that was the root cause and later removed and tested and it was.  

### Comments I Left

Ryan solved the filters being set to default when the phone flipped, but it differed from the approach after. So I was curious whether it changes anything else.

---

## One Thing I Understood More Deeply

This week its still more about using Android Studio and specifically understanding the find usage setting that helps see the path and where its being used and how. 

---

## One Thing I'm Still Confused About

 I still just get a little mixed up on the pushing, and I'm never completely sure if my PR is in the right place. But I have found the terminal easier and much more straight forward than using Android Studio itself

---

## Anything Else *(optional)*

<!-- Did you help a pod mate work through something? Did you discover something cool or frustrating?
     Did something from a previous week finally click? This is a good place to put it. --> Nothing extra, just excited to see how the app will develop week after week

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
