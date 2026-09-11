data class ArraySummary(
    val maxElement: Int,
    val elementsOverLimit: Int,
    val totalSum: Int
)

fun generateRandomArray(size: Int, maxValue: Int): IntArray {
    require(size > 0 && maxValue > 0) { "розмір та макс знач мають бути більш за 0" }
    return IntArray(size) { kotlin.random.Random.nextInt(1, maxValue + 1) }
}

fun main() {
    generateRandomArray(size = 8, maxValue = 40)
        .also {
            // через it лог
            println("початков масив: ${it.contentToString()}")
        }
        .apply {
            // this модифікую не змінюючи де
            for (i in indices) {
                if (this[i] % 2 != 0) {
                    this[i] *= 2
                } else {
                    this[i] /= 2
                }
            }
        }
        .also {
            // логування - it
            println("модифікований масив: ${it.contentToString()}")
            println("-".repeat(40))
        }
        .run {
            ArraySummary(
                maxElement = reduce { max, x -> maxOf(max, x) },
                elementsOverLimit = count { it > 20 },
                totalSum = sum()
            )
        }
        .let {
            """
            звіт:
             • найб значення: ${it.maxElement}
             • к-сть елементів > 20: ${it.elementsOverLimit}
             • сум знач: ${it.totalSum}
            """.trimIndent()
        }
        .also {
            println(it)
            println("-".repeat(40))
        }
}