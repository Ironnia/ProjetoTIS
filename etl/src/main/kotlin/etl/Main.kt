package etl

fun main() {
    println("===================")
    println(" INICIANDO PIPELINE PROMOCHEF ETL")
    println("======================")

    val csvExtractor = CsvExtractor()
    val transformer = DataTransformer()
    val loader = ConsoleLoader()

    val filePath = "arquivos/produtos_exemplo.csv"
    println("Lendo arquivo: $filePath")

    val rawData = csvExtractor.extract(filePath)
    
    if (rawData.isEmpty()) {
        println("Nenhum dado encontrado ou arquivo inexistente.")
        return
    }

    val products = mutableListOf<Product>()
    val errors = mutableListOf<LineError>()

    rawData.forEachIndexed { index, row ->
        val validationError = transformer.validate(row, listOf("nome", "preco", "categoria"))

        if (validationError != null) {
            errors.add(LineError(index + 2, filePath, validationError))
        } else {
            val product = Product(
                name = row["nome"]!!,
                category = row["categoria"]!!,
                price = transformer.parseDouble(row["preco"]!!)
            )
            products.add(product)
        }
    }

    loader.loadProducts(products)
    loader.showErrors(errors)

    println("\n========================")
    println("PIPELINE FINALIZADO COM SUCESSO")
    println("=====================")
}
