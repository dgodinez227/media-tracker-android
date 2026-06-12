# Week {{N}} Reflection

**Name: Diego**
**Date: 6/11/2**

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:https://github.com/dgodinez227/media-tracker-android/pull/4/changes/a2839f72eddd13b4e64e15cd862371d3b2685573**

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *Mai*
**Link to my review:https://github.com/mmoua02/media-tracker-android/pull/4/changes/857ae1ce554dea8180ba6da0e35e1381d2bedfd5#r3400292203**

### What I Looked At

<!-- Walk through the code you reviewed. What was the PR trying to do? Which files or
     functions did you focus on? --> I reviewed Mai and her changes to the RegisterScreen and her RegisterViewModel. I mostly focused on what she added for her registration logic and how she managed the state inside of the ViewModel but also the Ui. 

### What I Noticed

<!-- Be specific. Did you spot a potential bug? A pattern that could cause problems? Something
     done well that you want to call out? "I looked at the ViewModel and everything seemed fine"
     is not specific enough. Name the thing you noticed and explain why it matters. --> I noticed was her use of RegisterUiState with idle, loading, success, and error states. Which later found out was for the registration to be clear and have clear states that can later be reacted by the UI. One example I can think about is the app can show a loading message while in the register is in process.

### Comments I Left

<!-- Briefly summarize the comments you left on the PR. If you left a positive comment,
     say what it was. If you left a suggestion, say what you suggested and why. --> The comment I left was I liked the use of RegisterUiState because its a much structured way to handle the registration events in the register screens. Which I mentioned was different then my approach

---

## One Thing I Understood More Deeply

<!-- Be specific. Don't write "I learned about ViewModels." Write what specifically clicked —
     what was confusing before, what made it make sense, and how you'd explain it to someone else.
     There are no wrong answers here. --> While we worked on the registerScreen last week, this week helped to better understand them. I add all my variabels in the AuthViewModel and it created many errors. But I then structured the registerViewModel and understood how it manages UI state and also survives changes in the configuration.

---

## One Thing I'm Still Confused About

<!-- Be honest. This is the most useful part of the reflection for me — it tells me where to
     spend more time in class. You will not lose points for being confused. --> One thing Im still confused about might not necessarily needed but I decied to add a snackbar to show the signup screen wasnt implemented yet. But im still not completely sure when a snackbar is the best choice compared to using UI state. But it was something I decided to take on and I implemented using what I found. 

---

## Anything Else *(optional)*

<!-- Did you help a pod mate work through something? Did you discover something cool or frustrating?
     Did something from a previous week finally click? This is a good place to put it. --> Just again on the snackbar I added instead of the UI state. Im still wondering whether the app will function and remain optimal if I dont exactly use a RegisterUiState approach. Or if its something crucial for the app as we add more to it. Right now I learned the snackbar worked for user feedback but im interested to see as we continue.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
