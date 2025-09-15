import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { Match, Equipe, Terrain, Arbitre, EquipeWithPlayersDto, ArbitreWithTeamDto } from '../../models/tournament.model';
import { MatchService } from '../../services/match.service';
import { EquipeService } from '../../services/equipe.service';
import { TerrainService } from '../../services/terrain.service';
import { ArbitreService } from '../../services/arbitre.service';

declare var bootstrap: any;

@Component({
  selector: 'app-match-management',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule, NavigationComponent],
  templateUrl: './match-management.component.html',
  styleUrls: ['./match-management.component.css']
})
export class MatchManagementComponent implements OnInit {
  @ViewChild('matchModal') matchModal!: ElementRef;
  @ViewChild('scoreModal') scoreModal!: ElementRef;
  
  matches: Match[] = [];
  teams: EquipeWithPlayersDto[] = [];
  courts: Terrain[] = [];
  referees: ArbitreWithTeamDto[] = [];
  
  selectedMatch: Match | null = null;
  
  isLoading = true;
  isSaving = false;
  
  // Success message handling
  successMessage = '';
  showSuccess = false;
  
  // Error message handling
  errorMessage = '';
  showError = false;
  
  // Forms
  matchForm: FormGroup;
  scoreForm: FormGroup;

  constructor(
    private matchService: MatchService,
    private equipeService: EquipeService,
    private terrainService: TerrainService,
    private arbitreService: ArbitreService,
    private fb: FormBuilder
  ) {
    this.matchForm = this.fb.group({
      date: ['', Validators.required],
      heure: ['', Validators.required],
      equipe1Id: ['', Validators.required],
      equipe2Id: ['', Validators.required],
      terrainId: ['', Validators.required],
      arbitreId: ['']
    });

    this.scoreForm = this.fb.group({
      scoreEquipe1: [0, [Validators.required, Validators.min(0)]],
      scoreEquipe2: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.loadData();
  }

  refreshData(): void {
    // Refresh all match data
    this.loadData();
    this.showSuccessMessage('Data refreshed successfully!');
  }

  loadData(): void {
    this.isLoading = true;
    
    // Clear any existing messages
    this.showSuccess = false;
    this.showError = false;
    
    // Load matches with error handling
    this.matchService.getAllMatches().subscribe({
      next: (matches: Match[]) => {
        this.matches = matches;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading matches:', error);
        this.showErrorMessage('Failed to load matches. Using mock data.');
        this.matches = this.generateMockMatches();
        this.isLoading = false;
      }
    });

    // Load teams
    this.equipeService.getAllEquipes().subscribe({
      next: (teams: EquipeWithPlayersDto[]) => this.teams = teams,
      error: () => this.teams = this.generateMockTeams()
    });

    // Load courts
    this.terrainService.getAllTerrains().subscribe({
      next: (courts: Terrain[]) => this.courts = courts,
      error: () => this.courts = this.generateMockCourts()
    });

    // Load referees
    this.arbitreService.getAllArbitres().subscribe({
      next: (referees: ArbitreWithTeamDto[]) => this.referees = referees,
      error: () => this.referees = this.generateMockReferees()
    });
  }

  openCreateModal(): void {
    this.selectedMatch = null;
    this.matchForm.reset();
    
    // Set default date to tomorrow to avoid "date in the past" error
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const defaultDate = tomorrow.toISOString().split('T')[0];
    
    this.matchForm.patchValue({
      date: defaultDate
    });
  }

  
  openEditModal(match: Match): void {
    this.selectedMatch = match;
    
    // Format date and time for form inputs
    const formattedDate = this.formatDateForInput(match.date);
    const formattedTime = this.formatTimeForInput(match.heure);
    
    this.matchForm.patchValue({
      date: formattedDate,
      heure: formattedTime,
      equipe1Id: match.equipe1Id || match.equipe1?.id || '',
      equipe2Id: match.equipe2Id || match.equipe2?.id || '',
      terrainId: match.terrainId || match.terrain?.id || '',
      arbitreId: match.arbitreId || match.arbitre?.id || ''
    });
  }

  openScoreModal(match: Match): void {
    this.selectedMatch = match;
    this.scoreForm.patchValue({
      scoreEquipe1: match.scoreEquipe1 || 0,
      scoreEquipe2: match.scoreEquipe2 || 0
    });
  }

  saveMatch(): void {
    if (this.matchForm.valid) {
      this.isSaving = true;
      
      const formData = this.matchForm.value;
      
      // Validate that teams are different
      if (formData.equipe1Id === formData.equipe2Id) {
        this.showErrorMessage('A team cannot play against itself');
        this.isSaving = false;
        return;
      }
      
      // Validate date is not in the past
      const matchDate = new Date(formData.date);
      const today = new Date();
      today.setHours(0, 0, 0, 0); // Reset time part for comparison
      
      if (matchDate < today) {
        this.showErrorMessage('Match date cannot be in the past');
        this.isSaving = false;
        return;
      }
      
      // Find team, terrain, and arbitre objects
      const equipe1 = this.teams.find(t => t.id === parseInt(formData.equipe1Id));
      const equipe2 = this.teams.find(t => t.id === parseInt(formData.equipe2Id));
      const terrain = this.courts.find(c => c.id === parseInt(formData.terrainId));
      const arbitre = formData.arbitreId ? this.referees.find(r => r.id === parseInt(formData.arbitreId)) : undefined;
      
      if (!equipe1 || !equipe2 || !terrain) {
        this.showErrorMessage('Please select valid teams and court');
        this.isSaving = false;
        return;
      }
      
      // Prepare match object for backend
      const matchToSave: Partial<Match> = {
        date: formData.date,
        heure: formData.heure,
        equipe1: {
          id: equipe1.id,
          nom: equipe1.nom
        } as Equipe,
        equipe2: {
          id: equipe2.id,
          nom: equipe2.nom
        } as Equipe,
        terrain: {
          id: terrain.id,
          nom: terrain.nom
        } as Terrain,
        termine: this.selectedMatch?.termine || false,
        scoreEquipe1: this.selectedMatch?.scoreEquipe1 || 0,
        scoreEquipe2: this.selectedMatch?.scoreEquipe2 || 0
      };
      
      // Add arbitre if selected
      if (arbitre) {
        matchToSave.arbitre = {
          id: arbitre.id,
          nom: arbitre.nom
        } as Arbitre;
      }
      
      const operation = this.selectedMatch
        ? this.matchService.updateMatch(this.selectedMatch.id, matchToSave as Match)
        : this.matchService.createMatch(matchToSave as Match);
      
      operation.subscribe({
        next: (match) => {
          this.handleSaveSuccess(match);
        },
        error: (error) => {
          console.error('Error saving match:', error);
          this.isSaving = false;
          
          // Extract error message from response
          let errorMessage = 'Failed to save match. Please try again.';
          if (error.error) {
            if (typeof error.error === 'string') {
              errorMessage = error.error;
            } else if (error.error.message) {
              errorMessage = error.error.message;
            }
          } else if (error.message) {
            errorMessage = error.message;
          }
          
          this.showErrorMessage(errorMessage);
        }
      });
    } else {
      // Mark all fields as touched to show validation errors
      Object.keys(this.matchForm.controls).forEach(key => {
        this.matchForm.get(key)?.markAsTouched();
      });
    }
  }

  updateScore(): void {
    if (this.scoreForm.valid && this.selectedMatch) {
      this.isSaving = true;
      
      const scoreData = this.scoreForm.value;
      
      // Prepare updated match with scores
      const updatedMatch: Partial<Match> = {
        ...this.selectedMatch,
        scoreEquipe1: scoreData.scoreEquipe1,
        scoreEquipe2: scoreData.scoreEquipe2,
        termine: true
      };
      
      // Ensure team objects are properly structured
      if (this.selectedMatch.equipe1Id && this.selectedMatch.equipe1Nom) {
        updatedMatch.equipe1 = {
          id: this.selectedMatch.equipe1Id,
          nom: this.selectedMatch.equipe1Nom
        } as Equipe;
      }
      
      if (this.selectedMatch.equipe2Id && this.selectedMatch.equipe2Nom) {
        updatedMatch.equipe2 = {
          id: this.selectedMatch.equipe2Id,
          nom: this.selectedMatch.equipe2Nom
        } as Equipe;
      }
      
      if (this.selectedMatch.terrainId && this.selectedMatch.terrainNom) {
        updatedMatch.terrain = {
          id: this.selectedMatch.terrainId,
          nom: this.selectedMatch.terrainNom
        } as Terrain;
      }
      
      if (this.selectedMatch.arbitreId && this.selectedMatch.arbitreNom) {
        updatedMatch.arbitre = {
          id: this.selectedMatch.arbitreId,
          nom: this.selectedMatch.arbitreNom
        } as Arbitre;
      }
      
      this.matchService.updateMatch(this.selectedMatch.id, updatedMatch as Match).subscribe({
        next: (match) => {
          // Update local match
          const index = this.matches.findIndex(m => m.id === this.selectedMatch!.id);
          if (index !== -1) {
            this.matches[index] = match;
          }
          
          this.isSaving = false;
          this.closeScoreModal();
          this.showSuccessMessage('Match score updated successfully!');
        },
        error: (error) => {
          console.error('Error updating score:', error);
          this.isSaving = false;
          
          // Extract error message
          let errorMessage = 'Failed to update match score. Please try again.';
          if (error.error) {
            if (typeof error.error === 'string') {
              errorMessage = error.error;
            } else if (error.error.message) {
              errorMessage = error.error.message;
            }
          } else if (error.message) {
            errorMessage = error.message;
          }
          
          this.showErrorMessage(errorMessage);
        }
      });
    }
  }

  deleteMatch(matchId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce match ?')) {
      this.matchService.deleteMatch(matchId).subscribe({
        next: () => {
          this.matches = this.matches.filter(m => m.id !== matchId);
          this.showSuccessMessage('Match deleted successfully!');
        },
        error: (error) => {
          console.error('Error deleting match:', error);
          
          // Extract error message
          let errorMessage = 'Failed to delete match. Please try again.';
          if (error.error) {
            if (typeof error.error === 'string') {
              errorMessage = error.error;
            } else if (error.error.message) {
              errorMessage = error.error.message;
            }
          } else if (error.message) {
            errorMessage = error.message;
          }
          
          this.showErrorMessage(errorMessage);
        }
      });
    }
  }

  // Helper methods
  getStatusText(isFinished: boolean): string {
    return isFinished ? 'Terminé' : 'Programmé';
  }

  getMatchCount(status: string): number {
    if (status === 'TERMINE') {
      return this.matches.filter(m => m.termine).length;
    } else if (status === 'PROGRAMME') {
      return this.matches.filter(m => !m.termine).length;
    }
    return 0;
  }

  // Helper methods for display
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

  getAvailableTeams(): EquipeWithPlayersDto[] {
    const selectedTeam1 = this.matchForm.get('equipe1Id')?.value;
    // Filter out the selected team 1 to prevent selecting the same team for both teams
    return selectedTeam1 
      ? this.teams.filter(team => team.id !== parseInt(selectedTeam1)) 
      : this.teams;
  }

  // Success message handling
  showSuccessMessage(message: string): void {
    this.successMessage = message;
    this.showSuccess = true;
    // Auto-dismiss after 3 seconds
    setTimeout(() => {
      this.showSuccess = false;
    }, 3000);
  }

  dismissSuccessMessage(): void {
    this.showSuccess = false;
  }

  // Error message handling
  showErrorMessage(message: string): void {
    this.errorMessage = message;
    this.showError = true;
    // Auto-dismiss after 5 seconds
    setTimeout(() => {
      this.showError = false;
    }, 5000);
  }

  dismissErrorMessage(): void {
    this.showError = false;
  }

  // Mock data generators
  generateMockMatches(): Match[] {
    return [
      {
        id: 1,
        date: '2024-01-15',
        heure: '10:00',
        equipe1Id: 1,
        equipe1Nom: 'Les Lions',
        equipe2Id: 2,
        equipe2Nom: 'Les Aigles',
        terrainId: 1,
        terrainNom: 'Terrain A',
        arbitreId: 1,
        arbitreNom: 'Martin Jean',
        termine: false,
        scoreEquipe1: 0,
        scoreEquipe2: 0
      }
    ];
  }

  generateMockTeams(): EquipeWithPlayersDto[] {
    return [
      { id: 1, nom: 'Les Lions', joueurs: [], nbVictoires: 5, nbDefaites: 2, points: 12 },
      { id: 2, nom: 'Les Aigles', joueurs: [], nbVictoires: 4, nbDefaites: 3, points: 11 },
      { id: 3, nom: 'Les Tigres', joueurs: [], nbVictoires: 6, nbDefaites: 1, points: 13 },
      { id: 4, nom: 'Les Panthers', joueurs: [], nbVictoires: 3, nbDefaites: 4, points: 10 }
    ];
  }

  generateMockCourts(): Terrain[] {
    return [
      { id: 1, nom: 'Terrain A', localisation: 'Gymnase Principal' },
      { id: 2, nom: 'Terrain B', localisation: 'Gymnase Secondaire' }
    ];
  }

  generateMockReferees(): ArbitreWithTeamDto[] {
    return [
      { id: 1, nom: 'Martin Jean' },
      { id: 2, nom: 'Durand Marie' }
    ];
  }

  // Date formatting utility
  formatDate(dateString: string): string {
    if (!dateString) return '';
    
    // Handle different date formats
    let date: Date;
    
    if (dateString.match(/^\d{4}-\d{2}-\d{2}$/)) {
      // YYYY-MM-DD format
      date = new Date(dateString);
    } else {
      // Other formats
      date = new Date(dateString);
    }
    
    if (isNaN(date.getTime())) return dateString;
    
    // Format as DD/MM/YYYY
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    
    return `${day}/${month}/${year}`;
  }

  // Date and time formatting utilities
  formatDateForInput(dateString: string): string {
    // Convert from backend format (YYYY-MM-DD) to HTML date input format
    if (!dateString) return '';
    
    // If already in YYYY-MM-DD format, return as is
    if (dateString.match(/^\d{4}-\d{2}-\d{2}$/)) {
      return dateString;
    }
    
    // Handle other date formats if needed
    const date = new Date(dateString);
    if (isNaN(date.getTime())) return '';
    
    return date.toISOString().split('T')[0];
  }
  
  formatTimeForInput(timeString: string): string {
    // Convert from backend format (HH:mm:ss) to HTML time input format (HH:mm)
    if (!timeString) return '';
    
    // If already in HH:mm format, return as is
    if (timeString.match(/^\d{2}:\d{2}$/)) {
      return timeString;
    }
    
    // If in HH:mm:ss format, remove seconds
    if (timeString.match(/^\d{2}:\d{2}:\d{2}$/)) {
      return timeString.substring(0, 5);
    }
    
    return timeString;
  }

  private handleSaveSuccess(match: Match): void {
    // Update local matches array
    if (this.selectedMatch) {
      const index = this.matches.findIndex(m => m.id === this.selectedMatch!.id);
      if (index !== -1) {
        this.matches[index] = match;
      }
    } else {
      this.matches.push(match);
    }
    
    this.isSaving = false;
    this.closeMatchModal();
    
    // Show success feedback to user
    this.showSuccessMessage(this.selectedMatch ? 'Match updated successfully!' : 'Match scheduled successfully!');
  }

  private closeMatchModal(): void {
    if (this.matchModal) {
      const modalElement = this.matchModal.nativeElement;
      try {
        const modal = bootstrap.Modal.getInstance(modalElement);
        if (modal) {
          modal.hide();
        } else {
          const newModal = new bootstrap.Modal(modalElement);
          newModal.hide();
        }
      } catch (error) {
        console.error('Error closing modal with Bootstrap API:', error);
        // Fallback: simulate clicking the close button
        const closeButton = modalElement.querySelector('.btn-close');
        if (closeButton) {
          (closeButton as HTMLElement).click();
        }
      }
    }
  }

  private closeScoreModal(): void {
    if (this.scoreModal) {
      const modalElement = this.scoreModal.nativeElement;
      try {
        const modal = bootstrap.Modal.getInstance(modalElement);
        if (modal) {
          modal.hide();
        } else {
          const newModal = new bootstrap.Modal(modalElement);
          newModal.hide();
        }
      } catch (error) {
        console.error('Error closing modal with Bootstrap API:', error);
        // Fallback: simulate clicking the close button
        const closeButton = modalElement.querySelector('.btn-close');
        if (closeButton) {
          (closeButton as HTMLElement).click();
        }
      }
    }
  }
}