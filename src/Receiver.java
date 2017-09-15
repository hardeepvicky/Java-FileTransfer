/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Receiver extends Thread
{
    private String ip;
    private int port;
    private String file;
    
    public static Scanner scanner;

    public Receiver(String ip, int port, String file)
    {
        this.ip = ip;
        this.port = port;
        this.file = file;
    }
    
    public void run()
    {
        try 
        {
            this.receive();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void receive() throws IOException 
    {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        Socket socket = null;
        try {

            //creating connection.
            socket = new Socket(this.ip, this.port);
            System.out.println("connected.");

            // receive file
            byte[] byteArray;
            System.out.print("Please wait downloading file");
            long start = (long) System.currentTimeMillis() / 1000;

            //reading file from socket
            InputStream inputStream = socket.getInputStream();
            fileOutputStream = new FileOutputStream(this.file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            
            int bytesRead = 0;
            int current = 0;
            long cur_seconds = start;
            do 
            {
                byteArray = new byte[1024 * 10];
                bytesRead = inputStream.read(byteArray, 0, byteArray.length);
                if (bytesRead >= 0) 
                {
                    bufferedOutputStream.write(byteArray, 0, bytesRead);
                    current += bytesRead;
                }
                
                long temp = (long) System.currentTimeMillis() / 1000;
                if (temp > cur_seconds)
                {
                    cur_seconds = temp;
                    System.out.print(".");
                }
            }
            while (bytesRead > -1);
            
            bufferedOutputStream.flush();

            Float size =  ((float) current / (1024 * 1024) );
            
            long end = (long) System.currentTimeMillis() / 1000;
            
            DecimalFormat formatter = new DecimalFormat("#.00");
            
            System.out.println("\nDownloaded : " + formatter.format(size) + " MB in " + (end - start) + " seconds");
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("Error 1");
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }
    
    
//    public void receive() throws IOException 
//    {
//        FileOutputStream fileOutputStream = null;
//        BufferedOutputStream bufferedOutputStream = null;
//        Socket socket = null;
//        try {
//
//            //creating connection.
//            socket = new Socket(this.ip, this.port);
//            System.out.println("connected.");
//
//            // receive file
//            byte[] byteArray = new byte[1024 * 1024 * 50];
//            System.out.println("Please wait downloading file");
//
//            //reading file from socket
//            InputStream inputStream = socket.getInputStream();
//            fileOutputStream = new FileOutputStream(this.file);
//            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//            
//            int bytesRead = inputStream.read(byteArray, 0, byteArray.length);;
//            
//            bufferedOutputStream.write(byteArray, 0, bytesRead);
//            bufferedOutputStream.flush();
//
//            System.out.print("\nDownloaded : ");
//            System.out.println(bytesRead + " bytes");
//            
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            System.err.println("Error 1");
//            e.printStackTrace();
//        } finally {
//            if (fileOutputStream != null) {
//                fileOutputStream.close();
//            }
//            if (bufferedOutputStream != null) {
//                bufferedOutputStream.close();
//            }
//            if (socket != null) {
//                socket.close();
//            }
//        }
//    }
    
//    
//    public void receive() throws IOException 
//    {
//        int bytesRead = 0;
//        int current = 0;
//        FileOutputStream fileOutputStream = null;
//        BufferedOutputStream bufferedOutputStream = null;
//        Socket socket = null;
//        try {
//
//            //creating connection.
//            socket = new Socket(this.ip, this.port);
//            System.out.println("connected.");
//
//            // receive file
//            byte[] byteArray = new byte[1024 * 1024 * 50];					//I have hard coded size of byteArray, you can send file size from socket before creating this.
//            System.out.println("Please wait downloading file");
//
//            //reading file from socket
//            InputStream inputStream = socket.getInputStream();
//            fileOutputStream = new FileOutputStream(this.file);
//            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//            bytesRead = inputStream.read(byteArray, 0, byteArray.length);					//copying file from socket to byteArray
//
//            current = bytesRead;
//            do 
//            {
//                bytesRead = inputStream.read(byteArray, current, (byteArray.length - current));
//                if (bytesRead >= 0) 
//                {
//                    current += bytesRead;
//                }
//            } 
//            while (bytesRead > -1);
//            
//            bufferedOutputStream.write(byteArray, 0, current);							//writing byteArray to file
//            bufferedOutputStream.flush();												//flushing buffers
//
//            System.out.println("File " + this.file + " downloaded (" + (current / (1024 * 1024)) + " MB)");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (fileOutputStream != null) {
//                fileOutputStream.close();
//            }
//            if (bufferedOutputStream != null) {
//                bufferedOutputStream.close();
//            }
//            if (socket != null) {
//                socket.close();
//            }
//        }
//    }
}
