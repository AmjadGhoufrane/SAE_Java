package vue;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet de tracer des routes et des waypoints sur une carte.
 */
public class RoutePainter implements Painter<JXMapViewer>
{
    private Color color = Color.RED;
    private boolean antiAlias = true;
    private List<GeoPosition> track;

    /**
     * Constructeur de la classe RoutePainter.
     * @param track Une liste de GeoPositions représentant l'itinéraire à peindre.
     */
    public RoutePainter(List<GeoPosition> track)
    {
        this.track = new ArrayList<GeoPosition>(track);
    }

    /**
     * La méthode paint est responsable du dessin de l'itinéraire sur la carte.
     * @param g objet Graphics2D à utiliser pour le dessin.
     * @param map objet JXMapViewer représentant la carte.
     * @param w  largeur de la carte.
     * @param h  hauteur de la carte.
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h)
    {
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        if (antiAlias)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));

        drawRoute(g, map);

        g.setColor(color);
        g.setStroke(new BasicStroke(2));

        drawRoute(g, map);

        g.dispose();
    }

    /**
     * La méthode drawRoute est responsable du tracé des segments individuels de l'itinéraire.
     * @param g objet Graphics2D à utiliser pour le dessin.
     * @param map objet JXMapViewer représentant la carte.
     */
    private void drawRoute(Graphics2D g, JXMapViewer map)
    {
        int lastX = 0;
        int lastY = 0;

        boolean first = true;

        for (GeoPosition gp : track)
        {
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());

            if (first)
            {
                first = false;
            }
            else
            {
                g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
            }

            lastX = (int) pt.getX();
            lastY = (int) pt.getY();
        }
    }
}