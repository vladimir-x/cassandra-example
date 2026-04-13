package ru.dude.cass_example.controller

import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.data.cassandra.core.cql.CqlTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
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

    val cassTemplate: CassandraTemplate,
    val cqlTemplate: CqlTemplate
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
    fun catalogList() = catalogRepository.findAll()

    @PostMapping("/catalog/add", consumes = ["application/json"])
    fun catalogAdd(@RequestBody catalogItems: List<Catalog>) {
        catalogRepository.insert(catalogItems)
    }


    @GetMapping("/balance/list")
    fun balanceList() = balanceRepository.findAll()

    @PostMapping("/balance/change", consumes = ["application/json"])
    fun balanceChange(@RequestBody changeItem: ChangeDto) {

        // получить существующий баланс
        val balance = getBalanceOrCreateIfNeed(changeItem.barcode)

        val newBalanceAmount = balance.amount + changeItem.delta
        val newVersion = balance.version + 1
        val oldVersion = balance.version

        // обновить баланс
        balanceRepository.updateAmount(balance.barcode, newBalanceAmount, oldVersion, newVersion)


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
    fun receiptListAll() = receiptRepository.findAll()


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
        // НЕ контролируется уровень согласованности (используется по умолчанию)
        receiptRepository.insert(receiptItems)
    }
}


/**

val applied = cqlTemplate.execute { session ->
val preparedStatement = session.prepare(lwtCql)

val boundStatement = preparedStatement.bind(newBalanceAmount, newVersion, balance.barcode, balance.version)
.setConsistencyLevel(ConsistencyLevel.QUORUM)
.setSerialConsistencyLevel(ConsistencyLevel.SERIAL)

preparedStatement
}


val writeOpts = UpdateOptions.builder()
.consistencyLevel(ConsistencyLevel.QUORUM)
.serialConsistencyLevel(ConsistencyLevel.SERIAL)
.build()

cassTemplate.statementFactory.update(lwtCql, writeOpts).build({
it.
})

cassTemplate.query(lwtCql).
val update = Update.empty()
.set(Balance::amount.name, newBalanceAmount)
.set(Balance::version.name, newVersion)

val query = Query.query(where("id").`is`(id))
.queryOptions(UpdateOptions.builder().ifCondition(where("id").isEqualTo(oldVersion)).build())


Query.query(Update().set("barcode", it.barcode))

cassTemplate.up

balanceRepository.
balance.


}

balanceRepository.insert(changeItems)
 */
