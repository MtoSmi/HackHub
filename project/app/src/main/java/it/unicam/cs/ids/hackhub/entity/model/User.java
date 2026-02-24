package it.unicam.cs.ids.hackhub.entity.model;

import it.unicam.cs.ids.hackhub.entity.enumeration.Rank;

/**
 * Rappresenta un utente del sistema HackHub.
 */
public class User {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Team team = null;
    private Rank rank;
}
