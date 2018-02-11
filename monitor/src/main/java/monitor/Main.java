package monitor;

import monitor.observer.ServiceObserver;
import monitor.observer.alpha.AlphaServiceObserver;
import monitor.observer.beta.BetaServiceObserver;
import monitor.persistence.StatisticsRepository;
import monitor.rest.MonitorServer;
import monitor.statistics.Statistician;
import monitor.statistics.Statistics;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

	public static void main(String[] args) throws ReflectiveOperationException {
		accessResources();
		accessResourceBundle();

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

	private static void accessResources() throws ReflectiveOperationException {
		URL closed = Class
				.forName("monitor.resources.closed.Anchor")
				.getResource("file.txt");
		URL exported = Class
				.forName("monitor.resources.exported.Anchor")
				.getResource("file.txt");
		URL opened = Class
				.forName("monitor.resources.opened.Anchor")
				.getResource("file.txt");
		URL root = Class
				.forName("monitor.resources.closed.Anchor")
				.getResource("/file.txt");

		URL meta = Class
				.forName("monitor.resources.closed.Anchor")
				.getResource("/META-INF/file.txt");

		URL bytecode = Class
				.forName("monitor.resources.opened.Anchor")
				.getResource("Anchor.class");

		String urls = String.format(
				"closed: %s%nexported: %s%nopened: %s%nroot: %s%nmeta: %s%nbytecode: %s%n",
				closed, exported, opened, root, meta, bytecode);
		System.out.println(urls);
	}

	private static void accessResourceBundle() {
		Locale english = new Locale("en");

		printFromResourceBundle("monitor.resources.closed.messages", english, "text");
		printFromResourceBundle("monitor.resources.exported.messages", english, "text");
		printFromResourceBundle("monitor.resources.opened.messages", english, "text");
		System.out.println();
	}

	private static void printFromResourceBundle(String bundle, Locale english, String property) {
		try {
			ResourceBundle messages = ResourceBundle.getBundle(bundle, english);
			System.out.println(messages.getString(property));
		} catch (MissingResourceException ex) {
			System.out.println(ex.getMessage());
		}
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
