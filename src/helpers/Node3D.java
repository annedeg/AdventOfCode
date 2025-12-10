package helpers;

public class Node3D implements Comparable {
    public static long num = 0;

    public long nodeNum;
    public long x;
    public long y;
    public long z;

    public Node3D(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.nodeNum = num++;
    }

    public double calcDistance(Node3D node3D) {
        long x2 = node3D.x;
        long y2 = node3D.y;
        long z2 = node3D.z;

        long dx = x2-x;
        long dy = y2-y;
        long dz = z2-z;

        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));

//        return (double) Math.sqrt((double) Math.pow(Math.abs((double) this.x - node3D.x)+1, 2) + Math.pow(Math.abs((double) this.y - node3D.y)+1, 2) + Math.pow(Math.abs((double) this.z - node3D.z)+1, 2));
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Node3D node3D)) {
            return 0;
        }

        return Math.toIntExact(this.nodeNum - node3D.nodeNum);
    }
}