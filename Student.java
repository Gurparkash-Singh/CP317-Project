import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Student implements Comparable<Student>
{
    // Finish Constructors
    // Finish set name 
    // Finish set id
    // Finish addCourse
    // formattedCourses returns number rounded down

    private String name;
    private long id; // why long and not int?
    private HashMap<String, float[]> courses; // start with a smaller HashMap?

    public Student(String name, long id)
    {
        // Error handle name and id
        this.name = name;
        this.id = id;
        this.courses = new HashMap<String, float[]>();
    }

    public Student(String name)
    {
        // Error handle name
        this.name = name;
        this.courses = new HashMap<String, float[]>();
    }

    public Student(long id)
    {
        // Error handle id
        this.id = id;
        this.courses = new HashMap<String, float[]>();
    }

    public Student(Student original)
    {
        this.name = new String(original.name);
        this.id = original.id;
        this.courses = new HashMap<String, float[]>();
        original.courses.forEach((String key, float[] values) -> {
            float[] toAdd = new float[5];
            for (int i = 0; i < toAdd.length; i++)
            {
                toAdd[i] = values[i];
            }
            this.courses.put(new String(key), toAdd);
        });
    }

    public Student()
    {
        this.courses = new HashMap<String, float[]>();
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        // Add error handlers
        this.name = name;
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        // Add error handlers
        this.id = id;
    }

    public boolean containsCourse(String course) {
        return this.courses.containsKey(course);
    }

    // make it analogous to put instead? similar returns, similar name, to mimic HashMap?
    // use separate parameters? requires separate checks, but it's not as wonky
    public void addCourse(String courseCode, float[] marks)
    {

        // Throw Exception if course code is not right
        // Add exception in the if statement below
        // Add exception in the else statement in the loop below

        float[] addMarks = new float[5];

        float total = 0;

        if (marks.length != 4)
        {
            // Throw exception
        }

        for (int i = 0; i < marks.length; i++)
        {
            float mark = marks[i];

            if (0 <= mark && mark <= 100)
            {
                if (i < 3)
                {
                    total += mark * 0.2;
                }
                else
                {
                    total += mark * 0.4;
                }

                addMarks[i] = mark;
            }
            else
            {
                // Throw Exception
            }
        }

        addMarks[4] = total;

        courses.put(courseCode, addMarks);
    }

    public String[] formattedCourses()
    {
        String[] results = new String[courses.size()];

        courses.forEach((String courseCode, float[] marks) -> {
            String temp = "";
            temp += Long.toString(this.id) + ", ";
            temp += this.name + ", ";
            temp += courseCode + ", ";
            temp += String.format("%.1f", marks[4]);
            int index = 0;
            for (int i = 0; i < results.length; i++)
            {
                if (results[i] == null)
                {
                    index = i;
                    break;
                }
            }
            results[index] = temp;
        });

        return results;
    }


    public int compareTo(Student other)
    {
        return Long.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if (o == null) {
            return false;
        } else if (o == this) {
            result = true;
        } else if (o instanceof Student) {
            Student other = (Student) o;
            result = this.id == other.id;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(this.id).hashCode();
    }

    public static void main(String[] args) 
    {
        HashMap<Long, String> names = new HashMap<Long, String>();

        // these should be moved to a smaller scope???
		Matcher matcher;
		Pattern coursepattern = Pattern.compile("^(\\d{9})" // student id: ddddddddd
				+ ", ([A-Z]{2}\\d{3})" // course code: ssddd
				+ ", ((?:\\d\\d)|(?:\\d\\d\\.\\d\\d?))" // the rest are grades: dd or dd.d or dd.dd
				+ ", ((?:\\d\\d)|(?:\\d\\d\\.\\d\\d?))"
				+ ", ((?:\\d\\d)|(?:\\d\\d\\.\\d\\d?))"
				+ ", ((?:\\d\\d)|(?:\\d\\d\\.\\d\\d?))$");
        Pattern namepattern = Pattern.compile("^(\\d{9})" // student id: ddddddddd
        + ", (.{0,20}$)"); // student name: any 20 or fewer characters
				
        try (BufferedReader namefile = new BufferedReader(new FileReader(args[0]))){
            String line = null; 
			while ((line = namefile.readLine()) != null) {
				// only adds entry to names when the pattern matches the line and the id doesn't already map to a name
				// else there's a problem with the data and it quits
				matcher = namepattern.matcher(line);
				if (!matcher.matches()) {
					System.out.format("Invalid input from %s: text isn't in the correct format\n", args[0]);
					return;
				}
				
                // makes sure every name in namefile is unique
				Long tempid = Long.valueOf(matcher.group(1));  
				if (names.containsKey(tempid)) {
					System.out.format("Invalid input from %s: each student ID should map to exactly one name, but %d maps to %s and %s.\n"
					, args[0], tempid.longValue(), names.get(tempid), matcher.group(2));
					return;
				}
				
				names.put(tempid, matcher.group(2));
			}
		} catch (IOException e) {
			System.err.format("%s\n", e);
			return;
        }
		
		// read from coursefile
		// should be an error when there isn't a bijection from coursefile student ids to namefile student ids
        HashMap<Long, Student> students = new HashMap<Long, Student>();
		try (BufferedReader coursefile = new BufferedReader(new FileReader(args[1]))) {
            String line = null; 
			while ((line = coursefile.readLine()) != null) {
				// only writes when the pattern matches the line, (id, code) is not a duplicate, and id maps to a name
				// or else there's an issue with the data and it quits
				
				// -----
				matcher = coursepattern.matcher(line);
				if (!matcher.matches()) {
                    System.out.format("Invalid input from %s: text isn't in the correct format\n", args[1]);
					return;
				}
				
                // makes sure every (id, course) in coursefile is unique
                Long tempid = Long.valueOf(matcher.group(1));
                String tempcourse = matcher.group(2);
				if (students.containsKey(tempid) && students.get(tempid).containsCourse(tempcourse)) {
                    System.out.format("Invalid input from %s: student with ID %d already has course %s", args[1], tempid, tempcourse);
					return;
				}
			
                // makes sure each id in coursefile appears in namefile

                // damn... wrong order... another loop for checking getting names?
				if (!names.containsKey(tempid)) {
                    System.out.format("ID %d from %s doesn't appear in %s", tempid, args[1], args[0]);
					return;
				}

                Student student = new Student(names.get(tempid), tempid);
                names.remove(tempid);
                student.addCourse(tempcourse, new float[] {Float.valueOf(matcher.group(3)), Float.valueOf(matcher.group(4))
                    , Float.valueOf(matcher.group(5)), Float.valueOf(matcher.group(6))});
				students.put(tempid, student);
				// -----
            }

            // makes sure each id in namefile appears (at least once) in coursefile
            if (!names.isEmpty()) {
                System.out.format("ID from %s doesn't appear in %s", args[0], args[1]);
                return; // multi return???
            }

		} catch (IOException e) {
			System.err.format("%s\n", e);
			return;
		}
        
        // only gets to this point if every line is matched by a pattern, every name in namefile is unique, every (id, course) in coursefile is unique,
        // and every id that appears in one file appears in the other file
        // ie the input is valid

        // this way it doesn't overwrite the output file if there's an error
        try (PrintWriter outfile = new PrintWriter(args[2])) {
            for (Student student: students.values()) {
                for (String line: student.formattedCourses()) {
                    outfile.println(line);
                }
            }
        } catch (IOException e) {
            System.err.format("%s\n", e);
			return;
        }
    }
}