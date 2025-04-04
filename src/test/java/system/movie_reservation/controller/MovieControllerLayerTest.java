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
import system.movie_reservation.model.movie.MovieRequest;
import system.movie_reservation.model.movie.MovieRequestUpdate;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.repository.MovieRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MovieControllerLayerTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private Movie movie;
    private List<Category.CategoryLoad> categoryLoadList;

    @BeforeEach
    void setup(){

        movieRepository.deleteAll();

        categoryLoadList = List.of(Category.CategoryLoad.COMEDY, Category.CategoryLoad.ACTION);

        movie = Movie.builder()
                .name("SuperBad")
                .releaseDate(2007L)
                .description("High school seniors Seth and Evan have high hopes for a graduation party.")
                .categories(categoryLoadList.stream().map(Category.CategoryLoad::toCategory).toList())
                .duration("1h 53m")
                .posterUrl("IMAGE POSTER")
                .build();

        List<MovieTheater> movieTheaterList = List.of(
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_01.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_02.toMovieTime()),
                new MovieTheater(movie, MovieTime.MovieTimeLoad.TURN_03.toMovieTime()));

        movie.setRooms(movieTheaterList);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenMovieRequest_whenCreateMovie_thenMovieResponseObject() throws Exception{

        //Given
        MovieRequest movieRequest = new MovieRequest(
                movie.getName(),
                movie.getReleaseDate(),
                movie.getDescription(),
                categoryLoadList,
                movie.getPosterUrl(),
                movie.getDuration()
        );

        //When
        ResultActions response = mockMvc.perform(post("/movies/create")
                .content(objectMapper.writeValueAsString(movieRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(movie.getName()))
                .andExpect(jsonPath("$.duration").value(movie.getDuration()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenMovieRequestUpdate_whenUpdateMovie_thenMovieResponseObject() throws Exception{

        //Given
        Movie userSaved = movieRepository.save(movie);

        MovieRequestUpdate movieRequestUpdate = new MovieRequestUpdate(
                userSaved.getId(),
                "SuperBad 2",
                2024L,
                userSaved.getDescription(),
                categoryLoadList,
                userSaved.getPosterUrl(),
                "2h 15m"
        );

        //When
        ResultActions response = mockMvc.perform(put("/movies/update")
                .content(objectMapper.writeValueAsString(movieRequestUpdate))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(movieRequestUpdate.name()))
                .andExpect(jsonPath("$.releaseDate").value(movieRequestUpdate.releaseDate()))
                .andExpect(jsonPath("$.duration").value(movieRequestUpdate.duration()));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenId_whenGetMovieById_thenReturnMovie() throws Exception {

        //Given
        Movie movieSaved = movieRepository.save(movie);

        //When
        ResultActions response = mockMvc.perform(get("/movies/{id}", movieSaved.getId()));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movieSaved.getId()))
                .andExpect(jsonPath("$.name").value(movieSaved.getName()));
    }

    @Test
    public void given_whenGetAllMovies_thenReturnMoviesList() throws Exception{

        //Given
        Movie movieSaved = movieRepository.save(movie);

        //When
        ResultActions response = mockMvc.perform(get("/movies"));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(movieSaved.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenId_whenDeleteMovieById_thenReturnMessageString() throws Exception{

        //Given
        Movie movieSaved = movieRepository.save(movie);

        //When
        ResultActions response = mockMvc.perform(delete("/movies/{id}", movieSaved.getId()));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Movie deleted successfully."));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void given_whenDeleteAllMovies_thenReturnMessageString() throws Exception{

        //Given
        movieRepository.deleteAll();

        //When
        ResultActions response = mockMvc.perform(delete("/movies"));

        //Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("All movies was removed successfully"));
    }
}
