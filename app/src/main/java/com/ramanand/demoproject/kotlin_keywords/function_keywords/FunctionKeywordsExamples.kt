package com.ramanand.demoproject.kotlin_keywords.function_keywords

import kotlin.system.measureTimeMillis

/**
 * Kotlin Keywords - Function Keywords Package
 *
 * This package contains examples of all function-related keywords:
 * - fun (function declaration)
 * - suspend (coroutine suspension)
 * - inline (inline function)
 * - noinline (skip inlining)
 * - crossinline (crossinline lambda)
 * - return (return from function)
 * - tailrec (tail recursion optimization)
 */

// ============= FUN - Function Declaration =============
/**
 * What: Declares a function
 * Why: Define reusable code blocks
 * How: fun functionName(params): ReturnType { }
 */
fun example_BasicFunction(): String {
    return "Hello, World!"
}

fun example_FunctionWithParams(name: String, age: Int): String {
    return "$name is $age years old"
}

fun example_FunctionWithDefault(name: String = "Guest", greeting: String = "Hello") {
    println("$greeting, $name!")
}

fun example_ExpressionFunction(a: Int, b: Int) = a + b

fun example_VoidFunction() {
    println("This function returns Unit (void)")
}

// ============= SUSPEND - Coroutine Suspension =============
/**
 * What: Marks a function as suspendable (can be paused)
 * Why: For async operations without blocking
 * How: suspend fun functionName() { }
 *
 * Important: Can only be called from other suspend functions or coroutines
 */
suspend fun example_FetchData(id: Int): String {
    // Simulate async operation
    kotlinx.coroutines.delay(1000)
    return "Data for ID: $id"
}

suspend fun example_ChainedSuspend() {
    val data1 = example_FetchData(1)
    val data2 = example_FetchData(2)
    println("$data1, $data2")
}

// ============= INLINE - Inline Function =============
/**
 * What: Function whose code is inlined at call site
 * Why: Reduces function call overhead, faster execution
 * How: inline fun functionName() { }
 *
 * Important: Used often with lambdas
 * Code is literally copied to call site
 */
inline fun example_InlineFunction(block: () -> Unit) {
    println("Before")
    block()
    println("After")
}

inline fun <T> example_InlineWithGeneric(items: List<T>, filter: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in items) {
        if (filter(item)) result.add(item)
    }
    return result
}

inline fun example_Measure(name: String, block: () -> Unit) {
    val time = measureTimeMillis {
        block()
    }
    println("$name took ${time}ms")
}

// ============= NOINLINE - Skip Inlining =============
/**
 * What: Marks lambda parameter that shouldn't be inlined
 * Why: When you need to store lambda as reference
 * How: Used with inline fun
 */
inline fun example_InlineWithNoinline(
    inlinedBlock: () -> Unit,
    noinline storedBlock: () -> Unit
) {
    inlinedBlock()  // This is inlined
    // storedBlock can be stored as reference
    val stored = storedBlock
    stored()
}

// ============= CROSSINLINE - Crossinline Lambda =============
/**
 * What: Marks lambda that can be called indirectly
 * Why: Prevents 'return' from exiting outer function
 * How: crossinline block: () -> Unit
 */
inline fun example_Crossinline(crossinline block: () -> Unit) {
    val runnable = object : Runnable {
        override fun run() {
            block()  // Can't use 'return' here - that's why crossinline needed
        }
    }
}

// ============= RETURN - Return from Function =============
/**
 * What: Returns a value from a function
 * Why: Exit function with result
 * How: return value
 */
fun example_ReturnBasic(): Int {
    return 42
}

fun example_ReturnWithLabel(): String {
    val list = listOf(1, 2, 3, 4, 5)
    list.forEach { value ->
        if (value == 3) return@forEach  // Return from forEach
    }
    return "Completed"
}

fun example_ReturnOnMatch(value: Int): String {
    return when {
        value < 0 -> "Negative"
        value == 0 -> "Zero"
        value > 0 -> "Positive"
        else -> "Unknown"
    }
}

// ============= TAILREC - Tail Recursion Optimization =============
/**
 * What: Marks recursive function for tail-call optimization
 * Why: Prevents stack overflow in recursion
 * How: tailrec fun recursiveFunction()
 *
 * Important: Must have recursive call as last statement
 */
tailrec fun example_FactorialTailrec(n: Int, acc: Int = 1): Int {
    return if (n <= 1) acc else example_FactorialTailrec(n - 1, n * acc)
}

tailrec fun example_SumTailrec(n: Int, acc: Int = 0): Int {
    return if (n <= 0) acc else example_SumTailrec(n - 1, acc + n)
}

tailrec fun example_CountdownTailrec(n: Int) {
    if (n > 0) {
        println(n)
        example_CountdownTailrec(n - 1)  // Tail call
    }
}

// Without tailrec - stack overflow for large n
fun example_FactorialWithoutTailrec(n: Int): Int {
    return if (n <= 1) 1 else n * example_FactorialWithoutTailrec(n - 1)
}

// ============= Function Combinations =============
fun functionKeywordsCombined() {
    // Regular function
    println(example_BasicFunction())

    // Function with parameters
    println(example_FunctionWithParams("Alice", 30))

    // Function with defaults
    example_FunctionWithDefault()
    example_FunctionWithDefault("Bob")

    // Expression function
    val sum = example_ExpressionFunction(5, 3)
    println("Sum: $sum")

    // Inline function with lambda
    example_InlineFunction {
        println("This is the block")
    }

    // Tail recursion
    val factorial = example_FactorialTailrec(5)
    println("5! = $factorial")

    val sum1to5 = example_SumTailrec(5)
    println("Sum 1..5 = $sum1to5")
}

/**
 * Key Takeaways:
 * 1. fun is used for all function declarations
 * 2. suspend for async operations
 * 3. inline for performance-critical lambdas
 * 4. tailrec prevents stack overflow in recursion
 * 5. return exits function immediately
 * 6. noinline/crossinline used with inline functions for control
 */

