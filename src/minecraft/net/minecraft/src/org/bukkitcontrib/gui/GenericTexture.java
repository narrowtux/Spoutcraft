package org.getspout.Spout.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import net.minecraft.src.Tessellator;
import net.minecraft.src.BukkitContrib;
import org.getspout.Spout.io.CustomTextureManager;
import org.getspout.Spout.packet.PacketUtil;

public class GenericTexture extends GenericWidget implements Texture {
	protected String Url = null;
	public GenericTexture() {
		
	}
	
	public GenericTexture(String Url) {
		this.Url = Url;
	}

	@Override
	public WidgetType getType() {
		return WidgetType.Texture;
	}
	
	@Override
	public int getNumBytes() {
		return super.getNumBytes() + PacketUtil.getNumBytes(getUrl());
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		this.setUrl(PacketUtil.readString(input));
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		PacketUtil.writeString(output, getUrl());
	}
	
	@Override
	public void render() {
		String path = CustomTextureManager.getTextureFromUrl(getUrl());
		if (path == null) {
			return;
		}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(getX(), getY(), 0); //moves texture into place
		GL11.glBindTexture(GL11.GL_TEXTURE_2D , BukkitContrib.getGameInstance().renderEngine.getTexture(path));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, getHeight(), -90, 0.0D, 0.0D); //draw corners
		tessellator.addVertexWithUV(getWidth(), getHeight(), -90, -1, 0.0D);
		tessellator.addVertexWithUV(getWidth(), 0.0D, -90, -1, -1);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90, 0.0D, -1);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public String getUrl() {
		return Url;
	}

	@Override
	public Texture setUrl(String Url) {
		if (getUrl() != null) {
			//TODO release image?
		}
		this.Url = Url;
		if (getUrl() != null) {
			CustomTextureManager.downloadTexture(Url);
		}
		return this;
	}

}
