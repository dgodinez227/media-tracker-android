# Week 3 Reflection

**Name: Diego G**
**Date: 6/4/26**

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:https://github.com/dgodinez227/media-tracker-android/pull/3/commits**

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *Ryan*
**Link to my review:https://github.com/oppenrhymer/media-tracker-android/pull/3/changes/1e5a21b5e447c7f9c7044c01f9ef84e0f58195fd#r3360086526**

### What I Looked At

<!-- Walk through the code you reviewed. What was the PR trying to do? Which files or
     functions did you focus on? -->
I looked through his whole PR and he was quite far ahead and created many new files. He added full validation in isFormValid() to check
passwords and empty fields. His code is well structured and cleanly typed, so you can tell what's really going on in each file. 
He also created TokenRequest.kt where he made email, password, and refreshToken optional so the same class can handle both password login and token refresh later. 
Adding @Serializable now means Retrofit will work right away when he wires it up.

### What I Noticed

<!-- Be specific. Did you spot a potential bug? A pattern that could cause problems? Something
     done well that you want to call out? "I looked at the ViewModel and everything seemed fine"
     is not specific enough. Name the thing you noticed and explain why it matters. -->
I noticed that the beginning of class the RegisterScreen has a basic skeleton but we began building it and writing up the ViewModel which I dont have it exactly done yet.
But I noticed how to create the data classes for the API layer.

### Comments I Left

<!-- Briefly summarize the comments you left on the PR. If you left a positive comment,
     say what it was. If you left a suggestion, say what you suggested and why. --> I commented about the TokenRequest and the validation inside the ViewModel which I found very well structured and kept cleanly. 
---

## One Thing I Understood More Deeply

<!-- Be specific. Don't write "I learned about ViewModels." Write what specifically clicked —
     what was confusing before, what made it make sense, and how you'd explain it to someone else.
     There are no wrong answers here. --> One thing I undestood more deeply was finally understanding a bit more of how everything gets called. Such as the registerScreen calls the viewmodel, then the viewmodel calls the UserRepository which makes the actual API call, certain lines of code helped like the viewModelScope.launch. I will fix up my files to fully grasp and bring everything together for the next step.

---

## One Thing I'm Still Confused About

<!-- Be honest. This is the most useful part of the reflection for me — it tells me where to
     spend more time in class. You will not lose points for being confused. --> I didnt quite understand the reason to change the json libraries and add the other retrofit ones, I edited them out but yet I still am trying to piece to why having both might not work

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
