package monitor.observer.gamma;

import monitor.observer.ServiceObserver;
import monitor.observer.ServiceObserverFactory;

import java.util.Optional;

public class GammaServiceObserverFactory implements ServiceObserverFactory {

	public GammaServiceObserverFactory() {
		System.out.println("CREATED: GammaServiceFactory\n");
	}

	@Override
	public Optional<ServiceObserver> createIfMatchingService(String service) {
		return GammaServiceObserver.createIfGammaService(service);
	}

}
