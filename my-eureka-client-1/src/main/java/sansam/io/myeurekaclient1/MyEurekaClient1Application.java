package sansam.io.myeurekaclient1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class MyEurekaClient1Application {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(MyEurekaClient1Application.class, args);
    }

    @RequestMapping("/")
    public String home(@RequestParam("name") String name){
        return "hello " + name +" , i'm from port : " + port;
    }
}
