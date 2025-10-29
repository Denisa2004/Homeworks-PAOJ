import java.io.*;
import java.net.*;
import java.util.*;

public class Ex4 {

    public static class ChatServer {
        private static final int PORT = 8888;
        private static final List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
        private static boolean running = true;

        public static void main(String[] args) {
            System.out.println("Server pornit pe portul " + PORT);
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while (running) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client conectat: " + clientSocket);
                    clients.add(clientSocket);
                    new Thread(new ClientHandler(clientSocket)).start();
                }
            } catch (IOException e) {
                System.out.println("Server oprit.");
            }
        }

        public static void shutdown() throws IOException {
            running = false;
            synchronized (clients) {
                for (Socket s : clients) {
                    try {
                        s.close();
                    } catch (IOException ignored) {}
                }
                clients.clear();
            }
            System.out.println("Server inchis.");
            System.exit(0);
        }

        public static class ClientHandler implements Runnable {
            private static ThreadLocal<Socket> threadSocket = new ThreadLocal<>();
            private Socket socket;

            public ClientHandler(Socket socket) {
                this.socket = socket;
                threadSocket.set(socket);
            }

            @Override
            public void run() {
                try (
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        if (msg.equals("/quit")) {
                            clients.remove(socket);
                            socket.close();
                            System.out.println("Client deconectat.");
                            break;
                        }

                        if (msg.equals("/shutdown")) {
                            //primul e admin
                            if (clients.get(0).equals(socket)) {
                                ChatServer.shutdown();
                            } else {
                                out.println("Nu ai drepturi de admin.");
                            }
                            continue;
                        }

                        synchronized (clients) {
                            for (Socket s : clients) {
                                if (!s.equals(socket)) {
                                    PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                                    pw.println("Client: " + msg);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Eroare client: " + e.getMessage());
                }
            }
        }
    }

    public static class ChatClient {
        private static final String HOST = "localhost";
        private static final int PORT = 8888;

        public static void main(String[] args) {
            try (Socket socket = new Socket(HOST, PORT)) {
                System.out.println("Conectat la server.");

                Thread readThread = new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Serverul a inchis conexiunea.");
                    }
                });

                Thread writeThread = new Thread(() -> {
                    try (
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
                    ) {
                        String input;
                        while ((input = userInput.readLine()) != null) {
                            out.println(input);
                            if (input.equals("/quit")) {
                                socket.close();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Scriere oprita.");
                    }
                });

                readThread.start();
                writeThread.start();

                readThread.join();
                writeThread.join();

            } catch (IOException | InterruptedException e) {
                System.out.println("Eroare client: " + e.getMessage());
            }
        }
    }
}
