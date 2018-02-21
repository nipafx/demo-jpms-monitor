package monitor.observer.beta;

import monitor.observer.ServiceObserver;
import monitor.observer.ServiceObserverFactory;

import java.util.Optional;

public class BetaServiceObserverFactory implements ServiceObserverFactory {

	public BetaServiceObserverFactory() {
		System.out.println("CREATED: BetaServiceFactory\n");
	}

	@Override
	public Optional<ServiceObserver> createIfMatchingService(String service) {
		return BetaServiceObserver.createIfBetaService(service);
	}

}
