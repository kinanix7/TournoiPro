import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { JwtResponse, LoginRequest, RegisterRequest, User, Role } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/api/auth`;
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  private readonly TOKEN_KEY = 'authToken';
  private readonly USER_KEY = 'currentUser';
  private readonly SESSION_KEY = 'userSession';

  constructor(private http: HttpClient, private router: Router) {
    // Check if user is already logged in on service initialization
    this.checkStoredAuth();
  }

  private checkStoredAuth(): void {
    const token = this.getToken();
    const userStr = localStorage.getItem(this.USER_KEY);
    
    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        // Verify token is still valid
        if (this.isTokenValid(token)) {
          this.currentUserSubject.next(user);
          this.updateLastActivity();
        } else {
          this.logout();
        }
      } catch (error) {
        this.logout();
      }
    }
  }

  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          this.setToken(response.token);
          const user: User = {
            id: response.id,
            username: response.username,
            email: response.email,
            role: response.role,
            enabled: true
          };
          localStorage.setItem(this.USER_KEY, JSON.stringify(user));
          this.updateLastActivity();
          this.currentUserSubject.next(user);
        })
      );
  }

  register(registerData: RegisterRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.apiUrl}/register`, registerData)
      .pipe(
        tap(response => {
          this.setToken(response.token);
          const user: User = {
            id: response.id,
            username: response.username,
            email: response.email,
            role: response.role,
            enabled: true
          };
          localStorage.setItem(this.USER_KEY, JSON.stringify(user));
          this.updateLastActivity();
          this.currentUserSubject.next(user);
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    localStorage.removeItem(this.SESSION_KEY);
    this.currentUserSubject.next(null);
    
    // Navigate to login page
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private isTokenValid(token: string): boolean {
    if (!token) return false;
    
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

  private updateLastActivity(): void {
    const sessionData = {
      lastActivity: Date.now(),
      loginTime: Date.now()
    };
    localStorage.setItem(this.SESSION_KEY, JSON.stringify(sessionData));
  }

  getLastActivity(): number | null {
    const sessionStr = localStorage.getItem(this.SESSION_KEY);
    if (sessionStr) {
      try {
        const session = JSON.parse(sessionStr);
        return session.lastActivity;
      } catch {
        return null;
      }
    }
    return null;
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return this.isTokenValid(token || '');
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  hasRole(role: Role): boolean {
    const user = this.getCurrentUser();
    return user?.role === role || false;
  }

  isAdmin(): boolean {
    return this.hasRole(Role.ADMIN);
  }

  isUser(): boolean {
    return this.hasRole(Role.USER) || this.hasRole(Role.ADMIN);
  }

  // Auto-logout after inactivity (default: 2 hours)
  checkSessionTimeout(timeoutMinutes: number = 120): boolean {
    const lastActivity = this.getLastActivity();
    if (lastActivity) {
      const now = Date.now();
      const timeoutMs = timeoutMinutes * 60 * 1000;
      if (now - lastActivity > timeoutMs) {
        this.logout();
        return true; // Session expired
      }
    }
    return false; // Session is still valid
  }

  // Call this method whenever user performs an action
  refreshSession(): void {
    if (this.isAuthenticated()) {
      this.updateLastActivity();
    }
  }

  // Get session info
  getSessionInfo(): { loginTime: number | null, lastActivity: number | null } {
    const sessionStr = localStorage.getItem(this.SESSION_KEY);
    if (sessionStr) {
      try {
        const session = JSON.parse(sessionStr);
        return {
          loginTime: session.loginTime || null,
          lastActivity: session.lastActivity || null
        };
      } catch {
        return { loginTime: null, lastActivity: null };
      }
    }
    return { loginTime: null, lastActivity: null };
  }
}