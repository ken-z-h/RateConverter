import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    @Test
    public void ConventerTest1(){
        Converter C = new Converter();
        String a = C.convertCurrency(99.999,"CNY","AUD","src/test/resources/InitialExchangeRate.csv");
        assertEquals("469.9953",a);
    }

    @Test
    public void ConventerTest2(){
        Converter C = new Converter();
        String a = C.convertCurrency(99.999,"CNY","CNY","src/test/resources/InitialExchangeRate.csv");
        assertEquals("-",a);
    }
}
