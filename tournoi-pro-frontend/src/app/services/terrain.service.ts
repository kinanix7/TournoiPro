import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Terrain } from '../models/tournament.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TerrainService {
  private apiUrl = `${environment.apiUrl}/api/terrains`;

  constructor(private http: HttpClient) { }

  getAllTerrains(): Observable<Terrain[]> {
    return this.http.get<Terrain[]>(this.apiUrl);
  }

  getTerrainById(id: number): Observable<Terrain> {
    return this.http.get<Terrain>(`${this.apiUrl}/${id}`);
  }

  createTerrain(terrain: Terrain): Observable<Terrain> {
    return this.http.post<Terrain>(this.apiUrl, terrain);
  }

  updateTerrain(id: number, terrain: Terrain): Observable<Terrain> {
    return this.http.put<Terrain>(`${this.apiUrl}/${id}`, terrain);
  }

  deleteTerrain(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getTerrainsCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/terrains`);
  }
}