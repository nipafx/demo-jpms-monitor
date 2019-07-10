module monitor.observer.gamma {
	requires monitor.observer;
	provides monitor.observer.ServiceObserverFactory
		with monitor.observer.gamma.GammaServiceObserverFactory;
}
