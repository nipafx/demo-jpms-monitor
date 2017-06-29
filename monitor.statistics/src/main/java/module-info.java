module monitor.statistics {
	requires monitor.observer;
//	this requirements is useless; it is only there to demonstrate
//	that a missing transitive dependency causes compile errors for
//	dependant projects (in this case monitor.rest)
	requires transitive monitor.observer.alpha;

	exports monitor.statistics;
}
