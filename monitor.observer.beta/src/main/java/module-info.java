module monitor.observer.beta {
	requires monitor.observer;
	provides monitor.observer.ServiceObserverFactory
		with monitor.observer.beta.BetaServiceObserverFactory;
}
