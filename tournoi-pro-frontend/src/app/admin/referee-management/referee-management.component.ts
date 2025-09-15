import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { ArbitreService } from '../../services/arbitre.service';
import { EquipeService } from '../../services/equipe.service';
import { ArbitreWithTeamDto, EquipeWithPlayersDto } from '../../models/tournament.model';

declare var bootstrap: any;

@Component({
  selector: 'app-referee-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavigationComponent],
  templateUrl: './referee-management.component.html',
  styleUrls: ['./referee-management.component.css']
})
export class RefereeManagementComponent implements OnInit {
  @ViewChild('refereeModal') refereeModal!: ElementRef;

  referees: ArbitreWithTeamDto[] = [];
  teams: EquipeWithPlayersDto[] = [];

  currentReferee: Partial<ArbitreWithTeamDto> = {};
  selectedTeamId: number | null = null;
  isEditMode = false;
  isLoading = false;

  constructor(
    private arbitreService: ArbitreService,
    private equipeService: EquipeService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.isLoading = true;

    this.arbitreService.getAllArbitres().subscribe({
      next: (refs) => (this.referees = refs || []),
      error: () => (this.referees = [])
    });

    this.equipeService.getAllEquipes().subscribe({
      next: (teams) => (this.teams = teams || []),
      error: () => (this.teams = [])
    });

    this.isLoading = false;
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.currentReferee = { nom: '' };
    this.selectedTeamId = null;
  }

  openEditModal(referee: ArbitreWithTeamDto): void {
    this.isEditMode = true;
    this.currentReferee = { ...referee };
    this.selectedTeamId = referee.equipeLiee?.id || null;
  }

  saveReferee(): void {
    if (!this.currentReferee.nom) return;

    const refData: Partial<ArbitreWithTeamDto> = { nom: this.currentReferee.nom };
    if (this.isEditMode && this.currentReferee.id) refData.id = this.currentReferee.id;

    const operation = this.isEditMode
      ? this.arbitreService.updateArbitre(this.currentReferee.id!, refData)
      : this.arbitreService.createArbitre(refData);

    operation.subscribe({
      next: (ref) => {
        if (this.selectedTeamId) {
          this.arbitreService.assignRefereeToTeam(ref.id, this.selectedTeamId!).subscribe({
            next: () => this.loadData()
          });
        } else {
          this.loadData();
        }
        this.closeRefereeModal();
      }
    });
  }

  deleteReferee(ref: ArbitreWithTeamDto): void {
    if (!confirm(`Delete referee ${ref.nom}?`)) return;

    this.arbitreService.deleteArbitre(ref.id).subscribe({
      next: () => this.loadData()
    });
  }

  removeFromTeam(ref: ArbitreWithTeamDto): void {
    this.arbitreService.removeRefereeFromTeam(ref.id).subscribe({
      next: () => this.loadData()
    });
  }

  private closeRefereeModal(): void {
    const modalEl = this.refereeModal.nativeElement;
    const modal = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
    modal.hide();
  }
}
