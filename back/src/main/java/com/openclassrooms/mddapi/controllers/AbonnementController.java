package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.SubscriptionStatusDTO;
import com.openclassrooms.mddapi.services.Interfaces.IAbonnementService;

@RestController
@RequestMapping("/api/abonnement")
public class AbonnementController {

    @Autowired
    private IAbonnementService abonnementService;

    @PostMapping("/change/{themeId}")
    public ResponseEntity<SubscriptionStatusDTO> changeSubscriptionStatus(
            @PathVariable Integer themeId,
            Authentication authentication) {

        String userEmail = authentication.getName();
        SubscriptionStatusDTO message = abonnementService.changeSubscriptionStatus(themeId, userEmail);

        return ResponseEntity.ok(message);
    }
}
