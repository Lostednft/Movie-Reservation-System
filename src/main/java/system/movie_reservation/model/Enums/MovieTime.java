package system.movie_reservation.model.Enums;

import java.time.LocalTime;

public enum MovieTime {
    TURN_01(1,LocalTime.of(14,0) ,LocalTime.of(17,0)),
    TURN_02(2, LocalTime.of(17, 30), LocalTime.of(20, 30)),
    TURN_03(3, LocalTime.of(21, 0), LocalTime.of(0, 0));

    private Integer id;
    private final LocalTime startTime;
    private LocalTime endTime;

    MovieTime(Integer id, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
