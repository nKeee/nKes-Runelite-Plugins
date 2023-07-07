package nKesAgility;

import net.runelite.api.Client;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class RooftopsOverlay extends Overlay {
    private final Client client;
    private final RooftopsConfig config;
    private final RooftopsCourseManager course_manager;

    public RooftopsOverlay(final Client client, final RooftopsConfig config, final RooftopsCourseManager course_manager) {
        this.client = client;
        this.config = config;
        this.course_manager = course_manager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }
    @Override
    public Dimension render(Graphics2D graphics) {
        if (course_manager.getCourse() == null || !course_manager.isNearCourse()) return null;

        // Obstacles
        for (final TileObject obstacle : course_manager.getObstacles()) {
             final Color color =
                course_manager.isStoppingObstacle(obstacle.getId())
                    ? config.getObstacleStopColor()
                    : course_manager.getCourse().getNextObstacle().hasId(obstacle.getId())
                        ? course_manager.getCourse().isDoingObstacle()
                            ? config.getObstacleNextUnavailableColor()
                            : config.getObstacleNextColor()
                        : config.getObstacleUnavailableColor();
            renderShape(graphics, obstacle, color);

            if (color.equals(config.getObstacleNextColor())){
                dotRender(graphics,obstacle.getClickbox());
            }
        }

        // Marks
        for (final Tile mark : course_manager.getMarksOfGraces()) {
            renderShape(graphics, mark.getItemLayer(), config.getMarkOfGraceColor());

            if (course_manager.isStoppingObstacle(course_manager.getNextObstacle().getId())){
                dotRender(graphics,mark.getItemLayer().getClickbox());
            }
        }
        return null;
    }

    public double boxMuller(double mean, double variance) {
        double s,x,y;
        do {
            x = Math.random() * 2.0 - 1.0;
            y = Math.random() * 2.0 - 1.0;
            s = Math.pow(x, 2) + Math.pow(y, 2);
        } while ( (s > 1) || (s == 0) );

        double gaussian = x * Math.sqrt(-2*Math.log(s)/s);
        return mean + gaussian * Math.sqrt(variance);
    }

    private void dotRender(Graphics2D graphics, Shape s){
        if (s == null) return;

        Rectangle r = s.getBounds();
        double x, y;
        do {
            x = r.getX() + boxMuller(r.getWidth()/2,r.getWidth()*3);
            y = r.getY() + boxMuller(r.getHeight()/2, r.getHeight()*3);
        } while(!s.contains(x,y));
        Ellipse2D.Double dotRender = new Ellipse2D.Double(x,y, config.dotSize(), config.dotSize());
        graphics.setColor(config.getDotColor());
        graphics.fill(dotRender);
    }

    private void renderShape(final Graphics2D graphics, final TileObject obstacle, final Color color) {
        if (obstacle.getClickbox() == null || color.getAlpha() == 0) return;

        try {
            // Area border.
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(255, color.getAlpha() + 20)));
            graphics.setStroke(new BasicStroke(1));
            graphics.draw(obstacle.getClickbox());

            // Area fill.
            graphics.setColor(color);
            graphics.fill(obstacle.getClickbox());

        } catch (final Exception ignored) {}
    }
}
