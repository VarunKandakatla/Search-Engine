<%@page import="java.util.ArrayList"%>
<%@page import="pkgSearch.historyResult"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="resultTable">

        <table border =2>
            <tr>
                <td>Name</td>
                <td>Link</td>
            </tr>
            <%
                ArrayList<historyResult> results= (ArrayList<historyResult>)request.getAttribute("results");
                for(historyResult result: results){
            %>
            <tr>
                <td><%out.println(result.getName());%></td>
                <td><a href="<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
            </tr>

            <%
                }
            %>
        </table>
        </div>
</body>
</html>