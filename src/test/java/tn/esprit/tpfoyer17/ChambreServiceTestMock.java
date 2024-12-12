package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.services.ChambreService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ChambreServiceTestMock {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(200)
                .typeChambre(TypeChambre.SIMPLE)
                .build();
        log.info("Chambre initialisée : {}", chambre);
    }

    @Test
    void testAddChambre() {
        log.info("Démarrage du test d'ajout de chambre");

        // Mock du comportement du repository
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Appel de la méthode de service
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Vérifications
        assertNotNull(savedChambre, "La chambre sauvegardée ne doit pas être nulle.");
        assertEquals(200, savedChambre.getNumeroChambre(), "Le numéro de la chambre doit correspondre.");
        assertEquals(TypeChambre.SIMPLE, savedChambre.getTypeChambre(), "Le type de la chambre doit correspondre.");

        // Vérification des appels au mock
        verify(chambreRepository, times(1)).save(chambre);
        log.info("Test d'ajout de chambre terminé avec succès : {}", savedChambre);
    }

    @Test
    void testGetChambreById() {
        log.info("Démarrage du test de récupération de la chambre par ID");

        // Mock du comportement du repository
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Appel de la méthode de service
        Chambre foundChambre = chambreService.getChambreById(1L);

        // Vérifications
        assertNotNull(foundChambre, "La chambre trouvée ne doit pas être nulle.");
        assertEquals(200, foundChambre.getNumeroChambre(), "Le numéro doit correspondre.");
        assertEquals(TypeChambre.SIMPLE, foundChambre.getTypeChambre(), "Le type de chambre doit correspondre.");

        // Vérification des appels au mock
        verify(chambreRepository, times(1)).findById(1L);
        log.info("Test de récupération de la chambre par ID terminé : {}", foundChambre);
    }

    @Test
    void testUpdateChambre() {
        log.info("Démarrage du test de mise à jour de la chambre");

        // Préparer les données de mise à jour
        chambre.setNumeroChambre(300);  // Mettre à jour le numéro
        chambre.setTypeChambre(TypeChambre.DOUBLE);  // Mettre à jour le type

        // Mock du comportement du repository
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Appel de la méthode de service
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Vérifications
        assertEquals(300, updatedChambre.getNumeroChambre(), "Le numéro mis à jour doit correspondre.");
        assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre(), "Le type mis à jour doit correspondre.");

        // Vérification des appels au mock
        verify(chambreRepository, times(1)).save(chambre);
        log.info("Test de mise à jour de la chambre terminé : {}", updatedChambre);
    }

    @Test
    void testDeleteChambre() {
        log.info("Démarrage du test de suppression de chambre");

        // Mock du comportement du repository pour s'assurer que la chambre existe
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));  // S'assurer que la chambre existe

        // Appel de la méthode de service
        chambreService.deleteChambre(1L);

        // Vérification que la méthode du repository a été appelée pour supprimer la chambre
        verify(chambreRepository, times(1)).deleteById(1L);
        log.info("Test de suppression de chambre terminé");
    }
}
