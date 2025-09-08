package com.tournoipro.controller;

import com.tournoipro.model.Classement;
import com.tournoipro.service.ClassementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classements")
@Tag(name = "Classements", description = "Ranking management APIs")

public class ClassementController {

    private final ClassementService classementService;

    public ClassementController(ClassementService classementService) {
        this.classementService = classementService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Classement createClassement(@RequestBody Classement classement) {
        return classementService.createClassement(classement);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Classement> getAllClassements() {
        return classementService.getAllClassements();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public Classement getClassementById(@PathVariable Long id) {
        return classementService.getClassementById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Classement updateClassement(@PathVariable Long id, @RequestBody Classement classement) {
        return classementService.updateClassement(id, classement);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteClassement(@PathVariable Long id) {
        classementService.deleteClassement(id);
    }
}
