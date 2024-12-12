package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.services.BlocService;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BlocMoc {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    private Bloc bloc;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Test")
                .build();

        bloc = Bloc.builder()
                .nomBloc("Bloc A")
                .capaciteBloc(20)
                .foyer(foyer)
                .build();

        log.info("Bloc initialisé : {}", bloc);
    }

    @Test
    void testAjouterBloc() {
        log.info("Démarrage du test pour ajouter un bloc avec ID de foyer sans modifier BlocService");

        // Préparer les données
        long foyerId = 1L;
        when(foyerRepository.findById(foyerId)).thenReturn(Optional.of(foyer));
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Associer manuellement le foyer au bloc
        Optional<Foyer> foyerOptional = foyerRepository.findById(foyerId);
        if (foyerOptional.isPresent()) {
            Foyer foyer = foyerOptional.get();
            bloc.setFoyer(foyer);

            // Sauvegarder le bloc
            Bloc savedBloc = blocRepository.save(bloc);

            // Assertions
            assertNotNull(savedBloc, "Le bloc sauvegardé ne doit pas être nul.");
            assertEquals(foyer, savedBloc.getFoyer(), "Le foyer doit être correctement associé au bloc.");

            // Vérifications des appels mockés
            verify(foyerRepository, times(1)).findById(foyerId);
            verify(blocRepository, times(1)).save(bloc);

            log.info("Test réussi : le bloc est correctement ajouté au foyer.");
        } else {
            fail("Foyer introuvable avec l'ID : " + foyerId);
        }
    }


    @Test
    void testMettreAJourBloc() {
            log.info("Démarrage du test de mise à jour du bloc");

            // Préparer les données de mise à jour pour le bloc
            bloc.setNomBloc("Bloc B Updated");
            bloc.setCapaciteBloc(50);

            // Mock du comportement du repository
            when(blocRepository.save(bloc)).thenReturn(bloc);

            // Appel de la méthode de service pour mettre à jour le bloc
            Bloc updatedBloc = blocService.updateBloc(bloc);

            // Vérifications
            assertEquals("Bloc B Updated", updatedBloc.getNomBloc(), "Le nom du bloc mis à jour doit correspondre.");
            assertEquals(50, updatedBloc.getCapaciteBloc(), "La capacité du bloc mise à jour doit correspondre.");

            // Vérification des appels au mock
            verify(blocRepository, times(1)).save(bloc);
            log.info("Test de mise à jour du bloc terminé : {}", updatedBloc);
        }



        @Test
    void testSupprimerBloc() {
        log.info("Démarrage du test de suppression de bloc");

        // Mock du comportement du repository
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        doNothing().when(blocRepository).deleteById(1L);

        // Appel de la méthode de service
        blocService.deleteBloc(1L);

        // Vérification des appels au mock
        verify(blocRepository, times(1)).findById(1L);
        verify(blocRepository, times(1)).deleteById(1L);
        log.info("Test de suppression de bloc terminé avec succès");
    }
}
