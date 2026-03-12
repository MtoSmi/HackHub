package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.model.HelpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository per la gestione delle richieste di aiuto ({@link HelpRequest}).
 * Implementa l'interfaccia {@link Repository} fornendo operazioni CRUD
 * su una lista in memoria.
 */
public class HelpRequestRepository implements Repository<HelpRequest> {

    private List<HelpRequest> helpRequests;

    /**
     * Costruisce un nuovo {@code HelpRequestRepository} con una lista vuota.
     */
    public HelpRequestRepository() {
        helpRequests = new ArrayList<>();
    }

    /**
     * Restituisce tutte le richieste di aiuto presenti nel repository.
     *
     * @return lista di tutte le {@link HelpRequest}
     */
    @Override
    public List<HelpRequest> getAll() {
        return helpRequests;
    }

    /**
     * Restituisce tutte le richieste di aiuto indirizzate a un determinato mentor.
     *
     * @param id l'identificativo del mentor
     * @return lista di {@link HelpRequest} destinate al mentor con l'id specificato
     */
    public List<HelpRequest> getByMentor(Long id) {
        return helpRequests.stream().filter(hr -> hr.getTo().getId().equals(id)).toList();
    }

    /**
     * Restituisce la richiesta di aiuto con l'identificativo specificato.
     *
     * @param id l'identificativo della richiesta di aiuto da cercare
     * @return la {@link HelpRequest} corrispondente all'id, oppure {@code null} se non trovata
     */
    @Override
    public HelpRequest getById(Long id) {
        for (HelpRequest hr : helpRequests) {
            if (hr.getId().equals(id)) return hr;
        }
        return null;
    }

    /**
     * Aggiunge una nuova richiesta di aiuto al repository.
     * Assegna un id fisso pari a {@code 1L} alla richiesta prima di inserirla.
     *
     * @param hr la {@link HelpRequest} da aggiungere
     */
    @Override
    public void create(HelpRequest hr) {
        hr.setId(1L);
        helpRequests.add(hr);
    }

    /**
     * Aggiorna una richiesta di aiuto esistente nel repository.
     * Sostituisce la richiesta con lo stesso id con quella nuova fornita.
     *
     * @param newHr la {@link HelpRequest} aggiornata che sostituirà quella esistente
     */
    @Override
    public void update(HelpRequest newHr) {
        for (HelpRequest oldHr : helpRequests) {
            if (oldHr.getId().equals(newHr.getId())) {
                helpRequests.remove(oldHr);
                helpRequests.add(newHr);
            }
        }
    }
}