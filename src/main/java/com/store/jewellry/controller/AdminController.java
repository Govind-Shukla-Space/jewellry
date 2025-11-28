package com.store.jewellry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.jewellry.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveShop(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveShop(id));
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingShops() {
        return ResponseEntity.ok(adminService.getPendingShops());
    }
}
