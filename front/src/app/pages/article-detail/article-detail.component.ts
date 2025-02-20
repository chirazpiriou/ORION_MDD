import { Component, OnDestroy, OnInit } from '@angular/core'; 
import { ActivatedRoute } from '@angular/router'; 
import { filter, map, Subject, switchMap, takeUntil, tap } from 'rxjs'; 
import { Article } from 'src/app/core/models/article.model'; 
import { Commentaire } from 'src/app/core/models/commentaire.model'; 
import { ArticlesService } from 'src/app/core/services/ArticlesService'; 

@Component({
  selector: 'app-article-detail', 
  templateUrl: './article-detail.component.html', 
  styleUrls: ['./article-detail.component.scss'] 
})
export class ArticleDetailComponent implements OnInit, OnDestroy {
  article!: Article; // Article variable to hold the article data
  commentaires!: Commentaire[]; // Commentaire array to hold the article's comments
  article_id!: number | null; // Article ID extracted from the URL
  private destroy$: Subject<boolean> = new Subject(); // Subject to handle component destruction and unsubscribe
  error_str!: string; // Variable to hold error message

  constructor(private route: ActivatedRoute, private articleService: ArticlesService) { }
  
  ngOnInit(): void {
    // Using ActivatedRoute to extract the article ID from the URL and fetch the article details
    this.route.paramMap
      .pipe(
        // Extract the 'id' from the URL parameters
        map(params => params.get('id')),
        // Filter out null or undefined ID
        filter(id => !!id),
        // Convert the ID to a number
        map(id => Number(id)),
        // Save the article ID to a local variable
        tap(id => (this.article_id = id)),
        // Switch to the observable that fetches article details from the API
        switchMap(id => this.articleService.detail(id).pipe(takeUntil(this.destroy$))) // Automatically unsubscribe on component destroy
      )
      .subscribe({
        next: articleResponse => {
          // When data is successfully fetched, assign it to the component's properties
          console.log('article:', articleResponse);
          this.article = articleResponse;
          this.commentaires = articleResponse.commentaires; // Assign comments to the commentaire variable
          console.log('commentaires:', this.commentaires); // Ajouter un log pour vérifier les commentaires
        },
        error: error => {
          // Handle error and display a message
          this.error_str = error || "An error occurred while loading the article"; 
          console.error(this.error_str); // Log the error message
        }
      });
  }

  ngOnDestroy(): void {
    // Clean up the subscriptions when the component is destroyed to avoid memory leaks
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
