package ru.dude.cass_example.repository

import org.springframework.data.cassandra.repository.CassandraRepository
import ru.dude.cass_example.entity.Receipt


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface ReceiptRepository : CassandraRepository<Receipt, String> {


}
