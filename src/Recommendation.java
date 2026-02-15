import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class Recommendation {

    public static class Movie {
        private String title;
        private double[] Category;

        public Movie(String title, double[] Category) {
            this.title = title;
            this.Category = Category;
        }

        public String getTitle() { return title; }
        public double[] getCategory() { return Category; }
    }

    public static class Dataset {
        private List<String> CategoryNames;
        private double[] Ratings;
        private List<Movie> movies;

        public Dataset(List<String> CategoryNames, double[] Ratings, List<Movie> movies) {
            this.CategoryNames = CategoryNames;
            this.Ratings = Ratings;
            this.movies = movies;
        }

        public List<String> getCategoryNames() { return CategoryNames; }
        public double[] getRatings() { return Ratings; }
        public List<Movie> getMovies() { return movies; }
    }

    public static Dataset loadCsv(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String headerLine = br.readLine();
            String ratingLine = br.readLine();
            if (headerLine == null || ratingLine == null) throw new IllegalArgumentException();

            String[] headers = headerLine.split(",");
            String[] ratingRow = ratingLine.split(",");

            List<String> CategoryNames = new ArrayList<String>();
            for (int i = 1; i < headers.length; i++) CategoryNames.add(headers[i]);

            double[] Ratings = new double[CategoryNames.size()];
            for (int i = 1; i < ratingRow.length; i++) Ratings[i - 1] = Double.parseDouble(ratingRow[i]);

            List<Movie> movies = new ArrayList<Movie>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] Category = new double[CategoryNames.size()];
                for (int i = 1; i < parts.length; i++) Category[i - 1] = Double.parseDouble(parts[i]);
                movies.add(new Movie(parts[0], Category));
            }

            br.close();
            return new Dataset(CategoryNames, Ratings, movies);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double calculateSimilarity(double[] a, double[] b, double[] RatingArr) {
        double dot = 0;
        double na = 0;
        double nb = 0;

        for (int i = 0; i < a.length; i++) {
            dot += RatingArr[i] * a[i] * b[i];
            na += RatingArr[i] * a[i] * a[i];
            nb += RatingArr[i] * b[i] * b[i];
        }

        if (na == 0 || nb == 0) return 0;
        return dot / (Math.sqrt(na) * Math.sqrt(nb));
    }

    public static List<Movie> getRecommendationsOfMovies(List<Movie> movies, double[] Category, double[] RatingArr, int k) {
        List<Movie> copy = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) copy.add(movies.get(i));

        for (int i = 0; i < copy.size(); i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                double simI = calculateSimilarity(Category, copy.get(i).getCategory(), RatingArr);
                double simJ = calculateSimilarity(Category, copy.get(j).getCategory(), RatingArr);

                if (simJ > simI) {
                    Movie temp = copy.get(i);
                    copy.set(i, copy.get(j));
                    copy.set(j, temp);
                }
            }
        }

        if (k < 0) k = 0;
        if (k > copy.size()) k = copy.size();
        return copy.subList(0, k);
    }

    public static List<Movie> getRecommendationsOfMovies(List<Movie> movies, Movie selectedMovie, double[] RatingArr, int k) {
        List<Movie> copy = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i) != selectedMovie) copy.add(movies.get(i));
        }

        for (int i = 0; i < copy.size(); i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                double simI = calculateSimilarity(selectedMovie.getCategory(), copy.get(i).getCategory(), RatingArr);
                double simJ = calculateSimilarity(selectedMovie.getCategory(), copy.get(j).getCategory(), RatingArr);

                if (simJ > simI) {
                    Movie temp = copy.get(i);
                    copy.set(i, copy.get(j));
                    copy.set(j, temp);
                }
            }
        }

        if (k < 0) k = 0;
        if (k > copy.size()) k = copy.size();
        return copy.subList(0, k);
    }
}
