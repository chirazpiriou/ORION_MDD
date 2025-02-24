import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Theme } from '../../models/theme.model';
import { AbonnementService } from '../../services/AbonnementService';
import { take } from 'rxjs';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})

  /**
   * We do not use ngOnInit because there is no initialization logic needed.
   * - The `@Input()` property `theme` is already handled via a setter.
   * - The component does not need to fetch or process data at initialization.
   * - The service is injected but only used when `changeSubscriptionStatus` is called.
   * 
   * This makes the component simpler and avoids unnecessary lifecycle methods.
   */
export class ThemeComponent {
    @Input() theme!: Theme; 

   
    /**
     * Output event emitter to notify when the subscription status changes.
     */
    @Output() subscriptionChanged = new EventEmitter<void>();

    /**
     * Injecting the subscription service.
     */
    constructor(private abonnementService: AbonnementService) {}

    /**
     * Change the subscription status for the given theme.
     * Calls the service method and updates the local state on success.
     */
    changeSubscriptionStatus(themeId: number | undefined): void {
      if (!this.theme?.id) return; // Ensure the theme has a valid ID before proceeding
  
      /**
       * Using `take(1)` ensures that the Observable completes automatically 
       * after receiving the first response from the service. 
       * This prevents memory leaks and eliminates the need to manually 
       * unsubscribe in `ngOnDestroy()`. 
       * 
       * Without `take(1)`, the subscription would remain active, 
       * potentially causing unwanted behavior if the component is destroyed.
       */
      this.abonnementService.changeSubscriptionStatus(this.theme.id).pipe(take(1)).subscribe({
        next: (response) => {
          console.log(response); 
          this.theme.isSubscribed = !this.theme.isSubscribed; // change the subscription status
          this.subscriptionChanged.emit(); // Notify parent component about the change
        },
        error: (error) => console.error('Erreur de mise à jour de labonnement:', error)
      });
    }
  
}
