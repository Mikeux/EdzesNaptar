package mikeux.android.edzesnaptar;

import java.util.ArrayList;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EdzesFajtaActivity extends ListActivity {
	
	/** Items entered by the user is stored in this ArrayList variable */
    ArrayList list = new ArrayList();
 
    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter adapter;
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edzes_fajta);
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner_mertekegyseg);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,R.array.mertekegysegek, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner		
		spinner.setAdapter(spinner_adapter);
        
        
 
        /** Reference to the add button of the layout main.xml */
        Button btn = (Button) findViewById(R.id.btnAdd);
 
        /** Reference to the delete button of the layout main.xml */
        Button btnDel = (Button) findViewById(R.id.btnDel);
 
        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, list);
 
        /** Defining a click event listener for the button "Add" */
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.txtItem);
                list.add(edit.getText().toString());
                edit.setText("");
                adapter.notifyDataSetChanged();
            }
        };
 
        /** Defining a click event listener for the button "Delete" */
        OnClickListener listenerDel = new OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Getting the checked items from the listview */
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();
 
                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){
                        adapter.remove(list.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        };
 
        /** Setting the event listener for the add button */
        btn.setOnClickListener(listener);
 
        /** Setting the event listener for the delete button */
        btnDel.setOnClickListener(listenerDel);
 
        /** Setting the adapter to the ListView */
        setListAdapter(adapter);
    }
}
