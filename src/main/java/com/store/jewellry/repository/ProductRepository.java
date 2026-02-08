package com.store.jewellry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.jewellry.entity.Shop;
import com.store.jewellry.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShop(Shop shop);
}
