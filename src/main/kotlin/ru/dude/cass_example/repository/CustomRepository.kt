package ru.dude.cass_example.repository

import com.datastax.oss.driver.api.core.ConsistencyLevel


/**
 * @author Vladimir X
 * Date: 19.04.2026
 */
internal interface CustomRepository<Entity> {

    fun insertExt(entity: Entity, consistencyLevel: ConsistencyLevel): Entity
}
