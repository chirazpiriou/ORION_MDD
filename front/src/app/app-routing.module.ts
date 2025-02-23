import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { ThemesListComponent } from './pages/themes-list/themes-list.component';
import { ArticleCreateComponent } from './pages/article-create/article-create.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import { UserComponent } from './pages/user/user.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register',component: RegisterComponent},
  { path: 'article/detail/:id', component: ArticleDetailComponent},
  { path: 'article/all', component: ArticleListComponent},
  { path: 'article/create', component: ArticleCreateComponent},
  { path: 'themes', component: ThemesListComponent },
  { path: 'profile', component: UserComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  
})
export class AppRoutingModule {}
