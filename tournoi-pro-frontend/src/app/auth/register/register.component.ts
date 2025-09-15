import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest, Role } from '../../models/user.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerData: RegisterRequest = {
    username: '',
    email: '',
    password: '',
    role: Role.USER
  };
  
  confirmPassword: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;
  
  Role = Role;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (this.registerData.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }
    
    if (this.isValidForm()) {
      this.isLoading = true;
      this.errorMessage = '';
      
      this.authService.register(this.registerData).subscribe({
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
            : 'Registration failed. Please try again.';
        }
      });
    }
  }
  
  private isValidForm(): boolean {
    return !!(
      this.registerData.username &&
      this.registerData.email &&
      this.registerData.password &&
      this.registerData.role &&
      this.confirmPassword &&
      this.registerData.password === this.confirmPassword
    );
  }
}