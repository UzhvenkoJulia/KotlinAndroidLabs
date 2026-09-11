data class Vehicle(
    var brand: String = "",
    var model: String = "",
    var year: Int = 0,
    var licensePlate: String = ""
)

fun main() {
    val vehicle = Vehicle().apply {
        brand = "Honda"
        model = "Civic"
        year = 2020
        licensePlate = "КА3344ВІ"
    }.also {
        println("створено новий транспортний засіб: $it")
    }

    println("\nwith")

    with(vehicle) {  // scope-функція with
        println("марка = $brand")
        println("модель = $model")
        println("рік випуску: $year")
        println("номерний знак: $licensePlate")
    }
}