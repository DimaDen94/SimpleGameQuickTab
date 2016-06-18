package com.up_site.quick_tap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.up_site.quick_tap.GameScreen;
import com.up_site.quick_tap.QuickTap;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GameScreen";
		config.width = 512;
		config.height = 784;
		new LwjglApplication(new QuickTap(), config);
	}
}
