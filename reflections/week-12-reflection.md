# Week 12 Reflection — Bonus Feature Sprint (Week 2 of 2, Final)

*Second and last week of bonus feature work. Week 13 has no build time — this is the last chance to get your feature demo-ready before Week 14. This template replaces the standard weekly reflection, same as last week.*

**Name:** Diego Godinez
**Date:** 8/9/2026
**My assigned bonus feature:** *Priorities* 

---

## Commits This Week

**Link:**

---

## Code Review

**Reviewed:** *Mai*
**Link to my review:** https://github.com/mmoua02/media-tracker-android/pull/12

### What I Looked At
I reviewed Mai's quote feature, mainly looking at AddQuoteBottomSheet.kt and QuoteRepository.kt but her overall bonus feature files. 

### What I Noticed

One thing I noticed was the 500 character limit on the quote text field, which prevents too much text from being entered before it is submitted. I also noticed the use of runCatching in the repository. I personally haven't used runCatching in my code and have mostly used try/catch, so it was interesting seeing a cleaner way of handling multiple repository requests.

### Comments I Left
---
I left two comments, one about the 500 character limit and another about the use of runCatching in the repository.

## Bonus Feature — Final Status

<!-- Be concrete and honest. This is your last chance to flag something before demos.
     What does your feature actually do, end to end, right now? What's polished vs. rough?
     Is there anything you know is broken or half-done that you want on my radar before Week 14? -->

**What works end-to-end, right now:**

The priorities screen works now, you can go from library and add a want to item and choose high medium low and put hours and notes, then it shows in priorities and you can drag them around and it saves

**Tests written for this feature:**

I added PriorityLimitTest as a unit test for the five-item maximum. One test checks that adding another priority is allowed when there are four items, and the other checks that it returns false once there are already five. Both tests passed successfully.

**Known gaps or rough edges going into demos:**

---
The feature works, but some of the UI formatting is not an exact replica of the wireframe. The main functionality and general layout are there, including the priority cards, filters, estimated hours, notes, and drag-and-drop ordering. One issue I ran into was that the Library and the other provided wireframes did not show a clear way to actually navigate to the Priorities screen or add an item as a priority. I decided to add a Priorities button to the Library screen and use the media already in the user's Library, where "Want To" items can be assigned a priority. I also made the estimated hours and notes user inputs when setting a priority since that information isn't already part of the media itself. There also is a slight delay after reordering while the updated order is being saved.

## One Thing I Understood More Deeply

I understand the connection between the UI, ViewModel, repository, and API much better after working through this feature. The drag-and-drop especially helped because I learned that changing the order visually on the screen is different from actually updating the orderIndex and saving that change through the API. It helped me see more clearly how a change made by the user moves through each layer of the app.

---

## One Thing I'm Still Confused About

---
I'm still not completely familiar with drag-and-drop in Jetpack Compose since it wasn't something I had worked with before this feature. I had to look outside of what we had done in class to understand how the drag gestures and item positions could work together. I was able to get it working and persist the new order, but I would still like to understand the Compose approach to drag-and-drop better without having to rely as much on outside documentation or examples.
## Anything Else *(optional)*

<!-- Anything about the bonus feature sprint as a whole — the two-week format, being assigned a
     feature rather than choosing it, whatever's on your mind — is fair game here. -->

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Honest final-status report — what works end-to-end, what's rough, what's tested — plus a specific, genuine "Understood More Deeply" that reflects on the sprint as a whole, not just this week. | Present but vague, or only reports on this week rather than the feature's overall state. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** same as every other week — I check the link before grading.
