module monitor.observer.zero {
	requires monitor.observer;
	provides monitor.observer.ServiceObserverFactory
		with monitor.observer.zero.ZeroServiceObserverFactory;
}
