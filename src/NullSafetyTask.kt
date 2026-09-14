data class Address(
    val street: String,
    val city: String?,
    val postalCode: String?
)

data class Client(
    val id: Int,
    val name: String,
    val email: String?,
    val address: Address?,
    val extraData: Any?
)

val clientList = listOf(
    Client(1, "Олена", "olena@example.com", Address("вул. Саксаганського, 10", "Київ", "01033"), "VIP-клієнт"),
    Client(2, "Богдан", null, Address("вул. Городоцька, 45", "Львів", null), 42),
    Client(3, "Марія", "maria@example.com", null, null),
    Client(4, "Дмитро", null, Address("вул. Соборна, 1", null, null), "Очікує дзвінка")
)

// 1.
fun getShippingLabel(client: Client): String {
    val address = client.address ?: return "самовивіз: клієнт ${client.name} не надав адреси"
    val city = address.city ?: "місто не написано"
    val zip = address.postalCode ?: "індекс незрозумілий"
    return "адреса ${client.name}: ${address.street}, $city, $zip"
}

// 2.
fun printClientNote(client: Client) {
    val note = client.extraData as? String ?: "додаткові примітки відсутні"
    println("клієнт ${client.name} -> $note")
}

// 4.
fun getClientEmailOrThrow(client: Client): String {
    return client.email ?: throw IllegalArgumentException("клієнт з ID ${client.id} не має ел.пошти")
}

// 5.
fun forceGetPostalCode(client: Client): String {
    // !! - на 100% певна я, що значення не null
    return client.address!!.postalCode!!
}

fun main() {
    println("1")
    println(getShippingLabel(clientList[0]))
    println(getShippingLabel(clientList[1]))
    println(getShippingLabel(clientList[2]))

    println("\n2")
    clientList.forEach { printClientNote(it) }

    println("\n3")
    val validEmails = clientList.map { it.email }.filterNotNull()
    val shortestLength = validEmails.minByOrNull { it.length }?.length ?: 0
    println("база email для розсилки: $validEmails")
    println("довжина найкоротшого email: $shortestLength")

    println("\n4")
    try {
        getClientEmailOrThrow(clientList[1])
    } catch (e: Exception) {
        println("перехоплено виняток: ${e.message}")
    }

    println("\n5")
    println("інд Олена: ${forceGetPostalCode(clientList[0])}")
    try {
        forceGetPostalCode(clientList[1])
    } catch (e: NullPointerException) {
        println("перехоплено очікуваний NPE: ${e.message}")
    }
}