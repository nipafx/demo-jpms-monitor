package monitor.observer.dis;

import monitor.observer.DiagnosticDataPoint;
import monitor.observer.ServiceObserver;

import java.time.ZonedDateTime;

public class DisconnectedServiceObserver implements ServiceObserver {

	@Override
	public DiagnosticDataPoint gatherDataFromService() {
		return DiagnosticDataPoint.of("None", ZonedDateTime.now(), false);
	}
}
