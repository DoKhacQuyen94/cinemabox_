package vn.rikkei.cinemabox.showtime;

import java.time.LocalDateTime;

public record ShowtimeResponse(
        Long id,
        Long movieId,
        String movieTitle,
        Long roomId,
        String roomName,
        LocalDateTime startTime,
        Long price,
        Integer availableSeats) {

    public static ShowtimeResponse from(Showtime s) {
        return new ShowtimeResponse(
                s.getId(),
                s.getMovie().getId(),
                s.getMovie().getTitle(),
                s.getRoom().getId(),
                s.getRoom().getName(),
                s.getStartTime(),
                s.getPrice(),
                s.getAvailableSeats()
        );
    }
}