package ru.dude.cass_example.repository

import com.datastax.oss.driver.api.core.DefaultConsistencyLevel
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Consistency
import org.springframework.data.cassandra.repository.Query
import ru.dude.cass_example.entity.Reserve


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface ReserveRepository : CassandraRepository<Reserve, String> {

    @Query("INSERT INTO reserve(serialNumber, barcode, fio, phone) " +
            "VALUES (:serialNumber,:barcode, :fio, :phone) IF NOT EXISTS")
    @Consistency(DefaultConsistencyLevel.QUORUM)
    //@ConsistencySerial(DefaultConsistencyLevel.SERIAL) Не поддерживается. Используется параметр из app.property
    //TODO: ПРОВЕРИТЬ !!!  @Consistency(DefaultConsistencyLevel.SERIAL)
    fun book(serialNumber: String, barcode: String, fio: String, phone: String): Boolean

    @Consistency(DefaultConsistencyLevel.QUORUM)
    fun findBySerialNumber(serialNumber: String): Reserve?

}
