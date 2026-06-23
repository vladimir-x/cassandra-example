package ru.dude.cass_example.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
@Table("reserve")
internal class Reserve(

    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val serialNumber: String,

    val barcode: String,
    val fio: String,
    val phone: String,
)
