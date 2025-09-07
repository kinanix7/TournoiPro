package com.tournoipro.service;

import com.tournoipro.model.Match;
import com.tournoipro.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec id=" + id));
    }

    public Match updateMatch(Long id, Match matchDetails) {
        Match match = getMatchById(id);
        match.setDate(matchDetails.getDate());
        match.setHeure(matchDetails.getHeure());
        match.setEquipe1(matchDetails.getEquipe1());
        match.setEquipe2(matchDetails.getEquipe2());
        match.setTerrain(matchDetails.getTerrain());
        match.setArbitre(matchDetails.getArbitre());
        match.setScoreEquipe1(matchDetails.getScoreEquipe1());
        match.setScoreEquipe2(matchDetails.getScoreEquipe2());
        match.setPoule(matchDetails.getPoule());
        match.setTermine(matchDetails.getTermine());
        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        Match match = getMatchById(id);
        matchRepository.delete(match);
    }
}
