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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Topic 3: State Management
 *
 * This file contains code examples for:
 * 1. The remember() function
 * 2. rememberSaveable
 * 3. mutableStateOf
 * 4. State Hoisting
 *
 * Reference: docs/03_state_management.md
 */

// Q1: What is remember?
// Answer: A function that stores a value across recompositions

// WRONG - State is lost on every recomposition
@Composable
fun CounterWithoutRemember() {
    var count = 0  // Reset on every recomposition!

    Button(onClick = { count++ }) {
        Text("Count (WRONG): $count")  // Always shows 0
    }
}

// RIGHT - Using remember to preserve state
@Composable
fun CounterWithRemember() {
    var count by remember { mutableStateOf(0) }

    Button(onClick = { count++ }) {
        Text("Count (RIGHT): $count")  // Shows correct count
    }
}

// Q2: rememberSaveable - Survives rotation and process death
@Composable
fun CounterWithRememberSaveable() {
    var count by rememberSaveable { mutableStateOf(0) }

    Column {
        Text("Count: $count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
    // This count will be preserved even after device rotation!
}

// Q3: mutableStateOf creates a reactive state holder
@Composable
fun StateExample() {
    // Method 1: Using property delegation
    var name by remember { mutableStateOf("") }

    // Method 2: Direct state object
    val state = remember { mutableStateOf("") }

    Column {
        Text("Name: $name")
        Button(onClick = { name = "John" }) {
            Text("Set Name")
        }
    }
}

// Q4: State Hoisting - Move state to parent
// WRONG - Stateful child, can't reuse
@Composable
fun StatefulChild() {
    var isSelected by remember { mutableStateOf(false) }
    Button(onClick = { isSelected = !isSelected }) {
        Text(if (isSelected) "Selected" else "Not Selected")
    }
    // Problem: Can't reuse this with different parent states
}

// RIGHT - Stateless child, state in parent
@Composable
fun StatelessChild(
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    Button(onClick = { onSelectionChange(!isSelected) }) {
        Text(if (isSelected) "Selected" else "Not Selected")
    }
}

@Composable
fun ParentWithHoistedState() {
    var isSelected by remember { mutableStateOf(false) }

    Column {
        Text("Parent manages state")
        StatelessChild(
            isSelected = isSelected,
            onSelectionChange = { isSelected = it }
        )
    }
    // Now child is reusable and testable!
}

// Q5: Multiple states in one composable
@Composable
fun FormExample() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Name: $name")
        Button(onClick = { name = "John" }) {
            Text("Set Name")
        }

        Text("Email: $email")
        Button(onClick = { email = "john@example.com" }) {
            Text("Set Email")
        }

        Text("Age: $age")
        Button(onClick = { age = 25 }) {
            Text("Set Age")
        }
    }
}

/**
 * Key Takeaways:
 * 1. Use remember for temporary UI state
 * 2. Use rememberSaveable for data that should survive rotation
 * 3. mutableStateOf makes state reactive - triggers recomposition on change
 * 4. Hoist state to parent when multiple children need it
 * 5. Property delegation (by) makes state easier to work with
 * 6. All state should be hoisted to the lowest level where it's used
 */

