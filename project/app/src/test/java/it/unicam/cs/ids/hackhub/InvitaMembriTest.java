package it.unicam.cs.ids.hackhub;

import it.unicam.cs.ids.hackhub.controller.NotificationInterfaceController;
import it.unicam.cs.ids.hackhub.controller.TeamInterfaceController;
import it.unicam.cs.ids.hackhub.controller.UserInterfaceController;
import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;
import it.unicam.cs.ids.hackhub.entity.model.Notification;
import it.unicam.cs.ids.hackhub.entity.model.Team;
import it.unicam.cs.ids.hackhub.entity.model.User;
import it.unicam.cs.ids.hackhub.entity.requester.TeamRequester;
import it.unicam.cs.ids.hackhub.entity.requester.UserRequester;
import it.unicam.cs.ids.hackhub.repository.NotificationRepository;
import it.unicam.cs.ids.hackhub.repository.TeamRepository;
import it.unicam.cs.ids.hackhub.repository.UserRepository;
import it.unicam.cs.ids.hackhub.service.NotificationService;
import it.unicam.cs.ids.hackhub.service.TeamService;
import it.unicam.cs.ids.hackhub.service.UserService;
import it.unicam.cs.ids.hackhub.validator.TeamValidator;
import it.unicam.cs.ids.hackhub.validator.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InvitaMembriTest {
    private final UserRequester teamLeader = new UserRequester();
    private TeamInterfaceController teamController;
    private UserInterfaceController userController;
    private NotificationInterfaceController notificationController;

    @BeforeEach
    public void setup() {
        teamLeader.setName("Luigi");
        teamLeader.setSurname("Verdi");
        teamLeader.setEmail("luigi.verdi@tim.it");
        teamLeader.setPassword("Password5678");
        TeamValidator teamValidator = new TeamValidator();
        teamController = new TeamInterfaceController(new TeamService(new TeamRepository(), new UserRepository(), new NotificationService(new NotificationRepository(), new UserRepository()), teamValidator));
        UserValidator userValidator = new UserValidator();
        userController = new UserInterfaceController(new UserService(new UserRepository(), userValidator));
        notificationController = new NotificationInterfaceController(new NotificationService(new NotificationRepository(), new UserRepository()));
    }

    // Invito Valido
    @Test
    public void testInvitaMembriValido() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Registrazione Utente 2 (invitato)
        UserRequester userRequester = createValidUserRequest();
        User registrationUserResult = userController.registration(userRequester);
        Assertions.assertNotNull(registrationUserResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Invito Utente 2 al team
        teamController.inviteMember(registrationUserResult, registrationTeamResult);
        // Controllo Notifiche
        List<Notification> notifications = notificationController.showMyNotifications(registrationUserResult.getId());
        Assertions.assertNotNull(notifications, "La lista delle notifiche non dovrebbe essere null");
        Assertions.assertFalse(notifications.isEmpty(), "La lista delle notifiche non dovrebbe essere vuota");
        // Controllo Notifica specifica
        Notification notification = notificationController.showSelectedNotification(notifications.getFirst().getId());
        Assertions.assertNotNull(notification, "La notifica specifica non dovrebbe essere null");
        Assertions.assertEquals("Invito al Team", notification.getTitle(), "Il titolo della notifica dovrebbe essere 'Invito al Team'");
        Assertions.assertEquals("Sei stato invitato a unirti al team " + registrationTeamResult.getName(), notification.getDescription(), "Il messaggio della notifica dovrebbe indicare l'invito al team con il nome del team");
        Assertions.assertEquals(registrationUserResult, notification.getTo(), "Il destinatario della notifica dovrebbe essere l'utente invitato");
    }

    @Test
    public void testInvitaMembriRankMembroTeam() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Registrazione Utente 2 (invitato)
        UserRequester userRequester = createValidUserRequest();
        User registrationUserResult = userController.registration(userRequester);
        // Modifica rank Utente 2 (MEMBRO_TEAM)
        registrationUserResult.setRank(Rank.MEMBRO_TEAM);
        Assertions.assertNotNull(registrationUserResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Invito Utente 2 al team
        teamController.inviteMember(registrationUserResult, registrationTeamResult);
        // Controllo Notifiche
        List<Notification> notifications = notificationController.showMyNotifications(registrationUserResult.getId());
        // Controllo notifiche
        Assertions.assertNull(notifications, "La lista delle notifiche dovrebbe essere null per un utente con rank MEMBRO_TEAM che viene invitato a un team");
    }

    @Test
    public void testInvitaMembriRankOrganizzatore() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Registrazione Utente 2 (invitato)
        UserRequester userRequester = createValidUserRequest();
        User registrationUserResult = userController.registration(userRequester);
        // Modifica rank Utente 2 (ORGANIZZATORE)
        registrationUserResult.setRank(Rank.ORGANIZZATORE);
        Assertions.assertNotNull(registrationUserResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Invito Utente 2 al team
        teamController.inviteMember(registrationUserResult, registrationTeamResult);
        // Controllo Notifiche
        List<Notification> notifications = notificationController.showMyNotifications(registrationUserResult.getId());
        // Controllo notifiche
        Assertions.assertNull(notifications, "La lista delle notifiche dovrebbe essere null per un utente con rank ORGANIZZATORE che viene invitato a un team");
    }

    @Test
    public void testInvitaMembriRankGiudice() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Registrazione Utente 2 (invitato)
        UserRequester userRequester = createValidUserRequest();
        User registrationUserResult = userController.registration(userRequester);
        // Modifica rank Utente 2 (GIUDICE)
        registrationUserResult.setRank(Rank.GIUDICE);
        Assertions.assertNotNull(registrationUserResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Invito Utente 2 al team
        teamController.inviteMember(registrationUserResult, registrationTeamResult);
        // Controllo Notifiche
        List<Notification> notifications = notificationController.showMyNotifications(registrationUserResult.getId());
        // Controllo notifiche
        Assertions.assertNull(notifications, "La lista delle notifiche dovrebbe essere null per un utente con rank GIUDICE che viene invitato a un team");
    }

    @Test
    public void testInvitaMembriRankMentore() {
        // Registrazione Utente 1 (team Leader)
        User registrationLeaderResult = userController.registration(teamLeader);
        Assertions.assertNotNull(registrationLeaderResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Creazione del team
        TeamRequester teamRequest = createValidTeamRequest();
        Team registrationTeamResult = teamController.creationTeam(teamRequest);
        Assertions.assertNotNull(registrationTeamResult, "La creazione del team dovrebbe avere successo e restituire un team non null");
        // Registrazione Utente 2 (invitato)
        UserRequester userRequester = createValidUserRequest();
        User registrationUserResult = userController.registration(userRequester);
        // Modifica rank Utente 2 (MENTORE)
        registrationUserResult.setRank(Rank.MENTORE);
        Assertions.assertNotNull(registrationUserResult, "La registrazione dell'utente dovrebbe avere successo e restituire un utente non null");
        // Invito Utente 2 al team
        teamController.inviteMember(registrationUserResult, registrationTeamResult);
        // Controllo Notifiche
        List<Notification> notifications = notificationController.showMyNotifications(registrationUserResult.getId());
        // Controllo notifiche
        Assertions.assertNull(notifications, "La lista delle notifiche dovrebbe essere null per un utente con rank MENTORE che viene invitato a un team");
    }

    // Helper method per creare una richiesta di registrazione utente valida
    private UserRequester createValidUserRequest() {
        UserRequester request = new UserRequester();
        request.setName("Mario");
        request.setSurname("Rossi");
        request.setEmail("mario.rossi@aol.me");
        request.setPassword("Password123");
        return request;
    }

    // Helper method per creare una richiesta di creazione team valida
    private TeamRequester createValidTeamRequest() {
        TeamRequester request = new TeamRequester();
        request.setName("Team 1");
        request.setMembers(List.of(teamLeader));
        return request;
    }
}
