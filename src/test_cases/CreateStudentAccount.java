package test_cases;

import java.io.*;
import java.util.*;

import entity.Course;
import entity.Period;
import entity.Student;
//import entity.AllEnums.Gender;







public class CreateStudentAccount {
	
	private static final String SEPARATOR = "|";
	
	
	public CreateStudentAccount() {
	
		// create a file 
		PrintWriter createTxt = createFile("StudentAccount.txt");
		createTxt.close();
	}
	
	/*
	 * public CreateStudentAccount(String matricNo, String gender, String
	 * nationality, int MaxAU, String password) {
	 * 
	 * 
	 * 
	 * // PrintWriter createTxt = createFile("StudentAccount.txt"); // // // String
	 * userInfo = matricNo + "|" + gender + "|" + nationality +"|" + MaxAU +"|"
	 * +password; // // // // Writes the string to the file // //
	 * userOutput.println(userInfo);
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	
	  // an example of saving
	public static void saveStudent(String filename, List studentList) throws IOException {
			
		List newStudentList= new ArrayList() ;

	        for (int i = 0 ; i < studentList.size() ; i++) {
					Student student = (Student)studentList.get(i);
					
					StringBuilder st =  new StringBuilder() ;
					st.append(student.getMatricNo().trim());
					st.append(SEPARATOR);
					st.append(student.getName().trim());
					st.append(SEPARATOR);
					//st.append(student.getGender().toString().trim());
					//st.append(SEPARATOR);
					st.append(student.getNationality().trim());
					st.append(SEPARATOR);
					st.append(Integer.toString(student.getMaxAU()).trim());
					st.append(SEPARATOR);
					//st.append(student.getAccessPeriod().toString().trim());
					//st.append(SEPARATOR);
					st.append(student.getPassword().trim());
					st.append(SEPARATOR);
					newStudentList.add(st.toString()) ;
				}
				write(filename, newStudentList);
				

		}
	
	  /** Write fixed content to the given file. */
	  public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println((String)data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	  }
	  
	  
	   // an example of reading
		public static ArrayList readStudents(String filename) throws IOException {
			
			// read String from text file
			ArrayList stringArray = (ArrayList)read(filename);
			
			ArrayList studentList = new ArrayList() ;// to store Student data

	        for (int i = 0 ; i < stringArray.size() ; i++) {
					String st = (String)stringArray.get(i);
					// get individual 'fields' of the string separated by SEPARATOR
					StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

					String  matricNo = star.nextToken().trim();	// first token
					//String  gender = star.nextToken().trim();	// second token
					
					String  name = star.nextToken().trim();
					
					String  nationality = star.nextToken().trim();
					
					int  maxAU = Integer.parseInt(star.nextToken().trim());
					
					String  password = star.nextToken().trim();
					
					
					// create Student object from file data
					Student student = new Student(matricNo, name, nationality, maxAU, password);
					
					
					// add to Professors list
					studentList.add(student) ;
				}
				return studentList ;
		}
		
		 /** Read the contents of the given file. */
		  public static List read(String fileName) throws IOException {
			List data = new ArrayList() ;
		    Scanner scanner = new Scanner(new FileInputStream(fileName));
		    try {
		      while (scanner.hasNextLine()){
		        data.add(scanner.nextLine());
		      }
		    }
		    finally{
		      scanner.close();
		    }
		    return data;
		  }

		// Create the file and the PrintWriter that will write to the file

			private static PrintWriter createFile(String fileName) {

				try {

					// Creates a File object that allows you to work with files on the hardrive

					File listOfNames = new File(fileName);

					// FileWriter is used to write streams of characters to a file
					// BufferedWriter gathers a bunch of characters and then writes
					// them all at one time (Speeds up the Program)
					// PrintWriter is used to write characters to the console, file

					PrintWriter infoToWrite = new PrintWriter(new BufferedWriter(new FileWriter(listOfNames)));
					return infoToWrite;
				}

				// You have to catch this when you call FileWriter

				catch (IOException e) {

					System.out.println("An I/O Error Occurred");

					// Closes the program

					System.exit(0);

				}
				return null;
			}
}
