package stats.fancy;

import java.time.LocalDateTime;

public class FancyStats {

	private static final String COPYRIGHT_NOTICE = "DON'T USE FancyStats FOR EVIL! (©%d)";

	public static String copyrightNotice() {
		return String.format(COPYRIGHT_NOTICE, LocalDateTime.now().getYear());
	}

}
