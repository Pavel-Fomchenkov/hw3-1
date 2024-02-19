package pro.sky.javacoursepart3.hw31.mapper;

import org.springframework.stereotype.Component;
import pro.sky.javacoursepart3.hw31.dto.AvatarDTO;
import pro.sky.javacoursepart3.hw31.model.Avatar;

@Component
public class AvatarMapper {

    public AvatarDTO mapToDto(Avatar avatar) {
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setFileSize(avatar.getFileSize());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setStudent_id(avatar.getStudent().getId());
        return avatarDTO;
    }
}
