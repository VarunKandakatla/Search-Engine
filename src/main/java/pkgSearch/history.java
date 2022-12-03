package pkgSearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/history")
public class history extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            Connection connection=DatabaseConnection.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery("select * from history");

            ArrayList<historyResult> results=new ArrayList<historyResult>();
            while(resultSet.next())
            {
                historyResult historyresult=new historyResult();
                historyresult.setName(resultSet.getString("name"));
                historyresult.setLink(resultSet.getString("link"));
                results.add(historyresult);
            }

            for(historyResult historyresult : results)
            {
                System.out.println(historyresult.getName()+" "+historyresult.getLink()+"\n");
            }

            request.setAttribute("results",results);
            request.getRequestDispatcher("history.jsp").forward(request,response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ServletException servletException)
        {
            servletException.printStackTrace();
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
}
