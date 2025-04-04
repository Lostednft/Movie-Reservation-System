package system.movie_reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import system.movie_reservation.model.movie.EnumLoader.Category;
import system.movie_reservation.model.movie.EnumLoader.MovieTime;
import system.movie_reservation.model.movie.Movie;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.ticket.Ticket;
import system.movie_reservation.model.ticket.TicketRequest;
import system.movie_reservation.model.ticket.TicketRequestUpdate;
import system.movie_reservation.model.user.Enum.UserRole;
import system.movie_reservation.model.user.User;
import system.movie_reservation.repository.MovieRepository;
import system.movie_reservation.repository.TicketRepository;
import system.movie_reservation.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TicketControllerLayerTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    private Ticket ticket;
    private User user;
    private Movie movie;

    @BeforeEach
    void setup(){
        movieRepository.deleteAll();
        userRepository.deleteAll();
        ticketRepository.deleteAll();

        user = User.builder()
                .dateOfBirth(LocalDate.of(1998, 7, 15))
                .email("adriana@gmail.com")
                .role(UserRole.ADMIN)
                .password("1234")
                .username("adriana")
                .build();

        MovieTheater movieTheater = new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime());

        movie = Movie.builder()
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(List.of(Category.CategoryLoad.COMEDY.toCategory()))
                .rooms(List.of(movieTheater))
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();
        movieTheater.setMovie(movie);

        ticket = Ticket.builder()
                .seat(List.of("C2", "C3", "C4"))
                .movieTime(MovieTime.MovieTimeLoad.TURN_01.toMovieTime())
                .movie(movie)
                .user(user)
                .movieTheater(movieTheater)
                .build();

    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenTicketRequest_whenSaveTicket_thenReturnTicketObject() throws Exception{

        //Given
        Movie movieSaved = movieRepository.save(movie);
        User userSaved = userRepository.save(user);

        TicketRequest ticketRequest = new TicketRequest(
                userSaved.getId(),
                movieSaved.getId(),
                MovieTime.MovieTimeLoad.TURN_01,
                ticket.getSeat()
        );

        //When
        ResultActions response = mockMvc.perform(post("/ticket")
                .content(objectMapper.writeValueAsString(ticketRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(ticketRequest.userId()))
                .andExpect(jsonPath("$.movie_id").value(ticketRequest.movieId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void given_whenGetAllTickets_thenReturnTicketList() throws Exception {
        //Given
        movieRepository.save(movie);
        userRepository.save(user);
        Ticket ticketSaved = ticketRepository.save(ticket);

        //When
        ResultActions response = mockMvc.perform(get("/ticket"));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(ticketSaved.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void given_whenGetTicketById_thenReturnTicketObject() throws Exception{

        //Given
        movieRepository.save(movie);
        userRepository.save(user);
        Ticket ticketSaved = ticketRepository.save(ticket);

        //When
        ResultActions response = mockMvc.perform(get("/ticket/{id}", ticketSaved.getId()));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketSaved.getId()))
                .andExpect(jsonPath("$.user_id").value(ticketSaved.getUser().getId()));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void givenTicketRequestUpdate_whenUpdateUser_thenReturnTicketResponse() throws Exception{

        //Given
        movieRepository.save(movie);
        userRepository.save(user);
        Ticket ticketSaved = ticketRepository.save(ticket);

        List<String> seatUpdated = List.of("H1", "H2", "H3", "H4");
        TicketRequestUpdate ticketReqUpdate = new TicketRequestUpdate(
                ticketSaved.getId(),
                ticketSaved.getMovie().getId(),
                MovieTime.MovieTimeLoad.TURN_01,
                seatUpdated);
        //When
        ResultActions response = mockMvc.perform(put("/ticket")
                .content(objectMapper.writeValueAsString(ticketReqUpdate))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketSaved.getId()))
                .andExpect(jsonPath("$.seats.size()").value(seatUpdated.size()));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void givenId_whenDeleteById_thenReturnMessageString() throws Exception {

        //Given
        movieRepository.save(movie);
        userRepository.save(user);
        Ticket ticketSaved = ticketRepository.save(ticket);

        //When
        ResultActions response = mockMvc.perform(delete("/ticket/{id}", ticketSaved.getId()));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket was deleted successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void given_whenDeleteAllTickets_thenReturnTicketList() throws Exception {

        //Given
        movieRepository.save(movie);
        userRepository.save(user);
        ticketRepository.save(ticket);

        //When
        ResultActions response = mockMvc.perform(delete("/ticket"));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("All Tickets was deleted"));
    }
}
