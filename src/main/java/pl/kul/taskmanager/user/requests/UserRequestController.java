package pl.kul.taskmanager.user.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static pl.kul.taskmanager.commons.RestConstants.USER_REQUEST;

@Slf4j
@RestController
@RequestMapping(USER_REQUEST)
@RequiredArgsConstructor
public class UserRequestController {
    private final UserRequestService userRequestService;

    @GetMapping
    public ResponseEntity<List<UserRequestDTO>> getUserRequest() {
        log.debug("Getting user request");
        return ResponseEntity.ok(userRequestService.getUserRequests());
    }

    @PostMapping("/manage/{requestId}")
    public ResponseEntity<Void> manageUserRequest(@PathVariable(name = "requestId") Long requestId,
                                                  @RequestParam(name = "accepted") Boolean accepted) {
        log.debug("Inviting user to board");
        userRequestService.manageUserRequest(requestId, accepted);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeUserRequest(@PathVariable(name = "id") Long id) {
        log.debug("Removing user request");
        userRequestService.deleteUserRequest(id);
        return ResponseEntity.ok().build();
    }
}
