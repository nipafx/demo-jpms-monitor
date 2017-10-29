package monitor.observer.zero;

import monitor.observer.DiagnosticDataPoint;
import monitor.observer.ServiceObserver;

import java.time.ZonedDateTime;
import java.util.Optional;

public class ZeroServiceObserver implements ServiceObserver {

	private final String serviceName;

	ZeroServiceObserver(String serviceName) {
		this.serviceName = serviceName;
	}

	public static Optional<ServiceObserver> createIfZeroService(String service) {
		return Optional.of(service)
				// this check should do something more sensible
				.filter(s -> s.startsWith("0"))
				.map(ZeroServiceObserver::new);
	}

	@Override
	public DiagnosticDataPoint gatherDataFromService() {
		// this should actually contact the serviceName
		return DiagnosticDataPoint.of(serviceName, ZonedDateTime.now(), false);
	}

}
