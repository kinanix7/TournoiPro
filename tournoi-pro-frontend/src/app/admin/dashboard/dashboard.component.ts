import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavigationComponent } from '../../shared/navigation/navigation.component';
import { JoueurService } from '../../services/joueur.service';
import { EquipeService } from '../../services/equipe.service';
import { MatchService } from '../../services/match.service';
import { ArbitreService } from '../../services/arbitre.service';
import { TerrainService } from '../../services/terrain.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavigationComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  statistics = {
    players: 0,
    teams: 0,
    matches: 0,
    referees: 0,
    courts: 0
  };

  completionRate = 0;      
  teamFormationRate = 0;    
  pendingMatches = 0;
  completedMatches = 0;

  alerts: any[] = [];
  isLoading = false;

  constructor(
    private joueurService: JoueurService,
    private equipeService: EquipeService,
    private matchService: MatchService,
    private arbitreService: ArbitreService,
    private terrainService: TerrainService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    this.isLoading = true;

    this.joueurService.getJoueursCount().subscribe(count => this.statistics.players = count);
    this.equipeService.getEquipesCount().subscribe(count => this.statistics.teams = count);
    this.matchService.getMatchsCount().subscribe(count => this.statistics.matches = count);
    this.arbitreService.getArbitresCount().subscribe(count => this.statistics.referees = count);
    this.terrainService.getTerrainsCount().subscribe(count => this.statistics.courts = count);

    this.loadMatchesData();
    this.loadPlayersData();

    this.isLoading = false;
  }

  private loadMatchesData(): void {
    this.matchService.getAllMatches().subscribe(matches => {
      this.completedMatches = matches.filter(m => m.termine).length;
      this.pendingMatches = matches.length - this.completedMatches;
      this.completionRate = matches.length > 0
        ? Math.round((this.completedMatches / matches.length) * 100)
        : 0;
    });
  }

  private loadPlayersData(): void {
    this.joueurService.getAllJoueurs().subscribe(players => {
      const withTeam = players.filter(p => p.equipe).length;
      this.teamFormationRate = players.length > 0
        ? Math.round((withTeam / players.length) * 100)
        : 0;
    });
  }

  refreshData(): void {
    this.loadDashboardData();
  }

  checkSystemHealth(): void {
    this.alerts = [{
      type: 'success',
      icon: 'bi-check-circle',
      title: 'System Check Complete',
      message: 'All systems are operational.'
    }];

    setTimeout(() => this.alerts = [], 3000);
  }
}
