package de.relluem94.vulcan.renderEngine;

import org.lwjgl.opengl.GL11;

public class ImageRenderer {

    public ImageRenderer() {
    }

    public void renderQuad() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
    }
}
