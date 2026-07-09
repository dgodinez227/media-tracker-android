# Week 7 Reflection

**Name:** Diego Godinez
**Date:** 7/2/2026

---

## Commits This Week


**Link:https://github.com/dgodinez227/media-tracker-android/pull/7/commits**

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *Ryan*
**Link to my review:** https://github.com/oppenrhymer/media-tracker-android/pull/8#discussion_r3517265851

### What I Looked At

This week I had to look at Ryan's commits and his work done in MediaDetailScreen. He began from top to bottom with the top bar and adding the want to and save buttons, as well as a star rating function, and both hardcoded data and fake review cards. I focused on his getStars() function and how it fits into his code. 

### What I Noticed

I noticed his code is well organized and it does match to the wireframe quite well. Another thing I noticed was the getMediaTypeCount() function switches between PAGES for books and LENGTH for movies and shows. I havent implemented something like that on mine but I though it was a clean way to handle the different media. 

### Comments I Left

The comment I left this week was about his getStars() function. I mentioned that I saw it rounds to the nearest whole number and thought it was be interesting how half star could be supported using maybe a repeat() or an if statement

---

## One Thing I Understood More Deeply

I realized this week that having a hardcoded beginning fallback for data makes development so much easier. I spent a lot of time on login and API setup, but once that was working I still couldn't see anything on the detail screen because the API wasn't connected yet. Using fakeMedia as a placeholder meant I could build and style the whole UI without waiting for the API to work. It also gave me a clear path forward which is with UI works now with fake data, and later I can swap in real data by replacing fakeMedia with media from the ViewModel. Which would be in the following week or weeks.

---

## One Thing I'm Still Confused About

One thing im still confused about wasn't touched on a lot but its about the Asyncimage. I believe I have it working well with a hardcoded URL, but I wonder if the coverURL is empty or theres an issue while loading, I deleted the media type icon placeholder so I wonder whether its needed or not. I also used AsyncImage from Coil to load the cover art, but I don't fully understand how it caches images or handles errors yet.

---

## Anything Else *(optional)*

I just like to comment I liked the pace of this weeks class, maybe in the following weeks we'll continue and again pick up the pace for it. 

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
