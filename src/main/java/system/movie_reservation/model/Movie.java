package system.movie_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.movie_reservation.model.Enums.Category;
import system.movie_reservation.model.dto.MovieRequest;

import java.util.List;

@Entity(name = "movie_tb")
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long releaseDate;

    @Column(nullable = false)
    private String description;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Category> categories;

    @Column(nullable = false)
    private String posterUrl;

    @Column(nullable = false)
    private String duration;

    public Movie(MovieRequest movieRequest) {
        this.name = movieRequest.name();
        this.releaseDate = movieRequest.releaseDate();
        this.description = movieRequest.description();
        this.categories = movieRequest.categories().stream().map(Category.CategoryLoad::toCategory).toList();
        this.posterUrl = movieRequest.posterUrl();
        this.duration = movieRequest.duration();
    }
}
