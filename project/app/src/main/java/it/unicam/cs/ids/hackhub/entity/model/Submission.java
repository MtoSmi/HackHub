package it.unicam.cs.ids.hackhub.entity.model;

import java.time.LocalDateTime;

public class Submission {
    private long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Response> responses;
    private boolean complete;
}
