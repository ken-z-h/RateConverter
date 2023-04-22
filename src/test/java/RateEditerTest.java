import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateEditerTest {
    @Test
    public void RateEditertest1(){
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/a.csv");
        rw.setHistory_write_path("src/test/resources/b.csv");
        ArrayList<String> testString = new ArrayList<>();
        testString.add("AUD");
        testString.add("SGD");
        testString.add("US");
        testString.add("EU");
        testString.add("HKD");
        testString.add("CNY");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        rw.rateEditer("EU",testString,"2022/09/22");
        assertTrue(outContent.toString().contains("File Read Error"));
    }
    @Test
    public void RateEditertest2(){
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/CurrenciesRate.csv");
        rw.setHistory_write_path("src/test/resources/History.csv");
        ArrayList<String> testString = new ArrayList<>();
        testString.add("9");
        testString.add("10");
        testString.add("11");
        testString.add("23");
        testString.add("44");
        testString.add("22");

        assertEquals(0,rw.rateEditer("AUD",testString,"2022/09/22"));
    }

    @Test
    public void RateEditertest3(){
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/CurrenciesRate.csv");
        rw.setHistory_write_path("src/test/resources/History.csv");
        ArrayList<String> testString = new ArrayList<>();
        testString.add("9");
        testString.add("10");
        testString.add("11");
        testString.add("23");
        testString.add("44");
        testString.add("22");
        testString.add("99");
        testString.add("100");

        assertEquals(0,rw.rateEditer("US",testString,"2022/09/22"));
    }

    @Test
    public void RateEditertest4(){
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/CurrenciesRate.csv");
        rw.setHistory_write_path("src/test/resources/History.csv");
        ArrayList<String> testString = new ArrayList<>();

        assertEquals(0,rw.rateEditer("EU",testString,"2022/09/22"));
    }

    @Test
    public void RateEditertest5(){
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/CurrenciesRate.csv");
        rw.setHistory_write_path("src/test/resources/History.csv");
        ArrayList<String> testString = new ArrayList<>();
        testString.add("AUD");
        testString.add("SGD");
        testString.add("US");
        testString.add("EU");
        testString.add("HKD");
        testString.add("CNY");

        assertEquals(0,rw.rateEditer("JPY",testString,"2022/09/22"));
    }

    @Test
    public void RateEditertest6(){
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/InitialExchangeRate.csv");
        rw.setHistory_write_path("src/test/resources/History.csv");
        ArrayList<String> testString = new ArrayList<>();
        testString.add("9");
        testString.add("10");
        testString.add("11");
        testString.add("23");
        testString.add("44");
        testString.add("22");

        assertEquals(0,rw.rateEditer("JPY",testString,"2022/09/22"));
    }
}
