package org.getspout.Spout.gui;

public interface PopupScreen extends Screen{
	
	public boolean isTransparent();
	
	public PopupScreen setTransparent(boolean value);
	
	public boolean close();
}
