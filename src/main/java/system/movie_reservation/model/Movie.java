package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Enums.Category;
import system.movie_reservation.model.dto.MovieRequest;

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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Category> categories;

    public Movie(MovieRequest movieRequest) {
        this.name = movieRequest.name();
        this.releaseDate = movieRequest.releaseDate();
        this.description = movieRequest.description();
        this.categories = movieRequest.categories().stream().map(Category.CategoryLoad::toCategory).toList();
        this.posterUrl = movieRequest.posterUrl();
        this.duration = movieRequest.duration();
    }
}
