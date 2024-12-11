package tn.esprit.tpfoyer17;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer17.controllers.BlocController;
import tn.esprit.tpfoyer17.controllers.FoyerController;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.services.IBlocService;
import tn.esprit.tpfoyer17.services.IFoyerService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllerTests {

    private MockMvc blocMockMvc;
    private MockMvc foyerMockMvc;

    @Mock
    private IBlocService blocService;

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private BlocController blocController;

    @InjectMocks
    private FoyerController foyerController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blocMockMvc = MockMvcBuilders.standaloneSetup(blocController).build();
        foyerMockMvc = MockMvcBuilders.standaloneSetup(foyerController).build();
    }

    // --- BlocController Tests ---

    private Bloc createDummyBloc() {
        return Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .capaciteBloc(50L)
                .build();
    }

    @Test
    void testAddingBloc() throws Exception {
        Bloc bloc = createDummyBloc();
        when(blocService.addBloc(any(Bloc.class))).thenReturn(bloc);

        blocMockMvc.perform(post("/api/blocs/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"));

        verify(blocService, times(1)).addBloc(any(Bloc.class));
    }

    @Test
    void testGettingAllBloc() throws Exception {
        List<Bloc> blocs = Collections.singletonList(createDummyBloc());
        when(blocService.getAllBlocs()).thenReturn(blocs);

        blocMockMvc.perform(get("/api/blocs/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idBloc").value(1))
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"));

        verify(blocService, times(1)).getAllBlocs();
    }

    @Test
    void testUpdatingBloc() throws Exception {
        Bloc bloc = createDummyBloc();
        when(blocService.updateBloc(any(Bloc.class))).thenReturn(bloc);

        blocMockMvc.perform(put("/api/blocs/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"));

        verify(blocService, times(1)).updateBloc(any(Bloc.class));
    }

    // --- FoyerController Tests ---

    private Foyer createDummyFoyer() {
        return Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer A")
                .capaciteFoyer(100L)
                .build();
    }

    @Test
    void testAddingFoyer() throws Exception {
        Foyer foyer = createDummyFoyer();
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        foyerMockMvc.perform(post("/api/foyers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));

        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }

    @Test
    void testGettingAllFoyer() throws Exception {
        List<Foyer> foyers = Collections.singletonList(createDummyFoyer());
        when(foyerService.getAllFoyers()).thenReturn(foyers);

        foyerMockMvc.perform(get("/api/foyers/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idFoyer").value(1))
                .andExpect(jsonPath("$[0].nomFoyer").value("Foyer A"));

        verify(foyerService, times(1)).getAllFoyers();
    }

    @Test
    void testUpdatingFoyer() throws Exception {
        Foyer foyer = createDummyFoyer();
        when(foyerService.updateFoyer(any(Foyer.class))).thenReturn(foyer);

        foyerMockMvc.perform(put("/api/foyers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));

        verify(foyerService, times(1)).updateFoyer(any(Foyer.class));
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() throws Exception {
        Foyer foyer = createDummyFoyer();
        when(foyerService.ajouterFoyerEtAffecterAUniversite(any(Foyer.class), eq(1L))).thenReturn(foyer);

        foyerMockMvc.perform(post("/api/foyers/ajouter-affecter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer))
                        .param("idUniversite", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));

        verify(foyerService, times(1)).ajouterFoyerEtAffecterAUniversite(any(Foyer.class), eq(1L));
    }
}
