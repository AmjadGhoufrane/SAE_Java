/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

/**
 * @author iuseh
 */
public class Vol {

    private String idVol;
    private Aeroport dep, arrv;
    private int h, m, t;
    public Vol(String idVol, Aeroport dep, Aeroport arrv, int h, int m, int t) {
        this.idVol = idVol;
        this.dep = dep;
        this.arrv = arrv;
        this.h = h;
        this.m = m;
        this.t = t;
    }

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

    public double compDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    private int[] addMinutes(int[] temps, int ma) {

        int h = temps[0];
        int m = temps[1];

        int totalM = h * 60 + m + ma;

        int nH = (totalM / 60) % 24;
        int nM = totalM % 60;

        if (nH < 0) {
            nM += 24;
        }

        return new int[]{nH, nM};
    }

    private int diffMinutes(int[] time1, int[] time2) {

        int totM1 = time1[0] * 60 + time1[1];
        int totM2 = time2[0] * 60 + time2[1];

        int diff = totM2 - totM1;

        return diff;
    }

    private int diffMinutesm(int[] time1, int[] time2, int m1, int m2) {

        int totM1 = time1[0] * 60 + time1[1] + m1;
        int totM2 = time2[0] * 60 + time2[1] + m2;

        int diff = totM2 - totM1;

        return diff;
    }

    private int[] enMinutes(int[] time1, int[] time2) {

        int totM1 = time1[0] * 60 + time1[1];
        int totM2 = time2[0] * 60 + time2[1];


        return new int[]{totM1, totM2};
    }

    private boolean conflitTemps(Vol v2) {
        double[] inter = this.getIntersectionCoordonnees(this, v2);
        if (inter == null) {
            return false;
        }
        double vt1 = this.getVitesse();
        double vt2 = v2.getVitesse();
        double m_arrv1 = (this.compDistanceBetweenPoints(this.getDep().getX(), this.getDep().getY(), inter[0], inter[1]) * 1) / vt1;
        double m_arrv2 = (this.compDistanceBetweenPoints(v2.getDep().getX(), v2.getDep().getY(), inter[0], inter[1]) * 1) / vt2;
        int[] t_arrv1fin = this.addMinutes(new int[]{this.getH(), this.getM()}, (int) m_arrv1);
        int[] t_arrv2fin = this.addMinutes(new int[]{v2.getH(), v2.getM()}, (int) m_arrv2);

        if (Math.abs(diffMinutes(t_arrv1fin, t_arrv2fin)) <= 15) {
            return true;
        }
        return false;
    }

    public boolean conflit(Vol v2) {
        double[] inter = this.getIntersectionCoordonnees(this, v2);
            if (!v2.getDep().getCode().equals(this.getArrv().getCode()) && !v2.getArrv().getCode().equals(this.getDep().getCode())) {
                if (this.conflitTemps(v2)) {
                    return true;
                }
            }
            else if (v2.getDep().getCode().equals(this.getArrv().getCode())){
                int[] min = enMinutes(new int[]{this.getH(), this.getM()}, new int[]{v2.getH(), v2.getM()});
                if (Math.abs(min[0]+this.getDuree() - min[1])<= 15) {
                    return true;
                }

            }
            else if (v2.getArrv().getCode().equals(this.getDep().getCode())) {

                int[] min = enMinutes(new int[]{this.getH(), this.getM()}, new int[]{v2.getH(), v2.getM()});

                if (Math.abs(min[0] - min[1]+v2.getDuree() )<= 15) {
                    return true;
                }

            }
            else if (v2.getArrv().getCode().equals(this.getArrv().getCode()) && v2.getDep().getCode().equals(this.getDep().getCode())){
                return true;

            }
            else if (v2.getArrv().getCode().equals(this.getArrv().getCode()) ){
                int[] min = enMinutes(new int[]{this.getH(), this.getM()}, new int[]{v2.getH(), v2.getM()});
                if (Math.abs(min[0]+this.getDuree() - min[1]+v2.getDuree())<= 15) {
                    return true;
                }

            }
            else if (v2.getDep().getCode().equals(this.getDep().getCode())){
                int[] min = enMinutes(new int[]{this.getH(), this.getM()}, new int[]{v2.getH(), v2.getM()});
                if (Math.abs(min[0] - min[1])<= 15) {
                    return true;
                }
            }

            return false;
    }

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
    public boolean estIntersectionSurSegments(double[] intersection, Vol v1, Vol v2) {
        if(intersection!=null){
            double x = intersection[0];
            double y = intersection[1];

            double minX1 = Math.min(v1.getDep().getX(), v1.getArrv().getX());
            double maxX1 = Math.max(v1.getDep().getX(), v1.getArrv().getX());
            double minY1 = Math.min(v1.getDep().getY(), v1.getArrv().getY());
            double maxY1 = Math.max(v1.getDep().getY(), v1.getArrv().getY());

            double minX2 = Math.min(v2.getDep().getX(), v2.getArrv().getX());
            double maxX2 = Math.max(v2.getDep().getX(), v2.getArrv().getX());
            double minY2 = Math.min(v2.getDep().getY(), v2.getArrv().getY());
            double maxY2 = Math.max(v2.getDep().getY(), v2.getArrv().getY());

            if (x >= minX1 && x <= maxX1 && y >= minY1 && y <= maxY1 && x >= minX2 && x <= maxX2 && y >= minY2 && y <= maxY2) {
                return true;
            }
        }

        return false;
    }

    public class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


}
