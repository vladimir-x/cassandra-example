package ru.dude.cass_example.controller

import ru.dude.cass_example.entity.Reserve


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
internal data class ReserveDto(
    val serialNumber: String,
    val barcode: String,
    val fio: String,
    val phone: String,
) {
    companion object {
        fun byEntity(e: Reserve) = ReserveDto(e.serialNumber, e.barcode, e.fio, e.phone)
    }
}
