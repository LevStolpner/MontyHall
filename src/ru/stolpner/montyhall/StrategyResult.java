package ru.stolpner.montyhall;

import java.text.DecimalFormat;

public class StrategyResult {
    private final String strategyName;
    private final double successPercentage;

    public StrategyResult(String strategyName, double successPercentage) {
        this.strategyName = strategyName;
        this.successPercentage = successPercentage;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public double getSuccessPercentage() {
        return successPercentage;
    }

    @Override
    public String toString() {
        String formattedPercentage = new DecimalFormat("##.##%").format(successPercentage);
        return String.format("Strategy=%s. Success=%s", strategyName, formattedPercentage);
    }
}
