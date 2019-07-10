module monitor.observer.alpha {
	requires monitor.observer;
	provides monitor.observer.ServiceObserverFactory
		with monitor.observer.alpha.AlphaServiceObserverFactory;
}
