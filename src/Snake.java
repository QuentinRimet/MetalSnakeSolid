import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Timer;

@SuppressWarnings("serial")
public class Snake extends JPanel{
	//coordonees des points d'exclamations a recuperer
	private static int x;
	private static int y;
	//tableau de position de chaque "corp" du snake
	private int[] posX =new int[1000];
	private int[] posY =new int[1000];
	//nb de "corp" du snake
	private int i;
	//direction du snake
	private static boolean droite;
	private static boolean gauche;
	private static boolean haut;
	private static boolean bas;
	//gestion des phase du jeu
	private static boolean jeu=false;
	private static boolean start=true;
	//images
	private JLabel lab;
	private JLabel log;
	private JLabel log1;
	//score actuel
	private static Integer score=0;
	//couleur du snake
	private static Color couleur= Color.white;
	//vitesse du jeu
	private static int vit=1;
	//score max obtenu
	private static Integer max=Snake.Highscore();

	//permet d'initialiser les variables
	public Snake(){
		
		this.setBackground(Color.black);
		
		i=0;
		
		x=-30;
		y=-30;
		//configuration du JPanel
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(800,800));
		//ajout de l'ecouteur du clavier
		new KeySnake(this);
		
		lab = new JLabel(new ImageIcon("img/mgs.jpg"));
		lab.setVisible(false);
		add(lab);

		log1 = new JLabel(new ImageIcon("img/MGS.png"));
		log1.setVisible(true);
		add(log1);
		log = new JLabel(new ImageIcon("img/MGS_logo.png"));
		log.setVisible(true);
		add(log);
		log.setLocation(new Point(400,400));
		repaint();
	}
	//on demarre le menu start
	public void start(){
		//le snake regarde en bas
		start=true;
		droite=false;
		gauche=false;
		haut=false;
		bas=true;
		//on le centre
		this.posX[0]=this.getWidth()/2;
		this.posY[0]=600;
		//on end visible les bonnes images
		lab.setVisible(false);
		log.setVisible(true);
		log1.setVisible(true);
		repaint();
	}

	//permet de redemarrer le jeu
	public void reset(){
		i=0;
		score=0;
		jeu=true;
		start=false;
		pomme();
		log.setVisible(false);
		log1.setVisible(false);
		jeu();


	}

	//permet de faire avancer le snake
	public void deplacePos(int a,int c){
		//on fait avancer toute les cases du corps sauf la tete
		for (int b=i;b>0;b--){
			this.posX[b]=this.posX[b-1];
			this.posY[b]=this.posY[b-1];
		}
		//on modifie les coordonés de la tete
		//si il sort de l'ecran par la gauche alors il reaparait a droite
		if((this.posX[0]+a)<0 ){
			this.posX[0]=this.getHeight();
			this.posY[0]+=c;
		}
		else
			//si il sort de l'ecran par la droite alors il reaparait a gauche
			if((this.posX[0]+a)>this.getWidth() ){
				this.posX[0]=0;
				this.posY[0]+=c;
			}
			else
				//si il sort de l'ecran par le haut alors il reaparait en bas
				if((this.posY[0]+c)<0 ){
					this.posX[0]+=a;
					this.posY[0]=this.getHeight();
				}
				else
					//si il sort de l'ecran par le bas alors il reaparait en haut
					if((this.posY[0]+c)>this.getHeight() ){
						this.posX[0]+=a;
						this.posY[0]=0;
					}
					else
						//sinon il prend les nouvelles coordonées
					{
						this.posX[0]+=a;
						this.posY[0]+=c;
					}
		//si on touche son corp alors on as perdu
		for (int b=i;b>0;b--){
			if(this.posX[b]==this.posX[0] && this.posY[b]==this.posY[0]) {
				fin();

			}
		}
		//si on prend un point d'exclamation alors on augmente le corps du snake
		if(posX[0]==x && posY[0]==y){
			score+=1;
			ajouter();
			pomme();
			URL url =Main.class.getResource("surprise.wav");
			AudioClip ac = Applet.newAudioClip(url);
			ac.play();
		}
		this.repaint();

	}
	
	//gere la fin du jeu
	public void fin(){
		//on reinitialise les attributs
		jeu=false;
		Timer time=new Timer();
		i=0;
		this.posX[0]=-50;
		this.posY[0]=-50;
		x=-40;
		y=-50;
		//nouveau higscore
		if(score>max)
			max=score;
		newHighscore();
		repaint();
		//son de mort
		URL ur =Main.class.getResource("MORT1.wav");
		AudioClip acc = Applet.newAudioClip(ur);
		acc.play();
		lab.setVisible(true);
		//autre son un peu delayé
		time.schedule(new TimerTask(){
			public void run(){

				URL ur =Main.class.getResource("M.wav");
				AudioClip acc = Applet.newAudioClip(ur);
				acc.play();

			}
		}, 500);


	}


	public static boolean isStart() {
		return start;
	}

	public static void setStart(boolean start) {
		Snake.start = start;
	}

	public void paintComponent (Graphics g){

		super.paintComponent(g);
		//menu start
		if(start){
			g.setColor(couleur);
			g.fillRect(posX[0], posY[0]-200, 20, 200);
			g.drawString("Vitesse : "+vit, 500, 500);
			g.drawString("Highscore : "+max, 250, 500);
		}
		else
			//fin du jeu
			if(!jeu){
				g.setColor(Color.white);
				g.setFont(new Font("Arial", 1, 100));
				g.drawString("Score : ",240,350);
				g.setFont(new Font("Arial", 1, 500));

				if(score>=10)
					g.drawString(score.toString(),125,750);
				else
					g.drawString(score.toString(),260,750);
			}

		//on dessine le point d'exclamation
		g.setColor(couleur);
		g.fillOval(x+7, y, 6,13);
		g.fillOval(x+8, y+15, 4,5);
		
		//on dessine le snake
		for(int a=0;a<=i;a++){
			g.fillRect( this.posX[a],this.posY[a],20,20);
		}
		//on colorie les contours de la tete
		g.setColor(Color.black);
		g.drawLine(this.posX[0], this.posY[0], this.posX[0],this.posY[0]+19);
		g.drawLine(this.posX[0]+19, this.posY[0], this.posX[0]+19,this.posY[0]+19);
		g.drawLine(this.posX[0], this.posY[0]+19, this.posX[0]+19,this.posY[0]+19);
		g.drawLine(this.posX[0], this.posY[0], this.posX[0]+19,this.posY[0]);
		
		//on dessine les yeux du snake en fonction de l'orientation
		if(droite){

			g.fillOval(this.posX[0]+14, this.posY[0]+3, 4,4);
			g.fillOval(this.posX[0]+14, this.posY[0]+13, 4,4);
		}
		if(gauche){
			g.fillOval(this.posX[0]+2, this.posY[0]+3, 4,4);
			g.fillOval(this.posX[0]+2, this.posY[0]+13, 4,4);
		}
		if(haut){
			g.fillOval(this.posX[0]+3, this.posY[0]+2, 4,4);
			g.fillOval(this.posX[0]+13, this.posY[0]+2, 4,4);
		}
		if(bas){
			g.fillOval(this.posX[0]+3, this.posY[0]+14, 4,4);
			g.fillOval(this.posX[0]+13, this.posY[0]+14, 4,4);
		}

	}

	//ajoute un bout de corp au snake
	public void ajouter(){
		i+=1;
		posX[i]=posX[i-1];
		posY[i]=posY[i-1];
	}

	//permet de boucler le jeu temps qu'il tourne
	public void jeu(){

		Timer time=new Timer();
		if(jeu){
			deplacement();

			time.schedule(new TimerTask(){
				public void run(){
					jeu();
				}
			}, 50/vit);}

	}


  //construit un nouveau point d'exclamation
	public void pomme(){
		x=(int)(Math.random()*40)*20;
		y=(int)(Math.random()*40)*20;
	}





	//permet de choisir quelle deplacement faire
	public void deplacement(){
		if(haut){ 
			this.deplacePos(0,-20);
		}
		if(bas) {
			this.deplacePos(0,20);
		}
		if(gauche){
			this.deplacePos(-20,0);
		}
		if(droite) {
			this.deplacePos(20,0);
		}
	}

	//Permer d'actualiser le meilleur score
	public static void newHighscore(){
		BufferedWriter e;
		try {
			e = new BufferedWriter(new FileWriter("highscore.txt"));
			e.write(max.toString());
			e.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	//permet de recuperer le meilleur score
	public static Integer Highscore(){
		BufferedReader e;
		try {
			e = new BufferedReader(new FileReader("highscore.txt"));
			Integer m=Integer.parseInt(e.readLine());
			e.close();
			return m;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return 0;
		}

	}

	public static boolean isDroite() {
		return droite;
	}

	public static void setDroite(boolean droite) {
		Snake.droite = droite;
	}

	public static boolean isGauche() {
		return gauche;
	}

	public static void setGauche(boolean gauche) {
		Snake.gauche = gauche;
	}

	public static boolean isHaut() {
		return haut;
	}

	public static void setHaut(boolean haut) {
		Snake.haut = haut;
	}

	public static boolean isBas() {
		return bas;
	}

	public static void setBas(boolean bas) {
		Snake.bas = bas;
	}

	public static boolean isJeu() {
		return jeu;
	}

	public static void setJeu(boolean jeu) {
		Snake.jeu = jeu;
	}

	public static Color getCouleur() {
		return couleur;
	}

	public static void setCouleur(Color couleur) {
		Snake.couleur = couleur;
	}

	public static int getVit() {
		return vit;
	}

	public static void setVit(int vit) {
		Snake.vit = vit;
	}

}
