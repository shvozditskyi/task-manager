package pl.kul.taskmanager.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestConstants {
    public static final String API = "/api";
    public static final String AUTH = API + "/auth";
    public static final String BOARDS = API + "/boards";
    public static final String USER_REQUEST = API + "/user-request";
    public static final String TASKS = API + "/tasks";
}
