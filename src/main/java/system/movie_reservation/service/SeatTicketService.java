package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.seat.SeatTicket;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.repository.MovieTheaterRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SeatTicketService {

    private final MovieTheaterRepository movieTheaterRepository;

    public SeatTicketService(MovieTheaterRepository movieTheaterRepository) {
        this.movieTheaterRepository = movieTheaterRepository;
    }

    void saveSeatWithTickets(Ticket ticket){
        verifySeatsAvailable(ticket);
        MovieTheater movieTheater = insertSelectedSeatsTicket(ticket);
        movieTheaterRepository.save(movieTheater);
    }

    void updateTicketFromSeat(Ticket oldTicket, Ticket newTicket){
        verifySeatsAvailable(newTicket);
        MovieTheater movieTheater = removeSelectedSeatsTicket(oldTicket);
        newTicket.setMovieTheater(movieTheater);
        MovieTheater movieTheaterInserted = insertSelectedSeatsTicket(newTicket);
        movieTheaterRepository.save(movieTheaterInserted);
    }

    void removeTicketFromSeat(Ticket ticket){
        MovieTheater movieTheater = removeSelectedSeatsTicket(ticket);
        movieTheaterRepository.save(movieTheater);
    }

    void deleteAllTicketsFromSeat(){
        List<MovieTheater> allMovieTheater = movieTheaterRepository.findAll();

        allMovieTheater.forEach(stTicket ->
                stTicket.getSeatTicket().forEach(value ->
                        value.setTicketValue(null)
                )
        );
        movieTheaterRepository.saveAll(allMovieTheater);
    }

    private void verifySeatsAvailable(Ticket ticket){
        List<SeatTicket> seatTicketList = ticket.getMovieTheater().getSeatTicket();
        Map<String,SeatTicket> seatTicketMap = seatTicketList.stream()
                .collect(Collectors.toMap(SeatTicket::getSeatKey,seatTicket -> seatTicket));

        StringBuilder seatsUnavailable = new StringBuilder();

        for (String seat : ticket.getSeat()) {

            if(seatTicketMap.containsKey(seat) && seatTicketMap.get(seat).getTicketValue() != null)
                    seatsUnavailable.append(seat).append(" ");
        }
        if (!seatsUnavailable.isEmpty())
            throw new NoSuchElementException(seatsUnavailable + "Seats are no available");
    }

    private MovieTheater insertSelectedSeatsTicket(Ticket ticket){
        MovieTheater movieTheater = ticket.getMovieTheater();
        List<SeatTicket> seatTickets = movieTheater.getSeatTicket();

        for (String seat : ticket.getSeat()) {
            seatTickets.stream().filter(n -> n.getSeatKey().equals(seat))
                    .findFirst()
                    .ifPresent(st -> st.setTicketValue(ticket));
        }
        movieTheater.setSeatTicket(seatTickets);
        return movieTheater;
    }

    private MovieTheater removeSelectedSeatsTicket(Ticket ticket){
        MovieTheater movieTheater = ticket.getMovieTheater();
        List<SeatTicket> seatTickets = movieTheater.getSeatTicket();

        seatTickets.stream()
                .filter(st -> st.getTicketValue() != null &&
                        st.getTicketValue().equals(ticket))
                .forEach(st -> st.setTicketValue(null));

        movieTheater.setSeatTicket(seatTickets);
        return movieTheater;
    }
}
