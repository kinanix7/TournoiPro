package com.tournoipro.controller;

import com.tournoipro.model.Match;
import com.tournoipro.dto.MatchDto;
import com.tournoipro.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Matchs", description = "Match management APIs")
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new match")
    public ResponseEntity<?> createMatch(@RequestBody Match match) {
        try {
            MatchDto savedMatch = matchService.createMatch(match);
            return ResponseEntity.ok(savedMatch);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating match: " + e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    @Operation(summary = "Get all matches")
    public ResponseEntity<?> getAllMatches() {
        try {
            List<MatchDto> matches = matchService.getAllMatches();
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving matches: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get match by ID")
    public ResponseEntity<?> getMatchById(@PathVariable Long id) {
        try {
            MatchDto match = matchService.getMatchById(id);
            return ResponseEntity.ok(match);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving match: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing match")
    public ResponseEntity<?> updateMatch(@PathVariable Long id, @RequestBody Match match) {
        try {
            MatchDto updatedMatch = matchService.updateMatch(id, match);
            return ResponseEntity.ok(updatedMatch);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating match: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a match")
    public ResponseEntity<?> deleteMatch(@PathVariable Long id) {
        try {
            matchService.deleteMatch(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting match: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/matchs")
    public long countMatchs() {
        return matchService.getMatchsCount();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/termines")
    @Operation(summary = "Get finished matches")
    public ResponseEntity<?> getMatchsTermines() {
        try {
            List<MatchDto> matches = matchService.getMatchsTermines();
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving finished matches: " + e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/date/{date}")
    @Operation(summary = "Get matches by specific date")
    public ResponseEntity<?> getMatchsByDate(
         @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<MatchDto> matches = matchService.getMatchsByDate(date);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving matches: " + e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/periode")
    @Operation(summary = "Get matches between two dates")
    public ResponseEntity<?> getMatchsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        try {
            List<MatchDto> matches = matchService.getMatchsBetweenDates(dateDebut, dateFin);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving matches: " + e.getMessage());
        }
    }
}