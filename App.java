import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    // Finish readCourseFile
    // Finish readNameFile
    // Figure out if name and id are printed without course
    // Finish main method

    private HashMap<Long, Student> students;

    public App()
    {
        this.students = new HashMap<Long, Student>();
    }

    public void run(Scanner courseFile, Scanner nameFile, PrintStream output)
    {
        this.readCourseFile(courseFile);
        this.readNameFile(nameFile);
        this.printOutputFile(output);
    }

    private void readCourseFile(Scanner courseFile)
    {
        // Exception in if statement in while loop
        // Try catch id conversion in while loop
        // Try catch grade conversions

        String line;
        String[] courses;
        long id;
        Student student;
        float[] marks;
        marks = new float[4];

        while (courseFile.hasNextLine())
        {
            line = courseFile.nextLine();
            courses = line.split(", ");
            if (courses.length != 6)
            {
                // Throw Exception
            }
            id = Long.parseLong(courses[0].trim());
            for (int i = 0; i < marks.length; i++)
            {
                marks[i] = Float.parseFloat(courses[i + 2].trim());
            }
            student = new Student(id);
            student.addCourse(courses[1].trim(), marks);
            this.students.put(id, student);
        }
    }

    private void readNameFile(Scanner file)
    {
        // Exception in if statement in while loop
        // Try Catch id conversion
        // Throw Exception if name already associated with id

        String line;
        String[] names;
        long id;
        Student student;

        while (file.hasNextLine())
        {
            line = file.nextLine();
            names = line.split(", ");
            if (names.length != 2)
            {
                // Throw Exception
            }
            id = Long.parseLong(names[0].trim());
            student = this.students.get(id);
            student.setName(names[1].trim());
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

    public static void main(String[] args) {
        // For testing purposes only
        {
            File courseFile = new File("CourseFile.txt");
            File nameFile = new File("NameFile.txt");
            File outputFile = new File("OutputFile.txt");
            Scanner courseFileScanner;
            Scanner nameFileScanner;
            PrintStream outputFilePrinter;
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                courseFileScanner = new Scanner(courseFile);
                nameFileScanner = new Scanner(nameFile);
                outputFilePrinter = new PrintStream(outputFile);
                App main = new App();
                main.run(
                    courseFileScanner, 
                    nameFileScanner, 
                    outputFilePrinter
                );
                System.out.println("Finished!");
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
}
