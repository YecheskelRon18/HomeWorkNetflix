import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Recommendation.Dataset dataset = Recommendation.loadCsv("src/movies.csv");

        while (true) {

            System.out.println("Movie Lists choose: 1");
            System.out.println("Choose Preferences, choose: 2");
            System.out.println("Stop Program choose: 3");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    MoviesOption(scanner, dataset);
                    break;

                case 2:
                    PreferenceOption(scanner, dataset);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public static void MoviesOption(Scanner scanner, Recommendation.Dataset dataset) {

        List<Recommendation.Movie> movieList = dataset.getMovies();

        for (int i = 0; i < movieList.size(); i++)
            System.out.println(i + " " + movieList.get(i).getTitle());

        int index = Integer.parseInt(scanner.nextLine());
        Recommendation.Movie selectedMovie = movieList.get(index);

        List<Recommendation.Movie> recommendationList =
                Recommendation.getRecommendationsOfMovies(movieList, selectedMovie, dataset.getRatings(), 3);

        for (int i = 0; i < recommendationList.size(); i++)
            System.out.println(recommendationList.get(i).getTitle());
    }

    public static void PreferenceOption(Scanner scanner, Recommendation.Dataset dataset) {

        int CategoryCount = dataset.getCategoryNames().size();
        double[] Category = new double[CategoryCount];

        for (int i = 0; i < CategoryCount; i++) {
            System.out.print(dataset.getCategoryNames().get(i) + ": ");
            Category[i] = Integer.parseInt(scanner.nextLine()) / 5.0;
        }

        List<Recommendation.Movie> recommendationList =
                Recommendation.getRecommendationsOfMovies(dataset.getMovies(), Category, dataset.getRatings(), 3);

        for (int i = 0; i < recommendationList.size(); i++)
            System.out.println(recommendationList.get(i).getTitle());
    }
}
