interface Refundable {
    fun refund(amount: Double): Boolean
}
interface EReceiptable {
    val receiptEmail: String

    fun sendReceipt() {
        println(" [E-Receipt] Електронний чек надіслано на пошту: $receiptEmail")
    }
}

// Стан + поведінка - is-a
abstract class PaymentMethod(
    val transactionId: String,
    val amount: Double,
    val currency: String = "UAH"
) {
    init {
        require(amount > 0.0) { "сума платежу повинна бути більшою за 0" }
    }

    abstract fun processPayment(): Boolean

    open fun printDetails() {
        println("Транзакція #$transactionId | Сума: $amount $currency")
    }
}

class CreditCardPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val cardNumber: String,
    override val receiptEmail: String
) : PaymentMethod(transactionId, amount, currency), Refundable, EReceiptable {

    override fun processPayment(): Boolean {
        println("Списання $amount $currency з картки (закінчується на ${cardNumber.takeLast(4)})... Успіх")
        return true
    }

    override fun refund(amount: Double): Boolean {
        println("Повернення $amount $currency на картку ${cardNumber.takeLast(4)} виконано")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("Метод: Банківська картка (**** ${cardNumber.takeLast(4)})")
    }
}

class CryptoPayment(  // криптовалютою - Refundable
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val cryptoAddress: String,
    val networkFee: Double
) : PaymentMethod(transactionId, amount, currency), Refundable {

    override fun processPayment(): Boolean {
        println("Відправка $amount $currency (+ комісія $networkFee) на гаманець ${cryptoAddress.take(5)}......${cryptoAddress.takeLast(4)}... Підтверджено")
        return true
    }

    override fun refund(amountToRefund: Double): Boolean {
        if (amountToRefund > networkFee) {
            val finalAmount = amountToRefund - networkFee
            println("Крипто-повернення $finalAmount $currency (з урахуванням комісії мережі) надіслано")
            return true
        }
        return false
    }

    override fun printDetails() {
        super.printDetails()
        println("Метод: Криптовалюта (Адреса: ${cryptoAddress.take(5)}...)")
    }
}

class CashOnDeliveryPayment(
    transactionId: String,
    amount: Double,
    currency: String = "UAH",
    val deliveryAddress: String,
    override val receiptEmail: String
) : PaymentMethod(transactionId, amount, currency), EReceiptable {

    override fun processPayment(): Boolean {
        println("Замовлення зареєстровано. Оплата $amount $currency буде здійснена кур'єру за адресою: $deliveryAddress")
        return true
    }

    override fun printDetails() {
        super.printDetails()
        println("Метод: Післяплата (Адреса: $deliveryAddress)")
    }

    override fun sendReceipt() {
        println(" [SMS & E-Receipt] Фіскальний чек зареєстровано та продубльовано на $receiptEmail")
    }
}

fun processMassRefund(items: List<PaymentMethod>, refundPercentage: Double) {
    println("\n==================================================")
    println("МАСОВЕ ПОВЕРНЕННЯ КОШТІВ (${(refundPercentage * 100).toInt()}%)")
    println("==================================================")

    var successCount = 0
    for (item in items) {
        if (item is Refundable) {
            val refundAmount = item.amount * refundPercentage
            if (item.refund(refundAmount)) {
                successCount++
            }
        }
    }
    println("успішно оброблено повернень: $successCount з ${items.size} транзакцій")
}

fun main() {
    val payments: List<PaymentMethod> = listOf(
        CreditCardPayment("TXN-001", 2500.0, "UAH", "4149499988884321", "client@gmail.com"),
        CryptoPayment("TXN-002", 10000.0, "UAH", "0x71CB29C", 50.0),
        CashOnDeliveryPayment("TXN-003", 1200.0, "UAH", "м. Львів, Відділення №5", "receiver@lviv.ua")
    )

    println("==================================================")
    println("ОБРОБКА ПОТОЧНИХ ТРАНЗАКЦІЙ")
    println("==================================================")

    for (payment in payments) {
        payment.printDetails()
        payment.processPayment()

        if (payment is EReceiptable) {
            payment.sendReceipt()
        }
        println("--------------------------------------------------")
    }

    processMassRefund(payments, 0.5)
}