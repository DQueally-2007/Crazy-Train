import javax.swing.*;
import java.awt.*;

private Graphics graphics;
private Font drawingFont;

public class DrawingPanel extends JPanel 
{
    public DrawingPanel(Layout layout)
    {
        super(layout);
        graphics = new Graphics();
        drawingFont = new Font(ds, 1, 3);
        graphics.setFont(drawingFont);
    }

    public void paintPoint(int xPosition, int yPosition)
    {
        graphics.drawRect(xPosition, yPosition, 5, 5);
    }

    public void paintLine(int xStartPos, int yStartPos, int xEndPos, int yEndPos)
    {
        graphics.drawLine(xStartPos, yStartPos, xEndPos, yEndPos);
    }

    public void paintText(String stringToPaint, int xToPaint, int yToPaint)
    {
        graphics.drawString(stringToPaint, xToPaint, yToPaint);
    }
    
    public void paintCircle(int xPos, int yPos)
    {
        graphics.drawOval(xPos, yPos, 4, 4);
    }
}
