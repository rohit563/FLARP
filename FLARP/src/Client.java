import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
	public static String ipAddress = "127.0.0.1";
	protected static Socket skt;
	
    public Client()
    {
        super();
    }
    
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                //InputStream in = serialPort.getInputStream();
                //OutputStream out = serialPort.getOutputStream();
                
                BufferedReader in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                PrintWriter out = new PrintWriter(serialPort.getOutputStream());
                
                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    /** */
    public static class SerialReader implements Runnable 
    {
        BufferedReader in;
        
        
        
        
        public SerialReader ( BufferedReader in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                BufferedReader in2 = new BufferedReader(new
                   InputStreamReader(skt.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));
                System.out.print("Received string: '");
                
                String c1;
                String c2;
                String c3;
                String c4;
                while ( true )
                {
                	c1 = in.readLine();
                	//c4 = c1 +"\n"+c2+"\n"+c3;
                	System.out.println(c1);
                    out.write(c1);
                    out.flush();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        PrintWriter out;
        
        public SerialWriter ( PrintWriter out )
        {
            this.out = out;
        }
        
        public void run ()
        {
        	try {
                
                BufferedReader in = new BufferedReader(new
                   InputStreamReader(skt.getInputStream()));
                System.out.print("Received string: '");


                //System.out.print("'\n");
                //in.close();
                String c;
                while ( true )
                {
                	c = in.readLine();
                	System.out.println(c);
                    out.write(c);
                    out.flush();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    
    public static void main ( String[] args )
    {
    	
        try
        {
//            (new TwoWaySerialComm()).connect("/dev/tty.usbmodemfd131");
    		Scanner type = new Scanner(System.in);
    		System.out.print("New IP: ");
    		String newIP = type.nextLine();

    		if (!newIP.equals(""))
    			ipAddress = newIP;
    		
    		System.out.print("New COM number: ");
    		String com = type.nextLine();
    		
    		if (com.equals(""))
    			com = "4";
    		
    		skt = new Socket(ipAddress, 2343);
    		
            (new Client()).connect("COM" + com);
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
}