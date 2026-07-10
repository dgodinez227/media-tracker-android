# Week 8 Reflection

**Name:** Diego Godinez
**Date:** 7/9/26

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:** https://github.com/dgodinez227/media-tracker-android/pull/9/commits

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *Mai*
**Link to my review:** https://github.com/mmoua02/media-tracker-android/pull/9/changes/BASE..9f154d93859f75645151c1edda07893e8fe3badf#r3556116906

### What I Looked At

I looked at Mai's MediaDetailViewModel and MediaDetailScreen. Her PR was trying to replace the hardcoded media details with a real GET call and also check if the media was already in the library. I focused on her loadMedia() function and how she handled the UI state. She had a similar approach to mine using Loading, Success and Error states. 

### What I Noticed

She used the same sealed class pattern I did, Loading, Success, Error, which keeps the UI state handling clean. What stood out was she also called getLibraryItem to check if the media is already added. That's something I haven't done yet, so it was interesting to see how it fits into the detail screen. The try/catch with a fallback to fake data is also a good way to handle exceptions without crashing the app, which is something I need to think about more.

### Comments I Left

I just left one comment on her loadMedia function. I pointed out that the sealed class pattern keeps the UI clean and that the library check is a nice addition. I also mentioned the try/catch with fake data fallback is a good way to handle errors.

---

## One Thing I Understood More Deeply

I finally understand how the API call chain works for GET requests. We've been using POST for example the search APi, so the pattern was familiar, but I wasn't sure how it applied to getting data. But here I set up the MediaApiService with the GET endpoint for media details, the DefaultMediaRepository to handle the response, and the ViewModel to manage the state.  It follows the same pattern as POST, just with a different HTTP method and no request body. I also made sure the AuthInterceptor was created and also adding the token to the requests since I believe its required for authenticated GET calls.

---

## One Thing I'm Still Confused About

I'm unsure about is how to handle the cover image when coverUrl is null. Right now the app crashes, so my automatic thinking is I need to add a fallback, but I'm not sure if I should use a placeholder drawable or a check. Also I didnt quite get to it this week but for the stretch goal of reviews, whether to add the GET call in the existing MediaApiService or create a separate endpoint for reviews since it's a different part of the API.

---

## Anything Else *(optional)*

Nothing this week, just interested in what the following week with the Lightning exploration and working in our pods more.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
