package com.btranz.healthplus.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import com.btranz.healthplus.R;
import com.btranz.healthplus.model.PatientModel;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class PatientAdapter extends BaseAdapter implements View.OnClickListener {

	/*********** Declare Used Variables *********/
    private Context context;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    PatientModel tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public PatientAdapter(Context c, ArrayList d) {
    	
    	/********** Take passed values **********/
        context = c;
        data=d;
        //res = resLocal;
        
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
    	
    	if(data.size()<=0)
    		return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    /********* Create a holder to contain inflated xml file elements ***********/
    public static class ViewHolder{
    	
        public TextView name;
        public TextView ward;
        public TextView room;
        public TextView lastname;
        public TextView credit;

//public Button viewdetails;


        /*public ImageView image1;*/

    }

    /*********** Depends upon data size called for each row , Create each ListView row ***********/
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View vi=convertView;
        ViewHolder holder;
        
        if(convertView==null){ 
        	
        	/********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflater.inflate(R.layout.list_item_patient, null);
            
            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder=new ViewHolder();
            holder.name=(TextView)vi.findViewById(R.id.pat_name);
            holder.lastname=(TextView)vi.findViewById(R.id.lastname);
            holder.room=(TextView)vi.findViewById(R.id.roo_name);
            holder.ward=(TextView)vi.findViewById(R.id.ward_name);
           // holder.viewdetails=(Button)vi.findViewById(R.id.viewdetails);

   /* holder.viewdetails.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


    }
});*/
            /*holder.image1=(ImageView)vi.findViewById(R.id.imageView1);*/
            
           /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else  
            holder=(ViewHolder)vi.getTag();
        
        if(data.size()<=0)
        {
        	holder.name.setText("No Data");
            
        }
        else
        {
        	/***** Get each Model object from Arraylist ********/
	        tempValues=null;
	        tempValues = (PatientModel) data.get(position);
	        
	        /************  Set Model values in Holder elements ***********/
	         holder.name.setText("First Name  : "+tempValues.getFnameStr());
            holder.lastname.setText("Last Name  : "+tempValues.getLnameStr());
	         holder.ward.setText("Ward  : "+tempValues.getWardStr());
	         holder.room.setText("Room  : "+tempValues.getRoomStr());



            String name=tempValues.getLnameStr().toString();
            String healthid=tempValues.getHidStr().toString();
            String addressStr=tempValues.getAddressStr().toString();
            String ageStr=tempValues.getAgeStr().toString();

        //  Intent intent=new Intent(PatientAdapter.this,PatientDetails.class);
	       
	         /******** Set Item Click Listner for LayoutInflater for each row ***********/
	       // vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    
   @Override
    public void onClick(View v) {
            Log.v("CustomAdapter", "=====Row button clicked");
    }
    
   /* *//********* Called when Item click in ListView ************//*
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;
        
        OnItemClickListener(int position){
        	 mPosition = position;
        }
        
        @Override
        public void onClick(View arg0) {
        	FromAirportSearchActivity slv1 = (FromAirportSearchActivity)context;
        	slv1.onItemClick(mPosition);
           // FromAirportSearchActivity vd=activity
        }               
    }*/
}