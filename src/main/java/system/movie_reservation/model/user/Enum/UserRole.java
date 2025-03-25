package system.movie_reservation.model.user.Enum;


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
