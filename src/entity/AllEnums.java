package entity;

/**
 * Class to contain all enum values.
 */
public class AllEnums {

	/**
	 * Account types that can be used.
	 */
	public enum AccountType {
		DEFAULT, STUDENT, ADMIN
	}

	/**
	 * Day of the week that can be used.
	 */
	public enum Day {
		DEFAULT, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}

	/**
	 * Genders that can be used.
	 */
	public enum Gender {
		DEFAULT, MALE, FEMALE
	}

	/**
	 * Lesson types that can be used.
	 */
	public enum LessonType {
		DEFAULT, LECTURE, TUTORIAL, LAB
	}

	/**
	 * Week types that can be used.
	 */
	public enum WeekType {
		DEFAULT, ODD, EVEN, WEEKLY
	}

	/**
	 * Course types that can be used.
	 */
	public enum CourseType {
		DEFAULT, CORE, UNRESTRICTED_ELECTIVE, GER, PRESCRIBED,
	}
}
