class Employee(val firstName: String, val lastName: String, val position: String)
data class DataEmployee(val firstName: String, val lastName: String, val position: String) {
    var bonus: Int = 0 // поле поза конструктором для перевірки equals
}
class ManualEmployee(val firstName: String, val lastName: String, val position: String) {  // слово data
    var bonus: Int = 0

    override fun toString(): String {
        return "ManualEmployee(firstName=$firstName, lastName=$lastName, position=$position)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ManualEmployee) return false
        return firstName == other.firstName && lastName == other.lastName && position == other.position
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + position.hashCode()
        return result
    }

    operator fun component1(): String = firstName
    operator fun component2(): String = lastName
    operator fun component3(): String = position

    fun copy(
        firstName: String = this.firstName,
        lastName: String = this.lastName,
        position: String = this.position
    ): ManualEmployee {
        val newEmp = ManualEmployee(firstName, lastName, position)
        newEmp.bonus = this.bonus
        return newEmp
    }
}

fun main() {
    println("проблем звичайного класу")
    val emp1 = Employee("Анна", "Кострицька", "Developer")
    val emp2 = Employee("Анна", "Кострицька", "Developer")
    val emp3 = emp1

    println("println(emp1): $emp1")
    println("emp1 === emp3: ${emp1 === emp3}") // true
    println("emp1 == emp2: ${emp1 == emp2}")  // false, бо звичайний клас порівнює посилання, а не внутрішні поля

    println("\nData Class")
    val dEmp1 = DataEmployee("Анна", "Кострицька", "Developer")
    val dEmp2 = DataEmployee("Анна", "Кострицька", "Developer")

    println("Data class println: $dEmp1")
    println("Data class порівняння за значенням (dEmp1 == dEmp2): ${dEmp1 == dEmp2}")

    // деструктуризація
    val (name, surname, pos) = dEmp1
    println("ім'я = $name, прізвище = $surname, посада = $pos")

    // copy()
    val promotedEmp = dEmp1.copy(position = "Senior Developer")
    println("ctrlC працівник: $promotedEmp")

    val bonusEmp1 = DataEmployee("Назар", "Захаркевич", "QA").apply { bonus = 500 }
    val bonusEmp2 = DataEmployee("Назар", "Захаркевич", "QA").apply { bonus = 1000 }
    println("рівність об'єктів з різним bonus поза конструктором: ${bonusEmp1 == bonusEmp2}")

    println("\nручне")
    val manualEmp1 = ManualEmployee("Тарас", "Шевченко", "Manager")
    val manualEmp2 = ManualEmployee("Тарас", "Шевченко", "Manager")
    println("ManualEmployee toString: $manualEmp1")
    println("ManualEmployee ==: ${manualEmp1 == manualEmp2}")

    val (mName, mSur, mPos) = manualEmp1
    println("деструктуризація вручну: $mName, $mSur, $mPos")

    val manualCopied = manualEmp1.copy(position = "Director")
    println("ручне copy(): $manualCopied")
}