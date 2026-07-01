# Topic 2: ComponentActivity & Lifecycle

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [ComponentActivity vs AppCompatActivity](#componentactivity-vs-appcompatactivity)
2. [Activity Lifecycle](#activity-lifecycle)
3. [EdgeToEdge Display](#edgetodge-display)

---

## ComponentActivity vs AppCompatActivity

### Q1: What is ComponentActivity and why use it instead of AppCompatActivity?

**Answer:**
`ComponentActivity` is the base class for activities that support Jetpack Compose. It's lighter, more modern, and specifically designed for Compose-based apps.

### Why Choose ComponentActivity?

| Aspect | ComponentActivity | AppCompatActivity |
|--------|-------------------|-------------------|
| **Size** | Lightweight | Heavier (legacy) |
| **Compose Support** | Native | Partial/Retrofitted |
| **Dependencies** | Minimal | Many AppCompat libs |
| **Modern Features** | Yes | Older approach |
| **Edge-to-edge** | Easy | Requires setup |
| **Best For** | Compose Apps | XML/Hybrid Apps |

### Advantages:

✅ **Minimal dependencies** - Fewer unnecessary libraries  
✅ **Better Compose integration** - Built for Compose from ground up  
✅ **Modern approach** - No legacy baggage  
✅ **Edge-to-edge support** - System bars integration out of box  
✅ **Smaller APK** - Less bloat  
✅ **Cleaner code** - No AppCompat workarounds  

### When to Use:

- ✅ **ComponentActivity:** New Compose-based apps
- ✅ **AppCompatActivity:** Existing XML-based apps, gradual migration

### Example Reference:

📄 **Code Examples:** See `Topic2_ComponentActivityExamples.kt`

---

## Activity Lifecycle

### Q2: What is the Activity Lifecycle in Android?

**Answer:**
The Activity Lifecycle is a series of states an activity goes through from creation to destruction. Understanding this is crucial for managing resources properly.

### Lifecycle States:

```
┌─────────────┐
│   onCreate  │ ← Activity created (first time)
├─────────────┤
│  onStart    │ ← Activity becomes visible
├─────────────┤
│  onResume   │ ← Activity in focus, user can interact
├─────────────┤
│  onPause    │ ← Activity loses focus (dialog appears, etc.)
├─────────────┤
│  onStop     │ ← Activity not visible
├─────────────┤
│  onDestroy  │ ← Activity destroyed
└─────────────┘
```

### Key Lifecycle Methods:

1. **onCreate(savedInstanceState)**
   - Called once when activity is first created
   - Initialize UI, bind views, set up state
   - Your entry point

2. **onStart()**
   - Called when activity becomes visible
   - App still not interactive yet

3. **onResume()**
   - Called when activity is in foreground
   - User can now interact with the app
   - Start animations, timers

4. **onPause()**
   - Activity loses focus
   - Save data, stop animations
   - Another activity comes to front

5. **onStop()**
   - Activity is no longer visible
   - Stop heavy operations

6. **onDestroy()**
   - Activity is being destroyed
   - Clean up all resources

### Using Lifecycle with Compose:

With Compose and `setContent`, you typically only need `onCreate`:

```
ComponentActivity
└── onCreate()
    └── enableEdgeToEdge()
    └── setContent {
        └── Your Compose UI
    }
```

### Lifecycle Observations:

Use `LaunchedEffect` to observe lifecycle in Compose:

```kotlin
@Composable
fun MyScreen() {
    LaunchedEffect(Unit) {
        // Runs once when composable enters composition
        println("Screen entered")
        
        return@LaunchedEffect {
            // Cleanup when composable leaves composition
            println("Screen exited")
        }
    }
}
```

### Example Reference:

📄 **Code Examples:** See `Topic2_ComponentActivityExamples.kt`

---

## EdgeToEdge Display

### Q3: What does `enableEdgeToEdge()` do?

**Answer:**
`enableEdgeToEdge()` enables the app to use the full screen including system bars (status bar, navigation bar). Content extends behind these bars.

### What It Does:

🎯 **Enables:**
- Content renders behind status bar
- Content renders behind navigation bar
- Full screen utilization
- Modern immersive experience

### Visual Comparison:

```
WITHOUT enableEdgeToEdge()
┌────────────────────┐
│ [Status Bar]       │  (Reserved space, content below)
├────────────────────┤
│  Your Content      │
│                    │
│                    │
├────────────────────┤
│ [Navigation Bar]   │  (Reserved space, content above)
└────────────────────┘

WITH enableEdgeToEdge()
┌────────────────────┐
│[Status]Your Content│  (Content extends behind)
├────────────────────┤
│  Your Content      │
│                    │
├────────────────────┤
│Content[NavBar]     │  (Content extends behind)
└────────────────────┘
```

### Implementation:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()  // Add this line
    
    setContent {
        DemoProjectTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                // Your content with proper padding
            }
        }
    }
}
```

### Key Points:

✅ **Must pair with Scaffold** - Use `innerPadding` to avoid overlap  
✅ **System bar colors** - Can customize using theme  
✅ **Content safety** - Use proper padding to keep text readable  
✅ **Modern standard** - Expected in modern apps  

### Handling System Bars:

```kotlin
Scaffold(
    topBar = { TopAppBar(...) },
    modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()  // Automatically handle system bars
) { innerPadding ->
    // Content automatically positioned correctly
    Column(modifier = Modifier.padding(innerPadding)) {
        // Your content here
    }
}
```

### Benefits:

🎨 **Visual Appeal** - Modern, immersive look  
📱 **Full Screen** - Utilize all available space  
💫 **Professional** - Expected behavior in 2024+ apps  
🎯 **Consistent** - Matches other modern Android apps  

### Example Reference:

📄 **Code Examples:** See `Topic2_ComponentActivityExamples.kt`

---

## Your Current Implementation

Your `MainActivity.kt` uses all these concepts correctly:

```kotlin
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // ← Edge-to-edge display
        
        setContent {
            DemoProjectTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Top bar") })
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->  // ← Proper padding handling
                    Greetings(
                        "Hello kotlin",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
```

**What's Good:**
✅ Extends `ComponentActivity` - Modern choice  
✅ Uses `enableEdgeToEdge()` - Modern approach  
✅ Proper padding with `innerPadding` - Prevents overlap  
✅ Uses Material3 Scaffold - Correct structure  

---

## Interview Tips

### Common Questions:

**Q: Why ComponentActivity over AppCompatActivity?**
- Better Compose support
- Lighter weight
- Modern approach
- Less legacy code

**Q: What's the purpose of `enableEdgeToEdge()`?**
- Uses full screen real estate
- Renders behind system bars
- More modern UI/UX
- Expected in modern apps

**Q: When do you use which lifecycle method?**
- onCreate: Initialization
- onResume: Start heavy operations
- onPause: Save data, cleanup
- onDestroy: Final cleanup

**Q: Why use Scaffold with innerPadding?**
- Prevents content overlap with AppBar
- Automatic spacing management
- Follows Material Design
- Easy to maintain

---

## Common Mistakes

### ❌ Mistake 1: Ignoring innerPadding

```kotlin
// WRONG - Content hides behind TopAppBar
Scaffold(
    topBar = { TopAppBar(title = { Text("App") }) }
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Hidden!")  // This might be hidden
    }
}

// RIGHT - Using innerPadding
Scaffold(
    topBar = { TopAppBar(title = { Text("App") }) }
) { innerPadding ->
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {  // Proper spacing
        Text("Visible!")
    }
}
```

### ❌ Mistake 2: Using AppCompatActivity for Compose

```kotlin
// WRONG - Unnecessary dependencies
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { /* Compose code */ }
    }
}

// RIGHT - Use ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { /* Compose code */ }
    }
}
```

### ❌ Mistake 3: Forgetting enableEdgeToEdge()

```kotlin
// WRONG - Wastes screen space
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Missing enableEdgeToEdge()
    setContent { /* UI */ }
}

// RIGHT - Use edge-to-edge
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()  // Modern approach
    setContent { /* UI */ }
}
```

---

## Summary Table

| Concept | Purpose | When Used |
|---------|---------|-----------|
| **ComponentActivity** | Base class for Compose | All new Compose apps |
| **onCreate()** | Initialize activity | Once at startup |
| **enableEdgeToEdge()** | Full screen utilization | Modern apps |
| **Scaffold** | Material structure | Most Compose layouts |
| **innerPadding** | System bar spacing | Inside Scaffold |

---

## Next Steps

1. ✅ Understand ComponentActivity
2. ✅ Know lifecycle methods
3. ✅ Learn edge-to-edge display
4. ✅ Master State Management (Topic 3)

**Next Topic:** [Topic 3: State Management](./03_state_management.md)

