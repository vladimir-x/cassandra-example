package ru.dude.cass_example.controller

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.dude.cass_example.entity.Balance
import ru.dude.cass_example.entity.Catalog
import ru.dude.cass_example.entity.Receipt
import ru.dude.cass_example.repository.BalanceRepository
import ru.dude.cass_example.repository.CatalogRepository
import ru.dude.cass_example.repository.ReceiptRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
@RestController
internal class RestApi(
    val catalogRepository: CatalogRepository,
    val balanceRepository: BalanceRepository,
    val receiptRepository: ReceiptRepository,
) {

    companion object {
        val dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    }


    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }


    @GetMapping("/catalog/list")
    fun catalogList() = catalogRepository.findAllCatalog()

    @GetMapping("/catalog/get/{barcode}")
    fun catalogGet(@PathVariable barcode: String) =  catalogRepository.findByBarcode(barcode)


    @PostMapping("/catalog/add", consumes = ["application/json"])
    fun catalogAdd(@RequestBody catalogItems: List<Catalog>) {
        catalogItems.forEach { catalog ->
            catalogRepository.insertCatalog(catalog)
        }
    }


    @GetMapping("/balance/list")
    fun balanceList() = balanceRepository.findAll()

    @GetMapping("/balance/get/{barcode}")
    fun balanceGet(@PathVariable barcode: String) =  balanceRepository.findByBarcode(barcode)

    @PostMapping("/balance/change", consumes = ["application/json"])
    fun balanceChange(@RequestBody changeItem: ChangeDto) = updateBalance(changeItem.barcode,changeItem.delta)


    private fun updateBalance(barcode: String, delta: Long) {

        // 10 попыток на обновление
        var retries = 10


        while (retries-- > 0) {

            // получить существующий баланс
            val balance = getBalanceOrCreateIfNeed(barcode)

            val newBalanceAmount = balance.amount + delta
            val newVersion = balance.version + 1
            val oldVersion = balance.version

            // обновить баланс
            val applied = balanceRepository.updateAmount(balance.barcode, newBalanceAmount, oldVersion, newVersion)

            if (applied){
                //если обновление успешно
                return
            }
        }

        throw Exception("Update balance failed")
    }

    private fun getBalanceOrCreateIfNeed(barcode: String): Balance {

        val existed = balanceRepository.findByIdOrNull(barcode)
        if (existed != null) {
            return existed
        }

        balanceRepository.createRecord(barcode)

        return balanceRepository.findByIdOrNull(barcode) ?: throw Exception("Balance [$barcode] can't create")
    }



    @GetMapping("/receipt/list")
    fun receiptListAll() = receiptRepository.findAllReceipts()


    @GetMapping("/receipt/list/{shopId}/{day}")
    fun receiptListPart(
        @PathVariable shopId: String?,
        @PathVariable day: String?,
        @RequestParam("after") after: String,
        @RequestParam("limit") limit: Int
    ): List<Receipt> {

        val saleDateAfter = LocalDateTime.parse("$day $after", dateTimeFormatter)

        return receiptRepository.findByShopAndDayAndSaleDateAfter(
            shopId!!.toLong(),
            LocalDate.parse(day!!, dayFormatter),
            saleDateAfter,
            limit
        )
    }

    @PostMapping("/receipt/add", consumes = ["application/json"])
    fun receiptAdd(@RequestBody receiptItems: List<Receipt>) {
        receiptItems.forEach { receipt ->
            receiptRepository.insertReceipt(receipt)
        }
    }
}
