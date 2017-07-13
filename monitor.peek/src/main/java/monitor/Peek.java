package monitor;

import monitor.observer.ServiceObserver;
import monitor.observer.alpha.AlphaServiceObserver;
import monitor.persistence.StatisticsRepository;

public class Peek {

	public static void main(String[] args) {
		new StatisticsRepository()
				.load()
				.ifPresent(System.out::println);
		AlphaServiceObserver
				.createIfAlphaService("alpha-1")
				.map(ServiceObserver::gatherDataFromService)
				.ifPresent(System.out::println);
	}

}
