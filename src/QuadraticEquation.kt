import kotlin.math.sqrt
import kotlin.random.Random

// ---додаткове завдання: sealed interface для представлення різних типів коренів---

sealed interface Roots {
    data class TwoRoots(val x1: Double, val x2: Double) : Roots
    data class OneRoot(val x: Double) : Roots
    object NoRealRoots : Roots
}

class QuadraticEquation(val a: Double, val b: Double, val c: Double) {  // спрацьовує одразу при створенні об'єкта

    init {
        require(a != 0.0) { "коефіцієнт a не може бути 0" }
    }

    constructor(a: Int, b: Int, c: Int) : this(a.toDouble(), b.toDouble(), c.toDouble())  // для цілочисельних значень, ще Double є

    constructor(b: Double, c: Double) : this(1.0, b, c)  // a = 1.0

    val discriminant: Double
        get() = b * b - 4 * a * c

    fun solve(): List<Double> {
        val d = discriminant
        return when {
            d > 0.0 -> {
                val sqrtD = sqrt(d)
                listOf((-b + sqrtD) / (2 * a), (-b - sqrtD) / (2 * a))
            }
            d == 0.0 -> listOf(-b / (2 * a))
            else -> emptyList()
        }
    }

    // sealed interface Roots
    fun solveAsRoots(): Roots {
        val d = discriminant
        return when {
            d > 0.0 -> {
                val sqrtD = sqrt(d)
                Roots.TwoRoots((-b + sqrtD) / (2 * a), (-b - sqrtD) / (2 * a))
            }
            d == 0.0 -> Roots.OneRoot(-b / (2 * a))
            else -> Roots.NoRealRoots
        }
    }

    override fun toString(): String {
        return "${a}x^2 + (${b})x + ${c} = 0"
    }
}

fun main() {
    val eq1 = QuadraticEquation(1.0, -5.0, 6.0)
    val eq2 = QuadraticEquation(2, -4, 2)
    val eq3 = QuadraticEquation(-3.0, 2.0)

    println("р-ня 1: $eq1 | дискримінанта: ${eq1.discriminant} | корені: ${eq1.solve()}")
    println("р-ня 2: $eq2 | дискримінанта: ${eq2.discriminant} | корені: ${eq2.solve()}")

    // уникаю a == 0 за допомогою генерації ненульового а через цикл або доки а != 0
    val equations = List(100) {
        var randomA = 0.0
        while (randomA == 0.0) {
            randomA = Random.nextInt(-50, 51).toDouble()
        }
        val randomB = Random.nextInt(-50, 51).toDouble()
        val randomC = Random.nextInt(-50, 51).toDouble()
        QuadraticEquation(randomA, randomB, randomC)
    }

    val twoRootEquations = equations.filter { it.discriminant > 0.0 }  // рівно 2 розв'язки (D > 0)

    println("\nр-ня з двома коренями (знайдено: ${twoRootEquations.size})")
    twoRootEquations.forEach { eq ->
        println("р-ня: $eq -> корені: ${eq.solve()}")
    }

    println("\nsealed interface(Roots)")
    val sampleEq = QuadraticEquation(1.0, 0.0, -4.0)
    when (val result = sampleEq.solveAsRoots()) {
        is Roots.TwoRoots -> println("знайдено два корені: x1 = ${result.x1}, x2 = ${result.x2}")
        is Roots.OneRoot -> println("один: x = ${result.x}")
        is Roots.NoRealRoots -> println("дійсних нема")
    }
}