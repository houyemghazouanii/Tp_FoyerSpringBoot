package tn.esprit.tpfoyer17;

import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.services.BlocService;
import tn.esprit.tpfoyer17.services.ChambreService;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)  // Utilisation uniquement de JUnit 5
@SpringBootTest
@Slf4j
public class ChambreTest {

    @Autowired
    private ChambreService chambreService;
    @Autowired
    private ChambreRepository chambreRepository;
    @Autowired
    private BlocService blocService;
    private Long idChambre;

    @Test
    @Order(1)
    public void testAjouterChambreAuBloc() {
        // Récupérer un bloc existant (par exemple, avec l'ID 1)
        long blocId = 3; // Remplacez par un ID valide de bloc existant dans la base de données
        Bloc blocExistant = blocService.getBlocById(blocId);

        // Vérifier que le bloc existe
        assertNotNull(blocExistant, "Le bloc avec l'ID " + blocId + " n'existe pas.");

        // Créer une chambre et l'ajouter au bloc existant
        Chambre chambre = Chambre.builder()
                .numeroChambre(200)
                .typeChambre(TypeChambre.DOUBLE) // Enum correspondant au type de la chambre
                .bloc(blocExistant) // Affecter cette chambre au bloc existant
                .build();

        // Ajouter la chambre via le service
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Vérifier que la chambre a bien été ajoutée
        assertNotNull(savedChambre.getIdChambre(), "L'ID de la chambre ne doit pas être nul");
        Assertions.assertEquals(200,savedChambre.getNumeroChambre());
        Assertions.assertEquals(TypeChambre.DOUBLE, savedChambre.getTypeChambre());

        // Vérifier que la chambre est bien affectée au bloc existant
        Assertions.assertEquals(blocExistant.getIdBloc(), savedChambre.getBloc().getIdBloc(),
                "La chambre doit être affectée au bloc existant avec l'ID " + blocExistant.getIdBloc());
    }

    @Test
    @Order(2)
    public void testRecupererToutesLesChambres() {
        // Récupérer toutes les chambres
        var chambres = chambreService.getAllChambres();

        // Vérifier que la liste n'est pas vide
        assertNotNull(chambres, "La liste des chambres ne doit pas être nulle");
        Assertions.assertFalse(chambres.isEmpty(), "La liste des chambres ne doit pas être vide");
        log.info("Liste des chambres : {}", chambres);
    }

    @Test
    @Order(3)
    public void testMiseAJourChambre() {
        // Récupérer une chambre existante
        long chambreId = 13; // Remplacez par un ID valide d'une chambre existante
        Chambre chambre = chambreService.getChambreById(chambreId);

        // Vérifier que la chambre existe
        assertNotNull(chambre, "La chambre avec l'ID " + chambreId + " n'existe pas.");

        // Modifier certaines propriétés de la chambre
        chambre.setNumeroChambre(10);
        chambre.setTypeChambre(TypeChambre.DOUBLE);

        // Sauvegarder les modifications
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Vérifier que les modifications ont été sauvegardées
        Assertions.assertEquals(10, updatedChambre.getNumeroChambre());
        Assertions.assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());
    }


    @Test
    void testDeleteChambre() {
        // Supposons qu'une chambre existe déjà dans la base de données avec un ID spécifique
        long chambreId = 15; // L'ID de la chambre que vous souhaitez supprimer

        // Vérifier que la chambre existe avant la suppression
        Chambre existingChambre = chambreRepository.findById(chambreId).orElse(null);
        assertNotNull(existingChambre, "La chambre doit exister avant la suppression.");

        // Supprimer la chambre
        chambreRepository.deleteById(chambreId);

        // Vérifier que la chambre a bien été supprimée
        Chambre deletedChambre = chambreRepository.findById(chambreId).orElse(null);
        assertNull(deletedChambre, "La chambre devrait être nulle après suppression.");
    }

}