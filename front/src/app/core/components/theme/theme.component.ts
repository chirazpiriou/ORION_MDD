import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Theme } from '../../models/theme.model';
import { AbonnementService } from '../../services/AbonnementService';
import { take } from 'rxjs';
import { SubscriptionStatusDTO } from '../../interfaces/subscriptionStatusDTO.interface';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss'],
})


export class ThemeComponent {
  @Input() theme!: Theme;
  @Input() isUserSubscribedPage: boolean = false;

 
  @Output() subscriptionChanged = new EventEmitter<void>();

  isLoading: boolean = false;
  constructor(private abonnementService: AbonnementService ) {}


  changeSubscriptionStatus(themeId: number | undefined): void {
    if (!this.theme?.id) return;

    this.abonnementService
        .changeSubscriptionStatus(this.theme.id)
        .pipe(take(1))
        .subscribe({
            next: (response: SubscriptionStatusDTO) => {
                this.theme.isSubscribed = response.isSubscribed; 
                console.log('etat', this.theme.isSubscribed);
          
                this.subscriptionChanged.emit();
             
            },
            error: (error) =>
                console.error('Erreur de mise à jour de l\'abonnement:', error),
        });
}
}
