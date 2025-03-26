package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.seat.Seat;
import system.movie_reservation.model.seat.SeatTicket;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.repository.SeatRepository;

import java.util.List;

@Service
public class SeatTicketService {

    private final SeatRepository seatRepository;

    public SeatTicketService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
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

    void updateAndRemoveTicketFromSeat(Ticket oldTicket,
                                       Ticket newTicket,
                                       String methodHttp){

        Seat roomSeats = oldTicket.getRoomSeats();
        List<SeatTicket> seatTickets = roomSeats.getSeatTicket();

        seatTickets.stream()
                .filter(st -> st.getTicketValue() != null &&
                        st.getTicketValue().equals(oldTicket))
                .forEach(st -> st.setTicketValue(null));

        roomSeats.setSeatTicket(seatTickets);
        seatRepository.save(roomSeats);

        if (methodHttp.equals("update"))
            saveSeatWithTickets(newTicket);
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