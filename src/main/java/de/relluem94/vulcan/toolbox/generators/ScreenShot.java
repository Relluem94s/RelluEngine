package de.relluem94.vulcan.toolbox.generators;

import static de.relluem94.vulcan.toolbox.Variables.ScreenshotDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.Utils;

public class ScreenShot {

    private static int i, r, g, b;
    private static File file;
    private static BufferedImage image;
    private static ByteBuffer buffer;
    private static Runnable r1;

    public void takeScreenshot() {
        int width = Display.getWidth();
        int height = Display.getHeight();

        GL11.glReadBuffer(GL11.GL_FRONT);
        buffer = BufferUtils.createByteBuffer(width * height * 3);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
        file = new File(ScreenshotDir.getPath() + "/" + Utils.ScreenshotName() + "." + Main.screenshot_format.toLowerCase());
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Utils.log("Screenshot taken: " + file.getPath(), 0);

        r1 = new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        i = (x + (width * y)) * 3;
                        r = buffer.get(i) & 0xFF;
                        g = buffer.get(i + 1) & 0xFF;
                        b = buffer.get(i + 2) & 0xFF;
                        image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
                    }
                }
                try {
                    ImageIO.write(image, Main.screenshot_format, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        Main.executorService.submit(r1);
    }
}
