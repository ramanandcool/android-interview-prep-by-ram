package com.ramanand.demoproject.kotlin_keywords.variable_keywords

/**
 * Kotlin Keywords - Variable Keywords Package
 *
 * This package contains examples of all variable-related keywords:
 * - val (immutable variable)
 * - var (mutable variable)
 * - const (compile-time constant)
 * - lateinit (late initialization)
 */

// ============= VAL - Immutable Variable =============
/**
 * What: Declares an immutable (read-only) variable
 * Why: Prevents accidental modification, thread-safe, default choice
 * How: val variableName = value
 */
fun example_Val() {
    val name = "John"  // Immutable - cannot be changed
    val age = 25
    val isActive = true

    // WRONG - Cannot reassign val
    // name = "Jane"  // Compilation error: Val cannot be reassigned

    // ✅ CORRECT - Use val by default
    println("Name: $name, Age: $age, Active: $isActive")
}

// ============= VAR - Mutable Variable =============
/**
 * What: Declares a mutable (changeable) variable
 * Why: When value needs to change
 * How: var variableName = value
 */
fun example_Var() {
    var count = 0  // Mutable - can be changed
    count = 1      // OK
    count = 5      // OK

    var name = "Alice"
    name = "Bob"   // OK - reassignment allowed

    println("Count: $count, Name: $name")
}

// ============= CONST - Compile-Time Constant =============
/**
 * What: Declares a compile-time constant
 * Why: Zero runtime overhead, value is inlined
 * How: const val CONSTANT_NAME = value
 *
 * Important: Must be top-level or in object/companion object
 * Can only be primitive types or String
 */
object ConstExample {
    const val API_URL = "https://api.example.com"
    const val TIMEOUT_MS = 5000L
    const val VERSION = "1.0.0"
    const val MAX_RETRIES = 3
    const val PI = 3.14

    fun checkConstants() {
        println("API: $API_URL")
        println("Timeout: $TIMEOUT_MS ms")
        println("Version: $VERSION")
    }
}

// ============= LATEINIT - Late Initialization =============
/**
 * What: Declares a variable that will be initialized later
 * Why: Dependency injection, avoiding null checks
 * How: lateinit var property: Type
 *
 * Important: Cannot be used with nullable types or primitives
 * Must initialize before first use
 * Use ::property.isInitialized to check if initialized
 */
class LateinitExample {
    lateinit var name: String
    lateinit var email: String

    fun initialize(n: String, e: String) {
        name = n
        email = e
    }

    fun display() {
        if (::name.isInitialized) {
            println("Name: $name, Email: $email")
        } else {
            println("Not yet initialized")
        }
    }
}

// ============= Combination Examples =============
fun variableKeywordsCombined() {
    // Prefer val by default
    val immutableName = "John"
    val immutableAge = 25

    // Use var only when you need to reassign
    var counter = 0
    counter++

    // Use const for compile-time constants
    val appName = "MyApp"
    val buildVersion = "1.0"

    // Use lateinit for delayed initialization
    val person = object {
        lateinit var username: String

        fun login(name: String) {
            username = name
        }
    }
}

/**
 * Key Takeaways:
 * 1. Prefer val (immutable) by default
 * 2. Use var only when you need mutability
 * 3. Use const for compile-time constants (API URLs, version strings)
 * 4. Use lateinit for dependency injection and delayed initialization
 * 5. val is thread-safe, var requires synchronization in multi-threaded context
 */

