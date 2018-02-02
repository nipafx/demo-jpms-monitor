package monitor;

import monitor.observer.DiagnosticDataPoint;
import monitor.observer.ServiceObserver;
import monitor.persistence.StatisticsRepository;
import monitor.statistics.Statistician;
import monitor.statistics.Statistics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class Monitor {

	/** Use a concurrent list, so new observers can be added while statistics are updated */
	private final CopyOnWriteArrayList<ServiceObserver> serviceObservers;
	private final Statistician statistician;
	private final StatisticsRepository repository;

	private Statistics currentStatistics;

	public Monitor(
			List<ServiceObserver> serviceObservers,
			Statistician statistician,
			StatisticsRepository repository,
			Statistics initialStatistics) {
		this.serviceObservers = new CopyOnWriteArrayList<>(requireNonNull(serviceObservers));
		this.statistician = requireNonNull(statistician);
		this.repository = requireNonNull(repository);
		this.currentStatistics = requireNonNull(initialStatistics);
	}

	public void addServiceObserver(ServiceObserver observer) {
		serviceObservers.add(observer);
	}

	public void updateStatistics() {
		List<DiagnosticDataPoint> newData = serviceObservers.stream()
				.map(ServiceObserver::gatherDataFromService)
				.collect(toList());
		Statistics newStatistics = statistician.compute(currentStatistics, newData);
		currentStatistics = newStatistics;
		repository.store(newStatistics);
	}

	public Statistics currentStatistics() {
		return currentStatistics;
	}
}
