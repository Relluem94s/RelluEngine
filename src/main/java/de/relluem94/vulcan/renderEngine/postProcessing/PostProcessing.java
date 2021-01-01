package de.relluem94.vulcan.renderEngine.postProcessing;

import static de.relluem94.vulcan.main.Main.fbo_a;
import static de.relluem94.vulcan.main.Main.fbo_b;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.models.RawModel;

public class PostProcessing {

    private static final float[] POSITIONS = {-1, 1, -1, -1, 1, 1, 1, -1};
    private static RawModel quad;

    private static PostProcess pp1;
    private static PostProcess pp2;
    private static PostProcess pp3;
    private static PostProcess pp4;
    private static PostProcess pp5;
    private static PostProcess pp6;

    public static boolean pong_fbo = false;
    public static final int length = 6;
    public static final int ids = 9;
    public static int colourTexture;
    private static int[] postid;

    /**
     *
     * @param id <br>
     * 0 = Normal<br>
     * 1 = Vignette<br>
     * 2 = Contrast<br>
     * 3 = Color<br>
     * 4 = Underwater(not waving)<br>
     * 5 = Greyscale<br>
     * 6 = Sepia<br>
     * 7 = Blur<br>
     * 8 = DOF (not finished)<br>
     * 9 = GaussianBlur Horizontal<br>
     * 10 = GaussianBlur Vertical<br>
     */
    public static void init(int[] id) {
        postid = id;
        quad = Main.loader.loadToVAO(POSITIONS, 2);
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                pp1 = new PostProcess(id[i]);
            } else if (i == 1) {
                pp2 = new PostProcess(id[i]);
            } else if (i == 2) {
                pp3 = new PostProcess(id[i]);
            } else if (i == 3) {
                pp4 = new PostProcess(id[i]);
            } else if (i == 4) {
                pp5 = new PostProcess(id[i]);
            } else if (i == 5) {
                pp6 = new PostProcess(id[i]);
            }
        }
    }

    /**
     *
     * @param id <br>
     * 0 = Normal<br>
     * 1 = Vignette<br>
     * 2 = Contrast<br>
     * 3 = Color<br>
     * 4 = Underwater(not waving)<br>
     * 5 = Greyscale<br>
     * 6 = Sepia<br>
     * 7 = Blur<br>
     * 8 = DOF (not finished)<br>
     * 9 = GaussianBlur Horizontal<br>
     * 10 = GaussianBlur Vertical<br>
     */
    public static void update(int[] id) {
        postid = id;
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                pp1.update(id[i]);
            } else if (i == 1) {
                pp2.update(id[i]);
            } else if (i == 2) {
                pp3.update(id[i]);
            } else if (i == 3) {
                pp4.update(id[i]);
            } else if (i == 4) {
                pp5.update(id[i]);
            } else if (i == 5) {
                pp6.update(id[i]);
            }
        }
    }

    public static int[] getPostIDs() {
        return postid;
    }

    public static void doPostProcessing(int colourTexture, int depthTexture) {
        start(colourTexture);

        bind();
        pp1.render(PostProcessing.colourTexture);
        unbind();

        bind();
        pp2.render(PostProcessing.colourTexture);
        unbind();

        bind();
        pp3.render(PostProcessing.colourTexture);
        unbind();

        bind();
        pp4.render(PostProcessing.colourTexture);
        unbind();

        bind();
        pp5.render(PostProcessing.colourTexture);
        unbind();

        pp6.render(PostProcessing.colourTexture);
        end();
    }

    public static void bind() {
        if (!pong_fbo) {
            fbo_b.bindFrameBuffer();
        } else {
            fbo_a.bindFrameBuffer();
        }
    }

    public static void unbind() {
        if (!pong_fbo) {
            fbo_b.unbindFrameBuffer();
            PostProcessing.colourTexture = fbo_b.getColourTexture();
            pong_fbo = !pong_fbo;
        } else {
            fbo_a.unbindFrameBuffer();
            PostProcessing.colourTexture = fbo_a.getColourTexture();
            pong_fbo = !pong_fbo;
        }
    }

    public static void cleanUp() {
        pp1.cleanUp();
        pp2.cleanUp();
        pp3.cleanUp();
        pp4.cleanUp();
        pp5.cleanUp();
        pp6.cleanUp();
    }

    private static void start(int colourTexture) {
        pong_fbo = false;
        PostProcessing.colourTexture = colourTexture;
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    private static void end() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        fbo_a.unbindFrameBuffer();
        fbo_b.unbindFrameBuffer();
    }

}
