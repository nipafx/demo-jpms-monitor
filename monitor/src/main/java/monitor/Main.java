package monitor;

import monitor.observer.ServiceObserver;
import monitor.observer.ServiceObserverFactory;
import monitor.persistence.StatisticsRepository;
import monitor.rest.MonitorServer;
import monitor.statistics.Statistician;
import monitor.statistics.Statistics;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

	public static void main(String[] args) {
		Monitor monitor = createMonitor();

		MonitorServer server = MonitorServer
				.create(monitor::currentStatistics)
				.start();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(monitor::updateStatistics, 1, 1, TimeUnit.SECONDS);
		scheduler.schedule(() -> {
					scheduler.shutdown();
					server.shutdown();
				},
				10,
				TimeUnit.SECONDS);
	}

	private static Monitor createMonitor() {
		List<ServiceObserverFactory> observerFactories = ServiceLoader
				.load(ServiceObserverFactory.class).stream()
				.map(Provider::get)
				.collect(toList());
		List<ServiceObserver> observers = Stream.of("0-patient", "alpha-1", "alpha-2", "alpha-3", "beta-1")
				.map(serviceName -> createObserver(observerFactories, serviceName))
				.flatMap(Optional::stream)
				.collect(toList());
		Statistician statistician = new Statistician();
		StatisticsRepository repository = new StatisticsRepository();
		Statistics initialStatistics = repository.load().orElseGet(statistician::emptyStatistics);

		return new Monitor(observers, statistician, repository, initialStatistics);
	}

	private static Optional<ServiceObserver> createObserver(
			List<ServiceObserverFactory> observerFactories, String serviceName) {

		return observerFactories.stream()
				.flatMap(factory -> factory.createIfMatchingService(serviceName).stream())
				.findFirst();
	}

}
