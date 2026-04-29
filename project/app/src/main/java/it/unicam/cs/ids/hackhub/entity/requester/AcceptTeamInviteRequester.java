package it.unicam.cs.ids.hackhub.entity.requester;

public record AcceptTeamInviteRequester(
        Long notificationId,
        String invitedEmail, //Utente che viene invitato a entrare nel team
        String team
        //TODO: rimuovere requester e passare solo editorId (utente invitato) e notificationId (per recuperare il teamId e l'email dell'invitante dal database)
) {
}
