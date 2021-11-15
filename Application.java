package Infinity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import stockexchange.client.StockExchangeClient;
import stockexchange.client.StockExchangeClientImplementation;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private InfinityApplication infinityApplication;
	
	@Bean
	public StockExchangeClient client(){
	    return new StockExchangeClientImplementation();
	}

	
	@Bean
	@Autowired
	public InfinityApplication create(StockExchangeClient client){
		return new InfinityApplication(client);
	}

	@Override
	public void run(String... args) throws Exception {
		this.infinityApplication.execute();
	}

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}






