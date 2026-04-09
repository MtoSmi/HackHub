package it.unicam.cs.ids.hackhub.entity.requester;

public record TeamInviteRequester(
        String invitingEmail, //Utente che invita nel proprio team
        String invitedEmail, //Utente che viene invitato a entrare nel team
        Long teamId

) {
}
