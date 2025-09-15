import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { JoueurService } from '../../services/joueur.service';
import { EquipeService } from '../../services/equipe.service';
import { JoueurWithTeamDto, EquipeWithPlayersDto, TypeJoueur } from '../../models/tournament.model';

declare var bootstrap: any;

@Component({
  selector: 'app-player-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavigationComponent],
  templateUrl: './player-management.component.html',
  styleUrls: ['./player-management.component.css']
})
export class PlayerManagementComponent implements OnInit {
  @ViewChild('playerModal') playerModal!: ElementRef;

  players: JoueurWithTeamDto[] = [];
  filteredPlayers: JoueurWithTeamDto[] = [];
  availableTeams: EquipeWithPlayersDto[] = [];

  currentPlayer: Partial<JoueurWithTeamDto> = {};
  selectedTeamId: number | null = null;
  isEditMode = false;

  successMessage = '';
  errorMessage = '';

  searchTerm = '';
  selectedType = '';
  teamFilter = '';

  TypeJoueur = TypeJoueur;

  constructor(
    private joueurService: JoueurService,
    private equipeService: EquipeService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.joueurService.getAllJoueurs().subscribe({
      next: (players) => {
        this.players = players || [];
        this.filteredPlayers = [...this.players];
      }
    });

    this.equipeService.getAllEquipes().subscribe({
      next: (teams) => {
        this.availableTeams = teams || [];
      }
    });
  }

  applyFilters(): void {
    let filtered = [...this.players];

    if (this.searchTerm) {
      filtered = filtered.filter(p =>
        p.nom.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }

    if (this.selectedType) {
      filtered = filtered.filter(p => p.type === this.selectedType);
    }

    if (this.teamFilter === 'assigned') {
      filtered = filtered.filter(p => p.equipe);
    } else if (this.teamFilter === 'unassigned') {
      filtered = filtered.filter(p => !p.equipe);
    }

    this.filteredPlayers = filtered;
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.selectedType = '';
    this.teamFilter = '';
    this.applyFilters();
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.currentPlayer = { nom: '', role: '', type: TypeJoueur.ELEVE };
    this.selectedTeamId = null;
    this.openModal();
  }

  openEditModal(player: JoueurWithTeamDto): void {
    this.isEditMode = true;
    this.currentPlayer = { ...player };
    this.selectedTeamId = player.equipe?.id || null;
    this.openModal();
  }

  savePlayer(): void {
  if (!this.currentPlayer.nom || !this.currentPlayer.role) return;

  const playerToSave = {
    nom: this.currentPlayer.nom,
    role: this.currentPlayer.role,
    type: this.currentPlayer.type,
    id: this.isEditMode ? this.currentPlayer.id : undefined
  };

  const operation = this.isEditMode
    ? this.joueurService.updateJoueur(playerToSave.id!, playerToSave)
    : this.joueurService.createJoueur(playerToSave);

  operation.subscribe({
    next: () => {
      this.joueurService.getAllJoueurs().subscribe({
        next: (players) => {
          this.players = players || [];
          this.applyFilters(); 
          this.closeModal();
          this.showSuccess(this.isEditMode ? 'Player updated!' : 'Player created!');
        },
        error: () => this.showError('Failed to load updated players')
      });
    },
    error: () => this.showError('Failed to save player')
  });
}


  deletePlayer(player: JoueurWithTeamDto): void {
    if (confirm(`Delete ${player.nom}?`)) {
      this.joueurService.deleteJoueur(player.id).subscribe({
        next: () => {
          this.players = this.players.filter(p => p.id !== player.id);
          this.applyFilters();
          this.showSuccess(`Player ${player.nom} deleted!`);
        },
        error: () => this.showError('Failed to delete player')
      });
    }
  }

  openModal(): void {
    const modal = new bootstrap.Modal(this.playerModal.nativeElement);
    modal.show();
  }

  closeModal(): void {
    const modal = bootstrap.Modal.getInstance(this.playerModal.nativeElement);
    modal?.hide();
  }

  showSuccess(msg: string): void {
    this.successMessage = msg;
    setTimeout(() => this.successMessage = '', 3000);
  }

  showError(msg: string): void {
    this.errorMessage = msg;
    setTimeout(() => this.errorMessage = '', 5000);
  }
}
