/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author MMB
 */
public class MyProject extends JFrame implements ActionListener 
{
   JButton jb[] = new JButton[3];
   JTextField  jAT[],jBT[],jP[];
   JLabel  jl[],jl1,jl2,jl3, jl4;
   JPanel  jp,jp1,jp2;
   Container con;
   int  k,p;
   String str[] = {"SUBMIT","RESET","EXIT"};
   String str1[] = {"   Process","   Arival Time","Burs Time","Priorty","Waiting Time","Finish Time","Turn Around Time","    Response Time"};
   public MyProject()
	{ 
           super("Priority (Non-Preemptive) scheduling");
	   con = getContentPane();
           try
           {
	   k = Integer.parseInt(JOptionPane.showInputDialog("Enter number of process"));
           }
           catch(Exception e)
           {
               System.out.println("Validation error");
           }
           if(k > 0)
           {
                jl1 = new JLabel("      Process");
                jl2 = new JLabel("Arival Time");
                jl3 = new JLabel("CPU Burst Time");
                jl4 = new JLabel("Priority");

                jl = new JLabel[k];
                jAT = new JTextField[k];
                jBT = new JTextField[k];
                jP = new JTextField[k];


                for(int i=0;i<k;i++)
                {
                     jl[i] = new JLabel("      process"+(i+1));
                     jAT[i]  = new JTextField(10);
                     jBT[i]  = new JTextField(10);
                     jP[i]  = new JTextField(10);
                }
                
                for(int i=0;i<3;i++)
                {
                        jb[i] = new JButton(str[i]);
                }

                con.setLayout(new  GridLayout(k+2,4));
                con.add(jl1);
                con.add(jl2);
                con.add(jl3);
                con.add(jl4);
                
                for(int i=0;i<k;i++)
                {
                  con.add(jl[i]);
                  con.add(jAT[i]);
                  con.add(jBT[i]);
                  con.add(jP[i]);
                }
                
                JLabel nothing = new JLabel("");
                con.add(nothing);
                
                for(int i=0;i<3;i++)
                {
                        con.add(jb[i]);
                        jb[i].addActionListener(this);
                }
           }
           else
           {
                System.exit(0);
           }
	}//end of constructor

	public void actionPerformed(ActionEvent ae)
	{
                int AT[] = new int[k];
                int P[] = new int[k];    
                int BT[] = new int[k];
                int FT[] = new int[k];
                int WT[] = new int[k];
                int TAT[] = new int[k];
                int RT[] = new int[k];
                float sumW = 0;
                float sumR = 0;
                float sumT = 0;
                float avgW;
                float avgR;
                float avgT;
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
                jp = new JPanel();
		jp1 = new JPanel();
                jp2 = new JPanel();
		jp.setLayout(new GridLayout(k+1,7));
		jp1.setLayout(new BoxLayout(jp1, BoxLayout.Y_AXIS));
                jp2.setLayout(new FlowLayout());

	if(ae.getSource() == jb[2])
        {
            System.exit(0);
        }
	  
	else if(ae.getSource() == jb[1])
	{
		setVisible(false);
		MyProject  window = new MyProject();
		window.setSize(400,300);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
        else if(ae.getSource() == jb[0])
        {
            try
            {
                        for(int i=0;i<k;i++)
                        {
                            AT[i]=Integer.parseInt(jAT[i].getText());
                            BT[i]=Integer.parseInt(jBT[i].getText());
                            P[i]=Integer.parseInt(jP[i].getText());
                        }
                        int unFAT[] = AT;
                        int endLoop = 0;
                        for(int time=0;endLoop < k;time++)
                        {
                            int[] avp = Avp(time,unFAT);
                            int flag = avp[k+1];
                            if(avp[k]>0)
                            {
                                for(int i=0;i<k;i++)
                                {
                                   
                                    if(avp[i]==1)
                                    {
                                        if(P[flag]>P[i])
                                        {
                                            flag = i;
                                        }
                                    }
                                }
                                RT[flag] = time-AT[flag];
                                WT[flag] = RT[flag];
                                FT[flag] = time+BT[flag];
                                TAT[flag] = FT[flag]-AT[flag];
                                sumW += WT[flag];
                                sumR += RT[flag];
                                sumT += TAT[flag];
                                time += (BT[flag]-1);
                                unFAT[flag]=1000000005;
                                endLoop++;
                            }
                            
                        }
                        avgW=sumW/k;
                        avgR=sumR/k;
                        avgT=sumT/k;
                  
                        // End of clculate
                        jp.setBackground(new Color(142,203,140));
                        jp1.setBackground(new Color(182,198,180));
                        jp2.setBackground(new Color(182,198,180));
                        for (int i=0;i<8;i++ )
                        {
                            jp.add(new JLabel(str1[i]));
                        }
			for (int i=0;i<k;i++)
			{
			  jp.add(new JLabel("   process"+(i+1)));
			  jp.add(new JLabel("   "+Integer.parseInt(jAT[i].getText())));
			  jp.add(new JLabel(""+Integer.parseInt(jBT[i].getText())));
                          jp.add(new JLabel(""+Integer.parseInt(jP[i].getText())));
			  jp.add(new JLabel(""+WT[i]));
			  jp.add(new JLabel(""+FT[i]));
			  jp.add(new JLabel(""+TAT[i]));
		          jp.add(new JLabel("    "+RT[i]));
			}
                        
			String str2 = "   Average Waiting Time is "+ avgW;
                        String str3 = "   Average Response Time is "+ avgR;
                        String str4 = "   Average Turn Around Time is "+ avgT;
                        jp1.add(new JLabel(str2));
                        jp1.add(new JLabel(str3));
                        jp1.add(new JLabel(str4));
			main.add(jp,BorderLayout.NORTH);
			main.add(jp1,BorderLayout.CENTER);
                        main.add(jp2,BorderLayout.SOUTH);
                        
                        //Gantt Chart
                        JLabel guntt[]=new JLabel[k];
                        String g[]=new String[k];
                        int x;
                        int[] wid = new int[k];
                        int[] CRT = RT;
                        for(int i=0;i<k;i++)
                        { 
                            x = min(CRT);
                    
                            wid[i] = x;
                            g[i] = new String("  p"+(x+1));
                            CRT[x]=1000000;
                        }
                        for(int i=0;i<k;i++)
                        {
                            guntt[i] = new JLabel(g[i]);
                            guntt[i].setPreferredSize(new Dimension(20*BT[wid[i]], 50));
                            guntt[i].setBackground(new Color(((193+(i*100))%256),((163+(i*150))%256), ((92+(i*50))%256)));
                            guntt[i].setOpaque(true);
                            jp2.add(guntt[i]);
                        }
                        JOptionPane.showMessageDialog(null,main,"Answer",JOptionPane.PLAIN_MESSAGE);
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,main,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }     
    }//END ACTION PERFORMED
        
	public static void main(String[] args)
	{
            MyProject  window = new MyProject();
	    window.setSize(400,300);
	    window.setVisible(true);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end main

    private int min(int[] arr) 
    {
        int pos = 0;
        for(int i=1;i<arr.length;i++)
        {
            if(arr[pos]>arr[i])
            {
                pos = i;
            }
        }
        return pos;
    }

    private int[] Avp(int t,int[] AT) 
    {
        int[] arr = new int[k+2];
        int c=0,pos=0;
        for(int i=0;i<k;i++)
        {
            if(AT[i]<=t)
            {
                arr[i]=1;
                c++;
                pos=i;
            }
            else
            {
                arr[i]=0;
            }
        }
        arr[k] = c;
        arr[k+1] = pos;
        return arr;
    }

    
}
