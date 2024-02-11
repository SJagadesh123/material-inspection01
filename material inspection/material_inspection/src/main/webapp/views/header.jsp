<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/css/header.css">

<style>
body {
            font-family: sans-serif;
            margin: 0;
            padding: 0;
        }

        nav {
            background-color: #000080;
            color: white;
            padding: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
           
        }

        #logo {
            margin-right: auto;
        }

        #home-logout {
            margin-left: auto;
        }


</style>

</head>

<body>

	<nav>
		<div id="logo">

		</div>
		<div id="home-logout">
			<a href="/material-inspection/home-page" style="color:white; margin-right:24px;">Home</a> 
			
		</div>
		<button type="button" onclick="window.location.href='/material-inspection/logout'">Logout</button>
	</nav>



</body>

</html>
