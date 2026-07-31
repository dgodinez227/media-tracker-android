# Week 11 Reflection — Bonus Feature Sprint (Week 1 of 2)

*This week's reflection is different from the standard template. We're not doing Profile this week — instead, this is the first of two weeks building your assigned bonus feature (Write Review, Quotes, or Priorities). See `reflection-instructions.md` for naming/submission rules, which are unchanged; only the content below differs.*

**Name:** Diego Godinez
**Date:** 7/30/2026
**My assigned bonus feature:**  *Priorities*

---

## Commits This Week



**Link:** https://github.com/dgodinez227/media-tracker-android/pull/11/commits

---

## Code Review

<!-- Code review continues as normal — same pod rotation, regardless of which bonus feature you or your pod mate are building. -->

**Reviewed:** *Mai*
**Link to my review:**  https://github.com/mmoua02/media-tracker-android/pull/11#discussion_r3687952035 and https://github.com/mmoua02/media-tracker-android/pull/11#discussion_r3687957790

### What I Looked At
Today I looked at Mai's implementation, where she has a new Quote model, networking and repo classes, the reusable QuoteCard composable which I commented on, the AddQuoteDialog, and finally into her MediaDetailScreen and MediaDetailViewModels.
### What I Noticed
I noticed that she was using a similar structure to what we've been building in the Media Tracker app. I especially liked the reusable QuoteCard composable because it keeps the UI separate from the screen logic, making the code cleaner and easier to maintain.
### Comments I Left
---
I left two comments today, first comment I highlighted how the quote dialog is only displayed after the media has successfully loaded. My second comment focused on the reusable QuoteCard component, mentioning that separating it into its own composable keeps the code modular and makes it easier to reuse and overall a good block of code.
## Bonus Feature Progress

<!-- This is the most important section this week. Be concrete: which endpoint(s) did you wire?
     What's actually showing on screen with real data? What's still stubbed or fake?
     "I worked on my bonus feature" is not an answer. "I got POST /quotes working from Media Detail
     and quotes show up in a list on my profile, but I haven't wired edit or delete yet" is. -->

**What's working:** I finished most of the backend work for my bonus feature by creating the API calls, repository functions, request models, and the new files needed for the priorities feature. I also started working on the UI by creating the new PrioritiesScreen and PriorityItemCard. Most of the layout is there, but I'm still finishing the rest of the UI before wiring everything together with the real data, and using preview to just view how the UI looks.

**What's still stubbed, fake, or not started:** The UI is still unfinished. Since it is pretty similar to the existing LibraryScreen, I was able to reuse a lot of that code and modify it to work with priorities instead. The main difference is that instead of using library statuses, the priorities use values 1, 2, 3, and 4, so I had to adjust parts of the logic to work with the new model. I also haven't started the drag-and-drop reordering yet.

**What I'm blocked on, if anything:** Nothing big as of now. Given the API docs were pretty straightforward to follow, and the UI was similar enough to the Library screen that it made getting started easier. The biggest thing I'm expecting to spend more time on is implementing the drag-and-drop functionality since that's something we haven't worked with yet. Maybe UI as well held me back a bit trying to match it to the wireframe and reusing code but reorganizing it.

---

## One Thing I Understood More Deeply

---
One thing I understood more deeply how much faster and easier it is to create a new feature given some similar code. Having the main pieces already written is a huge help, but then it helped me notice I also have to pay attention to what goes where and how to implement it to the new files for the feature.

## One Thing I'm Still Confused About

Maybe not confused yet or still. I'm still a little confused about the drag-and-drop implementation in Compose. Since I haven't built anything like it before, I wasn't completely sure how to structure it or how it should update the priority order once an item is moved. Im thinking whether it's using index or do the items not technically move.

---

## Anything Else *(optional)*

---
Nothing this week

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Concrete progress report (what's wired, what's not) plus specific, honest "Understood More Deeply" and "Still Confused" sections. | Present but vague — "I worked on my feature" with no specifics on what's actually working. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match.
