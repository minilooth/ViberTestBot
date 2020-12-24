package by.autocapital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import by.autocapital.services.other.InitializationService;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories
public class AutocapitalApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(AutocapitalApplication.class, args);
		InitializationService initializationService = applicationContext.getBean(InitializationService.class);
		initializationService.initalize();
	}

}
