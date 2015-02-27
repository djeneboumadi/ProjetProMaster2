package fr.istic.ludecol.service.util;

import fr.istic.ludecol.domain.Pictures;
import fr.istic.ludecol.domain.Levels;


public class Star {
	
	private Levels level;

	private String user;
	
	private int nbStar;

	public Levels getLevel() {
		return level;
	}

	public void setLevel(Levels level) {
		this.level = level;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getNbStar() {
		return nbStar;
	}

	public void setNbStar(int nbStar) {
		this.nbStar = nbStar;
	}
	
	
	
}
