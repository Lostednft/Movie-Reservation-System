package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.Enum.MovieTime;
import system.movie_reservation.model.Movie;
import system.movie_reservation.model.Seat;
import system.movie_reservation.model.SeatTicket;
import system.movie_reservation.model.Ticket;
import system.movie_reservation.repository.SeatRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat getSeatById(Long id){
        return seatRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this ID."));
    }

    Seat getSeatByMovieAndMovieTime(Movie movie, MovieTime movieTime){
        return seatRepository.findByMovieAndMovieTime(movie, movieTime).orElseThrow(() ->
                new NoSuchElementException("No Seat found with this movie and movieTime."));
    }


    List<Seat> createSeatsToMovie(Movie movie){
        List<Seat> seatList = List.of(
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new Seat(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        seatRepository.saveAll(seatList);
        return seatList;
    }


    void saveSeatWithTickets(Ticket ticket){

        Seat roomSeats = ticket.getRoomSeats();
        List<SeatTicket> seatTickets = roomSeats.getSeatTicket();

        for (String seat : ticket.getSeat()) {

            seatTickets.stream().filter(n -> n.getSeatKey().equals(seat))
                    .findFirst()
                    .ifPresent(st -> st.setTicketValue(ticket));
        }
        roomSeats.setSeatTicket(seatTickets);
        seatRepository.save(roomSeats);
    }

    void updateAndRemoveTicketFromSeat(Ticket ticket, String methodHttp){

        Seat roomSeats = ticket.getRoomSeats();
        List<SeatTicket> seatTickets = roomSeats.getSeatTicket();

        for (SeatTicket seatTicket : seatTickets) {

            if(seatTicket.getTicketValue() != null)
                if(seatTicket.getTicketValue().equals(ticket))
                    seatTicket.setTicketValue(null);
        }
        if (methodHttp.equals("update"))
            saveSeatWithTickets(ticket);

        if (methodHttp.equals("delete")) {
            roomSeats.setSeatTicket(seatTickets);
            seatRepository.save(roomSeats);
        }
    }

    void deleteAllTicketsFromSeat(){
        List<Seat> allSeat = seatRepository.findAll();

        allSeat.forEach(stTicket ->
                stTicket.getSeatTicket().forEach(value ->
                        value.setTicketValue(null)
                )
        );
        seatRepository.saveAll(allSeat);
    }
}
