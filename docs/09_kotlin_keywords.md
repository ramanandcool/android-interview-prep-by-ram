# Topic 9: Kotlin Keywords & Their Usage

**Quick Navigation:** [Back to Main](../README.md)

---

## Table of Contents
1. [Overview](#overview)
2. [Variable Keywords](#variable-keywords)
3. [Function Keywords](#function-keywords)
4. [Class Keywords](#class-keywords)
5. [Control Flow Keywords](#control-flow-keywords)
6. [Visibility Keywords](#visibility-keywords)
7. [Modifier Keywords](#modifier-keywords)
8. [Type Keywords](#type-keywords)
9. [Other Keywords](#other-keywords)

---

## Overview

This topic covers all **60+ Kotlin keywords** with detailed explanations, use cases, and code examples.

Each keyword has its own code file in the `kotlin_keywords/` package for easy reference.

---

## Variable Keywords

### `val` - Immutable Variable
**What:** Declares an immutable (read-only) variable  
**Why:** Prevents accidental modification, thread-safe  
**How:** `val variableName = value`

📄 **Code Examples:** See `KotlinKeyword_Val.kt`

```kotlin
val name = "John"  // Cannot be changed
// name = "Jane"  // ERROR: Val cannot be reassigned
```

---

### `var` - Mutable Variable
**What:** Declares a mutable (changeable) variable  
**Why:** Allows modification when needed  
**How:** `var variableName = value`

📄 **Code Examples:** See `KotlinKeyword_Var.kt`

```kotlin
var age = 25
age = 26  // OK - can be reassigned
```

---

### `const` - Compile-Time Constant
**What:** Declares a compile-time constant (must be top-level or in object)  
**Why:** Zero runtime overhead, inlined  
**How:** `const val CONSTANT_NAME = value`

📄 **Code Examples:** See `KotlinKeyword_Const.kt`

```kotlin
const val PI = 3.14
const val APP_VERSION = "1.0"
```

---

### `lateinit` - Late Initialization
**What:** Declares a variable that will be initialized later  
**Why:** Defer initialization, useful for dependency injection  
**How:** `lateinit var property`

📄 **Code Examples:** See `KotlinKeyword_Lateinit.kt`

```kotlin
lateinit var name: String
// Later...
name = "John"
```

---

## Function Keywords

### `fun` - Function Declaration
**What:** Declares a function  
**Why:** Define reusable code blocks  
**How:** `fun functionName(params): ReturnType { }`

📄 **Code Examples:** See `KotlinKeyword_Fun.kt`

```kotlin
fun greet(name: String): String {
    return "Hello, $name!"
}
```

---

### `suspend` - Coroutine Suspension
**What:** Marks a function as suspendable (can be paused)  
**Why:** For async operations without blocking  
**How:** `suspend fun functionName() { }`

📄 **Code Examples:** See `KotlinKeyword_Suspend.kt`

```kotlin
suspend fun fetchData(): String {
    delay(1000)  // Non-blocking delay
    return "Data"
}
```

---

### `inline` - Inline Function
**What:** Function whose code is inlined at call site  
**Why:** Reduces function call overhead, faster execution  
**How:** `inline fun functionName() { }`

📄 **Code Examples:** See `KotlinKeyword_Inline.kt`

```kotlin
inline fun measure(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("Time: ${System.currentTimeMillis() - start}")
}
```

---

### `noinline` - Skip Inlining Parameter
**What:** Marks lambda parameter that shouldn't be inlined  
**Why:** Use with inline functions when you need to store lambda  
**How:** Used with `inline fun` and `noinline` parameters

📄 **Code Examples:** See `KotlinKeyword_Noinline.kt`

```kotlin
inline fun execute(inlined: () -> Unit, noinline stored: () -> Unit) {
    // inlined is inlined, stored can be stored as reference
}
```

---

### `crossinline` - Crossinline Lambda
**What:** Marks lambda that can be called indirectly  
**Why:** Prevents `return` statements from exiting outer function  
**How:** `crossinline block: () -> Unit`

📄 **Code Examples:** See `KotlinKeyword_Crossinline.kt`

```kotlin
inline fun execute(crossinline block: () -> Unit) {
    object : Runnable {
        override fun run() {
            block()  // Can't use return here
        }
    }
}
```

---

### `return` - Return from Function
**What:** Returns a value from a function  
**Why:** Exit function with result  
**How:** `return value` or `return@label value`

📄 **Code Examples:** See `KotlinKeyword_Return.kt`

```kotlin
fun getValue(): Int {
    return 42
}
```

---

### `tailrec` - Tail Recursion Optimization
**What:** Marks recursive function for tail-call optimization  
**Why:** Prevents stack overflow in recursion  
**How:** `tailrec fun recursiveFunction()`

📄 **Code Examples:** See `KotlinKeyword_Tailrec.kt`

```kotlin
tailrec fun factorial(n: Int, acc: Int = 1): Int {
    return if (n <= 1) acc else factorial(n - 1, n * acc)
}
```

---

## Class Keywords

### `class` - Class Declaration
**What:** Declares a class  
**Why:** Define objects with properties and methods  
**How:** `class ClassName { }`

📄 **Code Examples:** See `KotlinKeyword_Class.kt`

```kotlin
class Person {
    var name = ""
}
```

---

### `data` - Data Class
**What:** Class optimized for data holding  
**Why:** Automatically generates equals(), hashCode(), toString(), copy()  
**How:** `data class ClassName(val prop1, val prop2)`

📄 **Code Examples:** See `KotlinKeyword_Data.kt`

```kotlin
data class User(val name: String, val email: String)
```

---

### `interface` - Interface Declaration
**What:** Declares an interface (contract)  
**Why:** Define methods that classes must implement  
**How:** `interface InterfaceName { }`

📄 **Code Examples:** See `KotlinKeyword_Interface.kt`

```kotlin
interface Animal {
    fun speak()
}

class Dog : Animal {
    override fun speak() { println("Woof!") }
}
```

---

### `enum` - Enumeration
**What:** Declares an enumeration (set of constants)  
**Why:** Type-safe way to represent fixed set of values  
**How:** `enum class Color { RED, GREEN, BLUE }`

📄 **Code Examples:** See `KotlinKeyword_Enum.kt`

```kotlin
enum class Status {
    ACTIVE, INACTIVE, PENDING
}
```

---

### `sealed` - Sealed Class
**What:** Class with restricted inheritance  
**Why:** Represent restricted class hierarchies  
**How:** `sealed class Result`

📄 **Code Examples:** See `KotlinKeyword_Sealed.kt`

```kotlin
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val exception: Exception) : Result()
}
```

---

### `object` - Object Declaration
**What:** Declares a singleton object  
**Why:** Single instance throughout app, thread-safe  
**How:** `object SingletonName { }`

📄 **Code Examples:** See `KotlinKeyword_Object.kt`

```kotlin
object AppSettings {
    val apiUrl = "https://api.example.com"
}
```

---

### `companion` - Companion Object
**What:** Object inside class with static-like access  
**Why:** Class-level members without instance  
**How:** `companion object { }`

📄 **Code Examples:** See `KotlinKeyword_Companion.kt`

```kotlin
class MyClass {
    companion object {
        const val CONSTANT = 42
    }
}

// Access: MyClass.CONSTANT
```

---

### `constructor` - Constructor Declaration
**What:** Declares class constructor  
**Why:** Initialize instance when object is created  
**How:** `constructor(params)` or primary constructor in class declaration

📄 **Code Examples:** See `KotlinKeyword_Constructor.kt`

```kotlin
class Person(val name: String) {  // Primary constructor
    constructor(name: String, age: Int) : this(name)  // Secondary
}
```

---

### `init` - Initializer Block
**What:** Code block that runs when instance is created  
**Why:** Run initialization logic  
**How:** `init { }`

📄 **Code Examples:** See `KotlinKeyword_Init.kt`

```kotlin
class Person(val name: String) {
    init {
        println("Person created: $name")
    }
}
```

---

### `inner` - Inner Class
**What:** Nested class that can access outer class members  
**Why:** Access enclosing class instance  
**How:** `inner class InnerClass { }`

📄 **Code Examples:** See `KotlinKeyword_Inner.kt`

```kotlin
class Outer {
    inner class Inner {
        fun accessOuter() { /* can access Outer members */ }
    }
}
```

---

### `abstract` - Abstract Declaration
**What:** Declares abstract class or method  
**Why:** Define interface that must be overridden  
**How:** `abstract class` or `abstract fun`

📄 **Code Examples:** See `KotlinKeyword_Abstract.kt`

```kotlin
abstract class Shape {
    abstract fun area(): Double
}
```

---

### `open` - Open for Override
**What:** Marks class or method as overridable (classes are final by default)  
**Why:** Allow inheritance/override  
**How:** `open class` or `open fun`

📄 **Code Examples:** See `KotlinKeyword_Open.kt`

```kotlin
open class Animal {
    open fun speak() { println("...") }
}

class Dog : Animal() {
    override fun speak() { println("Woof!") }
}
```

---

### `override` - Override Method
**What:** Marks that function overrides parent's function  
**Why:** Implement inherited abstract method  
**How:** `override fun methodName()`

📄 **Code Examples:** See `KotlinKeyword_Override.kt`

```kotlin
open class Animal {
    open fun sound(): String = "Generic sound"
}

class Dog : Animal() {
    override fun sound(): String = "Woof"
}
```

---

## Control Flow Keywords

### `if` - Conditional
**What:** Conditional statement  
**Why:** Execute code based on condition  
**How:** `if (condition) { } else { }`

📄 **Code Examples:** See `KotlinKeyword_If.kt`

```kotlin
if (age >= 18) {
    println("Adult")
} else {
    println("Minor")
}
```

---

### `when` - Pattern Matching
**What:** Switch-like statement with pattern matching  
**Why:** Multiple conditions more cleanly than if/else  
**How:** `when (value) { is Type -> ... }`

📄 **Code Examples:** See `KotlinKeyword_When.kt`

```kotlin
when (age) {
    0..12 -> println("Child")
    13..19 -> println("Teen")
    else -> println("Adult")
}
```

---

### `for` - Loop
**What:** Iterates over values  
**Why:** Repeat code multiple times  
**How:** `for (item in collection) { }`

📄 **Code Examples:** See `KotlinKeyword_For.kt`

```kotlin
for (i in 1..5) {
    println(i)
}
```

---

### `while` - While Loop
**What:** Repeats while condition is true  
**Why:** Repeat until condition becomes false  
**How:** `while (condition) { }`

📄 **Code Examples:** See `KotlinKeyword_While.kt`

```kotlin
var i = 0
while (i < 5) {
    println(i)
    i++
}
```

---

### `do` - Do-While Loop
**What:** Do-while loop (executes at least once)  
**Why:** Loop that checks condition after execution  
**How:** `do { } while (condition)`

📄 **Code Examples:** See `KotlinKeyword_Do.kt`

```kotlin
do {
    println(x)
    x++
} while (x < 5)
```

---

### `break` - Break Loop
**What:** Exits loop immediately  
**Why:** Stop looping  
**How:** `break`

📄 **Code Examples:** See `KotlinKeyword_Break.kt`

```kotlin
for (i in 1..10) {
    if (i == 5) break
    println(i)
}
```

---

### `continue` - Continue to Next Iteration
**What:** Skips current iteration, continues to next  
**Why:** Skip some iterations  
**How:** `continue`

📄 **Code Examples:** See `KotlinKeyword_Continue.kt`

```kotlin
for (i in 1..5) {
    if (i == 3) continue
    println(i)  // Prints 1, 2, 4, 5
}
```

---

### `try` - Try-Catch
**What:** Catches exceptions  
**Why:** Handle errors gracefully  
**How:** `try { } catch (e: Exception) { }`

📄 **Code Examples:** See `KotlinKeyword_Try.kt`

```kotlin
try {
    riskyOperation()
} catch (e: Exception) {
    println("Error: ${e.message}")
}
```

---

### `catch` - Catch Exception
**What:** Catches specific exception  
**Why:** Handle specific error type  
**How:** `catch (e: SpecificException) { }`

📄 **Code Examples:** See `KotlinKeyword_Catch.kt`

```kotlin
try {
    divide(10, 0)
} catch (e: ArithmeticException) {
    println("Cannot divide by zero")
}
```

---

### `finally` - Finally Block
**What:** Executes regardless of exception  
**Why:** Cleanup code that always runs  
**How:** `finally { }`

📄 **Code Examples:** See `KotlinKeyword_Finally.kt`

```kotlin
try {
    openFile()
} finally {
    closeFile()  // Always runs
}
```

---

### `throw` - Throw Exception
**What:** Throws an exception  
**Why:** Signal error condition  
**How:** `throw Exception("message")`

📄 **Code Examples:** See `KotlinKeyword_Throw.kt`

```kotlin
if (age < 0) {
    throw IllegalArgumentException("Age cannot be negative")
}
```

---

## Visibility Keywords

### `private` - Private Visibility
**What:** Only visible inside class/file  
**Why:** Hide implementation details  
**How:** `private val/fun/class`

📄 **Code Examples:** See `KotlinKeyword_Private.kt`

```kotlin
class MyClass {
    private val secret = "hidden"
}
// secret not accessible outside
```

---

### `protected` - Protected Visibility
**What:** Visible in class and subclasses  
**Why:** Allow inheritance while hiding from outside  
**How:** `protected val/fun`

📄 **Code Examples:** See `KotlinKeyword_Protected.kt`

```kotlin
open class Parent {
    protected val data = "protected"
}

class Child : Parent() {
    fun accessData() { println(data) }  // OK
}
```

---

### `internal` - Internal Visibility
**What:** Visible within same module  
**Why:** Hide from other modules  
**How:** `internal val/fun/class`

📄 **Code Examples:** See `KotlinKeyword_Internal.kt`

```kotlin
internal class HelperClass {
    // Visible within module only
}
```

---

### `public` - Public Visibility
**What:** Visible everywhere (default)  
**Why:** Part of public API  
**How:** `public val/fun/class` (usually omitted as default)

📄 **Code Examples:** See `KotlinKeyword_Public.kt`

```kotlin
public class MyClass {  // Or just: class MyClass
    public fun publicMethod() { }
}
```

---

## Modifier Keywords

### `abstract` - Abstract
Already covered in [Class Keywords](#class-keywords)

---

### `final` - Final (Non-overridable)
**What:** Marks class/method as non-overridable (default in Kotlin)  
**Why:** Prevent subclassing/overriding  
**How:** `final class` or `final fun`

📄 **Code Examples:** See `KotlinKeyword_Final.kt`

```kotlin
final class ImmutableClass {  // Cannot be subclassed
    final fun cannotOverride() { }
}
```

---

### `operator` - Operator Overloading
**What:** Overload operators like +, -, [], ()  
**Why:** Use operators with custom types  
**How:** `operator fun plus(...)`

📄 **Code Examples:** See `KotlinKeyword_Operator.kt`

```kotlin
data class Vector(val x: Int, val y: Int) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)
}

val v1 = Vector(1, 2)
val v2 = Vector(3, 4)
val v3 = v1 + v2  // Uses operator
```

---

### `infix` - Infix Function
**What:** Function that can be called without dot notation  
**Why:** More readable syntax  
**How:** `infix fun functionName(param)`

📄 **Code Examples:** See `KotlinKeyword_Infix.kt`

```kotlin
infix fun Int.plus(other: Int) = this + other

val result = 5 plus 3  // Instead of: 5.plus(3)
```

---

### `reified` - Reified Type Parameter
**What:** Access type parameter at runtime  
**Why:** Use type information in generic functions  
**How:** `inline fun <reified T> function()`

📄 **Code Examples:** See `KotlinKeyword_Reified.kt`

```kotlin
inline fun <reified T> parseJson(json: String): T {
    return when (T::class) {
        String::class -> json as T
        Int::class -> json.toInt() as T
        else -> throw Exception("Unsupported type")
    }
}
```

---

### `vararg` - Variable Arguments
**What:** Accept variable number of arguments  
**Why:** Flexible function parameters  
**How:** `vararg params: Type`

📄 **Code Examples:** See `KotlinKeyword_Vararg.kt`

```kotlin
fun sum(vararg numbers: Int): Int {
    return numbers.sum()
}

sum(1, 2, 3, 4, 5)  // Can pass any number of args
```

---

### `by` - Delegation
**What:** Delegate functionality to another object  
**Why:** Avoid code duplication, use composition  
**How:** `val/var prop by delegateObject` or `class X : Interface by impl`

📄 **Code Examples:** See `KotlinKeyword_By.kt`

```kotlin
interface Writer {
    fun write(text: String)
}

class PrintWriter : Writer {
    override fun write(text: String) { println(text) }
}

class Logger(writer: Writer) : Writer by writer  // Delegate to writer
```

---

### `get` - Getter
**What:** Custom getter for property  
**Why:** Compute value on access  
**How:** `val prop get() = computation`

📄 **Code Examples:** See `KotlinKeyword_Get.kt`

```kotlin
class Rectangle(val width: Int, val height: Int) {
    val area: Int
        get() = width * height
}
```

---

### `set` - Setter
**What:** Custom setter for property  
**Why:** Validate or react to value assignment  
**How:** `var prop get() = field; set(value) { ... }`

📄 **Code Examples:** See `KotlinKeyword_Set.kt`

```kotlin
var age: Int = 0
    set(value) {
        if (value >= 0) field = value
    }
```

---

## Type Keywords

### `is` - Type Check
**What:** Checks if expression is of given type  
**Why:** Safe type checking  
**How:** `if (obj is Type) { }`

📄 **Code Examples:** See `KotlinKeyword_Is.kt`

```kotlin
if (obj is String) {
    println("It's a string: $obj")
}
```

---

### `as` - Type Cast
**What:** Casts to specific type  
**Why:** Convert between types  
**How:** `obj as Type` or `obj as? Type` (safe)`

📄 **Code Examples:** See `KotlinKeyword_As.kt`

```kotlin
val obj: Any = "Hello"
val str: String = obj as String
val int: Int? = obj as? Int  // Safe cast, returns null if not Int
```

---

### `in` - Contains/Range Check
**What:** Checks if value is in collection/range  
**Why:** Membership testing  
**How:** `if (value in collection) { }`

📄 **Code Examples:** See `KotlinKeyword_In.kt`

```kotlin
if (5 in 1..10) {
    println("5 is in range")
}

if ("a" in listOf("a", "b", "c")) {
    println("a is in list")
}
```

---

### `out` - Covariant Type Parameter
**What:** Type parameter can only be produced (output)  
**Why:** Type safety in generics  
**How:** `List<out Type>`

📄 **Code Examples:** See `KotlinKeyword_Out.kt`

```kotlin
fun printList(list: List<out Number>) {
    for (item in list) {
        println(item)
    }
}

printList(listOf(1, 2, 3))
printList(listOf(1.5, 2.5))
```

---

### `where` - Generic Constraint
**What:** Constrains type parameters with multiple bounds  
**Why:** Specify requirements for generic types  
**How:** `fun <T> where T : Type1, T : Type2`

📄 **Code Examples:** See `KotlinKeyword_Where.kt`

```kotlin
interface A
interface B

fun <T> process(item: T) where T : A, T : B {
    // T must implement both A and B
}
```

---

### `typealias` - Type Alias
**What:** Creates alternative name for type  
**Why:** Simplify complex types  
**How:** `typealias NewName = ExistingType`

📄 **Code Examples:** See `KotlinKeyword_Typealias.kt`

```kotlin
typealias Predicate<T> = (T) -> Boolean

fun filter(items: List<Int>, predicate: Predicate<Int>) {
    items.filter(predicate)
}
```

---

## Other Keywords

### `this` - Current Instance
**What:** Reference to current object  
**Why:** Explicitly refer to instance  
**How:** `this.property` or `this@ClassName`

📄 **Code Examples:** See `KotlinKeyword_This.kt`

```kotlin
class Person(val name: String) {
    fun introduce() {
        println("I am ${this.name}")
    }
}
```

---

### `super` - Parent Class Reference
**What:** Reference to parent class  
**Why:** Call parent methods/properties  
**How:** `super.method()` or `super@ClassName.method()`

📄 **Code Examples:** See `KotlinKeyword_Super.kt`

```kotlin
open class Parent {
    open fun speak() { println("Parent speaks") }
}

class Child : Parent() {
    override fun speak() {
        super.speak()
        println("Child speaks")
    }
}
```

---

### `true` / `false` - Boolean Literals
**What:** Boolean values  
**Why:** Represent true/false  
**How:** `val flag = true`

📄 **Code Examples:** See `KotlinKeyword_Boolean.kt`

```kotlin
val isActive = true
val isDeleted = false
```

---

### `null` - Null Value
**What:** Represents absence of value  
**Why:** Optional values  
**How:** `val value: Type? = null`

📄 **Code Examples:** See `KotlinKeyword_Null.kt`

```kotlin
val nullable: String? = null
val nonNull: String = "value"
```

---

### `it` - Implicit Lambda Parameter
**What:** Implicit parameter in single-parameter lambdas  
**Why:** Shorter syntax  
**How:** `{ it -> ... }` or just `{ ... }`

📄 **Code Examples:** See `KotlinKeyword_It.kt`

```kotlin
val list = listOf(1, 2, 3)
list.forEach { println(it) }  // 'it' is the implicit parameter
```

---

### `as` - Label Prefix
**What:** Can be used with labels for breaking nested loops  
**Why:** Control complex loop flows  
**How:** `outer@ for (...) { inner@ for (...) { break@outer } }`

📄 **Code Examples:** See `KotlinKeyword_Label.kt`

```kotlin
outer@ for (i in 1..5) {
    for (j in 1..5) {
        if (i * j > 10) break@outer
        println("$i * $j = ${i * j}")
    }
}
```

---

### `package` - Package Declaration
**What:** Declares package for file  
**Why:** Organize code into namespaces  
**How:** `package com.example.app`

📄 **Code Examples:** See `KotlinKeyword_Package.kt`

```kotlin
package com.example.app

class MyClass { }
```

---

### `import` - Import Declaration
**What:** Imports class/function from another package  
**Why:** Use external code  
**How:** `import com.example.Class`

📄 **Code Examples:** See `KotlinKeyword_Import.kt`

```kotlin
import kotlin.collections.List
import java.util.Date
```

---

## Interview Tips for Keywords

### Questions You Might Get:

**Q1: What's the difference between val and var?**
- val: immutable (cannot be reassigned)
- var: mutable (can be reassigned)

**Q2: When would you use const val?**
- Compile-time constants that are inlined
- Must be top-level or in object
- More efficient than regular val

**Q3: What is lateinit used for?**
- Delay initialization
- Dependency injection
- Avoid null checking

**Q4: Explain sealed classes vs abstract classes**
- sealed: Restricted inheritance (subtypes known at compile-time)
- abstract: Open inheritance, can be extended anywhere

**Q5: What's the purpose of data classes?**
- Automatically generates equals(), hashCode(), toString(), copy()
- For holding data

---

## Summary Table

| Keyword | Category | Purpose |
|---------|----------|---------|
| val | Variable | Immutable variable |
| var | Variable | Mutable variable |
| const | Variable | Compile-time constant |
| fun | Function | Declare function |
| class | Class | Declare class |
| interface | Class | Declare interface |
| object | Class | Singleton |
| when | Control | Pattern matching |
| if/else | Control | Conditional |
| for | Control | Loop |
| try/catch | Control | Exception handling |
| private | Visibility | Hide from outside |
| public | Visibility | Visible everywhere |
| open | Modifier | Allow override |
| override | Modifier | Override method |
| is/as | Type | Type checking/casting |
| suspend | Modifier | Coroutine suspension |
| inline | Modifier | Inline function |

---

## Next Steps

1. ✅ Review each keyword in detail
2. ✅ Check code examples in kotlin_keywords/ package
3. ✅ Understand when to use each keyword
4. ✅ Practice writing code with keywords

**Previous Topic:** [Topic 8: Interview Tips & Resources](./08_interview_tips.md)

**You've completed all 9 topics!** 🎉

