package com.ramanand.demoproject.interview_examples

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Topic 8: Interview Tips & Practice Exercises
 *
 * This file contains executable code for:
 * 1. Practice Exercise 1: Counter with State
 * 2. Practice Exercise 2: Form with Validation
 * 3. Practice Exercise 3: List Display
 * 4. Practice Exercise 4: API Data Fetching
 * 5. Practice Exercise 5: Multi-Screen Navigation
 *
 * Reference: docs/08_interview_tips.md
 */

// Exercise 1: Counter with State
@Composable
fun CounterExercise() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Counter App")
        Text("Count: $count")

        Button(
            onClick = { count++ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("+")
        }

        Button(
            onClick = { if (count > 0) count-- },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("-")
        }
    }
}

// Exercise 2: Form with Validation
@Composable
fun FormValidationExercise() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val isNameValid = name.isNotEmpty()
    val isEmailValid = email.contains("@") && email.contains(".")
    val isPhoneValid = phone.length >= 10
    val isFormValid = isNameValid && isEmailValid && isPhoneValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Registration Form")

        // Name field
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = name.isNotEmpty() && !isNameValid
        )
        if (name.isNotEmpty() && !isNameValid) {
            Text("Name required", color = androidx.compose.ui.graphics.Color.Red)
        }

        // Email field
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = email.isNotEmpty() && !isEmailValid
        )
        if (email.isNotEmpty() && !isEmailValid) {
            Text("Invalid email", color = androidx.compose.ui.graphics.Color.Red)
        }

        // Phone field
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = phone.isNotEmpty() && !isPhoneValid
        )
        if (phone.isNotEmpty() && !isPhoneValid) {
            Text("Phone must be 10+ digits", color = androidx.compose.ui.graphics.Color.Red)
        }

        // Submit button
        Button(
            onClick = { println("Form submitted: $name, $email, $phone") },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Submit")
        }
    }
}

// Exercise 3: List Display
@Composable
fun ListDisplayExercise() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    var selectedId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Item List")

        // Display list
        for (index in items.indices) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { selectedId = index }
            ) {
                Text(
                    text = items[index],
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Show selected item
        selectedId?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Selected Item: ${items[it]}")
                    Button(
                        onClick = { selectedId = null },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Deselect")
                    }
                }
            }
        }
    }
}

// Exercise 4: API Data Fetching
@Composable
fun DataFetchingExercise() {
    var data by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Data Fetching Example")

        when {
            loading -> {
                CircularProgressIndicator()
                Text("Loading...")
            }
            error != null -> {
                Text("Error: $error", color = androidx.compose.ui.graphics.Color.Red)
                Button(onClick = {
                    data = null
                    error = null
                }) {
                    Text("Retry")
                }
            }
            data != null -> {
                Text("Data: $data")
                Button(onClick = {
                    data = null
                    loading = false
                    error = null
                }) {
                    Text("Clear")
                }
            }
            else -> {
                Text("No data loaded")
                Button(onClick = {
                    loading = true
                    // Simulate API call
                    data = "Sample data from API"
                    loading = false
                }) {
                    Text("Fetch Data")
                }
            }
        }
    }
}

// Exercise 5: Multi-Screen Navigation (Simplified for example)
@Composable
fun NavigationExerciseHome(onNavigateToDetails: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen")
        Button(onClick = onNavigateToDetails) {
            Text("Go to Details")
        }
    }
}

@Composable
fun NavigationExerciseDetails(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Details Screen")
        Button(onClick = onNavigateBack) {
            Text("Back to Home")
        }
    }
}

@Composable
fun NavigationExerciseExample() {
    var showDetails by remember { mutableStateOf(false) }

    if (showDetails) {
        NavigationExerciseDetails(onNavigateBack = { showDetails = false })
    } else {
        NavigationExerciseHome(onNavigateToDetails = { showDetails = true })
    }
}

/**
 * Practice Exercises Summary:
 *
 * Exercise 1: Counter with State
 * - Learn: remember, mutableStateOf, state changes trigger recomposition
 * - Practice: Managing simple state
 *
 * Exercise 2: Form with Validation
 * - Learn: Multiple states, validation logic, conditional rendering
 * - Practice: Complex state management, user feedback
 *
 * Exercise 3: List Display
 * - Learn: Loops in composables, clickable modifier, item selection
 * - Practice: Interactive UI, user selection handling
 *
 * Exercise 4: API Data Fetching
 * - Learn: Loading states, error handling, state management
 * - Practice: Real-world patterns
 *
 * Exercise 5: Multi-Screen Navigation
 * - Learn: Screen switching, navigation state
 * - Practice: Multi-screen app structure
 *
 * Interview Tips:
 * 1. Understand WHY not just WHAT
 * 2. Know trade-offs between approaches
 * 3. Provide real examples from your experience
 * 4. Ask clarifying questions
 * 5. Think out loud during coding
 * 6. Be honest about what you don't know
 * 7. Show enthusiasm for the technology
 * 8. Ask good follow-up questions about the company/role
 */

