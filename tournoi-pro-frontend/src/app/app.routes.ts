import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login.component').then(c => c.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./auth/register/register.component').then(c => c.RegisterComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then(c => c.DashboardComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'matches',
    loadComponent: () => import('./features/matches/matches.component').then(c => c.MatchesComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'rankings',
    loadComponent: () => import('./features/rankings/rankings.component').then(c => c.RankingsComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    canActivate: [AdminGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./admin/dashboard/dashboard.component').then(c => c.AdminDashboardComponent)
      },
      {
        path: 'players',
        loadComponent: () => import('./admin/player-management/player-management.component').then(c => c.PlayerManagementComponent)
      },
      {
        path: 'teams',
        loadComponent: () => import('./admin/team-management/team-management.component').then(c => c.TeamManagementComponent)
      },
      {
        path: 'referees',
        loadComponent: () => import('./admin/referee-management/referee-management.component').then(c => c.RefereeManagementComponent)
      },
      {
        path: 'courts',
        loadComponent: () => import('./admin/court-management/court-management.component').then(c => c.CourtManagementComponent)
      },
      {
        path: 'matches',
        loadComponent: () => import('./admin/match-management/match-management.component').then(c => c.MatchManagementComponent)
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/login'
  }
];