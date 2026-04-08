package it.unicam.cs.ids.hackhub.entity.requester;

public record UserUpdateRequester(
        Long id,
        String name,
        String surname,
        String email,
        String password
) {
}
