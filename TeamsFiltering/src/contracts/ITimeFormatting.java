package contracts;

import java.time.Duration;

public interface ITimeFormatting {

	public Duration roundSeconds(Duration time);
	public String formatCSVOutputTime(Duration duration, String format);
}
