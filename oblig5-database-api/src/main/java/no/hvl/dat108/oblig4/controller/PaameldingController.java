package no.hvl.dat108.oblig4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.model.DeltagerSkjema;
import no.hvl.dat108.oblig4.model.Feilmeldinger;
import no.hvl.dat108.oblig4.service.DeltagerDbService;
import no.hvl.dat108.oblig4.service.LogginnService;
import no.hvl.dat108.oblig4.service.PassordService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaameldingController {

    /*
     * GET /paamelding
     */
    @GetMapping("/paamelding")
    public String henteSkjema() {

        return "paameldingsskjema";
    }

    /*
     * POST /paamelding
     */
    @PostMapping("/paamelding")
    public String meldePaa(
            HttpServletRequest request,
            @Valid @ModelAttribute("deltager") DeltagerSkjema skjema,
            BindingResult bindingResult) {

        String feilmelding =
                meldingFraInputValidering(skjema, bindingResult);

        if (feilmelding != null) {
            request.setAttribute("melding", feilmelding);
            return "paameldingsskjema";
        }

        String hashetPassord =
                PassordService.lagHashetPassord(skjema.getPassord());

        Deltager deltager =
                skjema.lagDeltager(hashetPassord);

        DeltagerDbService.lagreDeltager(deltager);

        LogginnService.loggInnBruker(request, deltager);

        return "redirect:/paameldt";
    }

    /*
     * GET /paameldt
     */
    @GetMapping("/paameldt")
    public String hentePaameldt(
            HttpServletRequest request,
            RedirectAttributes ra) {

        if (!LogginnService.erBrukerLoggetInn(request)) {

            ra.addFlashAttribute(
                    "melding",
                    Feilmeldinger.KREVER_INNLOGGET_MLD
            );

            return "redirect:/innlogging";
        }

        return "paameldingsbekreftelse";
    }

    private String meldingFraInputValidering(
            DeltagerSkjema skjema,
            BindingResult bindingResult) {

        String melding = null;

        if (bindingResult.hasErrors()) {

            melding = Feilmeldinger.GENERELL_BINDING_MLD;

        } else if (!skjema.getPassord()
                .equals(skjema.getPassordRepetert())) {

            melding = Feilmeldinger.FEIL_REPETERT_PASSORD_MLD;

        } else if (DeltagerDbService
                .erDeltagerPaameldt(skjema.getMobil())) {

            melding = Feilmeldinger.ALLEREDE_PAAMELDT_MLD;
        }

        return melding;
    }
}