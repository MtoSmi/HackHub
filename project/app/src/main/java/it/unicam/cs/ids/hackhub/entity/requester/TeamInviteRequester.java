package it.unicam.cs.ids.hackhub.entity.requester;
//TODO: eliminare e controllare su interface come sistemare
public record TeamInviteRequester(
        String invitingEmail, //Utente che invita nel proprio team
        String invitedEmail, //Utente che viene invitato a entrare nel team
        Long teamId

) {
}
