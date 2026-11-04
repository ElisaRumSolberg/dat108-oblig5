package no.hvl.dat108.oblig4.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.model.Feilmeldinger;

public record DeltagerPostRequestDTO(

        @NotNull
        @Pattern(
                regexp = "^\\d{8}$",
                message = Feilmeldinger.UGYLDIG_MOBIL_MLD
        )
        String mobil,

        @NotNull
        @Size(
                min = 4,
                message = Feilmeldinger.UGYLDIG_PASSORD_MLD
        )
        String passord,

        @NotNull
        @Pattern(
                regexp = "^[A-ZÆØÅ][\\p{L} -]{1,19}$",
                message = Feilmeldinger.UGYLDIG_FORNAVN_MLD
        )
        String fornavn,

        @NotNull
        @Pattern(
                regexp = "^[A-ZÆØÅ][\\p{L}-]{1,19}$",
                message = Feilmeldinger.UGYLDIG_ETTERNAVN_MLD
        )
        String etternavn,

        @NotNull
        @Pattern(
                regexp = "^(mann|kvinne)$",
                message = Feilmeldinger.UGYLDIG_KJONN_MLD
        )
        String kjonn

) {

    public Deltager lagDeltager(String hashetPassord) {

        return new Deltager(
                mobil,
                hashetPassord,
                fornavn,
                etternavn,
                kjonn
        );
    }
}