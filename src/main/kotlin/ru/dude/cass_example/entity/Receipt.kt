package ru.dude.cass_example.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
@Table("receipt")
internal class Receipt(

    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val shop_id: Long,


    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    val day: LocalDate,


    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val sale_date: LocalDateTime,

    @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    val receipt_id: String,

    val totalAmount: Long,
    val seller: String,
    val barcodes: List<String>
)
