package monitor.statistics;

import monitor.observer.DiagnosticDataPoint;
import stats.fancy.FancyStats;

public class Statistician {

	private final boolean isFancyAvailable;

	public Statistician() {
		isFancyAvailable = checkFancyStats();
	}

	private static boolean checkFancyStats() {
		boolean isFancyAvailable = isModulePresent("stats.fancy");
		String message = "Module 'stats.fancy' is"
				+ (isFancyAvailable ? " " : " not ")
				+ "available.";
		System.out.println(message);
		return isFancyAvailable;
	}

	private static boolean isModulePresent(String moduleName) {
		return Statistician.class
				.getModule()
				.getLayer()
				.findModule(moduleName)
				.isPresent();
	}

	public Statistics emptyStatistics() {
		return Statistics.empty();
	}

	public Statistics compute(Statistics currentStats, Iterable<DiagnosticDataPoint> dataPoints) {
		if (isFancyAvailable) {
			System.out.println(FancyStats.copyrightNotice());
		}

		Statistics finalStats = currentStats;
		for (DiagnosticDataPoint dataPoint : dataPoints)
			finalStats = finalStats.merge(dataPoint);
		return finalStats;
	}

}
