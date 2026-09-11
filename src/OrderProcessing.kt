data class OrderItem(
    val name: String,
    val price: Double,
    val quantity: Int
)

class Order(val id: String) {
    var customerName: String = ""
    var deliveryAddress: String = ""
    var discountPercent: Int = 0
    val items: MutableList<OrderItem> = mutableListOf()

    fun addItem(item: OrderItem) {
        items.add(item)
    }
}

fun main() {
    val order = Order("ORD-2026-001").apply {
        customerName = "Олексій Коваленко"
        deliveryAddress = "вул. Хрещатик, 22, Київ"
        discountPercent = 10
        addItem(OrderItem("Клавіатура", 2500.0, 1))
        addItem(OrderItem("Мишка", 1200.0, 1))
        addItem(OrderItem("Килимок", 400.0, 2))
    }.also {
        println("[LOG] Замовлення ${it.id} успішно налаштовано. К-сть позицій: ${it.items.size}")
    }

    val finalPrice: Double = order.run {
        // (2500*1) + (1200*1) + (400*2) = 2500 + 1200 + 800 = 4500
        val subtotal = items.sumOf { it.price * it.quantity }
        // Знижка 10%: 4500 - (4500 * 10 / 100) = 4500 - 450 = 4050.0
        subtotal - (subtotal * discountPercent / 100)
    }
    println("розрахована сума: ${finalPrice}")

    val paymentPayload: String = finalPrice.let {
        val cents = (it * 100).toLong()
        "TXN-${order.id}_AMOUNT_${cents}_UAH"
    }
    println("payload: $paymentPayload")

    with(order) {
        println("========================================")
        println("ЧЕК ЗАМОВЛЕННЯ: $id")
        println("Отримувач: $customerName")
        println("Адреса доставки: $deliveryAddress")
        println("----------------------------------------")
        println("Товари:")
        for (item in items) {
            println(" • ${item.name} (${item.quantity} шт.) - ${item.price * item.quantity} грн")
        }
        println("----------------------------------------")
        println("Знижка клієнта: $discountPercent%")
        println("Разом до сплати: $finalPrice грн")
        println("========================================")
    }
}