/*James Hsiao 5/23
This class builds the GUi of my tuner app and impliments the functionalities of the buttons and sliders. 
it also call on methods from its inner class and sound.java to make adjustment to the output of the program. 

 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Vector;
import javazoom.jl.converter.*;

public class Mygui extends JFrame {

    //fields of instance varaibles
    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 200;
    private int selected;
    JPanel volumePanel;
    JPanel radioButtonPanel;
    private int volumeValue;

    //setter for the initial value
    public void setVolumevalue(int v) {
        this.volumeValue = v;
    }
    
    //setter for the radiobutton "selected"
    public void setSelected(int s) {
        this.selected = s;
    }
    
    //getter for the radiobuttons "selected"
    public int getSelected() {
        return this.selected;
    }

    
    
    
    //constructor for the GUI
    public Mygui() {
        // call to the superclass constructor creates default border layout
        super("My GUI");
        setTitle("Tuner");
        this.volumeValue = 5;
        Sound c = new Sound("c.wav");
        // set the size of the window
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // set the application to exit when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel quitPanel = new JPanel();
        JButton myButton = new JButton("Close");
        //adding the play button
        JButton playButton = new JButton("Play");

        myButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        /*When the play button is clicked, it'll call two method on the sound file c. the 
        two methods will take input from the instance variables volumeValue and selected, which in terms alter the 
        volume and pitch of the file respectively. then the program proceeds to play the finished pitch*/
        playButton.addActionListener((ActionEvent event)
                -> {
            pitchRatio(selected, c);
            c.changeVol(volumeValue);
            c.blockingPlay();
        });

        //adddin the components to the gui
        quitPanel.add(myButton);
        quitPanel.add(playButton);
        add(quitPanel, BorderLayout.SOUTH);

        //calling method to create the volume slider
        createVolumeSlider();

        //calling method teo create the radio button panel for the pitch buttons
        createPitchButtonPanel();

        // make the window visible
        setVisible(true);
    }

    /*this is my method for creating the volume slider. I also incorporated the getter for the
    instance variable volumeValue within the method
     */
    private void createVolumeSlider() {
        volumePanel = new JPanel();
        volumePanel.setLayout(new GridLayout(3, 1));

        JLabel volumeLabel = new JLabel("Volume");
        JLabel volumeMarker = new JLabel();
        JSlider volumeSlider = new JSlider(0, 10);

        volumeSlider.setMajorTickSpacing(1);

        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(false);
        //labeling the slider

        //set the instance variable equal to the value of the volume slider. 
        volumeSlider.addChangeListener((ChangeEvent event)
                -> {
            int volume = volumeSlider.getValue();
            volumeMarker.setText(Integer.toString(volume));
            volumeValue = volume;
        }
        );

        //adding the components to the gui. 
        volumePanel.add(volumeLabel);
        volumePanel.add(volumeSlider);
        volumePanel.add(volumeMarker);
        add(volumePanel, BorderLayout.EAST);

    }

    /*method to convert the actual picth base on which radio button is selected. 
    this also uses the if/else logic to convert the int 0-12 to approriate ratio that correlates
    with pitch.
     */
    private Sound pitchRatio(int i, Sound c) {
        if (i == 0) {
            c.changeFreq(440.0 / 523.251);
        } else if (i == 1) {
            c.changeFreq(466.164 / 523.251);
        } else if (i == 2) {
            c.changeFreq(493.883 / 523.251);
        } else if (i == 3) {
            c.changeFreq(1.0);
        } else if (i == 4) {
            c.changeFreq(554.365 / 523.251);
        } else if (i == 5) {
            c.changeFreq(587.330 / 523.251);
        } else if (i == 6) {
            c.changeFreq(622.254 / 523.251);
        } else if (i == 7) {
            c.changeFreq(659.255 / 523.251);
        } else if (i == 8) {
            c.changeFreq(698.456 / 523.251);
        } else if (i == 9) {
            c.changeFreq(739.989 / 523.251);
        } else if (i == 10) {
            c.changeFreq(783.991 / 523.251);
        } else if (i == 11) {
            c.changeFreq(830.609 / 523.251);
        } else {
            c.changeFreq(880.0 / 523.251);
        }

        return c;
    }

    private void createPitchButtonPanel() {
        // Call the JPanel constructor.
        radioButtonPanel = new JPanel();

        // set the layout of the buttons in 2*6 grid
        radioButtonPanel.setLayout(new GridLayout(2, 6));
        // Create a ButtonGroup to only allow one selection at a time
        ButtonGroup pitchGroup = new ButtonGroup();

        //created an array of names for the button 
        String[] iconNames = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};

        // Create an array to hold the radio buttons
        JRadioButton[] pitchButtons = new JRadioButton[12];

        // setting the buttons to the names in the string array while initiating them
        for (int i = 0; i < pitchButtons.length; i++) {
            // Create the radio button
            pitchButtons[i] = new JRadioButton(iconNames[i]);
            // Add the button to the panel
            radioButtonPanel.add(pitchButtons[i]);
            // Add the button to radioGroup
            pitchGroup.add(pitchButtons[i]);
            // Register events for the buttons
            pitchButtons[i].addItemListener(new PitchButtonHandler(i));

            //add the panel to the center of the border layout
            add(radioButtonPanel, BorderLayout.CENTER);
            pitchButtons[0].setSelected(true);
        }

    }

    //inner class to control which pitch to play. This also includes the setter for the "selected" instance variable.
    public class PitchButtonHandler implements ItemListener {

        //instance variable to identify the buttons
        private int selectedButton;

        // constructor that sets the number of the button that this particular
        // instance of RadioButtonHandler handles
        public PitchButtonHandler(int i) {
            selectedButton = i;
        }

        //set the instance varaible equal to the radio button selected
        @Override
        public void itemStateChanged(ItemEvent event) {
            setSelected(selectedButton);
            getSelected();
        }

    }
}
