package ru.otus.java.pro.result.project.hotels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelsApplication.class, args);
    }

    //TODO добавить поддержку пересчета и поиска свободного жилья по датам
}
