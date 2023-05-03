import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class RadarGUI extends JPanel implements ActionListener{
    private static boolean radarOn = true;
    private final double[][] radarCoords = new double[50][2];
    private final AirController tower = new AirController(5, 5, 2, 2);
    private double angle = (Math.PI / 2);
    private static String[][] tableString = new String[3][4];

    public RadarGUI() {
        Timer timer = new Timer(10, this); // create a timer that fires every 10ms
        timer.start(); // start the timer
    }

    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < radarCoords.length; i++){
            double offset = (double) i/100;
            //keep the radar line inside the circle
            radarCoords[i][0] = 200 + 200 * Math.cos(angle-offset);
            radarCoords[i][1] = 200 - 200 * Math.sin(angle-offset);
        }
        angle+=0.02;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //set black background
        Color background = new Color(0,0,0);
        g2d.setColor(background);
        g2d.fillRect(0, 0, 400, 400);

        //set radar circles
        g2d.setStroke(new BasicStroke(4));
        Color stroke = new Color(0, 255, 64);
        g2d.setColor(stroke);
        g2d.drawOval(175,175, 50,50);
        g2d.drawOval(100,100, 200,200);
        g2d.drawOval(0,0, 400,400);

        // draw the planes
        for(Aircraft a : tower.planes) {
            int fade = Math.max(a.getFade(), 0);
            double[] tempCoords;
            Color c = a.getColor();
            if (radarOn) {
                tempCoords = a.getLastSeen();
            } else {
                tempCoords = a.getCoords();
                fade = 255;
            }
            g2d.setPaint(new Color(c.getRed(), c.getGreen(), c.getBlue(), fade));
            Ellipse2D.Double p = new Ellipse2D.Double(tempCoords[0], tempCoords[1], 5, 5);
            g2d.fill(p);
            a.hitByRadar(radarCoords[25]);
            a.move();
            a.decreaseFade(3);
            tower.calculateDist(a);
        }
        if(radarOn) {
            //set radar line with blur
            g2d.setStroke(new BasicStroke(3));
            for (int i = 0; i < radarCoords.length; i++) {
                g2d.setColor(new Color(0, 255, 64, 255 - (i * 5)));
                Line2D.Double line = new Line2D.Double(200, 200, radarCoords[i][0], radarCoords[i][1]);
                g2d.draw(line);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Radar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(415, 554);

        // create a BoxLayout with a vertical orientation
        BoxLayout layout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        // set the layout manager for the JFrame's content pane
        frame.getContentPane().setLayout(layout);

        // create the radar panel
        RadarGUI radar = new RadarGUI();
        radar.setPreferredSize(new Dimension(400,450));

        // create a JPanel to hold the controls
        JPanel controlPanel = new JPanel();

        // create and add the radio buttons
        JRadioButton button1 = new JRadioButton("Radar On");
        JRadioButton button2 = new JRadioButton("Radar Off");
        ButtonGroup group = new ButtonGroup();
        group.add(button1);
        group.add(button2);
        button1.setSelected(true);
        button1.addChangeListener(e -> radarOn = button1.isSelected());
        controlPanel.add(button1);
        controlPanel.add(button2);

        // create and add the print button
        JButton printButton = new JButton("3 Closest Aircraft");
        controlPanel.add(printButton);

        JPanel tablePanel = new JPanel();
        DefaultTableModel model = new DefaultTableModel(tableString, new String[]{"Index", "Type", "Color", "Distance"});

        // create the JTable with the data and column names
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(400,80));

        printButton.addActionListener(e -> {
            tableString = radar.tower.topThree();
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 4; j++) {
                    model.setValueAt(tableString[i][j], i, j);
                    model.fireTableCellUpdated(i, j);
                }
            }

        });

        // add the panels to the frame
        frame.getContentPane().add(radar);
        frame.getContentPane().add(controlPanel);
        frame.getContentPane().add(tablePanel);
        frame.setVisible(true);
    }
}