package ru.dude.cass_example.repository

import com.datastax.oss.driver.api.core.ConsistencyLevel
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Consistency
import org.springframework.data.cassandra.repository.Query
import ru.dude.cass_example.entity.Receipt
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface ReceiptRepository : CassandraRepository<Receipt, String>, CustomRepository<Receipt> {

    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    @Query("SELECT * FROM receipt ")
    fun findAllReceipts(): List<Receipt>

    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    @Query("SELECT * FROM Receipt WHERE shop_id = :shopId AND day = :day AND sale_date > :saleDateAfter LIMIT :limit")
    fun findByShopAndDayAndSaleDateAfter(shopId: Long, day: LocalDate, saleDateAfter: LocalDateTime, limit: Int): List<Receipt>

    fun insertReceipt(entity: Receipt) = insertExt(entity, ConsistencyLevel.LOCAL_QUORUM)
}
