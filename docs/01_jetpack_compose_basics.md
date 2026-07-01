# Topic 1: Jetpack Compose Basics

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [What is Jetpack Compose](#what-is-jetpack-compose)
2. [Composable Functions](#composable-functions)
3. [Recomposition](#recomposition)

---

## What is Jetpack Compose

### Q1: What is Jetpack Compose and how does it differ from XML-based layouts?

**Answer:**
Jetpack Compose is a modern, declarative UI toolkit for Android development that allows you to build user interfaces using Kotlin functions instead of XML files.

### Key Differences:

| Feature | Jetpack Compose | XML Layouts |
|---------|-----------------|-------------|
| **Approach** | Declarative | Imperative |
| **Language** | Kotlin | XML |
| **Recomposition** | Automatic | Manual updates |
| **Flexibility** | Higher | Lower |
| **Performance** | Optimized | Good |
| **Learning Curve** | Moderate | Easy |
| **Code Reuse** | Functions | Complex |
| **Type Safety** | Full (Kotlin) | Partial (XML parsing) |

### Why Compose?

✅ **Reactive updates** - UI automatically updates when state changes  
✅ **Concise code** - Less boilerplate than XML  
✅ **Type-safe** - Everything is Kotlin code, compile-time checking  
✅ **Reusable** - Composable functions are composable and reusable  
✅ **Preview support** - Instant previews without running the app  
✅ **Modern tooling** - Better IDE support and debugging  

---

## Composable Functions

### Q2: What is a Composable function? How do you identify one?

**Answer:**
A Composable function is a Kotlin function annotated with `@Composable` that describes a piece of UI. It returns `Unit` and can be called from other composable functions.

### Characteristics:

- ✅ Annotated with `@Composable`
- ✅ Returns `Unit` (no return value)
- ✅ Can contain UI building logic
- ✅ Can recompose when state changes
- ✅ Should be pure (no side effects)
- ✅ Can accept parameters for data

### Structure of a Composable:

```
@Composable
fun MyComposable(param1: String, param2: Int) {
    // UI Building Logic Here
    // Can call other composables
    // Can use state
}
```

### Key Rules:

1. **No side effects in composable** - Use `LaunchedEffect` for side effects
2. **Idempotent** - Same inputs should always produce same output
3. **Fast execution** - Composables should be lightweight
4. **Can be reordered** - Compose may reorder execution
5. **Can be called frequently** - Don't do expensive operations here

### Example Reference:

📄 **Code Examples:** See `Topic1_ComposableExamples.kt`

---

## Recomposition

### Q3: What is Recomposition in Jetpack Compose?

**Answer:**
Recomposition is the process of calling composable functions again when their state or input changes. Compose automatically handles this smart invalidation.

### Key Points:

🔄 **Occurs when:**
- `@State` values change
- Input parameters change
- Parent composable recomposes

📊 **Smart invalidation:**
- Only affected composables are recomposed
- Not the entire screen
- Skips unchanged branches

⚡ **Performance:**
- Should be fast and frequent
- Can happen multiple times per second
- That's why side effects must be in LaunchedEffect

🎯 **Requirements:**
- Composables must be idempotent (same inputs = same output)
- No external side effects
- No random values in composable body

### Recomposition Lifecycle:

```
1. Initial Composition
   └─ Composable functions called
   
2. State Changes
   └─ Recomposition triggered
   
3. Smart Invalidation
   └─ Only affected composables re-execute
   
4. Updated Output
   └─ New UI rendered
```

### What Triggers Recomposition?

✅ State changes (`remember { mutableStateOf() }`)  
✅ Parameter changes  
✅ External events  
❌ Does NOT happen automatically just by rendering  

### Example Reference:

📄 **Code Examples:** See `Topic1_ComposableExamples.kt`

---

## Common Mistakes

### ❌ Mistake 1: Creating State Without `remember`

```kotlin
// WRONG - New state created on every recomposition
@Composable
fun BadCounter() {
    var count = 0  // Lost on every recomposition!
    Button(onClick = { count++ }) {
        Text("Count: $count")  // Always shows 0
    }
}

// RIGHT - State preserved with remember
@Composable
fun GoodCounter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("Count: $count")  // Shows correct value
    }
}
```

### ❌ Mistake 2: Side Effects in Composable

```kotlin
// WRONG - Logging runs on every recomposition
@Composable
fun BadExample() {
    println("Composable executed")  // Runs on every recomp!
    Text("Hello")
}

// RIGHT - Use LaunchedEffect for side effects
@Composable
fun GoodExample() {
    LaunchedEffect(Unit) {
        println("Runs only once")
    }
    Text("Hello")
}
```

### ❌ Mistake 3: Not Understanding Idempotency

```kotlin
// WRONG - Result depends on execution count
@Composable
fun RandomColor() {
    val color = Color((Math.random() * 0xFF).toLong().or(0xFF000000L))
    Text("Random", color = color)  // Color changes on every recomp!
}

// RIGHT - Use remember for computed values
@Composable
fun RememberedColor() {
    val color by remember {
        mutableStateOf(Color((Math.random() * 0xFF).toLong().or(0xFF000000L)))
    }
    Text("Stable", color = color)  // Color stays same
}
```

---

## Interview Tips for Topic 1

### Questions You Might Get:

**Q: Why use Compose over XML?**
- Type safety
- Less boilerplate
- Better reusability
- Reactive by default
- Easier testing

**Q: What happens if you create state without remember?**
- State is lost on every recomposition
- UI won't update properly
- Value resets constantly

**Q: Can composables have side effects?**
- No, they should be pure
- Use LaunchedEffect, SideEffect, or DisposableEffect
- Side effects run multiple times otherwise

**Q: What is idempotency?**
- Same inputs = same output, always
- No external dependencies
- No random values

---

## Summary

| Concept | What It Is | When to Use |
|---------|-----------|-----------|
| **Composable** | A function that builds UI | Always for UI |
| **Recomposition** | Re-executing composables | Automatically by Compose |
| **State** | Data that changes UI | Use `remember` |
| **Idempotent** | Predictable output | All composables must be |

---

## Next Steps

1. ✅ Understand what composables are
2. ✅ Know how recomposition works
3. ✅ Learn state management (Topic 3)
4. ✅ Handle side effects properly (Topic 4)

**Next Topic:** [Topic 2: ComponentActivity & Lifecycle](./02_componentactivity_lifecycle.md)

