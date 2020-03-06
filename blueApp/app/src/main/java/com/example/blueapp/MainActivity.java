package com.example.blueapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity {
    //    private final String DEVICE_NAME="MyBTBee";
    private final String DEVICE_ADDRESS="20:16:10:08:44:13";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    Button startButton, sendButton,clearButton,stopButton;
    TextView textView;
    TextView textView2;
    TextView text1;
    TextView text2;
    TextView text3;
    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    int sensorCount =6;
    List<Integer> totalPressure = new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
    List<Integer> avgs= new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
    List<Integer> savedAvgs= new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
    String status="normal";
    int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.buttonStart);
        sendButton = (Button) findViewById(R.id.buttonSend);
        clearButton = (Button) findViewById(R.id.buttonClear);
        stopButton = (Button) findViewById(R.id.buttonStop);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);

        setUiEnabled(false);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveCurrentAvg();
                setStatus("normal");
                Toast.makeText(MainActivity.this, "saved....!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUiEnabled(boolean bool)
    {
        startButton.setEnabled(!bool);
        sendButton.setEnabled(bool);
        stopButton.setEnabled(bool);
        textView.setEnabled(bool);

    }

    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    public void onClickStart(View view) {
        if(BTinit())
        {
            if(BTconnect())
            {
                setUiEnabled(true);
                deviceConnected=true;
                beginListenForData();
                textView.append("\nConnection Opened!\n");
            }

        }
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];





        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                int total=0;
                String str = "";

                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {

                    try
                    {

                        int byteCount = inputStream.available();
                        if(byteCount>0 && total<(sensorCount*4)){
                            byte[] rawBytes = new byte[byteCount];
                            int bytes = inputStream.read(rawBytes);
                            String readMessage = new String(rawBytes, 0, bytes);
                            str+= readMessage;
                            total+=byteCount;
                        }else if(total>=(sensorCount*4)){
                            final String st = str;
                            count++;
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    textView.setText(st+"\navg : "+avgs.toString());
                                    text3.setText("SENSOR 1 :  "+avgs.get(0)+"\nSENSOR 2 :  "+avgs.get(1)+"\nSENSOR 3 :  "+avgs.get(2)+"\nSENSOR 4 :  "+avgs.get(3)
                                    +"\nSENSOR 5 :  "+avgs.get(4));
                                }
                            });
                            setTotalPressureList(getIntListFromString(st));
                            if(count==5){
                                count=0;
                                setAvgPressure();
//                                System.out.println("avg pressure is :"+avgs);
                            }
//                            System.out.println(st);
                            str="";
                            total=0;
                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

    public void onClickSend(View view) {
//        String string = editText.getText().toString();
//        string.concat("\n");
//        try {
//            outputStream.write(string.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        textView.append("\nSent Data:"+string+"\n");

    }

    public void onClickStop(View view) throws IOException {
        stopThread = true;
        outputStream.close();
        inputStream.close();
        socket.close();
        setUiEnabled(false);
        deviceConnected=false;
        textView.append("\nConnection Closed!\n");
    }

    public void onClickClear(View view) {
        textView.setText("");
    }

    public List<Integer> getIntListFromString(String st){
        List<Integer> result = new ArrayList<>();
        try{
            int sensor_1 = Integer.parseInt(st.substring(1,4));
            int sensor_2 = Integer.parseInt(st.substring(5,8));
            int sensor_3 = Integer.parseInt(st.substring(9,12));
            int sensor_4 = Integer.parseInt(st.substring(13,16));
            int sensor_5 = Integer.parseInt(st.substring(17,20));
            int sensor_6 = Integer.parseInt(st.substring(21,23));


            result.add(sensor_1);
            result.add(sensor_2);
            result.add(sensor_3);
            result.add(sensor_4);
            result.add(sensor_5);
            result.add(sensor_6);

            return result;
        }catch (Exception e){
            System.out.println("error in conversion");
            return result;
        }
    }


    public void setTotalPressureList(List<Integer> list){
//        System.out.println("total pressure is : "+totalPressure);
        try {
            for(int i =0;i<sensorCount;i++){
                totalPressure.set(i,totalPressure.get(i)+list.get(i));
            }
        }catch (Exception e){
            System.out.println("error in adding to total");
        }
    }
    public void setAvgPressure(){
//        System.out.println(totalPressure);
        try {
            int attentionCount=0;

            for(int i =0;i<sensorCount;i++){
                avgs.set(i,(totalPressure.get(i)/5));
                System.out.println(savedAvgs.get(i)+"  "+avgs.get(i));
                if(savedAvgs.get(i)!=0 && Math.abs(savedAvgs.get(i)-avgs.get(i))>50) {
                    attentionCount++;
                }
            }
            if(attentionCount!=0){
                status="Attentions";
                setStatus("Attentions");
            }else{
                status="normal";
                setStatus("normal");
            }
            totalPressure = new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
        }catch (Exception e){
            System.out.println("error in setting avg");
        }
    }

    public void saveCurrentAvg(){
        savedAvgs = new ArrayList<>();
        for(int i =0;i<sensorCount;i++){
            savedAvgs.add(avgs.get(i));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                text1.setText("Normal Averages "+savedAvgs.toString());
                text2.setText("Temp :"+savedAvgs.get(5).toString());
            }
        });

        System.out.println("saved avgs is "+savedAvgs);
    }

    public void setStatus(String st){
        final String str = st;
        System.out.println(st);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView2.setText(str);
            }
        });

    }


}