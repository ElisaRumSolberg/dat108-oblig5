package no.hvl.dat108.oblig4.service;

import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.repository.DeltagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeltagerDbService {

    private final DeltagerRepository deltagerRepository;

    public DeltagerDbService(DeltagerRepository deltagerRepository) {
        this.deltagerRepository = deltagerRepository;
    }

    /**
     * @param mobil
     * @return En deltager med gitt mobilnummer, null hvis ingen.
     */
    public Deltager finnDeltagerMedMobil(String mobil) {

        return deltagerRepository
                .findById(mobil)
                .orElse(null);
    }

    /**
     * @return Alle deltagerne, sortert stigende på fullt navn.
     */
    public List<Deltager> finnAlleSortertPaaFulltNavn() {

        return deltagerRepository
                .findAll()
                .stream()
                .sorted(Deltager.paaFulltNavn)
                .toList();
    }

    /**
     * @param mobil
     * @return Om deltager med gitt mobil er påmeldt
     */
    public boolean erDeltagerPaameldt(String mobil) {

        return deltagerRepository.existsById(mobil);
    }

    /**
     * Lagrer en deltager i databasen.
     *
     * @param deltager
     */
    public void lagreDeltager(Deltager deltager) {

        deltagerRepository.save(deltager);
    }
}