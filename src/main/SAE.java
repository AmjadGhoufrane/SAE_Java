/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import map.MapViewerPanel;
import org.graphstream.graph.Graph;
import sae.Graphegen;

public class SAE {


    public static void main(String[] args) {
        String fichier = "Data_Test/vol-test4.csv";

        Graphegen g = new Graphegen("aeroports.txt", fichier);
        g.setOutput(true);
        Graph graph = g.genGraph();
        graph.display();

        MapViewerPanel mapViewerPanel = new MapViewerPanel(fichier);
        mapViewerPanel.visualize();




//        for (int i = 0; i <= 9; i++) {
//            Graphegen gg = new Graphegen("aeroports.txt", "vol-evalP1/vol-eval"+i+".csv");
//            Graph graphe = gg.genGraph();
//        }


    }

}
