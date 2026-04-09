package it.unicam.cs.ids.hackhub.service;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Hackathon;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.repository.HackathonRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MentorService {
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;

    /**
     * Costruisce un'istanza di {@code MentorService} con le dipendenze richieste.
     *
     * @param hRepo      il repository per accedere e aggiornare i dati degli hackathon
     */
    public MentorService(UserRepository uRepo, HackathonRepository hRepo) {
        this.userRepository = uRepo;
        this.hackathonRepository = hRepo;
    }

    /**
     * Aggiunge un mentor a un hackathon specificato tramite il suo identificativo.
     *
     * Prima di procedere con l'aggiunta, l'utente viene validato tramite
     * Se la validazione fallisce, il metodo termina senza apportare modifiche.
     *
     * @param mentor l'utente da aggiungere come mentor all'hackathon
     * @param hId    l'identificativo univoco dell'hackathon a cui aggiungere il mentor
     */
    public boolean addMentor(String mentor, Long hId) {
        User user = userRepository.findByEmail(mentor);
        // TODO: check if user is already a mentor
//        if(!userValidator.validate(user)) return false;
//        return mentor.rank().equals(Rank.STANDARD);
        user.setRank(Rank.MENTORE);
        Hackathon h = hackathonRepository.getReferenceById(hId);
        h.getMentors().add(user);

        hackathonRepository.save(h);
        return true;

    }
}
