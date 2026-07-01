@file:Suppress("FunctionName", "unused","AllWarnings")

package com.ramanand.demoproject.kotlin_keywords.type_and_other_keywords

/**
 * Kotlin Keywords - Type and Other Keywords Package
 *
 * This package contains examples of type and other keywords:
 * - is (type check)
 * - as (type cast)
 * - in (contains/range check)
 * - out (covariant type parameter)
 * - where (generic constraint)
 * - typealias (type alias)
 * - this (current instance)
 * - super (parent class reference)
 * - true/false (boolean literals)
 * - null (null value)
 * - it (implicit lambda parameter)
 * - package/import (module organization)
 */

// ============= IS - Type Check =============
/**
 * What: Checks if expression is of given type
 * Why: Safe type checking before casting
 * How: if (obj is Type) { }
 */
fun example_Is(obj: Any) {
    if (obj is String) {
        println("It's a string: $obj")
    } else if (obj is Int) {
        println("It's an int: $obj")
    } else if (obj is List<*>) {
        println("It's a list")
    }
}

fun example_IsPattern(value: Any): String {
    return when {
        value is String -> "String: $value"
        value is Int -> "Int: $value"
        value is Double -> "Double: $value"
        else -> "Unknown"
    }
}

// ============= AS - Type Cast =============
/**
 * What: Casts to specific type (unsafe)
 * Why: Convert between types
 * How: obj as Type (unsafe) or obj as? Type (safe)
 */
fun example_AsCast() {
    val obj: Any = "Hello"
    val str: String = obj as String  // Unsafe cast

    val num: Int? = obj as? Int  // Safe cast - returns null if not Int
}

fun example_AsSafe() {
    val obj: Any = 42
    val str: String? = obj as? String  // null
    val int: Int? = obj as? Int        // 42

    println("String: $str")  // null
    println("Int: $int")     // 42
}

// ============= IN - Contains/Range Check =============
/**
 * What: Checks if value is in collection/range
 * Why: Membership testing
 * How: if (value in collection) { }
 */
fun example_InRange() {
    if (5 in 1..10) {
        println("5 is in range 1..10")
    }
}

fun example_InList() {
    val list = listOf("a", "b", "c")
    if ("a" in list) {
        println("'a' is in list")
    }
}

fun example_InNegation() {
    if (15 !in 1..10) {
        println("15 is not in range")
    }
}

// ============= OUT - Covariant Type Parameter =============
/**
 * What: Type parameter can only be produced (output)
 * Why: Type safety in generics - producer
 * How: List<out Type>
 */
@Suppress("REDUNDANT_PROJECTION")
fun printList(list: List<out Number>) {
    for (item in list) {
        println(item)
    }
}

fun example_Out() {
    val ints: List<Int> = listOf(1, 2, 3)
    val doubles: List<Double> = listOf(1.5, 2.5)

    printList(ints)      // OK - Int is Number
    printList(doubles)   // OK - Double is Number
}

// ============= WHERE - Generic Constraint =============
/**
 * What: Constrains type parameters with multiple bounds
 * Why: Specify requirements for generic types
 * How: fun <T> where T : Type1, T : Type2
 */
interface Comparable
interface Serializable

fun <T> process(item: T) where T : Comparable, T : Serializable {
    // T must implement both interfaces
}

// ============= TYPEALIAS - Type Alias =============
/**
 * What: Creates alternative name for type
 * Why: Simplify complex types
 * How: typealias NewName = ExistingType
 */
typealias StringList = List<String>
typealias IntPredicate = (Int) -> Boolean
typealias Callback<T> = (T) -> Unit

fun filterInts(list: List<Int>, predicate: IntPredicate) {
    list.filter(predicate)
}

fun example_Typealias() {
    val names: StringList = listOf("Alice", "Bob")
    val condition: IntPredicate = { it > 5 }
    val callback: Callback<String> = { println(it) }
}

// ============= THIS - Current Instance =============
/**
 * What: Reference to current object
 * Why: Explicitly refer to instance
 * How: this.property or this@ClassName
 */
class Person(val name: String) {
    fun introduce() {
        println("I am ${this.name}")
    }
}

class Outer {
    val value = "Outer"

    inner class Inner {
        val value = "Inner"

        fun display() {
            println("Inner: ${this.value}")     // Inner's value
            println("Outer: ${this@Outer.value}")  // Outer's value
        }
    }
}

// ============= SUPER - Parent Class Reference =============
/**
 * What: Reference to parent class
 * Why: Call parent methods/properties
 * How: super.method() or super@ClassName.method()
 */
open class Parent {
    open fun speak() {
        println("Parent speaks")
    }
}

class Child : Parent() {
    override fun speak() {
        super.speak()  // Call parent's method
        println("Child speaks")
    }
}

// ============= TRUE/FALSE - Boolean Literals =============
/**
 * What: Boolean values
 * Why: Represent true/false
 * How: val flag = true
 */
fun example_Boolean() {
    val isActive = true
    val isDeleted = false

    if (isActive && !isDeleted) {
        println("Record is active")
    }
}

// ============= NULL - Null Value =============
/**
 * What: Represents absence of value
 * Why: Optional values
 * How: val value: Type? = null
 */
fun example_Null() {
    val nullable: String? = null
    val nonNull: String = "value"

    // Safe call
    println(nullable?.length)  // null

    // Elvis operator
    @Suppress("UnusedVariable")
    val length = nullable?.length ?: 0

    // Not null assertion (use carefully)
    // val value = nullable!!
}

// ============= IT - Implicit Lambda Parameter =============
/**
 * What: Implicit parameter in single-parameter lambdas
 * Why: Shorter, more readable syntax
 * How: { it -> ... } or just { ... }
 */
fun example_It() {
    val list = listOf(1, 2, 3, 4, 5)

    // Without 'it' - explicit parameter
    list.filter({ n -> n > 2 })

    // With 'it' - implicit parameter
    list.filter { it > 2 }

    // Using 'it' with forEach
    list.forEach { println(it) }

    // Using 'it' with map
    list.map { it * 2 }
}

// ============= PACKAGE - Package Declaration =============
/**
 * What: Declares package for file
 * Why: Organize code into namespaces
 * How: package com.example.app
 */
// This file's package is: com.ramanand.demoproject.kotlin_keywords.type_and_other_keywords

fun example_PackageUsage() {
    println("This code is in kotlin_keywords package")
}

// ============= IMPORT - Import Declaration =============
/**
 * What: Imports class/function from another package
 * Why: Use external code
 * How: import com.example.Class
 */
// Examples:
// import java.util.Date
// import kotlin.collections.List
// import kotlin.io.println

// ============= COMBINATIONS AND ADVANCED EXAMPLES =============

// Example: Complex type checking and casting
fun processAny(obj: Any) {
    when {
        obj is String -> println("String length: ${obj.length}")
        obj is Int -> println("Int value: $obj")
        obj is List<*> -> println("List size: ${obj.size}")
        else -> println("Unknown type")
    }
}

// Example: Using typealias with generics
typealias ErrorHandler<T> = (T) -> Unit

fun handleErrors(error: Exception, handler: ErrorHandler<Exception>) {
    handler(error)
}

// Example: Safe casting with null coalescing
fun getSafeString(obj: Any?): String {
    return (obj as? String) ?: "Not a string"
}

// Example: Type bounds with where clause
interface Drawable {
    fun draw()
}

interface Saveable {
    fun save()
}

fun <T> processGraphic(item: T) where T : Drawable, T : Saveable {
    item.draw()
    item.save()
}

/**
 * Key Takeaways:
 * 1. is: Type checking (always safe)
 * 2. as: Type casting (unsafe - can throw)
 * 3. as?: Safe type casting (returns null)
 * 4. in: Check membership in collection/range
 * 5. out: Covariance for producers
 * 6. where: Multiple type bounds
 * 7. typealias: Simplify complex types
 * 8. this: Current instance reference
 * 9. super: Parent class reference
 * 10. true/false: Boolean literals
 * 11. null: Nullable values
 * 12. it: Implicit lambda parameter
 * 13. package/import: Code organization
 */

