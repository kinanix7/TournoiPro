import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials: LoginRequest = {
    username: '',
    password: ''
  };
  
  rememberMe: boolean = false;
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.loadSavedCredentials();
  }

  onSubmit(): void {
    if (this.credentials.username && this.credentials.password) {
      this.isLoading = true;
      this.errorMessage = '';
      
      if (this.rememberMe) {
        this.saveCredentials();
      } else {
        this.clearSavedCredentials();
      }
      
      this.authService.login(this.credentials).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response.role === 'ADMIN') {
            this.router.navigate(['/admin/dashboard']);
          } else {
            this.router.navigate(['/dashboard']);
          }
        },
        error: (error) => {
          this.isLoading = false;
          this.errorMessage = typeof error.error === 'string' 
            ? error.error 
            : 'Login failed. Please check your credentials.';
        }
      });
    }
  }

  private loadSavedCredentials(): void {
    const savedCredentials = localStorage.getItem('rememberedCredentials');
    if (savedCredentials) {
      try {
        const parsed = JSON.parse(savedCredentials);
        this.credentials.username = parsed.username || '';
        this.credentials.password = parsed.password || '';
        this.rememberMe = true;
      } catch (error) {
        this.clearSavedCredentials();
      }
    }
  }

  private saveCredentials(): void {
    const credentialsToSave = {
      username: this.credentials.username,
      password: this.credentials.password
    };
    localStorage.setItem('rememberedCredentials', JSON.stringify(credentialsToSave));
  }

  private clearSavedCredentials(): void {
    localStorage.removeItem('rememberedCredentials');
  }
}