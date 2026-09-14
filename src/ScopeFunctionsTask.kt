import kotlin.random.Random

fun generateMyRandomArray(size: Int, maxValue: Int): IntArray? {
    if (size <= 0 || maxValue <= 0) return null
    return IntArray(size) { Random.nextInt(0, maxValue + 1) }
}

fun main() {
    println("Ланцюжки Scope-ф")

    generateMyRandomArray(10, 50)
        ?.apply {
            for (i in this.indices) {
                if (this[i] % 2 != 0) {
                    this[i] = this[i] * 2
                } else {
                    this[i] = this[i] / 2
                }
            }
        }
        ?.also {
            println("модифік масив: ${it.contentToString()}")
        }
        ?.let {
            println("макс знач масив: ${it.maxOrNull()}")
        } ?: println("помилка вхід дан")
}