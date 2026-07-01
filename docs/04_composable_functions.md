# Topic 4: Composable Functions - Advanced

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [Side Effects in Composables](#side-effects-in-composables)
2. [LaunchedEffect](#launchedeffect)
3. [SideEffect & DisposableEffect](#sideeffect--disposableeffect)
4. [Experimental APIs](#experimental-apis)

---

## Side Effects in Composables

### Q1: Can a Composable function have side effects? How should we handle them?

**Answer:**
Composable functions should be pure (no side effects). Side effects must be handled separately using special effect composables like `LaunchedEffect` or `SideEffect`.

### Why No Side Effects in Composables?

The Problem with Side Effects in Composables:

```kotlin
// WRONG - Side effects in composable body
@Composable
fun FetchUserData() {
    var user by remember { mutableStateOf<User?>(null) }
    
    // This runs on EVERY recomposition!
    val api = Api()
    api.fetchUser { result ->
        user = result
    }
    
    Text(user?.name ?: "Loading...")
}

// Problems:
// ❌ Network request on every recomposition
// ❌ Multiple requests triggered
// ❌ Memory leaks (callbacks not cleaned up)
// ❌ Unpredictable behavior
// ❌ Performance issues
```

### Why Recomposition Is Frequent:

```
Triggers for Recomposition:
1. State changes
2. Parameter changes
3. Parent recomposes
4. Configuration changes
5. Navigation changes

Result: Composable function can be called 
        hundreds of times per second!

Side effects would run hundreds of times 
per second too!
```

### The Rules:

✅ **DO:**
- Use LaunchedEffect for async operations
- Use SideEffect for state updates
- Use DisposableEffect for cleanup
- Keep composables pure

❌ **DON'T:**
- Call APIs directly in composable
- Launch coroutines in composable body
- Access files/databases in composable
- Update external state in composable

### Example Reference:

📄 **Code Examples:** See `Topic4_ComposableFunctionsExamples.kt`

---

## LaunchedEffect

### Q2: What is LaunchedEffect and when do you use it?

**Answer:**
`LaunchedEffect` is used to run side effects (like network requests, database operations, logging) safely in composables. It runs when the composable enters composition and cleans up when it leaves.

### How LaunchedEffect Works:

```
Timeline:
─────────
Composable Enters Composition
    ↓
LaunchedEffect block executes
    ↓
Coroutine runs (can be suspend)
    ↓
Composable updates UI with results
    ↓
Composable Leaves Composition
    ↓
Cleanup block runs (if provided)
```

### Basic Usage:

```kotlin
@Composable
fun FetchUserData(userId: String) {
    var user by remember { mutableStateOf<User?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(userId) {
        // Runs once per userId change
        try {
            user = Api.fetchUser(userId)
        } catch (e: Exception) {
            error = e.message
        }
    }
    
    when {
        user != null -> Text("${user.name}")
        error != null -> Text("Error: $error")
        else -> Text("Loading...")
    }
}
```

### Key Concept: Dependencies

```kotlin
LaunchedEffect(key) {  // Dependency key
    // Runs when key changes
    // Cleanup runs before re-execution
}

Examples:
─────────
LaunchedEffect(Unit) {
    // Runs once when composable enters
}

LaunchedEffect(userId) {
    // Runs when userId changes
    // Cleans up previous userId's effects
}

LaunchedEffect(userId, page) {
    // Runs when userId OR page changes
}

LaunchedEffect(keys = arrayOf(a, b, c)) {
    // Multiple dependencies
}
```

### With Cleanup:

```kotlin
@Composable
fun LocationTracker() {
    LaunchedEffect(Unit) {
        val listener = LocationListener { location ->
            println("Location: ${location.latitude}, ${location.longitude}")
        }
        
        startLocationUpdates(listener)
        
        return@LaunchedEffect {
            // Cleanup block
            stopLocationUpdates(listener)
        }
    }
}
```

### Real-World Examples:

**Example 1: Network Request**
```kotlin
@Composable
fun MovieDetails(movieId: String) {
    var movie by remember { mutableStateOf<Movie?>(null) }
    var loading by remember { mutableStateOf(true) }
    
    LaunchedEffect(movieId) {
        loading = true
        try {
            movie = ApiService.getMovie(movieId)
        } finally {
            loading = false
        }
    }
    
    if (loading) CircularProgressIndicator()
    else movie?.let { Text(it.title) }
}
```

**Example 2: Timer**
```kotlin
@Composable
fun Stopwatch() {
    var seconds by remember { mutableStateOf(0) }
    
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            seconds++
        }
    }
    
    Text("Time: ${seconds}s")
}
```

### Example Reference:

📄 **Code Examples:** See `Topic4_ComposableFunctionsExamples.kt`

---

## SideEffect & DisposableEffect

### Q3: What are SideEffect and DisposableEffect?

**Answer:**
- **SideEffect**: Runs after composition, used for non-suspendable side effects
- **DisposableEffect**: SideEffect with guaranteed cleanup

### SideEffect:

```kotlin
@Composable
fun AnalyticsEvent(eventName: String) {
    SideEffect {
        // Runs after every successful composition
        Analytics.logEvent(eventName)
    }
}

// Runs on:
// - First composition
// - Every recomposition
// - Key changes
```

### DisposableEffect:

```kotlin
@Composable
fun SavedStateManager(key: String, value: Any) {
    DisposableEffect(key, value) {
        // Setup - runs after composition
        SavedState.set(key, value)
        
        onDispose {
            // Cleanup - runs before disposal
            SavedState.clear(key)
        }
    }
}
```

### Comparison:

| Aspect | LaunchedEffect | SideEffect | DisposableEffect |
|--------|---------------|-----------|------------------|
| **Can suspend** | ✅ Yes | ❌ No | ❌ No |
| **Has cleanup** | ✅ Yes | ❌ No | ✅ Yes |
| **Use for async** | ✅ Best | ❌ No | ❌ No |
| **Use for logging** | ✅ Works | ✅ Better | ❌ No |
| **Use for cleanup** | ✅ Yes | ❌ No | ✅ Yes |

### When to Use Each:

**LaunchedEffect:**
- Network requests
- Database queries
- Timer/Delay operations
- Any async work

**SideEffect:**
- Logging
- Analytics
- Simple side effects
- Non-suspendable operations

**DisposableEffect:**
- Resource cleanup
- Lifecycle management
- Listener registration
- Any setup/teardown pattern

### Example Reference:

📄 **Code Examples:** See `Topic4_ComposableFunctionsExamples.kt`

---

## Experimental APIs

### Q4: What is `@OptIn(ExperimentalMaterial3Api::class)` and how do you use it?

**Answer:**
`@OptIn` is used to opt into experimental/unstable APIs. It signals that you're aware the API may change. Material3 APIs are marked as experimental.

### Why Experimental APIs Exist:

✅ **API Evolution** - APIs may change before final release  
✅ **Beta Features** - New features under evaluation  
✅ **Feedback** - Google collects feedback before finalizing  
✅ **Breaking Changes** - Warns developers of potential changes  

### The Warning System:

```
Experimental API
    ↓
Marked with @Experimental or @OptIn
    ↓
Compiler warning if used without @OptIn
    ↓
Must explicitly opt-in
    ↓
Signals understanding of potential changes
```

### Using @OptIn:

**Method 1: Function-level OptIn**
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    TopAppBar(...)  // Experimental API
    NavigationBar(...)  // Experimental API
}
```

**Method 2: File-level OptIn**
```kotlin
@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.myapp

@Composable
fun MyScreen() {
    TopAppBar(...)  // No @OptIn needed here
    NavigationBar(...)
}
```

**Method 3: Class-level OptIn**
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // All composables in this activity can use experimental APIs
    }
}
```

### Your Code Example:

```kotlin
// Your MainActivity uses experimental TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    setContent {
        DemoProjectTheme {
            Scaffold(
                topBar = {
                    TopAppBar(  // ← ExperimentalMaterial3Api
                        title = { Text("Top bar") }
                    )
                }
            ) { innerPadding ->
                Greetings("Hello kotlin", modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
```

### Common Experimental APIs:

```
Material3:
├─ TopAppBar
├─ NavigationBar
├─ NavigationRail
└─ SearchBar

Animation:
├─ Animatable
└─ rememberInfiniteTransition

Layout:
├─ FlowRow
└─ FlowColumn
```

### Best Practices:

✅ **Understand** what experimental means  
✅ **Document** why you're using it  
✅ **Monitor** for API changes  
✅ **Test thoroughly** - API may change  
✅ **Check release notes** before updating libraries  

### Example Reference:

📄 **Code Examples:** See `Topic4_ComposableFunctionsExamples.kt`

---

## Composable Function Rules

### Rule 1: Pure Functions

```kotlin
// WRONG - Not pure
var counter = 0
@Composable
fun PureExample() {
    counter++  // Modifies external state!
    Text("$counter")
}

// RIGHT - Pure function
@Composable
fun PureExample(count: Int) {
    Text("$count")  // Only uses parameters
}
```

### Rule 2: Idempotent

```kotlin
// WRONG - Not idempotent
@Composable
fun IdempotentExample() {
    val random = Math.random()
    Text("$random")  // Different each time!
}

// RIGHT - Idempotent
@Composable
fun IdempotentExample() {
    val random by remember { mutableStateOf(Math.random()) }
    Text("$random")  // Same each execution
}
```

### Rule 3: Lightweight

```kotlin
// WRONG - Heavy computation
@Composable
fun HeavyExample() {
    val result = (1..1000000).sum()  // Every recomposition!
    Text("$result")
}

// RIGHT - Cached
@Composable
fun HeavyExample() {
    val result by remember { mutableStateOf((1..1000000).sum()) }
    Text("$result")
}
```

---

## Interview Tips

### Common Questions:

**Q: Why can't composables have side effects?**
- Recomposition is frequent
- Effects would run multiple times
- Unpredictable behavior
- Memory leaks
- Performance issues

**Q: When do you use LaunchedEffect vs SideEffect?**
- `LaunchedEffect`: For async operations, network calls
- `SideEffect`: For simple logging, analytics
- `DisposableEffect`: For cleanup operations

**Q: What does @OptIn mean?**
- Using experimental/unstable API
- API may change in future
- Must explicitly acknowledge understanding
- Encourages caution

**Q: Why must composables be idempotent?**
- Compose may reorder/skip execution
- Same inputs must always produce same output
- Enables smart recomposition
- Prevents bugs

---

## Summary

| Concept | Purpose | When to Use |
|---------|---------|-----------|
| **LaunchedEffect** | Run suspend functions | Async operations |
| **SideEffect** | Run after composition | Simple side effects |
| **DisposableEffect** | Run with cleanup | Resource management |
| **@OptIn** | Use experimental APIs | When necessary |
| **Purity** | No external changes | Always |
| **Idempotency** | Same input = same output | Always |

---

## Next Steps

1. ✅ Understand composable rules
2. ✅ Master effect functions
3. ✅ Learn about experimental APIs
4. ✅ Study layout and modifiers (Topic 5)

**Next Topic:** [Topic 5: Layout & Modifiers](./05_layout_modifiers.md)

