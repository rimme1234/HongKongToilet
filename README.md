OUHK Mobile Applicaiton Development Assignment

Student:Tang Chi Pong Ronnie

-Using own Google API
-Assign build project to own Google API for the Map View

The App to be built has five requirements.
	-It gets the location of the mobile device in terms of latitude and longitude.
	-It gets the language setting of the mobile device.
	-It sends the HTTP GET with the following URL:
		Get Toilet API
		http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?lat=22&lng=114&lang=zh_tw
		http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?lat=22&lng=114&lang=zh_hk
		http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?lat=22&lng=114&lang=zh_cn
		http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?lat=22&lng=114
		You can also specify row_index and display_row in the URL.
		The default value of row_index = 0 and display_row = 10
		http://plbpc013.ouhk.edu.hk/lbitest/json-toilet-v2.php?lat=22&lng=114&row_index=10&display_row=20
	
	
	-It receives results from the server.
	-It displays the results on screen.

***It displays the location on the built-in Google map view