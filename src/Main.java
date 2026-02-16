import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Dataset dataset = Recommendation.loadCsv("src/movies.csv");

        while (true) {
            System.out.println("For movie lists choose: 1");
            System.out.println("For find movie from preferences, choose: 2");
            System.out.println("For stop Program choose: 3");

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

    public static void MoviesOption(Scanner scanner, Dataset dataset)
    {

        List<Movie> movieList = dataset.getMovies();

        for (int i = 0; i < movieList.size(); i++)
        {
            System.out.println(i + " " + movieList.get(i).getTitle());
        }

        int index = Integer.parseInt(scanner.nextLine());
        Movie selectedMovie = movieList.get(index);

        List<Movie> recommendationList = Recommendation.getRecommendationsOfMovies(movieList, selectedMovie, dataset.getRatings(), 3);

        for (int i = 0; i < recommendationList.size(); i++)
        {
            System.out.println(recommendationList.get(i).getTitle());
        }
    }

    public static void PreferenceOption(Scanner scanner, Dataset dataset)
    {

        int categoryCount = dataset.getCategoryNames().size();
        double[] category = new double[categoryCount];

        for (int i = 0; i < categoryCount; i++)
        {
            System.out.print(dataset.getCategoryNames().get(i) + ": ");
            category[i] = Integer.parseInt(scanner.nextLine()) / 5.0;
        }

        List<Movie> recommendationList = Recommendation.getRecommendationsOfMovies(dataset.getMovies(), category, dataset.getRatings(), 3);

        for (int i = 0; i < recommendationList.size(); i++)
        {
            System.out.println(recommendationList.get(i).getTitle());
        }
    }
}
