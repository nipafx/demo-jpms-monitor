package monitor.observer;

import java.util.Optional;

public interface ServiceObserverFactory {

	Optional<ServiceObserver> createIfMatchingService(String service);

}
