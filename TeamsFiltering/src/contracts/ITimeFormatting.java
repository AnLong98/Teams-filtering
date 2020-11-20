package contracts;

import java.time.Duration;

public interface ITimeFormatting {

	public Duration roundTime(Duration time, int decimalsToHave);
	public String formatCSVOutputTime(Duration duration, String format);
}
