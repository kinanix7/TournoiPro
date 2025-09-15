import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipe, EquipeWithPlayersDto } from '../models/tournament.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EquipeService {
  private apiUrl = `${environment.apiUrl}/api/equipes`;

  constructor(private http: HttpClient) { }

  getAllEquipes(): Observable<EquipeWithPlayersDto[]> {
    return this.http.get<EquipeWithPlayersDto[]>(`${this.apiUrl}/with-basic-info`);
  }

  getEquipeById(id: number): Observable<EquipeWithPlayersDto> {
    return this.http.get<EquipeWithPlayersDto>(`${this.apiUrl}/${id}`);
  }

  createEquipe(equipe: Partial<EquipeWithPlayersDto>): Observable<EquipeWithPlayersDto> {
    // Remove ID for new entities
    const equipeToCreate = { ...equipe };
    delete (equipeToCreate as any).id;
    return this.http.post<EquipeWithPlayersDto>(this.apiUrl, equipeToCreate);
  }

  updateEquipe(id: number, equipe: Partial<EquipeWithPlayersDto>): Observable<EquipeWithPlayersDto> {
    return this.http.put<EquipeWithPlayersDto>(`${this.apiUrl}/${id}`, equipe);
  }

  deleteEquipe(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getEquipesCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/equipes`);
  }

  getEquipesByNom(nom: string): Observable<EquipeWithPlayersDto[]> {
    return this.http.get<EquipeWithPlayersDto[]>(`${this.apiUrl}/equipes/${nom}`);
  }

  getEquipesByPoule(pouleId: number): Observable<EquipeWithPlayersDto[]> {
    return this.http.get<EquipeWithPlayersDto[]>(`${this.apiUrl}/poule/${pouleId}`);
  }

  getEquipesSansPoule(): Observable<EquipeWithPlayersDto[]> {
    return this.http.get<EquipeWithPlayersDto[]>(`${this.apiUrl}/sans-poule`);
  }

  addPlayerToTeam(equipeId: number, playerId: number): Observable<EquipeWithPlayersDto> {
    return this.http.post<EquipeWithPlayersDto>(`${this.apiUrl}/${equipeId}/players/${playerId}`, {});
  }

  removePlayerFromTeam(equipeId: number, playerId: number): Observable<EquipeWithPlayersDto> {
    return this.http.delete<EquipeWithPlayersDto>(`${this.apiUrl}/${equipeId}/players/${playerId}`);
  }

  addRefereeToTeam(equipeId: number, arbitreId: number): Observable<EquipeWithPlayersDto> {
    return this.http.post<EquipeWithPlayersDto>(`${this.apiUrl}/${equipeId}/referees/${arbitreId}`, {});
  }

  removeRefereeFromTeam(equipeId: number, arbitreId: number): Observable<EquipeWithPlayersDto> {
    return this.http.delete<EquipeWithPlayersDto>(`${this.apiUrl}/${equipeId}/referees/${arbitreId}`);
  }
}