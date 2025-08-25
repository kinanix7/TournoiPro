package com.tournoipro.service;

import com.tournoipro.dto.JoueurDto;
import com.tournoipro.mapper.JoueurMapper;
import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import com.tournoipro.repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JoueurService {

    @Autowired
    private JoueurRepository joueurRepository;

    @Autowired
    private JoueurMapper joueurMapper;

    public List<JoueurDto> getAllJoueurs() {
        List<Joueur> joueurs = joueurRepository.findAll();
        return joueurMapper.toDtoList(joueurs);
    }

    public Optional<JoueurDto> getJoueurById(Long id) {
        return joueurRepository.findById(id)
                .map(joueurMapper::toDto);
    }

    public List<JoueurDto> getJoueursByEquipe(Long equipeId) {
        List<Joueur> joueurs = joueurRepository.findByEquipeId(equipeId);
        return joueurMapper.toDtoList(joueurs);
    }

    public List<JoueurDto> getJoueursByType(TypeJoueur type) {
        List<Joueur> joueurs = joueurRepository.findByType(type);
        return joueurMapper.toDtoList(joueurs);
    }

    public List<JoueurDto> searchJoueursByNom(String nom) {
        List<Joueur> joueurs = joueurRepository.findByNomContainingIgnoreCase(nom);
        return joueurMapper.toDtoList(joueurs);
    }

    public JoueurDto createJoueur(JoueurDto joueurDto) {
        if (joueurDto.getEquipeId() != null &&
                joueurRepository.existsByNomAndEquipeId(joueurDto.getNom(), joueurDto.getEquipeId())) {
            throw new RuntimeException("Un joueur avec ce nom existe déjà dans cette équipe");
        }

        Joueur joueur = joueurMapper.toEntity(joueurDto);
        Joueur savedJoueur = joueurRepository.save(joueur);
        return joueurMapper.toDto(savedJoueur);
    }

    public JoueurDto updateJoueur(Long id, JoueurDto joueurDto) {
        return joueurRepository.findById(id)
                .map(existingJoueur -> {
                    if (!existingJoueur.getNom().equals(joueurDto.getNom()) &&
                            joueurDto.getEquipeId() != null &&
                            joueurRepository.existsByNomAndEquipeId(joueurDto.getNom(), joueurDto.getEquipeId())) {
                        throw new RuntimeException("Un joueur avec ce nom existe déjà dans cette équipe");
                    }

                    existingJoueur.setNom(joueurDto.getNom());
                    existingJoueur.setRole(joueurDto.getRole());
                    existingJoueur.setType(joueurDto.getType());

                    Joueur updatedJoueur = joueurRepository.save(existingJoueur);
                    return joueurMapper.toDto(updatedJoueur);
                })
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'id: " + id));
    }

    public void deleteJoueur(Long id) {
        if (!joueurRepository.existsById(id)) {
            throw new RuntimeException("Joueur non trouvé avec l'id: " + id);
        }
        joueurRepository.deleteById(id);
    }
}
