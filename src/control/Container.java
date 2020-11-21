package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import entity.*;

public class Container
{
	private Container()
	{}
	
	// Debug mode
	public static final boolean DEBUG_MODE			= true;
	
	// Constants
	public static final int BREAK_MENU				= -1;
	public static final String DIVIDER				= "|";
	public static final String ADMIN_FILE			= "Admin.txt";
	public static final String COURSE_FILE			= "Course.txt";
	public static final String COURSEPLAN_FILE		= "CoursePlan.txt";
	public static final String COURSESLOT_FILE		= "CourseSlots.txt";
	public static final String LESSON_FILE			= "Lesson.txt";
	public static final String STUDENT_FILE			= "StudentAccount.txt";
	public static final String SENDER_EMAIL_FILE	= "senderEmail.txt";
	
	// Reference List
	public static ArrayList<Lesson> lessonList				= new ArrayList<Lesson>();
	public static ArrayList<CoursePlan> coursePlanList		= new ArrayList<CoursePlan>();
	public static ArrayList<Student> studentList			= new ArrayList<Student>();
	public static ArrayList<Course> courseList				= new ArrayList<Course>();
	public static ArrayList<CourseSlots> courseSlotsList 	= new ArrayList<CourseSlots>();
	public static ArrayList<Admin> adminList				= new ArrayList<Admin>();

	
	public static boolean readLessonFile(String fileName, ArrayList<Lesson> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);

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
	
	
	public static boolean readStudentFile(String fileName, ArrayList<Student> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);

        for (int i = 0 ; i < stringArray.size() ; i++)
        {
        	if(!stringArray.get(i).toString().isBlank())
        	{
        		Student l = new Student();
        		l.readDataFile((String)stringArray.get(i));
        		list.add(l);
        	}
        }
        return true;
    }
	
	
	public static boolean readAdminFile(String fileName, ArrayList<Admin> list) throws IOException
	{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(fileName);

        for (int i = 0 ; i < stringArray.size() ; i++)
        {
        	if(!stringArray.get(i).toString().isBlank())
        	{
        		Admin a = new Admin();
        		a.readDataFile((String)stringArray.get(i));
        		list.add(a);
        	}
        }
        
        return true;
    }

	// TODO S: combine all read from File methods into one dynamic method [possible wildcard <?> usage]
//	public static <T extends IOData> boolean readFile(String fileName, ArrayList<? extends IOData> list) throws IOException
//	{
//		// read String from text file
//		ArrayList<String> stringArray = (ArrayList<String>)read(fileName);
//
//        for (int i = 0 ; i < stringArray.size() ; i++)
//        {
//    		IOData<T> iodata;
//    		iodata.readDataFile((String)stringArray.get(i));
//    		list.add(iodata); // cannot add data into wildcard list ... need find alternative
//        }
//        
//        return true;
//    }

	public static boolean overwriteFileWithData(String fileName, ArrayList<? extends IOData> l)
	{

		for(int i = 0; i < l.size(); i++) {
			if(i==0) {
				l.get(i).writeDataToFile(fileName,true);
			}
			else {
				l.get(i).writeDataToFile(fileName,false);
			}
		}
		return true;
	}
	
	/** Read the contents of the given file. */
	public static List read(String fileName) throws IOException
	{
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try
		{
			while (scanner.hasNextLine())
			{
				data.add(scanner.nextLine());
			}
		} finally
		{
			scanner.close();
		}
		return data;
	}
	
	public static CourseSlots getCourseSlotByIndex(int index)
	{
		for (int i = 0; i < courseSlotsList.size(); i++)
		{
			if (courseSlotsList.get(i).getCoursePlan().getIndex() == index)
			{
				return courseSlotsList.get(i);
			}
		}
		return null;
	}
	
	public static CoursePlan getCoursePlanByIndex(int index)
	{
		for (int i = 0; i < coursePlanList.size(); i++)
		{
			if (coursePlanList.get(i).getIndex() == index)
			{
				return coursePlanList.get(i);
			}
		}
		return null;
	}
	
	public static CoursePlan getCoursePlanByIndex(int index, List<CoursePlan> l)
	{
		for (int i = 0; i < l.size(); i++)
		{
			if (l.get(i).getIndex() == index)
			{
				return l.get(i);
			}
		}
		return null;
	}
	
	public static Student getStudentByUsername(String username)
	{
		for (int i = 0; i < studentList.size(); i++)
		{
			if (studentList.get(i).getUserName().equals(username))
			{
				return studentList.get(i);
			}
		}
		return null;
	}
	
	public static Student getStudentByMatricNo(String matricno)
	{
		for (int i = 0; i < studentList.size(); i++)
		{
			if (studentList.get(i).getMatricNo().equals(matricno))
			{
				return studentList.get(i);
			}
		}
		return null;
	}
	
	public static Admin getAdminByUsername(String username)
	{
		for (int i = 0; i < adminList.size(); i++)
		{
			if (adminList.get(i).getUserName().equals(username))
			{
				return adminList.get(i);
			}
		}
		return null;
	}
}
