package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.validator.MentorValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
/**
 * Service per la gestione dei mentor all'interno degli hackathon.
 * Fornisce operazioni per aggiungere mentor a un hackathon specifico,
 * previa validazione tramite {@link MentorValidator}.
 */
import java.util.List;

@Service
public class MentorService {
    private final MentorValidator mentorValidator;
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;

    /**
     * Costruisce un'istanza di {@code MentorService} con le dipendenze richieste.
     *
     * @param mValidator il validator utilizzato per verificare che un utente
     *                   possa ricoprire il ruolo di mentor
     * @param hRepo      il repository per accedere e aggiornare i dati degli hackathon
     */
    public MentorService(MentorValidator mValidator, UserRepository uRepo, HackathonRepository hRepo) {
        this.mentorValidator = mValidator;
        this.userRepository = uRepo;
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
    public boolean addMentor(String mentor, Long hId) {
        User user = userRepository.findByEmail(mentor);
        if(!mentorValidator.validate(user)) return false;
        user.setRank(Rank.MENTORE);
        Hackathon h = hackathonRepository.getReferenceById(hId);
        h.getMentors().add(user);

        hackathonRepository.save(h);
        return true;

    }
}
