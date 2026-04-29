package it.unicam.cs.ids.hackhub.entity.requester;

public record SubscribeHackathonRequester(
        Long hackathonId,
        String email
        //TODO: eliminare per iscrizione team a hackathon, viene passato hId e uId (da uId viene recuperato team)
) {
}
