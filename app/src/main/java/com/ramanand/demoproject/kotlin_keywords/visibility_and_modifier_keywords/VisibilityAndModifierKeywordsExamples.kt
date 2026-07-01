package com.ramanand.demoproject.kotlin_keywords.visibility_and_modifier_keywords

/**
 * Kotlin Keywords - Visibility and Modifier Keywords Package
 *
 * This package contains examples of visibility and modifier keywords:
 * - private (private visibility)
 * - protected (protected visibility)
 * - internal (internal visibility)
 * - public (public visibility - default)
 * - abstract (abstract)
 * - final (final - default)
 * - operator (operator overloading)
 * - infix (infix function)
 * - reified (reified type parameter)
 * - vararg (variable arguments)
 * - by (delegation)
 * - get/set (property getter/setter)
 */

// ============= PRIVATE - Private Visibility =============
/**
 * What: Only visible inside class/file
 * Why: Hide implementation details
 * How: private val/fun/class
 */
class PrivateExample {
    private val secret = "hidden"
    private var internalCounter = 0

    private fun internalMethod() {
        println("This is private")
    }

    fun publicMethod() {
        println("Can access: $secret")  // OK - same class
        internalMethod()  // OK
    }
}

// secret, internalMethod NOT accessible outside

// ============= PROTECTED - Protected Visibility =============
/**
 * What: Visible in class and subclasses
 * Why: Allow inheritance while hiding from outside
 * How: protected val/fun
 */
open class ParentClass {
    protected val protectedData = "protected"
    protected fun protectedMethod() {
        println("Protected method")
    }
}

class ChildClass : ParentClass() {
    fun accessProtected() {
        println(protectedData)  // OK - subclass
        protectedMethod()  // OK
    }
}

// protectedData NOT accessible outside

// ============= INTERNAL - Internal Visibility =============
/**
 * What: Visible within same module
 * Why: Hide from other modules
 * How: internal val/fun/class
 */
internal class HelperClass {
    internal fun helperFunction() {
        println("Helper")
    }
}

// Visible within this module, hidden from other modules

// ============= PUBLIC - Public Visibility =============
/**
 * What: Visible everywhere (default)
 * Why: Part of public API
 * How: public val/fun/class (usually omitted as default)
 */
public class PublicClass {
    public fun publicMethod() {
        println("Public")
    }
}

// Or simply:
class SimpleClass {
    fun simpleMethod() {
        println("Also public - default")
    }
}

// ============= ABSTRACT - Abstract =============
/**
 * What: Declares abstract class or method
 * Why: Define interface that must be overridden
 * How: abstract class / abstract fun
 */
abstract class AbstractShape {
    abstract fun getArea(): Double

    abstract val name: String

    open fun describe() {
        println("I am a shape: $name")
    }
}

class Circle(val radius: Double) : AbstractShape() {
    override val name = "Circle"

    override fun getArea() = Math.PI * radius * radius
}

// ============= FINAL - Final (Non-overridable) =============
/**
 * What: Marks class/method as non-overridable (default in Kotlin)
 * Why: Prevent subclassing/overriding
 * How: final class / final fun (usually implicit)
 */
final class ImmutableClass {  // Cannot be subclassed
    final fun cannotOverride() {
        println("This method cannot be overridden")
    }
}

// try: class X : ImmutableClass() {}  // ERROR

// ============= OPERATOR - Operator Overloading =============
/**
 * What: Overload operators like +, -, [], ()
 * Why: Use operators with custom types
 * How: operator fun plus(...)
 */
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun get(index: Int) = if (index == 0) x else y
    operator fun invoke() = "Point($x, $y)"
}

fun example_Operator() {
    val p1 = Point(1, 2)
    val p2 = Point(3, 4)

    val p3 = p1 + p2  // Uses operator plus()
    val p4 = p1 - p2  // Uses operator minus()
    val x = p1[0]     // Uses operator get()
    val str = p1()    // Uses operator invoke()
}

// ============= INFIX - Infix Function =============
/**
 * What: Function that can be called without dot notation
 * Why: More readable syntax
 * How: infix fun functionName(param)
 */
infix fun Int.times(other: Int) = this * other
infix fun String.concat(other: String) = this + other

fun example_Infix() {
    val result = 5 times 3      // Instead of: 5.times(3)
    val text = "Hello" concat "World"
}

// ============= REIFIED - Reified Type Parameter =============
/**
 * What: Access type parameter at runtime
 * Why: Use type information in generic functions
 * How: inline fun <reified T> function()
 */
inline fun <reified T> parseJson(json: String): T {
    return when (T::class.simpleName) {
        "String" -> json as T
        "Int" -> json.toIntOrNull() as? T ?: 0 as T
        "Boolean" -> (json == "true") as T
        else -> throw Exception("Unsupported type")
    }
}

fun example_Reified() {
    val str: String = parseJson("hello")
    val num: Int = parseJson("42")
    val bool: Boolean = parseJson("true")
}

// ============= VARARG - Variable Arguments =============
/**
 * What: Accept variable number of arguments
 * Why: Flexible function parameters
 * How: vararg params: Type
 */
fun sum(vararg numbers: Int): Int {
    return numbers.sum()
}

fun printAll(vararg items: String) {
    for (item in items) {
        println(item)
    }
}

fun example_Vararg() {
    sum(1, 2, 3, 4, 5)  // Can pass any number
    printAll("A", "B", "C", "D")
}

// ============= BY - Delegation =============
/**
 * What: Delegate functionality to another object
 * Why: Avoid code duplication, use composition
 * How: val/var prop by delegateObject or class X : Interface by impl
 */
interface Writer {
    fun write(text: String)
}

class PrintWriter : Writer {
    override fun write(text: String) {
        println(text)
    }
}

class Logger(writer: Writer) : Writer by writer {  // Delegate to writer
    fun logError(error: String) {
        write("ERROR: $error")
    }
}

fun example_By() {
    val writer = PrintWriter()
    val logger = Logger(writer)
    logger.write("Hello")  // Delegated to writer
    logger.logError("Something failed")
}

// ============= GET - Getter =============
/**
 * What: Custom getter for property
 * Why: Compute value on access
 * How: val prop get() = computation
 */
class Rectangle(val width: Int, val height: Int) {
    val area: Int
        get() = width * height  // Computed on access

    val perimeter: Int
        get() = 2 * (width + height)
}

fun example_Get() {
    val rect = Rectangle(5, 10)
    println("Area: ${rect.area}")       // Computed
    println("Perimeter: ${rect.perimeter}")  // Computed
}

// ============= SET - Setter =============
/**
 * What: Custom setter for property
 * Why: Validate or react to value assignment
 * How: var prop set(value) { ... }
 */
class AgeValidator {
    var age: Int = 0
        set(value) {
            if (value >= 0 && value <= 150) {
                field = value
            } else {
                println("Invalid age")
            }
        }
}

fun example_Set() {
    val person = AgeValidator()
    person.age = 25  // OK
    person.age = -5  // Invalid - prints warning
    person.age = 200 // Invalid - prints warning
}

/**
 * Key Takeaways:
 * 1. private: Limit visibility to class/file
 * 2. protected: Allow subclasses to access
 * 3. internal: Module-level visibility
 * 4. public: Global visibility (default)
 * 5. abstract: Force implementation in subclasses
 * 6. final: Prevent overriding (default)
 * 7. operator: Overload operators
 * 8. infix: Better readability for single-parameter functions
 * 9. reified: Access type info at runtime
 * 10. vararg: Accept multiple arguments
 * 11. by: Delegate to other objects
 * 12. get/set: Custom getters/setters
 */

