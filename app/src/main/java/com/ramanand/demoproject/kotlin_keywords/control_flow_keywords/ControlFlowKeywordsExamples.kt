package com.ramanand.demoproject.kotlin_keywords.control_flow_keywords

/**
 * Kotlin Keywords - Control Flow Keywords Package
 *
 * This package contains examples of all control flow keywords:
 * - if (conditional)
 * - when (pattern matching)
 * - for (loop)
 * - while (loop)
 * - do (do-while loop)
 * - break (exit loop)
 * - continue (skip iteration)
 * - try (exception handling)
 * - catch (catch exception)
 * - finally (cleanup)
 * - throw (throw exception)
 */

// ============= IF - Conditional Statement =============
/**
 * What: Conditional statement
 * Why: Execute code based on condition
 * How: if (condition) { } else { }
 */
fun example_If(age: Int) {
    if (age >= 18) {
        println("You are an adult")
    } else {
        println("You are a minor")
    }
}

fun example_IfAsExpression(age: Int): String {
    return if (age >= 18) "Adult" else "Minor"
}

fun example_IfElseIf(score: Int) {
    if (score >= 90) {
        println("A")
    } else if (score >= 80) {
        println("B")
    } else if (score >= 70) {
        println("C")
    } else {
        println("F")
    }
}

// ============= WHEN - Pattern Matching =============
/**
 * What: Switch-like statement with pattern matching
 * Why: Multiple conditions more cleanly than if/else
 * How: when (value) { is Type -> ... }
 */
fun example_When(value: Int) {
    when (value) {
        1 -> println("One")
        2 -> println("Two")
        3 -> println("Three")
        else -> println("Other")
    }
}

fun example_WhenWithRange(age: Int) {
    when (age) {
        in 0..12 -> println("Child")
        in 13..19 -> println("Teen")
        in 20..59 -> println("Adult")
        else -> println("Senior")
    }
}

fun example_WhenWithType(obj: Any) {
    when (obj) {
        is String -> println("String: $obj")
        is Int -> println("Int: $obj")
        is Boolean -> println("Boolean: $obj")
        else -> println("Unknown type")
    }
}

fun example_WhenAsExpression(x: Int): String {
    return when {
        x < 0 -> "Negative"
        x == 0 -> "Zero"
        else -> "Positive"
    }
}

// ============= FOR - Loop =============
/**
 * What: Iterates over values
 * Why: Repeat code multiple times
 * How: for (item in collection) { }
 */
fun example_ForRange() {
    for (i in 1..5) {
        println(i)  // Prints 1, 2, 3, 4, 5
    }
}

fun example_ForList() {
    val list = listOf("A", "B", "C")
    for (item in list) {
        println(item)
    }
}

fun example_ForWithIndex() {
    val list = listOf("A", "B", "C")
    for ((index, value) in list.withIndex()) {
        println("$index: $value")
    }
}

fun example_ForDownTo() {
    for (i in 5 downTo 1) {
        println(i)  // Prints 5, 4, 3, 2, 1
    }
}

fun example_ForStep() {
    for (i in 0..10 step 2) {
        println(i)  // Prints 0, 2, 4, 6, 8, 10
    }
}

// ============= WHILE - While Loop =============
/**
 * What: Repeats while condition is true
 * Why: Repeat until condition becomes false
 * How: while (condition) { }
 */
fun example_While() {
    var i = 0
    while (i < 5) {
        println(i)
        i++
    }
}

fun example_WhileWithCondition() {
    var x = 10
    while (x > 0) {
        println("x = $x")
        x--
    }
}

// ============= DO - Do-While Loop =============
/**
 * What: Do-while loop (executes at least once)
 * Why: Loop that checks condition after execution
 * How: do { } while (condition)
 */
fun example_DoWhile() {
    var x = 0
    do {
        println(x)
        x++
    } while (x < 5)
}

fun example_DoWhileMenuExample() {
    var choice = 0
    do {
        println("Menu:")
        println("1. Option 1")
        println("2. Option 2")
        println("0. Exit")
        // choice = readLine()?.toIntOrNull() ?: 0
        // Runs at least once even if choice is 0
    } while (choice != 0)
}

// ============= BREAK - Break Loop =============
/**
 * What: Exits loop immediately
 * Why: Stop looping
 * How: break
 */
fun example_Break() {
    for (i in 1..10) {
        if (i == 5) break
        println(i)  // Prints 1, 2, 3, 4
    }
}

fun example_BreakNested() {
    outer@ for (i in 1..5) {
        for (j in 1..5) {
            if (i * j > 10) break@outer
            println("$i * $j = ${i * j}")
        }
    }
}

// ============= CONTINUE - Continue to Next Iteration =============
/**
 * What: Skips current iteration, continues to next
 * Why: Skip some iterations
 * How: continue
 */
fun example_Continue() {
    for (i in 1..5) {
        if (i == 3) continue
        println(i)  // Prints 1, 2, 4, 5 (skips 3)
    }
}

fun example_ContinueLabeled() {
    outer@ for (i in 1..3) {
        for (j in 1..3) {
            if (j == 2) continue@outer
            println("$i, $j")
        }
    }
}

// ============= TRY - Try-Catch =============
/**
 * What: Catches exceptions
 * Why: Handle errors gracefully
 * How: try { } catch (e: Exception) { }
 */
fun example_Try() {
    try {
        val result = 10 / 2  // No error
        println("Result: $result")
    } catch (e: ArithmeticException) {
        println("Division error: ${e.message}")
    }
}

fun example_TryCatchMultiple() {
    try {
        // risky operation
    } catch (e: IllegalArgumentException) {
        println("Illegal argument")
    } catch (e: NumberFormatException) {
        println("Number format error")
    } catch (e: Exception) {
        println("General exception")
    }
}

// ============= CATCH - Catch Exception =============
/**
 * What: Catches specific exception
 * Why: Handle specific error type
 * How: catch (e: SpecificException) { }
 */
fun example_CatchSpecific() {
    val str = "abc"
    try {
        str.toInt()  // Throws NumberFormatException
    } catch (e: NumberFormatException) {
        println("Not a number: ${e.message}")
    }
}

// ============= FINALLY - Finally Block =============
/**
 * What: Executes regardless of exception
 * Why: Cleanup code that always runs
 * How: finally { }
 */
fun example_Finally() {
    var file: String? = null
    try {
        file = "opened"
        println("Doing work with file")
        // throw Exception("Error occurred")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        file = null  // Always runs
        println("File closed")
    }
}

fun example_FinallyWithReturn() {
    fun test(): Int {
        return try {
            10 / 2
        } catch (e: Exception) {
            0
        } finally {
            println("Finally block always executes")
        }
    }
}

// ============= THROW - Throw Exception =============
/**
 * What: Throws an exception
 * Why: Signal error condition
 * How: throw Exception("message")
 */
fun example_Throw(age: Int) {
    if (age < 0) {
        throw IllegalArgumentException("Age cannot be negative")
    }
    println("Age: $age")
}

fun example_ThrowWithMessage(email: String) {
    if (!email.contains("@")) {
        throw IllegalArgumentException("Invalid email format")
    }
}

// ============= Control Flow Combinations =============
fun controlFlowCombined() {
    // If-else
    val num = 10
    if (num > 0) println("Positive")

    // When
    when (num) {
        10 -> println("Ten")
        else -> println("Other")
    }

    // For loop
    for (i in 1..3) {
        if (i == 2) continue
        println(i)
    }

    // While
    var x = 5
    while (x > 0) x--

    // Try-catch-finally
    try {
        val result = 5 / 1
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        println("Cleanup")
    }
}

/**
 * Key Takeaways:
 * 1. if: Simple conditional logic
 * 2. when: Complex pattern matching (prefer over if/else if/else)
 * 3. for: Iterate over collections
 * 4. while: Loop while condition is true
 * 5. do: Loop at least once
 * 6. break: Exit loop immediately
 * 7. continue: Skip to next iteration
 * 8. try/catch/finally: Exception handling
 * 9. throw: Signal error conditions
 * 10. Use labeled breaks/continues for nested loops
 */

