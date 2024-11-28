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
import org.junit.jupiter.api.Assertions;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FoyerServiceImplTest {

    @Autowired
    private FoyerService foyerService;

    @Test
    @Order(1)
    public void testAddFoyer() {
        // Créer un nouvel foyer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer D")
                .capaciteFoyer(100)
                .build();

        // Ajouter le foyer via le service
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Vérifier que l'ID du foyer n'est pas nul après la sauvegarde
        Assertions.assertNotNull(savedFoyer.getIdFoyer(), "L'ID du foyer ne doit pas être nul");
        Assertions.assertEquals("Foyer D", savedFoyer.getNomFoyer());
        Assertions.assertEquals(100, savedFoyer.getCapaciteFoyer());
    }


}
