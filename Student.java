import java.util.HashMap;


class Student
{
    // Finish Constructors
    // Finish set name 
    // Finish set id
    // Finish addCourse
    // formattedCourses returns number rounded down

    private String name;
    private long id;
    private HashMap<String, Float> courses = new HashMap<String, Float>();

    public Student(String name, long id)
    {
        // Error handle name and id
        this.name = name;
        this.id = id;
    }

    public Student(String name)
    {
        // Error handle name
        this.name = name;
    }

    public Student(long id)
    {
        // Error handle id
        this.id = id;
    }

    public Student(Student original)
    {
        this.name = new String(original.name);
        this.id = original.id;
        original.courses.forEach((String key, Float value) -> {
            this.courses.put(new String(key), value.floatValue());
        });
    }
    
    public Student()
    {
        return;
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

    public void addCourse(String courseCode, float[] marks)
    {

        // Throw Exception if course code is not right
        // Add exception in the if statement below
        // Add exception in the else statement in the loop below

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
            }
            else
            {
                // Throw Exception
            }
        }

        courses.put(courseCode, total);
    }

    public String[] formattedCourses()
    {
        String[] results = new String[courses.size()];

        courses.forEach((String courseCode, Float marks) -> {
            String temp = "";
            temp += Long.toString(this.id) + ", ";
            temp += this.name + ", ";
            temp += courseCode + ", ";
            temp += String.format("%.1f", marks);
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

    public long compareTo(Student other)
    {
        long result = 0;

        if (this.id == other.id)
        {
            result = this.name.compareTo(other.name);
        }
        else
        {
            result = this.id - other.id;
        }

        return result;
    }
}