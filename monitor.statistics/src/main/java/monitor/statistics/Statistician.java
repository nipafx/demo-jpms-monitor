package monitor.statistics;

import monitor.observer.DiagnosticDataPoint;
import stats.fancy.FancyStats;

public class Statistician {

	private final boolean isFancyAvailable;

	public Statistician() {
		isFancyAvailable = checkFancyStats();
	}

	private boolean checkFancyStats() {
		boolean isFancyAvailable = isModulePresent("stats.fancy");
		String message = "Module 'stats.fancy' is"
				+ (isFancyAvailable ? " " : " not ")
				+ "available.";
		System.out.println(message);
		return isFancyAvailable;
	}

	private boolean isModulePresent(String moduleName) {
		return this.getClass()
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
			// here, fancystats could actually be used
			System.out.println(FancyStats.copyrightNotice());
			return emptyStatistics();
		} else {
			Statistics finalStats = currentStats;
			for (DiagnosticDataPoint dataPoint : dataPoints)
				finalStats = finalStats.merge(dataPoint);
			return finalStats;
		}
	}

}
