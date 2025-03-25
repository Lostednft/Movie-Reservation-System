package system.movie_reservation.service;

import org.springframework.stereotype.Service;
import system.movie_reservation.model.Seat.Seat;
import system.movie_reservation.model.Seat.SeatTicket;
import system.movie_reservation.model.Ticket.Ticket;
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
