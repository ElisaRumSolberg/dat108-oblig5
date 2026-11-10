package no.hvl.dat108.oblig4;

import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.repository.DeltagerRepository;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeltagerDbServiceTest {

    static DeltagerDbService dbService;
    @Mock static DeltagerRepository deltagerRepository;

    @Test
    void finnAlleSortertPaaFulltNavn() {
        dbService = new DeltagerDbService(deltagerRepository);
        List<Deltager> alle = Arrays.asList(
                new Deltager("00000001", "pass", "John Townes", "Van Zandt", "Mann"),
                new Deltager("00000002", "pass", "Jessica", "Pratt", "Kvinne"),
                new Deltager("00000003", "pass", "Nora", "Brown", "Kvinne"),
                new Deltager("00000004", "pass", "Bill", "Callahan", "Mann"),
                new Deltager("00000005", "pass", "Jody", "Stecher", "Mann"),
                new Deltager("00000006", "pass", "Karen", "Dalton", "Kvinne"),
                new Deltager("00000007", "pass", "Anne", "Briggs", "Kvinne"),
                new Deltager("00000008", "pass", "Joan", "Baez", "Kvinne"),
                new Deltager("00000009", "pass", "Amy Lou", "Keeler", "Kvinne"),
                new Deltager("00000010", "pass", "Anna", "St. Louis", "Kvinne"),
                new Deltager("00000011", "pass", "Shirley", "Collins", "Kvinne")
        );
        List<Deltager> sortert = Arrays.asList(
                new Deltager("00000009", "pass", "Amy Lou", "Keeler", "Kvinne"),
                new Deltager("00000010", "pass", "Anna", "St. Louis", "Kvinne"),
                new Deltager("00000007", "pass", "Anne", "Briggs", "Kvinne"),
                new Deltager("00000004", "pass", "Bill", "Callahan", "Mann"),
                new Deltager("00000002", "pass", "Jessica", "Pratt", "Kvinne"),
                new Deltager("00000008", "pass", "Joan", "Baez", "Kvinne"),
                new Deltager("00000005", "pass", "Jody", "Stecher", "Mann"),
                new Deltager("00000001", "pass", "John Townes", "Van Zandt", "Mann"),
                new Deltager("00000006", "pass", "Karen", "Dalton", "Kvinne"),
                new Deltager("00000003", "pass", "Nora", "Brown", "Kvinne"),
                new Deltager("00000011", "pass", "Shirley", "Collins", "Kvinne")
        );

        Mockito.when(deltagerRepository.findAll()).thenReturn(alle);
        List<Deltager> resultat = dbService.finnAlleSortertPaaFulltNavn();

        for (int i = 0; i < resultat.size(); i++) {
            assertTrue(resultat.get(i).getMobil().equals(sortert.get(i).getMobil()));
        }
    }
}
