import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Joueur, TypeJoueur, JoueurWithTeamDto } from '../models/tournament.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JoueurService {
  private apiUrl = `${environment.apiUrl}/api/joueurs`;

  constructor(private http: HttpClient) { }

  getAllJoueurs(): Observable<JoueurWithTeamDto[]> {
    return this.http.get<JoueurWithTeamDto[]>(this.apiUrl);
  }

  getAllJoueursWithTeamInfo(): Observable<JoueurWithTeamDto[]> {
    return this.http.get<JoueurWithTeamDto[]>(`${this.apiUrl}/with-teams`);
  }

  getJoueurById(id: number): Observable<JoueurWithTeamDto> {
    return this.http.get<JoueurWithTeamDto>(`${this.apiUrl}/${id}`);
  }

  createJoueur(joueur: Partial<JoueurWithTeamDto>): Observable<JoueurWithTeamDto> {
    // Remove ID for new entities
    const joueurToCreate = { ...joueur };
    delete (joueurToCreate as any).id;
    
    // Set proper headers for JSON content
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    // Log the data being sent for debugging
    console.log('Sending player data to create:', joueurToCreate);
    
    return this.http.post<JoueurWithTeamDto>(this.apiUrl, joueurToCreate, { headers });
  }

  updateJoueur(id: number, joueur: Partial<JoueurWithTeamDto>): Observable<JoueurWithTeamDto> {
    // Set proper headers for JSON content
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    // Log the data being sent for debugging
    console.log('Sending player data to update:', joueur);
    
    return this.http.put<JoueurWithTeamDto>(`${this.apiUrl}/${id}`, joueur, { headers });
  }

  deleteJoueur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getJoueursCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/joueurs`);
  }

  getJoueursByType(type: TypeJoueur): Observable<JoueurWithTeamDto[]> {
    return this.http.get<JoueurWithTeamDto[]>(`${this.apiUrl}/type/${type}`);
  }

  searchJoueursByNom(nom: string): Observable<JoueurWithTeamDto[]> {
    const params = new HttpParams().set('nom', nom);
    return this.http.get<JoueurWithTeamDto[]>(`${this.apiUrl}/search`, { params });
  }

  getJoueursByEquipeId(equipeId: number): Observable<JoueurWithTeamDto[]> {
    return this.http.get<JoueurWithTeamDto[]>(`${this.apiUrl}/equipes/${equipeId}/joueurs`);
  }

  assignPlayerToTeam(playerId: number, equipeId: number): Observable<JoueurWithTeamDto> {
    // Set proper headers for JSON content
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    return this.http.put<JoueurWithTeamDto>(`${this.apiUrl}/${playerId}/assign-team/${equipeId}`, {}, { headers });
  }

  unassignPlayerFromTeam(playerId: number): Observable<JoueurWithTeamDto> {
    // Set proper headers for JSON content
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    return this.http.put<JoueurWithTeamDto>(`${this.apiUrl}/${playerId}/unassign-team`, {}, { headers });
  }

  getUnassignedPlayers(): Observable<JoueurWithTeamDto[]> {
    return this.http.get<JoueurWithTeamDto[]>(`${this.apiUrl}/unassigned`);
  }
}