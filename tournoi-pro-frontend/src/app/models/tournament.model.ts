export interface Joueur {
  id: number;
  nom: string;
  role: string;
  type: TypeJoueur;
  equipe?: Equipe;
}

export interface JoueurWithTeamDto {
  id: number;
  nom: string;
  role: string;
  type: TypeJoueur;
  equipe?: {
    id: number;
    nom: string;
  };
}

export enum TypeJoueur {
  ELEVE = 'ELEVE',
  PROFESSEUR = 'PROFESSEUR'
}

export interface Equipe {
  id: number;
  nom: string;
  joueurs: Joueur[];
  nbVictoires: number;
  nbDefaites: number;
  points: number;
  poule?: Poule;
}

export interface EquipeWithPlayersDto {
  id: number;
  nom: string;
  joueurs: JoueurWithTeamDto[];
  nbVictoires: number;
  nbDefaites: number;
  points: number;
  poule?: {
    id: number;
    nom: string;
  };
}

export interface Arbitre {
  id: number;
  nom: string;
  equipeLiee?: Equipe;
}

export interface ArbitreWithTeamDto {
  id: number;
  nom: string;
  equipeLiee?: {
    id: number;
    nom: string;
  };
}

export interface Terrain {
  id: number;
  nom: string;
  localisation: string;
}

export interface Match {
  id: number;
  date: string; // LocalDate as string in ISO format (YYYY-MM-DD)
  heure: string; // LocalTime as string in ISO format (HH:mm:ss)
  equipe1Id?: number;
  equipe1Nom?: string;
  equipe2Id?: number;
  equipe2Nom?: string;
  terrainId?: number;
  terrainNom?: string;
  arbitreId?: number;
  arbitreNom?: string;
  scoreEquipe1?: number;
  scoreEquipe2?: number;
  pouleId?: number;
  pouleNom?: string;
  termine: boolean;
  
  // Keep legacy properties for backward compatibility
  equipe1?: Equipe;
  equipe2?: Equipe;
  terrain?: Terrain;
  arbitre?: Arbitre;
  poule?: Poule;
}

export interface Classement {
  id: number;
  equipe: Equipe;
  points: number;
  victoire: number;
  defaite: number;
  position: number;
}

export interface Poule {
  id: number;
  nom: string;
  equipes: Equipe[];
  matchs: Match[];
}