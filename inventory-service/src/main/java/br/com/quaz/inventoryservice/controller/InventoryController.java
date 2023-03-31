package br.com.quaz.inventoryservice.controller;

import br.com.quaz.inventoryservice.services.InventoryService;
import br.com.quaz.inventoryservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam(name = "skuCode") final List<String> skuCodeList) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCodeList));
    }
}
