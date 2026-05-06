package etl

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TransformerTest {

    private val transformer = DataTransformer()

    @Test
    fun `deve validar colunas obrigatorias corretamente`() {
        val row = mapOf("nome" to "Suco", "preco" to "7.50")
        
        val ok = transformer.validate(row, listOf("nome", "preco"))
        val erro = transformer.validate(row, listOf("nome", "categoria"))

        assertNull(ok)
        assertNotNull(erro)
    }

    @Test
    fun `deve converter decimal com virgula para ponto`() {
        val preco = "12,50"
        assertEquals(12.5, transformer.parseDouble(preco))
    }

    @Test
    fun `deve retornar zero para preco invalido`() {
        assertEquals(0.0, transformer.parseDouble("abc"))
    }

    @Test
    fun `deve calcular dias para vencer corretamente`() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        // Criamos uma data de 10 dias no futuro
        val daquiDezDias = LocalDate.now().plusDays(10).format(formatter)

        assertEquals(10, transformer.calculateDaysToExpire(daquiDezDias))
    }

    @Test
    fun `deve retornar -1 para data invalida`() {
        assertEquals(-1, transformer.calculateDaysToExpire("32/13/2024"))
    }
}
