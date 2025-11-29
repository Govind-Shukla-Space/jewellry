package com.store.jewellry.service;

import java.util.List;

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
    
    public List<Shop> getApprovedShops() {
        return shopRepository.findByApproved(true);
    }
    public Shop updateShop(Long id, Shop details) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
                if (!shop.getEmail().equals(details.getEmail())) {
                    if (shopRepository.findByEmail(shop.getEmail()).isPresent()) {
                        throw new RuntimeException("Email already in use!");
                    }
                }
        shop.setShopName(details.getShopName());
        shop.setEmail(details.getEmail());
        shop.setOwnerName(details.getOwnerName());
        shop.setAddress(details.getAddress());
        shop.setPhone(details.getPhone());
    
        return shopRepository.save(shop);
    }
}
