package etl

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExtractorTest {

    private val extractor = CsvExtractor()

    @Test
    fun `deve extrair dados corretamente de um CSV valido`() {
        val testFile = File("test_temp.csv")
        testFile.writeText("nome,categoria,preco\nSuco,Bebida,7.50\nBolo,Comida,12.00")

        val result = extractor.extract("test_temp.csv")

        assertEquals(2, result.size)
        assertEquals("Suco", result[0]["nome"])
        assertEquals("7.50", result[0]["preco"])
        assertEquals("Bolo", result[1]["nome"])

        testFile.delete()
    }

    @Test
    fun `deve retornar lista vazia para arquivo inexistente`() {
        val result = extractor.extract("nao_existe.csv")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `deve lidar com arquivo CSV que so tem cabecalho`() {
        val testFile = File("empty_temp.csv")
        testFile.writeText("nome,preco,categoria")

        val result = extractor.extract("empty_temp.csv")

        assertTrue(result.isEmpty())
        testFile.delete()
    }
}
