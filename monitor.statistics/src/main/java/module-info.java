module monitor.statistics {
	requires monitor.observer;
//	this requirement is useless; it is only there to demonstrate
//	that a missing transitive dependency causes compile errors for
//	dependent projects (in this case monitor.rest)
	requires transitive monitor.observer.alpha;

	exports monitor.statistics;
}
