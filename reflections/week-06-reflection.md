# Week 6 Reflection

**Name:** Diego Godinez
**Date:** 6/25/26

---

## Commits This Week


**Link:https://github.com/dgodinez227/media-tracker-android/pull/6/changes/c8516517cfb642510a993efe48142a9b76ee6e1c**

---

## Code Review

**Reviewed:** *Mai*
**Link to my review:https://github.com/mmoua02/media-tracker-android/pull/6/changes/0c88101a213777f6a21cc86fc0f2d2ec0a2e804d#r3478998182**

### What I Looked At

I reviewed Mai's search implementation, mainly focusing on her SeachResultsViewModel. I looked at how the search() and loadNextPage() instead of putting everything into one function. I also noticed that network exceptions are currently caught and ignored in loadNextPage(). While I know that works during development, adding an error state or message later would let her know if a search request fails. I also looked at her overall commits and saw how she built the feature over time, including the Search screens, the API integration

### What I Noticed

I noticed a few things, them being that the search logic was split between search() and loadNextPage() instead of putting everything into one function. I also noticed that network exceptions are currently caught and ignored in loadNextPage(). While that works during development, adding an error state or message later would let the user know if a search request fails.

### Comments I Left

I commented that I liked how her search logic was split between search() and loadNextPage() in SearchResultsViewModel instead of handling everything in one function. I also suggested that it might be helpful to show an error state or message when a search request fails instead of silently ignoring exceptions. I say that because its small stuff that can strengthen code.


---

## One Thing I Understood More Deeply

One thing that clicked for me this week was understanding the different responsibilities between SearchScreen, SearchResultsScreen, and SearchResultsViewModel. At first I was trying to make SearchScreen handle more of the search functionality, but after working through the project I realized that SearchScreen is mainly for starting the search, SearchResultsScreen is responsible for displaying the results, and SearchResultsViewModel manages the search state and logic between them. That helped me understand why the search feature is split across multiple files instead of everything being handled in one place. Which is what we saw for previous weeks but again more practice to Kotlin.


---

## One Thing I'm Still Confused About

I'm still a little confused about some of the steps after the search API is connected. I understand the overall flow, but I'm not completely sure how everything comes together before the feature is fully finished. It feels like we're building each step from the back to the front every week.


---

## Anything Else *(optional)*

<!-- Did you help a pod mate work through something? Did you discover something cool or frustrating?
     Did something from a previous week finally click? This is a good place to put it. -->

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
