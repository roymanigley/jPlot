package ch.bytecrowd.jplot;

import ch.bytecrowd.jplot.utils.ChartZoomUtils;
import ch.bytecrowd.jplot.utils.SaveAsImageUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChartPlotter {

    static final String DEFAULT_TITLE = "";
    static final ChartType DEFAULT_CHART_TYPE = ChartType.LINE_CHART;
    static final double DEFAULT_X_AXIS_ROTATION = 0;
    static final String DEFAULT_X_AXIS_LABEL = "X";
    static final String DEFAULT_Y_AXIS_LABEL = "Y";

    private final Map<String, Map<Object, Number>> data = new LinkedHashMap<>();
    private XYChart chart;
    private ChartType chartType = DEFAULT_CHART_TYPE;
    private String title = DEFAULT_TITLE;
    private double xAxisRotation = DEFAULT_X_AXIS_ROTATION;
    private String xAxisLabel = DEFAULT_X_AXIS_LABEL;
    private String yAxisLabel = DEFAULT_Y_AXIS_LABEL;

    public ChartPlotter chartType(ChartType chartType) {
        this.chartType = chartType;
        return this;
    }

    public ChartPlotter title(String title) {
        this.title = title;
        return this;
    }

    public ChartPlotter xAxisRotation(double xAxisRotation) {
        this.xAxisRotation = xAxisRotation;
        return this;
    }

    public ChartPlotter xAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
        return this;
    }

    public ChartPlotter yAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
        return this;
    }

    public ChartPlotter data(Map<String, LinkedHashMap<Object, Number>> data) {
        this.data.putAll(data);
        return this;
    }

    public void plot(Stage stage) {
        this.chart = createChart();
        this.chart.setTitle(title);
        fillChart();
        ChartZoomUtils.makeChartZoomable(chart);
        var contextMenu = createContextMenu(stage, chart);
        chart.setOnMouseClicked(event -> {
            if (MouseButton.SECONDARY.equals(event.getButton())){
                contextMenu.show(stage, event.getSceneX(), event.getSceneY());
            }
        });
        final var scene = new Scene(chart);
        stage.setScene(scene);
        stage.show();
    }

    private XYChart createChart() {
        final Axis xAxis;
        if (isXAxisInstanceOfNumber()) {
            xAxis =  new NumberAxis();
        } else {
            xAxis =  new CategoryAxis();
        }
        xAxis.setTickLabelRotation(xAxisRotation);
        final var yAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);

        return this.chartType.createCart(xAxis, yAxis);
    }

    private boolean isXAxisInstanceOfNumber() {
        return data.entrySet().stream()
                .findFirst()
                .map(Map.Entry::getValue)
                .flatMap(entry -> entry.entrySet().stream().findFirst())
                .filter(o -> o.getKey() instanceof Number)
                .isPresent();
    }

    private void fillChart() {
        for (var entry : this.data.entrySet()) {
            final var series = new XYChart.Series<String, Number>();
            series.setName(entry.getKey());
            for (var data: entry.getValue().entrySet()) {
                series.getData().add(new XYChart.Data(data.getKey(), data.getValue()));
            }
            chart.getData().add(series);
        }
    }

    private ContextMenu createContextMenu(Stage stage, Chart chart) {
        var contextMenu = new ContextMenu();
        var menuItemSave = new MenuItem();
        menuItemSave.setText("Save");
        menuItemSave.setOnAction(event -> {
            SaveAsImageUtils.saveAsImage(stage, chart);
        });
        contextMenu.getItems().add(
                menuItemSave
        );
        return contextMenu;
    }
}
