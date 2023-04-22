import org.apache.commons.io.input.ReversedLinesFileReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PopularTest {
    @Test
    public void PopularTest1(){
        ArrayList<String> ans = new ArrayList<>();
        ans.add("AUD");
        ans.add("SGD");
        ans.add("US");
        ans.add("EU");
        assertEquals(ans, readfile.popularCurrency("src/test/resources/PopularCurrencies.csv"));
    }
    @Test
    public void PopCWriter2() throws Exception {
        PopCWriter PC = new PopCWriter();
        ArrayList<String> testString = new ArrayList<>();
        testString.add("AUD");
        testString.add("SGD");
        testString.add("US");
        testString.add("EU");
        PC.setPath("src/test/resources/PopularCurrencies.csv");
        PC.PopWriter(testString);

        ArrayList<String> test = new ArrayList<>();
        test.add("currency"+ "3" + ","+"EU");
        test.add("currency"+ "2" + ","+"US");
        test.add("currency"+ "1" + ","+"SGD");
        test.add("currency"+ "0" + ","+"AUD");

        File file = new File("src/test/resources/PopularCurrencies.csv");
        int n_lines = 4;
        int counter = 1;
        ArrayList<String> CsvString = new ArrayList<>();

        ReversedLinesFileReader object = new ReversedLinesFileReader(file, 4096, "UTF-8");
        while (counter <= n_lines) {
            String a = object.readLine();
            CsvString.add(a);
            counter++;
        }
        assertEquals(test,CsvString);
    }

    @Test
    public void PopCWriterCatchErrorTest1() throws Exception {
        final InputStream oldIn = System.in;
        final PrintStream oldOut = System.out;
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        PopCWriter PC = new PopCWriter();
        PC.setPath("src/test/resources/1.csv");
        assertEquals(-1,PC.PopWriter(new ArrayList<>()));
        assertTrue(outContent.toString().contains("File Read Error"));
        System.setIn(oldIn);
        System.setOut(oldOut);
    }
}
