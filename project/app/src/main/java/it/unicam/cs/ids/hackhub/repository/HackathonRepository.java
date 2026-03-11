package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository per la gestione degli hackathon.
 * Implementa l'interfaccia {@link Repository} per l'entità {@link Hackathon}.
 * Fornisce operazioni CRUD e metodi di ricerca specifici per gli hackathon.
 */
public class HackathonRepository implements Repository<Hackathon> {

    private List<Hackathon> hackathons;

    /**
     * Costruisce un nuovo {@code HackathonRepository} con una lista vuota di hackathon.
     */
    public HackathonRepository() {
        hackathons = new ArrayList<>();
    }

    /**
     * Restituisce tutti gli hackathon presenti nel repository.
     *
     * @return una lista contenente tutti gli hackathon
     */
    @Override
    public List<Hackathon> getAll() {
        return hackathons;
    }

    /**
     * Restituisce tutti gli hackathon che hanno lo stato specificato.
     *
     * @param s lo stato da utilizzare come filtro
     * @return una lista di hackathon con lo stato specificato
     */
    public List<Hackathon> getHackathonsByStatus(Status s) {
        return hackathons.stream().filter(h -> h.getStatus().equals(s)).toList();
    }

    /**
     * Restituisce l'hackathon con l'identificativo specificato.
     *
     * @param id l'identificativo univoco dell'hackathon da cercare
     * @return l'hackathon corrispondente all'id, oppure {@code null} se non trovato
     */
    @Override
    public Hackathon getById(Long id) {
        for (Hackathon h : hackathons) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    /**
     * Aggiunge un nuovo hackathon al repository, assegnandogli un identificativo.
     *
     * @param h l'hackathon da aggiungere al repository
     */
    @Override
    public void create(Hackathon h) {
        h.setId(1L);
        hackathons.add(h);
    }

    /**
     * Aggiorna un hackathon esistente nel repository.
     * L'hackathon da aggiornare viene individuato tramite il suo identificativo.
     * Se trovato, l'hackathon precedente viene rimosso e sostituito con quello nuovo.
     *
     * @param newH l'hackathon con i dati aggiornati
     */
    @Override
    public void update(Hackathon newH) {
        for (Hackathon oldH : hackathons) {
            if (oldH.getId().equals(newH.getId())) {
                hackathons.remove(oldH);
                hackathons.add(newH);
            }
        }
    }
}