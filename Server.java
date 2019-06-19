import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Server {
    private static ServerSocket server = null;
    private static Socket socket1 = null;
    private static Socket socket2 = null;
    private static final int port = 8080;
    public static void main(String[] args) {
//Create IO Objects
        BufferedReader in1 = null;
        BufferedReader in2 = null;
        PrintWriter out1 = null;
        PrintWriter out2 = null;
        Scanner consoleInput = new Scanner(System.in);
//Start Server
        try {
            System.out.println("Server is starting ...");
            server = new ServerSocket(port);
            System.out.println("Server has started");
        } catch (IOException e) {
            System.out.println("Can not listen to port: " + port);
            System.exit(-1);
        } while(true) {
//Create Socket
            try {
                socket1 = server.accept();
                System.out.println("Client has been connected\n");
            } catch (IOException e) {
                System.out.println("Communication Error with client1");
                        System.exit(-1);
            }try {
                socket2 = server.accept();
                System.out.println("Client2 has been connected\n");
            } catch (IOException e) {
                System.out.println("Communication Error with client2");
                        System.exit(-1);
            }

            //communication with clients
            try {

                //communication with client 1
                in1 = new BufferedReader(
                        new InputStreamReader(
                                socket1.getInputStream()
                        )
                );
                out1 = new PrintWriter(socket1.getOutputStream(),
                        true);
                out1.println("CSE338 LAB Server");
                System.out.println("Client1 Name: " + in1.readLine());

                //communication with client 2
                in2 = new BufferedReader(
                        new InputStreamReader(
                                socket2.getInputStream()
                        )
                );
                out2 = new PrintWriter(socket2.getOutputStream(),
                        true);
                out2.println("CSE338 LAB Server");
                System.out.println("Client2 Name: " + in2.readLine());
                while(socket1.isConnected() && socket2.isConnected()) {
                    System.out.print("Server: ");

                    System.out.print("Client1: ");
                    System.out.println(in1.readLine());
                    out2.println(consoleInput.nextLine());
                    System.out.print("Client2: ");
                    System.out.println(in2.readLine());
                }

            } catch (IOException e) {
                System.out.print("a Client Left");
                consoleInput.close();
            }

        }
    }
}