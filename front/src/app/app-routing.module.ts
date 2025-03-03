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
import { UnauthGuard } from './core/guards/UnauthGuard';
import { AuthGuard } from './core/guards/AuthGuard';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [UnauthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [UnauthGuard],
  },
  {
    path: 'article/detail/:id',
    component: ArticleDetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'article/all',
    component: ArticleListComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'article/create',
    component: ArticleCreateComponent,
    canActivate: [AuthGuard],
  },
  { path: 'themes', component: ThemesListComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: UserComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
