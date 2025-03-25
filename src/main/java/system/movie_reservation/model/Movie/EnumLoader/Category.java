package system.movie_reservation.model.Movie.EnumLoader;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category{

    @Id
    private Integer id;
    private String category;

    public enum CategoryLoad {
        DRAMA(1,"drama"),
        ACTION(2, "action"),
        ADVENTURE(3,"adventure"),
        COMEDY(4,"comedy"),
        FANTASY(5,"fantasy"),
        HORROR(6, "horror"),
        ROMANCE(7, "romance"),
        THRILLER(8, "thriller"),
        MISTERY(9,"mistery"),
        CRIME(10, "crime"),
        DOCUMENTARY(11, "documentary"),
        SCIENCE_FICTION(12,"science fiction"),
        ANIMATION(13,"animation"),
        MUSICAL(14, "musical");

        private Integer id;
        private String categoryResponse;

        CategoryLoad(Integer id, String categoryResponse) {
            this.id = id;
            this.categoryResponse = categoryResponse;
        }

        public Category toCategory(){
            return new Category(id, categoryResponse);
        }
    }
}