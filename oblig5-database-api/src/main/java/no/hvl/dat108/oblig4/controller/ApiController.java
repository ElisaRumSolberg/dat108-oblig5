package no.hvl.dat108.oblig4.controller;

import jakarta.validation.Valid;
import no.hvl.dat108.oblig4.dto.DeltagerPostRequestDTO;
import no.hvl.dat108.oblig4.dto.DeltagerResponseDTO;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.service.ApiNokkelService;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import no.hvl.dat108.oblig4.service.PassordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/deltagere")
public class ApiController {

    private final DeltagerDbService deltagerDbService;
    private final ApiNokkelService apiNokkelService;

    public ApiController(
            DeltagerDbService deltagerDbService,
            ApiNokkelService apiNokkelService) {

        this.deltagerDbService = deltagerDbService;
        this.apiNokkelService = apiNokkelService;
    }

    /*
     * GET /api/deltagere
     */
    @GetMapping
    public ResponseEntity<?> hentAlle(
            @RequestHeader(value = "Api-Key", required = false)
            String apiKey) {

        if (!apiNokkelService.erNokkelGyldig(apiKey)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Ugyldig eller manglende API-nøkkel");
        }

        List<DeltagerResponseDTO> deltagere =
                deltagerDbService
                        .finnAlleSortertPaaFulltNavn()
                        .stream()
                        .map(DeltagerResponseDTO::new)
                        .toList();

        return ResponseEntity.ok(deltagere);
    }

    /*
     * GET /api/deltagere/{mobil}
     */
    @GetMapping("/{mobil}")
    public ResponseEntity<?> hentEn(
            @RequestHeader(value = "Api-Key", required = false)
            String apiKey,
            @PathVariable String mobil) {

        if (!apiNokkelService.erNokkelGyldig(apiKey)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Ugyldig eller manglende API-nøkkel");
        }

        Deltager deltager =
                deltagerDbService.finnDeltagerMedMobil(mobil);

        if (deltager == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Fant ingen deltager med mobil " + mobil);
        }

        return ResponseEntity.ok(
                new DeltagerResponseDTO(deltager)
        );
    }

    /*
     * POST /api/deltagere
     */
    @PostMapping
    public ResponseEntity<?> opprettDeltager(
            @RequestHeader(value = "Api-Key", required = false)
            String apiKey,
            @Valid @RequestBody DeltagerPostRequestDTO dto) {

        if (!apiNokkelService.erNokkelGyldig(apiKey)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Ugyldig eller manglende API-nøkkel");
        }

        if (deltagerDbService.erDeltagerPaameldt(dto.mobil())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Deltager med dette mobilnummeret finnes allerede");
        }

        String hashetPassord =
                PassordService.lagHashetPassord(dto.passord());

        Deltager deltager =
                dto.lagDeltager(hashetPassord);

        deltagerDbService.lagreDeltager(deltager);

        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{mobil}")
                        .buildAndExpand(deltager.getMobil())
                        .toUri();

        return ResponseEntity
                .created(location)
                .body("Deltager opprettet");
    }
}