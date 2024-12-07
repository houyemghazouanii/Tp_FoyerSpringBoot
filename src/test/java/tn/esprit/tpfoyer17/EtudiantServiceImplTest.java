package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.EtudiantService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)  // Utilisation de JUnit 5
@SpringBootTest
@Slf4j
public class EtudiantServiceImplTest {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    @Order(1)
    public void testAddEtudiant() {
        // Créer un nouvel étudiant
        Etudiant etudiant = Etudiant.builder()
                .nomEtudiant("Doe")
                .prenomEtudiant("John")
                .cinEtudiant(12345678L)
                .dateNaissance(new java.util.Date())
                .build();

        // Ajouter l'étudiant via le service
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Vérifier que l'ID n'est pas nul après la sauvegarde, ce qui confirme que l'étudiant est enregistré
        assertNotNull(savedEtudiant.getIdEtudiant(), "L'ID de l'étudiant ne doit pas être nul");
        Assertions.assertEquals("Doe", savedEtudiant.getNomEtudiant());
        Assertions.assertEquals("John", savedEtudiant.getPrenomEtudiant());
        Assertions.assertEquals(12345678L, savedEtudiant.getCinEtudiant());
    }

    @Test
    @Order(2)
    public void testGetAllEtudiants() {
        // Récupérer tous les étudiants
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();

        // Vérifier que la liste des étudiants n'est pas vide
        assertNotNull(etudiants, "La liste des étudiants ne doit pas être nulle");
        Assertions.assertFalse(etudiants.isEmpty(), "La liste des étudiants ne doit pas être vide");
        log.info("Liste des étudiants : {}", etudiants);
    }

    @Test
    @Order(3)
    public void testUpdateEtudiant() {
        // Récupérer un étudiant existant (par exemple, avec l'ID 1)
        long etudiantId = 1;  // Remplacez par un ID valide d'un étudiant existant
        Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);

        // Vérifier que l'étudiant existe
        assertNotNull(etudiant, "L'étudiant avec l'ID " + etudiantId + " n'existe pas.");

        // Modifier certaines propriétés de l'étudiant
        etudiant.setNomEtudiant("Dho");
        etudiant.setPrenomEtudiant("Jhon");
        etudiant.setCinEtudiant(98765432L);  // Mise à jour du CIN

        // Sauvegarder les modifications
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);

        // Vérifier que les modifications ont été sauvegardées
        Assertions.assertEquals("Dho", updatedEtudiant.getNomEtudiant());
        Assertions.assertEquals("Jhon", updatedEtudiant.getPrenomEtudiant());
        Assertions.assertEquals(98765432L, updatedEtudiant.getCinEtudiant());  // Vérification du CIN mis à jour
    }


    @Test
    @Order(4)
    public void testDeleteEtudiant() {
        // Supposons qu'un étudiant existe déjà dans la base de données avec un ID spécifique
        long etudiantId = 1;  // L'ID de l'étudiant que vous souhaitez supprimer

        // Vérifier que l'étudiant existe avant la suppression
        Etudiant existingEtudiant = etudiantRepository.findById(etudiantId).orElse(null);
        assertNotNull(existingEtudiant, "L'étudiant doit exister avant la suppression.");

        // Supprimer l'étudiant
        etudiantService.deleteEtudiant(etudiantId);

        // Vérifier que l'étudiant a bien été supprimé
        Etudiant deletedEtudiant = etudiantRepository.findById(etudiantId).orElse(null);
        Assertions.assertNull(deletedEtudiant, "L'étudiant devrait être nul après suppression.");
    }
}
