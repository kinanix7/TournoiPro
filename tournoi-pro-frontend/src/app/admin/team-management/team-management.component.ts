import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { EquipeService } from '../../services/equipe.service';
import { JoueurService } from '../../services/joueur.service';
import { EquipeWithPlayersDto, JoueurWithTeamDto } from '../../models/tournament.model';

declare var bootstrap: any;

@Component({
  selector: 'app-team-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavigationComponent],
  templateUrl: './team-management.component.html',
  styleUrls: ['./team-management.component.css']
})
export class TeamManagementComponent implements OnInit {
  @ViewChild('teamModal') teamModal!: ElementRef;

  teams: EquipeWithPlayersDto[] = [];
  allPlayers: JoueurWithTeamDto[] = [];
  availablePlayers: JoueurWithTeamDto[] = [];

  currentTeam: Partial<EquipeWithPlayersDto> = {};
  selectedTeam: EquipeWithPlayersDto | null = null;

  isLoading = false;
  isEditMode = false;

  constructor(
    private equipeService: EquipeService,
    private joueurService: JoueurService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.isLoading = true;

    this.equipeService.getAllEquipes().subscribe({
      next: (teams) => {
        this.teams = teams || [];
        this.isLoading = false;
      },
      error: () => (this.isLoading = false)
    });

    this.joueurService.getAllJoueurs().subscribe({
      next: (players) => {
        this.allPlayers = players || [];
        this.updateAvailablePlayers();
      }
    });
  }

  updateAvailablePlayers(): void {
    this.availablePlayers = this.allPlayers.filter(p => !p.equipe);
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.currentTeam = { nom: '' };
  }

  openEditModal(team: EquipeWithPlayersDto): void {
    this.isEditMode = true;
    this.currentTeam = { ...team };
  }

  saveTeam(): void {
    if (!this.currentTeam.nom) return;

    const operation = this.isEditMode && this.currentTeam.id
      ? this.equipeService.updateEquipe(this.currentTeam.id, this.currentTeam as any)
      : this.equipeService.createEquipe(this.currentTeam as any);

    operation.subscribe({
      next: () => {
        this.loadData();
        this.closeTeamModal();
      }
    });
  }

  deleteTeam(team: EquipeWithPlayersDto): void {
    if (!confirm(`Delete team ${team.nom}?`)) return;

    this.equipeService.deleteEquipe(team.id).subscribe({
      next: () => this.loadData()
    });
  }

  addPlayerToTeam(player: JoueurWithTeamDto): void {
    if (!this.selectedTeam) return;

    this.equipeService.addPlayerToTeam(this.selectedTeam.id, player.id).subscribe({
      next: () => this.loadData()
    });
  }

  removePlayerFromTeam(player: JoueurWithTeamDto): void {
    if (!this.selectedTeam) return;

    this.equipeService.removePlayerFromTeam(this.selectedTeam.id, player.id).subscribe({
      next: () => this.loadData()
    });
  }

  openPlayersModal(team: EquipeWithPlayersDto): void {
    this.selectedTeam = team;
  }

  private closeTeamModal(): void {
    const modalEl = this.teamModal.nativeElement;
    const modal = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
    modal.hide();
  }
}
