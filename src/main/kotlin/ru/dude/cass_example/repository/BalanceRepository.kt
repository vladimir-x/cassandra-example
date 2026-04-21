package ru.dude.cass_example.repository

import com.datastax.oss.driver.api.core.DefaultConsistencyLevel
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Consistency
import org.springframework.data.cassandra.repository.Query
import ru.dude.cass_example.entity.Balance


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface BalanceRepository : CassandraRepository<Balance, String> {

    @Query("UPDATE balance SET amount = :newAmount, version = :newVersion WHERE barcode = :barcode IF version = :oldVersion")
    @Consistency(DefaultConsistencyLevel.QUORUM)
    //@ConsistencySerial(DefaultConsistencyLevel.SERIAL) Не поддерживается. Используется параметр из app.property
    fun updateAmount(barcode: String, newAmount: Long, oldVersion: Long, newVersion: Long): Boolean

    @Query("INSERT INTO balance(barcode, amount, version) VALUES (:barcode, 0, 0) IF NOT EXISTS")
    @Consistency(DefaultConsistencyLevel.QUORUM)
    //@ConsistencySerial(DefaultConsistencyLevel.SERIAL) Не поддерживается. Используется параметр из app.property
    fun createRecord(barcode: String): Boolean


    @Consistency(DefaultConsistencyLevel.QUORUM)
    fun findByBarcode(barcode: String): Balance

}
