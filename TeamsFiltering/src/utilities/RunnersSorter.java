package utilities;

import java.util.ArrayList;
import java.util.Comparator;

import contracts.IRunnerSorting;
import data.Runner;

public class RunnersSorter implements IRunnerSorting{
	
	public ArrayList<Runner> sortRunnersByTimeAscending(ArrayList<Runner> runners)
	{
		ArrayList<Runner> sortedArray =  new ArrayList<Runner>(runners);
		
		sortedArray.sort(new Comparator<Runner>() {

			@Override
			public int compare(Runner r1, Runner r2) {
				return r1.getChipTime().compareTo(r2.getChipTime());
			}
			});
		
		return sortedArray;
	}
}
