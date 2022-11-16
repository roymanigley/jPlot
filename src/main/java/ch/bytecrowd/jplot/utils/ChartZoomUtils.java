package ch.bytecrowd.jplot.utils;

import javafx.scene.chart.Chart;

public final class ChartZoomUtils {

    private static final double SCALE_DELTA = 1.1;

    private ChartZoomUtils() {

    }

    public static void makeChartZoomable(Chart chart) {
        chart.setOnScroll(event -> {
            event.consume();
            if (event.getDeltaY() == 0) {
                return;
            }
            var scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
            chart.setScaleX(chart.getScaleX() * scaleFactor);
            chart.setScaleY(chart.getScaleY() * scaleFactor);
        });

        chart.setOnMousePressed(event -> {
            if (event.getClickCount() == 2) {
                chart.setScaleX(1.0);
                chart.setScaleY(1.0);
            }
        });
    }
}
