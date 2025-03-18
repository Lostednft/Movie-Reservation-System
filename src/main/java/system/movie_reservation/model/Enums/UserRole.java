package system.movie_reservation.model.Enums;


public enum UserRole {
    USER(1, "user"),
    ADMIN(2, "admin");

    private Integer id;
    private String role;

    UserRole(Integer id, String role) {
        this.id = id;
        this.role = role;
    }
}
