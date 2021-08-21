package project3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class auto implements Runnable {
    private int x;
    private int y = 10;
    private final AtomicBoolean on = new AtomicBoolean(false);
    public final AtomicBoolean atLight = new AtomicBoolean(false);
    public final AtomicBoolean suspended = new AtomicBoolean(false);
    private String threadName = "";
    public Thread thread;
    private int speed = 0;
    
    
    public auto(String name, int max, int min) {
        this.threadName = name;
        this.x = ThreadLocalRandom.current().nextInt(min, max);
        System.out.println("Creating " + threadName);}

    public synchronized int getPosition() {
        return x;}
    
    public int getSpeed() {
        if(on.get()) {
            if(atLight.get()) 
                speed = 0;
            else 
                speed = 3*20;
        } else 
            speed = 0;
        return speed;}
    
    public void start() {
        System.out.println("Starting " + threadName);
        if(thread == null) {
            thread = new Thread(this, threadName);
            thread.start();}}
    
    public void stop() {
        thread.interrupt();
        on.set(false);
        System.out.println("Stopping " + threadName);}
    
    public void suspend() {
        suspended.set(true);
        System.out.println("Suspending " + threadName);}
    
    public synchronized void resume() {
        if(suspended.get() || atLight.get()) {
            suspended.set(false);
            atLight.set(false);
            notify();
            System.out.println("Resuming " + threadName);}}
   
    public void run() {
        System.out.println("Running " + threadName);
        on.set(true);
        while(on.get()) {
            try {
                while(x < 3000) {
                    synchronized(this) {
                        while(suspended.get() || atLight.get()) {
                            System.out.println(threadName + " waiting");
                            wait();}}
                    if(on.get()) {
                    Thread.sleep(100);
                    x+=5;}}
                x = 0;     
            } catch (InterruptedException ex) {
                return;}}}
}