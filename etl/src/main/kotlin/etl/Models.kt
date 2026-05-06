package etl

data class Product(
    val id: Int? = null,
    val name: String,
    val category: String,
    val price: Double
)

data class IngredientBatch(
    val id: Int? = null,
    val ingredientName: String,
    val quantity: Double,
    val unit: String,
    val expirationDate: String,
    val daysToExpire: Int? = null
)

data class Sale(
    val id: Int? = null,
    val productName: String,
    val quantity: Int,
    val totalValue: Double,
    val saleDate: String
)

// Captura erros de linha para o relatório final sem interromper o processo
data class LineError(
    val lineNumber: Int,
    val fileName: String,
    val errorMessage: String
)
