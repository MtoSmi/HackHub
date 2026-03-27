package it.unicam.cs.ids.hackhub.repository;

import it.unicam.cs.ids.hackhub.entity.enumeration.Status;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Repository per la gestione degli hackathon.
 * Implementa l'interfaccia {@link Repository} per l'entità {@link Hackathon}.
 * Fornisce operazioni CRUD e metodi di ricerca specifici per gli hackathon.
 */
@org.springframework.stereotype.Repository
public interface HackathonRepository extends Repository<Hackathon> {


    /**
     * Restituisce tutti gli hackathon presenti nel repository.
     *
     * @return una lista contenente tutti gli hackathon
     */
    @Override
    @NonNull
    List<Hackathon> findAll();

    List<Hackathon> findHackathonsByStatus(Status s);

    List<Hackathon> findHackathonByParticipantsIsContaining(Optional<Team> participants);

    /**
     * Restituisce l'hackathon con l'identificativo specificato.
     *
     * @param id l'identificativo univoco dell'hackathon da cercare
     * @return l'hackathon corrispondente all'id, oppure {@code null} se non trovato
     */
    @Override
    @NonNull
    Optional<Hackathon> findById(@NonNull Long id);
}