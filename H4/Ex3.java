import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ex3 {

    static class SimpleConnectionPool {
        private final List<Connection> connections = new ArrayList<>();
        private final int maxConnections;

        public SimpleConnectionPool(String url, String user, String pass, int maxConnections) throws SQLException {
            this.maxConnections = maxConnections;
            for (int i = 0; i < maxConnections; i++) {
                connections.add(DriverManager.getConnection(url, user, pass));
            }
        }

        public synchronized Connection getConnection() throws InterruptedException {
            while (connections.isEmpty()) {
                wait();
            }
            return connections.remove(0);
        }

        public synchronized void releaseConnection(Connection conn) {
            connections.add(conn);
            notifyAll();
        }

        public synchronized void closeAll() {
            for (Connection conn : connections) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            connections.clear();
        }
    }

    static class WorkerThread extends Thread {
        private final SimpleConnectionPool pool;
        private final int id;

        public WorkerThread(SimpleConnectionPool pool, int id) {
            this.pool = pool;
            this.id = id;
        }

        @Override
        public void run() {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = pool.getConnection();
                String sql = "INSERT INTO Log (mesaj) VALUES (?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "Mesaj de la thread-ul " + id);
                ps.executeUpdate();

                Thread.sleep(100 + (int)(Math.random() * 400));

            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (conn != null) pool.releaseConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final String url = "jdbc:postgresql://localhost:5432/tema4";
        final String user = "postgres";
        final String pass = "parola";
        final int M = 5;
        final int K = 10;

        SimpleConnectionPool pool = null;

        try {
            Class.forName("org.postgresql.Driver");
            pool = new SimpleConnectionPool(url, user, pass, M);

            Thread[] threads = new Thread[K];
            for (int i = 0; i < K; i++) {
                threads[i] = new WorkerThread(pool, i + 1);
                threads[i].start();
            }

            for (Thread t : threads) {
                t.join();
            }

            Connection conn = pool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Log");
            if (rs.next()) {
                System.out.println("In Log sunt " + rs.getInt(1) + " inregistrari.");
            }
            rs.close();
            stmt.close();
            pool.releaseConnection(conn);

            conn = pool.getConnection();
            CallableStatement cstmt = conn.prepareCall("CALL sterge_mesaje_vechi()");
            cstmt.execute();
            cstmt.close();
            pool.releaseConnection(conn);

            System.out.println("Mesajele mai vechi de o ora au fost sterse.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool != null) {
                pool.closeAll();
            }
        }
    }
}
