import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Match } from '../models/tournament.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private apiUrl = `${environment.apiUrl}/api/matches`;

  constructor(private http: HttpClient) { }

  getAllMatches(): Observable<Match[]> {
    return this.http.get<Match[]>(this.apiUrl);
  }

  getMatchById(id: number): Observable<Match> {
    return this.http.get<Match>(`${this.apiUrl}/${id}`);
  }

  createMatch(match: Partial<Match>): Observable<Match> {
    const matchToCreate = { ...match };
    delete (matchToCreate as any).id; // bach ma ytsiftech l'id
    return this.http.post<Match>(this.apiUrl, matchToCreate);
  }

  updateMatch(id: number, match: Partial<Match>): Observable<Match> {
    return this.http.put<Match>(`${this.apiUrl}/${id}`, match);
  }

  deleteMatch(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getMatchsCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/matchs`);
  }

  getMatchsTermines(): Observable<Match[]> {
    return this.http.get<Match[]>(`${this.apiUrl}/termines`);
  }

  getMatchsByDate(date: string): Observable<Match[]> {
    return this.http.get<Match[]>(`${this.apiUrl}/date/${date}`);
  }

  getMatchsBetweenDates(dateDebut: string, dateFin: string): Observable<Match[]> {
    const params = new HttpParams()
      .set('dateDebut', dateDebut)
      .set('dateFin', dateFin);
    return this.http.get<Match[]>(`${this.apiUrl}/periode`, { params });
  }
}
