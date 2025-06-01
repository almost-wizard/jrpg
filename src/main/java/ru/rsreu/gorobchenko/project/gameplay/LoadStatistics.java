package ru.rsreu.gorobchenko.project.gameplay;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ru.rsreu.gorobchenko.project.shared.utils.NodeUtils;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;

public class LoadStatistics {
    private static final String TEMPLATE = "fps: %.2f\n1 frame render time: %.3f ms";
    private static final Text fpsCounterText = TextFactory.create(TEMPLATE, TextSize.M);

    public static void initUI() {
        fpsCounterText.setTextAlignment(TextAlignment.CENTER);
        FXGL.addUINode(fpsCounterText, 0, FXGL.getAppHeight() - fpsCounterText.getBoundsInLocal().getHeight() - 10);
        NodeUtils.bindXCenter(fpsCounterText, FXGL.getAppWidth());
    }

    public static void updateUI() {
        String result = String.format(TEMPLATE, 1.0 / FXGL.tpf(), FXGL.cpuNanoTime() / 1000000.0);
        fpsCounterText.setText(result);
    }
}
