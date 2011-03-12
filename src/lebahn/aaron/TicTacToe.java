package lebahn.aaron;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToe extends Activity {
	public static final int X=-1;
	public static final int O=1;
	public static final int EMPTY=0;
	public static final int TIE=-2;
	private Button [] buttons;
	private int [] board;
	private int turn;
	private int gameState;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if(savedInstanceState==null) {
        	board = new int[9];
            turn = X;
        } else {
        	board = savedInstanceState.getIntArray("board");
        	turn = savedInstanceState.getInt("turn");
        }
        buttons = new Button[9];
        for(int i=0; i<9; i++) {
        	if(savedInstanceState==null) {
            	board[i] = 0;
        	}
        	Button button = new Button(this);
        	button.setId(i);
        	button.setTextSize(40);
        	button.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(board[v.getId()]==EMPTY && gameState==EMPTY) {
						board[v.getId()]=turn;
						turn=-turn;	//switch player
						switch(checkState()) {
						case EMPTY:
							break;
						case X:
							Toast.makeText(TicTacToe.this,R.string.x_wins, Toast.LENGTH_SHORT).show();
							break;
						case O:
							Toast.makeText(TicTacToe.this,R.string.o_wins, Toast.LENGTH_SHORT).show();
							break;
						case TIE:
							Toast.makeText(TicTacToe.this,R.string.tie, Toast.LENGTH_SHORT).show();
							break;
						}
						updateDisplay();
					}
				}
			});
        	buttons[i] = button;
        }
        checkState();	//initialize gameState;
        
        TableLayout buttonArea = (TableLayout) findViewById(R.id.grid);
        for(int nRow=0; nRow<3; nRow++) {
        	TableRow row=new TableRow(this);
        	for(int col=0; col<3; col++) {
        		row.addView(buttons[nRow*3+col]);
        	}
        	buttonArea.addView(row);
        }
        updateDisplay();
    }
    
    private void updateDisplay() {
    	TextView playerTurn=(TextView) findViewById(R.id.playerMove);
    	if(gameState==EMPTY) {
	    	switch(turn) {
	    	case X:
	    		playerTurn.setText(R.string.x_turn);
	    		break;
	    	case O:
	    		playerTurn.setText(R.string.o_turn);
	    		break;
	    	}
    	} else {
    		playerTurn.setText("");
    		switch(gameState) {
    		case X:
    			((TextView) findViewById(R.id.status)).setText(R.string.x_wins);
    			break;
    		case O:
    			((TextView) findViewById(R.id.status)).setText(R.string.o_wins);
    			break;
    		case TIE:
    			((TextView) findViewById(R.id.status)).setText(R.string.tie);
    			break;
    		}
    	}
    	for(int i=0; i<9; i++) {
    		switch(board[i]) {
    		case X:
    			buttons[i].setText(R.string.x);
    			break;
    		case O:
    			buttons[i].setText(R.string.o);
    			break;
    		case EMPTY:
    			buttons[i].setText("");
    			break;
    		}
    	}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(R.string.new_game);
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
        turn = X;
        for(int i=0; i<9; i++) {
        	board[i] = EMPTY;
        }
        ((TextView) findViewById(R.id.status)).setText(R.string.instruction);
        checkState();
        updateDisplay();
		return super.onOptionsItemSelected(item);
	}

	public static int evaluateState(int [] board) {
    	for(int row=0; row<3; row++) {
    		if(board[row] != EMPTY && board[row]==board[row+3] && board[row]==board[row+6]) {
    			return board[row];
    		}
    		if(board[3*row] != EMPTY && board[3*row]==board[3*row+1] && board[3*row]==board[3*row+2]) {
    			return board[3*row];
    		}
    	}
    	if(board[0]!=EMPTY && board[0]==board[4] && board[0]==board[8]) {
    		return board[0];
    	}
    	if(board[2]!=EMPTY && board[2]==board[4] && board[2]==board[6]) {
    		return board[2];
    	}
    	for(int i=0; i<9; i++) {
    		if(board[i]==EMPTY) {
    			return EMPTY;
    		}
    	}
    	return TIE;
		
	}
	private int checkState() {
    	return gameState=evaluateState(board);
    }

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		checkState();
		updateDisplay();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putIntArray("board", board);
		outState.putInt("turn", turn);
	}
}