package com.Accio;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        //Getting keyWord from frontend
        String keyword = req.getParameter("keyword");

        //Setting connection to database
        Connection connection = DatabaseConnection.getConnection();
        try {

            //Read com.Accio.Search quaries
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();

            //Getting results after running rankines quary
            ResultSet resultSet = connection.createStatement().executeQuery("select pageTitle,pageLink,(length(lower(pagetext))-length(replace(lower(pageText),'"+keyword.toLowerCase()+"','')))/length('"+keyword.toLowerCase()+"') as countoccurance from pages order by countoccurance DESC limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();

            //Adding values or transferring values from resultset to arraylist
            while (resultSet.next()){
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

            //Displaying results in the console
            for(SearchResult result:results){
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }

            //requestin to disply on frontend
            req.setAttribute("results",results);
            req.getRequestDispatcher("search.jsp").forward(req, resp);
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
        }
        catch (SQLException | ServletException sqlException){
            sqlException.printStackTrace();
        }


    }
}
