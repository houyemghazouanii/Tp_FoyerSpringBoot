package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.FoyerService;
import tn.esprit.tpfoyer17.services.UniversiteService;
import org.junit.jupiter.api.Assertions;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UniversiteServiceImplTest {

    @Autowired
    private UniversiteService universiteService;

    @Autowired
    private FoyerService foyerService;

    @Test
    @Order(1)
    public void testAjouterUniversite() {
        // Récupérer un foyer existant (par exemple, avec l'ID 1)
        long foyerId = 1; // Remplacez par un ID valide de foyer existant dans la base de données
        Foyer foyerExistant = foyerService.getFoyerById(foyerId);

        // Vérifier que le foyer existe
        Assertions.assertNotNull(foyerExistant, "Le foyer avec l'ID " + foyerId + " n'existe pas.");

        // Créer une nouvelle université à ajouter
        Universite universite = Universite.builder()
                .nomUniversite("Université Esprit")
                .adresse("Tunis, Tunisie")
                .foyer(foyerExistant) // Affecter ce bloc au foyer existant
                .build();

        // Ajouter l'université via le service
        Universite savedUniversite = universiteService.addUniversite(universite);

        // Vérifier que l'université a bien été ajoutée
        Assertions.assertNotNull(savedUniversite.getIdUniversite(), "L'ID de l'université ne doit pas être nul");
        Assertions.assertEquals("Université Esprit", savedUniversite.getNomUniversite());
        Assertions.assertEquals("Tunis, Tunisie", savedUniversite.getAdresse());


        // Vérifier que le bloc est bien affecté au foyer existant
        Assertions.assertEquals(foyerExistant.getIdFoyer(), savedUniversite.getFoyer().getIdFoyer(),
                "Le bloc doit être affecté au foyer existant avec l'ID " + foyerExistant.getIdFoyer());
    }
}
