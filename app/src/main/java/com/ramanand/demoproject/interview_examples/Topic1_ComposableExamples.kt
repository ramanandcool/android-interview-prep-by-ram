package com.ramanand.demoproject.interview_examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Topic 1: Jetpack Compose Basics
 *
 * This file contains code examples for:
 * 1. What is Jetpack Compose
 * 2. Composable Functions
 * 3. Recomposition Concept
 *
 * Reference: docs/01_jetpack_compose_basics.md
 */

// Q1: What is Jetpack Compose?
// Answer: It's a declarative UI toolkit that allows building UIs with Kotlin functions

// Q2: What is a Composable function?
// Answer: A function decorated with @Composable that builds UI

@Composable
fun BasicComposable(name: String) {
    Text(text = "Hello, $name!")
}

// Q3: What is Recomposition?
// This composable will recompose when count changes
@Composable
fun RecompositionExample() {
    // Without remember - WRONG!
    var count = 0  // This will be reset on every recomposition
    Button(onClick = { count++ }) {
        Text("Count (Wrong): $count")  // Will always show 0
    }
}

// Better approach - using state
@Composable
fun ComposableWithState() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Basic Composable Example")
        BasicComposable("Kotlin")
    }
}

/**
 * Key Takeaways:
 * 1. Use @Composable annotation
 * 2. Recomposition is automatic when state changes
 * 3. Composables should be pure (no side effects)
 * 4. Use remember for state (see Topic 3)
 * 5. Composables are called frequently - keep them fast
 */

