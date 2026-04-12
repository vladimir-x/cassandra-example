package ru.dude.cass_example.controller

import com.datastax.oss.driver.api.core.ConsistencyLevel
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.data.cassandra.core.UpdateOptions
import org.springframework.data.cassandra.core.cql.CqlTemplate
import org.springframework.data.cassandra.core.cql.WriteOptions
import org.springframework.data.cassandra.core.query.Query
import org.springframework.data.cassandra.core.query.Update
import org.springframework.data.cassandra.core.query.isEqualTo
import org.springframework.data.cassandra.core.query.where
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.dude.cass_example.entity.Balance
import ru.dude.cass_example.entity.Catalog
import ru.dude.cass_example.entity.Receipt
import ru.dude.cass_example.repository.BalanceRepository
import ru.dude.cass_example.repository.CatalogRepository
import ru.dude.cass_example.repository.ReceiptRepository


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
    fun balanceChange(@RequestBody changeItems: List<ChangeDto>) {

        changeItems.forEach {
            val balance = balanceRepository.findByIdOrNull(it.barcode) ?: throw Exception("Balance [${it.barcode}] not found")

            val newBalanceAmount = balance.amount + it.delta
            val newVersion = balance.version + 1
            val oldVersion = balance.version

            val lwtCql = "UPDATE balance SET amount= :amount, version = :newVersion WHERE barcode = :barcode IF version = :oldVersion"

            throw Exception("Not supported Yet")

        }

    }


    @GetMapping("/receipt/list")
    fun receiptList() = receiptRepository.findAll()

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
