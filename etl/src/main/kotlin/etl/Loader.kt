package etl

class ConsoleLoader {
    // simulando
    fun loadProducts(products: List<Product>) {
        if (products.isEmpty()) return
        
        println("\nCarregando ${products.size} produtos:")
        println("--------------------------------------------------")
        products.forEach { 
            println("PRODUTO: ${it.name.padEnd(20)} | PREÇO: R$ ${"%.2f".format(it.price)}")
        }
    }

    // simulando
    fun loadBatches(batches: List<IngredientBatch>) {
        if (batches.isEmpty()) return

        println("\nCarregando ${batches.size} lotes de ingredientes:")
        println("--------------------------------------------------")
        batches.forEach { 
            val status = if ((it.daysToExpire ?: 0) < 5) " VENCENDO!" else " OK"
            println("LOTE: ${it.ingredientName.padEnd(20)} | VALIDADE: ${it.daysToExpire} dias | $status")
        }
    }

    fun showErrors(errors: List<LineError>) {
        if (errors.isEmpty()) return

        println("\nErros encontrados durante o processamento:")
        println("--------------------------------------------------")
        errors.forEach { 
            println("ARQUIVO: ${it.fileName} | LINHA: ${it.lineNumber} | MOTIVO: ${it.errorMessage}")
        }
    }
}
