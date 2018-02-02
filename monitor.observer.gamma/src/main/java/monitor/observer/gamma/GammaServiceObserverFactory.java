package monitor.observer.gamma;

import monitor.observer.ServiceObserver;
import monitor.observer.ServiceObserverFactory;

import java.util.Optional;

public class GammaServiceObserverFactory implements ServiceObserverFactory {

	@Override
	public Optional<ServiceObserver> createIfMatchingService(String service) {
		return GammaServiceObserver.createIfGammaService(service);
	}

}
