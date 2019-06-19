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
                System.out.println("Client1 has been connected");
            } catch (IOException e) {
                System.out.println("Communication Error with client1");
                        System.exit(-1);
            }try {
                socket2 = server.accept();
                System.out.println("Client2 has been connected");
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
                String Client1Name = in1.readLine();
                System.out.println("Client1 Name: " + Client1Name);

                //communication with client 2
                in2 = new BufferedReader(
                        new InputStreamReader(
                                socket2.getInputStream()
                        )
                );
                out2 = new PrintWriter(socket2.getOutputStream(),
                        true);
                out2.println("CSE338 LAB Server");
                String Client2Name = in2.readLine();
                System.out.println("Client2 Name: " + Client2Name);
                while(socket1.isConnected() && socket2.isConnected()) {
                    System.out.print("\nServer: Game starts \n");

                    out1.println("Make your move - 1.Rock, 2.Paper, 3.Scissor");
                    System.out.print("Client 1 ( "+Client1Name+" ): ");
                    int Client1Move = Integer.parseInt(in1.readLine());
                    System.out.println(Client1Move);

                    out2.println("Make your move - 1.Rock, 2.Paper, 3.Scissor");
                    System.out.print("Client 2 ( "+Client2Name+" ): ");
                    int Client2Move = Integer.parseInt(in2.readLine());
                    System.out.println(Client2Move);

                    //Game winner calculation
                    String Winner = "";
                    if ( Client1Move == Client2Move ){
                        Winner = " Draw";
                    }else if ( (Client1Move==2 && Client2Move ==1 ) || (Client1Move==1 && Client2Move ==3 ) || (Client1Move==3 && Client2Move ==2 ) ){
                        Winner = "Client 1";
                        out1.println("You won !");

                    }else if ( (Client2Move==2 && Client1Move ==1 ) || (Client2Move==1 && Client1Move ==3 ) || (Client2Move==3 && Client1Move ==2 ) ){
                        Winner = "Client 2";
                        out2.println("You won !");
                    }else {
                        Winner = "Error";
                    }


                    String MovesSummary = "Client 1 move : " + Client1Move + ", Client 2 move : "+ Client2Move + ". Winner : " + Winner;
                    out1.println(MovesSummary);
                    out2.println(MovesSummary);
                    System.out.print(MovesSummary);

                }

            } catch (IOException e) {
                System.out.print("a Client left");
                consoleInput.close();
            }

        }
    }
}