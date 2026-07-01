package com.ramanand.demoproject.interview_examples

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Topic 7: Best Practices
 *
 * This file contains code examples for:
 * 1. Writing Good Composables
 * 2. Preview Annotation
 * 3. User Interactions
 * 4. Navigation
 *
 * Reference: docs/07_best_practices.md
 */

// Q1: Best practices for composables

// WRONG - Too much logic in one composable
@Composable
fun CompleteScreenWrong() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column {
        TextField(value = name, onValueChange = { name = it })
        TextField(value = email, onValueChange = { email = it })
        TextField(value = phone, onValueChange = { phone = it })
        Button(onClick = {}) { Text("Submit") }
    }
    // More than needed logic in one place
}

// RIGHT - Break into small, reusable pieces
@Composable
fun CompleteScreenRight() {
    var formData by remember {
        mutableStateOf(FormData())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        NameField(
            value = formData.name,
            onValueChange = { formData = formData.copy(name = it) }
        )
        EmailField(
            value = formData.email,
            onValueChange = { formData = formData.copy(email = it) }
        )
        PhoneField(
            value = formData.phone,
            onValueChange = { formData = formData.copy(phone = it) }
        )
        SubmitButton(
            onClick = { println("Submitting: $formData") }
        )
    }
}

@Composable
fun NameField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun PhoneField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Phone") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Submit")
    }
}

data class FormData(
    val name: String = "",
    val email: String = "",
    val phone: String = ""
)

// Q2: Preview annotation
@Composable
fun GreetingScreen(name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello, $name!")
        Button(onClick = {}) {
            Text("Click me")
        }
    }
}

// Simple preview
@Composable
fun GreetingScreenSimplePreview() {
    GreetingScreen("World")
}

// Preview with custom settings
@Composable
fun GreetingScreenPreviewLight() {
    GreetingScreen("Alice")
}

@Composable
fun GreetingScreenPreviewDark() {
    GreetingScreen("Bob")
}

// Q3: User interactions
@Composable
fun UserInteractionsExample() {
    var text by remember { mutableStateOf("") }
    var clicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Text input
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Text("You entered: $text")

        // Click handling
        Button(
            onClick = { clicked = !clicked },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(if (clicked) "Clicked!" else "Click me")
        }

        if (clicked) {
            Card(modifier = Modifier.padding(16.dp)) {
                Text("You clicked the button!", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

// Q3: Form validation example
@Composable
fun FormValidationExample() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = name.isEmpty()
        )
        if (name.isEmpty()) {
            Text("Name is required", color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = !email.contains("@")
        )
        if (email.isNotEmpty() && !email.contains("@")) {
            Text("Invalid email", color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(
            onClick = { println("Form submitted") },
            enabled = name.isNotEmpty() && "@" in email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Submit")
        }
    }
}

// Q4: Navigation setup
@Composable
fun NavigationExampleHome() {
    Column {
        Text("Home Screen")
        Button(onClick = {}) {
            Text("Go to Details")
        }
    }
}

@Composable
fun NavigationExampleDetails(itemId: String) {
    Column {
        Text("Details for Item: $itemId")
        Button(onClick = {}) {
            Text("Back")
        }
    }
}

// Good composable - Small and focused
@Composable
fun UserCard(name: String, email: String) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Name: $name")
            Text("Email: $email")
        }
    }
}

// Good composable - Reusable
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text)
    }
}

// Good composable - With state hoisting
@Composable
fun ParentWithStateHoisting() {
    var count by remember { mutableStateOf(0) }

    Column {
        CounterDisplay(count)
        CounterButton(
            onClick = { count++ },
            text = "Increment"
        )
    }
}

@Composable
fun CounterDisplay(count: Int) {
    Text("Count: $count")
}

@Composable
fun CounterButton(onClick: () -> Unit, text: String) {
    Button(onClick = onClick) {
        Text(text)
    }
}

/**
 * Key Takeaways:
 * 1. Keep composables small and focused
 * 2. Use meaningful names for clarity
 * 3. Make composables reusable with parameters
 * 4. Hoist state when multiple children need it
 * 5. Use Preview annotation for quick feedback
 * 6. Handle interactions with callbacks
 * 7. Validate user input
 * 8. Break complex UIs into smaller composables
 * 9. One responsibility per composable
 * 10. Test with previews in different states
 * 11. Document complex logic with comments
 * 12. Avoid hard-coded values
 */

