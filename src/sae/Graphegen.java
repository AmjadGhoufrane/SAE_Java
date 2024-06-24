package sae;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Lecture de fichier et creation du graphe conflits
 */
public class Graphegen {
    private boolean output = false;

    private final String fv;
    Graph graph = new SingleGraph("");

    private int cpt_conflits = 0;
    private Aeroport[] taba;

    private Aeroport[] tabavc;
    private Vol[] tabv;
    private int nbaeroports, nbvols;

    /**
     * Constructeur
     * @param nFichierA nom du fichier contenant les informations sur les aéroports.
     * @param nFichierV nom du fichier contenant les informations sur les vols.
     */
    public Graphegen(String nFichierA, String nFichierV) {
        this.taba = chargerAeroports(nFichierA);
        this.tabv = chargerVols(nFichierV);
        this.fv = nFichierV;
    }

    /**
     * Recherche d'aéroport par code
     * @param na code de l'aéroport.
     * @return aéroport correspondant au code donné.
     */
    private Aeroport trouverAero(String na) {
        for (Aeroport a : taba) {
            if (a.getCode().equals(na)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Vérification et ajout ou non de conflit entre deux vols
     * @param v1 premier vol.
     * @param v2 deuxième vol.
     */
    private void Intersection(Vol v1, Vol v2) {
        if (v1!=null && v2!=null) {
            if (
                    (v1.conflit(v2))

                            &&

                            !graph.getNode(v1.getIdVol()).hasEdgeBetween(v2.getIdVol())

            ) {
                if(output){
                    System.out.println(v1.getDep().getCode() + "-" + v1.getArrv().getCode() + " en conflit avec " + v2.getDep().getCode() + "-" + v2.getArrv().getCode());
                }

                graph.addEdge(v1.getIdVol() + v2.getIdVol(), v1.getIdVol(), v2.getIdVol());
                cpt_conflits++;
            }
        }
    }

    /**
     * Compte le nombre d'aéroports dans un fichier
     * @param nom_fichier nom du fichier aéroports.
     * @return nombre d'aéroports dans le fichier.
     */
    private int compteAeroports(String nom_fichier) {
        int cpt = 0;
        try {
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                cpt++;
                scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nbaeroports = cpt;
        return cpt;
    }

    /**
     * Chargement des aéroports à partir d'un fichier
     * @param nom_fichier nom du fichier aéroports.
     * @return tableau contenant les aéroports.
     */
    public Aeroport[] chargerAeroports(String nom_fichier) {
        Aeroport[] tab = new Aeroport[compteAeroports(nom_fichier)];
        try {
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                String cour = scanner.nextLine();
                String[] split_cour = cour.split(";");
                Aeroport a = new Aeroport(split_cour[0], split_cour[1], Integer.valueOf(split_cour[2]), Integer.valueOf(split_cour[3]), Integer.valueOf(split_cour[4]), Integer.valueOf(split_cour[6]), Integer.valueOf(split_cour[7]), Integer.valueOf(split_cour[8]),split_cour[5].charAt(0),split_cour[9].charAt(0));;
                tab[i] = a;
                i++;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tab;
    }

    /**
     * Compte le nombre de vols dans un fichier
     * @param nom_fichier nom du fichier vols.
     * @return nombre de vols dans le fichier.
     */
    private int compteVols(String nom_fichier) {
        int cpt = 0;
        try {
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                cpt++;
                scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nbvols = cpt;
        return cpt;
    }

    /**
     * Charge des vols à partir d'un fichier
     * @param nom_fichier nom du fichier vols.
     * @return tableau contenant les vols.
     */
    public Vol[] chargerVols(String nom_fichier) {
        Vol[] tab = new Vol[compteVols(nom_fichier)];
        tabavc = new Aeroport[compteVols(nom_fichier)];
        try {
            FileInputStream file = new FileInputStream(nom_fichier);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                String cour = scanner.nextLine();
                String[] split_cour = cour.split(";");

                int a3,a4,a5;

                try {
                    a3=Integer.valueOf(split_cour[3]);
                } catch (NumberFormatException e) {
                    a3=0;
                }
                try {
                    a4=Integer.valueOf(split_cour[4]);
                } catch (NumberFormatException e) {
                    a4=0;
                }
                try {
                    a5=Integer.valueOf(split_cour[5]);
                } catch (NumberFormatException e) {
                    a5=0;
                }
                Aeroport dep,arrv;
                dep = this.trouverAero(split_cour[1]);
                arrv = this.trouverAero(split_cour[2]);
                if (dep == null || arrv == null){
                    continue;
                }
                else{
                    tab[i] = new Vol(split_cour[0], dep, arrv, a3, a4, a5);
                }
                tabavc[i] = this.trouverAero(split_cour[1]);
                i++;
            }
            scanner.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return tab;
    }

    /**
     * Génération de graphe
     * @return Le graphe généré.
     */
    public Graph genGraph() {
        for (Vol v : tabv) {
            if(v != null){
                Node node = graph.addNode(v.getIdVol());
                node.setAttribute("ui.label", v.getDep().getCode()+"-"+v.getArrv().getCode());
            }
        }
        for (Vol v : tabv) {
            if (v != null) {
                for (Vol vp : tabv) {
                    if (!v.equals(vp)) {
                        Intersection(v, vp);
                    }
                }
            }
        }
        System.out.println("Nombre de conflits "+this.fv+": " + cpt_conflits);
        return graph;
    }

    /**
     * Renvoie le tableau des aéroports
     * @return Le tableau des aéroports.
     */
    public Aeroport[] getTaba() {
        return taba;
    }

    /**
     * Renvoie le tableau des aéroports avec conflits
     * @return Le tableau des aéroports avec conflits.
     */
    public Aeroport[] getTabavc() {
        return tabavc;
    }

    /**
     * Renvoie le tableau des vols
     * @return Le tableau des vols.
     */
    public Vol[] getTabvol() {
        return tabv;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }
}