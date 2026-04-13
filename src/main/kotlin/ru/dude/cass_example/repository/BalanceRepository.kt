package ru.dude.cass_example.repository

import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import ru.dude.cass_example.entity.Balance


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface BalanceRepository : CassandraRepository<Balance, String> {

    @Query("UPDATE balance SET amount = :newAmount, version = :newVersion WHERE barcode = :barcode IF version = :oldVersion")
    fun updateAmount(barcode: String, newAmount: Long, oldVersion: Long, newVersion: Long): Boolean

    @Query("INSERT INTO balance(barcode, amount, version) VALUES (:barcode, 0, 0) IF NOT EXISTS")
    fun createRecord(barcode: String): Boolean

}
