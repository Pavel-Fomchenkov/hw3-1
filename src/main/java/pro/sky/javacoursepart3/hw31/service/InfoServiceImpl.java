package pro.sky.javacoursepart3.hw31.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

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


    @Override
    public Long getSumOfSequence() {
        long sum;
//        long startTime, endTime;
//        Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
//
//        startTime = System.currentTimeMillis();
//        sum = Stream.iterate(1, a -> a + 1)
//                .limit(1_000_000)
//                .reduce(0, (a, b) -> a + b);
//        endTime = System.currentTimeMillis();
//        logger.info(endTime - startTime + "");
//
//        startTime = System.currentTimeMillis();
//        sum = Stream.iterate(1, a -> a + 1)
//                .limit(1_000_000)
//                .parallel()
//                .reduce(0, (a, b) -> a + b);
//        endTime = System.currentTimeMillis();
//        logger.info(endTime - startTime + "");

//        startTime = System.currentTimeMillis();
        sum = 0;
        for (int i = 1; i <= 1_000_000; i++) {
            sum += i;
        }
//        endTime = System.currentTimeMillis();
//        logger.info(endTime - startTime + "");
        return sum;
    }
}
