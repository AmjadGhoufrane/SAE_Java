/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;


public class Vol {
    public final int tdiff = 15;
    private String idVol;
    private Aeroport dep, arrv;
    private int h, m, t;

    /**
     * Constructeur
     * @param idVol identifiant du vol.
     * @param dep aéroport de départ.
     * @param arrv aéroport d'arrivée.
     * @param h heure de départ.
     * @param m  minutes de départ.
     * @param t  durée du vol.
     */
    public Vol(String idVol, Aeroport dep, Aeroport arrv, int h, int m, int t) {
        this.idVol = idVol;
        this.dep = dep;
        this.arrv = arrv;
        this.h = h;
        this.m = m;
        this.t = t;
    }

    /**
     * Calcule la vitesse du vol
     * @return vitesse du vol.
     */
    public double getVitesse() {
        double distance = compDistanceBetweenPoints(dep.getX(), dep.getY(), arrv.getX(), arrv.getY());
        return distance / t;
    }

    public String getIdVol() {
        return idVol;
    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public int getDuree() {
        return t;
    }

    public Aeroport getDep() {
        return dep;
    }

    public Aeroport getArrv() {
        return arrv;
    }

    /**
     * Calcule la distance entre deux points
     * @param x1 Coordonnée x du premier point.
     * @param y1 Coordonnée y du premier point.
     * @param x2 Coordonnée x du deuxième point.
     * @param y2 Coordonnée y du deuxième point.
     * @return distance entre deux points.
     */
    public double compDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    private double[] addMinutes(double[] temps, double ma) {

        double h = temps[0];
        double m = temps[1];

        Double totalM = h * 60 + m + ma;

        int nH = totalM.intValue() / 60;
        double nM = totalM % 60;

        return new double[]{nH, nM};
    }

    private double diffMinutes(double[] time1, double[] time2) {

        double totM1 = time1[0] * 60 + time1[1];
        double totM2 = time2[0] * 60 + time2[1];

        if (totM1 < totM2){
            return totM2 - totM1;
        }
        else {
            return totM1 - totM2;
        }

    }


    private double[] enMinutes(double[] time1, double[] time2) {

        double totM1 = time1[0] * 60 + time1[1];
        double totM2 = time2[0] * 60 + time2[1];


        return new double[]{totM1, totM2};
    }

    private double diff(double a, double b) {
        if(a > b) {
            return a - b;
        }
        return b - a;
    }

    /**
     * Vérifie si deux vols passe par l'intersection de leurs trajectoires à moins de 15 minutes
     * @param v2 L'autre vol.
     * @return vrai s'il y a un conflit, faux sinon.
     */
    private boolean conflitTemps(Vol v2) {
        double[] inter = this.getIntersectionCoordonnees(this, v2);
        if (inter == null) {
            return false;
        }
        double vt1 = this.getVitesse();
        double vt2 = v2.getVitesse();
        double m_arrv1 = (this.compDistanceBetweenPoints(this.getDep().getX(), this.getDep().getY(), inter[0], inter[1])) / vt1;
        double m_arrv2 = (this.compDistanceBetweenPoints(v2.getDep().getX(), v2.getDep().getY(), inter[0], inter[1])) / vt2;
        double[] t_arrv1fin = this.addMinutes(new double[]{this.getH(), this.getM()}, m_arrv1);
        double[] t_arrv2fin = this.addMinutes(new double[]{v2.getH(), v2.getM()}, m_arrv2);

//        System.out.println("This : "+t_arrv1fin[0]+":"+t_arrv1fin[1]+" V2 : "+t_arrv2fin[0]+":"+t_arrv2fin[1]);

        if (diffMinutes(t_arrv1fin, t_arrv2fin) < tdiff) {
            return true;
        }
        return false;
    }


    /**
     * Recherche de conflits
     * @param v2 L'autre vol.
     * @return vrai s'il y a un conflit, faux sinon.
     */
    public boolean conflit(Vol v2) {

        if (!v2.getDep().getCode().equals(this.getArrv().getCode())
                && !v2.getArrv().getCode().equals(this.getDep().getCode())
                && !v2.getDep().getCode().equals(this.getDep().getCode())
                && !v2.getArrv().getCode().equals(this.getArrv().getCode())){

            if (this.conflitTemps(v2)) {
                return true;
            }

        }

        else if (v2.getArrv().getCode().equals(this.getArrv().getCode())
                && v2.getDep().getCode().equals(this.getDep().getCode())){

            double[] min = enMinutes(new double[]{this.getH(), this.getM()}, new double[]{v2.getH(), v2.getM()});

            if (diff(min[0], min[1])< tdiff) {
                return true;
            }

        }

        else if (v2.getDep().getCode().equals(this.getArrv().getCode())
                && v2.getArrv().getCode().equals(this.getDep().getCode())){

            double[] min = enMinutes(new double[]{this.getH(), this.getM()}, new double[]{v2.getH(), v2.getM()});
            double dv = compDistanceBetweenPoints(this.getDep().getX(), this.getDep().getY(), this.getArrv().getX(), this.getArrv().getY())/2;
            double temps_pour_atteindre_this = (dv / this.getVitesse());
            double temps_pour_atteindre_v2 = (dv / v2.getVitesse());

            if (diff(min[0]+this.getDuree(), min[1])< tdiff || diff(min[0], min[1]+v2.getDuree())< tdiff || diff(min[0]+(temps_pour_atteindre_this), min[1]+(temps_pour_atteindre_v2))< tdiff) {
                return true;
            }

        }

        else if (v2.getDep().getCode().equals(this.getArrv().getCode())){

            double[] min = enMinutes(new double[]{this.getH(), this.getM()}, new double[]{v2.getH(), v2.getM()});

            if (diff(min[0]+this.getDuree(), min[1])< tdiff) {
//                System.out.println("" + (min[0]+this.getDuree()) + " " + min[1]);
                return true;
            }

        }

        else if (v2.getArrv().getCode().equals(this.getDep().getCode())) {

            double[] min = enMinutes(new double[]{this.getH(), this.getM()}, new double[]{v2.getH(), v2.getM()});

            if (diff(min[0], min[1]+v2.getDuree())< tdiff) {
                return true;
            }

        }


        else if (v2.getArrv().getCode().equals(this.getArrv().getCode()) ){ // v2 arrive à l'aéroport d'arrivée de this

            double[] min = enMinutes(new double[]{this.getH(), this.getM()}, new double[]{v2.getH(), v2.getM()});

            if (diff(min[0]+this.getDuree(), min[1]+v2.getDuree())< tdiff) {
                return true;
            }

        }

        else if (v2.getDep().getCode().equals(this.getDep().getCode())){ // v2 part de l'aéroport de départ de this

            double[] min = enMinutes(new double[]{this.getH(), this.getM()}, new double[]{v2.getH(), v2.getM()});

            if (diff(min[0], min[1])< tdiff) {
                return true;
            }
            
        }

        return false;
    }

    /**
     * Calcule les coordonnées d'intersection de deux vols
     * @param v1 Le premier vol.
     * @param v2 Le deuxième vol.
     * @return Un tableau de doubles contenant les coordonnées d'intersection.
     */
    public double[] getIntersectionCoordonnees(Vol v1, Vol v2) {
        double x1 = v1.getDep().getX();
        double y1 = v1.getDep().getY();
        double x2 = v1.getArrv().getX();
        double y2 = v1.getArrv().getY();
        double x3 = v2.getDep().getX();
        double y3 = v2.getDep().getY();
        double x4 = v2.getArrv().getX();
        double y4 = v2.getArrv().getY();
        double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        double y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && x >= Math.min(x3, x4) && x <= Math.max(x3, x4)) {
            return new double[]{x, y};
        }
        return null;
    }


}
