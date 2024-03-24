package pl.kul.taskmanager.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessages {
    public final static String EMAIL_IS_REQUIRED = "Email is required";
    public final static String EMAIL_IS_NOT_VALID = "Email is not valid";
    public final static String EMAIL_SIZE = "Email must contain from 4 to 100 characters";
    public final static String PASSWORD_IS_REQUIRED = "Password is required";
    public final static String PASSWORD_SIZE = "Password must contain from 6 to 30 characters";
    public final static String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
    public final static String PASSWORD_IS_NOT_VALID = "Password must contain at least one digit, one lowercase and one uppercase letter";
    public final static String FIRST_NAME_IS_REQUIRED = "First name is required";
    public final static String FIRST_NAME_SIZE = "First name must contain from 2 to 50 characters";
    public final static String FIRST_NAME_REGEXP = "^[a-zA-Z]*$";
    public final static String FIRST_NAME_IS_NOT_VALID = "First name must contain only letters";
    public final static String LAST_NAME_SIZE = "Last name must contain up to 50 characters";
    public final static String LAST_NAME_REGEXP = "^[a-zA-Z]*$";
    public final static String LAST_NAME_IS_NOT_VALID = "Last name must contain only letters";
    public final static String PHONE_NUMBER_SIZE = "Phone number must contain from 9 to 13 characters";
    public final static String PHONE_NUMBER_REGEXP = "^[0-9]*$";
    public final static String PHONE_NUMBER_IS_NOT_VALID = "Phone number must contain only digits";
    public static final String NAME_IS_REQUIRED = "Name is required";
    public static final String NAME_LENGTH = "Name must contain up to 50 characters";
    public static final String DESCRIPTION_LENGTH = "Description must contain up to 200 characters";
    public static final String COLOR_IS_NOT_VALID = "Color must be in hex format, for example: #FFFFFF or #FFF";
    public static final String REQUEST_MESSAGE_TOO_LONG = "Request message is too long";
    public static final String REQUEST_TYPE_NOT_NULL = "Request type is required";
    public static final String RECEIVER_NOT_NULL = "Receiver email is required";
    public static final String SENDER_NULL = "Sender email must be null";
    public static final String REQUEST_STATUS_NULL = "Request status must be null";
    public static final String BOARD_ID_NOT_NULL = "Board id is required";
}