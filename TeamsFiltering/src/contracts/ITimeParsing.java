package contracts;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public interface ITimeParsing {
	public LocalTime parseChipTime(String timeString);
	public String parseChipTimeFormat(String timeString);

}
