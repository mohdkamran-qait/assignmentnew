package javaassignment;

/**
 * 
 * @author mohdkamran
 *
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student {
	private int ID;
	private String name;
	private double CGPA;

	public Student(int ID, String name, Double CGPA) {
		this.ID = ID;
		this.name = name;
		this.CGPA = CGPA;

	}

	/**
	 * get the ID of Student
	 * 
	 * @return ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * get the name of Student
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the CGPA of Student
	 * 
	 * @return CGPA
	 */
	public double getCGPA() {
		return CGPA;
	}

}

class Compare implements Comparator {
	public static void main(String args[]) {
		Compare com = new Compare();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the no of student");
		int N = scan.nextInt();
		ArrayList<Student> list = new ArrayList<Student>();
		while (N > 0) {
			int ID = scan.nextInt();
			String name = scan.next();
			double CGPA = scan.nextDouble();

			Student S = new Student(ID, name, CGPA);
			list.add(S);

			N--;
		}

		Collections.sort(list, com);
		for (Student st : list) {
			System.out.println(st.getName());

		}

	}

	@Override
	public int compare(Object o1, Object o2) {
		Student s1 = (Student) o1;
		Student s2 = (Student) o2;

		if (s1.getID() == s2.getID()) {
			if (s1.getName() == s2.getName()) {
				if (s1.getID() < s2.getID()) {
					return 1;
				} else {
					return -1;
				}
			} else {
				return s1.getName().compareTo(s2.getName());
			}
		} else if (s1.getCGPA() < s2.getCGPA()) {
			return 1;
		} else {
			return -1;
		}
	}
}