package com.ramanand.demoproject.interview_examples

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * Topic 4: Composable Functions - Advanced
 *
 * This file contains code examples for:
 * 1. Side Effects in Composables
 * 2. LaunchedEffect
 * 3. SideEffect & DisposableEffect
 * 4. Experimental APIs
 *
 * Reference: docs/04_composable_functions.md
 */

// Q1: Can composables have side effects? How to handle them?
// Answer: NO - use LaunchedEffect, SideEffect, or DisposableEffect

// WRONG - Side effect in composable body
@Composable
fun BadSideEffect() {
    var count by remember { mutableStateOf(0) }

    // This runs on EVERY recomposition! WRONG!
    Log.d("TAG", "Recomposed with count: $count")

    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
    // Result: Hundreds of logs per second!
}

// Q2: LaunchedEffect - For async operations
// CORRECT - Using LaunchedEffect
@Composable
fun GoodSideEffect() {
    var count by remember { mutableStateOf(0) }

    LaunchedEffect(count) {
        // Runs only when count changes
        Log.d("TAG", "Count changed to: $count")
    }

    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}

// LaunchedEffect Example 1: Run once on enter
@Composable
fun InitializeOnce() {
    var data by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // Runs once when composable enters composition
        data = "Initialized"
    }

    Text(data)
}

// LaunchedEffect Example 2: Async operation with dependency
@Composable
fun FetchDataExample(userId: String) {
    var data by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        // Runs when userId changes
        loading = true
        try {
            // Simulate API call
            kotlinx.coroutines.delay(1000)
            data = "Data for $userId"
        } finally {
            loading = false
        }
    }

    Column {
        when {
            loading -> CircularProgressIndicator()
            data != null -> Text("Data: $data")
            else -> Text("No data")
        }
    }
}

// LaunchedEffect Example 3: With cleanup
@Composable
fun LaunchedEffectWithCleanup() {
    LaunchedEffect(Unit) {
        Log.d("TAG", "Setup: Starting")
        // Cleanup happens when effect is cancelled (try/finally)
        Log.d("TAG", "Cleanup: Stopped")
    }
}

// Q3: SideEffect - For non-suspendable side effects
@Composable
fun SideEffectExample() {
    var count by remember { mutableStateOf(0) }

    SideEffect {
        // Runs after every successful composition
        // Use for logging, analytics, etc.
        Log.d("TAG", "Analytics: Composed with count=$count")
    }

    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}

// Q4: @OptIn(ExperimentalMaterial3Api::class)
// Answer: Opt-in to experimental/unstable APIs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsingExperimentalAPI() {
    // Now we can use experimental Material3 APIs
    Text("Using experimental APIs")
    // Example: TopAppBar, NavigationBar, etc.
}

// Alternative: File-level OptIn
// Add at the top of file:
// @file:OptIn(ExperimentalMaterial3Api::class)

// Composable Function Rules
@Composable
fun PureFunction() {
    // ✅ GOOD - Pure function
    val items = listOf("A", "B", "C")
    Text(items.joinToString(","))
}

@Composable
fun ImpureFunction() {
    // ❌ WRONG - Not pure (depends on randomness)
    val random = Random.nextInt()  // Different every time!
    Text("Random: $random")

    // ✅ FIX: Use remember
    val randomFixed by remember { mutableStateOf(Random.nextInt()) }
    Text("Random: $randomFixed")  // Same every time
}

@Composable
fun Idempotent() {
    // ✅ GOOD - Idempotent (same inputs = same output)
    val result by remember { mutableStateOf("Same") }
    Text(result)
}

// Complex Example: Data fetching with error handling
@Composable
fun DataFetchingWithErrorHandling(id: String) {
    var data by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        loading = true
        error = null
        data = null

        try {
            // Simulate API call
            kotlinx.coroutines.delay(2000)
            if (Random.nextBoolean()) {
                data = "Data for $id"
            } else {
                throw Exception("API Error")
            }
        } catch (e: Exception) {
            error = e.message
        } finally {
            loading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            loading -> CircularProgressIndicator()
            error != null -> Text("Error: $error")
            data != null -> Text("Success: $data")
            else -> Text("No data")
        }
    }
}

/**
 * Key Takeaways:
 * 1. NEVER put side effects in composable body
 * 2. Use LaunchedEffect for async operations
 * 3. Use SideEffect for post-composition effects
 * 4. Use DisposableEffect for cleanup patterns
 * 5. Composables must be pure and idempotent
 * 6. Recomposition happens frequently - keep operations fast
 * 7. Use @OptIn cautiously - understand what you're opting into
 */

