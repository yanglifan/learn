package yanglifan.learn.design;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaxCalculator {
    private List<TaxStandard> taxStandardList = new ArrayList<>();

    public BigDecimal calculate(BigDecimal income) {
        if (income.compareTo(taxStandardList.get(0).getValue()) <= 0) {
            return BigDecimal.ZERO;
        }

        for (int i = 1; i < taxStandardList.size(); i++) {
            if (income.compareTo(taxStandardList.get(i).getValue()) == 1) {
                continue;
            }

            return income
                    .subtract(taxStandardList.get(i - 1).getValue())
                    .multiply(taxStandardList.get(i).getRate())
                    .add(taxStandardList.get(i).getBase())
                    .setScale(0, BigDecimal.ROUND_DOWN);
        }

        throw new RuntimeException("Should not reach here");
    }

    public void addTaxStandard(TaxStandard taxStandard) {
        this.taxStandardList.add(taxStandard);
        Collections.sort(taxStandardList);
    }
}
