package etl

import com.opencsv.CSVReader
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileReader

class CsvExtractor {

    //Usei Mapas para que o Transformer não precise conhecer a ordem das colunas, apenas os nomes.
    //A chave do mapa é o nome da coluna (cabeçalho).

    fun extract(filePath: String): List<Map<String, String>> {
        val file = File(filePath)

        if (!file.exists()) {
            return emptyList()
        }

        // CSVReader faz o trabalho pesado de ler e separar as colunas
        val reader = CSVReader(FileReader(file))
        val allRows = reader.readAll()
        reader.close()

        if (allRows.isEmpty()) return emptyList()

        val header = allRows[0]
        val result = mutableListOf<Map<String, String>>()

        // Começamos do índice 1 para pular o cabeçalho
        for (i in 1 until allRows.size) {
            val row = allRows[i]
            val map = mutableMapOf<String, String>()

            for (j in header.indices) {
                // Evita erro se a linha tiver menos colunas que o cabeçalho
                val value = if (j < row.size) row[j] else ""
                map[header[j].trim()] = value.trim()
            }
            result.add(map)
        }

        return result
    }
}

class XlsxExtractor {

    fun extract(filePath: String): List<Map<String, String>> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()

        // WorkbookFactory detecta automaticamente o formato do Excel
        val workbook = WorkbookFactory.create(file)
        val sheet = workbook.getSheetAt(0)
        val formatter = DataFormatter()

        val result = mutableListOf<Map<String, String>>()
        val headerRow = sheet.getRow(0) ?: return emptyList()
        val headers = headerRow.map { formatter.formatCellValue(it).trim() }

        for (i in 1..sheet.lastRowNum) {
            val row = sheet.getRow(i) ?: continue
            val map = mutableMapOf<String, String>()

            for (j in headers.indices) {
                val cell = row.getCell(j)
                map[headers[j]] = formatter.formatCellValue(cell).trim()
            }
            result.add(map)
        }

        workbook.close()
        return result
    }
}
