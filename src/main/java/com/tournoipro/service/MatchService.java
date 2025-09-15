package com.tournoipro.service;

import com.tournoipro.model.Match;
import com.tournoipro.model.Equipe;
import com.tournoipro.model.Poule;
import com.tournoipro.dto.MatchDto;
import com.tournoipro.mapper.MatchMapper;
import com.tournoipro.repository.MatchRepository;
import com.tournoipro.repository.EquipeRepository;
import com.tournoipro.repository.TerrainRepository;
import com.tournoipro.repository.ArbitreRepository;
import com.tournoipro.repository.PouleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final EquipeRepository equipeRepository;
    private final TerrainRepository terrainRepository;
    private final ArbitreRepository arbitreRepository;
    private final PouleRepository pouleRepository;
    private final MatchMapper matchMapper;

    public MatchService(MatchRepository matchRepository,
                       EquipeRepository equipeRepository,
                       TerrainRepository terrainRepository,
                       ArbitreRepository arbitreRepository,
                       PouleRepository pouleRepository,
                       MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.equipeRepository = equipeRepository;
        this.terrainRepository = terrainRepository;
        this.arbitreRepository = arbitreRepository;
        this.pouleRepository = pouleRepository;
        this.matchMapper = matchMapper;
    }

    public MatchDto createMatch(Match match) {
        // Set the poule based on team poules
        setMatchPoule(match);
        
        // Check for conflicts
        validateMatchConflicts(match);
        
        // Set default values
        if (match.getScoreEquipe1() == null) {
            match.setScoreEquipe1(0);
        }
        if (match.getScoreEquipe2() == null) {
            match.setScoreEquipe2(0);
        }
        if (match.getTermine() == null) {
            match.setTermine(false);
        }
        
        Match savedMatch = matchRepository.save(match);
        return matchMapper.toDto(savedMatch);
    }

    public List<MatchDto> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    public MatchDto getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec id=" + id));
        return matchMapper.toDto(match);
    }

    public MatchDto updateMatch(Long id, Match matchDetails) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec id=" + id));
        
        // Check for conflicts if date, time, or terrain changed
        if (!match.getDate().equals(matchDetails.getDate()) ||
            !match.getHeure().equals(matchDetails.getHeure()) ||
            !match.getTerrain().getId().equals(matchDetails.getTerrain().getId())) {
            validateMatchConflicts(matchDetails);
        }
        
        // Update match properties
        match.setDate(matchDetails.getDate());
        match.setHeure(matchDetails.getHeure());
        
        // Update teams if provided
        if (matchDetails.getEquipe1() != null) {
            match.setEquipe1(matchDetails.getEquipe1());
        }
        if (matchDetails.getEquipe2() != null) {
            match.setEquipe2(matchDetails.getEquipe2());
        }
        
        // Update terrain if provided
        if (matchDetails.getTerrain() != null) {
            match.setTerrain(matchDetails.getTerrain());
        }
        
        // Update arbitre if provided
        if (matchDetails.getArbitre() != null) {
            match.setArbitre(matchDetails.getArbitre());
        }
        
        // Update scores
        if (matchDetails.getScoreEquipe1() != null) {
            match.setScoreEquipe1(matchDetails.getScoreEquipe1());
        }
        if (matchDetails.getScoreEquipe2() != null) {
            match.setScoreEquipe2(matchDetails.getScoreEquipe2());
        }
        
        // Update poule if provided
        if (matchDetails.getPoule() != null) {
            match.setPoule(matchDetails.getPoule());
        }
        
        // Update finished status
        if (matchDetails.getTermine() != null) {
            match.setTermine(matchDetails.getTermine());
        }
        
        Match updatedMatch = matchRepository.save(match);
        return matchMapper.toDto(updatedMatch);
    }

    public void deleteMatch(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec id=" + id));
        matchRepository.delete(match);
    }

    public long getMatchsCount() {
        return matchRepository.countMatchs();
    }

    public List<MatchDto> getMatchsTermines (){
        return matchRepository.findByTermine(true).stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getMatchsByDate(LocalDate date) {
        return matchRepository.findByDate(date).stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getMatchsBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
        return matchRepository.findMatchsBetweenDates(dateDebut , dateFin).stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }
    
    // Validation methods

    private void setMatchPoule(Match match) {
        Equipe equipe1 = match.getEquipe1();
        Equipe equipe2 = match.getEquipe2();
        
        // Fetch full equipe entities with poule information
        equipe1 = equipeRepository.findById(equipe1.getId())
                .orElseThrow(() -> new IllegalArgumentException("Team 1 not found"));
        equipe2 = equipeRepository.findById(equipe2.getId())
                .orElseThrow(() -> new IllegalArgumentException("Team 2 not found"));
        
        Poule poule1 = equipe1.getPoule();
        Poule poule2 = equipe2.getPoule();
        
        // If both teams have the same poule, use that poule
        if (poule1 != null && poule2 != null && poule1.getId().equals(poule2.getId())) {
            match.setPoule(poule1);
        }
        // If teams have different poules, throw an error
        else if (poule1 != null && poule2 != null && !poule1.getId().equals(poule2.getId())) {
            throw new IllegalArgumentException("Teams belong to different poules and cannot play against each other");
        }
        // If one team has a poule and the other doesn't, throw an error
        else if ((poule1 != null && poule2 == null) || (poule1 == null && poule2 != null)) {
            throw new IllegalArgumentException("Teams must belong to the same poule to play against each other");
        }
        // If both teams have no poule, create or use a default "Poule Générale"
        else {
            // Check if "Poule Générale" already exists
            Poule pouleGenerale = pouleRepository.findByNom("Poule Générale");
            if (pouleGenerale == null) {
                // Create a new "Poule Générale"
                pouleGenerale = new Poule();
                pouleGenerale.setNom("Poule Générale");
                pouleGenerale = pouleRepository.save(pouleGenerale);
            }
            match.setPoule(pouleGenerale);
        }
    }
    
    private void validateMatchConflicts(Match match) {
        // Check for terrain conflicts
        List<Match> conflictingMatches = matchRepository.findConflictingMatches(
            match.getDate(), match.getHeure(), match.getTerrain().getId());
        
        // If updating, exclude the current match
        if (match.getId() != null) {
            conflictingMatches = conflictingMatches.stream()
                .filter(m -> !m.getId().equals(match.getId()))
                .toList();
        }
        
        if (!conflictingMatches.isEmpty()) {
            throw new IllegalArgumentException("Court is already booked at this time");
        }
        
        // Check if teams have conflicting matches
        List<Match> teamConflicts = matchRepository.findTeamConflicts(
            match.getDate(), match.getHeure(), 
            match.getEquipe1().getId(), match.getEquipe2().getId());
            
        if (match.getId() != null) {
            teamConflicts = teamConflicts.stream()
                .filter(m -> !m.getId().equals(match.getId()))
                .toList();
        }
        
        if (!teamConflicts.isEmpty()) {
            throw new IllegalArgumentException("One or both teams already have a match at this time");
        }
    }
}
