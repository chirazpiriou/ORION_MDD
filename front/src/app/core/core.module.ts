import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { ArticleListComponent } from '../pages/article-list/article-list.component';
import { ThemesListComponent } from '../pages/themes-list/themes-list.component';
import { RouterModule } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import * as fr from '@angular/common/locales/fr';
import { ArticleCreateComponent } from '../pages/article-create/article-create.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ArticleComponent } from './components/article/article.component';
import { ArticleDetailComponent } from '../pages/article-detail/article-detail.component';
import { ThemeComponent } from './components/theme/theme.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { CommentFormComponent } from './components/comment-form/comment-form.component';
import { AuthInterceptor } from '../interceptors/AuthInterceptor';

@NgModule({
  declarations: [
    HeaderComponent,
    ArticleListComponent,
    ThemesListComponent,
    ArticleCreateComponent,
    ArticleComponent,
    ArticleDetailComponent,
    ThemeComponent,
    CommentListComponent,
    CommentFormComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    HeaderComponent,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    ArticleListComponent,
    ArticleDetailComponent,
    ArticleCreateComponent,
    ThemeComponent,
    CommentListComponent,
    CommentFormComponent,
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
})
export class CoreModule {
  constructor() {
    registerLocaleData(fr.default);
  }
}
