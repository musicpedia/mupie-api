package com.musicpedia.musicpediaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MusicpediaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicpediaApiApplication.class, args);
	}

}
