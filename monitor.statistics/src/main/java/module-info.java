module monitor.statistics {
	requires transitive monitor.observer;
	requires static stats.fancy;
	exports monitor.statistics;
}
