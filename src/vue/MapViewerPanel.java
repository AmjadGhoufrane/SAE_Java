package vue;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.*;
import sae.Aeroport;
import sae.Graphegen;
import sae.Vol;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MapViewerPanel extends JXMapViewer{
    private RoutePainter painter;
    Graphegen graphegen = new Graphegen("aeroports.txt", "Data_Test/vol-test4.csv");
    private JXMapViewer mapViewer;
    private Set<Waypoint> waypoints;
    private WaypointPainter<Waypoint> waypointPainter;

    public MapViewerPanel(){

        mapViewer = new JXMapViewer();

        OSMTileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition france = new GeoPosition(46.603354, 1.888334);
        mapViewer.setZoom(13);
        mapViewer.setAddressLocation(france);

        waypoints = new HashSet<>();
        waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        setLayout(new BorderLayout());
        add(new JScrollPane(mapViewer), BorderLayout.CENTER);
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }

    // rededfine paint
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 800, 600);
    }

    public void addWaypoint(double latitude, double longitude) {
        Waypoint waypoint = new DefaultWaypoint(latitude, longitude);
        waypoints.add(waypoint);
        waypointPainter.setWaypoints(waypoints);
        mapViewer.repaint();
    }

    public void visualize() {
        JFrame frame = new JFrame("Map Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        for(Aeroport a : graphegen.getTabavc()) {
            this.addWaypoint(a.getLat()*(-1), a.getLongi());
        }

        ArrayList<Double[]> tab = new ArrayList<Double[]>();

        for(Vol v : graphegen.getTabvol()) {
            Double[] d = new Double[]{v.getDep().getLat()*(-1), v.getDep().getLongi(), v.getArrv().getLat()*(-1), v.getArrv().getLongi()};
            tab.add(d);
            System.out.println(this.getGraphics() == null);

            System.out.println(d[0] + " " + d[1] + " " + d[2] + " " + d[3]);
        }

        painter = new RoutePainter(tab);
        this.setOverlayPainter(painter);

        frame.getContentPane().add(this);
        frame.setVisible(true);
    }

}