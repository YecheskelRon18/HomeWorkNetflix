
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    @Test
    void testIdenticalVectors(){
        assertEquals(1.0,
                Recommendation.calculateSimilarity(
                        new double[]{1,2,3},
                        new double[]{1,2,3},
                        new double[]{1,1,1}),0.0001);
    }

    @Test
    void testOrthogonalVectors(){
        assertEquals(0.0,
                Recommendation.calculateSimilarity(
                        new double[]{1,0},
                        new double[]{0,1},
                        new double[]{1,1}),0.0001);
    }

    @Test
    void testWeightedSimilarity(){
        double s1=Recommendation.calculateSimilarity(new double[]{1,1},new double[]{1,0},new double[]{0.5,0.5});
        double s2=Recommendation.calculateSimilarity(new double[]{1,1},new double[]{1,0},new double[]{0.99,0.01});
        assertTrue(s2>s1);
    }

    @Test
    void testLengthMismatch(){
        assertThrows(IllegalArgumentException.class,()->
                Recommendation.calculateSimilarity(
                        new double[]{1,2,3},
                        new double[]{1,2},
                        new double[]{1,1,1}));
    }
}
