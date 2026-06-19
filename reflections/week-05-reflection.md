# Week {{N}} Reflection

**Name: Diego G**
**Date: 6/18/2026**

---

## Commits This Week



**Link:https://github.com/dgodinez227/media-tracker-android/pull/5/changes/a87f63496e9c83e5bf3396d2d81ec621a6a82c33**

---

## Code Review



**Reviewed:** *Ryan*
**Link to my review:https://github.com/oppenrhymer/media-tracker-android/pull/5/changes/f4ab582fe371d043eda16695ddb10d5aab313e43#r3440154400**

### What I Looked At

I reviewed Ryan and his code. Specifically looking at his authentication flow, which was in his UserRepository.kt. Where I spotted the login() function, where he created the TokenRequest and how that request was being sent over to /tokens. 

### What I Noticed

One thing I noticed and made this whole authentication process easier to understand was how its separated into its own functions inside. And inside it creates the request using the users username and password as well as API constants before sending it out. 

### Comments I Left

The comment I left was about the things I spotted, in his UserRepository. I mentioned how I liked the TokenRequest and how it send it to the endpoint. Making the login flow quite easy to follow. 

---

## One Thing I Understood More Deeply

Well we touched upon similar things last week with the viewModels. But this week reinforced my understanding a little more since this time we expanded into authentication and while I didnt get to the token storing, I understand that even if endpoints are different, the app sends information to the API, the API processes the request and returns a response, and then the app uses that response to determine what happens next or what it does with it.

---

## One Thing I'm Still Confused About

One thing im still confused about is the token storage while I didnt exactly get to finish it, I understood a bit but, I am not completely sure why those values need to be stored in a separate DefaultSessionRepository instead of being handled directly in the ViewModel or UserRepository. I understand that it has something to do with keeping the user logged in between app launches but when is each component is directly handling the data. 

---

## Anything Else *(optional)*

null

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
