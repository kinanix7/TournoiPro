package com.tournoipro.controller;

import com.tournoipro.model.Match;
import com.tournoipro.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Matchs", description = "Match management APIs")

public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return matchService.createMatch(match);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Match updateMatch(@PathVariable Long id, @RequestBody Match match) {
        return matchService.updateMatch(id, match);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/matchs")
    public long countMatchs() {
        return matchService.getMatchsCount();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/termines")
    public List<Match> getMatchsTermines() {
        return matchService.getMatchsTermines();

    }


    @GetMapping("/date/{date}")
    public List<Match> getMatchsByDate(
         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return matchService.getMatchsByDate(date);
    }


    @GetMapping("/periode")
    public List<Match> getMatchsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return matchService.getMatchsBetweenDates(dateDebut, dateFin);

    }
}
