import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateWritertest {
    @Test
    public void RateWriter1() throws Exception {
        RateWriter rw = new RateWriter();
        rw.setpath("src/test/resources/CurrenciesRate.csv");
        rw.setHistory_write_path("src/test/resources/History.csv");
        ArrayList<String> testString = new ArrayList<>();
        testString.add("AUD");
        testString.add("EU");
        testString.add("CNY");
        rw.setCurcList(testString);
        HashMap<String, ArrayList<String>> has = new HashMap<>();
        ArrayList<String> AUD = new ArrayList<>();
        AUD.add("null");
        AUD.add("1.08");
        AUD.add("1.08");
        AUD.add("1.47");
        AUD.add("0.19");
        AUD.add("0.21");
        ArrayList<String> EU = new ArrayList<>();
        EU.add("1.08");
        EU.add("null");
        EU.add("1.47");
        ArrayList<String> CNY = new ArrayList<>();
        CNY.add("1.47");
        CNY.add("1.07");
        CNY.add("1.48");
        has.put("AUD",AUD);
        has.put("EU",EU);
        has.put("CNY",CNY);

        rw.setRateCurc(has);
        rw.rateWriter();
        ArrayList<String> real = new ArrayList<>();

        String file = "src/test/resources/CurrenciesRate.csv";
        File myObj = new File(file);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            real.add(data);
        }
        myReader.close();
        ArrayList<String> expect = new ArrayList<>();
        expect.add("AUD,EU,CNY");
        expect.add("EU,1.08,null");
        expect.add("CNY,1.47,1.07");
        assertEquals(expect,real);
    }

    @Test
    public void RateWriterCatch1() throws Exception {
        final PrintStream oldOut = System.out;
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RateWriter PC = new RateWriter();
        PC.setpath("src/test/resources/CurrenciesRate.csv");
        PC.setHistory_write_path("src/test/resources/History.csv");
        PC.rateWriter();
        assertTrue(outContent.toString().contains("File write Error"));
        System.setOut(oldOut);
    }

    @Test
    public void RateWriterCatch2() throws Exception {
        final PrintStream oldOut = System.out;
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RateWriter PC = new RateWriter();
        PC.setpath("src/test/resources/1.csv");
        PC.setHistory_write_path("src/test/resources/2.csv");
        PC.rateWriter();
        assertTrue(outContent.toString().contains("File write Error"));
        System.setOut(oldOut);
    }
}
