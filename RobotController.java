/*
Jacob Calkins
11294637
03/31/2014
Assignment 4: Robot Teleoperation
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;

public class RobotController extends JFrame implements ActionListener{
	private JButton takeoff = new JButton("Takeoff");
	private JButton land = new JButton("Land");
	private JButton up = new JButton("Up");
	private JButton left = new JButton("Left");
	private JButton forward = new JButton("Forward");
	private JButton back = new JButton("Backward");
	private JButton right = new JButton("Right");
	private JButton down = new JButton("Down");
	private JButton clockWise = new JButton("Rotate Left");
	private JButton counterClockWise = new JButton("Rotate Right");
	public PrintWriter out;
	
	public RobotController(){
		try{
			Socket socket = new Socket("139.78.141.250", 9095);
			out = new PrintWriter(socket.getOutputStream());
			out.print("hello robot");
			out.flush();
		} catch(UnknownHostException ex){
			System.out.println("Unknown Host.");
		} catch(IOException e){
			System.out.println("IOException");
		} 

		takeoff.addActionListener(this);
		land.addActionListener(this);
		up.addActionListener(this);
		left.addActionListener(this);
		forward.addActionListener(this);
		back.addActionListener(this);
		right.addActionListener(this);
		down.addActionListener(this);
		clockWise.addActionListener(this);
		counterClockWise.addActionListener(this);

		setVisible(true);
		setTitle("Robot Controller");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setSize(screenSize.width/4,screenSize.height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3,1));

		JPanel top = new JPanel();
		top.setLayout(new GridLayout(2,1));
		top.add(takeoff);
		top.add(land);
		this.add(top);
		
		JPanel middle = new JPanel();
		middle.setLayout(new BorderLayout());
		middle.add(up,BorderLayout.PAGE_START);
		middle.add(left,BorderLayout.LINE_START);
		
		JPanel subMiddle = new JPanel();
		subMiddle.setLayout(new BorderLayout());
		subMiddle.add(forward,BorderLayout.PAGE_START);
		subMiddle.add(back,BorderLayout.PAGE_END);
		
		middle.add(subMiddle,BorderLayout.CENTER);
		middle.add(right,BorderLayout.LINE_END);
		middle.add(down,BorderLayout.PAGE_END);
		this.add(middle);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(2,1));
		bottom.add(clockWise);
		bottom.add(counterClockWise);
		this.add(bottom);
		repaint();
	}

	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if(source == takeoff){
			String takeoff_msg = "{\"op\":\"publish\",\"topic\":\"/ardrone/takeoff\",\"msg\":{}}";
			out.print(takeoff_msg);
			out.flush();
		} else if(source == land){
			String land_msg = "{\"op\":\"publish\",\"topic\":\"/ardrone/land\",\"msg\":{}}";
			out.print(land_msg);
			out.flush();
		} else if(source == up){
			String up_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":0,\"z\":0.5},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}";
			out.print(up_msg);
			out.flush();
		} else if(source == left){
			String left_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":0.5,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}";
			out.print(left_msg);
			out.flush();
		} else if(source == forward){
			String forward_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0.5,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}";
			out.print(forward_msg);
			out.flush();
		} else if(source == back){
			String back_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":-0.5,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}";
			out.print(back_msg);
			out.flush();
		} else if(source == right){
			String right_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":-0.5,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}";
			out.print(right_msg);
			out.flush();
		} else if(source == down){
			String down_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":0,\"z\":-0.5},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}";
			out.print(down_msg);
			out.flush();
		} else if(source == clockWise){
			String clockWise_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":1}}}";
			out.print(clockWise_msg);
			out.flush();
		} else{
			String counterClockWise_msg = "{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":-1}}}";
			out.print(counterClockWise_msg);
			out.flush();
		}
		try{
			Thread.sleep(1000L);
		} catch(Exception e){}
		out.print("{\"op\":\"publish\",\"topic\":\"/cmd_vel\",\"msg\":{\"linear\":{\"x\":0,\"y\":0,\"z\":0},\"angular\":{\"x\":0,\"y\":0,\"z\":0}}}");
		out.flush();
	}
}