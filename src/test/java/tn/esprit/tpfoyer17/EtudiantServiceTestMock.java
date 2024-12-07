package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.EtudiantService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class EtudiantServiceTestMock {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = Etudiant.builder()
                .nomEtudiant("Do")
                .prenomEtudiant("Joo")
                .cinEtudiant(12345678L)
                .dateNaissance(new java.util.Date())
                .build();
        log.info("Étudiant initialisé : {}", etudiant);
    }

    @Test
    void testAddEtudiant() {
        log.info("Démarrage du test d'ajout d'étudiant");

        // Mock du comportement du repository
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Appel de la méthode de service
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Vérifications
        assertNotNull(savedEtudiant, "L'étudiant sauvegardé ne doit pas être nul.");
        assertEquals("Do", savedEtudiant.getNomEtudiant(), "Le nom de l'étudiant doit correspondre.");
        assertEquals("Joo", savedEtudiant.getPrenomEtudiant(), "Le prénom de l'étudiant doit correspondre.");
        assertEquals(12345678L, savedEtudiant.getCinEtudiant(), "Le CIN de l'étudiant doit correspondre.");

        // Vérification des appels au mock
        verify(etudiantRepository, times(1)).save(etudiant);
        log.info("Test d'ajout d'étudiant terminé avec succès : {}", savedEtudiant);
    }

    @Test
    void testGetEtudiantById() {
        log.info("Démarrage du test de récupération de l'étudiant par ID");

        // Mock du comportement du repository
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Appel de la méthode de service
        Etudiant foundEtudiant = etudiantService.getEtudiantById(1L);

        // Vérifications
        assertNotNull(foundEtudiant, "L'étudiant trouvé ne doit pas être nul.");
        assertEquals("Do", foundEtudiant.getNomEtudiant(), "Le nom doit correspondre.");
        assertEquals("Joo", foundEtudiant.getPrenomEtudiant(), "Le prénom doit correspondre.");
        assertEquals(12345678L, foundEtudiant.getCinEtudiant(), "Le CIN doit correspondre.");

        // Vérification des appels au mock
        verify(etudiantRepository, times(1)).findById(1L);
        log.info("Test de récupération de l'étudiant par ID terminé : {}", foundEtudiant);
    }

    @Test
    void testUpdateEtudiant() {
        log.info("Démarrage du test de mise à jour de l'étudiant");

        // Préparer les données de mise à jour
        etudiant.setNomEtudiant("Do Updated");
        etudiant.setPrenomEtudiant("Joo Updated");

        // Mock du comportement du repository
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Appel de la méthode de service
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);

        // Vérifications
        assertEquals("Do Updated", updatedEtudiant.getNomEtudiant(), "Le nom mis à jour doit correspondre.");
        assertEquals("Joo Updated", updatedEtudiant.getPrenomEtudiant(), "Le prénom mis à jour doit correspondre.");

        // Vérification des appels au mock
        verify(etudiantRepository, times(1)).save(etudiant);
        log.info("Test de mise à jour de l'étudiant terminé : {}", updatedEtudiant);
    }

    @Test
    void testDeleteEtudiant() {
        log.info("Démarrage du test de suppression d'étudiant");

        // Mock du comportement du repository pour s'assurer que l'étudiant existe
        lenient().when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Appel de la méthode de service
        etudiantService.deleteEtudiant(1L);

        // Vérification que la méthode du repository a été appelée pour supprimer l'étudiant
        verify(etudiantRepository, times(1)).deleteById(1L);
        log.info("Test de suppression d'étudiant terminé");
    }


}
