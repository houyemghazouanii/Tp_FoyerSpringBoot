package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer17.entities.*;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderTest {

    @Test
    public void testBlocBuilder() {
        // Utilisation du builder pour créer un objet Bloc
        Bloc bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .capaciteBloc(100)
                .foyer(new Foyer())  // Assurez-vous que Foyer est une entité valide
                .chambres(Set.of()) // Créez un ensemble de chambres vide pour cet exemple
                .build();

        // Assertions pour vérifier que le Bloc est bien construit
        assertNotNull(bloc);
        assertEquals(1L, bloc.getIdBloc());
        assertEquals("Bloc A", bloc.getNomBloc());
        assertEquals(100, bloc.getCapaciteBloc());
        assertNotNull(bloc.getFoyer());
        assertTrue(bloc.getChambres().isEmpty());  // Vérifie qu'il n'y a pas de chambres
    }

    @Test
    public void testFoyerBuilder() {
        // Utilisation du builder pour créer un objet Foyer
        Foyer foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer A")
                .capaciteFoyer(500)
                .universite(new Universite())  // Assurez-vous que Universite est une entité valide
                .blocs(Set.of(new Bloc())) // Créez un ensemble de blocs
                .build();

        // Assertions pour vérifier que le Foyer est bien construit
        assertNotNull(foyer);
        assertEquals(1L, foyer.getIdFoyer());
        assertEquals("Foyer A", foyer.getNomFoyer());
        assertEquals(500, foyer.getCapaciteFoyer());
        assertNotNull(foyer.getUniversite());
        assertEquals(1, foyer.getBlocs().size());  // Vérifiez le nombre de blocs
    }
    @Test
    public void testUniversiteBuilder() {
        // Utilisation du builder pour créer un objet Universite
        Universite universite = Universite.builder()
                .idUniversite(1L)
                .nomUniversite("Université A")
                .adresse("123 rue de l'université")
                .foyer(new Foyer())  // Assurez-vous que Foyer est une entité valide
                .build();

        // Assertions pour vérifier que l'Universite est bien construite
        assertNotNull(universite);
        assertEquals(1L, universite.getIdUniversite());
        assertEquals("Université A", universite.getNomUniversite());
        assertEquals("123 rue de l'université", universite.getAdresse());
        assertNotNull(universite.getFoyer());
    }
    @Test
    public void testReservationBuilder() {
        // Création d'un ensemble d'étudiants (assurez-vous qu'Etudiant est une entité valide)
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();

        // Utilisation du builder pour créer un objet Reservation
        Reservation reservation = Reservation.builder()
                .idReservation("R001")
                .anneeUniversitaire(new Date())  // Assurez-vous que la date est valide
                .estValide(true)
                .etudiants(Set.of(etudiant1, etudiant2)) // Créez un ensemble d'étudiants
                .build();

        // Assertions pour vérifier que la Reservation est bien construite
        assertNotNull(reservation);
        assertEquals("R001", reservation.getIdReservation());
        assertNotNull(reservation.getAnneeUniversitaire());
        assertTrue(reservation.isEstValide());
        assertEquals(2, reservation.getEtudiants().size());  // Vérifiez le nombre d'étudiants
    }
    @Test
    public void testChambreBuilder() {
        // Création d'un bloc (assurez-vous que Bloc est une entité valide)
        Bloc bloc = new Bloc();

        // Création d'une réservation (assurez-vous que Reservation est une entité valide)
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();

        // Utilisation du builder pour créer un objet Chambre
        Chambre chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .typeChambre(TypeChambre.SIMPLE)  // Assurez-vous que TypeChambre est un enum valide
                .bloc(bloc)
                .reservations(Set.of(reservation1, reservation2)) // Créez un ensemble de réservations
                .build();

        // Assertions pour vérifier que la Chambre est bien construite
        assertNotNull(chambre);
        assertEquals(1L, chambre.getIdChambre());
        assertEquals(101L, chambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, chambre.getTypeChambre());  // Vérifiez le type de chambre
        assertNotNull(chambre.getBloc());
        assertEquals(2, chambre.getReservations().size());  // Vérifiez le nombre de réservations
    }
}
