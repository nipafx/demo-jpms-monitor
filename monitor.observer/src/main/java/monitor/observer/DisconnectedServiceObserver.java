package monitor.observer;

import java.time.ZonedDateTime;

class DisconnectedServiceObserver implements ServiceObserver {

	@Override
	public DiagnosticDataPoint gatherDataFromService() {
		return DiagnosticDataPoint.of("None", ZonedDateTime.now(), false);
	}
}
