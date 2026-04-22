package it.unicam.cs.ids.hackhub.entity.requester;

public record ViolationRequester(
        String description,
        Long teamId,
        Long hostId
) {
}
