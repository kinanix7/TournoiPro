import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Classement } from '../models/tournament.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClassementService {
  private apiUrl = `${environment.apiUrl}/api/classements`;

  constructor(private http: HttpClient) { }

  getAllClassements(): Observable<Classement[]> {
    return this.http.get<Classement[]>(this.apiUrl);
  }

  getClassementById(id: number): Observable<Classement> {
    return this.http.get<Classement>(`${this.apiUrl}/${id}`);
  }

  createClassement(classement: Classement): Observable<Classement> {
    return this.http.post<Classement>(this.apiUrl, classement);
  }

  updateClassement(id: number, classement: Classement): Observable<Classement> {
    return this.http.put<Classement>(`${this.apiUrl}/${id}`, classement);
  }

  deleteClassement(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}