package no.hvl.dat108.oblig4.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat108.oblig4.model.Deltager;


public class LogginnService {

    /**
     * Sjekker om brukeren for gjeldende request/sesjon er innlogget
     * @param request
     * @return
     */
    public static boolean erBrukerLoggetInn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        return session != null && session.getAttribute("user") != null;
    }

    /**
     * Logger ut evt. innlogget bruker fra gjeldende request/sesjon
     * @param request
     */
    public static void loggUtBruker(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Logger inn bruker for gjeldende request
     * @param request
     * @param deltager Brukeren som skal logges inn
     */
    public static void loggInnBruker(HttpServletRequest request, Deltager deltager) {

        HttpSession session = request.getSession(true);

        session.setAttribute("user", deltager);
    }


}
