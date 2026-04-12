package ru.dude.cass_example.repository

import org.springframework.data.cassandra.repository.CassandraRepository
import ru.dude.cass_example.entity.Balance


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal interface BalanceRepository : CassandraRepository<Balance, String> {


}
