package system.movie_reservation.model.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.movie.EnumLoader.Category;
import system.movie_reservation.model.seat.MovieTheater;
import system.movie_reservation.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Long releaseDate;
    private String description;
    private String posterUrl;
    private String duration;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieTheater> rooms;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "movie_categories_tb",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Movie(MovieRequest movieRequest) {
        this.name = movieRequest.name();
        this.releaseDate = movieRequest.releaseDate();
        this.description = movieRequest.description();
        this.categories = movieRequest.categories()
                .stream()
                .map(Category.CategoryLoad::toCategory)
                .toList();
        this.posterUrl = movieRequest.posterUrl();
        this.duration = movieRequest.duration();
    }

    public Movie(MovieRequestUpdate movieReqUpdate) {
        this.id = movieReqUpdate.id();
        this.name = movieReqUpdate.name();
        this.releaseDate = movieReqUpdate.releaseDate();
        this.description = movieReqUpdate.description();
        this.categories = movieReqUpdate.categories()
                .stream()
                .map(Category.CategoryLoad::toCategory)
                .toList();
        this.posterUrl = movieReqUpdate.posterUrl();
        this.duration = movieReqUpdate.duration();
    }
}
