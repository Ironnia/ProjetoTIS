package etl

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DataTransformer {

    // Valida se o mapa tem as colunas obrigatórias. Vai retornar nada se estiver tudo certo.
    fun validate(row: Map<String, String>, requiredColumns: List<String>): String? {
        val missing = requiredColumns.filter { row[it].isNullOrBlank() }

        if (missing.isNotEmpty()) {
            return "Campos obrigatórios ausentes: ${missing.joinToString(", ")}"
        }

        return null
    }

    // Trtar a vírgula como ponto
    fun parseDouble(value: String): Double = value.replace(",", ".").toDoubleOrNull() ?: 0.0

    fun parseInt(value: String): Int = value.toIntOrNull() ?: 0

    fun calculateDaysToExpire(dateStr: String): Int {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val expiration = LocalDate.parse(dateStr, formatter)
            val today = LocalDate.now()
            ChronoUnit.DAYS.between(today, expiration).toInt()
        } catch (e: Exception) {
            -1 // Data inválida
        }
    }
}
