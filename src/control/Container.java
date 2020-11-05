package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.*;

public class Container
{
	private Container()
	{}
	
	public static ArrayList<Lesson> lessonList = new ArrayList<Lesson>();
	public static ArrayList<CoursePlan> coursePlanList = new ArrayList<CoursePlan>();
	public static ArrayList<Student> studentList = new ArrayList<Student>();
	public static ArrayList<Course> courseList = new ArrayList<Course>();
	public static ArrayList<CourseSlots> courseSlotsList = new ArrayList<CourseSlots>();
	
	
	public static boolean readLessonFile(String fileName, ArrayList<Lesson> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);
		ArrayList alr = new ArrayList() ;// to store data

        for (int i = 0 ; i < stringArray.size() ; i++)
        {
        	if(!stringArray.get(i).toString().isBlank())
        	{
        		Lesson l = new Lesson();
        		l.readDataFile((String)stringArray.get(i));
        		list.add(l);
        	}
        }
        return true;
    }
	
	public static boolean readCourseFile(String fileName, ArrayList<Course> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);
		ArrayList alr = new ArrayList() ;// to store data

        for (int i = 0 ; i < stringArray.size() ; i++)
        {
        	if(!stringArray.get(i).toString().isBlank())
        	{
        		Course l = new Course();
        		l.readDataFile((String)stringArray.get(i));
        		list.add(l);
        	}
        }
        
        return true;
    }
	
	
	public static boolean readCoursePlanFile(String fileName, ArrayList<CoursePlan> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);
		ArrayList alr = new ArrayList() ;// to store data

        for (int i = 0 ; i < stringArray.size() ; i++)
        {
        	if(!stringArray.get(i).toString().isBlank())
        	{
        		CoursePlan l = new CoursePlan();
        		l.readDataFile((String)stringArray.get(i));
        		list.add(l);
        	}
        }
        return true;
    }
	
	
	
	public static boolean readCourseSlotsFile(String fileName, ArrayList<CourseSlots> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);
		ArrayList alr = new ArrayList() ;// to store data

        for (int i = 0 ; i < stringArray.size() ; i++)
        {
        	if(!stringArray.get(i).toString().isBlank())
        	{
        		CourseSlots l = new CourseSlots();
        		l.readDataFile((String)stringArray.get(i));
        		list.add(l);
        	}
        }
        return true;
    }
//	
//	public static <T> boolean readFile(String fileName, ArrayList<IOData> list) throws IOException
//	{
//		// read String from text file
//		ArrayList stringArray = (ArrayList)read(fileName);
//		ArrayList alr = new ArrayList() ;// to store Professors data
//
//        for (int i = 0 ; i < stringArray.size() ; i++)
//        {
//    		IOData iodata;
//    		iodata.readDataFile((String)stringArray.get(i));
//    		list.add(iodata);
//        }
//        
//        return true;
//    }

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
}
