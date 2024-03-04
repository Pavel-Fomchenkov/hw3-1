package pro.sky.javacoursepart3.hw31.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.javacoursepart3.hw31.model.Avatar;
import pro.sky.javacoursepart3.hw31.model.Student;
import pro.sky.javacoursepart3.hw31.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${avatars.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method AvatarService.uploadAvatar({}, avatarFile)", studentId);
        logger.debug("Method uploadAvatar(studentId, avatarFile) executes findByStudentId(studentId)");
        Student student = studentService.find(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        if (avatar == null) {
            avatar = new Avatar();
        }
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateAvatarSmall(filePath));
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method AvatarService.getExtensions({})", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method AvatarService.findAvatar({})", studentId);
        logger.debug("Method findAvatar(studentId) executes findByStudentId(studentId)");
        return avatarRepository.findByStudentId(studentId);
    }

    private byte[] generateAvatarSmall(Path filePath) throws IOException {
        logger.info("Was invoked method AvatarService.generateAvatarSmall({})", filePath.toString());
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage avatarSmall = new BufferedImage(100, height, image.getType());
            Graphics2D g2 = avatarSmall.createGraphics();
            g2.drawImage(image, 0, 0, 100, height, null);
            g2.dispose();
            ImageIO.write(avatarSmall, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public List<Avatar> getAvatars(Integer page, Integer size) {
        logger.info("Was invoked method AvatarService.getAvatars({}, {})", page, size);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
