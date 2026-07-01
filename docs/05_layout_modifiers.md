# Topic 5: Layout & Modifiers

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [Understanding Modifiers](#understanding-modifiers)
2. [Modifier Chain & Order](#modifier-chain--order)
3. [Layout Containers](#layout-containers)
4. [Size Modifiers](#size-modifiers)
5. [Spacing Modifiers](#spacing-modifiers)

---

## Understanding Modifiers

### Q1: What are Modifiers in Jetpack Compose and give examples

**Answer:**
Modifiers are objects that customize the appearance and behavior of Compose UI elements. They're chainable and allow you to set size, padding, color, click behavior, and much more.

### What Modifiers Do:

```
Composable Element
    ↓
Apply Modifier
    ├─ Size adjustment
    ├─ Spacing (padding)
    ├─ Colors & styling
    ├─ Interaction handlers
    ├─ Alignment
    └─ Custom effects
    ↓
Customized Element
```

### Common Modifiers:

| Modifier | Purpose | Example |
|----------|---------|---------|
| `.fillMaxSize()` | Fill all available space | `Modifier.fillMaxSize()` |
| `.fillMaxWidth()` | Fill width only | `Modifier.fillMaxWidth()` |
| `.fillMaxHeight()` | Fill height only | `Modifier.fillMaxHeight()` |
| `.size()` | Set fixed size | `Modifier.size(100.dp)` |
| `.width()` | Set fixed width | `Modifier.width(200.dp)` |
| `.height()` | Set fixed height | `Modifier.height(50.dp)` |
| `.padding()` | Add internal spacing | `Modifier.padding(16.dp)` |
| `.background()` | Set background color | `Modifier.background(Color.Red)` |
| `.border()` | Add border | `Modifier.border(2.dp, Color.Black)` |
| `.clickable()` | Handle clicks | `Modifier.clickable { }` |
| `.align()` | Align within parent | `Modifier.align(Alignment.Center)` |

### Complete Example:

```kotlin
@Composable
fun ModifierExample() {
    Text(
        text = "Hello",
        modifier = Modifier
            .fillMaxWidth()           // Takes full width
            .height(60.dp)            // Set height
            .background(Color.Blue)   // Blue background
            .padding(16.dp)           // Internal spacing
            .clickable { println("Clicked!") }  // Clickable
    )
}
```

### Example Reference:

📄 **Code Examples:** See `Topic5_LayoutModifiersExamples.kt`

---

## Modifier Chain & Order

### Q2: Does the order of modifiers matter?

**Answer:**
YES! The order of modifiers matters significantly. Modifiers are applied left-to-right, and each modifier builds on the result of the previous one.

### How Modifier Order Works:

```
Modifier.size(100.dp).padding(16.dp)

Step 1: size(100.dp)
        Box becomes 100x100 dp

Step 2: padding(16.dp)
        Space added OUTSIDE the 100x100 box
        Total becomes 132x132 dp (100 + 16*2)

vs.

Modifier.padding(16.dp).size(100.dp)

Step 1: padding(16.dp)
        Allocate space for padding
        
Step 2: size(100.dp)
        Box is exactly 100x100 (inside padding)
        Total appears 132x132 but layout is different
```

### Visual Comparison:

```
Order 1: size().padding()
┌────────────────────┐ 16.dp padding
│  ┌──────────────┐  │ 
│  │              │  │ 100.dp
│  │   Content    │  │
│  │              │  │
│  └──────────────┘  │
│  ┌──────────────┐  │
└────────────────────┘


Order 2: padding().size()
┌──────────────────────┐
│  ┌────────────────┐  │ 16.dp padding
│  │  ┌──────────┐ │  │ (constrained by size)
│  │  │ Content  │ │  │ 100.dp
│  │  └──────────┘ │  │
│  └────────────────┘  │
└──────────────────────┘
```

### General Rules:

✅ **Put layout modifiers first:**
```kotlin
Modifier
    .size(100.dp)
    .padding(16.dp)
```

✅ **Then decoration modifiers:**
```kotlin
Modifier
    .size(100.dp)
    .padding(16.dp)
    .background(Color.Blue)
```

✅ **Then interaction modifiers:**
```kotlin
Modifier
    .size(100.dp)
    .padding(16.dp)
    .background(Color.Blue)
    .clickable { }
```

### Real Example:

```kotlin
// Good order
Text(
    "Click me",
    modifier = Modifier
        .size(100.dp)          // Layout
        .padding(16.dp)        // Spacing
        .background(Color.Blue) // Decoration
        .clickable { }         // Interaction
)

// Also correct - different effect
Button(
    modifier = Modifier
        .fillMaxWidth()        // Layout
        .padding(16.dp)        // Spacing
        .height(50.dp),        // Layout adjustment
    onClick = {}
) {
    Text("Submit")
}
```

### Example Reference:

📄 **Code Examples:** See `Topic5_LayoutModifiersExamples.kt`

---

## Layout Containers

### Q3: What is the difference between Column, Row, and Box?

**Answer:**
These are layout containers that arrange children in different ways:
- **Column**: Vertical arrangement (top to bottom)
- **Row**: Horizontal arrangement (left to right)
- **Box**: Stacking on top of each other

### Column - Vertical Layout

```kotlin
@Composable
fun ColumnExample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Item 1")
        Text("Item 2")
        Text("Item 3")
    }
}

Visual Result:
┌─────────────┐
│   Item 1    │
├─────────────┤
│   Item 2    │
├─────────────┤
│   Item 3    │
└─────────────┘
```

### Row - Horizontal Layout

```kotlin
@Composable
fun RowExample() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Item 1")
        Text("Item 2")
        Text("Item 3")
    }
}

Visual Result:
┌──────────────────────┐
│  Item 1  Item 2  Item 3  │
└──────────────────────┘
```

### Box - Stacked Layout

```kotlin
@Composable
fun BoxExample() {
    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(...)           // Background
        Text("On Top")       // Foreground
        Icon(...)            // On top of text
    }
}

Visual Result (Z-order):
┌──────────────┐
│  ┌──────────┐│
│  │ Image    ││
│  │ Text     ││
│  │ Icon     ││
│  └──────────┘│
└──────────────┘
```

### Comparison Table:

| Aspect | Column | Row | Box |
|--------|--------|-----|-----|
| **Direction** | Vertical | Horizontal | Stacked |
| **Main axis** | Y axis | X axis | Z axis (depth) |
| **Best for** | Lists, forms | Toolbars, buttons | Overlays, backgrounds |
| **Arrangement** | verticalArrangement | horizontalArrangement | contentAlignment |
| **Alignment** | horizontalAlignment | verticalAlignment | contentAlignment |

### Your Code Example (Column):

```kotlin
// From your MainActivity.kt
@Composable
fun Greetings(name: String, modifier: Modifier) {
    Column(
        Modifier
            .fillMaxSize()  // Takes entire space
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,  // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally  // Center horizontally
    ) {
        Text(text = name, modifier = modifier)
        Button(onClick = {}) {
            Text(text = "Click me Button")
        }
    }
}
```

### Example Reference:

📄 **Code Examples:** See `Topic5_LayoutModifiersExamples.kt`

---

## Size Modifiers

### Q4: Explain `fillMaxSize()` vs `fillMaxWidth()` vs `wrapContentSize()`

**Answer:**

| Modifier | Behavior | Use Case |
|----------|----------|----------|
| **fillMaxSize()** | Takes 100% width AND height | Full-screen content |
| **fillMaxWidth()** | Takes 100% width, height wraps content | Buttons, full-width containers |
| **fillMaxHeight()** | Takes 100% height, width wraps content | Sidebars, vertical dividers |
| **wrapContentSize()** | Takes only space needed | Small elements, exactly-sized content |
| **size(dp)** | Fixed size | Exact dimensions |
| **width(dp)** | Fixed width, height wraps | Precise width, flexible height |
| **height(dp)** | Fixed height, width wraps | Precise height, flexible width |

### Visual Examples:

```
fillMaxSize() - Takes everything
┌────────────────────┐
│                    │ 100% height
│    Content         │
│                    │
└────────────────────┘
  100% width


fillMaxWidth() - Full width, wrap height
┌────────────────────┐
│    Content         │ Auto height
└────────────────────┘
  100% width


fillMaxHeight() - Full height, wrap width
┌──┐
│  │ 100% height
│  │
└──┘
Auto width


wrapContentSize() - Only needed space
┌────┐
│ Hi │ Auto
└────┘
Auto
```

### Practical Examples:

```kotlin
// Full screen column
Column(modifier = Modifier.fillMaxSize()) {
    Text("Top")
    Spacer(modifier = Modifier.weight(1f))
    Text("Bottom")
}

// Full width button
Button(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    onClick = {}
) {
    Text("Full Width Button")
}

// Fixed size square
Box(modifier = Modifier.size(100.dp)) {
    Text("100x100")
}

// Width 200dp, height auto
Text(
    "Long text...",
    modifier = Modifier.width(200.dp)
)

// Height 50dp, width auto
Text(
    "Text",
    modifier = Modifier.height(50.dp)
)

// Only what's needed
Text(
    "Compact",
    modifier = Modifier.wrapContentSize()
)
```

### Example Reference:

📄 **Code Examples:** See `Topic5_LayoutModifiersExamples.kt`

---

## Spacing Modifiers

### Q5: What are padding and margin modifiers?

**Answer:**
- **padding()**: Internal spacing (inside borders)
- **margin()**: External spacing (outside element) - use arrangement instead in Compose

In Compose, use `Arrangement` for spacing between children, and `padding()` for internal spacing.

### Padding Examples:

```kotlin
// Same padding all sides
Text(
    "Hello",
    modifier = Modifier.padding(16.dp)  // All sides: 16.dp
)

// Different sides
Text(
    "Hello",
    modifier = Modifier.padding(
        top = 8.dp,
        bottom = 8.dp,
        start = 16.dp,  // start = left in LTR
        end = 16.dp     // end = right in LTR
    )
)

// Horizontal and vertical
Text(
    "Hello",
    modifier = Modifier.padding(
        horizontal = 16.dp,  // Left and right
        vertical = 8.dp      // Top and bottom
    )
)
```

### Arrangement Examples:

```kotlin
// Space items evenly
Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceEvenly
) {
    Text("Item 1")
    Text("Item 2")
    Text("Item 3")
}

// Space between items
Row(
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text("First")
    Text("Last")
}

// Space around items
Column(
    verticalArrangement = Arrangement.SpaceAround
) {
    Text("A")
    Text("B")
    Text("C")
}

// Centered with space
Row(
    horizontalArrangement = Arrangement.Center
) {
    Text("Centered")
}
```

### Arrangement Types:

| Arrangement | Behavior |
|------------|----------|
| `Arrangement.Start` | Items at start (left/top) |
| `Arrangement.End` | Items at end (right/bottom) |
| `Arrangement.Center` | Items centered |
| `Arrangement.SpaceBetween` | Space between items, none at edges |
| `Arrangement.SpaceEvenly` | Equal space everywhere |
| `Arrangement.SpaceAround` | Equal space around items |

### Example Reference:

📄 **Code Examples:** See `Topic5_LayoutModifiersExamples.kt`

---

## Alignment

### Alignment in Column:

```kotlin
Column(
    horizontalAlignment = Alignment.CenterHorizontally  // X axis
) {
    // All children centered horizontally
}

// Options:
// - Alignment.Start (left)
// - Alignment.CenterHorizontally (center)
// - Alignment.End (right)
```

### Alignment in Row:

```kotlin
Row(
    verticalAlignment = Alignment.CenterVertically  // Y axis
) {
    // All children centered vertically
}

// Options:
// - Alignment.Top
// - Alignment.CenterVertically (center)
// - Alignment.Bottom
```

### Alignment in Box:

```kotlin
Box(
    contentAlignment = Alignment.Center  // Both X and Y
) {
    // Content centered in box
}

// Options:
// - Alignment.TopStart
// - Alignment.TopCenter
// - Alignment.Center
// - Alignment.BottomEnd
// - etc...
```

---

## Interview Tips

### Common Questions:

**Q: Does modifier order matter?**
- Yes! Order significantly affects layout
- Layout modifiers first
- Then decoration, then interaction
- Different order = different result

**Q: When would you use Column vs Row?**
- Column: Vertical stacking (lists, forms)
- Row: Horizontal stacking (toolbars, buttons)
- Box: Overlays, stacked layouts

**Q: What's the difference between padding and arrangement?**
- Padding: Internal spacing within element
- Arrangement: Spacing between children in container

**Q: How do you center content in Compose?**
- Column + horizontalAlignment = Alignment.CenterHorizontally
- Row + verticalAlignment = Alignment.CenterVertically
- Box + contentAlignment = Alignment.Center

---

## Summary Table

| Concept | Purpose | Example |
|---------|---------|---------|
| **Modifiers** | Customize elements | `Modifier.size(100.dp)` |
| **Modifier Order** | Affects layout result | Size → Padding → Decoration |
| **Column** | Vertical layout | `Column { }` |
| **Row** | Horizontal layout | `Row { }` |
| **Box** | Stacked layout | `Box { }` |
| **fillMaxSize()** | Full screen | `.fillMaxSize()` |
| **padding()** | Internal spacing | `.padding(16.dp)` |
| **Arrangement** | Space between children | `Arrangement.Center` |
| **Alignment** | Align children | `Alignment.CenterHorizontally` |

---

## Next Steps

1. ✅ Understand modifiers
2. ✅ Master layout containers
3. ✅ Learn size and spacing
4. ✅ Study Material Design 3 (Topic 6)

**Next Topic:** [Topic 6: Material Design 3](./06_material_design3.md)

