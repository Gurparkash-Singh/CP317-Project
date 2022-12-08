import java.util.HashMap;

class Student implements Comparable<Student>
{
    // formattedCourses returns number rounded down

    private String name;
    private long id;
    private HashMap<String, Float> courses = new HashMap<String, Float>();

    public Student(String name, long id) throws Exception
    {
        this.setName(name);
        this.setId(id);
    }

    public Student(String name) throws Exception
    {
        this.setName(name);
    }

    public Student(long id) throws Exception
    {
        this.setId(id);
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

    public void setName(String name) throws Exception
    {
        if (name.length() > 20)
        {
            throw new Exception("Name length exceeds 20 chars");
        }
        this.name = name;
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id) throws Exception
    {
        if (id >= 1000000000)
        {
            throw new Exception("Id length exceeds 9 digtis");
        }
        this.id = id;
    }

    public boolean courseExists(String courseCode)
    {
        return this.courses.containsKey(courseCode);
    }

    public void addCourse(String courseCode, float[] marks) throws Exception
    {
        if (!courseCode.matches("^[A-Za-z]{2}[0-9]{3}$"))
        {
            throw new Exception("Invalid course code");
        }

        if (this.courses.containsKey(courseCode))
        {
            throw new Exception("Course already exists");
        }

        float total = 0;

        if (marks.length != 4)
        {
            throw new Exception(
                String.format(
                    "Expected 4 marks received %d", 
                    marks.length
            ));
        }

        for (int i = 0; i < marks.length; i++)
        {
            float mark = marks[i];

            if (0 <= mark && mark < 100)
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
                throw new Exception("Marks should be between 0 and 100");
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

    public int compareTo(Student other)
    {
        return Long.compare(this.id, other.id);
    }
}