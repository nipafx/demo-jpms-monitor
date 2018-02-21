package monitor.observer.zero;

import monitor.observer.ServiceObserver;
import monitor.observer.ServiceObserverFactory;

import java.util.Optional;

public class ZeroServiceObserverFactory implements ServiceObserverFactory {

	public ZeroServiceObserverFactory() {
		System.out.println("CREATED: ZeroServiceFactory\n");
	}

	@Override
	public Optional<ServiceObserver> createIfMatchingService(String service) {
		return ZeroServiceObserver.createIfZeroService(service);
	}

}
