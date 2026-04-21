package ru.dude.cass_example.repository

import com.datastax.oss.driver.api.core.ConsistencyLevel
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.data.cassandra.core.InsertOptions
import org.springframework.stereotype.Component


/**
 * Специальный репозиторий для реализации вставок с указанным уровнем согласованности.
 * @author Vladimir X
 * Date: 19.04.2026
 */
@Component
internal class CustomRepositoryImpl<Entity: Any>(
    private val cassandraTemplate: CassandraTemplate
) : CustomRepository<Entity> {

    override fun insertExt(entity: Entity, consistencyLevel: ConsistencyLevel): Entity {

        val options = InsertOptions.builder()
            .consistencyLevel(consistencyLevel)
            .build()

        return cassandraTemplate.insert(entity, options).entity
    }

}
