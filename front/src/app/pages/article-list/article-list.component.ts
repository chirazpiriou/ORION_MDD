import { Component, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from 'rxjs';
import { Article } from 'src/app/core/models/article.model';
import { ArticlesService } from 'src/app/core/services/article.service';


@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent implements OnInit {

  articles!: Article[];
  articles$!:Observable<Article[]>;
  private destroy$: Subject<boolean> = new Subject();
  error_str!:string;

  constructor(private articleService:ArticlesService) { }
  
  ngOnInit(): void {
    const userEmail = 'jun.wei@tech.com'; 
    this.articleService.all(userEmail).pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (articlesFromServer: Article[]) => {
        this.articles = articlesFromServer;
        console.log(this.articles); 
       
      },
      error: (error) => {
        this.error_str =
          error ||
          "Une erreur est survenue lors du chargement de l'article";
      },
    });
    console.log(this.articles);
    
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
