package ru.dude.cass_example.controller

import org.springframework.web.bind.annotation.*
import ru.dude.cass_example.entity.Catalog
import ru.dude.cass_example.repository.CatalogRepository


/**
 * API для каталога
 *
 * @author Vladimir X
 * Date: 11.04.2026
 */
@RestController
internal class CatalogRestApi(val catalogRepository: CatalogRepository) {


    @GetMapping("/catalog/list")
    fun catalogList() = catalogRepository.findAllCatalog()

    @GetMapping("/catalog/get/{barcode}")
    fun catalogGet(@PathVariable barcode: String) = catalogRepository.findByBarcode(barcode)


    @PostMapping("/catalog/add", consumes = ["application/json"])
    fun catalogAdd(@RequestBody catalogItems: List<Catalog>) {
        catalogItems.forEach { catalog ->
            catalogRepository.insertCatalog(catalog)
        }
    }

}
