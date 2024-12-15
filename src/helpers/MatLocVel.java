package helpers;

import java.util.Objects;

public class MatLocVel {
    public int x;
    public int y;
    public int xc;
    public int yc;
    public int mx;
    public int my;

    public MatLocVel(int x, int y, int xc, int yc, int mx, int my) {
        this.x = x;
        this.y = y;
        this.xc = xc;
        this.yc = yc;
        this.mx = mx;
        this.my = my;
    }

    public MatLocVel getNextPosition(int iterations) {
        for (;iterations > 0;iterations--) {
            this.x += xc;
            this.y += yc;

            if (this.x >= mx) {
                this.x = this.x - mx;
            }

            if (this.y >= my) {
                this.y = this.y - my;
            }

            if (this.x < 0) {
                this.x = this.x + mx;
            }

            if (this.y < 0) {
                this.y = this.y + my;
            }
        }

        return this;
    }

    public MatLocVel getNextPosition() {
        return getNextPosition(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatLocVel matLocVel = (MatLocVel) o;
        return x == matLocVel.x && y == matLocVel.y && xc == matLocVel.xc && yc == matLocVel.yc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, xc, yc);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXc() {
        return xc;
    }

    public void setXc(int xc) {
        this.xc = xc;
    }

    public int getYc() {
        return yc;
    }

    public void setYc(int yc) {
        this.yc = yc;
    }
}
