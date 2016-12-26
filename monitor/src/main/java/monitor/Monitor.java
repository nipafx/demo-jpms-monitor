package monitor;

import monitor.observer.DiagnosticDataPoint;
import monitor.observer.ServiceObserver;
import monitor.observer.alpha.AlphaServiceObserver;
import monitor.observer.beta.BetaServiceObserver;
import monitor.persistence.StatisticsRepository;
import monitor.statistics.Statistician;
import monitor.statistics.Statistics;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class Monitor {

	private final ScheduledExecutorService scheduler;
	private final List<ServiceObserver> serviceObservers;
	private final Statistician statistician;
	private final StatisticsRepository repository;

	private Statistics currentStatistics;

	Monitor(
			ScheduledExecutorService scheduler,
			List<ServiceObserver> serviceObservers,
			Statistician statistician,
			StatisticsRepository repository,
			Statistics initialStatistics) {
		this.scheduler = requireNonNull(scheduler);
		this.serviceObservers = requireNonNull(serviceObservers);
		this.statistician = requireNonNull(statistician);
		this.repository = requireNonNull(repository);
		this.currentStatistics = requireNonNull(initialStatistics);
	}

	public void startGatheringStatisticsEvery(long period, TimeUnit unit) {
		scheduler.scheduleAtFixedRate(this::updateStatistics, period, period, unit);
	}

	public void stopGatheringStatistics() {
		scheduler.shutdown();
	}

	private void updateStatistics() {
		List<DiagnosticDataPoint> newDataPoints = serviceObservers.stream()
				.map(ServiceObserver::gatherDataFromService)
				.collect(toList());
		Statistics newStatistics = statistician.compute(currentStatistics, newDataPoints);
		currentStatistics = newStatistics;
		repository.store(newStatistics);
	}

	// CREATE AND RUN

	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		List<ServiceObserver> observers = Stream.of("alpha-1", "alpha-2", "alpha-3", "beta-1")
				.map(Monitor::createObserver)
				.collect(toList());
		Statistician statistician = new Statistician();
		StatisticsRepository repository = new StatisticsRepository();
		Statistics initialStatistics = repository.load().orElseGet(statistician::emptyStatistics);

		Monitor monitor = new Monitor(scheduler, observers, statistician, repository, initialStatistics);
		monitor.startGatheringStatisticsEvery(1, TimeUnit.SECONDS);

		scheduler.schedule(monitor::stopGatheringStatistics, 10, TimeUnit.SECONDS);
	}

	private static ServiceObserver createObserver(String serviceName) {
		return AlphaServiceObserver.createIfAlphaService(serviceName)
				.or(() -> BetaServiceObserver.createIfBetaService(serviceName))
				.orElseThrow(IllegalArgumentException::new);
	}

}
