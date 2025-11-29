package com.store.jewellry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.jewellry.entity.Shop;
import com.store.jewellry.entity.User;
import com.store.jewellry.repository.ShopRepository;
import com.store.jewellry.repository.UserRepository;

@Service
public class AdminService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private UserRepository userRepository;

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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }
}
