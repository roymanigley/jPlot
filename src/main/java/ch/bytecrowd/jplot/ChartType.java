package ch.bytecrowd.jplot;

import javafx.scene.chart.*;

import java.util.function.BiFunction;

public enum ChartType {
    LINE_CHART((xAxis, yAxis) -> new LineChart<>(xAxis, yAxis)),
    BAR_CHART((xAxis, yAxis) -> new BarChart<>(xAxis, yAxis)),
    SCATTER_CHART((xAxis, yAxis) -> new ScatterChart<>(xAxis, yAxis)),
    AREA_CHART((xAxis, yAxis) -> new AreaChart<>(xAxis, yAxis)),
    BUBBLE_CHART((xAxis, yAxis) -> new BubbleChart<>(xAxis, yAxis));

    private final BiFunction<Axis, Axis, XYChart> chartCreator;

    ChartType(BiFunction<Axis, Axis, XYChart> chartCreator) {
        this.chartCreator = chartCreator;
    }

    public XYChart createCart(Axis xAxis, Axis yAxis) {
        return chartCreator.apply(xAxis, yAxis);
    }
}