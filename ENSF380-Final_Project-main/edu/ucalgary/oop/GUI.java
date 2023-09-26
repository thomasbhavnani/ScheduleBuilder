/**
 * @author  380-PROJ 23
 * @version 1.4
 * @since 1.0
 */

package edu.ucalgary.oop;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;


import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.*;

public class GUI extends JFrame implements ActionListener, MouseListener{
    
    private JLabel instructions;
    


    /**
     * Constructs a new GUI object.
     */
    public GUI(){
        super("Create a schedule");
        setupGUI();
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLocationRelativeTo(null);    
        
    }
    
    /**
     * Sets up the graphical user interface.
     */
    public void setupGUI(){
        
        instructions = new JLabel("Generate a schedule for the day.");
       
        
        
        
     
        
        JButton button = new JButton("Generate Schedule");
        button.addActionListener(this);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new FlowLayout());

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());
        
        clientPanel.add(instructions);

        submitPanel.add(button);
        
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(clientPanel, BorderLayout.CENTER);
        this.add(submitPanel, BorderLayout.PAGE_END);
    }
    

    /**
     * Responds to a user action by generating a schedule and displaying it in a JOptionPane.
     * If the schedule cannot be generated due to a ScheduleGenerationException, the user is prompted to manually adjust the schedule.
     * If a volunteer is needed, the user is prompted to confirm that a backup volunteer has been called.
     *
     * @param event the event that triggered the action
     */
    public void actionPerformed(ActionEvent event){
       
        
        
            // String date = idProcessing();
            DatabaseConnection connect = new DatabaseConnection();
            // connect to database
            connect.createConnection();

            // get database contents
            ArrayList<String[]> animals = connect.selectAnimals();
            ArrayList<String[]> tasks = connect.selectTasks();
            ArrayList<String[]> treatments = connect.selectTreatments();
            Schedule schedule = new Schedule(animals, tasks, treatments);
            String[] options = { "OK", "CANCEL" };
            String[] otherOptions = {"OK", "Staff vet not present, make manual changes"};
            boolean showYes = false;
            boolean cancelled = false;
            int counter = 0;

            try {
                schedule.generateSchedule();
                if (schedule.getNeedVolunter()) {
                    
                
                    int selectedValue = JOptionPane.showOptionDialog(rootPane, "A backup volunteer is required.", "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
                   
                    if (selectedValue == 0) {
                        String hourList = "";
                        for (Integer hours : schedule.getVolunteerHours()) {
                            hourList += hours + ":00 ";
                        }
                        int selection = JOptionPane.showOptionDialog(rootPane, "Backup volunteer needed for these hours: " + 
                        hourList + "\nPlease do not press \"OK\" until the volunteer(s) have been called.", "Confirm",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        options, options[0] );
                        if (selection == 0) {
                            JTextArea textArea = new JTextArea(schedule.format());
                            JScrollPane scrollPane = new JScrollPane(textArea);  
                            textArea.setLineWrap(true);  
                            textArea.setWrapStyleWord(true); 
                            scrollPane.setPreferredSize( new Dimension( 700, 700 ) );
                            JOptionPane.showMessageDialog(rootPane, scrollPane, "Schedule",  
                                                                JOptionPane.DEFAULT_OPTION);
                        }
                    }
                }
                else {
                    JTextArea textArea = new JTextArea(schedule.format());
                    JScrollPane scrollPane = new JScrollPane(textArea);  
                    textArea.setLineWrap(true);  
                    textArea.setWrapStyleWord(true); 
                    scrollPane.setPreferredSize( new Dimension( 700, 600 ) );
                    JOptionPane.showMessageDialog(rootPane, scrollPane, "Schedule",  
                                                        JOptionPane.DEFAULT_OPTION);
                }

                
                
            }
                 
            
            catch (ScheduleGenerationException e) {
                
                int selection =JOptionPane.showOptionDialog(rootPane, "The schedule can not be made. Please contact the staff vet to request a change in requirements.",
                "Schedule Build Fail", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, otherOptions, otherOptions[0]);
                
                if (selection == 1) {
                    String failureMessage = "";
                    
                    failureMessage += "\nTask that failed: ";
                    for (Item item : schedule.getItemFailures()) {
                        ArrayList<Integer> openHours = schedule.findOpenHours(item);
                        String selectedHour = "";
                        while (true) {
                            selectedHour = JOptionPane.showInputDialog(rootPane, "The following task failed to be scheduled"+ failureMessage + item.getTaskName()
                            +"\nPlease input a new hour to move " + item.getTaskName() + " to." + "\nThe available hours are: " + openHours + "\nPlease Input an hour with the following format: 7:00 -> \"7\"" );
                            if (selectedHour == null) {
                                cancelled = true;
                                break;
                            }
                            try {
                                int hour = Integer.parseInt(selectedHour);
                                if (openHours.contains(hour)) {
                                    // not working properly
                                    schedule.addTask(hour, item);
                                    connect.update(item.getTreatmentID(), hour);
                                    showYes = true;
                                    counter++;
                                    break;
                                }
                                String[] optionsInvalidHour = {"OK"};
                                JOptionPane.showOptionDialog(rootPane, "Invalid hour input.", "Warning",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, optionsInvalidHour, optionsInvalidHour[0]);
                            }
                            
                            catch (NumberFormatException ex) {
                                String[] optionsInvalidHour = {"OK"};
                                JOptionPane.showOptionDialog(rootPane, "Invalid hour input.", "Warning",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, optionsInvalidHour, optionsInvalidHour[0]);
                            }
                        }
                        if (cancelled) {
                            break;
                        }
                    }
                    if (showYes && (schedule.getItemFailures().size() == counter)) {
                        JTextArea textArea = new JTextArea(schedule.format());
                        JScrollPane scrollPane = new JScrollPane(textArea);  
                        textArea.setLineWrap(true);  
                        textArea.setWrapStyleWord(true); 
                        scrollPane.setPreferredSize( new Dimension( 700, 600 ) );
                        JOptionPane.showMessageDialog(rootPane, scrollPane, "Schedule",  
                                                            JOptionPane.DEFAULT_OPTION);
                    }
                }
            }
    }
    
    public void mouseEntered(MouseEvent event){
        
    }

    public void mouseExited(MouseEvent event){
    }

    public void mousePressed(MouseEvent event){
        
    }

    public void mouseReleased(MouseEvent event){
        
    }
    
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);        
        });
    }

    
    public void mouseClicked(MouseEvent e) {
        
    }
        
}


