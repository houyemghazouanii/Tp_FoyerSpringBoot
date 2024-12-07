package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.UniversiteService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicesTests {
    private static final Logger logger = LoggerFactory.getLogger(BlocTest.class);

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    FoyerRepository foyerRepository;

    @InjectMocks
    UniversiteService universiteService;

    private Universite universite;

    @BeforeEach
    public void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Université A");
    }

    @Test
    public void testAddUniversite() {
        // Arrange
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite createdUniversite = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(createdUniversite);
        assertEquals("Université A", createdUniversite.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testGetAllUniversites() {
        // Arrange
        Universite universite1 = new Universite();
        Universite universite2 = new Universite();
        when(universiteRepository.findAll()).thenReturn(List.of(universite1, universite2));

        // Act
        List<Universite> universites = universiteService.getAllUniversites();

        // Assert
        assertNotNull(universites);
        assertEquals(2, universites.size());
    }

    @Test
    public void testGetUniversiteById() {
        // Arrange
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite foundUniversite = universiteService.getUniversiteById(1L);

        // Assert
        assertNotNull(foundUniversite);
        assertEquals("Université A", foundUniversite.getNomUniversite());
    }

    @Test
    public void testDeleteUniversite() {
        // Arrange : Utilisation de lenient pour ignorer les stubbings inutilisés
        lenient().when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act : Appel de la méthode à tester
        universiteService.deleteUniversite(1L);

        // Assert : Vérification que l'université est bien supprimée
        verify(universiteRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testUpdateUniversite() {
        // Arrange
        universite.setNomUniversite("Université B");
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite updatedUniversite = universiteService.updateUniversite(universite);

        // Assert
        assertEquals("Université B", updatedUniversite.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    
}
