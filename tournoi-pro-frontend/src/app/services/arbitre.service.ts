import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Arbitre, ArbitreWithTeamDto } from '../models/tournament.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArbitreService {
  private apiUrl = `${environment.apiUrl}/api/arbitres`;

  constructor(private http: HttpClient) { }

  getAllArbitres(): Observable<ArbitreWithTeamDto[]> {
    return this.http.get<ArbitreWithTeamDto[]>(this.apiUrl);
  }

  getAllArbitresWithTeamInfo(): Observable<ArbitreWithTeamDto[]> {
    return this.http.get<ArbitreWithTeamDto[]>(`${this.apiUrl}/with-teams`);
  }

  getArbitreById(id: number): Observable<ArbitreWithTeamDto> {
    return this.http.get<ArbitreWithTeamDto>(`${this.apiUrl}/${id}`);
  }

  createArbitre(arbitre: Partial<ArbitreWithTeamDto>): Observable<ArbitreWithTeamDto> {
    const arbitreToCreate = { ...arbitre };
    delete (arbitreToCreate as any).id;
    return this.http.post<ArbitreWithTeamDto>(this.apiUrl, arbitreToCreate);
  }

  updateArbitre(id: number, arbitre: Partial<ArbitreWithTeamDto>): Observable<ArbitreWithTeamDto> {
    return this.http.put<ArbitreWithTeamDto>(`${this.apiUrl}/${id}`, arbitre);
  }

  assignRefereeToTeam(arbitreId: number, equipeId: number): Observable<ArbitreWithTeamDto> {
    return this.http.put<ArbitreWithTeamDto>(`${this.apiUrl}/${arbitreId}/assign-team/${equipeId}`, {});
  }


  removeRefereeFromTeam(arbitreId: number): Observable<ArbitreWithTeamDto> {
    return this.http.put<ArbitreWithTeamDto>(`${this.apiUrl}/${arbitreId}/remove-team`, {});
  }

  deleteArbitre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getArbitresCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/arbitres`);
  }
}