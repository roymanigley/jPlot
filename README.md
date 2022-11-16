# jPlot

> With this Project you can plot following charts using a simple builder or the CLI
> - LINE_CHART
> - BAR_CHART
> - SCATTER_CHART
> - AREA_CHART
> - BUBBLE_CHART

> using right-click on the chart you can save it as PNG file  

> the data has to be hold in a Map (`Map<String, LinkedHashMap<Object, Number>>`)  
## Requrements
- JDK 17
- Maven

## Build & RUN

### Build

    mvn clean package

### Example usage of the CLI
    Usage: java -jar jplot.jar --csv=/path/to/csv
    
    Optional parameters:
        --title="My Fancy Title"
        --chartType=[LINE_CHART (DEFAULT), BAR_CHART, SCATTER_CHART, AREA_CHART, BUBBLE_CHART]
        --xAxisRotation=45.0
        --xAxisLabel="X"
        --yAxisLabel="Y"

### Example usage of `ch.bytecrowd.jplot.ChartPlotter`

        var xyValues = new LinkedHashMap<Object, Number>();
        xyValues.put(1, 1);
        xyValues.put(2, 2);
        xyValues.put(3, 3);
        xyValues.put(4, 4);
        
        var data = Map.of(
                "A", xyValues,
                ...
        );

        new ch.bytecrowd.jplot.ChartPlotter()
                .data(data)                                         // required
                .chartType(ch.bytecrowd.jplot.ChartType.LINE_CHART) // optional
                .title("My Fancy Chart")                            // optional
                .xAxisRotation(45)                                  // optional
                .xAxisLabel("X")                                    // optional
                .yAxisLabel("Y")                                    // optional
                .plot(stage);