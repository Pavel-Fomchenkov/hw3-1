package pro.sky.javacoursepart3.hw31.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.javacoursepart3.hw31.service.InfoService;

@RestController
@RequestMapping()
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("port")
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(infoService.getPort());
    }

    @GetMapping("sequenceSum")
    public ResponseEntity<Long> getSequenceSum() {
        return ResponseEntity.ok(infoService.getSumOfSequence());
    }
}
