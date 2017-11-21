package yanglifan.learn.design;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TaxCalculatorTest {
    private TaxCalculator taxCalculator;

    @Before
    public void setUp() throws Exception {
        taxCalculator = new TaxCalculator();
        taxCalculator.addTaxStandard(new TaxStandard(BigDecimal.valueOf(2200), BigDecimal.valueOf(0.0), BigDecimal.valueOf(0)));
        taxCalculator.addTaxStandard(new TaxStandard(BigDecimal.valueOf(2700), BigDecimal.valueOf(0.14), BigDecimal.valueOf(0)));
        taxCalculator.addTaxStandard(new TaxStandard(BigDecimal.valueOf(3200), BigDecimal.valueOf(0.15), BigDecimal.valueOf(70)));
        taxCalculator.addTaxStandard(new TaxStandard(BigDecimal.valueOf(102200), BigDecimal.valueOf(0.69), BigDecimal.valueOf(100000)));
        taxCalculator.addTaxStandard(new TaxStandard(BigDecimal.valueOf(Long.MAX_VALUE), BigDecimal.valueOf(0.7), BigDecimal.valueOf(53090)));
    }

    @Test
    public void calculate() throws Exception {
        assertThat(taxCalculator.calculate(BigDecimal.valueOf(2100)), is(BigDecimal.ZERO));
        assertThat(taxCalculator.calculate(BigDecimal.valueOf(2200)), is(BigDecimal.ZERO));
        assertThat(taxCalculator.calculate(BigDecimal.valueOf(2700)), is(BigDecimal.valueOf(70)));
        assertThat(taxCalculator.calculate(BigDecimal.valueOf(200000)), is(BigDecimal.valueOf(121550))); // 121550
    }

}