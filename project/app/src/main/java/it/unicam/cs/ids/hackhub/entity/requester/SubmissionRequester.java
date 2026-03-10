package it.unicam.cs.ids.hackhub.entity.requester;

import it.unicam.cs.ids.hackhub.entity.model.Submission;

/**
 * Rappresenta un richiedente di submission.
 * </p>
 * Questa classe estende {@link Submission} e serve come specializzazione
 * per i casi d'uso in cui è necessario distinguere una submission richiesta
 * (ad esempio in logiche di workflow o richieste).
 */
public class SubmissionRequester extends Submission {
}
