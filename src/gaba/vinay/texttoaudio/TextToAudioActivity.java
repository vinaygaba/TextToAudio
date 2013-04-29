package gaba.vinay.texttoaudio;



import java.util.Locale;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TextToAudioActivity extends Activity implements OnSeekBarChangeListener,TextToSpeech.OnInitListener{
	private SeekBar bar; // declare seekbar object variable
	// declare text label objects
	private TextView textProgress,textAction;
	private TextToSpeech tts1;
	EditText edittext;
	Button speak;
	public float value;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // load the layout
        setContentView(R.layout.main); 
        tts1 = new TextToSpeech(this, this);
        bar = (SeekBar)findViewById(R.id.seekbar);
        edittext=(EditText)findViewById(R.id.text);
        speak=(Button) findViewById(R.id.speak);// make seekbar object
        bar.setOnSeekBarChangeListener(this); // set seekbar listener.
        // since we are using this class as the listener the class is "this"
        
        // make text label for progress value
        //textProgress = (TextView)findViewById(R.id.textViewProgress);
        // make text label for action
        //textAction = (TextView)findViewById(R.id.textViewAction);
        speak.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
    			
            	speakOut();
    		}
            });
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
    		boolean fromUser) {
    	// TODO Auto-generated method stub
    	
    	// change progress text label with current seekbar value
    	//textProgress.setText("The value is: "+progress);
    	// change action text label to changing
    	//textAction.setText("changing");
    	value=progress;
    	value=value/100;
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    	// TODO Auto-generated method stub
    	//textAction.setText("starting to track touch");
    	
    }
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    	// TODO Auto-generated method stub
    	seekBar.setSecondaryProgress(seekBar.getProgress());
    	//textAction.setText("ended tracking touch");    	
    }

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			Locale locale = new Locale("en", "IN");
            int result = tts1.setLanguage(locale);
            
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else 
            {
               // speakOut();
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
		}
	
	private void speakOut() {
		// TODO Auto-generated method stub
		String texttospeech=edittext.getText().toString();
		if(value==0.0)
		{
			value=(float) 0.1;
		}
		tts1.setSpeechRate((float) value);
    	//thread1.sleep(1000);
    	tts1.speak(texttospeech, TextToSpeech.QUEUE_ADD, null);
	}

	public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts1 != null) {
            tts1.stop();
            tts1.shutdown();
        }
        super.onDestroy();
    }
}