package com.ramanand.demoproject.interview_examples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Topic 2: ComponentActivity & Lifecycle
 *
 * This file contains code examples for:
 * 1. ComponentActivity vs AppCompatActivity
 * 2. Activity Lifecycle
 * 3. EdgeToEdge Display
 *
 * Reference: docs/02_componentactivity_lifecycle.md
 */

/**
 * Q1: Why ComponentActivity over AppCompatActivity?
 *
 * Answer:
 * ✅ Lightweight - Minimal dependencies
 * ✅ Better Compose support - Built for Compose
 * ✅ Modern approach - No legacy code
 * ✅ Edge-to-edge ready - Easy system bar integration
 */
class Topic2ExampleActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Q3: enableEdgeToEdge()
        // Answer: Allows content to render behind system bars (status bar, nav bar)
        enableEdgeToEdge()

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Example App") })
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Topic2ExampleContent(modifier = Modifier.padding(innerPadding))
            }
        }
    }

    // Q2: Activity Lifecycle
    // Answer: Series of states an activity goes through
    override fun onStart() {
        super.onStart()
        // Activity becomes visible
    }

    override fun onResume() {
        super.onResume()
        // Activity is in focus - user can interact
    }

    override fun onPause() {
        super.onPause()
        // Activity loses focus
    }

    override fun onStop() {
        super.onStop()
        // Activity not visible
    }

    override fun onDestroy() {
        super.onDestroy()
        // Activity being destroyed - cleanup here
    }
}

@Composable
fun Topic2ExampleContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Component Activity Example")
        Text("With enableEdgeToEdge() enabled")
    }
}

/**
 * Key Takeaways:
 * 1. Use ComponentActivity for Compose apps
 * 2. Call enableEdgeToEdge() in onCreate()
 * 3. Use Scaffold for automatic padding handling
 * 4. Apply innerPadding to content to avoid overlap with AppBar
 * 5. Activity lifecycle methods run on main thread
 */

