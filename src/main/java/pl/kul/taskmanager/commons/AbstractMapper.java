package pl.kul.taskmanager.commons;

public interface AbstractMapper<DTO extends AbstractDTO, ENTITY extends AbstractEntity> {
    ENTITY mapToEntity(DTO dto);
    DTO mapToDTO(ENTITY dto);
}
