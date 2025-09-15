import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { MatchService } from '../../services/match.service';
import { ClassementService } from '../../services/classement.service';
import { Match, Classement } from '../../models/tournament.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavigationComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']

})
export class DashboardComponent implements OnInit {
  allMatches: Match[] = [];
  upcomingMatches: Match[] = [];
  completedMatches: Match[] = [];
  rankings: Classement[] = [];
  isLoading = true;

  constructor(
    private matchService: MatchService,
    private classementService: ClassementService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    this.isLoading = true;
    
    this.matchService.getAllMatches().subscribe({
      next: (matches) => {
        this.allMatches = matches;
        this.separateMatches();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading matches:', error);
        this.isLoading = false;
      }
    });
    
    this.classementService.getAllClassements().subscribe({
      next: (rankings) => {
        this.rankings = rankings.sort((a, b) => {
          if (b.points !== a.points) {
            return b.points - a.points;
          }
          return b.victoire - a.victoire;
        });
      },
      error: (error) => {
        console.error('Error loading rankings:', error);
      }
    });
  }

  private separateMatches(): void {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    this.completedMatches = this.allMatches.filter(match => match.termine);
    this.upcomingMatches = this.allMatches.filter(match => !match.termine);
    
    this.upcomingMatches.sort((a, b) => {
      const dateA = new Date(a.date);
      const dateB = new Date(b.date);
      return dateA.getTime() - dateB.getTime();
    });
    
    this.completedMatches.sort((a, b) => {
      const dateA = new Date(a.date);
      const dateB = new Date(b.date);
      return dateB.getTime() - dateA.getTime();
    });
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
      month: 'short', 
      day: 'numeric',
      year: 'numeric'
    });
  }

  getRankingBadgeColor(index: number): string {
    if (index === 0) return 'warning'; // Gold
    if (index === 1) return 'secondary'; // Silver
    if (index === 2) return 'warning'; // Bronze
    return 'primary';
  }

  getScoreBadgeColor(score1: number | undefined, score2: number | undefined, isTeam1: boolean): string {
    if (score1 === undefined || score2 === undefined) return 'secondary';
    
    if (isTeam1) {
      return score1 > score2 ? 'success' : 'danger';
    } else {
      return score2 > score1 ? 'success' : 'danger';
    }
  }

  getTeamName(match: Match, teamNumber: 1 | 2): string {
    if (teamNumber === 1) {
      return match.equipe1Nom || match.equipe1?.nom || 'Team 1';
    } else {
      return match.equipe2Nom || match.equipe2?.nom || 'Team 2';
    }
  }
  
  getCourtName(match: Match): string {
    return match.terrainNom || match.terrain?.nom || 'Unknown Court';
  }
  
  getRefereeName(match: Match): string {
    return match.arbitreNom || match.arbitre?.nom || 'No Referee';
  }
}