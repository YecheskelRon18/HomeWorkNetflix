import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Recommendation {

    public static Dataset loadCsv(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String headerLine = br.readLine();
            String ratingLine = br.readLine();
            if (headerLine == null || ratingLine == null) throw new IllegalArgumentException();

            String[] headers = headerLine.split(",");
            String[] ratingRow = ratingLine.split(",");

            List<String> categoryNames = new ArrayList<>();
            for (int i = 1; i < headers.length; i++) categoryNames.add(headers[i]);

            double[] ratings = new double[categoryNames.size()];
            for (int i = 1; i < ratingRow.length; i++) ratings[i - 1] = Double.parseDouble(ratingRow[i]);

            List<Movie> movies = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] category = new double[categoryNames.size()];
                for (int i = 1; i < parts.length; i++) category[i - 1] = Double.parseDouble(parts[i]);
                movies.add(new Movie(parts[0], category));
            }

            return new Dataset(categoryNames, ratings, movies);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double calculateSimilarity(double[] selectedMovie, double[] otherMovie, double[] ratingArr) {

        double similaritySum = 0;
        double selectedLength = 0;
        double otherLength = 0;

        for (int i = 0; i < selectedMovie.length; i++) {

            similaritySum += ratingArr[i] * selectedMovie[i] * otherMovie[i];

            selectedLength += ratingArr[i] * selectedMovie[i] * selectedMovie[i];

            otherLength += ratingArr[i] * otherMovie[i] * otherMovie[i];
        }

        if (selectedLength == 0 || otherLength == 0)
        {
            return 0;
        }

        return similaritySum / (Math.sqrt(selectedLength) * Math.sqrt(otherLength));
    }

    public static List<Movie> getRecommendationsOfMovies(List<Movie> movies, double[] category, double[] ratingArr, int k)
    {
        List<Movie> copy = new ArrayList<>(movies);

        for (int i = 0; i < copy.size(); i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                double simI = calculateSimilarity(category, copy.get(i).getCategory(), ratingArr);
                double simJ = calculateSimilarity(category, copy.get(j).getCategory(), ratingArr);

                if (simJ > simI) {
                    Movie temp = copy.get(i);
                    copy.set(i, copy.get(j));
                    copy.set(j, temp);
                }
            }
        }

        k = Math.min(Math.max(k, 0), copy.size());
        return new ArrayList<>(copy.subList(0, k));
    }


    public static List<Movie> getRecommendationsOfMovies(List<Movie> movies, Movie selectedMovie, double[] ratingArr, int k) {
        List<Movie> copy = new ArrayList<>();

        for (Movie m : movies) {
            if (!m.getTitle().equals(selectedMovie.getTitle())) {
                copy.add(m);
            }
        }

        for (int i = 0; i < copy.size(); i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                double simI = calculateSimilarity(selectedMovie.getCategory(), copy.get(i).getCategory(), ratingArr);
                double simJ = calculateSimilarity(selectedMovie.getCategory(), copy.get(j).getCategory(), ratingArr);

                if (simJ > simI) {
                    Movie temp = copy.get(i);
                    copy.set(i, copy.get(j));
                    copy.set(j, temp);
                }
            }
        }

        k = Math.min(Math.max(k, 0), copy.size());
        return new ArrayList<>(copy.subList(0, k));
    }
}
