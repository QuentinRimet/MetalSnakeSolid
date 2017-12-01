import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeySnake implements KeyListener{
	

	public KeySnake(Snake pan){
	
		pan.addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		//permet d'aller d'orienter le snake vers le haut en jeu
		if (e.getKeyCode()==38 && !Snake.isStart()) {
			if(!Snake.isBas()){
				Snake.setHaut(true); 
				Snake.setDroite(false);
				Snake.setGauche(false);
				Snake.setBas(false);
			}
		}

		//permet d'aller d'orienter le snake vers le bas en jeu
		if (e.getKeyCode()==40 && !Snake.isStart()) {
			if(!Snake.isHaut()){
				Snake.setBas(true);
				Snake.setDroite(false);
				Snake.setGauche(false);
				Snake.setHaut(false); 
			}
		}

		//permet d'aller d'orienter le snake vers la droite en jeu
		if (e.getKeyCode()==39 && !Snake.isStart() ){
			if(!Snake.isGauche()){
				Snake.setGauche(false);
				Snake.setDroite(true);
				Snake.setHaut(false); 
				Snake.setBas(false);
			}
		}

		//permet d'aller d'orienter le snake vers la gauche en jeu
		if (e.getKeyCode()==37 && !Snake.isStart()) {
			if(!Snake.isDroite()){
				Snake.setDroite(false);
				Snake.setGauche(true);
				Snake.setHaut(false); 
				Snake.setBas(false);
			}
		}
		//permet de changer la couleur du snake lors le menu
		if (e.getKeyCode()==37 && Snake.isStart()){
			if(Snake.getCouleur()==(Color.green)) Snake.setCouleur(Color.cyan);
			else if(Snake.getCouleur()==Color.cyan) Snake.setCouleur(Color.blue);
			else if(Snake.getCouleur()==Color.blue) Snake.setCouleur(Color.magenta);
			else if(Snake.getCouleur()==Color.magenta) Snake.setCouleur(Color.red);
			else if(Snake.getCouleur()==Color.red) Snake.setCouleur(Color.pink);
			else if(Snake.getCouleur()==Color.pink) Snake.setCouleur(Color.orange);
			else if(Snake.getCouleur()==Color.orange)  Snake.setCouleur(Color.yellow);		
			else if(Snake.getCouleur()==Color.yellow) Snake.setCouleur(Color.white);
			else if(Snake.getCouleur()==Color.white) Snake.setCouleur(Color.gray);
			else if(Snake.getCouleur()==Color.gray) Snake.setCouleur(Color.green);
			((Snake)e.getSource()).repaint();
					
			
									
		}
		//permet de changer la couleur du snake lors du menu
		if (e.getKeyCode()==39 && Snake.isStart()){
			if(Snake.getCouleur()==(Color.green)) Snake.setCouleur(Color.gray);
			else if(Snake.getCouleur()==Color.cyan) Snake.setCouleur(Color.green);
			else if(Snake.getCouleur()==Color.blue) Snake.setCouleur(Color.cyan);
			else if(Snake.getCouleur()==Color.magenta) Snake.setCouleur(Color.blue);
			else if(Snake.getCouleur()==Color.red) Snake.setCouleur(Color.magenta);
			else if(Snake.getCouleur()==Color.pink) Snake.setCouleur(Color.red);
			else if(Snake.getCouleur()==Color.orange)  Snake.setCouleur(Color.pink);		
			else if(Snake.getCouleur()==Color.yellow) Snake.setCouleur(Color.orange);
			else if(Snake.getCouleur()==Color.white) Snake.setCouleur(Color.yellow);
			else if(Snake.getCouleur()==Color.gray) Snake.setCouleur(Color.white);
			((Snake)e.getSource()).repaint();
		}
		//permet d'augmenter la vitesse du jeu lors du menu
		if (e.getKeyCode()==38 && Snake.isStart()){
			if(Snake.getVit()!=5)
			Snake.setVit(Snake.getVit()+1);
			((Snake)e.getSource()).repaint();
		}
		//permet de reduire la vitesse du jeu lors du menu
		if (e.getKeyCode()==40 && Snake.isStart()){
			if(Snake.getVit()!=1)
			Snake.setVit(Snake.getVit()-1);
			((Snake)e.getSource()).repaint();
		}
		//permet de lancer le jeu lorsque l'on ai dans le menu
		if (e.getKeyCode()==32 && Snake.isStart()){
			((Snake)e.getSource()).reset();
		}
		//permet de retourner au menu a la fin du jeu
		if (e.getKeyCode()==32 && !Snake.isStart() && !Snake.isJeu()){
			((Snake)e.getSource()).start();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}



}
