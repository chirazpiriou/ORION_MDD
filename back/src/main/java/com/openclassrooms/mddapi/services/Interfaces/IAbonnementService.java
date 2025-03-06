package com.openclassrooms.mddapi.services.Interfaces;

import com.openclassrooms.mddapi.dto.SubscriptionStatusDTO;

/**
 * Interface for the Subscription Service.
 * Defines the method for managing subscription-related operations.
 */
public interface IAbonnementService {

 
    SubscriptionStatusDTO changeSubscriptionStatus(Integer id, String userEmail);
}
