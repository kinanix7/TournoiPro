package com.tournoipro.Controller;

import com.tournoipro.model.Classement;
import com.tournoipro.service.ClassementService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping
    public Classement createClassement(@RequestBody Classement classement) {
        return classementService.createClassement(classement);
    }

    @GetMapping
    public List<Classement> getAllClassements() {
        return classementService.getAllClassements();
    }

    @GetMapping("/{id}")
    public Classement getClassementById(@PathVariable Long id) {
        return classementService.getClassementById(id);
    }

    @PutMapping("/{id}")
    public Classement updateClassement(@PathVariable Long id, @RequestBody Classement classement) {
        return classementService.updateClassement(id, classement);
    }

    @DeleteMapping("/{id}")
    public void deleteClassement(@PathVariable Long id) {
        classementService.deleteClassement(id);
    }
}
