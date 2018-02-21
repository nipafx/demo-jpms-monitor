package monitor;

import monitor.observer.ServiceObserver;
import monitor.observer.ServiceObserverFactory;
import monitor.persistence.StatisticsRepository;
import monitor.rest.MonitorServer;
import monitor.statistics.Statistician;
import monitor.statistics.Statistics;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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

	private static Monitor monitor;

	public static void main(String[] args) throws Exception {
		monitor = createMonitor();

		MonitorServer server = MonitorServer
				.create(monitor::currentStatistics)
				.start();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(monitor::updateStatistics, 1, 1, TimeUnit.SECONDS);
		scheduler.schedule(Main::registerNewGammaService, 4, TimeUnit.SECONDS);
		scheduler.schedule(() -> {
					scheduler.shutdown();
					server.shutdown();
				},
				10, TimeUnit.SECONDS);
	}

	private static Monitor createMonitor() {
		List<ServiceObserver> observers = Stream
				.of("0-patient", "alpha-1", "alpha-2", "alpha-3", "beta-1")
				.map(Main::createObserver)
				.flatMap(Optional::stream)
				.collect(toList());
		Statistician statistician = new Statistician();
		StatisticsRepository repository = new StatisticsRepository();
		Statistics initialStatistics = repository.load().orElseGet(statistician::emptyStatistics);

		return new Monitor(observers, statistician, repository, initialStatistics);
	}

	private static Optional<ServiceObserver> createObserver(String serviceName) {
		return createObserver(getThisLayer(), serviceName);
	}

	private static Optional<ServiceObserver> createObserver(
			ModuleLayer layer, String serviceName) {
		Stream<ServiceObserverFactory> observerFactories = ServiceLoader
				.load(layer, ServiceObserverFactory.class).stream()
				.map(Provider::get);
		return observerFactories
				.flatMap(factory -> factory.createIfMatchingService(serviceName).stream())
				.findFirst();
	}

	private static void registerNewGammaService() {
		Path dir = Paths.get(System.getProperty("user.dir")).resolve("mods-gamma");
		registerNewService("gamma-x", dir);
	}

	private static void registerNewService(String serviceName, Path... modulePaths) {
		ModuleLayer layer = createLayer(modulePaths);
		Stream<ServiceObserverFactory> observerFactories = ServiceLoader
				.load(layer, ServiceObserverFactory.class).stream()
				.map(Provider::get);
		Optional<ServiceObserver> observer = observerFactories
				.flatMap(factory -> factory.createIfMatchingService(serviceName).stream())
				.findFirst();
		observer.ifPresentOrElse(
						monitor::addServiceObserver,
						() -> System.out.println(
								"WARNING: Failed to create service for " + serviceName)
				);
	}

	private static ModuleLayer createLayer(Path[] modulePaths) {
		Configuration configuration = createConfiguration(modulePaths);
		ClassLoader thisLoader = getThisLoader();
		return getThisLayer()
				.defineModulesWithOneLoader(configuration, thisLoader);
	}

	private static Configuration createConfiguration(Path[] modulePaths) {
		return getThisLayer()
				.configuration()
				.resolveAndBind(
						ModuleFinder.of(),
						ModuleFinder.of(modulePaths),
						Collections.emptyList());
	}

	private static ModuleLayer getThisLayer() {
		return Main.class.getModule().getLayer();
	}

	private static ClassLoader getThisLoader() {
		return Main.class.getClassLoader();
	}

}
