package monitor.observer.utils;

import java.util.function.Predicate;

public class ObserverUtil {

	public static Predicate<String> byName(String name) {
		return service -> byName(service, name);
	}

	public static boolean byName(String service, String name) {
		return service.contains(name);
	}

}
