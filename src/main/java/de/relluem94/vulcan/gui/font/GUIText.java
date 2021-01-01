package de.relluem94.vulcan.gui.font;

import org.lwjgl.util.vector.Vector2f;

import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.toolbox.maths.Color3f;
import de.relluem94.vulcan.toolbox.maths.Vector3f;
import de.relluem94.vulcan.toolbox.maths.Vector4f;

/**
 * Represents a piece of text in the game.
 *
 * @author Karl
 *
 */
public class GUIText {

    private String textString;
    private float fontSize;

    private int textMeshVao;
    private int vertexCount;
    private Color3f colour;
    private Color3f outlineColour;

    private float borderWidth;
    private float width;
    private float borderEdge;
    private float edge;

    private Vector2f position;
    private float lineMaxSize;
    private int numberOfLines;
    private boolean isVisible = false;

    private FontType font;

    private boolean centerText = false;

    /**
     * Creates a new text, loads the text's quads into a VAO, and adds the text
     * to the screen.
     *
     * @param text - the text.
     * @param fontSize - the font size of the text, where a font size of 1 is
     * the default size.
     * @param font - the font that this text should use.
     * @param position - the position on the screen where the top left corner of
     * the text should be rendered. The top left corner of the screen is (0, 0)
     * and the bottom right is (1, 1).
     * @param maxLineLength - basically the width of the virtual page in terms
     * of screen width (1 is full screen width, 0.5 is half the width of the
     * screen, etc.) Text cannot go off the edge of the page, so if the text is
     * longer than this length it will go onto the next line. When text is
     * centered it is centered into the middle of the line, based on this line
     * length value.
     * @param centered - whether the text should be centered or not.
     */
    public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
            boolean centered) {
        this.textString = text;
        this.fontSize = fontSize;
        this.font = font;
        this.position = position;
        this.lineMaxSize = maxLineLength;
        this.centerText = centered;
        Main.guiText.add(this);
        TextMaster.loadText(this);
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setFontSize(float size) {
        this.fontSize = size;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setText(String text) {
        this.textString = text;
    }

    /**
     * Remove the text from the screen.
     */
    public void remove() {
        TextMaster.removeText(this);
    }

    /**
     * @return The font used by this text.
     */
    public FontType getFont() {
        return font;
    }

    public void setColour(Color3f color) {
        colour = color;
    }

    /**
     * Set the colour of the text.
     *
     * @param r - red value, between 0 and 1.
     * @param g - green value, between 0 and 1.
     * @param b - blue value, between 0 and 1.
     */
    public void setColour(float r, float g, float b, float a) {
        colour = new Color3f(r, g, b);
    }

    /**
     * Set the Border of the text.
     *
     * LagerText lower Edge Values higher Width
     *
     * @param borderwidth - 0 for no Effect.
     */
    public void set(float edge, float borderedge, float width, float borderwidth, Vector3f outlineColor, Vector3f color) {
        this.borderEdge = borderedge;
        this.borderWidth = borderwidth;
        this.edge = edge;
        this.width = width;
        outlineColour = new Color3f(outlineColor.x, outlineColor.y, outlineColor.z);
        colour = new Color3f(color.x, color.y, color.z);
    }

    public void set(float edge, float borderedge, float width, float borderwidth, Color3f outlineColor, Color3f color) {
        this.borderEdge = borderedge;
        this.borderWidth = borderwidth;
        this.edge = edge;
        this.width = width;
        this.outlineColour = outlineColor;
        this.colour = color;
    }

    /**
     * Set the Border of the text.
     *
     * LagerText lower Edge Values higher Width
     *
     * @param edges - borderEdge, edge.
     *
     * @param widths - borderWidth, width.
     *
     * @param outlineColor - Color3f * @param color - Color3f
     */
    public void set(Vector2f edges, Vector2f widths, Color3f outlineColor, Color3f color) {
        this.borderEdge = edges.x;
        this.edge = edges.y;
        this.borderWidth = widths.x;
        this.width = widths.y;

        this.outlineColour = outlineColor;
        this.colour = color;
    }

    /**
     * Set the Border of the text.
     *
     * LagerText lower Edge Values higher Width
     *
     * @param params - borderEdge, edge, borderWidth, width.
     *
     * @param outlineColor - Color3f.
     *
     * @param color - Color3f.
     */
    public void set(Vector4f params, Color3f outlineColor, Color3f color) {
        this.borderEdge = params.x;
        this.edge = params.y;
        this.borderWidth = params.z;
        this.width = params.w;

        this.outlineColour = outlineColor;
        this.colour = color;
    }

    public Color3f getOutlineColour() {
        return outlineColour;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public float getWidth() {
        return width;
    }

    public float getBorderEdge() {
        return borderEdge;
    }

    public float getEdge() {
        return edge;
    }

    /**
     * @return the colour of the text.
     */
    public Color3f getColour() {
        return colour;
    }

    /**
     * @return The number of lines of text. This is determined when the text is
     * loaded, based on the length of the text and the max line length that is
     * set.
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * @return The position of the top-left corner of the text in screen-space.
     * (0, 0) is the top left corner of the screen, (1, 1) is the bottom right.
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * @return the ID of the text's VAO, which contains all the vertex data for
     * the quads on which the text will be rendered.
     */
    public int getMesh() {
        return textMeshVao;
    }

    /**
     * Set the VAO and vertex count for this text.
     *
     * @param vao - the VAO containing all the vertex data for the quads on
     * which the text will be rendered.
     * @param verticesCount - the total number of vertices in all of the quads.
     */
    public void setMeshInfo(int vao, int verticesCount) {
        this.textMeshVao = vao;
        this.vertexCount = verticesCount;
    }

    /**
     * @return The total number of vertices of all the text's quads.
     */
    public int getVertexCount() {
        return this.vertexCount;
    }

    /**
     * @return the font size of the text (a font size of 1 is normal).
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * Sets the number of lines that this text covers (method used only in
     * loading).
     *
     * @param number
     */
    public void setNumberOfLines(int number) {
        this.numberOfLines = number;
    }

    /**
     * @return {@code true} if the text should be centered.
     */
    public boolean isCentered() {
        return centerText;
    }

    public void setCentered(boolean center) {
        centerText = center;
    }

    /**
     * @return The maximum length of a line of this text.
     */
    public float getMaxLineSize() {
        return lineMaxSize;
    }

    /**
     * @return The string of text.
     */
    public String getTextString() {
        return textString;
    }

    public void replace(String string, float number, FontType font, Vector2f vector2f, float number2, boolean bool) {
        TextMaster.removeText(this);
        this.textString = string;
        this.font = font;
        this.position = vector2f;
        this.centerText = bool;
        TextMaster.loadText(this);
    }

}
