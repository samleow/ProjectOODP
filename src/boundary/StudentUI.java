package boundary;
import java.util.Scanner;
import control.StudentControl;

import control.Container;
import entity.*;

public class StudentUI {

	public static void studentLogin() {
		int choice, indexno = -1, cIndex = -1;
		boolean run = true;

		System.out.println("Welcome " + StudentControl.studentInfo.getName());

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("(1) *Add Course");
			System.out.println("(2) Drop Course");
			System.out.println("(3) Check/Print Courses Registered");
			System.out.println("(4) Check Vacancies Available");
			System.out.println("(5) Change Index Number of Course");
			System.out.println("(6) Swop Index Number with Another Student");
			System.out.println("(7) Log Out");
			System.out.print("Enter the your choice: ");
			// validate the choice input
			if (sc.hasNextInt()) {
				choice = sc.nextInt();
				switch (choice) {
				case 1: /* (1) *Add Course */
					System.out.print("Enter the Index Number of the Course to add: ");
					indexno = sc.nextInt();
					StudentControl.addCourse(indexno);
					break;
					
				case 2: /* (2) *Drop Course */
					System.out.println("Display Current Courses you have took");
					for(int i = 0; i < StudentControl.studentInfo.getCoursePlan().size(); i++) 
					{
						System.out.println("CourseID: " + StudentControl.studentInfo.getCoursePlan().get(i).getCourseID() + " " + "IndexNo: " + StudentControl.studentInfo.getCoursePlan().get(i).getIndex());
					}
				
					System.out.print("Enter the Index Number of the Course to drop: ");
					indexno = sc.nextInt();
					StudentControl.dropCourse(indexno);
					break;
					
				case 3: /* (3) Check/Print Courses Registered*/
					StudentControl.displayCourse();

					break;
				case 4: /* (4) Check Vacancies Available*/
					boolean op4 = true;
					indexno = -1;
					do {
						System.out.println("(4) Check Vacancies Available");
						System.out.print("Enter course index no. to check: ");
						if (sc.hasNextInt())
						{
							indexno = sc.nextInt();
							for (int i=0;i<Container.courseSlotsList.size();i++)
							{
								if(Container.courseSlotsList.get(i).getCoursePlan().getIndex()==indexno)
								{
									CourseSlots cs = Container.courseSlotsList.get(i);
									int v = cs.getTotalSlots()-cs.getSlotList().size();
									System.out.printf("The vacancies available for %d are : %d\n",indexno,v);
									System.out.printf("The number of students in waiting list for %d are : %d\n",indexno,cs.getWaitingList().size());
									op4=false;
									break;
								}
							}
							if(op4)
								System.out.printf("No course index no. %d found!\n",indexno);
						}
						else
						{
							System.out.println("Invalid input for course index no.!");
							// Clear sc
							sc.next();
						}
					}
					while(op4);
					break;
					
				case 5: /* (5) Change Index Number of Course*/
					boolean op5 = true;
					indexno = -1;
					cIndex = -1;
					do {
						System.out.println("(5) Change Index Number of Course");
						System.out.print("Enter course index no. to change: ");
						if (sc.hasNextInt())
						{
							cIndex = sc.nextInt();
							// TODO:
							// use the new method for getting Student
							Student s = null;
							for(int i=0;i<Container.studentList.size();i++)
							{
								if(Container.studentList.get(i).getUserName().equals(StudentControl.studentInfo.getUserName()))
								{
									s = Container.studentList.get(i);
									break;
								}
							}
							
							CoursePlan currCP = null;
							for(int i=0;i<s.getCoursePlan().size();i++)
							{
								if(s.getCoursePlan().get(i).getIndex()==cIndex)
								{
									currCP = s.getCoursePlan().get(i);
									break;
								}
							}
							
							if(currCP==null)
							{
								System.out.println("Course index no. not registered!");
								continue;
							}

							System.out.print("Enter new course index no.: ");
							if (sc.hasNextInt())
							{
								indexno = sc.nextInt();
								
								if(currCP.getIndex()==indexno)
								{
									System.out.println("Course index no. entered is same as old one!");
									continue;
								}
								
								CourseSlots newCS = null;
								for(int i=0;i<Container.courseSlotsList.size();i++)
								{
									if(Container.courseSlotsList.get(i).getCoursePlan().getIndex()==indexno)
									{
										newCS = Container.courseSlotsList.get(i);
										break;
									}
								}

								if(newCS==null)
								{
									System.out.println("Course index no. not found!");
									continue;
								}
								else if(!newCS.getCoursePlan().getCourseID().equals(currCP.getCourseID()))
								{
									System.out.println("Course index no. does not match with course!");
									continue;
								}
								
								// TODO:
								// need check for clash in timetable here
								// need check for valid slots here
								
								// student update
								s.getCoursePlan().remove(currCP);
								s.getCoursePlan().add(newCS.getCoursePlan());
								
								// course slots update
								newCS.getSlotList().add(s.getMatricNo());
								for(int i=0;i<Container.courseSlotsList.size();i++)
								{
									if(Container.courseSlotsList.get(i).getCoursePlan().getIndex()==cIndex)
									{
										Container.courseSlotsList.get(i).getSlotList().remove(s.getMatricNo());
										break;
									}
								}
								
								// TODO:
								// update and save to file here
								
								// TODO:
								// do proper checks on courseslots after proper test cases population
								if(Container.DEBUG_MODE)
								{
									for(int i=0;i<s.getCoursePlan().size();i++)
									{
										System.out.println(s.getCoursePlan().get(i));
									}
									for(int i=0;i<Container.courseSlotsList.size();i++)
									{
										System.out.println(Container.courseSlotsList.get(i).getSlotList());
									}
								}

								System.out.printf("Index no. for Course %s successfully set to %d!\n",currCP.getCourseID(),indexno);
								op5=false;
								break;
							}
							else
							{
								System.out.println("Invalid input for course index no.!");
								// Clear sc
								sc.next();
							}
							
						}
						else
						{
							System.out.println("Invalid input for course index no.!");
							// Clear sc
							sc.next();
						}
					}
					while(op5);
					break;
					
				case 6: /* (6) Swop Index Number with Another Student*/
					boolean op6 = true;
					indexno = -1;
					cIndex = -1;
					do {
						System.out.println("(6) Swop Index Number with Another Student");
						System.out.print("Enter course index no. to swop: ");
						if (sc.hasNextInt())
						{
							cIndex = sc.nextInt();
							// TODO:
							// continue here ...
						}
						else
						{
							System.out.println("Invalid input for course index no.!");
							// Clear sc
							sc.next();
						}
					}
					while(op6);
					break;
					
				case 7: /* (7) Log Out*/
					StudentControl.studentInfo = null;
					System.out.println("Log Out Student...");
					run = false;
					break;

				default:
					System.out.println("Please Enter Choice from 1 to 7");
				}
			} else {
				System.out.println("Please Enter Choice from 1 to 7");
				// Clear sc
				sc.next();
			}
		} while (run);
	}
	
}
