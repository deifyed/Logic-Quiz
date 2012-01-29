package com.cognitiveadventure.logicquiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {
	private final long TMRSTART_TIME = 59999; // 59999
	private final long TMRSECOND = 1000;
	private final long VIBRATETIME = 100;
	private final String CASESFOLDER = "cases";
	
	private short score = 0;
	
	private CountDownTimer tmr;
	private Random rand;
	
	private String[] paths;
	Case currentCase;
	
	AlertDialog.Builder newDialog;
	AlertDialog.Builder endDialog;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        load();
        
        newGameDialog();
    }
    
	public void onClick(View v) {
		if(v.getId() == R.id.btnLeft && currentCase.answer == true)
		{
			correct();
			Toast.makeText(this, "Left button: Correct", Toast.LENGTH_SHORT).show();
		}
		else if(v.getId() == R.id.btnRight && currentCase.answer == false)
		{
			correct();
			Toast.makeText(this, "Right Button: Correct", Toast.LENGTH_SHORT).show();
		}
		else
			wrong();
	}
    
    /*
     *		Custom methods/functions
     */
    
    private void load()
    {
    	final TextView lblCountDown = (TextView) findViewById(R.id.lblCountDown);
    	
    	tmr = new CountDownTimer(TMRSTART_TIME, TMRSECOND) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				if((millisUntilFinished / 1000) < 10)
				{
					lblCountDown.setTextColor(Color.RED);
					lblCountDown.setText(String.format("00:0%d", (millisUntilFinished / 1000)));
				}
				else
					lblCountDown.setText(String.format("00:%d", (millisUntilFinished / 1000)));
			}
			
			@Override
			public void onFinish() {
				lblCountDown.setText("00:00");
				endGameDialog();
			}
		};
		
    	rand = new Random();
		
		/*
		 * 		Dialogs
		 */
    	newDialog = new AlertDialog.Builder(this);
    	
    	newDialog.setTitle(getResources().getString(R.string.NewGameDialogTitle));
    	newDialog.setMessage(getResources().getString(R.string.NewGameDialogMessage));
    	
    	newDialog.setPositiveButton(getResources().getString(R.string.NewGameDialogPlayButton), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				newGame();
			}
		});
    	newDialog.setNegativeButton(getResources().getString(R.string.NewGameDialogCancelButton), new DialogInterface.OnClickListener()
    	{
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
    	
    	endDialog = new AlertDialog.Builder(this);
    	endDialog.setTitle(getResources().getString(R.string.EndGameDialogTitle));
    	endDialog.setCancelable(false);
    	endDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				newGameDialog();
			}
    	});
    }
    
    private void newGame()
    {
    	reset();
    	
    	makeCase();
    	
    	tmr.start();
    }
    
    private void makeCase()
    {
    	currentCase = new Case(paths[rand.nextInt(paths.length)]);
    	
    	List<String> list = new ArrayList<String>(Arrays.asList(paths));
    	list.removeAll(Arrays.asList(currentCase.path));
    	paths = list.toArray(new String[list.size()]);
    	
    	try {
			((ImageView) findViewById(R.id.imgCase)).setBackgroundDrawable(Drawable.createFromStream(getAssets().open(CASESFOLDER + "/" + currentCase.path), null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void populate()
    {   	
    	try {
			paths = getAssets().list(CASESFOLDER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void correct()
    {
    	score++;
    	
    	makeCase();
    }
    
    private void wrong()
    {
    	((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VIBRATETIME);
    	
    	makeCase();
    }
    
    private void reset()
    {
    	score = 0;
    	
    	populate();
    }
    
    /*
     *		Dialogs
     */
    
    private void newGameDialog()
    {
    	newDialog.show();
    }
    
    private void endGameDialog()
    {
    	endDialog.setMessage(getResources().getString(R.string.EndGameDialogMessage) + " " + score + " poeng");
    	endDialog.show();
    }
}