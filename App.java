public class App {
    public static void main(String[] args) {
        // For testing purposes only
        {
            Student saara = new Student("Saara Beattie", 237711821);
            saara.addCourse("PS262", new float[]{56, 96, 75, 95});
            saara.addCourse("MA370", new float[]{57, 75, 55, 73});
            String[] results = saara.formattedCourses();
            for (String result : results)
            {
                System.out.println(result);
            }
        }
    }
}
