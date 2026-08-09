# Extra Credit Reflection — Design Alignment

*See `extra-credit-design-alignment.md` for submission requirements and the full assignment description.*

**Name:** Diego Godinez
**Date:** 7/9/26

---

## The Audit

*Before touching any code, compare your running app to the wireframes screen by screen. List what you found — be specific about which screen, which component, and what was different. "The colors were off" is not specific. "The active chip on the Search screen was using amber instead of primary container (#E0E0FF)" is specific.*

*List at least five concrete differences you found:*

1. First thing I noticed was that my primary and secondary theme colors werent the same as the wireframes. So everything that had primary color looked off 
2. Another thing I spotted before was the there wasnt a specific shape for the corner. For example the Sign in and Sign up had much smaller corners than the wireframe.
3. I also spotted that the Library screen's Want To, In Progress, and Finished status badges were using the default Material styling instead of the specified purple #7C3AED, blue (#2563EB, and green #059669 colors from the design system.
4. Also I saw a few things across multiple screen, for example the Library, Search Results, and Activity Feed cards did not match either. Their corner radius and elevation were different from the consistent 12dp rounded cards shown in the design system. While it was a slight change its noticeable
5. The Login and Register OutlinedTextFields were using the standard styling instead of the what we needed for the wireframe.

---

## What You Changed

*Walk through the changes you made. For each area of the design system, describe what the code looked like before and what you changed it to. Reference specific files and Composables.*

### Color System

So I began with Color.kt, and up until now I rarely went in it to modify, and I saw it was using colors that did not match wireframe, and some components were still relying on default Material colors. I updated Color.kt by replacing the primary and secondary colors with the values from the design system and added the Want To, In Progress, and Finished colors along with their container colors. I then updated Theme.kt so those colors were used through MaterialTheme across the application.

### Typography

Here is where I first ran into the hard coded values. There was several composables that used hardcoded fontWeight values, even when a MaterialTheme.typography style was already being used. I updated Type.kt to better match the required typography and replaced several hardcoded fontWeight values with the appropriate typography styles where possible

### Buttons

I specifically worked on the standard Button and Outlined Button specifically in Login, Register, Connections, and Profile. Where they each used RoundedCornerShape(), where I fixed them to be 20.dp each. Where I also added the primary colored border to outline those buttons

### Text Fields

Similar to the Color, the TextFields I spotted were using the standard default appearances. So I went in the LoginScreen and RegisterScreen and updated the fields to use RoundedCornerShape(8.dp) and also the themed border. There was also inside ths Search screens of SearchScreen and SearchResultsScreen where I had to use a large rounded shape. 

### Other Components

Here is where I had many changes. I updated the Card components in ActivityFeedScreen, LibraryScreen, and SearchComponents to use 12dp rounded corners and 2dp elevation. Before, the cards looked flatter with smaller rounded corners and very little depth, making them blend into the background more. After changing the corner radius and elevation, they stood out more as separate cards. I also updated the FilterChips in LibraryScreen.kt and SearchComponents.kt. Again previously, they were previous Material shape and colors, so I changed them to use 8dp rounded corners along with the theme colors for both the selected and unselected states. Finally, I  updated BottomNavBar so the navigation items used the colors from MaterialTheme instead of the default colors. At last I updated the status badges in LibraryScreen to use the Want To, In Progress, and Finished colors instead of the default Material colors.

---

## What Was Hard

*Describe the most technically challenging part of this work. Don't write "it was confusing." Explain specifically what confused you, what you tried, and what helped you figure it out. If something in the Jetpack Compose theming system surprised you, describe it.* The most difficult part for me was figuring out what could actually be customized with the version of Material3 our project was using. When I got to the SegmentedButton, I found examples online that used parameters for changing the selected colors, but those parameters did not exist in our project. At first I thought I was doing something wrong, but after checking the available parameters and trying different approaches, I realized it was because our project was using an older version of Material3. Instead of trying to force unsupported code, I kept the default SegmentedButton styling and focused on updating the other components that the assignment required.

---

## What You Understand Now

*What do you understand about Jetpack Compose theming — `MaterialTheme`, `colorScheme`, `typography`, component defaults — that you didn't fully grasp before this assignment? Be specific enough that you could explain it to a pod mate who hasn't done this yet.* I understand that MaterialTheme is where most of the styling comes from. But by updating Color.kt, Theme.kt, and Type.kt, many components automatically use the new colors and typography without changing every little Composable. I also learned that some components can be customized directly while others depend on the version of the Material3 library being used, I understood how important it is to know the dependencies of the project.

---

## Self-Assessment

*Look at the rubric (`extra-credit-design-alignment-rubric.md`) and estimate your own score for each section. Be honest — this does not affect your grade, but it shows me whether you read the rubric carefully.*

| Section | Possible | My Estimate |
|:---|:---:|:-----------:|
| Color System | 13 |     13      |
| Typography | 5 |      5      |
| Component Styling | 15 |     12      |
| Navigation & Cards | 5 |      5      |
| Reflection | 12 |     11      |
| **Total** | **50** |     46      |

*One thing I think I did well:* I think I did a good job going through the project one requirement at a time, like going through one file at a time using the search component instead of trying to change everything at once. I also believe I did good to constantly check the wireframe to my app to make sure it looks similar.

*One thing I know I left incomplete or could have done better:* Well I think overall I did a good job but one thing that still I felt incomplete was. I think the Search screen could still be adjusted to more closely match the wireframe, and I wasn't able to customize the SegmentedButton colors because the version of Material3 used by the project did not support the newer customization options I found. Im not sure if its just on my end or not. 
