module monitor.rest {
	requires transitive monitor.statistics;
	requires spark.core;
	exports monitor.rest;
}
