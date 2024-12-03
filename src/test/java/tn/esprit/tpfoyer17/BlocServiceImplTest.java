package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.services.BlocService;
import tn.esprit.tpfoyer17.services.FoyerService;
import org.junit.jupiter.api.Assertions;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BlocServiceImplTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private FoyerService foyerService;

    @Test
    @Order(1)
    public void testAjouterBlocAFoyerExistant() {
        // Récupérer un foyer existant (par exemple, avec l'ID 1)
        long foyerId = 2; // Remplacez par un ID valide de foyer existant dans la base de données
        Foyer foyerExistant = foyerService.getFoyerById(foyerId);

        // Vérifier que le foyer existe
        Assertions.assertNotNull(foyerExistant, "Le foyer avec l'ID " + foyerId + " n'existe pas.");

        // Créer un bloc à ajouter au foyer existant
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc G")
                .capaciteBloc(30)
                .foyer(foyerExistant) // Affecter ce bloc au foyer existant
                .build();

        // Ajouter le bloc via le service
        Bloc savedBloc = blocService.addBloc(bloc);

        // Vérifier que le bloc a bien été ajouté
        Assertions.assertNotNull(savedBloc.getIdBloc(), "L'ID du bloc ne doit pas être nul");
        Assertions.assertEquals("Bloc G", savedBloc.getNomBloc());
        Assertions.assertEquals(30, savedBloc.getCapaciteBloc());

        // Vérifier que le bloc est bien affecté au foyer existant
        Assertions.assertEquals(foyerExistant.getIdFoyer(), savedBloc.getFoyer().getIdFoyer(),
                "Le bloc doit être affecté au foyer existant avec l'ID " + foyerExistant.getIdFoyer());
    }
}
