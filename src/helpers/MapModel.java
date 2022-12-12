package helpers;


public class MapModel extends Model {
    MapView view;
    char[][] map;

    public MapModel(MapView view, char[][] map) {
        this.view = view;
        this.map = map;
    }

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public void updateTile(int x, int y, char changeTo) {
        this.map[x][y] = changeTo;
    }

}
