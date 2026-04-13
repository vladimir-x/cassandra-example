package ru.dude.cass_example.repository

import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import ru.dude.cass_example.entity.Receipt
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface ReceiptRepository : CassandraRepository<Receipt, String> {

    @Query("SELECT * FROM Receipt WHERE shop_id = :shopId AND day = :day AND sale_date > :saleDateAfter LIMIT :limit")
    fun findByShopAndDayAndSaleDateAfter(shopId: Long, day: LocalDate, saleDateAfter: LocalDateTime, limit: Int): List<Receipt>

}
