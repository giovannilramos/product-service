package br.com.quaz.inventoryservice.services;

import br.com.quaz.inventoryservice.dto.InventoryResponse;
import br.com.quaz.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(final List<String> skuCodeList) {
//        sleep(10000);
        return inventoryRepository.findAllBySkuCodeIn(skuCodeList).stream()
                .map(inventory -> new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() > 0))
                .toList();
    }
}
