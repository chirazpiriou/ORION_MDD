package com.openclassrooms.mddapi.services.Interfaces;

/**
 * Interface for the Subscription Service.
 * Defines the method for managing subscription-related operations.
 */
public interface IAbonnementService {

    /**
     * Changes the subscription status for a user on a specific theme.
     * If the user is already subscribed, it unsubscribes them; otherwise, it
     * subscribes them.
     *
     * @param id        The ID of the theme the user wants to subscribe to or
     *                  unsubscribe from.
     * @param userEmail The email of the user whose subscription status is being
     *                  changed.
     * @return A message indicating the new subscription status (e.g., "Subscribed"
     *         or "Unsubscribed").
     */
    String changeSubscriptionStatus(Integer id, String userEmail);
}
