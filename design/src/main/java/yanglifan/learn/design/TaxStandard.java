package yanglifan.learn.design;

import java.math.BigDecimal;

public class TaxStandard implements Comparable<TaxStandard> {
    private BigDecimal value;
    private BigDecimal rate;
    private BigDecimal base;

    public TaxStandard(BigDecimal value, BigDecimal rate, BigDecimal base) {
        this.value = value;
        this.rate = rate;
        this.base = base;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getBase() {
        return base;
    }

    @Override
    public int compareTo(TaxStandard o) {
        return this.value.compareTo(o.value);
    }
}
