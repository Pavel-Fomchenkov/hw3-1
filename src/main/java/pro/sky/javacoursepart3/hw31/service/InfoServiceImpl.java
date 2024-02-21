package pro.sky.javacoursepart3.hw31.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private Integer port;
    private final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

    public InfoServiceImpl() {
    }

    @Override
    public Integer getPort() {
        logger.info("Was invoked method InfoService.getPort()");
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
