package tn.esprit.tpfoyer17.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // Cette annotation assure que idBloc n'est pas modifiable via le setter
    long idBloc;

    String nomBloc;

    long capaciteBloc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foyer_id") // Spécifier explicitement le nom de la colonne
    @ToString.Exclude
    Foyer foyer;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    Set<Chambre> chambres;

    // Optionnel : Un constructeur personnalisé si nécessaire (par exemple, sans foyer et chambres)
    public Bloc(String nomBloc, long capaciteBloc) {
        this.nomBloc = nomBloc;
        this.capaciteBloc = capaciteBloc;
    }

    // Utilisation du Builder pour construire un Bloc
    public static class BlocBuilder {
        // Votre code ici si vous avez besoin de personnaliser des méthodes de construction
    }
}
