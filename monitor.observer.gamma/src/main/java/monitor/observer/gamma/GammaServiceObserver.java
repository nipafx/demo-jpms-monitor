package monitor.observer.gamma;

import monitor.observer.DiagnosticDataPoint;
import monitor.observer.ServiceObserver;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

public class GammaServiceObserver implements ServiceObserver {

	private static final Random RANDOM = new Random();

	private final String serviceName;

	GammaServiceObserver(String serviceName) {
		this.serviceName = serviceName;
	}

	public static Optional<ServiceObserver> createIfGammaService(String service) {
		return Optional.of(service)
				// this check should do something more sensible
				.filter(s -> s.contains("gamma"))
				.map(GammaServiceObserver::new);
	}

	@Override
	public DiagnosticDataPoint gatherDataFromService() {
		// this check should actually contact the serviceName
		boolean alive = RANDOM.nextFloat() > 0.25;
		return DiagnosticDataPoint.of(serviceName, ZonedDateTime.now(), alive);
	}

}
