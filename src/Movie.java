public class Movie {
    private final String title;
    private final double[] category;

    public Movie(String title, double[] category)
    {
        this.title = title;
        this.category = category;
    }

    public String getTitle()
    {
        return title;
    }
    public double[] getCategory()
    {
        return category;
    }
}
