import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReadfileTest {
    @Test
    public void readfile1(){
        readfile r = new readfile();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ArrayList<String> rs = r.popularCurrency("1/csv");
        assertFalse(outContent.toString().contains("File write Error"));
    }

    @Test
    public void readfile2(){
        readfile r = new readfile();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        HashMap<String, HashMap<String, String>> rs = readfile.fileInfor("1/csv");
        assertFalse(outContent.toString().contains("File write Error"));
    }
}
