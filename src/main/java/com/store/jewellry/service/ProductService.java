package com.store.jewellry.service;

import com.store.jewellry.entity.*;
import com.store.jewellry.repository.*;
import com.store.jewellry.dto.ProductRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ImageStorageService imageStorageService;

    public Product addProduct(Long shopId, ProductRequest req, MultipartFile file) throws IOException {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        String imageUrl = imageStorageService.storeImage(file);

        Product product = new Product();
        product.setName(req.getName());
        product.setPrice(req.getPrice());
        product.setDescription(req.getDescription());
        product.setImageUrl(imageUrl);
        product.setShop(shop);
        product.setShopName(shop.getShopName());
        System.out.println(product);

        return productRepository.save(product);
    }

    public List<Product> getProductsByShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        return productRepository.findByShop(shop);
    }

    public void deleteProduct(Long productId, String loggedInEmail,Authentication auth) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
//from here
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            // System.out.println("Is Admin: " + isAdmin+" " +auth.getAuthorities().stream().map(a -> a.getAuthority()).toList());
        if (isAdmin) {
            // ADMIN can delete ANY product
            productRepository.delete(product);
            return;
        }
//till here removw okay, only shop owner can delete their product
        // 🔐 OWNER CHECK
        if (!product.getShop().getEmail().equals(loggedInEmail)) {
            throw new RuntimeException("You cannot delete another shop's product");
        }

        productRepository.delete(product);
    }

}
