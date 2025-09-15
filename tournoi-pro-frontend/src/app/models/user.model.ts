export interface User {
  id: number;
  username: string;
  email: string;
  role: Role;
  enabled: boolean;
}

export enum Role {
  ADMIN = 'ADMIN',
  USER = 'USER'
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  role: Role;
}

export interface JwtResponse {
  token: string;
  id: number;
  username: string;
  email: string;
  role: Role;
}