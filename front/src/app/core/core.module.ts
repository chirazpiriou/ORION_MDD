import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { ArticleListComponent } from '../pages/article-list/article-list.component';
import { ThemesListComponent } from '../pages/themes-list/themes-list.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import * as fr from '@angular/common/locales/fr';
import { ArticleCreateComponent } from '../pages/article-create/article-create.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ArticleComponent } from './components/article/article.component';

@NgModule({
  declarations: [
    HeaderComponent,
    ArticleListComponent,
    ThemesListComponent,
    ArticleCreateComponent,
    ArticleComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports:[
    HeaderComponent,
    MatToolbarModule,
    MatIconModule,
    MatListModule
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'fr-FR' }],
})
export class CoreModule { 
  constructor() {
    registerLocaleData(fr.default);
  }
}
