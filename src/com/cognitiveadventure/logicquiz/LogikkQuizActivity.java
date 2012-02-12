	package com.cognitiveadventure.logicquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/*
 * TODO:
 * 	* Design
 * 	* Scoreboard?
 */
public class LogikkQuizActivity extends Activity implements OnClickListener {
	static final String PREFS = "logprefs";
	static final String KEY_PREFIX = "pos";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
    @Override
    protected void onResume() {
    	loadScoreboard();
    	super.onResume();
    }
    
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.btnNewGame:
    			newGame();
    	}
    }
    
    /*
     *		Custom methods/functions
     */
    
    private void newGame()
    {
    	Intent i = new Intent(this, GameActivity.class);
    	
    	startActivity(i);
    }
    
    private void loadScoreboard() {
    	SharedPreferences prefs = getSharedPreferences(PREFS, 0);
    	for(int i = 1; i < 6; i++)
    		((EditText) findViewById(R.id.scoreBoard)).append("\n" + i + ": " + ((prefs.getInt(KEY_PREFIX + i, 0) != 0) ? prefs.getInt(KEY_PREFIX + i, 0) : ""));
    }
}