package contracts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.Runner;
import exceptions.IllegalInputHeaderException;

public interface IFileParsing {
	public ArrayList<Runner> readRunnersFromFile(File file) throws IllegalInputHeaderException, IOException;
}
