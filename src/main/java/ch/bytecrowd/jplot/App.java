package ch.bytecrowd.jplot;

import ch.bytecrowd.jplot.utils.CsvUtils;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) {
        ParamExtractor paramExtractor = new ParamExtractor(getParameters());
        String pathToCsv = paramExtractor.extractPathToCsv();

        if (pathToCsv != null && !pathToCsv.isBlank()) {
            var data = CsvUtils.readDataFromCSV(pathToCsv);
            new ChartPlotter()
                    .data(data)
                    .title(paramExtractor.extractTitle())
                    .chartType(paramExtractor.extractChartType())
                    .xAxisRotation(paramExtractor.extractXAxisRotation())
                    .xAxisLabel(paramExtractor.extractXAxisLabel())
                    .yAxisLabel(paramExtractor.extractYAxisLabel())
                    .plot(stage);
        } else {
            System.out.println(
"""
Usage: java -jar jplot.jar --csv=/path/to/csv

Optional parameters:
    --title="My Fancy Title"
    --chartType=[LINE_CHART (DEFAULT), BAR_CHART, SCATTER_CHART, AREA_CHART, BUBBLE_CHART]
    --xAxisRotation=45.0
    --xAxisLabel="X"
    --yAxisLabel="Y"
""");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ParamExtractor {

    private static final String CSV_KEY = "csv";
    private static final String TITLE_KEY = "title";
    private static final String CHART_TYPE_KEY = "type";
    private static final String X_AXIS_ROTATION_KEY = "xAxisRotation";
    private static final String X_AXIS_LABEL_KEY = "xAxisLabel";
    private static final String Y_AXIS_LABEL_KEY = "yAxisLabel";

    private final Application.Parameters parameters;

    public ParamExtractor(Application.Parameters parameters) {
        this.parameters = parameters;
    }

    public String extractPathToCsv() {
        return parameters.getNamed().get(CSV_KEY);
    }

    public String extractTitle() {
        return Optional.ofNullable(this.parameters.getNamed().get(TITLE_KEY))
                .orElse(ChartPlotter.DEFAULT_TITLE);
    }

    public ChartType extractChartType() {
        return Optional.ofNullable(this.parameters.getNamed().get(CHART_TYPE_KEY))
                .map(type -> ChartType.valueOf(type))
                .orElse(ChartPlotter.DEFAULT_CHART_TYPE);
    }

    public Double extractXAxisRotation() {
        return Optional.ofNullable(this.parameters.getNamed().get(X_AXIS_ROTATION_KEY))
                .map(Double::valueOf)
                .orElse(ChartPlotter.DEFAULT_X_AXIS_ROTATION);
    }

    public String extractXAxisLabel() {
        return Optional.ofNullable(this.parameters.getNamed().get(X_AXIS_LABEL_KEY))
                .orElse(ChartPlotter.DEFAULT_X_AXIS_LABEL);
    }

    public String extractYAxisLabel() {
        return Optional.ofNullable(this.parameters.getNamed().get(Y_AXIS_LABEL_KEY))
                .orElse(ChartPlotter.DEFAULT_Y_AXIS_LABEL);
    }
}