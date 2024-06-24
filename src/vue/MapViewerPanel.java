package vue;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;
import sae.Graphegen;
import sae.Vol;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.util.*;

/**
 * Visualisation des vols sur une carte.
 */
public class MapViewerPanel {
    Graphegen graphegen;
    JXMapViewer mapViewer = new JXMapViewer();

    /**
     * Constructeur
     * @param fichier nom du fichier vols.
     */
    public MapViewerPanel(String fichier) {
        this.graphegen = new Graphegen("aeroports.txt", fichier);
    }

    /**
     * Tracé des vols et waypoints + affichage de la carte.
     */
    public void visualize(){
        JFrame frame = new JFrame("Carte des vols");
        frame.getContentPane().add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        GeoPosition france = new GeoPosition(46.603354, 1.888334);
        mapViewer.setZoom(14);
        mapViewer.setAddressLocation(france);
        List<Painter<JXMapViewer>> painters = new ArrayList<>();

        MouseInputListener mm = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mm);
        mapViewer.addMouseMotionListener(mm);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // Ajout des waypoints et tracés de vols pour chaque objet vol
        for(Vol v : this.graphegen.getTabvol()){
            if (v!=null) {
                GeoPosition dep = new GeoPosition(v.getDep().getLat()*(-1), v.getDep().getLongi());
                GeoPosition arrv = new GeoPosition(v.getArrv().getLat()*(-1), v.getArrv().getLongi());

                List<GeoPosition> track = Arrays.asList(dep,arrv);
                RoutePainter routePainter = new RoutePainter(track);

                Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
                        new DefaultWaypoint(dep),
                        new DefaultWaypoint(arrv)));

                WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
                waypointPainter.setWaypoints(waypoints);

                painters.add(routePainter);
                painters.add(waypointPainter);
            }
        }

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }
}