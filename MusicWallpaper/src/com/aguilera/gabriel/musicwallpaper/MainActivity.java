package com.aguilera.gabriel.musicwallpaper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;



public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//android wallpaper manager
		WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
		String originalWallpaperFileName = "original_wallpaper.png";
		FileInputStream fis;
		try{
			fis = openFileInput(originalWallpaperFileName);
			byte[] input = new byte[fis.available()];
			while(fis.read(input) != -1){}
			myWallpaperManager.clear();
			Bitmap bmp;
			bmp = BitmapFactory.decodeByteArray(input, 0, input.length);
			myWallpaperManager.setBitmap(bmp);

			Toast.makeText(getApplicationContext(), "Your old wallpaper was successfully opened!", Toast.LENGTH_SHORT).show();;
			fis.close();
			return;
		}
		catch(IOException e) {
			//fis.close();
			e.printStackTrace();
		}



		Drawable originalWallpaper = myWallpaperManager.getDrawable(); //Gets current wallpaper
		Bitmap originalWallpaperBitmap = ((BitmapDrawable)originalWallpaper).getBitmap(); //Converts it to a bitmap
		ByteArrayOutputStream stream = new ByteArrayOutputStream(); //Prepares a byte array
		originalWallpaperBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // compress it

		byte[] bitmapData = stream.toByteArray(); // make into a byte array
		FileOutputStream fos;
		try {
			fos = openFileOutput(originalWallpaperFileName, Context.MODE_PRIVATE); //Open stream
			fos.write(bitmapData); //Save the file.
			Toast.makeText(getApplicationContext(), "Original wallpaper successfully saved!", Toast.LENGTH_SHORT).show();
			fos.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}

		try 
		{
			//set wallpaper picture from resource here
			myWallpaperManager.setResource(R.drawable.mbdtf);
			Toast.makeText(getApplicationContext(), "Wallpaper change was successful!", Toast.LENGTH_SHORT).show();
		}
		catch(IOException e){
			Toast.makeText(getApplicationContext(), "Unsuccessful. :(", Toast.LENGTH_SHORT).show();
		}

		//        if (savedInstanceState == null) {
		//            getFragmentManager().beginTransaction()
		//                    .add(R.id.container, new PlaceholderFragment())
		//                    .commit();
		//        }
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()){

		case R.id.action_about:
		//TextView updatedAboutVersion = (TextView)findViewByID(R.id.aboutVersion);
			//String updatedVersionString = getString(R.string.versionLabel) + getString(R.string.versionNumber);
			//((TextView)findViewById(R.id.aboutVersion)).setText(updatedVersionString);
			Dialog dialog = new Dialog(this); //Can't use getApplicationContext
				//dialog.requestWindowFeature();
				dialog.setTitle("About");
				dialog.setContentView(R.layout.action_about);
				dialog.setCancelable(true);
				dialog.show();
				return true;

		case R.id.action_settings:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	//    public static class PlaceholderFragment extends Fragment {
	//
	//        public PlaceholderFragment() {
	//        }
	//
	//        @Override
	//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	//                Bundle savedInstanceState) {
	//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
	//            return rootView;
	//        }
	//    }
}
