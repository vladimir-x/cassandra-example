package ru.dude.cass_example.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import ru.dude.cass_example.entity.Receipt
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * @author Vladimir X
 * Date: 22.06.2026
 */
internal data class ReceiptDto (
    val shop_id: Long,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val day: LocalDate,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val sale_date: LocalDateTime,
    val receipt_id: String,
    val totalAmount: Long,
    val seller: String,
    val barcodes: List<String>
) {
    companion object {
        fun byEntity(e: Receipt) =
            ReceiptDto(e.shop_id, e.day, e.sale_date, e.receipt_id, e.totalAmount, e.seller, e.barcodes)

    }

    fun toEntity() = Receipt(shop_id, day, sale_date, receipt_id, totalAmount, seller, barcodes)
}
