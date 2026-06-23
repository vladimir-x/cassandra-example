package ru.dude.cass_example.controller

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import ru.dude.cass_example.controller.dto.BalanceChangeDto
import ru.dude.cass_example.entity.Balance
import ru.dude.cass_example.repository.BalanceRepository


/**
 * API для баланса
 *
 * @author Vladimir X
 * Date: 11.04.2026
 */
@RestController
internal class BalanceRestApi(val balanceRepository: BalanceRepository) {

    @GetMapping("/balance/list")
    fun balanceList() = balanceRepository.findAll()

    @GetMapping("/balance/get/{barcode}")
    fun balanceGet(@PathVariable barcode: String) = balanceRepository.findByBarcode(barcode)

    @PostMapping("/balance/change", consumes = ["application/json"])
    fun balanceChange(@RequestBody changeItem: BalanceChangeDto) = updateBalance(changeItem.barcode, changeItem.delta)


    private fun updateBalance(barcode: String, delta: Long) {

        // 10 попыток на обновление
        var retries = 10


        while (retries-- > 0) {

            // получить существующий баланс
            val balance = getBalanceOrCreateIfNeed(barcode)

            val newBalanceAmount = balance.amount + delta
            val newVersion = balance.version + 1
            val oldVersion = balance.version

            // обновить баланс
            val applied = balanceRepository.updateAmount(balance.barcode, newBalanceAmount, oldVersion, newVersion)

            if (applied) {
                //если обновление успешно
                return
            }
        }

        throw Exception("Update balance failed")
    }

    private fun getBalanceOrCreateIfNeed(barcode: String): Balance {

        val existed = balanceRepository.findByIdOrNull(barcode)
        if (existed != null) {
            return existed
        }

        balanceRepository.createRecord(barcode)

        return balanceRepository.findByIdOrNull(barcode) ?: throw Exception("Balance [$barcode] can't create")
    }


}
