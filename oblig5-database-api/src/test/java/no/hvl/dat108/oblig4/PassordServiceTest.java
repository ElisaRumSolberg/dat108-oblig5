package no.hvl.dat108.oblig4;

import no.hvl.dat108.oblig4.service.PassordService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassordServiceTest {

    @Test
    void erPassordKorrekt_skalReturnereTrueForRiktigPassord() {

        String passord = "abcd1234";
        String hashetPassord = PassordService.lagHashetPassord(passord);

        boolean resultat =
                PassordService.erPassordKorrekt(passord, hashetPassord);

        assertTrue(resultat);
    }

    @Test
    void erPassordKorrekt_skalReturnereFalseForFeilPassord() {

        String riktigPassord = "abcd1234";
        String feilPassord = "feilpassord";

        String hashetPassord =
                PassordService.lagHashetPassord(riktigPassord);

        boolean resultat =
                PassordService.erPassordKorrekt(feilPassord, hashetPassord);

        assertFalse(resultat);
    }
}