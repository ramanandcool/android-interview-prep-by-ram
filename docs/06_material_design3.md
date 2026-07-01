# Topic 6: Material Design 3

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [Scaffold Component](#scaffold-component)
2. [TopAppBar](#topappbar)
3. [InnerPadding](#innerpadding)
4. [Material Design 3 Colors](#material-design-3-colors)

---

## Scaffold Component

### Q1: What is a Scaffold in Compose?

**Answer:**
`Scaffold` is a layout composable that implements Material Design structure. It provides slots for AppBar, FloatingActionButton, BottomNavigation, and main content with automatic padding management.

### Why Use Scaffold?

✅ **Material Design compliance** - Follows official guidelines  
✅ **Consistent structure** - Standard app layout  
✅ **Automatic padding** - Handles system bars & AppBar  
✅ **Pre-built slots** - TopBar, FAB, BottomBar, Drawer  
✅ **Professional** - Expected in modern apps  

### Scaffold Structure:

```
┌─────────────────────────┐
│      TopAppBar          │ (topBar = { })
├─────────────────────────┤
│                         │
│    Main Content         │ (innerPadding applied)
│                         │
├─────────────────────────┤
│    BottomNavigation     │ (bottomBar = { })
├─────────────────────────┤
│                         │
│  FloatingActionButton   │ (floatingActionButton = { })
└─────────────────────────┘
```

### Basic Implementation:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My App") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Content with innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Main content")
        }
    }
}
```

### Scaffold Slots:

| Slot | Purpose | Optional |
|------|---------|----------|
| **topBar** | AppBar at top | ✅ Yes |
| **bottomBar** | Navigation at bottom | ✅ Yes |
| **floatingActionButton** | FAB button | ✅ Yes |
| **snackbarHost** | Snackbar container | ✅ Yes |
| **drawerContent** | Navigation drawer | ✅ Yes |
| **content** | Main content | ❌ Required |

### Your Implementation:

```kotlin
// From your MainActivity.kt
Scaffold(
    topBar = {
        TopAppBar(
            title = {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    Alignment.CenterHorizontally
                ) { Text("Top bar") }
            }
        )
    },
    modifier = Modifier.fillMaxSize()
) { innerPadding ->
    Greetings(
        "Hello kotlin",
        modifier = Modifier.padding(innerPadding)
    )
}
```

### Example Reference:

📄 **Code Examples:** See `Topic6_MaterialDesign3Examples.kt`

---

## TopAppBar

### Q2: What is TopAppBar and how do you customize it?

**Answer:**
`TopAppBar` is a Material Design 3 component that displays the app's primary navigation and branding. It appears at the top of the screen.

### TopAppBar Types:

| Type | Use Case | When |
|------|----------|------|
| **TopAppBar** | Standard bar | Most common |
| **CenterAlignedTopAppBar** | Centered title | Alternative style |
| **MediumTopAppBar** | Larger with collapse | Scrollable content |
| **LargeTopAppBar** | Large with collapse | Featured content |

### Basic TopAppBar:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBar() {
    TopAppBar(
        title = { Text("My App") },
        modifier = Modifier.fillMaxWidth()
    )
}
```

### TopAppBar with Navigation:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithNavigation() {
    TopAppBar(
        title = { Text("Screen Title") },
        navigationIcon = {
            IconButton(onClick = { /* back */ }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
        }
    )
}
```

### TopAppBar with Actions:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithActions() {
    TopAppBar(
        title = { Text("Messages") },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Menu, "Menu")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, "Search")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, "More")
            }
        }
    )
}
```

### TopAppBar with Colors:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarStyled() {
    TopAppBar(
        title = { Text("Styled Title", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
```

### Example Reference:

📄 **Code Examples:** See `Topic6_MaterialDesign3Examples.kt`

---

## InnerPadding

### Q3: What is `innerPadding` in Scaffold and why is it important?

**Answer:**
`innerPadding` is the padding automatically provided by Scaffold that accounts for space taken by TopAppBar, BottomNavigation, and system bars. It prevents content from being hidden behind these elements.

### Why InnerPadding Is Important:

```
Without innerPadding:
┌──────────────────┐
│   TopAppBar      │  (Height = 56.dp)
├──────────────────┤
│ Content Hidden! ← │  Hidden behind AppBar!
│ Text            │
│ Buttons         │
└──────────────────┘

With innerPadding:
┌──────────────────┐
│   TopAppBar      │
├──────────────────┤  ← innerPadding applied
│ Content Visible │
│ Text            │
│ Buttons         │
└──────────────────┘
```

### How It Works:

```kotlin
Scaffold(
    topBar = { TopAppBar(...) }  // Height = 56.dp
) { innerPadding ->
    // innerPadding = PaddingValues(top = 56.dp)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)  // Apply the padding!
    ) {
        Text("Now content is visible!")
    }
}
```

### PaddingValues Structure:

```kotlin
// innerPadding provides:
PaddingValues(
    top = 56.dp,      // TopAppBar height
    bottom = 80.dp,   // BottomBar height (if exists)
    start = 0.dp,     // Left padding
    end = 0.dp        // Right padding
)
```

### Common Mistakes:

```kotlin
// WRONG - Ignoring innerPadding
Scaffold(
    topBar = { TopAppBar(title = { Text("App") }) }
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Hidden behind TopAppBar!")  // ❌
    }
}

// RIGHT - Using innerPadding
Scaffold(
    topBar = { TopAppBar(title = { Text("App") }) }
) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)  // ✅ Apply padding
    ) {
        Text("Now visible!")
    }
}
```

### With Multiple Bars:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithAllBars() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Top") }) },
        bottomBar = { BottomNavigation(...) }
    ) { innerPadding ->
        // innerPadding accounts for BOTH bars!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Safe content area")
        }
    }
}
```

### Example Reference:

📄 **Code Examples:** See `Topic6_MaterialDesign3Examples.kt`

---

## Material Design 3 Colors

### Q4: What are Material Design 3 colors and themes?

**Answer:**
Material Design 3 uses a dynamic color system with primary, secondary, and tertiary colors that automatically adjust based on light/dark theme and device wallpaper (Android 12+).

### Color Palette:

Material3 defines these main colors:

| Color | Purpose | Light | Dark |
|-------|---------|-------|------|
| **Primary** | Main brand color | Vibrant | Darker shade |
| **Secondary** | Accent color | Muted | Darker shade |
| **Tertiary** | Additional accent | Different hue | Darker shade |
| **Error** | Error state | Red | Red |
| **Success** | Success state | Green | Green |

### Accessing Colors:

```kotlin
@Composable
fun ColorExample() {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val background = MaterialTheme.colorScheme.background
    val surface = MaterialTheme.colorScheme.surface
    val error = MaterialTheme.colorScheme.error
    
    // Use in composables
    Text("Hello", color = primary)
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text("Click")
    }
}
```

### Surface & Container Colors:

```
onPrimary
    ↑
Primary (background)
    ↓
Content color automatically chosen

Examples:
- Text on primary background: onPrimary (usually white)
- Text on surface: onSurface (usually black/dark)
- Text on error: onError (usually white)
```

### Dynamic Colors (Android 12+):

```kotlin
// Material3 automatically adapts to wallpaper
// No extra code needed

// Light theme: Colors extracted from wallpaper (lighter)
// Dark theme: Colors extracted from wallpaper (darker)
```

### Using Theme Throughout App:

```kotlin
@Composable
fun ThemedApp() {
    DemoProjectTheme {
        // All composables get theme colors automatically
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("App") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    "Themed Text",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
```

### Color Combinations:

```
Container Color     On Color
─────────────────────────────
Primary         →   OnPrimary
Secondary       →   OnSecondary
Tertiary        →   OnTertiary
Background      →   OnBackground
Surface         →   OnSurface
Error           →   OnError
```

### Example Reference:

📄 **Code Examples:** See `Topic6_MaterialDesign3Examples.kt`

---

## Material Design 3 Components

### Common Components:

| Component | Purpose |
|-----------|---------|
| **Button** | Primary action |
| **FilledButton** | Emphasized action |
| **OutlinedButton** | Secondary action |
| **TextButton** | Tertiary action |
| **Card** | Container with shadow |
| **TextField** | Text input |
| **TopAppBar** | Header |
| **BottomNavigation** | Navigation bar |
| **FloatingActionButton** | Floating action |
| **Chip** | Selectable tag |

### Using Components:

```kotlin
@Composable
fun ComponentsExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Primary button
        Button(onClick = {}) {
            Text("Primary")
        }
        
        // Secondary button
        OutlinedButton(onClick = {}) {
            Text("Secondary")
        }
        
        // Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Card Title")
                Text("Card Content")
            }
        }
        
        // Text field
        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") }
        )
    }
}
```

---

## Interview Tips

### Common Questions:

**Q: What is Scaffold?**
- Material Design layout structure
- Provides slots for AppBar, FAB, BottomBar
- Handles automatic padding with innerPadding
- Professional standard for apps

**Q: Why use innerPadding?**
- Prevents content overlap with AppBar
- Automatic spacing management
- Works with multiple bars
- Follows Material Design

**Q: What are Material Design 3 colors?**
- Primary, Secondary, Tertiary colors
- Light and Dark themes
- Dynamic color from wallpaper (Android 12+)
- OnColor for contrast

**Q: How do you customize TopAppBar?**
- Set title, navigationIcon, actions
- Customize colors with TopAppBarDefaults
- Different types: Standard, Centered, Medium, Large
- Add styling as needed

---

## Summary Table

| Concept | Purpose | Example |
|---------|---------|---------|
| **Scaffold** | App structure | Main layout container |
| **TopAppBar** | Header | `TopAppBar(...)` |
| **bottomBar** | Navigation | BottomNavigation |
| **innerPadding** | Spacing | `padding(innerPadding)` |
| **MaterialTheme** | Colors | `MaterialTheme.colorScheme.primary` |
| **Button** | Action | `Button(...)` |
| **Card** | Container | `Card(...)` |
| **TextField** | Input | `TextField(...)` |

---

## Next Steps

1. ✅ Understand Scaffold structure
2. ✅ Master TopAppBar customization
3. ✅ Learn innerPadding usage
4. ✅ Apply Material Design 3 colors
5. ✅ Study Best Practices (Topic 7)

**Next Topic:** [Topic 7: Best Practices](./07_best_practices.md)

