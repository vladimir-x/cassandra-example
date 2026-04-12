package ru.dude.cass_example.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
@Table("receipt")
internal class Receipt(

    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val shop_id: String,


    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    val day: String,


    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    val sale_date: String,

    val totalAmount: Long,
    val seller: String,
    val lines: List<String>,
)
