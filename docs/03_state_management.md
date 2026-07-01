# Topic 3: State Management

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [The Remember Function](#the-remember-function)
2. [RememberSaveable](#remembersaveable)
3. [MutableStateOf](#mutablestateof)
4. [State Hoisting](#state-hoisting)

---

## The Remember Function

### Q1: What is the `remember` function and when should you use it?

**Answer:**
`remember` is a Compose API that stores a value across recompositions. It ensures the same object instance is preserved when the composable recomposes.

### Why You Need Remember:

Without `remember`, state is lost on every recomposition:

```
Recomposition Timeline (Without remember):
────────────────────────────────────────
Time 1: count = 0 → User clicks → count = 1
Time 2: Recomposition happens
        count = 0 (NEW variable created!)
Time 3: count = 0 → User clicks → count = 1
Time 4: Recomposition happens
        count = 0 (NEW variable created again!)

Result: Counter always resets!
```

### What Remember Does:

```
Recomposition Timeline (With remember):
────────────────────────────────────────
Time 1: count = 0 (stored in remember)
        User clicks → count = 1
Time 2: Recomposition happens
        count = 1 (SAME instance from remember!)
Time 3: count = 1 → User clicks → count = 2
Time 4: Recomposition happens
        count = 2 (SAME instance preserved!)

Result: Counter works correctly!
```

### When to Use Remember:

✅ **Store UI state:**
- Selection state
- Visibility toggles
- Form input
- Animation state

✅ **Cache expensive computations:**
- Complex calculations
- Database queries
- Image processing

✅ **Remember user interactions:**
- Scroll position
- Text input
- Selected items

### Important Limitation:

⚠️ **Remember alone doesn't survive:**
- Configuration changes (device rotation)
- App being killed by system
- Process death

For these cases, use `rememberSaveable` instead!

### Example Reference:

📄 **Code Examples:** See `Topic3_StateManagementExamples.kt`

---

## RememberSaveable

### Q2: What is `rememberSaveable` and how does it differ from `remember`?

**Answer:**
`rememberSaveable` is like `remember` but also persists state through configuration changes and process death.

### Comparison Table:

| Aspect | `remember` | `rememberSaveable` |
|--------|-----------|-------------------|
| **Survives recomposition** | ✅ Yes | ✅ Yes |
| **Survives rotation** | ❌ No | ✅ Yes |
| **Survives process death** | ❌ No | ✅ Yes (with saver) |
| **Use case** | Temporary UI state | Persistent state |
| **Performance** | Faster | Slightly slower |
| **When to use** | Most cases | Important data |

### State Persistence Scenarios:

```
Scenario 1: Recomposition
─────────────────────────
remember: ✅ Survives
rememberSaveable: ✅ Survives

Scenario 2: Device Rotation
───────────────────────────
remember: ❌ Lost (reset to initial value)
rememberSaveable: ✅ Preserved

Scenario 3: App Backgrounded & Killed
──────────────────────────────────────
remember: ❌ Lost
rememberSaveable: ✅ Preserved (if saver configured)

Scenario 4: Configuration Change
────────────────────────────────
remember: ❌ Lost
rememberSaveable: ✅ Preserved
```

### When to Use RememberSaveable:

✅ **Counter in a game** - User shouldn't lose score on rotation  
✅ **Form data** - User shouldn't lose input on rotation  
✅ **Search query** - Should persist through config changes  
✅ **Tab selection** - Tab should stay selected on rotation  

### Example Reference:

📄 **Code Examples:** See `Topic3_StateManagementExamples.kt`

---

## MutableStateOf

### Q3: What is `mutableStateOf` and how does it work?

**Answer:**
`mutableStateOf` creates a mutable state object that triggers recomposition when its value changes. It's the core of reactive UI in Compose.

### How It Works:

```
mutableStateOf() → State<T> object
                 ├─ Holds a value
                 ├─ Notifies when value changes
                 └─ Triggers recomposition

Usage:
val state: State<Int> = mutableStateOf(0)
val value: Int = state.value  // Get value
state.value = 5               // Set value → Triggers recomp
```

### Creating State:

```kotlin
// Method 1: Using delegation
var count by mutableStateOf(0)  // Cleaner syntax
count = 1  // Direct assignment

// Method 2: Direct assignment
val state = mutableStateOf(0)
state.value = 1

// Method 3: With remember
var count by remember { mutableStateOf(0) }  // Best practice
```

### Under the Hood:

```
State Object Structure:
┌─────────────────────────────┐
│ State<Int>                  │
├─────────────────────────────┤
│ value: Int                  │ ← Holds actual value
│                             │
│ notify observers()          │ ← Notifies on change
└─────────────────────────────┘

When value changes:
1. state.value = newValue
2. Internal notification triggered
3. Compose detects change
4. Affected composables recompose
5. New UI rendered
```

### Property Delegation:

```kotlin
// Without delegation:
val state = mutableStateOf(0)
println(state.value)  // Must use .value
state.value = 5

// With delegation:
var count by mutableStateOf(0)
println(count)  // Direct access!
count = 5  // Direct assignment!

// Delegation works like:
// "var count by X" means:
// - Read: return X.value
// - Write: set X.value
```

### Observable State:

```kotlin
var count by mutableStateOf(0)

Button(onClick = { count++ }) {  // Triggers recomposition
    Text("Count: $count")  // Automatically updated
}
```

### Example Reference:

📄 **Code Examples:** See `Topic3_StateManagementExamples.kt`

---

## State Hoisting

### Q4: What is State Hoisting?

**Answer:**
State Hoisting is moving state up to a parent composable to make child composables stateless and reusable.

### The Problem (Without Hoisting):

```kotlin
// WRONG - Stateful child component
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}

// Problems:
// ❌ Can't reuse in different contexts
// ❌ Can't synchronize state between instances
// ❌ Hard to test
// ❌ Can't compose with other logic
```

### The Solution (With Hoisting):

```kotlin
// RIGHT - Stateless child, state in parent
@Composable
fun Counter(count: Int, onCountChange: (Int) -> Unit) {
    Button(onClick = { onCountChange(count + 1) }) {
        Text("Count: $count")
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    
    Counter(
        count = count,
        onCountChange = { count = it }
    )
}

// Benefits:
// ✅ Reusable component
// ✅ Testable
// ✅ Composable
// ✅ Flexible
```

### State Hoisting Pattern:

```
Stateful Parent
└── Manages state
    └── Passes to stateless children
        ├── Child 1 (stateless)
        ├── Child 2 (stateless)
        └── Child 3 (stateless)

Each child only needs:
- Value to display
- Callback to notify changes
```

### Real-World Example:

```kotlin
// Stateless Input Field
@Composable
fun UserInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) }
    )
}

// Stateful Form
@Composable
fun UserForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    
    Column {
        UserInputField(
            value = name,
            onValueChange = { name = it },
            label = "Name"
        )
        
        UserInputField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        
        Button(onClick = { submitForm(name, email) }) {
            Text("Submit")
        }
    }
}
```

### Benefits of Hoisting:

✅ **Reusability** - Same component in different contexts  
✅ **Testability** - Easy to test with different values  
✅ **Composability** - Combine states easily  
✅ **Flexibility** - One source of truth for state  
✅ **Clarity** - Clear data flow  

### Rule of Thumb:

```
State should be at the lowest level where:
1. It's used
2. Multiple children need it
3. It can be shared

If only one child uses state → Keep it in that child
If multiple children need it → Move to parent
```

### Example Reference:

📄 **Code Examples:** See `Topic3_StateManagementExamples.kt`

---

## State Management Patterns

### Pattern 1: Simple State

```kotlin
@Composable
fun SimpleCounter() {
    var count by remember { mutableStateOf(0) }
    
    Column {
        Text("Count: $count")
        Button(onClick = { count++ }) { Text("Increment") }
    }
}
```

### Pattern 2: Hoisted State

```kotlin
@Composable
fun Parent() {
    var count by remember { mutableStateOf(0) }
    Child(count = count, onCountChange = { count = it })
}

@Composable
fun Child(count: Int, onCountChange: (Int) -> Unit) {
    Button(onClick = { onCountChange(count + 1) }) {
        Text("Count: $count")
    }
}
```

### Pattern 3: Multiple States

```kotlin
@Composable
fun MultiStateExample() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }
    var isSelected by remember { mutableStateOf(false) }
    
    // Use all three states
}
```

### Pattern 4: Persistent State

```kotlin
@Composable
fun PersistentForm() {
    var formData by rememberSaveable { 
        mutableStateOf(FormData())
    }
    
    // State survives rotation and process death
}

data class FormData(val name: String = "", val email: String = "")
```

---

## Common Mistakes

### ❌ Mistake 1: Creating State Without Remember

```kotlin
// WRONG
@Composable
fun Counter() {
    var count = 0
    Button(onClick = { count++ }) {
        Text("$count")  // Always shows 0
    }
}

// RIGHT
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("$count")  // Shows correct count
    }
}
```

### ❌ Mistake 2: Not Hoisting State When Needed

```kotlin
// WRONG - Can't sync state between instances
@Composable
fun ProfileCard1() {
    var isLiked by remember { mutableStateOf(false) }
    // ...
}

@Composable
fun ProfileCard2() {
    var isLiked by remember { mutableStateOf(false) }
    // Different instance!
}

// RIGHT - Hoist state to parent
@Composable
fun ProfileList() {
    var likes by remember { mutableStateOf(mapOf<Int, Boolean>()) }
    
    ProfileCard(id = 1, isLiked = likes[1] ?: false)
    ProfileCard(id = 2, isLiked = likes[2] ?: false)
}
```

### ❌ Mistake 3: Using remember Without mutableStateOf

```kotlin
// WRONG - Static value, won't recompose on change
@Composable
fun Example() {
    val items = remember { listOf(1, 2, 3) }
    Text("${items.size}")  // Never updates
}

// RIGHT - Use mutableStateOf for reactive data
@Composable
fun Example() {
    var items by remember { mutableStateOf(listOf(1, 2, 3)) }
    Text("${items.size}")  // Updates when items changes
}
```

---

## Interview Tips

### Common Questions:

**Q: When should you use remember vs rememberSaveable?**
- `remember`: Temporary UI state (visibility, focus, scroll)
- `rememberSaveable`: Data user cares about (form input, selections)

**Q: What happens if you don't use remember?**
- State is lost on every recomposition
- Counter resets, text disappears, selections clear
- App behaves unexpectedly

**Q: Explain state hoisting:**
- Move state to parent composable
- Pass state + callback to children
- Makes children stateless and reusable
- Enables state sharing

**Q: How does mutableStateOf trigger recomposition?**
- Notifies observers when value changes
- Compose detects change
- Only affected composables recompose
- UI updates automatically

---

## Summary Table

| Concept | Purpose | Persists | Use Case |
|---------|---------|----------|----------|
| `remember` | Cache across recomp | Recomposition only | Temporary state |
| `rememberSaveable` | Cache & persist | Rotation, process death | Important data |
| `mutableStateOf` | Create reactive state | Depends on container | Any reactive value |
| State Hoisting | Share state | Yes | Multi-component sync |

---

## Next Steps

1. ✅ Understand remember and mutableStateOf
2. ✅ Know when to use rememberSaveable
3. ✅ Master state hoisting
4. ✅ Learn composable functions properly (Topic 4)

**Next Topic:** [Topic 4: Composable Functions](./04_composable_functions.md)

