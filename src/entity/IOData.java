package entity;

// Interface class for entity classes
// that reads from external file
// or stores to external file
public interface IOData<T>
{
	// abstract method to write class object data to file
	// takes in name of file to write to
	// and boolean to specify whether to append data or overwrite
	// returns if write data is successful
	public abstract boolean writeDataToFile(String fileName, boolean overwrite);
	
	// abstract method to read the data from file line,
	// overrides class object data and return it
	public abstract T readDataFile(String fileLine);
	
	// abstract method to update single line of data
	// using array of key values to search line
	// and replace entire class object data with new values
	public abstract boolean updateLineInFile(String fileName, String[] keys);
}