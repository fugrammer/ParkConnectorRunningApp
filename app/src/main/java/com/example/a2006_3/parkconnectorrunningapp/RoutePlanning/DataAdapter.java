package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

/**
 * Created by Song Wei on 30/10/2017.
 */

public class DataAdapter {

    String username;
    int dist;
    int min;
    int sec;
    int cal;
    String date;

    public String getUsername(){
        return username;
    }

    public void setUsername(String username1){
        this.username = username1;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist1) {
        this.dist = dist1;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min1) {
        this.min = min1;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec1) {
        this.sec = sec1;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal1) {
        this.cal = cal1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date1) {
        this.date = date1.substring(0, 10);
    }

}


