import kotlin.random.Random

fun generateMyRandomArray(size: Int, maxValue: Int): IntArray? {
    if (size <= 0 || maxValue <= 0) return null
    return IntArray(size) { Random.nextInt(0, maxValue + 1) }
}

fun main() {
    println("Ланцюжки Scope-ф")

    generateMyRandomArray(10, 50)?.let { array ->
        array.apply {
            for (i in indices) {
                if (this[i] % 2 != 0) {
                    this[i] = this[i] * 2
                } else {
                    this[i] = this[i] / 2
                }
            }
        }.also { modifiedArray ->
            println("модифік масив: ${modifiedArray.contentToString()}")
        }.maxOrNull()
    }?.also { maxVal ->
        println("макс знач масив: $maxVal")
    } ?: println("помилка вхідних даних")
}