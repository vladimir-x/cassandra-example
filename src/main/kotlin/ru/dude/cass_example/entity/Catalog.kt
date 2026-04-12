package ru.dude.cass_example.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
@Table("catalog")
internal class Catalog(

    @PrimaryKey
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val barcode: String,

    val brand: String,
    val name: String,
    val cost: Long
)
