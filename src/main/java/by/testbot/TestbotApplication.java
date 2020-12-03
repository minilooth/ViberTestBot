package by.testbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import by.testbot.services.InitializationService;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
@EnableTransactionManagement
public class TestbotApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(TestbotApplication.class, args);
		InitializationService initializationService = applicationContext.getBean(InitializationService.class);
		initializationService.initalize();
	}

}
