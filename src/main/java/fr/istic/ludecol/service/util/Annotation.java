package fr.istic.ludecol.service.util;

import fr.istic.ludecol.domain.Pictures;
import fr.istic.ludecol.domain.Species;
import fr.istic.ludecol.domain.User;
 public class Annotation{
	 
    	private Double x,y;
    	
    	private String text;
    	
    	private Long id_species;
    	
    	private Long id_user;
    	
    	private Long id_picture;
    	
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
		public Long getSpecies() {
			return id_species;
		}
		public void setSpecies(Long species) {
			this.id_species = species;
		}
		public Long getUser() {
			return id_user;
		}
		public void setUser(Long user) {
			this.id_user = user;
		}
		public Long getPicture() {
			return id_picture;
		}
		public void setPicture(Long picture) {
			this.id_picture = picture;
		}
		
    }