package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.validator.MentorValidator;

/**
 * Service per la gestione dei mentor all'interno degli hackathon.
 * Fornisce operazioni per aggiungere mentor a un hackathon specifico,
 * previa validazione tramite {@link MentorValidator}.
 */
public class MentorService {
    private final MentorValidator mentorValidator;
    private final HackathonRepository hackathonRepository;

    /**
     * Costruisce un'istanza di {@code MentorService} con le dipendenze richieste.
     *
     * @param mValidator il validator utilizzato per verificare che un utente
     *                   possa ricoprire il ruolo di mentor
     * @param hRepo      il repository per accedere e aggiornare i dati degli hackathon
     */
    public MentorService(MentorValidator mValidator, HackathonRepository hRepo) {
        this.mentorValidator = mValidator;
        this.hackathonRepository = hRepo;
    }

    /**
     * Aggiunge un mentor a un hackathon specificato tramite il suo identificativo.
     *
     * Prima di procedere con l'aggiunta, l'utente viene validato tramite
     * {@link MentorValidator#validate(User)}. Se la validazione fallisce,
     * il metodo termina senza apportare modifiche.
     *
     * @param mentor l'utente da aggiungere come mentor all'hackathon
     * @param hId    l'identificativo univoco dell'hackathon a cui aggiungere il mentor
     */
    public void addMentor(User mentor, Long hId) {
        if (!mentorValidator.validate(mentor)) return;
        hackathonRepository.getById(hId).getMentors().add(mentor);
        hackathonRepository.update(hackathonRepository.getById(hId));
    }
}