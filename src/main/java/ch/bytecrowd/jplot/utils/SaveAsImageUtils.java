package ch.bytecrowd.jplot.utils;

import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class SaveAsImageUtils {

    private SaveAsImageUtils() {

    }

    public static void saveAsImage(Stage stage, Chart chart) {
        var fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser
                .ExtensionFilter("image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        var file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Double width = chart.getWidth();
            Double height = chart.getHeight();
            var image = new WritableImage(width.intValue(), height.intValue());
            chart.snapshot(null, image);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            try {
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
