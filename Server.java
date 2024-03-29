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
    private static int s1Flag = 0;
    private static int s2Flag = 0;
    private static Socket socket2 = null;
    private static final int port = 8080;
    public static void main(String[] args) {
//Create IO Objects
        BufferedReader in1 = null;
        BufferedReader in2 = null;
        PrintWriter out1 = null;
        PrintWriter out2 = null;
        String Client1Name="";
        String Client2Name="";
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



            if( s1Flag == 0 ) {
                try {
                    socket1 = null;
                    socket1 = server.accept();
                    System.out.println("Client1 has been connected");

                } catch (IOException e) {
                    System.out.println("Communication Error with client1");
                    System.exit(-1);
                }
            }

            if(s2Flag==0) {
                try {
                    socket2 = null;
                    socket2 = server.accept();
                    System.out.println("Client2 has been connected");

                } catch (IOException e) {
                    System.out.println("Communication Error with client2");
                    System.exit(-1);
                }
            }

            //communication with clients
            try {

                System.out.println(" inside try soc1");

                //communication with client 1
                if(s1Flag==0) {
                    in1 = new BufferedReader(
                            new InputStreamReader(
                                    socket1.getInputStream()
                            )
                    );

                    System.out.println(" check in1 " + in1);

                    out1 = new PrintWriter(socket1.getOutputStream(),
                            true);
                    out1.println("CSE338 LAB Server");
                    Client1Name = in1.readLine();
                    System.out.println("Client1 Name: " + Client1Name);
                    s1Flag=1;
                }

                //communication with client 2
                if(s2Flag==0) {
                    in2 = new BufferedReader(
                            new InputStreamReader(
                                    socket2.getInputStream()
                            )
                    );
                    out2 = new PrintWriter(socket2.getOutputStream(),
                            true);
                    out2.println("CSE338 LAB Server");
                    Client2Name = in2.readLine();
                    System.out.println("Client2 Name: " + Client2Name);
                    s2Flag=1;
                }

                out1.println(Client1Name+ " vs " + Client2Name);
                out2.println(Client1Name+ " vs " + Client2Name);

                while(socket1.isConnected() && socket2.isConnected() ) {
                    System.out.print("\n\nServer: Game starts \n");
                    out1.println("Make your move - 1.Rock, 2.Paper, 3.Scissor 4.Exit game");
                    System.out.print("Client 1 ("+Client1Name+" ): ");
                    int Client1Move = Integer.parseInt(in1.readLine());
                    System.out.println(Client1Move);

                    out2.println("Make your move - 1.Rock, 2.Paper, 3.Scissor 4.Exit game");
                    System.out.print("Client 2 ("+Client2Name+" ): ");
                    int Client2Move = Integer.parseInt(in2.readLine());
                    System.out.println(Client2Move);

                    String Winner = ""; // Tracks winner



                    //Game winner calculation
                    if ( Client1Move == Client2Move ){
                        Winner = " Draw";
                    }else if ( (Client1Move==2 && Client2Move ==1 ) || (Client1Move==1 && Client2Move ==3 ) || (Client1Move==3 && Client2Move ==2 ) ){
                        Winner = "Client 1 : "+ Client1Name;
                    }else if ( (Client2Move==2 && Client1Move ==1 ) || (Client2Move==1 && Client1Move ==3 ) || (Client2Move==3 && Client1Move ==2 ) ){
                        Winner = "Client 2 : "+ Client2Name;
                    }else {
                        Winner = "Abandoned";
                    }

                    //Show game summary
                    String MovesSummary = "Client 1 move : " + Client1Move + "    Client 2 move : "+ Client2Move + "    Winner : " + Winner;
                    out1.println(MovesSummary);
                    out2.println(MovesSummary);
                    System.out.print(MovesSummary);

                    //Exit game condition handler
                    if ( Client1Move==4){
                        System.out.println("Client 1 left");
                        s1Flag=0;
                        out2.println("Abandoned game");
                        socket1.close();
                        Winner = "Game Abandoned";
                    }else if( Client2Move==4){
                        System.out.println("Client 2 left");
                        s2Flag=0;
                        out1.println("Abandoned game");
                        socket2.close();
                        Winner = "Game Abandoned";
                    }

                }

            } catch (IOException e) {
                if(!socket1.isConnected()){
                    System.out.println("-Client 1 left");
                }if(!socket2.isConnected()){
                    System.out.println("-Client 2 left");
                }

                System.out.print("IO EXCEPTION -- ");
                System.out.print(e);

                consoleInput.close();
            }

        }
    }
}