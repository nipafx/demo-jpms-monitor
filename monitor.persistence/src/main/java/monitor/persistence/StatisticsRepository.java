package monitor.persistence;

import monitor.statistics.Statistics;
import monitor.statistics.Statistics.LivenessQuota;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Optional;

public class StatisticsRepository {

	private final NumberFormat format = NumberFormat.getInstance(new Locale("fi", "FI"));

	public Optional<Statistics> load() {
		return Optional.empty();
	}

	public void store(Statistics statistics) {
		System.out.println("Total liveness: " + formatLiveness(statistics.totalLivenessQuota()));
		statistics.livenessQuotaByService()
				.sorted(Comparator.comparing(Entry::getKey))
				.map(serviceLiveness -> formatServiceLiveness(serviceLiveness.getKey(), serviceLiveness.getValue()))
				.forEach(System.out::println);
		System.out.println();
	}

	private String formatLiveness(LivenessQuota quota) {
		return String.format("%s (from %s data points)",
				format.format(quota.livenessQuota()),
				format.format(quota.dataPointCount()));
	}

	private String formatServiceLiveness(String serviceName, LivenessQuota quota) {
		return String.format(" * %s liveness: %s", serviceName, formatLiveness(quota));
	}

}
