
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI extends JFrame
{
    private Container contents;  

    private ButtonHandler bh;
    private Game game;

    private JTextField timer1, timer2, name1, name2;   
    private JLabel reminder;

    //This JLabels can show whether the chess is alive or dead.   
    private final JLabel[][] states = new JLabel[2][8];    //[0][] means chess of blue(P1) player    [1][] means chess of red(P2) player
    /*
     * index 0 -> elephant 
     * index 1 -> lion
     * index 2 -> tiger
     * index 3 -> leopard
     * index 4 -> wolf
     * index 5 -> dog
     * index 6 -> cat
     * index 7 -> rat
     */

    private JButton btnBack;        // directly end the game and return to menu (no one win)
    private JButton btnRule;        // rule for player who doesn't know
    private JButton btnTool;        // players can use tool in their own turn
    private JButton btnFail;         // end the game (the person who clicks will lose the game)

    private final Color color = new Color(57,19,0);      // General color of background

    public GameUI()
    {
        super("Game");
        contents = getContentPane();  

        BorderLayout bl = new BorderLayout( );       
        contents.setLayout( bl );

        game = new Game(this);
        bh = new ButtonHandler();

        contents.add(TopPane(), bl.NORTH);
        contents.add(BottomPane(), bl.SOUTH);
        contents.add(game.paintButtons(), SwingConstants.CENTER);      

        setSize(440, 620);

        contents.setBackground(color);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);                     
    }

    public JPanel TopPane(){
        JPanel panel = new JPanel();        
        panel.setLayout(new BorderLayout() );

        JPanel top = new JPanel();  //for two buttons
        top.setLayout(new BorderLayout() ); 
        top.setBackground(color);

        JPanel center = new JPanel(); //for user Info
        center.setLayout(new BorderLayout() );
        center.setBackground(color);

        JPanel bottom = new JPanel();  //for state of Blue(P1)
        bottom.setBackground(color);

        //Top:
        JPanel topLeft = new JPanel();
        topLeft.setBackground(color);

        btnBack = new JButton("");
        btnBack.setIcon(new ImageIcon(getClass().getResource("Picture/Back.png")));
        btnBack.setPreferredSize(new Dimension(25,25));
        btnBack.addActionListener(bh);
        topLeft.add(btnBack);

        btnRule = new JButton("");
        btnRule.setIcon(new ImageIcon(getClass().getResource("Picture/Rule.png")));
        btnRule.setPreferredSize(new Dimension(25,25));
        btnRule.addActionListener(bh);
        topLeft.add(btnRule);

        top.add(topLeft, BorderLayout.WEST);

        //Center:
        JPanel centerLeft = new JPanel();
        JPanel centerRight = new JPanel();

        centerLeft.setLayout(new BorderLayout());
        centerRight.setLayout(new BorderLayout());

        centerLeft.setBackground(color);
        centerRight.setBackground(color);

        name1 = new JTextField("Blue ");
        name2 = new JTextField(" Red");
        timer1 = game.paintTimer(1);
        timer2 = game.paintTimer(2);

        name1.setEditable(false);
        name2.setEditable(false);
        timer1.setEditable(false);
        timer2.setEditable(false);

        name1.setForeground(Color.WHITE);
        name2.setForeground(Color.WHITE);
        timer1.setForeground(Color.WHITE);
        timer2.setForeground(Color.WHITE);

        name1.setBackground(color);
        name2.setBackground(color);
        timer1.setBackground(color);
        timer2.setBackground(color);

        timer1.setBorder(null);
        timer2.setBorder(null);

        centerLeft.add(name1, BorderLayout.WEST);
        centerLeft.add(timer1, BorderLayout.EAST);
        centerRight.add(name2, BorderLayout.EAST);
        centerRight.add(timer2, BorderLayout.WEST);

        center.add(centerLeft, BorderLayout.WEST);
        center.add(centerRight, BorderLayout.EAST);

        //Bottom: 
        bottom = game.paintStates(0);        

        //Final(total):
        panel.add(top, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);        
        return panel;
    }

    public JPanel BottomPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout() );
        panel.setBackground(color);        

        JPanel top = new JPanel();  //for state of Red(P2)
        JPanel bottom = new JPanel();   //for tool button and "give up" button

        bottom.setLayout(new BorderLayout() );

        top.setBackground(color);
        bottom.setBackground(color);

        //Top:
        top = game.paintStates(1);

        //Bottom:        
        btnTool = new JButton("");
        btnTool.setIcon(new ImageIcon(getClass().getResource("Picture/ToolButton.png")));
        btnTool.setPreferredSize(new Dimension(60,55));
        btnTool.addActionListener(bh);
        bottom.add(btnTool, BorderLayout.WEST);

        reminder = new JLabel(new ImageIcon(getClass().getResource("Picture/Reminder.png")));
        bottom.add(reminder);

        //Final(total):
        panel.add(top, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH); 
        return panel;
    }

    public JFrame getUI(){
        return this;
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent ae){
           try{
                if(ae.getSource() == btnBack){                  
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit? (All the record of this field will lose)", "Confirm", JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION){
                        dispose();
                        MenuUI menu = new MenuUI();
                        menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    }
                }
                else if(ae.getSource() == btnRule){
                    GameRule rule = new GameRule();
                    rule.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
           }catch(Exception e){
               JOptionPane.showMessageDialog(null, "Unknown error G_A_M_E", "Error", JOptionPane.ERROR_MESSAGE);
           }
        }
    } 

    private class GameRule extends JDialog{
        private Container contents;
        private JTextArea rule;
        private String text;
        public GameRule(){
            super(getUI(), "Rule", true);
            contents = getContentPane();
            contents.setLayout(new FlowLayout());

            text = "This is an animal chess game, the strength of different chesses are different.\n" +
            "Elephant > Lion > Tiger > Leopard > Wolf > Dog > Cat > Rat > Elephant.\n" +
            "Magma will spread after 20 turns, which can kill any chess.\n" +
            "Also, if no chess is killed in 9 turns, magma will immediately begin spreading.";
            rule = new JTextArea(text);
            rule.setEditable(false);
            rule.setBackground(null);

            contents.add(rule);
            setSize(530,100);
            this.setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        }
    }
}

