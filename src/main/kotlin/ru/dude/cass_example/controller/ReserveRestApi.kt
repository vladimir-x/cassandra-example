package ru.dude.cass_example.controller

import org.springframework.web.bind.annotation.*
import ru.dude.cass_example.controller.dto.ReserveDto
import ru.dude.cass_example.repository.ReserveRepository


/**
 * API для резервирования
 *
 * @author Vladimir X
 * Date: 11.04.2026
 */
@RestController
internal class ReserveRestApi(val reserveRepository: ReserveRepository) {

    @GetMapping("/reserve/list")
    fun reserveList() = reserveRepository.findAll().map { ReserveDto.byEntity(it) }

    @GetMapping("/reserve/get/{serialNumber}")
    fun reserveGet(@PathVariable serialNumber: String) =
        reserveRepository.findBySerialNumber(serialNumber)?.let { ReserveDto.byEntity(it) }

    @PostMapping("/reserve/book", consumes = ["application/json"])
    fun reserveBook(@RequestBody reserveDto: ReserveDto) = reserveBookInternal(reserveDto)

    private fun reserveBookInternal(dto: ReserveDto): BookResponse {
        // 3 попытки на вставку
        var retries = 3

        while (retries-- > 0) {
            try {


                val applied = reserveRepository.book(dto.serialNumber, dto.barcode, dto.fio, dto.phone)

                if (applied) {
                    return BookResponse("Device booking succeed: ${dto.serialNumber}")
                } else {
                    val existed = reserveRepository.findBySerialNumber(dto.serialNumber)

                    return if (existed == null) {
                        BookResponse("Reserve ${dto.serialNumber} not found")
                    } else if (existed.phone == dto.phone) {
                        BookResponse("Device booking succeed: ${dto.serialNumber} already booked by ${existed.phone}")
                    } else {
                        BookResponse("Device booking failed: ${dto.serialNumber} already booked by different ${existed.phone}")
                    }
                }

            } catch (e: Exception) {
                println("WARNING: ${e.message}")
            }
        }

        throw Exception("Device booking failed")
    }

    data class BookResponse(val message: String)

}
