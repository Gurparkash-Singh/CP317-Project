import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class FileFormatter {
    // Finish printOutputFile
    // Finish main method

    private HashMap<Long, Student> students;

    public FileFormatter()
    {
        this.students = new HashMap<Long, Student>();
    }

    public void run(File courseFile, File nameFile, File outputFile)
    {
        Scanner courseFileScanner = null;
        Scanner nameFileScanner = null;
        PrintStream outputFilePrinter = null;

        try
        {
            courseFileScanner = new Scanner(courseFile);
            nameFileScanner = new Scanner(nameFile);
            outputFilePrinter = new PrintStream(outputFile);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        try
        {
            this.readCourseFile(courseFileScanner, courseFile.getName());
            this.readNameFile(nameFileScanner, nameFile.getName());;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        for (Student value : this.students.values())
        {
            try {
                if (value.getName() == null)
                {
                    String message = String.format(
                        "Student with id %d is not present in NameFile",
                        value.getId()
                    );
                    throw new Exception(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        this.printOutputFile(outputFilePrinter);
    }

    private void readCourseFile(Scanner courseFile, String fileName) throws FileParsingException
    {

        String line;
        String[] courses;
        long id;
        Student student;
        float[] marks;
        marks = new float[4];

        int lineNumber = 1;
        while (courseFile.hasNextLine())
        {
            line = courseFile.nextLine();
            courses = line.split(",");
            if (courses.length > 6)
            {
                throw new FileParsingException(
                    "Extra Fields Provided", 
                    fileName, 
                    lineNumber
                );
            }

            if (courses.length < 6)
            {
                throw new FileParsingException(
                    "Missing fields", 
                    fileName, 
                    lineNumber
                );
            }

            try{
                id = Long.parseLong(courses[0].trim());
            }
            catch(Exception e)
            {
                throw new FileParsingException(
                    "Id must be a number", 
                    fileName, 
                    lineNumber
                );
            }
            for (int i = 0; i < marks.length; i++)
            {
                marks[i] = Float.parseFloat(courses[i + 2].trim());
            }
            if (this.students.containsKey(id))
            {
                student = this.students.get(id);
                try
                {
                    student.addCourse(courses[1].trim(), marks);
                }
                catch(Exception e)
                {
                    throw new FileParsingException(
                        e.getMessage(),
                        fileName,
                        lineNumber
                    );
                }
            }
            else
            {
                try {
                    student = new Student(id);
                    student.addCourse(courses[1].trim(), marks);
                } catch (Exception e) {
                    throw new FileParsingException(
                        e.getMessage(),
                        fileName,
                        lineNumber
                    );
                }
            }
            this.students.put(id, student);
            lineNumber++;
        }
    }

    private void readNameFile(Scanner file, String fileName) throws FileParsingException
    {
        String line;
        String[] names;
        long id;
        Student student;

        int lineNumber = 1;
        while (file.hasNextLine())
        {
            line = file.nextLine();
            names = line.split(", ");
            if (names.length > 2)
            {
                throw new FileParsingException(
                    "Extra Fields provided", 
                    fileName, 
                    lineNumber
                );
            }

            if (names.length < 2)
            {
                throw new FileParsingException(
                    "Missing Fields", 
                    fileName, 
                    lineNumber
                );
            }

            try{
                id = Long.parseLong(names[0].trim());
            }
            catch(Exception e)
            {
                throw new FileParsingException(
                    "Id must be a number", 
                    fileName, 
                    lineNumber
                );
            }

            if (!this.students.containsKey(id))
            {
                throw new FileParsingException(
                    "Student not present in course file", 
                    fileName, 
                    lineNumber
                );
            }

            student = this.students.get(id);
            if (student.getName() != null)
            {
                throw new FileParsingException(
                    "Student with same id exists", 
                    fileName, 
                    lineNumber
                );
            }

            try {
                student.setName(names[1].trim());
            } catch (Exception e) {
                throw new FileParsingException(
                    e.getMessage(),
                    fileName,
                    lineNumber
                );
            }
            lineNumber++;
        }
    }

    private void printOutputFile(PrintStream output) 
    {
        this.students.forEach((Long key, Student value) -> {
            String[] courses = value.formattedCourses();
            for (String course : courses)
            {
                output.format(course + "\n");
            }
        });
    }
}
