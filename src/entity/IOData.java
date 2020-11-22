package entity;

/**
 * Interface class for entity classes that reads from or stores to external file
 * @param <T> 
 */
public interface IOData<T>
{	
	/**
	 * An abstract method to write/overwrite class object data to text file
	 * @param fileName The name of the file to write to.
	 * @param overwrite To indicate whether to overwrite the file or to simply append at the bottom of the text file.
	 * @return boolean value to confirm if writing is successful.
	 */
	public abstract boolean writeDataToFile(String fileName, boolean overwrite);
	/**
	 * To read each line of data from the text file.
	 * @param fileLine To indicate which line of code to read from.
	 * @return relevant information.
	 */
	public abstract T readDataFile(String fileLine);
}