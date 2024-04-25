package webapp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ServletTask extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String task = req.getParameter("task");

        if ("add".equals(action)) {
        } else if ("modify".equals(action)) {
            String originalTask = req.getParameter("taskDescription");
            String modifiedTask = req.getParameter("modifiedTask");

        } else if ("delete".equals(action)) {
            String taskDescriptionToDelete = req.getParameter("taskDescription");

        try{
            String hostname = "localhost";
            String port = "3306";
            String username = "root";
            String password = "root";
            String schema = "db";
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionUrl = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", hostname, port, schema);
            Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            String query = "INSERT INTO tasks VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, task);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                HttpSession session = req.getSession();
                session.setAttribute("task", task);

                resp.sendRedirect(req.getContextPath() + "/home.jsp");
            } else {
                System.out.println("Error: Failed to add task.");
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}