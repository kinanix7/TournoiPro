import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { ClassementService } from '../../services/classement.service';
import { Classement } from '../../models/tournament.model';

@Component({
  selector: 'app-rankings',
  standalone: true,
  imports: [CommonModule, RouterModule, NavigationComponent],
  templateUrl: './rankings.component.html',
  styleUrls: ['./rankings.component.css']
})
export class RankingsComponent implements OnInit {
  rankings: Classement[] = [];
  isLoading = true;

  constructor(private classementService: ClassementService) {}

  ngOnInit(): void {
    this.loadRankings();
  }

  private loadRankings(): void {
    this.isLoading = true;
    
    this.classementService.getAllClassements().subscribe({
      next: (rankings) => {
        this.rankings = this.sortRankings(rankings);
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading rankings:', error);
        this.isLoading = false;
      }
    });
  }

  refreshRankings(): void {
    this.loadRankings();
  }

  private sortRankings(rankings: Classement[]): Classement[] {
    return rankings.sort((a, b) => {
      // Primary sort: points (descending)
      if (b.points !== a.points) {
        return b.points - a.points;
      }
      
      // Secondary sort: wins (descending)
      if (b.victoire !== a.victoire) {
        return b.victoire - a.victoire;
      }
      
      // Tertiary sort: losses (ascending)
      return a.defaite - b.defaite;
    });
  }

  getRankingRowClass(index: number): string {
    if (index < 3) return 'top-3-highlight';
    if (index < 5) return 'top-5-highlight';
    return '';
  }

  getRankBadgeClass(index: number): string {
    if (index === 0) return 'bg-warning'; // Gold
    if (index === 1) return 'bg-secondary'; // Silver
    if (index === 2) return 'bg-warning'; // Bronze
    if (index < 5) return 'bg-info';
    return 'bg-primary';
  }

  getRankIcon(index: number): string {
    if (index === 0) return 'bi-trophy-fill';
    if (index === 1) return 'bi-award-fill';
    if (index === 2) return 'bi-award-fill';
    return '';
  }

  getRankColor(index: number): string {
    if (index === 0) return '#ffd700'; // Gold
    if (index === 1) return '#c0c0c0'; // Silver
    if (index === 2) return '#cd7f32'; // Bronze
    return '';
  }

  getTotalGames(ranking: Classement): number {
    return ranking.victoire + ranking.defaite;
  }

  getWinRate(ranking: Classement): number {
    const totalGames = this.getTotalGames(ranking);
    if (totalGames === 0) return 0;
    return Math.round((ranking.victoire / totalGames) * 100);
  }

  getWinRateProgressClass(ranking: Classement): string {
    const winRate = this.getWinRate(ranking);
    if (winRate >= 80) return 'bg-success';
    if (winRate >= 60) return 'bg-info';
    if (winRate >= 40) return 'bg-warning';
    return 'bg-danger';
  }

  getTrendIcon(ranking: Classement): string {
    const winRate = this.getWinRate(ranking);
    if (winRate >= 70) return 'bi-trending-up';
    if (winRate >= 40) return 'bi-dash';
    return 'bi-trending-down';
  }

  getTrendColor(ranking: Classement): string {
    const winRate = this.getWinRate(ranking);
    if (winRate >= 70) return '#198754'; // Success green
    if (winRate >= 40) return '#6c757d'; // Neutral gray
    return '#dc3545'; // Danger red
  }

  getTotalWins(): number {
    return this.rankings.reduce((total, ranking) => total + ranking.victoire, 0);
  }

  getTotalPoints(): number {
    return this.rankings.reduce((total, ranking) => total + ranking.points, 0);
  }

  getAverageWinRate(): number {
    if (this.rankings.length === 0) return 0;
    const totalWinRate = this.rankings.reduce((total, ranking) => total + this.getWinRate(ranking), 0);
    return Math.round(totalWinRate / this.rankings.length);
  }
}