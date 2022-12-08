import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        FileFormatter file = new FileFormatter();
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Enter course file: ");
        String courseFileName = keyboard.nextLine();

        System.out.print("Enter name file: ");
        String nameFileName = keyboard.nextLine();

        System.out.print("Enter output file: ");
        String outputFileName = keyboard.nextLine();

        keyboard.close();

        File courseFile = new File(courseFileName);
        File nameFile = new File(nameFileName);
        File outputFile = new File(outputFileName);

        if (!courseFile.isFile())
        {
            throw new FileNotFoundException("course file doesn't exist");
        }

        if (!nameFile.isFile())
        {
            throw new FileNotFoundException("name file doesn't exist");
        }

        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.run(courseFile, nameFile, outputFile);
    }
}
