import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\nМеню:")
        println("1. Шифрование текста")
        println("2. Расшифровка текста с помощью ключа")
        println("3. Расшифровка текста с помощью brute force")
        println("4. Расшифровка текста с помощью статистического анализа")
        println("0. Выход")
        print("Выберите пункт меню: ")

        when (scanner.nextLine().toIntOrNull()) {
            1 -> {
                print("Введите текст для шифрования: ")
                val text = scanner.nextLine()
                print("Введите ключ (сдвиг): ")
                val shift = scanner.nextLine().toIntOrNull() ?: 0
                val encrypted = caesarRussian(text, shift)
                println("Зашифрованный текст: $encrypted")
            }
            2 -> {
                print("Введите зашифрованный текст: ")
                val text = scanner.nextLine()
                print("Введите ключ (сдвиг): ")
                val shift = scanner.nextLine().toIntOrNull() ?: 0
                val decrypted = caesarRussian(text, -shift)
                println("Расшифрованный текст: $decrypted")
            }
            3 -> {
                print("Введите зашифрованный текст: ")
                val text = scanner.nextLine()
                bruteForceRussian(text)
            }
            4 -> {
                print("Введите зашифрованный текст: ")
                val text = scanner.nextLine()
                frequencyAnalysisRussian(text)
            }
            0 -> {
                println("Выход.")
                break
            }
            else -> println("Неверный ввод. Попробуйте снова.")
        }
    }
}

fun caesarRussian(text: String, shift: Int): String {
    val ruLower = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    val ruUpper = ruLower.uppercase()
    val size = ruLower.length
    val result = StringBuilder()

    for (char in text) {
        when {
            char in ruLower -> {
                val index = ruLower.indexOf(char)
                val newIndex = (index + shift).floorMod(size)
                result.append(ruLower[newIndex])
            }
            char in ruUpper -> {
                val index = ruUpper.indexOf(char)
                val newIndex = (index + shift).floorMod(size)
                result.append(ruUpper[newIndex])
            }
            else -> result.append(char)
        }
    }

    return result.toString()
}

fun Int.floorMod(mod: Int): Int = ((this % mod) + mod) % mod

fun bruteForceRussian(text: String) {
    val alphabetSize = 33
    println("Brute force расшифровка:")
    for (shift in 1 until alphabetSize) {
        val decrypted = caesarRussian(text, -shift)
        println("Сдвиг $shift: $decrypted")
    }
}

fun frequencyAnalysisRussian(text: String) {
    val frequencyLetters = listOf('о', 'е', 'а', 'и', 'н', 'т', 'с', 'р', 'в', 'л')
    val ruAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

    val cleanedText = text.lowercase().filter { it in ruAlphabet }
    if (cleanedText.isEmpty()) {
        println("Недостаточно букв для анализа.")
        return
    }

    val mostCommonChar = cleanedText.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
    if (mostCommonChar == null) {
        println("Не удалось определить частую букву.")
        return
    }

    for (common in frequencyLetters) {
        val shift = (ruAlphabet.indexOf(mostCommonChar) - ruAlphabet.indexOf(common)).floorMod(ruAlphabet.length)
        val decrypted = caesarRussian(text, -shift)
        println("Предположим, '$mostCommonChar' → '$common'. Сдвиг: $shift")
        println("Результат: $decrypted")
        println("--------")
    }
}
