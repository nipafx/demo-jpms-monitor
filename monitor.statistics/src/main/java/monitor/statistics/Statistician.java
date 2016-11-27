package monitor.statistics;

import monitor.observer.DiagnosticDataPoint;

import java.util.stream.Stream;

public class Statistician {

	public Statistics emptyStatistics() {
		return Statistics.create();
	}

	public Statistics compute(Statistics currentStats, Stream<DiagnosticDataPoint> dataPoints) {
		Statistics finalStats = currentStats;
		for (DiagnosticDataPoint dataPoint : (Iterable<DiagnosticDataPoint>) dataPoints::iterator)
			finalStats = finalStats.merge(dataPoint);
		return finalStats;
	}

}
