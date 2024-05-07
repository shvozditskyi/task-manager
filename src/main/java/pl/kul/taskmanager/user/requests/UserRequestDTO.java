package pl.kul.taskmanager.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long id;

    @Size(max = 500, message = ValidationMessages.REQUEST_MESSAGE_TOO_LONG)
    private String requestMessage;

    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserRequestStatus requestStatus = UserRequestStatus.PENDING;

    @NotNull(message = ValidationMessages.REQUEST_TYPE_NOT_NULL)
    private UserRequestType requestType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String senderEmail;

    @NotNull(message = ValidationMessages.RECEIVER_NOT_NULL)
    @Email(message = ValidationMessages.EMAIL_IS_NOT_VALID)
    @Size(min = 4, max = 100, message = ValidationMessages.EMAIL_SIZE)
    private String receiverEmail;

    @NotNull(message = ValidationMessages.BOARD_ID_NOT_NULL)
    private Long boardId;
}
