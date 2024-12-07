package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.*;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class BlocTest {

    private static final Logger logger = LoggerFactory.getLogger(BlocTest.class);

    @Mock
    private Foyer foyer;

    @Mock
    private Etudiant etudiant;
    @InjectMocks
    private Bloc bloc;

    @InjectMocks
    private Chambre chambre;

    @InjectMocks
    private Universite universite;

    @InjectMocks
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // Initialiser l'entité Bloc avec des données de test
        bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);

        // Initialiser l'entité Chambre avec des données de test
        chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.SIMPLE);  // Exemple de type de chambre
        chambre.setBloc(bloc);  // Associer la chambre au bloc

        // Initialiser l'entité Universite avec des données de test
        universite = new Universite();
        universite.setNomUniversite("Université ABC");
        universite.setAdresse("123 Rue Université");
        universite.setFoyer(foyer);  // Associer l'université à un foyer (foyer simulé)

        // Initialiser l'entité Reservation avec des données de test
        reservation = new Reservation();
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);

        // Initialiser une liste d'étudiants et ajouter un étudiant à la réservation
        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiant);
        reservation.setEtudiants(etudiants);

    }

    // Test pour l'ajout d'un bloc avec Mock et Log
    @Test
    void testAjouterBloc() {
        // Simuler l'interaction avec Foyer (mocker l'objet Foyer si nécessaire)
        when(foyer.getIdFoyer()).thenReturn(1L);
        bloc.setFoyer(foyer);

        // Afficher un message avec Log
        logger.info("Test d'ajout du Bloc : Nom = {}, Capacité = {}", bloc.getNomBloc(), bloc.getCapaciteBloc());

        // Vérifier que le nom et la capacité du bloc sont correctement définis
        assertEquals("Bloc A", bloc.getNomBloc(), "Le nom du bloc devrait être 'Bloc A'");
        assertEquals(100, bloc.getCapaciteBloc(), "La capacité du bloc devrait être 100");

        // Affichage supplémentaire via le log
        logger.info("Bloc ajouté avec succès : {}", bloc);
    }
    @Test
    void testAjouterFoyer() {
        // Initialiser un foyer et l'associer à un bloc
        foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(200);

        // Afficher un message avec Log
        logger.info("Test d'ajout du Foyer : Nom = {}, Capacité = {}", foyer.getNomFoyer(), foyer.getCapaciteFoyer());

        // Vérifier que le nom et la capacité du foyer sont correctement définis
        assertEquals("Foyer A", foyer.getNomFoyer(), "Le nom du foyer devrait être 'Foyer A'");
        assertEquals(200, foyer.getCapaciteFoyer(), "La capacité du foyer devrait être 200");

        // Afficher un message de succès via le log
        logger.info("Foyer ajouté avec succès : {}", foyer);
    }

    @Test
    void testAjouterChambre() {
        // Initialiser une chambre et l'associer à un bloc
        chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.SIMPLE); // Exemple de type de chambre
        chambre.setBloc(bloc);

        // Afficher un message avec Log
        logger.info("Test d'ajout de la Chambre : Numéro = {}, Type = {}", chambre.getNumeroChambre(), chambre.getTypeChambre());

        // Vérifier que le numéro et le type de chambre sont correctement définis
        assertEquals(101, chambre.getNumeroChambre(), "Le numéro de la chambre devrait être 101");
        assertEquals(TypeChambre.SIMPLE, chambre.getTypeChambre(), "Le type de la chambre devrait être SIMPLE");

        // Vérifier que la chambre est correctement associée au bloc
        assertNotNull(chambre.getBloc(), "La chambre devrait être associée à un bloc");
        assertEquals("Bloc A", chambre.getBloc().getNomBloc(), "Le bloc associé à la chambre devrait être 'Bloc A'");

        // Afficher un message de succès via le log
        logger.info("Chambre ajoutée avec succès : {}", chambre);
    }

    @Test
    void testAjouterUniversite() {
        // Initialiser l'entité Universite
        universite.setNomUniversite("Université ABC");
        universite.setAdresse("123 Rue Université");

        // Simuler l'association de l'université avec un foyer
        when(foyer.getNomFoyer()).thenReturn("Foyer A");
        universite.setFoyer(foyer);

        // Afficher un message avec Log
        logger.info("Test d'ajout de l'Université : Nom = {}, Adresse = {}", universite.getNomUniversite(), universite.getAdresse());

        // Vérifier que le nom et l'adresse de l'université sont correctement définis
        assertEquals("Université ABC", universite.getNomUniversite(), "Le nom de l'université devrait être 'Université ABC'");
        assertEquals("123 Rue Université", universite.getAdresse(), "L'adresse de l'université devrait être '123 Rue Université'");

        // Vérifier que l'université est bien associée à un foyer
        assertNotNull(universite.getFoyer(), "L'université devrait être associée à un foyer");
        assertEquals("Foyer A", universite.getFoyer().getNomFoyer(), "Le foyer associé à l'université devrait être 'Foyer A'");

        // Afficher un message de succès via le log
        logger.info("Université ajoutée avec succès : {}", universite);
    }
    @Test
    void testAjouterReservation() {
        // Créer un mock pour l'étudiant
        Etudiant etudiantMock = mock(Etudiant.class);

        // Simuler l'ID de l'étudiant
        when(etudiantMock.getIdEtudiant()).thenReturn(12345L);

        // Initialiser la réservation
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(new Date());  // Assurez-vous d'initialiser cette valeur
        reservation.setEstValide(true);  // Assurez-vous de définir la validité de la réservation

        // Ajouter l'étudiant mocké à la réservation
        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiantMock);
        reservation.setEtudiants(etudiants);

        // Afficher un message de log
        logger.info("Test d'ajout de la réservation : Année universitaire = {}, Est valide = {}", reservation.getAnneeUniversitaire(), reservation.isEstValide());

        // Vérifier que l'année universitaire n'est pas nulle
        assertNotNull(reservation.getAnneeUniversitaire(), "L'année universitaire ne devrait pas être nulle");

        // Vérifier que la réservation est valide
        assertTrue(reservation.isEstValide(), "La réservation devrait être valide");

        // Vérifier que l'étudiant est bien ajouté à la réservation
        assertNotNull(reservation.getEtudiants(), "La liste des étudiants ne devrait pas être nulle");
        assertTrue(reservation.getEtudiants().contains(etudiantMock), "L'étudiant devrait être présent dans la réservation");

        // Afficher un message de succès via le log
        logger.info("Réservation ajoutée avec succès : {}", reservation);
    }
    @Test
    void testAjouterBlocAvecNomNull() {
        bloc.setNomBloc(null);  // Tester un bloc avec un nom null
        assertNull(bloc.getNomBloc(), "Le nom du bloc ne doit pas être null");
    }

    @Test
    void testAjouterBlocAvecCapaciteZero() {
        bloc.setCapaciteBloc(0);  // Tester un bloc avec une capacité de 0
        assertEquals(0, bloc.getCapaciteBloc(), "La capacité du bloc devrait être 0");
    }
    @Test
    void testAjouterChambreSansBloc() {
        chambre.setBloc(null);  // Tester une chambre sans bloc
        assertNull(chambre.getBloc(), "La chambre ne devrait pas être associée à un bloc");
    }
    @Test
    void testAjouterEtudiantAReservation() {
        Etudiant etudiantMock = mock(Etudiant.class);
        when(etudiantMock.getIdEtudiant()).thenReturn(12345L);

        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiantMock);
        reservation.setEtudiants(etudiants);

        assertTrue(reservation.getEtudiants().contains(etudiantMock), "L'étudiant devrait être ajouté à la réservation");
    }

    @Test
    void testRetirerEtudiantDeReservation() {
        Etudiant etudiantMock = mock(Etudiant.class);
        when(etudiantMock.getIdEtudiant()).thenReturn(12345L);

        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiantMock);
        reservation.setEtudiants(etudiants);

        etudiants.remove(etudiantMock);
        reservation.setEtudiants(etudiants);

        assertFalse(reservation.getEtudiants().contains(etudiantMock), "L'étudiant devrait être retiré de la réservation");
    }
    @Test
    void testAjouterReservationAvecAnneeUniversitaireNull() {
        reservation.setAnneeUniversitaire(null);
        assertNull(reservation.getAnneeUniversitaire(), "L'année universitaire ne doit pas être null");
    }
    @Test
    void testAjouterUniversiteSansFoyer() {
        universite.setFoyer(null);  // Tester une université sans foyer
        assertNull(universite.getFoyer(), "L'université ne devrait pas être associée à un foyer");
    }
    @Test
    void testChambreAssociéeCorrectement() {
        chambre.setBloc(bloc);
        assertNotNull(chambre.getBloc(), "La chambre devrait être associée à un bloc");
        assertEquals("Bloc A", chambre.getBloc().getNomBloc(), "Le bloc associé à la chambre devrait être 'Bloc A'");
    }


}
