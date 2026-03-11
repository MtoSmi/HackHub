package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link Repository} per la gestione delle entità {@link Team}.
 * Fornisce operazioni CRUD di base utilizzando una lista in memoria.
 */
public class TeamRepository implements Repository<Team> {

    private List<Team> teams;

    /**
     * Costruisce un nuovo {@code TeamRepository} inizializzando la lista dei team vuota.
     */
    public TeamRepository() {
        teams = new ArrayList<>();
    }

    /**
     * Restituisce tutti i team presenti nel repository.
     *
     * @return una {@link List} contenente tutti i {@link Team}
     */
    @Override
    public List<Team> getAll() {
        return teams;
    }

    /**
     * Cerca e restituisce un team in base al suo identificativo univoco.
     *
     * @param id l'identificativo univoco del team da cercare
     * @return il {@link Team} corrispondente all'id specificato, oppure {@code null} se non trovato
     */
    @Override
    public Team getById(Long id) {
        for(Team t : teams) {
            if(t.getId().equals(id)) return t;
        }
        return null;
    }

    /**
     * Aggiunge un nuovo team al repository, assegnandogli un identificativo fisso.
     *
     * @param t il {@link Team} da aggiungere al repository
     */
    @Override
    public void create(Team t) {
        t.setId(1L);
        teams.add(t);
    }

    /**
     * Aggiorna un team esistente nel repository sostituendolo con uno nuovo avente lo stesso id.
     * Se non viene trovato alcun team con l'id specificato, non viene effettuata alcuna modifica.
     *
     * @param newT il {@link Team} aggiornato che sostituirà quello esistente con lo stesso id
     */
    @Override
    public void update(Team newT) {
        for(Team oldT : teams){
            if(oldT.getId().equals(newT.getId())){
                teams.remove(oldT);
                teams.add(newT);
            }
        }
    }
}