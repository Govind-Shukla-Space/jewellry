package com.store.jewellry.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.jewellry.dto.LoginRequest;
import com.store.jewellry.dto.PasswordUpdateRequest;
import com.store.jewellry.entity.Shop;
import com.store.jewellry.entity.User;
import com.store.jewellry.repository.ShopRepository;
import com.store.jewellry.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User with this email already exists";
        }
        userRepository.save(user);
        return "User registered successfully";
    }
    public String login(LoginRequest request){
        //user
        Optional<User> User= userRepository.findByEmail(request.getEmail());
        if(User.isPresent() && User.get().getPassword().equals(request.getPassword())){
            return "User login successful";
        }
        //check shop
        Optional<Shop> Shop= shopRepository.findByEmail(request.getEmail());
        if(Shop.isPresent() && Shop.get().getPassword().equals(request.getPassword())){
            if(!Shop.get().isApproved()){
                return "Shop not approved yet";
            }
            return "Shop login successful";
        }
        //check admin
        if (request.getEmail().equals("admin@gmail.com") && request.getPassword().equals("admin123")) {
            return "ADMIN_LOGIN_SUCCESS";
        }

        return "INVALID_CREDENTIALS";
    }
    public String updatePassword(PasswordUpdateRequest request) {

        // 1. Check ADMIN
        if (request.getEmail().equals("admin@gmail.com")) {
            if (!request.getOldPassword().equals("admin123")) {
                return "Incorrect old password for admin";
            }
            // NOTE: Real admin should be stored in DB but for now:
            return "Admin password updated successfully (hardcoded)";
        }

        // 2. Check USER
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getPassword().equals(request.getOldPassword())) {
                return "Incorrect old password";
            }
            user.setPassword(request.getNewPassword());
            userRepository.save(user);
            return "User password updated successfully";
        }

        // 3. Check SHOP
        Optional<Shop> shopOpt = shopRepository.findByEmail(request.getEmail());
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            if (!shop.getPassword().equals(request.getOldPassword())) {
                return "Incorrect old password";
            }
            shop.setPassword(request.getNewPassword());
            shopRepository.save(shop);
            return "Shop password updated successfully";
        }

        return "Account not found!";
    }
}
