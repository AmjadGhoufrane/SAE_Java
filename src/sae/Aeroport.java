package sae;

/**
 * Aéroport.
 */
public class Aeroport {
    private String code;
    private String Ville;
    private int d1, m1, s1;
    private int d2, m2, s2;
    private double lat, longi;
    private double x, y;
    private double rTerre = 6371;
    private char orientation1, orientation2;

    /**
     * Constructeur
     * @param code  code de l'aéroport.
     * @param Ville  nom de la ville où se trouve l'aéroport.
     * @param n1  latitude de l'aéroport.
     * @param n2  longitude de l'aéroport.
     * @param n3 altitude de l'aéroport.
     * @param e1 heure d'ouverture de l'aéroport.
     * @param e2 heure de fermeture de l'aéroport.
     * @param e3  nombre de pistes de l'aéroport.
     * @param o1 orientation de l'aéroport.
     * @param o2 orientation de l'aéroport.
     */
    public Aeroport(String code, String Ville, int n1, int n2, int n3, int e1, int e2, int e3,char o1,char o2) {
        this.code = code;
        this.Ville = Ville;
        this.d1 = n1;
        this.m1 = n2;
        this.s1 = n3;
        this.d2 = e1;
        this.m2 = e2;
        this.s2 = e3;
        this.orientation2 = o2;
        int coef1 = (orientation1 == 'N' || orientation1 == 'E') ? 1 : -1;
        int coef2 = (orientation2 == 'N' || orientation2 == 'E') ? 1 : -1;
        this.lat = coef1 * (d1 + (m1 / 60.0) + (s1 / 3600.0));
        this.longi = coef2 * (d2 + (m2 / 60.0) + (s2 / 3600.0));
        double[] d = this.gpsaCartesien(lat, longi);
        this.x = d[0];
        this.y = d[1];
    }

    /**
     * Convertit des coordonnées GPS en coordonnées cartésiennes
     * @param latitude  latitude à convertir.
     * @param longitude  longitude à convertir.
     * @return Un tableau de doubles contenant les coordonnées cartésiennes.
     */
    private double[] gpsaCartesien(double latitude, double longitude) {
        double latRad = Math.toRadians(latitude);
        double lonRad = Math.toRadians(longitude);
        double x = rTerre * Math.cos(latRad) * Math.sin(lonRad);
        double y = rTerre * Math.cos(latRad) * Math.cos(lonRad);
        return new double[]{x, y};
    }

    public String getCode() {
        return code;
    }

    public String getVille() {
        return Ville;
    }

    public int getD1() {
        return d1;
    }

    public int getM1() {
        return m1;
    }

    public int getS1() {
        return s1;
    }

    public int getD2() {
        return d2;
    }

    public int getM2() {
        return m2;
    }

    public int getS2() {
        return s2;
    }

    public double getLat() {
        return lat;
    }

    public double getLongi() {
        return longi;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * @return chaîne de caractères représentant l'objet Aeroport.
     */
    @Override
    public String toString() {
        return "Aeroport{" +
                "code='" + code + '\'' +
                ", Ville='" + Ville + '\'' +
                ", d1=" + d1 +
                ", m1=" + m1 +
                ", s1=" + s1 +
                ", d2=" + d2 +
                ", m2=" + m2 +
                ", s2=" + s2 +
                ", lat=" + lat +
                ", longi=" + longi +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}