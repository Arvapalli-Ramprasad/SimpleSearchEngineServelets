<%@page import="com.Accio.HistoryResult"%>
<%@page import="java.util.ArrayList"%>
<html
<head>
<link rel = "stylesheet" type = "text/css" href = "styles.css">
</head>
<body>
<h1>Simple Search Engine</h1>
<form action = "Search">
    <input type = "text" name = "keyword"></input>
    <button type = "submit">Search</button>
</form>
<form action = "History">
    <button type = "submit">History</button>
</form>
    <table border="2" class = "resultTable">
        <tr>
            <th>Keyword</th>
            <th>Link</th>
        </tr>
        <%
            ArrayList<HistoryResult> results = (ArrayList<HistoryResult>) request.getAttribute("results");
            if (results != null) {
                for(HistoryResult result: results) {
        %>
        <tr>
            <td><%= result.getKeyword() %></td>
            <td><a href="<%= result.getLink() %>"><%= result.getLink() %></a></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="2">No history results found.</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>