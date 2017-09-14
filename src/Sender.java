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

public class Sender extends Thread
{
    private int port;
    private String file;
    
    public static Scanner scanner;

    public Sender(int port, String file)
    {
        this.port = port;
        this.file = file;
    }
    
    public void run()
    {
        try 
        {
            this.send();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public void send() throws IOException 
    {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;

        OutputStream outputStream = null;
        ServerSocket serverSocket = null;
        Socket socket = null;

        //creating connection between sender and receiver
        try 
        {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Waiting for receiver...");
            try {
                socket = serverSocket.accept();
                System.out.println("Accepted connection : " + socket);
                //connection established successfully

                //creating object to send file
                File file = new File(this.file);
                byte[] byteArray = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                
                bufferedInputStream.read(byteArray, 0, byteArray.length); // copied file into byteArray

                //sending file through socket
                outputStream = socket.getOutputStream();
                System.out.println("Sending " + this.file + "( size: " + byteArray.length + " bytes)");
                outputStream.write(byteArray, 0, byteArray.length); //copying byteArray to socket
                outputStream.flush(); //flushing socket
                System.out.println("Sent : " + this.file); //file has been sent
                
            } finally {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (outputStream != null) {
                    bufferedInputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            }
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
    
}
