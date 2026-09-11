data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val inStock: Boolean
)

fun main() {
    val products = listOf(
        Product(1, "Google Pixel 9", "Smartphones", 899.0, 4.8, true),
        Product(2, "iPhone 16 Pro", "Smartphones", 1199.0, 4.9, true),
        Product(3, "Galaxy A55", "Smartphones", 399.0, 4.3, false),
        Product(4, "MacBook Air M3", "Laptops", 1299.0, 4.9, true),
        Product(5, "ThinkPad X1 Carbon", "Laptops", 1499.0, 4.6, false),
        Product(6, "Dell XPS 13", "Laptops", 1150.0, 4.4, true),
        Product(7, "Sony WH-1000XM5", "Audio", 349.0, 4.7, true),
        Product(8, "AirPods Pro 2", "Audio", 249.0, 4.8, false),
        Product(9, "Pixel Buds Pro 2", "Audio", 229.0, 4.5, true)
    )

    // filter, sortedByDescending, map
    println("трансформації")
    products
        .filter { it.inStock && it.rating >= 4.7 && it.price < 1000.0 }
        .sortedByDescending { it.rating }
        .map { "назва: ${it.name} | рейтинг: ${it.rating} | ціна: $${it.price}" }
        .forEach { println(it) }

    println("\nшук та предикати")  // предикатні перевірки (find, any, all)

    val expensiveLaptop = products.find { it.category == "Laptops" && it.price > 1200.0 }
    println("знайдений ноутбук: ${expensiveLaptop?.name ?: "товар не знайдено"}")

    val hasExpensiveAudio = products.any { it.category == "Audio" && it.price > 300.0 }
    println("чи є аудіо дорожче за 300.0? $hasExpensiveAudio")

    val allSmartphonesGoodRating = products.filter { it.category == "Smartphones" }.all { it.rating > 4.0 }
    println("усі смартфони мають рейтинг > 4.0? $allSmartphonesGoodRating")

    println("\nрозділення та груп")

    // на дві колекції за наявністю
    val (available, outOfStock) = products.partition { it.inStock }
    println("товарів в наявності: ${available.size}, немає на складі: ${outOfStock.size}")

    // за категорією, maxByOrNull у кожній
    val groupedByCategory = products.groupBy { it.category }
    groupedByCategory.forEach { (category, items) ->
        val mostExpensive = items.maxByOrNull { it.price }
        println("категорія $category -> найдорожчий: ${mostExpensive?.name} ($${mostExpensive?.price})")
    }

    println("\nфункц вищ порядку")
    val promoMessages = products.filterAndTransform(
        predicate = { it.price < 300.0 },
        transform = { "акц ціна на ${it.name}: лише $${it.price}" }
    )
    promoMessages.forEach { println(it) }

    // fold
    println("\nfold")
    val inStockProducts = products.filter { it.inStock }

    val totalPrice = inStockProducts.fold(0.0) { acc, product -> acc + product.price }  // fold замість sumOf
    val averagePrice = if (inStockProducts.isNotEmpty()) totalPrice / inStockProducts.size else 0.0

    println("сум варт товарів в наявності: $$totalPrice")
    println("середня вартіст товарів в наявності: $$averagePrice")
}

// власн ф вищого порядку - функц-розшир для List<Product>
fun List<Product>.filterAndTransform(
    predicate: (Product) -> Boolean,
    transform: (Product) -> String
): List<String> {
    val result = mutableListOf<String>()
    for (product in this) {
        if (predicate(product)) {
            result.add(transform(product))
        }
    }
    return result
}