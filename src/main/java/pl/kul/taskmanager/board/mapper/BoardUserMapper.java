package pl.kul.taskmanager.board.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kul.taskmanager.board.dto.BoardUserDTO;
import pl.kul.taskmanager.board.entity.BoardUserEntity;
import pl.kul.taskmanager.commons.AbstractMapper;
import pl.kul.taskmanager.user.utils.UserUtils;

@Component
@RequiredArgsConstructor
public class BoardUserMapper implements AbstractMapper<BoardUserDTO, BoardUserEntity> {

    private final BoardMapper boardMapper;
    private final UserUtils userUtils;

    @Override
    public BoardUserEntity mapToEntity(BoardUserDTO dto) {
        return BoardUserEntity.builder()
                .board(boardMapper.mapToEntity(dto.getBoard()))
                .user(userUtils.findUserDetailsByUserId(dto.getUserId()))
                .isOwner(dto.getIsOwner())
                .isDefault(dto.getIsDefault())
                .build();
    }

    @Override
    public BoardUserDTO mapToDTO(BoardUserEntity entity) {
        return BoardUserDTO.builder()
                .board(boardMapper.mapToDTO(entity.getBoard()))
                .userId(entity.getUser().getId())
                .isOwner(entity.getIsOwner())
                .isDefault(entity.getIsDefault())
                .build();
    }
}
