package fr.istic.ludecol.service.util;

import fr.istic.ludecol.domain.Pictures;
import fr.istic.ludecol.domain.Species;
import fr.istic.ludecol.domain.User;
 public class Annotation{
	 
    	private Double x,y;
    	
    	private String text;
    	
    	private Species species;
    	
    	private String user;
    	
    	private Pictures picture;
    	
    	public Double getX() {
			return x; 
		}
		public void setX(Double x) {
			this.x = x;
		}
		public Double getY() {
			return y;
		}
		public void setY(Double y) {
			this.y = y;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Species getSpecies() {
			return species;
		}
		public void setSpecies(Species species) {
			this.species = species;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public Pictures getPicture() {
			return picture;
		}
		public void setPicture(Pictures picture) {
			this.picture = picture;
		}
		
    }