package boundary;
import java.util.Scanner;
import control.StudentControl;

import control.Container;
import control.HashingPassword;
import entity.*;
import entity.AllEnums.WeekType;

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
					do {
						// TODO S: Menu going back to previous error input instead of back to start
						indexno = -1;
						System.out.println("(4) Check Vacancies Available");
						System.out.printf("Enter course index no. to check (%d to return): ",Container.BREAK_MENU);
						if (sc.hasNextInt())
						{
							indexno = sc.nextInt();
							
							if(indexno==Container.BREAK_MENU)
							{
								op4=false;
								break;
							}
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
							sc.next();
						}
					}
					while(op4);
					break;
					
				case 5: /* (5) Change Index Number of Course*/
					boolean op5 = true;
					do {
						// TODO S: Menu going back to previous error input instead of back to start
						indexno = -1;
						cIndex = -1;
						System.out.println("(5) Change Index Number of Course");
						System.out.printf("Enter course index no. to change (%d to return): ",Container.BREAK_MENU);
						if (sc.hasNextInt())
						{
							cIndex = sc.nextInt();
							if(cIndex==Container.BREAK_MENU)
							{
								op5=false;
								break;
							}
							
							CoursePlan currCP = null;
							for(int i=0;i<StudentControl.studentInfo.getCoursePlan().size();i++)
							{
								if(StudentControl.studentInfo.getCoursePlan().get(i).getIndex()==cIndex)
								{
									currCP = StudentControl.studentInfo.getCoursePlan().get(i);
									break;
								}
							}
							
							if(currCP==null)
							{
								System.out.println("Course index no. not registered!");
								continue;
							}

							System.out.printf("Enter new course index no. (%d to return): ",Container.BREAK_MENU);
							if (sc.hasNextInt())
							{
								indexno = sc.nextInt();
								if(indexno==Container.BREAK_MENU)
								{
									op5=false;
									break;
								}
								
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
								// TODO S: [CHECK IF WORKING] need check for valid slots here
								else if(newCS.getTotalSlots() == newCS.getSlotList().size())
								{
									System.out.println("No vacancy for new course index!");
									continue;
								}
								
								// TODO S: [CHECK IF WORKING] check clash in timetable (can extract into reusable method)
								if(StudentControl.timetableClash(StudentControl.studentInfo, currCP, newCS.getCoursePlan()))
								{
									System.out.println("Course index no. clashes with timetable!");
									continue;
								}
								
								// student update
								StudentControl.studentInfo.getCoursePlan().remove(currCP);
								StudentControl.studentInfo.getCoursePlan().add(newCS.getCoursePlan());
								
								// course slots update
								newCS.getSlotList().add(StudentControl.studentInfo.getMatricNo());
								for(int i=0;i<Container.courseSlotsList.size();i++)
								{
									if(Container.courseSlotsList.get(i).getCoursePlan().getIndex()==cIndex)
									{
										Container.courseSlotsList.get(i).getSlotList().remove(StudentControl.studentInfo.getMatricNo());
										// TODO S: [WIP HALTED] Update course slot waiting list -> move user into courseslot to fill vacancy
										break;
									}
								}
								
								// TODO S: [CHECK IF WORKING] update and save to file here (overwriteFileWithData)
								if(Container.DEBUG_MODE) // TODO S: remove DEBUG_MODE limitation for saving to txt file
								{
									Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
									Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
								}
								
								// TODO S: do proper checks on courseslots after proper test cases population
								if(Container.DEBUG_MODE)
								{
									System.out.println(StudentControl.studentInfo.getCoursePlan());
									for(int i=0;i<Container.courseSlotsList.size();i++)
									{
										System.out.println(Container.courseSlotsList.get(i).getCoursePlan().getCourseID() + ":\t"
												+ Container.courseSlotsList.get(i).getSlotList());
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
					String userName, password;
					Student st2;
					do {
						// TODO S: Menu going back to previous error input instead of back to start
						indexno = -1;
						cIndex = -1;
						st2 = null;
						System.out.println("(6) Swop Index Number with Another Student");
						System.out.printf("Enter course index no. to swop (%d to return): ", Container.BREAK_MENU);
						if (sc.hasNextInt())
						{
							cIndex = sc.nextInt();
							if(cIndex==Container.BREAK_MENU)
							{
								op6=false;
								break;
							}

							CoursePlan currCP = null;
							for(int i=0;i<StudentControl.studentInfo.getCoursePlan().size();i++)
							{
								if(StudentControl.studentInfo.getCoursePlan().get(i).getIndex()==cIndex)
								{
									currCP = StudentControl.studentInfo.getCoursePlan().get(i);
									break;
								}
							}

							if(currCP==null)
							{
								System.out.println("Course index no. not registered!");
								continue;
							}

							System.out.println("\tFOR PEER (STUDENT 2):");
							System.out.printf("Please enter user name (%d to return): ", Container.BREAK_MENU);
							userName = sc.next();
							if(userName.equals(""+Container.BREAK_MENU))
							{
								op6=false;
								break;
							}
							else if(userName.equals(StudentControl.studentInfo.getUserName()))
							{
								System.out.println("You have entered the same username as the one currently logged in!");
								continue;
							}
							
							System.out.print("Enter your Password: ");
							
							if(Container.DEBUG_MODE)
								password = sc.next();
							else
							{
								// For masking password.
								char[] passMask = System.console().readPassword(); 
								password = new String(passMask);
							}

							password = HashingPassword.encryptThisString(password);
							
							if(LoginAccount.getFileInfo(userName, password))
							{
								// After login successful
								for(int i=0;i<Container.studentList.size();i++)
								{
									if(Container.studentList.get(i).getUserName().equals(userName))
									{
										st2 = Container.studentList.get(i);
										break;
									}
								}
							}
							else
							{
								System.out.println("You have enter the wrong Username or Password");
								continue;
							}
							
							if(st2 == null)
							{
								System.out.println("Student account not found!");
								continue;
							}

							System.out.printf("Enter student 2's course index no. to swop (%d to return): ", Container.BREAK_MENU);
							if (sc.hasNextInt())
							{
								indexno = sc.nextInt();
								if(indexno==Container.BREAK_MENU)
								{
									op6=false;
									break;
								}
								
								if(cIndex == indexno)
								{
									System.out.println("Same course index no. selected!");
									continue;
								}
								
								CoursePlan newCP = null;
								for(int i=0;i<st2.getCoursePlan().size();i++)
								{
									if(st2.getCoursePlan().get(i).getIndex()==indexno)
									{
										newCP = st2.getCoursePlan().get(i);
										break;
									}
								}
								
								if(newCP==null)
								{
									System.out.println("Course index no. not registered for student 2!");
									continue;
								}
								
								// TODO S: [CHECK IF WORKING] Check if new indexes clashes with timetable for both students
								if(StudentControl.timetableClash(StudentControl.studentInfo, currCP, newCP))
								{
									System.out.println("Course index no. clashes with timetable for Student " + StudentControl.studentInfo.getUserName() + "!");
									continue;
								}
								else if(StudentControl.timetableClash(st2, newCP, currCP))
								{
									System.out.println("Course index no. clashes with timetable for Student " + st2.getUserName() + "!");
									continue;
								}
								
								System.out.println("Swopping ...");
								
								// student update
								StudentControl.studentInfo.getCoursePlan().remove(currCP);
								StudentControl.studentInfo.getCoursePlan().add(newCP);
								st2.getCoursePlan().remove(newCP);
								st2.getCoursePlan().add(currCP);
								
								// course slots update
								for(int i=0;i<Container.courseSlotsList.size();i++)
								{
									if(Container.courseSlotsList.get(i).getCoursePlan().equals(currCP))
									{
										Container.courseSlotsList.get(i).getSlotList().remove(StudentControl.studentInfo.getMatricNo());
										Container.courseSlotsList.get(i).getSlotList().add(st2.getMatricNo());
									}
									else if(Container.courseSlotsList.get(i).getCoursePlan().equals(newCP))
									{
										Container.courseSlotsList.get(i).getSlotList().remove(st2.getMatricNo());
										Container.courseSlotsList.get(i).getSlotList().add(StudentControl.studentInfo.getMatricNo());
									}
								}
								
								// TODO S: [CHECK IF WORKING] update and save to file here
								if(Container.DEBUG_MODE) // TODO S: remove DEBUG_MODE limitation for saving to txt file
								{
									Container.overwriteFileWithData(Container.STUDENT_FILE, Container.studentList);
									Container.overwriteFileWithData(Container.COURSESLOT_FILE, Container.courseSlotsList);
								}
								
								// TODO S: do proper checks on courseslots after proper test cases population
								if(Container.DEBUG_MODE)
								{
									System.out.println(StudentControl.studentInfo.getCoursePlan());
									System.out.println(st2.getCoursePlan());
									for(int i=0;i<Container.courseSlotsList.size();i++)
									{
										System.out.println(Container.courseSlotsList.get(i).getCoursePlan().getCourseID() + ":\t"
												+ Container.courseSlotsList.get(i).getSlotList());
									}
								}

								System.out.println("Successfully swopped indexes!");
								op6=false;
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
