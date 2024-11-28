package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
//import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.tpfoyer17.services.EtudiantService;
import tn.esprit.tpfoyer17.entities.Etudiant;
import org.junit.jupiter.api.Assertions;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EtudiantServiceImplTest {

    @Autowired
    private EtudiantService etudiantService;

    @Test
    @Order(1)
    public void testAddEtudiant() {
        // Créer un nouvel étudiant
        Etudiant etudiant = Etudiant.builder()
                .nomEtudiant("Doe")
                .prenomEtudiant("John")
                .cinEtudiant(12345678L)
                .dateNaissance(new Date())
                .build();

        // Ajouter l'étudiant via le service
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Vérifier que l'ID n'est pas nul après la sauvegarde, ce qui confirme que l'étudiant est enregistré
        Assertions.assertNotNull(savedEtudiant.getIdEtudiant(), "L'ID de l'étudiant ne doit pas être nul");
        Assertions.assertEquals("Doe", savedEtudiant.getNomEtudiant());
        Assertions.assertEquals("John", savedEtudiant.getPrenomEtudiant());
        Assertions.assertEquals(12345678L, savedEtudiant.getCinEtudiant());
    }
}
