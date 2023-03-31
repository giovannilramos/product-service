package br.com.quaz.inventoryservice.repositories;

import br.com.quaz.inventoryservice.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> findAllBySkuCodeIn(final List<String> skuCodeList);
}
