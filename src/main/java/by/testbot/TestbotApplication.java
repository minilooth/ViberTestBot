package by.testbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import by.testbot.services.other.InitializationService;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories
public class TestbotApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(TestbotApplication.class, args);
		InitializationService initializationService = applicationContext.getBean(InitializationService.class);
		initializationService.initalize();
	}

}
