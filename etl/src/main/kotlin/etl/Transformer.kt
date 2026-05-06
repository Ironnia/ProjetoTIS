package etl

class DataTransformer {

    // Valida se o mapa tem as colunas obrigatórias. Vai retornar nada se estiver tudo certo.
    fun validate(row: Map<String, String>, requiredColumns: List<String>): String? {
        val missing = requiredColumns.filter { row[it].isNullOrBlank() }

        if (missing.isNotEmpty()) {
            return "Campos obrigatórios ausentes: ${missing.joinToString(", ")}"
        }

        return null
    }
}
