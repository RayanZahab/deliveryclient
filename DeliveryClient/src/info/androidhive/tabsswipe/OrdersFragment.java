package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class OrdersFragment extends Fragment {

    int depth = 0;
    private List<String> sequence =new ArrayList<String>();

    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
		sequence.add("country");
		sequence.add("city");
		sequence.add("area");
        return inflater.inflate(R.layout.fragment_orders, container, false);  
    }  

    @Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
           
        ArrayList<Item> mylist = getList(sequence.get(0));
        final ListView listView = (ListView) getActivity().findViewById(R.id.list);  
        listView.setAdapter(new MyCustomAdapter(getActivity(), android.R.layout.simple_list_item_1, mylist));  
           
        listView.setOnItemClickListener(new OnItemClickListener() {
   
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        			depth++;
                 ArrayList<Item> mylist = getList(sequence.get(depth));
                 listView.setAdapter(new MyCustomAdapter(getActivity()
                		 , android.R.layout.simple_list_item_1, mylist)); 
            }  
        }); 
           
    }  
    
    public ArrayList<Item> getList(String type)
    {
    	ArrayList<Item> mylist = new ArrayList<Item>();
    	if(type.equals("country"))
    	{
    		for(int i=0, count=20; i<count; i++){  
    			Item it = new Item();
    			it.setName("Country: "+i);
    			it.setType("txt");
    			mylist.add(it);  
            }  
    	}
    	else if (type.equals("city"))
    	{
    		for(int i=0, count=20; i<count; i++){  
    			Item it = new Item();
    			it.setName("City: "+i);
    			it.setType("txt");
    			mylist.add(it);  
            } 
    	}
    	return mylist;
    }
       
    private void showToast(String msg){  
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();  
    }  
   
} 