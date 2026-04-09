package it.unicam.cs.ids.hackhub.entity.requester;

public record AcceptTeamInviteRequester(
        Long notificationId,
        String invitedEmail, //Utente che viene invitato a entrare nel team
        String team
) {
}
