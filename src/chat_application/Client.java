package chat_application;

import static chat_application.Server.Vertical;
import static chat_application.Server.f;
import static chat_application.Server.formatLabel;
import static chat_application.Server.output;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;

public class Client  implements ActionListener
{

    public JTextField textField;
    static public JPanel panel2;
    static Box Vertical=Box.createVerticalBox();
    static DataOutputStream output1;
    
    static JFrame f=new JFrame();
    Client()
    {
        f.setLayout(null);
        
        JPanel panel1= new JPanel();
        panel1.setBackground(new Color(7,94,84));
        panel1.setBounds(0,0,450,70);
        panel1.setLayout(null);
        f.add(panel1);
        
        ImageIcon image1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image image2=image1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(image2);
        JLabel back = new JLabel(image);
        back.setBounds(5,20,25,25);
        panel1.add(back);
        
        back.addMouseListener(new MouseAdapter()
                {
                   @Override
                   public void mouseClicked(MouseEvent ME)
                {
                    System.exit(0);
                }
                }
        );
        
        ImageIcon image4=new ImageIcon(ClassLoader.getSystemResource("icons/v.jpg"));
        Image image5=image4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon image6 = new ImageIcon(image5);
        JLabel profile = new JLabel(image6);
        profile.setBounds(40,10,50,50);
        panel1.add(profile);
        
        ImageIcon image7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image image8=image7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon image9 = new ImageIcon(image8);
        JLabel video = new JLabel(image9);
        video.setBounds(300,20,30,30);
        panel1.add(video);
        
        ImageIcon image10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image image11=image10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon image12 = new ImageIcon(image11);
        JLabel tele = new JLabel(image12);
        tele.setBounds(360,20,30,30);
        panel1.add(tele);
        
        ImageIcon image13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image image14=image13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon image15 = new ImageIcon(image14);
        JLabel more = new JLabel(image15);
        more.setBounds(420,20,10,25);
        panel1.add(more);
        
        JLabel name=new JLabel("Vaibhav");
        name.setBounds(110,15,100,10);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        panel1.add(name);
        
        JLabel status=new JLabel("Online");
        status.setBounds(110,35,100,10);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        panel1.add(status);
        
        panel2 = new JPanel();
        panel2.setBounds(5,75,440,570);
        JScrollPane scrollPane = new JScrollPane(panel2);
        scrollPane.setBounds(5,75,440,570);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        f.add(scrollPane);
        
        textField = new JTextField();
        textField.setBounds(5,655,310,40);
        textField.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(textField);
        
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);
        
        f.setSize(450,700);
        f.setUndecorated(true);
        f.setLocation(800,50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            String text=textField.getText();
            //System.out.println(text);

            JPanel outputPanel = formatLabel(text);


            panel2.setLayout(new BorderLayout());

            JPanel right=new JPanel(new BorderLayout());
            right.add(outputPanel,BorderLayout.LINE_END);
            Vertical.add(right);
            Vertical.add(Box.createVerticalStrut(17));

            panel2.add(Vertical,BorderLayout.PAGE_START);

            output1.writeUTF(text);

            textField.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception z)
        {
            z.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String text)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel(text);
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        /*Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));*/
        return panel;
    }
    
   public static void main(String[] args)
   {
       new Client();
       
       try
       {
          Socket socket=new Socket("127.0.0.1",4545);
          DataInputStream input = new DataInputStream(socket.getInputStream());
          output1 = new DataOutputStream(socket.getOutputStream());
          while(true)
            {
                panel2.setLayout(new BorderLayout());
                String message=input.readUTF();
                JPanel panel =formatLabel(message);
                   
                JPanel left=new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                Vertical.add(left);
                Vertical.add(Box.createVerticalStrut(15));
                panel2.add(Vertical,BorderLayout.PAGE_START);
                f.validate();
            }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
          
   }
}