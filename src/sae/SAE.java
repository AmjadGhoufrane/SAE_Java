/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae;

import org.graphstream.graph.Graph;

/**
 * @author iuseh
 */
public class SAE {


    public static void main(String[] args) {
        Graphegen g = new Graphegen("aeroports.txt", "Data_Test/vol-test3.csv");
        Graph graph = g.genGraph();
        graph.display();


    }

}
