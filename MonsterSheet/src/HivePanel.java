/*
 * HivePanel.java -- This is a JPanel that has button elements that help show and change stats of all Hive Creatures!
 * Jason Melnik
 * 2/8/2019
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class HivePanel extends JPanel{
	
	private static JButton thrallButton, acolyteButton;
	private static JLabel noise, weight, speed, skill, weapon, location, color, leader, home, goals, war, life, picurl;
	private static JTextField noiseT, weightT, speedT, skillT, weaponT, locationT, colorT, leaderT, homeT, goalsT, warT, lifeT, picurlT;
	private static ArrayList<JButton> monsterButtons;
	private static ArrayList<HiveCreature> allHive;
	private static JTextField[] textList;
	private static JLabel[] statLabels;
	private static JLabel picture;
	private HiveCreature thrall, acolyte;
	private static HiveCreature currentEditing;
	private JTextField editing;
	private MonsterPanel env;
	private static Graphics graphics;
	private static HivePanel ths;
	
	public HivePanel(){
		ths = this;
		graphics = this.getGraphics();
		
		thrall = new HiveCreature("Thrall");
		acolyte = new HiveCreature("Acolyte");
		
		thrall.setImage("src//thrall.jpg");
		acolyte.setImage("src//acolyte.jpg");
		
		allHive = new ArrayList<HiveCreature>();
		allHive.add(thrall);
		allHive.add(acolyte);
		
		setBackground(Color.BLACK);
		
		noiseT = new JTextField("");
		weightT = new JTextField("");
		speedT = new JTextField("");
		skillT = new JTextField("");
		weaponT = new JTextField("");
		locationT = new JTextField("");
		colorT = new JTextField("");
		leaderT = new JTextField("");
		homeT = new JTextField("");
		goalsT = new JTextField("");
		warT = new JTextField("");
		lifeT = new JTextField("");
		picurlT = new JTextField("");
		textList = new JTextField[]{noiseT, weightT, speedT, skillT, weaponT, locationT, colorT, leaderT, homeT, goalsT, warT, lifeT, picurlT};
		
		thrallButton = new JButton("Thrall");
		acolyteButton = new JButton("Acolyte");
		
		noise = new JLabel("Noise:");
		weight = new JLabel("Weight:");
		speed = new JLabel("Speed:");
		skill = new JLabel("Skills:");
		weapon = new JLabel("Weapon:");
		location = new JLabel("Locations:");
		color = new JLabel("Color:");
		leader = new JLabel("Leader:");
		home = new JLabel("Home:");
		goals = new JLabel("Goals:");
		war = new JLabel("In war with:");
		life = new JLabel("Lifespan:");
		picurl = new JLabel("Picture:");
		
		monsterButtons = new ArrayList<JButton>();
		statLabels = new JLabel[13];
		
		monsterButtons.add(thrallButton);
		monsterButtons.add(acolyteButton);
    	
		statLabels[0] = noise;
    	statLabels[1] = weight;
    	statLabels[2] = speed;
    	statLabels[3] = skill;
    	statLabels[4] = weapon;
    	statLabels[5] = location;
    	statLabels[6] = color;
    	statLabels[7] = leader;
    	statLabels[8] = home;
    	statLabels[9] = goals;
    	statLabels[10] = war;
    	statLabels[11] = life;
    	statLabels[12] = picurl;
    	
    	ButtonListener listener = new ButtonListener();
    	
    	int counterx = 10;
    	int countery = 10;
    	for(JButton button : monsterButtons){
    		button.setBorderPainted(false);
        	button.setBackground(getBackground());
        	button.setForeground(Color.RED);
        	button.setFocusable(false);
        	button.addActionListener(listener);
        	button.setBounds(counterx, countery, button.getPreferredSize().width, button.getPreferredSize().height);
        	counterx += button.getPreferredSize().width;
        	if(counterx + button.getPreferredSize().width > 750) {
        		counterx = 10;
        		countery += 30;
        	}
        	add(button);
    	}
    	
    	setLayout(null);
    	
    	editing = new JTextField();
		editing.addKeyListener(new KeysListener());
		
    	add(editing);
    	
    	
    	
    	picture = new JLabel(new ImageIcon());
    	picture.setBounds(540, 250, 150, 150);
    	
    	add(picture);
	}
	
	public static void addCreature(HiveCreature monster) {
		allHive.add(monster);
		JButton creature = new JButton(monster.getType());
		monsterButtons.add(creature);
		updateButtons();
	}
	
	public static void updateButtons() {
		for(JButton button : monsterButtons){ths.remove(button);}
		
		int counterx = 10;
    	int countery = 10;
    	for(JButton button : monsterButtons){
    		button.setBorderPainted(false);
        	button.setBackground(ths.getBackground());
        	button.setForeground(Color.RED);
        	button.setFocusable(false);
        	button.addActionListener(new ButtonListener());
        	
        	if(counterx + button.getPreferredSize().width > 750) {
        		counterx = 10;
        		countery += 30;
        	}
        	
        	button.setBounds(counterx, countery, button.getPreferredSize().width, button.getPreferredSize().height);
        	counterx += button.getPreferredSize().width;
        	ths.add(button);
    	}
	}
	
	public void update() {
		update(this.getGraphics());
	}
	
	private static class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			for(int x = 0; x < monsterButtons.size(); x++) {
				if(monsterButtons.get(x) == event.getSource()) {
					ths.remove(picture);
					picture.setIcon(allHive.get(x).bodyImage());
					picture.setBounds(540, 250, 150, 150);
					ths.add(picture);
					int countery = 200;
			    	for(JLabel label : statLabels){
			    		label.setBackground(Color.BLACK);
			    		label.setForeground(Color.BLUE);
			        	label.setBounds(0, countery, 80, label.getPreferredSize().height);
			        	countery += 20;
			        	ths.add(label);
			    	}
			    	
			    	noiseT.setText(allHive.get(x).noise());
			    	weightT.setText(allHive.get(x).weight()); 
			    	speedT.setText(allHive.get(x).speed());  
			    	skillT.setText(allHive.get(x).skills()); 
			    	weaponT.setText(allHive.get(x).weapon()); 
			    	locationT.setText(allHive.get(x).locationsFoundIn());  
			    	colorT.setText(allHive.get(x).commonColors()); 
			    	leaderT.setText(allHive.get(x).leaderOfSpecies()); 
			    	homeT.setText(allHive.get(x).homeWorld()); 
			    	goalsT.setText(allHive.get(x).goals());  
			    	warT.setText(allHive.get(x).atWarWith());  
			    	lifeT.setText(allHive.get(x).lifeSpan()); 
			    	picurlT.setText(allHive.get(x).getImage()); 
			    	
			    	ths.currentEditing = allHive.get(x);
			    	
			    	for(JTextField text : textList) {ths.remove(text);}
			    	
			    	
			    	System.out.println(textList.length);
			    	for(int y = 0; y < textList.length; y ++) {
			    		textList[y].setBounds(65, y*20 + 200, 415, 20);
			    		textList[y].addKeyListener(new KeysListener());
			    		ths.add(textList[y]);
			    	}
			    	
			    	ths.update();
			    	break;
				}
			}
		}
	}
    
	
	public static class KeysListener implements KeyListener{
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				if(event.getSource() == noiseT) {
					currentEditing.setNoise(noiseT.getText());
				}
				if(event.getSource() == weightT) {
					currentEditing.setWeight(weightT.getText());
				}
				if(event.getSource() == speedT) {
					currentEditing.setSpeed(speedT.getText());
				}
				if(event.getSource() == skillT) {
					currentEditing.setSkills(skillT.getText());
				}
				if(event.getSource() == weaponT) {
					currentEditing.setWeapon(weaponT.getText());
				}
				if(event.getSource() == locationT) {
					currentEditing.setLocations(locationT.getText());
				}
				if(event.getSource() == colorT) {
					currentEditing.setCommonColors(colorT.getText());
				}
				if(event.getSource() == leaderT) {
					currentEditing.setLeader(leaderT.getText());
				}
				if(event.getSource() == homeT) {
					currentEditing.setHome(homeT.getText());
				}
				if(event.getSource() == goalsT) {
					currentEditing.setGoals(goalsT.getText());
				}
				if(event.getSource() == warT) {
					currentEditing.setWar(warT.getText());
				}
				if(event.getSource() == lifeT) {
					currentEditing.setLifespan(lifeT.getText());
				}
				if(event.getSource() == picurlT) {
					currentEditing.setImage(picurlT.getText());
					ths.remove(picture);
					picture.setIcon(currentEditing.bodyImage());
					picture.setBounds(540, 250, 150, 150);
					ths.add(picture);
					ths.update();
				}
			}
		}
		public void keyTyped(KeyEvent event) {}
		public void keyReleased(KeyEvent event) {}
	}
	
}
