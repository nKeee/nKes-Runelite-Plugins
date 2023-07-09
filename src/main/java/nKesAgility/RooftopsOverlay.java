package nKesAgility;

import net.runelite.api.Client;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
        double U = Math.random();
        double V = (Math.random() * 5.2831853071795862);
        U = Math.sqrt(-2*Math.log(U))*variance;
        return mean + U*Math.cos(V);
    }

    private void dotRender(Graphics2D graphics, Shape s){
        if (s == null) return;

        Rectangle2D r = s.getBounds();
        double x, y;
        do {
            x = r.getBounds2D().getX() + boxMuller(r.getWidth()/2,r.getWidth()/ config.deviation());
            y = r.getBounds2D().getY() + boxMuller(r.getHeight()/2,r.getHeight()/config.deviation());
        } while(!r.contains(x,y));
        Shape dotRender = new Ellipse2D.Double(x- config.dotSize(),y- config.dotSize(), config.dotSize(), config.dotSize());
        if(r.contains(dotRender.getBounds2D())){
            graphics.setColor(config.getDotColor());
            graphics.fill(dotRender);
        }
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
