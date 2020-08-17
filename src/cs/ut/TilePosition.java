package cs.ut;

import java.util.Objects;

public class TilePosition {
    int xPosition;
    int yPosition;

    public TilePosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public String toString() {
        return "[" + xPosition + ", " + yPosition + "]";
    }

    @Override
    public boolean equals(Object o) {
        TilePosition that = (TilePosition) o;
        return xPosition == that.xPosition &&
                yPosition == that.yPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPosition, yPosition);
    }
}
