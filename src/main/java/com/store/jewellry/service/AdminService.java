package com.store.jewellry.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.jewellry.entity.Shop;
import com.store.jewellry.repository.ShopRepository;

@Service
public class AdminService {
    @Autowired
    private ShopRepository shopRepository;

    public String approveShop(Long shopId) {
          Shop shop = shopRepository.findById(shopId).orElse(null);
        if (shop == null) {
            return "SHOP_NOT_FOUND";
        }
        shop.setApproved(true);
        shopRepository.save(shop);
        return "SHOP_APPROVED_SUCCESSFULLY";
    }
    public List<Shop> getPendingShops() {
        return shopRepository.findByApproved(false);
    }
}
