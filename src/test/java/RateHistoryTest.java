import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateHistoryTest {
    @Test
    public void RateHistoryScenario1() {
        RateHistory rh = new RateHistory();
        rh.SetCSV("src/test/resources/History2.csv");
        double[] exp = {5.105,0.21,10.0,5.105,4.895};
        double[] ans =  rh.doOperation("2022/09/15","AUD","CNY","2022/09/17");
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],ans[i]);
        }
    }

    @Test
    public void RateHistoryScenario2() {
        RateHistory rh = new RateHistory();
        rh.SetCSV("src/test/resources/History1.csv");
        double[] ans = rh.doOperation("2022/09/15","AUD","CNY","2022/09/22");
        double[] exp = {0.21, 0.21, 0.21, 0.21, 0.0};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],ans[i]);
        }
    }
    @Test
    public void RateHistoryScenario3() {
        RateHistory rh = new RateHistory();
        rh.SetCSV("src/test/resources/a.csv");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        double[] ans = rh.doOperation("2022/09/15","AUD","CNY","2022/09/18");
        assertEquals(null,ans);
    }
}
