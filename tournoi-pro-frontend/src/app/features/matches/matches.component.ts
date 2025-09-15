import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { MatchService } from '../../services/match.service';
import { Match } from '../../models/tournament.model';

@Component({
  selector: 'app-matches',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavigationComponent],
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {
  allMatches: Match[] = [];
  filteredMatches: Match[] = [];
  completedMatches: Match[] = [];
  upcomingMatches: Match[] = [];
  
  isLoading = true;
  viewMode: 'card' | 'table' = 'card';
  
  selectedFilterType = 'all';
  specificDate = '';
  startDate = '';
  endDate = '';
  searchTerm = '';

  constructor(private matchService: MatchService) {}

  ngOnInit(): void {
    this.loadMatches();
  }

  private loadMatches(): void {
    this.isLoading = true;
    
    this.matchService.getAllMatches().subscribe({
      next: (matches) => {
        this.allMatches = matches;
        this.separateMatches();
        this.applyFilters();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading matches:', error);
        this.isLoading = false;
      }
    });
  }

  private separateMatches(): void {
    this.completedMatches = this.allMatches.filter(match => match.termine);
    this.upcomingMatches = this.allMatches.filter(match => !match.termine);
  }

  onFilterTypeChange(): void {
    this.specificDate = '';
    this.startDate = '';
    this.endDate = '';
    this.applyFilters();
  }

  applyDateFilter(): void {
    if (this.specificDate) {
      this.matchService.getMatchsByDate(this.specificDate).subscribe({
        next: (matches) => {
          this.filteredMatches = matches;
          this.applySearch();
        },
        error: (error) => {
          console.error('Error loading matches by date:', error);
        }
      });
    } else {
      this.applyFilters();
    }
  }

  applyDateRangeFilter(): void {
    if (this.startDate && this.endDate) {
      this.matchService.getMatchsBetweenDates(this.startDate, this.endDate).subscribe({
        next: (matches) => {
          this.filteredMatches = matches;
          this.applySearch();
        },
        error: (error) => {
          console.error('Error loading matches by date range:', error);
        }
      });
    } else {
      this.applyFilters();
    }
  }

  applyFilters(): void {
    let matches = [...this.allMatches];
    
    switch (this.selectedFilterType) {
      case 'completed':
        matches = this.completedMatches;
        break;
      case 'upcoming':
        matches = this.upcomingMatches;
        break;
      case 'all':
      default:
        matches = this.allMatches;
        break;
    }
    
    this.filteredMatches = matches;
    this.applySearch();
  }

  applySearch(): void {
    if (!this.searchTerm.trim()) {
      return;
    }
    
    const searchLower = this.searchTerm.toLowerCase();
    this.filteredMatches = this.filteredMatches.filter(match =>
      match.equipe1?.nom.toLowerCase().includes(searchLower) ||
      match.equipe2?.nom.toLowerCase().includes(searchLower)
    );
  }

  clearFilters(): void {
    this.selectedFilterType = 'all';
    this.specificDate = '';
    this.startDate = '';
    this.endDate = '';
    this.searchTerm = '';
    this.filteredMatches = [...this.allMatches];
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
      month: 'short', 
      day: 'numeric',
      year: 'numeric'
    });
  }

  getScoreClass(score1: number | undefined, score2: number | undefined): string {
    if (score1 === undefined || score2 === undefined) return '';
    return score1 > score2 ? 'text-success' : 'text-danger';
  }

  getScoreBadgeClass(score1: number | undefined, score2: number | undefined): string {
    if (score1 === undefined || score2 === undefined) return 'bg-secondary';
    return score1 > score2 ? 'bg-success' : 'bg-danger';
  }

  getNoMatchesMessage(): string {
    switch (this.selectedFilterType) {
      case 'completed':
        return 'No completed matches found.';
      case 'upcoming':
        return 'No upcoming matches scheduled.';
      case 'date':
        return 'No matches found for the selected date.';
      case 'range':
        return 'No matches found in the selected date range.';
      default:
        return this.searchTerm ? 'No matches found matching your search criteria.' : 'No matches available.';
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