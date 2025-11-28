package com.store.jewellry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.jewellry.entity.Shop;
import com.store.jewellry.repository.ShopRepository;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    public Shop registerShop(Shop shop) {
        if (shopRepository.findByEmail(shop.getEmail()).isPresent()) {
            return null;
        }
        shop.setApproved(false); 
        shop.setRole("SHOP");
        return shopRepository.save(shop);
    }
    public Shop getShopById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }
}
