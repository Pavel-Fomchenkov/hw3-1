package pro.sky.javacoursepart3.hw31.service;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.javacoursepart3.hw31.model.Avatar;

import java.io.IOException;

public interface AvatarService {
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);
}
