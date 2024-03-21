package pl.kul.taskmanager.user.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kul.taskmanager.commons.AbstractDTO;
import pl.kul.taskmanager.commons.ValidationMessages;
import pl.kul.taskmanager.user.requests.enums.UserRequestStatus;
import pl.kul.taskmanager.user.requests.enums.UserRequestType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO extends AbstractDTO {
    @Size(max = 500, message = ValidationMessages.REQUEST_MESSAGE_TOO_LONG)
    private String requestMessage;

    @Builder.Default
    @Null(message = ValidationMessages.REQUEST_STATUS_NULL)
    private UserRequestStatus requestStatus = UserRequestStatus.PENDING;

    @NotNull(message = ValidationMessages.REQUEST_TYPE_NOT_NULL)
    private UserRequestType requestType;

    @Null(message = ValidationMessages.SENDER_NULL)
    @Email(message = ValidationMessages.EMAIL_IS_NOT_VALID)
    @Size(min = 4, max = 100, message = ValidationMessages.EMAIL_SIZE)
    private String senderEmail;

    @NotNull(message = ValidationMessages.RECEIVER_NOT_NULL)
    @Email(message = ValidationMessages.EMAIL_IS_NOT_VALID)
    @Size(min = 4, max = 100, message = ValidationMessages.EMAIL_SIZE)
    private String receiverEmail;

    @Null(message = ValidationMessages.BOARD_ID_NULL)
    private Long boardId;
}
