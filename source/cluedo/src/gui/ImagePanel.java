package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Jpanel that draws tiled background image.
 */
public class ImagePanel extends JPanel {
    private Image backgroundImage;
    private Image borderTL, borderTR, borderBL, borderBR, borderTop, borderBottom, borderLeft, borderRight;
    private boolean drawBorder, tile;
    private int borderSize;
    
    /** 
     * Constructor with basic information.
     * @param backgroundImage
     * @param tile
     * @param borderSize
     */
    public ImagePanel(Image backgroundImage, boolean tile, int borderSize) {
        this.backgroundImage = backgroundImage;
        drawBorder = false;
        this.tile = tile;
        this.borderSize = borderSize;
    }

    /**
     * Constructor with specific border information
     * @param backgroundImage
     * @param borderTL
     * @param borderTR
     * @param borderBL
     * @param borderBR
     * @param borderTop
     * @param borderBottom
     * @param borderLeft
     * @param borderRight
     * @param borderSize
     */
    public ImagePanel(Image backgroundImage, Image borderTL, Image borderTR, Image borderBL, Image borderBR, Image borderTop, Image borderBottom, Image borderLeft, Image borderRight, int borderSize) {
        this.backgroundImage = backgroundImage;
        this.borderTL = borderTL;
        this.borderTR = borderTR;
        this.borderBL = borderBL;
        this.borderBR = borderBR;
        this.borderTop = borderTop;
        this.borderBottom = borderBottom;
        this.borderLeft = borderLeft;
        this.borderRight = borderRight;
        drawBorder = true;
        tile = true;
        this.borderSize = borderSize;
    }

    /**
     * Sets the background image to the specified image
     */
    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
    }

    /**
     * Draws the necessary images and borders
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        if(tile) {
            drawBackgroundTiled(g);
            drawBorder(g);
        } else {
            drawBackgroundStretched(g);
        }
    }

    /**
     * Draws the background stretched. Uses one image and stretches it over the whole panel
     */
    private void drawBackgroundStretched(Graphics g) {
        if (backgroundImage == null ) return;

        // Draw tiled image
        Dimension dimensions = getSize();
        g.drawImage(backgroundImage, 0, 0, dimensions.width, dimensions.height, null, null);
    }

    /**
     * Draws the background as tiled, repeating the image until it fills the box
     */
    private void drawBackgroundTiled(Graphics g) {
        if (backgroundImage == null ) return;

        // Draw tiled image
        Dimension dimensions = getSize();
        for (int x = 0; x < dimensions.width; x += backgroundImage.getWidth(null)) {
            for (int y = 0; y < dimensions.height; y += backgroundImage.getHeight(null)) {
                g.drawImage(backgroundImage, x, y, null, null);
            }
        }
    }

    /**
     * Draws a border around the panel using the specified border images
     */
    private void drawBorder(Graphics g) {
        if(!drawBorder) return;

        Dimension dimensions = getSize();

        //top border
        for (int x = 0; x < dimensions.width; x += borderTop.getWidth(null)) {
            g.drawImage(borderTop, x, 0, borderTop.getWidth(null), borderSize,null, null);
        }

        //bottom border
        for (int x = 0; x < dimensions.width; x += borderBottom.getWidth(null)) {
            g.drawImage(borderBottom, x, dimensions.height - borderSize, borderBottom.getWidth(null), borderSize, null, null);
        }

        //left border
        for (int y = 0; y < dimensions.height; y += borderLeft.getHeight(null)) {
            g.drawImage(borderLeft, 0, y, borderSize, borderLeft.getHeight(null), null, null);
        }

        //right border
        for (int y = 0; y < dimensions.height; y += borderRight.getHeight(null)) {
            g.drawImage(borderRight, dimensions.width - borderSize, y, borderSize, borderRight.getHeight(null), null, null);
        }

        g.drawImage(borderTL, 0, 0, borderSize, borderSize, null, null);
        g.drawImage(borderTR, dimensions.width - borderSize, 0, borderSize, borderSize,null, null);
        g.drawImage(borderBL, 0, dimensions.height - borderSize, borderSize, borderSize,null, null);
        g.drawImage(borderBR, dimensions.width - borderSize, dimensions.height - borderSize, borderSize, borderSize,null, null);
    }
}
