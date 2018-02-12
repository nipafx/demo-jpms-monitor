package monitor;

import monitor.observer.ServiceObserver;
import monitor.observer.alpha.AlphaServiceObserver;
import monitor.observer.beta.BetaServiceObserver;
import monitor.persistence.StatisticsRepository;
import monitor.rest.MonitorServer;
import monitor.statistics.Statistician;
import monitor.statistics.Statistics;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Version;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

	public static void main(String[] args) {
		printModuleVersions();

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

	private static void printModuleVersions() {
		Main.class
				.getModule()
				.getLayer()
				.modules().stream()
				.filter(module -> module.getName().startsWith("monitor"))
				.forEach(Main::printModuleVersion);
		System.out.println();
	}

	public static void printModuleVersion(Module module) {
		System.out.printf("%s @ %s%n", module.getName(), module.getDescriptor().rawVersion().orElse("unknown"));
		module
				.getDescriptor()
				.requires().stream()
				.map(requires -> String.format("\t-> %s @ %s",
						requires.name(),
						requires.rawCompiledVersion().orElse("unknown")))
				.forEach(System.out::println);
	}

	private static Monitor createMonitor() {
		List<ServiceObserver> observers = Stream.of("alpha-1", "alpha-2", "alpha-3", "beta-1")
				.map(Main::createObserver)
				.flatMap(Optional::stream)
				.collect(toList());
		Statistician statistician = new Statistician();
		StatisticsRepository repository = new StatisticsRepository();
		Statistics initialStatistics = repository.load().orElseGet(statistician::emptyStatistics);

		return new Monitor(observers, statistician, repository, initialStatistics);
	}

	private static Optional<ServiceObserver> createObserver(String serviceName) {
		return AlphaServiceObserver.createIfAlphaService(serviceName)
				.or(() -> BetaServiceObserver.createIfBetaService(serviceName))
				.or(() -> {
					System.out.printf("No observer for %s found.%n", serviceName);
					return Optional.empty();
				});
	}

}
