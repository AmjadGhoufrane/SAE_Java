/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae;

import vue.MapViewerPanel;

/**
 * @author iuseh
 */
public class SAE {


    public static void main(String[] args) {
//        Graphegen g = new Graphegen("aeroports.txt", "Data_Test/vol-test5.csv");
//        Graph graph = g.genGraph();
//        graph.display();
        MapViewerPanel mapViewerPanel = new MapViewerPanel("Data_Test/vol-test2.csv");
        mapViewerPanel.visualize();


//        for (int i = 0; i <= 9; i++) {
//            Graphegen g = new Graphegen("aeroports.txt", "Data_Test/vol-test"+i+".csv");
//            Graph graph = g.genGraph();
//        }


    }

}
