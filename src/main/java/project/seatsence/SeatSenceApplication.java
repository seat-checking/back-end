package project.seatsence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SeatSenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatSenceApplication.class, args);

        // 남은 메모리 출력
        long freeMemory = Runtime.getRuntime().freeMemory()/1024/1024;
        System.out.println("Free Memory : " + freeMemory + " MB");

        // 전체 메모리
        long totalMemory = Runtime.getRuntime().totalMemory()/1024/1024;

        //메모리 사용량 출력
        System.out.println("Used Memory : " + (totalMemory - freeMemory) + "MB");
    }
}
