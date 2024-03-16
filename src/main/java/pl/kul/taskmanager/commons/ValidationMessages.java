package pl.kul.taskmanager.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessages {
    public final static String NOT_NULL = "Field cannot be null";
    public final static String WRONG_SIZE = "Wrong size";
}
