import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { TerrainService } from '../../services/terrain.service';
import { MatchService } from '../../services/match.service';
import { Terrain, Match } from '../../models/tournament.model';

declare var bootstrap: any;

@Component({
  selector: 'app-court-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavigationComponent],
  templateUrl: './court-management.component.html',
  styleUrls: ['./court-management.component.css']
})
export class CourtManagementComponent implements OnInit {
  @ViewChild('courtModal') courtModal!: ElementRef;

  courts: Terrain[] = [];
  filteredCourts: Terrain[] = [];
  allMatches: Match[] = [];

  currentCourt: Partial<Terrain> = {};
  selectedCourt: Terrain | null = null;

  isLoading = false;
  isSaving = false;
  isEditMode = false;

  searchTerm = '';
  locationSearch = '';

  constructor(
    private terrainService: TerrainService,
    private matchService: MatchService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.isLoading = true;

    this.terrainService.getAllTerrains().subscribe({
      next: (courts) => {
        this.courts = courts || [];
        this.applyFilters();
      },
      error: () => {
        this.courts = [];
        this.applyFilters();
      }
    });

    this.matchService.getAllMatches().subscribe({
      next: (matches) => (this.allMatches = matches || []),
      error: () => (this.allMatches = [])
    });

    this.isLoading = false;
  }

  applyFilters(): void {
    let filtered = [...this.courts];

    if (this.searchTerm.trim()) {
      const s = this.searchTerm.toLowerCase();
      filtered = filtered.filter(c => c.nom.toLowerCase().includes(s));
    }

    if (this.locationSearch.trim()) {
      const l = this.locationSearch.toLowerCase();
      filtered = filtered.filter(c => c.localisation.toLowerCase().includes(l));
    }

    this.filteredCourts = filtered;
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.locationSearch = '';
    this.applyFilters();
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.currentCourt = { nom: '', localisation: '' };
  }

  openEditModal(court: Terrain): void {
    this.isEditMode = true;
    this.currentCourt = { ...court };
  }

  saveCourt(): void {
    if (!this.currentCourt.nom || !this.currentCourt.localisation) return;

    this.isSaving = true;

    const data: Partial<Terrain> = {
      nom: this.currentCourt.nom,
      localisation: this.currentCourt.localisation
    };

    if (this.isEditMode && this.currentCourt.id) data.id = this.currentCourt.id;

    const operation = this.isEditMode
      ? this.terrainService.updateTerrain(this.currentCourt.id!, data as Terrain)
      : this.terrainService.createTerrain(data as Terrain);

    operation.subscribe({
      next: () => {
        this.loadData();
        this.closeCourtModal();
        this.isSaving = false;
      },
      error: () => (this.isSaving = false)
    });
  }

  deleteCourt(court: Terrain): void {
    if (!confirm(`Delete court "${court.nom}"?`)) return;

    this.terrainService.deleteTerrain(court.id).subscribe({
      next: () => this.loadData()
    });
  }

  // Helpers
  getCourtMatchCount(court: Terrain): number {
    return this.allMatches.filter(m => m.terrain?.id === court.id).length;
  }

  getNextMatch(court: Terrain): Match | null {
    const up = this.allMatches
      .filter(m => m.terrain?.id === court.id && !m.termine)
      .sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
    return up.length > 0 ? up[0] : null;
  }

  private closeCourtModal(): void {
    const modalEl = this.courtModal.nativeElement;
    const modal = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
    modal.hide();
  }
}
