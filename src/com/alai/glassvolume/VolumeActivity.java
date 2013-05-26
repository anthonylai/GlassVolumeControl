package com.alai.glassvolume;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.alai.glassvolume.R;

public class VolumeActivity extends Activity {

	int currentVolume;
	int maxVolume;
	TextView currentLevel;
	Spinner audioSpinner;


	int audioSelection;
	
	AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_volume);

		audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

		audioSpinner = (Spinner) findViewById(R.id.spinner1);
		
		currentLevel = (TextView) findViewById(R.id.currentLevel);
		
		
		Button upButton = (Button) findViewById(R.id.up);
		Button downButton = (Button) findViewById(R.id.down);
		
		upButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				audioManager.adjustStreamVolume(getAudioTypeFromSelection(), AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
				refresh();
			}
		});

		downButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				audioManager.adjustStreamVolume(getAudioTypeFromSelection(), AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
				refresh();
			}
		});
		
		audioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				refresh();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				refresh();
			}
		});
		
		audioSpinner.setSelection(0);
		refresh();

		
	}

	private int getAudioTypeFromSelection() {
		
		switch (audioSpinner.getSelectedItemPosition()) {
		case 0:
			return AudioManager.STREAM_VOICE_CALL;
		case 1:
			return AudioManager.STREAM_MUSIC;
		case 2:
			return AudioManager.STREAM_RING;
		case 3:
			return AudioManager.STREAM_SYSTEM;
		case 4:
			return AudioManager.STREAM_NOTIFICATION;
		case 5:
			return AudioManager.STREAM_ALARM;
		case 6:
			return AudioManager.STREAM_DTMF;
		default:	
			return -1;
		}
	}
	private void refresh() {
		int audioType = getAudioTypeFromSelection();
		if (audioType == -1)
			currentLevel.setText("N/A");
		else
			currentLevel.setText(Integer.valueOf(audioManager.getStreamVolume(audioType)).toString()
					+ "/" + Integer.valueOf(audioManager.getStreamMaxVolume(audioType)).toString());	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.volume, menu);
		return true;
	}

}
