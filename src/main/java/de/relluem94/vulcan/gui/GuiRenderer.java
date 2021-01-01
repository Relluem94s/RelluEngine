package de.relluem94.vulcan.gui;

import static de.relluem94.vulcan.main.Things.guiTextures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.RawModel;
import de.relluem94.vulcan.toolbox.maths.Maths;

public class GuiRenderer {

    private final RawModel quad;
    private GuiShader shader;

    public GuiRenderer() {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = Main.loader.loadToVAO(positions, 2);
        shader = new GuiShader();
    }

    public void render() {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for (GuiTexture gui : guiTextures) {
            if (gui.isVisible()) {
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
                Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
                shader.loadTransformation(matrix);
                GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
            }
        }
        for (GuiElement gui : Main.guiElements) {
            if (gui.isVisible()) {
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
                Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
                shader.loadTransformation(matrix);
                GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
            }
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public void cleanUp() {
        shader.cleanUp();
    }
}
