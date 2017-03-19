module monitor.rest {
	requires spark.core;
	requires transitive monitor.statistics;

	exports monitor.rest;
}
