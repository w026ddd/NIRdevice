package com.example.nirplotter;

import android.app.Activity;
import android.content.Intent;
import android.graph.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import org.achartengine.GraphicalView;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.util.UUID;
import java.io.*;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//Button btnOn, btnOff;
	//  TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
	    Handler bluetoothIn;
	    private static GraphicalView view;
		private LineGraph line = new LineGraph();
		private static Thread thread;
		
		//variables to graph
		private int readstring = 0;
		public double time = 1;
        public double CHb = 1;
        public double CHbO2 = 1;
		
	  final int handlerState = 1;                        //used to identify handler message
	  private BluetoothAdapter btAdapter = null;
	  private BluetoothSocket btSocket = null;
	  private StringBuilder recDataString = new StringBuilder();
	 
	  private ConnectedThread mConnectedThread;
	 
	  // SPP UUID service - this should work for most devices
	  private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	 
	  // String for MAC address
	  private static String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    //Link the buttons and textViews to respective views
	    //btnOn = (Button) findViewById(R.id.buttonOn);
	    //btnOff = (Button) findViewById(R.id.buttonOff);
	  //  txtString = (TextView) findViewById(R.id.txtString);
	  //  txtStringLength = (TextView) findViewById(R.id.testView1);
	   // sensorView0 = (TextView) findViewById(R.id.sensorView0);
	   // sensorView1 = (TextView) findViewById(R.id.sensorView1);
	   // sensorView2 = (TextView) findViewById(R.id.sensorView2);
	    //sensorView3 = (TextView) findViewById(R.id.sensorView3); 
	    /*thread = new Thread() {
	    	public void run()
			{
				for (int i = 0; i < 20; i++) 
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Point p = MockData.getDataFromReceiver(i); // We got new data!
					line.addNewPoints(p); // Add it to our graph
					view.repaint();
				}
			}
		};
		thread.start();*/
	    
	    
	    bluetoothIn = new Handler() {
	        public void handleMessage(android.os.Message msg) {
	            if (msg.what == handlerState) {                                     //if message is what we want
	                String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
	                recDataString.append(readMessage);                                      //keep appending to string until ~
	                int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
	                if (endOfLineIndex > 0) {                                           // make sure there data before ~
	                    String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
	                 //   txtString.setText("Data Received = " + dataInPrint);
	                    int dataLength = dataInPrint.length();                          //get length of data received
	                 //   txtStringLength.setText("String Length = " + String.valueOf(dataLength));
	 
	                   // if (recDataString.charAt(0) == '#')                             //if it starts with # we know it is what we are looking for
	                    if(true)
	                    {
	                        // String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
	                       // String sensor1 = recDataString.substring(6, 10);            //same again...
	                       // String sensor2 = recDataString.substring(11, 15);
	                       // String sensor3 = recDataString.substring(16, 20);
	   	            	 StringTokenizer st = new StringTokenizer(recDataString.substring(1, recDataString.length()) , "+");
	   		            
		            	 String sensor0 = st.nextElement().toString(); 
		            	 String sensor1 = st.nextElement().toString();
		            	 String sensor2 = st.nextElement().toString();
	                    
	                    	
	                        time = Double.parseDouble(sensor0);
	                        CHb = Double.parseDouble(sensor1);
	                        CHbO2 = Double.parseDouble(sensor2);
	                        
	    	                    Point p = MockData.create(time,CHb,CHbO2); // We got new data!	
	    	                    line.addNewPoints(p); // Add it to our graph
	        					view.repaint();
	        					
	    	                    
	                      //  sensorView0.setText(" Sensor 0 Voltage = " + sensor0 + "V");    //update the textviews with sensor values
	                      //  sensorView1.setText(" Sensor 1 Voltage = " + sensor1 + "V");
	                      //  sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
	                      //  sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
	                    }
	                    recDataString.delete(0, recDataString.length());                    //clear all string data
	                  //  strIncom =" ";
	                    dataInPrint = " ";
	                }
	            }
	        }
	    };
	    
	    btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
	    checkBTState(); 
	 
	  /* Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
	    btnOff.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	        mConnectedThread.write("0");    // Send "0" via Bluetooth
	        Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
	      }
	    });
	 
	    btnOn.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	        mConnectedThread.write("1");    // Send "1" via Bluetooth
	        Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
	      }
	    });
	    */
		
	}
	
	private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
		 
	      return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
	      //creates secure outgoing connection with BT device using UUID
	  }
	
	@Override
	  //public void onResume() {
	  //  super.onResume();
	 public void onStart(){
		super.onStart();
	    //Get MAC address from DeviceListActivity via intent
	    Intent intent = getIntent();
	 
	    //Get the MAC address from the DeviceListActivty via EXTRA
	    address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
	 
	    //create device and set the MAC address
	   // BluetoothDevice device = btAdapter.getRemoteDevice(address);
	    BluetoothDevice device = btAdapter.getRemoteDevice("00:1A:7D:DA:71:13");
	    try {
	        btSocket = createBluetoothSocket(device);
	    } catch (IOException e) {
	        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
	    }
	    // Establish the Bluetooth socket connection.
	    try
	    {
	      btSocket.connect();
	    } catch (IOException e) {
	      try
	      {
	        btSocket.close();
	      } catch (IOException e2)
	      {
	        //insert code to deal with this
	      }
	    }
	    mConnectedThread = new ConnectedThread(btSocket);
	    mConnectedThread.start();
	 
	    //I send a character when resuming.beginning transmission to check device is connected
	    //If it is not an exception will be thrown in the write method and finish() will be called
	    mConnectedThread.write("x");
	    view = line.getView(this);//
		setContentView(view);//
	  }
	 
	  @Override
	  public void onPause()
	  {
	    super.onPause();
	    try
	    {
	    //Don't leave Bluetooth sockets open when leaving activity
	      btSocket.close();
	    } catch (IOException e2) {
	        //insert code to deal with this
	    }
	  }
	 
	 //Checks that the Android device Bluetooth is available and prompts to be turned on if off
	  private void checkBTState() {
	 
	    if(btAdapter==null) {
	        Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
	    } else {
	      if (btAdapter.isEnabled()) {
	      } else {
	        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	        startActivityForResult(enableBtIntent, 1);
	      }
	    }
	  }
	 
	  //create new class for connect thread
	  private class ConnectedThread extends Thread {
	        private final InputStream mmInStream;
	        private final OutputStream mmOutStream;
	 
	        //creation of the connect thread
	        public ConnectedThread(BluetoothSocket socket) {
	            InputStream tmpIn = null;
	            OutputStream tmpOut = null;
	 
	            try {
	                //Create I/O streams for connection
	                tmpIn = socket.getInputStream();
	                tmpOut = socket.getOutputStream();
	            } catch (IOException e) { }
	 
	            mmInStream = tmpIn;
	            mmOutStream = tmpOut;
	        }
	 
	        public void run() {
	            byte[] buffer = new byte[256];
	            int bytes; 
	          //  int i =1;
	            // Keep looping to listen for received messages
	            while (true) {
	            //	for (int i = 0; i < 20; i++) {
	                try {
	                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
	                    String readMessage = new String(buffer, 0, bytes);
	                    // Send the obtained bytes to the UI Activity via handler
	                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
	                 
	                } catch (IOException e) {
	                    //break;
	                }
	            }
	        }
	        //write method
	        public void write(String input) {
	            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
	            try {
	                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
	            } catch (IOException e) {
	                //if you cannot write, close the application
	                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
	                finish();
	 
	              }
	            }
	        }
	
	
	/*@Override
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
		@Override
		protected void onStart() {
			super.onStart();
			view = line.getView(this);
			setContentView(view);
		}*/

}
	
