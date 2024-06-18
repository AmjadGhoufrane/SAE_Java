package vue;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class RoutePainter implements Painter<JXMapViewer> {
    ArrayList<Double[]> tab;
    private double lat1, longi1, lat2, longi2;


    public RoutePainter(ArrayList<Double[]> tab) {
        this.tab = tab;
    }

    @Override
    public void paint(Graphics2D graphics2D, JXMapViewer map, int width, int height) {
            for(Double[] d : tab){
                GeoPosition start = new GeoPosition(d[0], d[1]);
                GeoPosition end = new GeoPosition(d[2], d[3]);

                Point2D startPt = map.getTileFactory().geoToPixel(start, map.getZoom());
                Point2D endPt = map.getTileFactory().geoToPixel(end, map.getZoom());

                graphics2D.setColor(Color.RED);
                graphics2D.setStroke(new BasicStroke(2));

                graphics2D.drawLine((int) startPt.getX(), (int) startPt.getY(),
                        (int) endPt.getX(), (int) endPt.getY());
                System.out.println("Drawing line from " + start + " to " + end);
            }

    }
}
