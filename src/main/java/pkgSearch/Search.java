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

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            String keyword= request.getParameter("keyword");
            Connection connection=DatabaseConnection.getConnection();
            keyword=keyword.toLowerCase();
            //Getting results from data base
            ResultSet resultSet=connection.createStatement().executeQuery("select pagetitle,pagelink, (length(lower(pagedata))-length(replace(lower(pagedata),'"+keyword+"','')))/length('"+keyword+"')as countoccurence\n" +
                    " from pages order by countoccurence desc limit 30 ;");

            ArrayList<SearchResult> results=new ArrayList<SearchResult>();
            while(resultSet.next())
            {
                SearchResult searchResult=new SearchResult();
                searchResult.setTitle(resultSet.getString("pagetitle"));
                searchResult.setLink(resultSet.getString("pagelink"));
                results.add(searchResult);
            }
//            for(SearchResult searchResult: results)
//            {
//                System.out.println(searchResult.getTitle()+" "+searchResult.getLink()+"\n");
//            }




            PreparedStatement preparedStatement=connection.prepareStatement("Insert into history values (?,?)");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/Searcher?keyword="+keyword);
            preparedStatement.executeUpdate();

            request.setAttribute("results",results);
            request.getRequestDispatcher("search.jsp").forward(request,response);
            response.setContentType("text/html");
            PrintWriter out=response.getWriter();
            out.println("<h3>This is my Servlet "+keyword+"</h3>");

        }
        catch(IOException ioException)
        {
            System.out.println(ioException);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        catch (ServletException  e)
        {
            e.printStackTrace();
        }
    }
}
