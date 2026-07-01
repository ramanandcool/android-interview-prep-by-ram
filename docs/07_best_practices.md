# Topic 7: Best Practices

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [Writing Good Composables](#writing-good-composables)
2. [Preview Annotation](#preview-annotation)
3. [User Interactions](#user-interactions)
4. [Navigation](#navigation)

---

## Writing Good Composables

### Q1: What are the best practices for writing Composable functions?

**Answer:**
Writing good composables means keeping them small, focused, reusable, and testable. Follow these practices for clean, maintainable code.

### Practice 1: Keep Composables Small and Focused

```kotlin
// WRONG - Too much logic in one composable
@Composable
fun CompleteScreen() {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    Column {
        TextField(value = userName, onValueChange = { userName = it })
        TextField(value = email, onValueChange = { email = it })
        TextField(value = phone, onValueChange = { phone = it })
        Button(onClick = { /* submit */ }) { Text("Submit") }
    }
    // 500 more lines...
}

// RIGHT - Break into smaller composables
@Composable
fun UserForm() {
    var user by remember { mutableStateOf(User()) }
    
    Column {
        UserNameField(value = user.name, onValueChange = { user = user.copy(name = it) })
        EmailField(value = user.email, onValueChange = { user = user.copy(email = it) })
        PhoneField(value = user.phone, onValueChange = { user = user.copy(phone = it) })
        SubmitButton(onClick = { submitUser(user) })
    }
}

@Composable
fun UserNameField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Name") }
    )
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") }
    )
}
```

### Practice 2: Use Meaningful Names

```kotlin
// WRONG - Unclear names
@Composable
fun UI1() { Text("Hello") }

@Composable
fun S() { /* Screen */ }

@Composable
fun D(x: String) { /* Detail */ }

// RIGHT - Clear names
@Composable
fun HomeScreen() { Text("Welcome") }

@Composable
fun UserProfileCard() { /* Profile UI */ }

@Composable
fun ProductDetailScreen(productId: String) { /* Details */ }
```

### Practice 3: Make Composables Reusable

```kotlin
// WRONG - Hard-coded values, not reusable
@Composable
fun GreetingCard() {
    Card {
        Column {
            Text("Hello, John!")
            Text("john@example.com")
        }
    }
}

// Can only display John!

// RIGHT - Accept parameters, reusable
@Composable
fun GreetingCard(name: String, email: String) {
    Card {
        Column {
            Text("Hello, $name!")
            Text(email)
        }
    }
}

// Can display any user!
// Usage:
GreetingCard("John", "john@example.com")
GreetingCard("Jane", "jane@example.com")
GreetingCard("Bob", "bob@example.com")
```

### Practice 4: Use State Correctly - Hoist When Needed

```kotlin
// WRONG - State in every child
@Composable
fun UserList() {
    Column {
        UserCard1()  // Has own state
        UserCard2()  // Has own state
        UserCard3()  // Has own state
    }
}

@Composable
fun UserCard1() {
    var isLiked by remember { mutableStateOf(false) }
    // Can't sync between cards
}

// RIGHT - Hoist state to parent
@Composable
fun UserList() {
    var likedUsers by remember { mutableStateOf(setOf<Int>()) }
    
    Column {
        UserCard(
            id = 1,
            isLiked = 1 in likedUsers,
            onLikeChange = { liked ->
                likedUsers = if (liked) likedUsers + 1 else likedUsers - 1
            }
        )
        UserCard(
            id = 2,
            isLiked = 2 in likedUsers,
            onLikeChange = { liked ->
                likedUsers = if (liked) likedUsers + 2 else likedUsers - 2
            }
        )
    }
}

@Composable
fun UserCard(
    id: Int,
    isLiked: Boolean,
    onLikeChange: (Boolean) -> Unit
) {
    // Stateless - only receives data and callbacks
    Card {
        Button(onClick = { onLikeChange(!isLiked) }) {
            Text(if (isLiked) "❤️" else "🤍")
        }
    }
}
```

### Practice 5: Separate UI from Logic

```kotlin
// WRONG - Logic mixed with UI
@Composable
fun DataDisplay() {
    var data by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        val api = Api()
        val response = api.fetchData()
        val processed = response.split(",").map { it.trim() }
        val filtered = processed.filter { it.length > 3 }
        data = filtered.joinToString("\n")
    }
    
    Text(data ?: "Loading")
}

// RIGHT - Extract logic to functions
@Composable
fun DataDisplay(viewModel: DataViewModel) {
    val data by viewModel.data.collectAsState()
    
    Text(data ?: "Loading")
}

class DataViewModel : ViewModel() {
    val data = flow {
        val api = Api()
        val response = api.fetchData()
        val processed = processData(response)
        emit(processed)
    }
    
    private fun processData(response: String): String {
        return response
            .split(",")
            .map { it.trim() }
            .filter { it.length > 3 }
            .joinToString("\n")
    }
}
```

### Example Reference:

📄 **Code Examples:** See `Topic7_BestPracticesExamples.kt`

---

## Preview Annotation

### Q2: What is the Preview annotation and how do you use it?

**Answer:**
`@Preview` allows you to preview composables in Android Studio without running the app. It's useful for rapid UI development and testing.

### Basic Preview:

```kotlin
@Composable
fun Greetings(name: String) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name)
        Button(onClick = {}) {
            Text(text = "Click me")
        }
    }
}

// Simple preview
@Preview
@Composable
fun GreetingsPreview() {
    Greetings("Hello Kotlin")
}
```

### Preview with Custom Settings:

```kotlin
@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = "spec:width=1080px,height=1920px",
    heightDp = 100
)
@Composable
fun GreetingsPreviewLight() {
    DemoProjectTheme {
        Greetings("Hello Kotlin")
    }
}
```

### Common Preview Attributes:

| Attribute | Purpose | Example |
|-----------|---------|---------|
| **name** | Display name | `name = "Light Mode"` |
| **showBackground** | Show background | `showBackground = true` |
| **backgroundColor** | BG color | `backgroundColor = 0xFFFFFFFF` |
| **device** | Device spec | `device = "spec:..."` |
| **widthDp** | Width in dp | `widthDp = 320` |
| **heightDp** | Height in dp | `heightDp = 640` |
| **fontScale** | Font scaling | `fontScale = 1.5f` |
| **showSystemUi** | Show system bars | `showSystemUi = true` |
| **uiMode** | Light/Dark theme | `uiMode = UI_MODE_NIGHT_YES` |

### Multiple Previews:

```kotlin
@Preview(name = "Light")
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GreetingsMultiplePreview() {
    DemoProjectTheme {
        Greetings("Hello Kotlin")
    }
}
```

### Device Previews:

```kotlin
@Preview(device = "spec:width=320dp,height=640dp", name = "Small Phone")
@Preview(device = "spec:width=480dp,height=854dp", name = "Large Phone")
@Preview(device = "spec:width=600dp,height=1024dp", name = "Tablet")
@Composable
fun ResponsivePreview() {
    DemoProjectTheme {
        Greetings("Hello Kotlin")
    }
}
```

### Benefits:

✅ **Fast feedback** - See changes instantly  
✅ **No rebuilding** - Don't wait for app compile  
✅ **Multiple views** - Test different devices/sizes  
✅ **Theme testing** - Test light and dark modes  
✅ **Development** - Quick iteration  

### Example Reference:

📄 **Code Examples:** See `Topic7_BestPracticesExamples.kt`

---

## User Interactions

### Q3: How do you handle user interactions (clicks, text input)?

**Answer:**
Use `clickable()` modifier for clicks and `TextField` for text input. Always use callbacks to communicate state changes.

### Click Handling:

```kotlin
@Composable
fun ClickExample() {
    var clicked by remember { mutableStateOf(false) }
    
    // Method 1: Using clickable modifier
    Text(
        "Click me",
        modifier = Modifier.clickable {
            clicked = !clicked
        }
    )
    
    // Method 2: Using Button
    Button(onClick = { clicked = !clicked }) {
        Text("Button Click")
    }
    
    // Method 3: Using IconButton
    IconButton(onClick = { clicked = !clicked }) {
        Icon(Icons.Default.Settings, "Settings")
    }
    
    if (clicked) {
        Text("Clicked!")
    }
}
```

### Text Input:

```kotlin
@Composable
fun TextInputExample() {
    var text by remember { mutableStateOf("") }
    
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter name") },
            singleLine = true
        )
        
        Text("You entered: $text")
    }
}
```

### Form Handling:

```kotlin
@Composable
fun FormExample() {
    var formData by remember { 
        mutableStateOf(FormData()) 
    }
    
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = formData.name,
            onValueChange = { 
                formData = formData.copy(name = it) 
            },
            label = { Text("Name") }
        )
        
        TextField(
            value = formData.email,
            onValueChange = { 
                formData = formData.copy(email = it) 
            },
            label = { Text("Email") }
        )
        
        Button(onClick = { submitForm(formData) }) {
            Text("Submit")
        }
    }
}

data class FormData(
    val name: String = "",
    val email: String = ""
)

fun submitForm(data: FormData) {
    println("Name: ${data.name}, Email: ${data.email}")
}
```

### Long Press & Gestures:

```kotlin
@Composable
fun GestureExample() {
    var longPressed by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(if (longPressed) Color.Green else Color.Blue)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { println("Tapped") },
                    onLongPress = { longPressed = !longPressed }
                )
            }
    ) {
        Text("Long press me!")
    }
}
```

### Example Reference:

📄 **Code Examples:** See `Topic7_BestPracticesExamples.kt`

---

## Navigation

### Q4: How do you navigate between screens in Compose?

**Answer:**
Use the Jetpack Navigation library with Compose support. Define routes, create a NavHost, and use NavController for navigation.

### Setup Dependencies:

```gradle
dependencies {
    implementation "androidx.navigation:navigation-compose:2.7.0"
}
```

### Basic Navigation:

```kotlin
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToDetails = { id ->
                    navController.navigate("details/$id")
                }
            )
        }
        
        composable(
            "details/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailsScreen(id = id)
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    Column {
        Text("Home Screen")
        Button(onClick = { onNavigateToDetails("123") }) {
            Text("Go to Details")
        }
    }
}

@Composable
fun DetailsScreen(id: String) {
    Column {
        Text("Details Screen for ID: $id")
        Button(onClick = { }) {
            Text("Back")
        }
    }
}
```

### Navigation with Callbacks:

```kotlin
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("details") {
            DetailsScreen(navController = navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Button(onClick = { 
        navController.navigate("details") 
    }) {
        Text("Go to Details")
    }
}

@Composable
fun DetailsScreen(navController: NavHostController) {
    Button(onClick = { 
        navController.popBackStack() 
    }) {
        Text("Back")
    }
}
```

### Navigation with Arguments:

```kotlin
navController.navigate("details/123")

// Receiving:
composable(
    "details/{itemId}",
    arguments = listOf(
        navArgument("itemId") { type = NavType.StringType }
    )
) { backStackEntry ->
    val itemId = backStackEntry.arguments?.getString("itemId")
    DetailsScreen(itemId = itemId ?: "")
}
```

### Example Reference:

📄 **Code Examples:** See `Topic7_BestPracticesExamples.kt`

---

## Code Quality Checklist

### Before submitting code, check:

✅ **Composables are small** - Less than 50 lines each  
✅ **Names are clear** - Anyone understands the purpose  
✅ **State is hoisted** - Not duplicated in children  
✅ **No side effects** - Use LaunchedEffect for effects  
✅ **Modifiers are ordered** - Layout → Decoration → Interaction  
✅ **Reusable** - Can be used in multiple places  
✅ **Tested** - Has preview or unit tests  
✅ **Documented** - Comments for complex logic  
✅ **No hard-coded values** - Use parameters or constants  
✅ **Performance** - No unnecessary recompositions  

---

## Interview Tips

### Common Questions:

**Q: How do you keep composables small?**
- Extract into separate functions
- One responsibility per composable
- Delegate complex logic to ViewModels
- Use composition over inheritance

**Q: What makes a good preview?**
- Shows realistic data
- Tests different states
- Multiple device sizes
- Light and dark themes

**Q: How do you handle complex navigation?**
- Use NavGraph for complex flows
- DeepLinks for direct access
- Back stack management
- State preservation

**Q: Best practices for user interactions?**
- Use callbacks for state changes
- Hoist state to parent
- Handle all edge cases
- Provide visual feedback

---

## Summary Table

| Practice | Benefit | Example |
|----------|---------|---------|
| **Small functions** | Reusable, testable | Extract common UI |
| **Clear names** | Readable code | UserCard not UI1 |
| **Reusable** | DRY principle | Accept parameters |
| **State hoisting** | Shared state | Pass to children |
| **Previews** | Fast feedback | @Preview annotation |
| **Callbacks** | Clean interactions | onValueChange lambda |
| **Navigation** | Multi-screen apps | NavHost + NavController |

---

## Next Steps

1. ✅ Write small, focused composables
2. ✅ Use Preview for rapid development
3. ✅ Handle user interactions properly
4. ✅ Implement navigation
5. ✅ Review interview tips (Topic 8)

**Next Topic:** [Topic 8: Interview Tips & Resources](./08_interview_tips.md)

