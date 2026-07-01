package com.ramanand.demoproject.interview_examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Topic 6: Material Design 3
 *
 * This file contains code examples for:
 * 1. Scaffold Component
 * 2. TopAppBar
 * 3. InnerPadding
 * 4. Material Design 3 Colors
 *
 * Reference: docs/06_material_design3.md
 */

// Q1: What is Scaffold?
// Answer: Layout that implements Material Design structure
// Provides: TopAppBar, FAB, BottomBar, main content with automatic padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBasicExample() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My App") })
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Main content here")
        }
    }
}

// Q1: Scaffold with all components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCompleteExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complete Scaffold") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, "Search")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, "More")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, "Add")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Content with TopAppBar and FAB")
        }
    }
}

// Q2: TopAppBar - App header
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBasic() {
    TopAppBar(
        title = { Text("Simple TopAppBar") }
    )
}

// Q2: TopAppBar with customization
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustomized() {
    TopAppBar(
        title = { Text("Customized") },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, "Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

// Q3: InnerPadding - IMPORTANT!
// Answer: Automatic padding from Scaffold accounting for AppBar height

// WRONG - Ignoring innerPadding
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithoutInnerPadding() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Top Bar") })
        }
    ) {
        // MISTAKE: Ignoring the innerPadding!
        Column(modifier = Modifier.fillMaxSize()) {
            Text("This might be hidden behind TopAppBar!")  // ❌
        }
    }
}

// RIGHT - Using innerPadding
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithInnerPadding() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Top Bar") })
        }
    ) { innerPadding ->  // ✅ Receive innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // ✅ Apply padding
        ) {
            Text("Content is now properly positioned!")
        }
    }
}

// Q4: Material Design 3 Colors
@Composable
fun MaterialColorsExample() {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val background = MaterialTheme.colorScheme.background
    val surface = MaterialTheme.colorScheme.surface
    val error = MaterialTheme.colorScheme.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Primary Color", color = primary)
                Text("Secondary Color", color = secondary)
                Text("Tertiary Color", color = tertiary)
                Text("Error Color", color = error)
            }
        }
    }
}

// Using themed colors in components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemedComponentsExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Themed App") },
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
                .padding(16.dp)
        ) {
            // Themed card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Card Title",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Card content",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Themed button
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("Themed Button")
            }
        }
    }
}

// Your MainActivity implementation (for reference)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourMainActivityImplementation() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) { Text("Top bar") }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello kotlin")
            Button(onClick = {}) {
                Text(text = "Click me Button")
            }
        }
    }
}

/**
 * Key Takeaways:
 * 1. Scaffold provides Material Design app structure
 * 2. Always use innerPadding to avoid overlap with AppBar
 * 3. TopAppBar can have title, navigation icon, and actions
 * 4. Material Design 3 colors are automatically themed
 * 5. Use MaterialTheme.colorScheme for consistent coloring
 * 6. "On" colors are for text/content on colored backgrounds
 * 7. Scaffold handles automatic spacing and layout
 * 8. FloatingActionButton is positioned automatically by Scaffold
 * 9. Multiple bars (top + bottom) work together with innerPadding
 * 10. Theme colors adapt to light/dark mode automatically
 */

