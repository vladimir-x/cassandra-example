package ru.dude.cass_example.repository

import com.datastax.oss.driver.api.core.ConsistencyLevel
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Consistency
import org.springframework.data.cassandra.repository.Query
import ru.dude.cass_example.entity.Catalog


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */

internal interface CatalogRepository : CassandraRepository<Catalog, String>, CustomRepository<Catalog> {


    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    @Query("SELECT * FROM catalog ")
    fun findAllCatalog(): List<Catalog>

    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    fun findByBarcode(barcode: String): Catalog

    fun insertCatalog(entity: Catalog) = insertExt(entity, ConsistencyLevel.EACH_QUORUM)

}
