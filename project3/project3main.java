package project3;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class project3main extends JFrame implements Runnable, ChangeListener {
    
    static JLabel timeText = new JLabel();
    static JLabel signalAtext = new JLabel();
    static JLabel signalBtext = new JLabel();
    static JLabel signalCtext = new JLabel();
    private JButton begin = new JButton("Begin");
    private JButton pause = new JButton("Pause");
    private JButton shutdown = new JButton("Shutdown");
    //we use j sliders to mark movement of the vehicles
    static JSlider crossoverMove = new JSlider(0, 3000);
    static JSlider superMove = new JSlider(0, 3000);
    static JSlider suvMove = new JSlider(0, 3000);

    
    private static boolean isRunning;
    private static final AtomicBoolean simIsRunning = new AtomicBoolean(false);  
    //Creates object threads for signal and vehicles
    signal firstStreet = new signal("firstThread", signalAtext);
    signal secondStreet = new signal("secondThread", signalBtext);
    signal thirdStreet = new signal("thirdThread", signalCtext);
    auto crossover = new auto("crossover", 450, 0);
    auto supercar = new auto("supercar", 900, 0);
    auto suv = new auto("suv", 1999, 1000);
 
    //Array of cars to loop through later
    auto[] vehicles = {crossover, supercar, suv};
    signal[] streets = {firstStreet, secondStreet, thirdStreet};
    static Thread gui;
    Object[][] traffic = {{"Crossover", crossover.getPosition(), 0, 0},{"SuperCar", supercar.getPosition(), 0, 0},{"SUV", suv.getPosition(), 0, 0},};
    //J-table use to demonstrate the data
    String[] columnNames = {"Vehicle", "X-Pos", "Y-Pos", "Speed mph"};
    JTable data = new JTable(traffic, columnNames);
    
    //default constructor for project3main which checks the threads
    //class buildGUI
    //call setButtons
    public project3main() {
        super("Project 3");
        isRunning = Thread.currentThread().isAlive();
        buildGUI();
        setButtons();}
    
    //calls display function that sets the size of the panel and configures the j-frame
    private void display() {
        setSize(600,450);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
    
    //run gets the value as the threads are running for the x position of the vehicles
    public void run() {
        while(isRunning) {
            if(simIsRunning.get()) {
            crossoverMove.setValue(crossover.getPosition());
            superMove.setValue(supercar.getPosition());
            suvMove.setValue(suv.getPosition());
            // sim is running gets states of the colors
            if(simIsRunning.get()) {
                switch(firstStreet.getColor()) {
                    case "Red":
                    	//if the vehicles is at the light stops the car 500 meter before light
                        for(auto light: vehicles) {
                            if(light.getPosition()>500 && light.getPosition()<1000) {
                                light.atLight.set(true);}}
                        break;
                        //if green resumes the thread to begin again at the light
                    case "Green":
                        for(auto light:vehicles) {
                            if(light.atLight.get()) {
                                light.resume();}}
                        break;}
                // second street stops vehicles between 500 meter from the light which is positioned 
                // at 2000 so 1500 is where the stop begin
                switch(secondStreet.getColor()) {
                    case "Red":
                        for(auto light: vehicles) {
                            if(light.getPosition()>1500 && light.getPosition()<2000) {
                                light.atLight.set(true);}}
                        break;
                 // same as first street the vehicles goes when the light is green
                    case "Green":
                        for(auto light:vehicles) {
                            if(light.atLight.get()) {
                                light.resume();}}
                        break;}
                // third street light stops the user 500 before the light which is 3000 meters away
                //this causes the light to change at 2500 meters
                switch(thirdStreet.getColor()) {
                    case "Red":
                        for(auto light: vehicles) {
                            if(light.getPosition()>2500 && light.getPosition()<3000) {
                                light.atLight.set(true);}}
                        break;
                 //thread resumes on green light
                    case "Green":
                        for(auto light:vehicles) {
                            if(light.atLight.get()) {
                                light.resume();}}
                        break;}}}}}
    
    private void buildGUI() {
        
        JLabel current = new JLabel("Current time: ");
        JLabel signalA = new JLabel("First Street: ");
        JLabel signalB = new JLabel("Second Street: ");
        JLabel signalC = new JLabel("Third Street: ");
        crossoverMove.addChangeListener(this);
        superMove.addChangeListener(this);
        suvMove.addChangeListener(this);       
        crossoverMove.setValue(crossover.getPosition());
        superMove.setValue(supercar.getPosition());
        suvMove.setValue(suv.getPosition());   
        crossoverMove.setMajorTickSpacing(1000);
        crossoverMove.setPaintTicks(true);   
        superMove.setMajorTickSpacing(1000);
        superMove.setPaintTicks(true);
    
        data.setPreferredScrollableViewportSize(new Dimension(400, 70));
        data.setFillsViewportHeight(true);
        
        JPanel dataPanel = new JPanel();  
        JScrollPane scrollPane = new JScrollPane(data);
        dataPanel.add(scrollPane);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createSequentialGroup())
            
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(begin).addComponent(pause).addComponent(shutdown))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(crossoverMove).addComponent(superMove).addComponent(suvMove))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(signalA).addComponent(signalAtext).addComponent(signalB).addComponent(signalBtext).addComponent(signalC).addComponent(signalCtext)).addComponent(dataPanel)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addGap(50, 50, 50)).addGap(50, 50, 50)
        	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(current).addComponent(timeText)));
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addContainerGap(30,30) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(layout.createSequentialGroup().addComponent(shutdown).addComponent(pause).addComponent(begin)))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(current).addComponent(timeText))).addComponent(suvMove).addComponent(crossoverMove).addComponent(superMove) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)   
                .addGroup(layout.createSequentialGroup().addComponent(signalA).addComponent(signalAtext).addContainerGap(50,50).addComponent(signalB).addComponent(signalBtext).addContainerGap(10,10).addComponent(signalC).addComponent(signalCtext)).addComponent(dataPanel))).addContainerGap(50,50)    
            );
        pack();
    }
    
    private void setButtons() {
        begin.addActionListener((ActionEvent e) -> {
            if(!simIsRunning.get()) {
                System.out.println(Thread.currentThread().getName() + " calling start");
                firstStreet.start();
                secondStreet.start();
                thirdStreet.start();
                crossover.start();
                supercar.start();
                suv.start();
                gui.start();}
            simIsRunning.set(true);});
        
        pause.addActionListener((ActionEvent e) -> {
            if(simIsRunning.get()) {
                for(auto v: vehicles) {
                    v.suspend();
                    System.out.println(Thread.currentThread().getName() + "suspending thread");}
                for(signal s: streets) {
                    s.interrupt();
                    s.suspend();}                
                pause.setText("Continue");
                simIsRunning.set(false);
            } else {
                for(auto v:vehicles) {
                    if(v.suspended.get()) {
                        v.resume();
                        System.out.println(Thread.currentThread().getName() + "resuming thread");}}
                
                for(signal s: streets) {s.resume();}
                pause.setText("Pause");
                simIsRunning.set(true);}});
        
        shutdown.addActionListener((ActionEvent e) -> {
            if(simIsRunning.get()) {
                System.out.println(Thread.currentThread().getName() + " calling stop");
                for(auto v: vehicles) {
                    v.stop();
                }
                for(signal s: streets) {
                    s.stop();
                }
                simIsRunning.set(false);}});}
    
    // traffic table to change the events to also updates the speed of the vehicles.
    public void stateChanged(ChangeEvent e) {
        traffic[0][3] = crossover.getSpeed() + " mph";
        traffic[1][3] = supercar.getSpeed() + " mph";
        traffic[2][3] = suv.getSpeed() + " mph";
    	traffic[0][1] = crossoverMove.getValue();
        traffic[1][1] = superMove.getValue();
        traffic[2][1] = suvMove.getValue();
        data.repaint();
    }
   
    //main function that calls start and creates the time thread that runs for the duration of the program
    public static void main(String[] args) {
        project3main test = new project3main();
        test.display();
        gui = new Thread(test);
        Thread time = new Thread(new Time());
        time.start();
    }   
}
