package project3;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JLabel;

public class signal implements Runnable {
    private final String[] COLORS = {"Green", "Yellow", "Red"};
    private int value = 0;
    private String currentLight = COLORS[value];
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    public final AtomicBoolean suspended = new AtomicBoolean(false);
    Thread signalT;
    String signalName;
 
    private JLabel part;
    
    public signal(String name, JLabel x) {
        this.signalName = name;
        this.part = x;
        System.out.println("Creating " + signalName);}
    
    public void run() {
        System.out.println("Running " + signalName);
        isRunning.set(true);
        while(isRunning.get()) {
            try {
                synchronized(this) {
                        while(suspended.get()) {
                            System.out.println(signalName + " waiting");
                            wait();}}
                switch (getColor()) {
                    case "Green":
                        part.setForeground(Color.BLACK); //Set font color to green
                        part.setText(getColor());
                        Thread.sleep(10000);
                        value = value +1;
                        break;
                    case "Yellow":
                        part.setForeground(Color.BLACK); //Font color yellow
                        part.setText(getColor());
                        Thread.sleep(5000);
                        value = value + 1;
                        break;
                    case "Red":
                        part.setForeground(Color.BLACK); //Font color red
                        part.setText(getColor());
                        Thread.sleep(5000);
                        value = 0;
                        break;
                    default:
                        break;}                
            } catch (InterruptedException ex) {
                suspended.set(true);}}}
    

    
    //Synchronized method for getting signal light color
    public synchronized String getColor() {
        this.currentLight = COLORS[value];
        return this.currentLight;}
    
    public void suspend() {
        suspended.set(true);
        System.out.println("Suspending " + signalName);}
    
    public synchronized void resume() {
        suspended.set(false);
        notify();
        System.out.println("Resuming " + signalName);}
    
    public void interrupt() {signalT.interrupt();}
    
    public void start() {
        System.out.println("Starting " + signalName);
        if(signalT == null) {
            signalT = new Thread(this, signalName);
            signalT.start();}}
    
    public void stop() {
        signalT.interrupt();
        isRunning.set(false);
        System.out.println("Stopping " + signalName);}}
 

    
    
  