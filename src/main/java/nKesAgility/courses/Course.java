package nKesAgility.courses;

import nKesAgility.MarkOfGrace;

public abstract class Course {
    private final String id;
    private final int[] regions;
    private final Obstacle[] obstacles;
    private final MarkOfGrace[] mark_of_graces;

    private int obstacle_index = 0;
    private boolean doing_obstacle;

    public Course(
        final String id,
        final int[] regions,
        final Obstacle[] obstacles,
        final MarkOfGrace[] mark_of_graces
    ) {
        this.id = id;
        this.regions = regions;
        this.obstacles = obstacles;
        this.mark_of_graces = mark_of_graces;
    }

    public String getId() {
        return id;
    }

    public int[] getRegions() {
        return regions;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public MarkOfGrace[] getMarkOfGraces() {
        return mark_of_graces;
    }

    public Obstacle getNextObstacle() {
        return obstacles[obstacle_index];
    }

    public void startObstacle() {
        // Obstacle already started.
        if (doing_obstacle) return;

        doing_obstacle = true;
        obstacle_index = obstacle_index + 1 == obstacles.length
            ? 0
            : obstacle_index + 1;
    }

    public void completeObstacle() {
        doing_obstacle = false;
    }

    public void reset() {
        doing_obstacle = false;
        obstacle_index = 0;
    }

    public boolean isDoingObstacle() {
        return doing_obstacle;
    }

    public boolean isInRegion(final int region) {
        for (final int r : this.regions) {
            if (r == region) {
                return true;
            }
        }

        return false;
    }

    public boolean isNearRegion(final int[] regions) {
        for (final int region : regions) {
            for (final int course_region : this.regions) {
                if (course_region == region) {
                    return true;
                }
            }
        }

        return false;
    }
}
