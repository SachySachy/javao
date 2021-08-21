package project3;
//Sachin Saigal
//Time.java
//time object that is used to create a thread of time
//a thread that runs the current time of the object time
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time implements Runnable {
    private boolean active;
    private String times = "hh:mm:ss a";
    private SimpleDateFormat timeF = new SimpleDateFormat(times); 
    Date date = new Date(System.currentTimeMillis());

    
    public Time() {
        this.active = Thread.currentThread().isAlive();}
    
    //creates date data type
    public String current() {
        date = new Date(System.currentTimeMillis());
        return timeF.format(date);}

    public void run() {
        while (active) {
            project3main.timeText.setText(current());}}
    
}
