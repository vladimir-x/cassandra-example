package ru.dude.cass_example.controller

import org.springframework.web.bind.annotation.*
import ru.dude.cass_example.controller.dto.ReceiptDto
import ru.dude.cass_example.repository.ReceiptRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * API для чеков
 *
 * @author Vladimir X
 * Date: 11.04.2026
 */
@RestController
internal class ReceiptRestApi(val receiptRepository: ReceiptRepository) {

    companion object {
        val dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    }


    @GetMapping("/receipt/list")
    fun receiptListAll() = receiptRepository.findAllReceipts().map { ReceiptDto.byEntity(it) }


    @GetMapping("/receipt/list/{shopId}/{day}")
    fun receiptListPart(
        @PathVariable shopId: String?,
        @PathVariable day: String?,
        @RequestParam("after") after: String,
        @RequestParam("limit") limit: Int
    ): List<ReceiptDto> {

        val saleDateAfter = LocalDateTime.parse("$day $after", dateTimeFormatter)

        return receiptRepository.findByShopAndDayAndSaleDateAfter(
            shopId!!.toLong(),
            LocalDate.parse(day!!, dayFormatter),
            saleDateAfter,
            limit
        ).map { ReceiptDto.byEntity(it) }
    }

    @PostMapping("/receipt/add", consumes = ["application/json"])
    fun receiptAdd(@RequestBody receiptItems: List<ReceiptDto>) {
        receiptItems.forEach { receipt ->
            receiptRepository.insertReceipt(receipt.toEntity())
        }
    }
}
