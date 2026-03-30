package it.unicam.cs.ids.hackhub.entity.dto;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        Long team,
        String rank
) {}