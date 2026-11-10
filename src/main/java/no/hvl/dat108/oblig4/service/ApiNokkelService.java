package no.hvl.dat108.oblig4.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiNokkelService {

    @Value("${app.api-key}")
    private String denEnesteGyldigeApiNokkelen;

    public boolean erNokkelGyldig(String nokkel) {
        return nokkel != null
                && denEnesteGyldigeApiNokkelen.equals(nokkel);
    }
}