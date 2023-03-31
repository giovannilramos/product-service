package br.com.quaz.inventoryservice.services;

import br.com.quaz.inventoryservice.dto.InventoryResponse;
import br.com.quaz.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(final List<String> skuCodeList) {
        return inventoryRepository.findAllBySkuCodeIn(skuCodeList).stream()
                .map(inventory -> new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() > 0))
                .toList();
    }
}
