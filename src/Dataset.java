import java.util.List;

public class Dataset {
    private final List<String> categoryNames;
    private final double[] ratings;
    private final List<Movie> movies;

    public Dataset(List<String> categoryNames, double[] ratings, List<Movie> movies) {
        this.categoryNames = categoryNames;
        this.ratings = ratings;
        this.movies = movies;
    }

    public List<String> getCategoryNames()
    {
        return categoryNames;
    }
    public double[] getRatings()
    {
        return ratings;
    }
    public List<Movie> getMovies()
    {
        return movies;
    }
}

