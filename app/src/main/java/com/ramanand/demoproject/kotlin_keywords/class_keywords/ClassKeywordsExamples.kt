package com.ramanand.demoproject.kotlin_keywords.class_keywords

/**
 * Kotlin Keywords - Class Keywords Package
 *
 * This package contains examples of all class-related keywords:
 * - class (class declaration)
 * - data (data class)
 * - interface (interface declaration)
 * - enum (enumeration)
 * - sealed (sealed class)
 * - object (singleton)
 * - companion (companion object)
 * - constructor (constructor declaration)
 * - init (initializer block)
 * - inner (inner class)
 * - abstract (abstract class)
 * - open (open for override)
 * - override (override method)
 */

// ============= CLASS - Class Declaration =============
/**
 * What: Declares a class
 * Why: Define objects with properties and methods
 * How: class ClassName { }
 */
class example_SimpleClass {
    var name: String = ""
    var age: Int = 0

    fun greet() {
        println("Hello, I'm $name")
    }
}

class example_ClassWithPrimaryConstructor(val name: String, val age: Int) {
    fun display() {
        println("$name is $age years old")
    }
}

// ============= DATA - Data Class =============
/**
 * What: Class optimized for data holding
 * Why: Automatically generates equals(), hashCode(), toString(), copy()
 * How: data class ClassName(val prop1, val prop2)
 */
data class User(val id: Int, val name: String, val email: String)

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val inStock: Boolean = true
)

fun example_DataClass() {
    val user1 = User(1, "Alice", "alice@example.com")
    val user2 = User(1, "Alice", "alice@example.com")

    // Automatic equals()
    println(user1 == user2)  // true

    // Automatic toString()
    println(user1)  // User(id=1, name=Alice, email=alice@example.com)

    // Automatic copy()
    val user3 = user1.copy(name = "Bob")
    println(user3)  // User(id=1, name=Bob, email=alice@example.com)
}

// ============= INTERFACE - Interface Declaration =============
/**
 * What: Declares an interface (contract)
 * Why: Define methods that classes must implement
 * How: interface InterfaceName { }
 */
interface Animal {
    fun speak()
    fun move()
}

interface Drawable {
    fun draw()
}

class Dog : Animal {
    override fun speak() {
        println("Woof!")
    }

    override fun move() {
        println("Running...")
    }
}

// Multiple interface implementation
class ShapeExample : Animal, Drawable {
    override fun speak() { println("Shape speaks") }
    override fun move() { println("Shape moves") }
    override fun draw() { println("Drawing shape") }
}

// ============= ENUM - Enumeration =============
/**
 * What: Declares an enumeration (set of constants)
 * Why: Type-safe way to represent fixed set of values
 * How: enum class Color { RED, GREEN, BLUE }
 */
enum class Color {
    RED, GREEN, BLUE, YELLOW
}

enum class Status(val code: Int, val message: String) {
    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    PENDING(3, "Pending"),
    ERROR(4, "Error")
}

enum class Direction {
    NORTH { override fun turnRight() = EAST },
    EAST { override fun turnRight() = SOUTH },
    SOUTH { override fun turnRight() = WEST },
    WEST { override fun turnRight() = NORTH };

    abstract fun turnRight(): Direction
}

fun example_Enum() {
    val color = Color.RED
    println("Color: $color")

    val status = Status.ACTIVE
    println("Status: ${status.message} (Code: ${status.code})")

    when (color) {
        Color.RED -> println("Red")
        Color.GREEN -> println("Green")
        else -> println("Other")
    }
}

// ============= SEALED - Sealed Class =============
/**
 * What: Class with restricted inheritance
 * Why: Represent restricted class hierarchies
 * How: sealed class Result
 */
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val exception: Exception) : Result()
    object Loading : Result()
}

fun example_Sealed(result: Result) {
    when (result) {
        is Result.Success -> println("Success: ${result.data}")
        is Result.Error -> println("Error: ${result.exception.message}")
        is Result.Loading -> println("Loading...")
    }
}

// ============= OBJECT - Singleton Object =============
/**
 * What: Declares a singleton object
 * Why: Single instance throughout app, thread-safe
 * How: object SingletonName { }
 */
object AppSettings {
    val apiUrl = "https://api.example.com"
    val timeout = 30000L

    fun getConfig() = "API: $apiUrl, Timeout: $timeout"
}

object DatabaseConnection {
    private val connection = "Connected to DB"

    fun query() = connection
}

fun example_Object() {
    println(AppSettings.getConfig())
    println(DatabaseConnection.query())
}

// ============= COMPANION - Companion Object =============
/**
 * What: Object inside class with static-like access
 * Why: Class-level members without instance
 * How: companion object { }
 */
class Logger {
    companion object {
        const val LOG_LEVEL = "DEBUG"

        fun log(message: String) {
            println("[$LOG_LEVEL] $message")
        }
    }
}

class Config {
    companion object {
        var apiUrl = "https://api.example.com"
        var timeout = 5000
    }
}

fun example_Companion() {
    Logger.log("App started")
    println("API URL: ${Config.apiUrl}")
}

// ============= CONSTRUCTOR - Constructor Declaration =============
/**
 * What: Declares class constructor
 * Why: Initialize instance when object is created
 * How: constructor(params) or primary constructor
 */
class Person(val name: String, val age: Int) {  // Primary constructor
    var email: String = ""

    constructor(name: String, age: Int, email: String) : this(name, age) {  // Secondary
        this.email = email
    }
}

class Car(val brand: String) {
    var model: String = ""

    init {
        println("Car created: $brand")
    }

    constructor(brand: String, model: String) : this(brand) {
        this.model = model
    }
}

// ============= INIT - Initializer Block =============
/**
 * What: Code block that runs when instance is created
 * Why: Run initialization logic
 * How: init { }
 */
class Account(val owner: String, var balance: Double = 0.0) {
    init {
        if (balance < 0) {
            throw IllegalArgumentException("Balance cannot be negative")
        }
        println("Account created for $owner")
    }

    init {
        println("Balance: $$balance")
    }
}

// ============= INNER - Inner Class =============
/**
 * What: Nested class that can access outer class members
 * Why: Access enclosing class instance
 * How: inner class InnerClass { }
 */
class Outer {
    val name = "Outer"

    inner class Inner {
        fun accessOuter() {
            println("Outer class: ${this@Outer.name}")
        }
    }
}

// ============= ABSTRACT - Abstract Class =============
/**
 * What: Declares abstract class or method
 * Why: Define interface that must be overridden
 * How: abstract class
 */
abstract class ShapeBase {
    abstract fun area(): Double

    open fun describe() {
        println("This is a shape")
    }
}

class Circle(val radius: Double) : ShapeBase() {
    override fun area() = kotlin.math.PI * radius * radius

    override fun describe() {
        super.describe()
        println("Circle with radius: $radius")
    }
}

// ============= OPEN - Open for Override =============
/**
 * What: Marks class or method as overridable
 * Why: Allow inheritance/override (classes are final by default)
 * How: open class
 */
open class Vehicle {
    open fun start() {
        println("Starting...")
    }
}

class Car2 : Vehicle() {
    override fun start() {
        super.start()
        println("Car engine started")
    }
}

// ============= OVERRIDE - Override Method =============
/**
 * What: Marks that function overrides parent's function
 * Why: Implement inherited abstract method
 * How: override fun methodName()
 */
open class Animal2 {
    open fun sound() = "Generic sound"
}

class Dog2 : Animal2() {
    override fun sound() = "Woof"
}

/**
 * Key Takeaways:
 * 1. class is default for creating objects
 * 2. data class for data holding
 * 3. interface for contracts
 * 4. enum for fixed set of values
 * 5. sealed for restricted inheritance
 * 6. object for singletons
 * 7. companion object for static-like members
 * 8. abstract for base classes with unimplemented methods
 * 9. open allows overriding (classes are final by default)
 * 10. override marks overridden methods
 */

