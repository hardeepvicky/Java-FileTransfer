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
            System.err.println(e.getMessage());
        }
    }
    
    public void receive() throws IOException 
    {
        int bytesRead = 0;
        int current = 0;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        Socket socket = null;
        try {

            //creating connection.
            socket = new Socket(this.ip, this.port);
            System.out.println("connected.");

            // receive file
            byte[] byteArray = new byte[1024 * 1024 * 50];					//I have hard coded size of byteArray, you can send file size from socket before creating this.
            System.out.println("Please wait downloading file");

            //reading file from socket
            InputStream inputStream = socket.getInputStream();
            fileOutputStream = new FileOutputStream(this.file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bytesRead = inputStream.read(byteArray, 0, byteArray.length);					//copying file from socket to byteArray

            current = bytesRead;
            do 
            {
                bytesRead = inputStream.read(byteArray, current, (byteArray.length - current));
                if (bytesRead >= 0) 
                {
                    current += bytesRead;
                }
            } 
            while (bytesRead > -1);
            
            bufferedOutputStream.write(byteArray, 0, current);							//writing byteArray to file
            bufferedOutputStream.flush();												//flushing buffers

            System.out.println("File " + this.file + " downloaded (" + (current / (1024 * 1024)) + " MB)");
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
}
