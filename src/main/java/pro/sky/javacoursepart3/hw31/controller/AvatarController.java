package pro.sky.javacoursepart3.hw31.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.javacoursepart3.hw31.dto.AvatarDTO;
import pro.sky.javacoursepart3.hw31.mapper.AvatarMapper;
import pro.sky.javacoursepart3.hw31.model.Avatar;
import pro.sky.javacoursepart3.hw31.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    private final AvatarService avatarService;
    private final AvatarMapper avatarMapper;

    public AvatarController(AvatarService avatarService, AvatarMapper avatarMapper) {
        this.avatarService = avatarService;
        this.avatarMapper = avatarMapper;
    }

    @PostMapping(value = "{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable("studentId") Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big.");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{studentId}/avatarSmall")
    public ResponseEntity<byte[]> downloadAvatarFromDb(@PathVariable("studentId") Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{studentId}/avatar")
    public void downloadAvatar(@PathVariable("studentId") Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {

            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AvatarDTO>> getAvatars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        page = page == null ? 1 : page;
        size = size == null || size > 5 ? 5 : size;
        List<AvatarDTO> DtoAvatars = avatarService.getAvatars(page, size).stream()
                .map(avatarMapper::mapToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(DtoAvatars);
    }
}
