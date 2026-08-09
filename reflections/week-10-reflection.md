# Week 10 Reflection

**Name:** Diego Godinez
**Date:** 7/28/2026

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:** https://github.com/dgodinez227/media-tracker-android/pull/10/commits

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *Ryan*
**Link to my review:** https://github.com/oppenrhymer/media-tracker-android/pull/10/changes/BASE..7016e440d2eeeff6431b279149438e16d6f5c61d#r3643047277

### What I Looked At

I reviewed Ryans MediaDetailViewModel, specifically how he handled the library and favorites state. I focused on the functions that load media details, save media to the library or favorites, and check whether an item has already been saved. I also looked at how he used StateFlow to keep the UI updated.

### What I Noticed

One thing I noticed was how Ryan separated the library and favorites state into their own MutableStateFlow<Boolean> values instead of trying to manage everything with one variable. That made it much easier to understand how the UI knows whether an item is already in the library or favorites. I also noticed that checkLibrary() and checkFavorites() launch coroutines and then immediately return a Boolean. I thought it wouldnt have thought it was reflected because maybe the coroutine may still be running. 

### Comments I Left

Comments I left was also of what I noticed. I commented I liked the way the library and favorites state was set as its pretty clean, and I also thought and asked whether the boolean outside the coroutine could update the state.

---

## One Thing I Understood More Deeply

Well this week has been more than one thing more deeply as I used this week to catch up and sharpen my app completely and up to date now. I spent time polishing the Media Detail screen so everything worked together. I handled cases like loading media correctly, dealing with missing media responses, and displaying the library and favorites state on the detail page. I also kept adding features like ratings, reviews, and metadata info, I realized the screen became much easier to manage by moving repeated logic into helper functions and composables instead of keeping everything inside one large MediaDetailScreen(). Seeing the backend changes connect directly to the UI helped me understand how the repository, ViewModel, and Compose screen all work together rather than as separate pieces.

---

## One Thing I'm Still Confused About

I'm still getting used to knowing the best way to organize larger compose screens as we add onto them. As I kept adding more features to the Media Detail screen. It works now, but I'd like to get better at structuring larger files so they're easier to read and maintain. Like whether to use compose and helper functions vs being inside the main screen and file.

---

## Anything Else *(optional)*

nope 

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
