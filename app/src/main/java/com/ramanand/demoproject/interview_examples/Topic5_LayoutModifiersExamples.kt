package com.ramanand.demoproject.interview_examples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Topic 5: Layout & Modifiers
 *
 * This file contains code examples for:
 * 1. Understanding Modifiers
 * 2. Modifier Chain & Order
 * 3. Layout Containers (Column, Row, Box)
 * 4. Size Modifiers
 * 5. Spacing Modifiers
 *
 * Reference: docs/05_layout_modifiers.md
 */

// Q1: What are Modifiers?
// Answer: Objects that customize the appearance and behavior of UI elements

@Composable
fun ModifierExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()           // Takes all available space
            .padding(16.dp)          // Internal spacing
            .background(Color.White) // Background color
            .border(2.dp, Color.Blue) // Border
    ) {
        Text(
            text = "Hello",
            modifier = Modifier
                .fillMaxWidth()      // Full width
                .height(60.dp)       // Fixed height
                .background(Color.Blue)
                .padding(16.dp)
                .clickable { println("Clicked!") }  // Handle clicks
        )
    }
}

// Q2: Does modifier order matter? YES!
@Composable
fun ModifierOrderMatters() {
    // Order 1: size() then padding()
    Box(
        modifier = Modifier
            .size(100.dp)      // 100x100 box
            .padding(16.dp)    // 16dp padding OUTSIDE
            .background(Color.Blue)
    ) {
        Text("Size First")
    }

    // Order 2: padding() then size()
    Box(
        modifier = Modifier
            .padding(16.dp)    // 16dp padding
            .size(100.dp)      // 100x100 box (constrained)
            .background(Color.Green)
    ) {
        Text("Padding First")
    }

    // Result: Different layouts!
}

// Q3: Column - Vertical Layout
@Composable
fun ColumnExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,  // Space between items
        horizontalAlignment = Alignment.CenterHorizontally  // Center horizontally
    ) {
        Text("Item 1")
        Text("Item 2")
        Text("Item 3")
    }
}

// Q3: Row - Horizontal Layout
@Composable
fun RowExample() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,  // Space between items
        verticalAlignment = Alignment.CenterVertically     // Center vertically
    ) {
        Text("Item 1")
        Text("Item 2")
        Text("Item 3")
    }
}

// Q3: Box - Stacked Layout
@Composable
fun BoxExample() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center  // Center content
    ) {
        Text("On Top", color = Color.White)
    }
}

// Q4: Size Modifiers - fillMaxSize() vs fillMaxWidth() vs wrapContentSize()
@Composable
fun SizeModifiersExample() {
    Column {
        // fillMaxSize() - Takes 100% width AND height
        Box(
            modifier = Modifier
                .fillMaxSize(0.3f)  // 30% of available space
                .background(Color.Red)
        ) {
            Text("fillMaxSize()", color = Color.White)
        }

        // fillMaxWidth() - Full width, wrap height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Green)
                .padding(8.dp)
        ) {
            Text("fillMaxWidth()")
        }

        // fillMaxHeight() - Full height, wrap width
        Box(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .width(100.dp)
                .background(Color.Blue)
                .padding(8.dp)
        ) {
            Text("fillMaxHeight()", color = Color.White)
        }

        // wrapContentSize() - Only needed space
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Yellow)
                .padding(8.dp)
        ) {
            Text("wrapContentSize()")
        }
    }
}

// Q5: Spacing - Padding and Arrangement
@Composable
fun SpacingExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Padding all sides
        Text(
            "All sides padding",
            modifier = Modifier
                .padding(16.dp)
                .background(Color.LightGray)
                .padding(8.dp)
        )

        // Different padding per side
        Text(
            "Different padding",
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .background(Color.LightGray)
                .padding(8.dp)
        )

        // Horizontal and vertical
        Text(
            "H/V padding",
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .background(Color.LightGray)
                .padding(8.dp)
        )
    }
}

// Q3 Continued: Your Code Example from MainActivity
@Composable
fun YourGreetingsExample(name: String, modifier: Modifier) {
    Column(
        Modifier
            .fillMaxSize()                          // Take all space
            .padding(20.dp),                        // Internal spacing
        verticalArrangement = Arrangement.Center,  // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally  // Center horizontally
    ) {
        Text(text = name, modifier = modifier)
        Button(onClick = {}) {
            Text(text = "Click me Button")
        }
    }
}

// Practical Example: Proper Modifier Usage
@Composable
fun ProperModifierUsage() {
    Column(
        modifier = Modifier
            .fillMaxSize()      // Layout - fill space
            .background(Color.White)  // Decoration
    ) {
        // Header with proper order
        Box(
            modifier = Modifier
                .fillMaxWidth()    // Layout
                .height(56.dp)     // Layout
                .background(Color.Blue)  // Decoration
                .padding(16.dp),   // Spacing
            contentAlignment = Alignment.CenterStart
        ) {
            Text("Header", color = Color.White)
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()  // Layout
                .padding(16.dp)  // Spacing
        ) {
            Text("Content goes here")
            Button(onClick = {}) {
                Text("Action")
            }
        }
    }
}

/**
 * Key Takeaways:
 * 1. Modifiers customize appearance and behavior
 * 2. Order MATTERS - Layout → Decoration → Interaction
 * 3. Column for vertical stacking
 * 4. Row for horizontal stacking
 * 5. Box for stacked/overlay layouts
 * 6. fillMaxSize() takes 100% of both dimensions
 * 7. fillMaxWidth() takes 100% width only
 * 8. wrapContentSize() takes only needed space
 * 9. Padding is internal spacing
 * 10. Arrangement controls spacing between children
 * 11. Alignment positions content
 */

