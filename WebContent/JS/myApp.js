/**
 * 
 */
angular.module('myApp',[])
.controller('myAppCrtl', ['$scope','$http', function($scope,$http) {
    $scope.getMyCtrlScope = function() {
        return $scope;   
    }
	$scope.loggedIn=false;
	$scope.liked = false;
	$scope.registered = true;
	$scope.isBrowsed = false;
	$scope.isReading = false;
	$scope.showDetails = false;
	$scope.isisHovering = false;
	$scope.optBuy = false;
	$scope.showRDetails = false;
	$scope.inputValidity = true;
	$scope.regValidity = true;
	$scope.errorBox="";
	$scope.popUpAddReview = false;
	$scope.showReviews = false;
	$scope.showRReviews = false;

	
	var loggedUser = "";
	var loggedPass = "";
	var desiredBook ="";
	var loggedNick = "";
	/*LOGIN*/
	$scope.loginFunc = function(){
		var val = JSON.stringify({uName:$scope.uName , uPass:$scope.uPass});
		$http.post("/Web2018/Login",val).
		success(function(data,status,headers,config){
			$scope.loggedIn=true;
			$scope.user = data.nickname;
			loggedUser = data.username;
			loggedPass = data.password;
			loggedNick = data.nickname;
			$scope.isBrowsed = false;
			$scope.isReading = false;
			$scope.showDetails = false;
			}).
		error(function(data,status,headers,config){
			$scope.errorBox="Error";
			});
	}
	/*REGISTER*/
	$scope.registerFunc = function() {
		/*VALIDITY CHECK HERE*/
		
		var val = JSON.stringify({username:$scope.regUname , email:$scope.regEmail ,  address:$scope.regAddress, phone:$scope.regTelephone, 
			password:$scope.regPass, nickname:$scope.regNick, picture:$scope.regPic, description:$scope.regDesc, type:0});
		var isnum;
		
		if ($scope.regTelephone) 
		{
			
			isnum = /^\d+$/.test($scope.regTelephone);
			if (!(isnum))
			{
				$scope.regtValidity = false;
				alert("Please insert only digits in your telephone number!");
				$scope.regTelephone = "";
			}
			if($scope.regTelephone.length != 10 && $scope.regTelephone.length != 9)
			{
				$scope.regtValidity = false;
				alert("For land line enter 9 digits, for mobile 10 digits!");
				$scope.regTelephone = "";
			}
		}
		if ((!$scope.regUname))
		{
				alert("Please fill up username field");
				$scope.regUname = "";
				$scope.regValidity = false;
		}
		if ((!$scope.regPass))
		{
				alert("Please choose a password");
				$scope.regPass = "";
				$scope.regValidity = false;
		}
		if ((!$scope.regNick))
		{
				alert("Please fill up nickname field");
				$scope.regNick = "";
				$scope.regValidity = false;
		}
		if ($scope.regValidity)
		{
			$http.post("/Web2018/Register", val).success(
					function(data, status, headers, config) {
						$scope.loggedIn = true;
						loggedUser = $scope.regUname;
						loggedPass = $scope.regPass;
						$scope.user = $scope.regNick;
						$scope.isBrowsed = false;
						$scope.isReading = false;
						$scope.showDetails = false;
						$scope.answer = loggedUser;
					}).error(
					function(data, status, headers, config) {
						$scope.errorBox = "Error";
						if (status == 400)
							$scope.errorBox = "User already exists";
					}).done(function(data) {});
		}
		regValidity = true;
	};
	/*TOGGLE*/
	$scope.toggleRegister = function(){
		$scope.registered = !($scope.registered);
	}
	/*LOGOUT*/
	$scope.logout = function(){
		$scope.loggedIn = false;
	}
	
	
	/*BROWSE*/
	$scope.browse = function(){
		var val = JSON.stringify({uName:loggedUser , uPass:loggedPass})
		$http.post("/Web2018/GetAvailableBooks",val).success(
				function(data, status, headers, config) {
					/*PUT BOOKS IN PROPER DIV*/
					$scope.isBrowsed = true;
					$scope.isReading = false;
					$scope.browseBooks = data;
					$scope.showRDetails = false;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 401){
						/*wrong password, log user out and display error*/
					}
				})
	}
	
	/* Read book */
	$scope.readBook =function() {
		var val = JSON.stringify({uName:loggedUser , uPass:loggedPass})
		/* Change getAvailableBooks with GetMyBooks*/
		$http.post("/Web2018/GetMyBooks",val).success(
				function(data, status, headers, config) {
					/*PUT BOOKS IN PROPER DIV*/
					$scope.isReading = true;
					$scope.isBrowsed = false;
					$scope.myBooks = data;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 401){
						/*wrong password, log user out and display error*/
					}
				})

	}
	
	/*Show Likes*/
	$scope.displayLikes = function(bookID){
		var val = JSON.stringify({bookid:bookID})
		$http.post("/Web2018/GetLikersByBook",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.browseLikers = data;
					$scope.isHovering = true;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
	}	
	$scope.updateLikers = function(book) {
		var val = JSON.stringify({username:loggedUser, bookid:book.bookId})
		$http.post("/Web2018/AddLike",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.liked = true;
					$scope.displayLikes(book.bookId);
					$scope.currRBook.likes = $scope.currRBook.likes + 1; 
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
	}
	$scope.removeLike = function(book) {
		var val = JSON.stringify({username:loggedUser, bookid:book.bookId})
		$http.post("/Web2018/RemoveLike",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.liked = false;
					$scope.browseLikers = data;
					$scope.currRBook.likes = $scope.currRBook.likes - 1; 
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
	}
	$scope.dontDispLikes = function(){
		$scope.isHovering = false;
	}
	$scope.showBook = function(clicked_id){
		$scope.booksReviews = clicked_id.reviews;
		$scope.showDetails = true;
		$scope.currBook = clicked_id;
		$scope.showReviews = false;
		$scope.counter = 1;
	}	
	$scope.showReadingBook = function(book_being_read){
		$scope.showRDetails = true;
		$scope.popUpAddReview = false;
		$scope.popUpAddReview = false;
		$scope.currRBook = book_being_read;
		
		var val = JSON.stringify({bookid:book_being_read.bookId})
		$http.post("/Web2018/GetLikersByBook",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					angular.forEach(data, function(value, key) {
						if(angular.equals(value.username,loggedUser)) {
							$scope.liked = true;
						}
					});
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
		
	}

	$scope.buyBook = function(bookId) {
		$scope.optBuy = true;
		$scope.showDetails = false;
		desiredBook = bookId;
	}
	$scope.retToBrowseBooks = function() {
		$scope.optBuy = false;
		$scope.showDetails = true;
	}

	$scope.finishBuy = function() {
		/* INPUT VALIDITY CHECK! */
		/* Check card number contains only digits and size is 16 */
		var isnum = /^\d+$/.test($scope.regCardNum);
		if (!(isnum))
		{
			$scope.inputValidity = false;
			alert("Please fill Card Number again!");
			$scope.regCardNum = "";
		}
		if($scope.regCardNum.length != 16)
		{
			$scope.inputValidity = false;
			alert("Please fill Card Number again!");
			$scope.regCardNum = "";
		}
		
		/* Check Month consists of 2 digits */
		var isnum = /^\d+$/.test($scope.regMonth);
		if (!(isnum))
		{
			$scope.inputValidity = false;
			$scope.regMonth = "";
			alert("Please fill Month again!");
		}
		if($scope.regMonth.length != 2 && $scope.regMonth.length != 1)
		{
			$scope.inputValidity = false;
			$scope.regMonth = "";
			alert("Please fill Month again!");
		}
		if(parseInt($scope.regMonth) > 12)
		{
			$scope.inputValidity = false;
			$scope.regMonth = "";
			alert("Please fill Month again!");
		}
		/* Check Year consists of 2 digits */
		var isnum = /^\d+$/.test($scope.regYear);
		if (!(isnum))
		{
			$scope.inputValidity = false;
			$scope.regYear = "";
			alert("Please fill Year again!");
		}
		if($scope.regYear.length != 2)
		{
			$scope.inputValidity = false;
			$scope.regYear = "";
			alert("Please fill Year again!");

		}
		if(parseInt($scope.regYear) < 18)
		{
			$scope.inputValidity = false;
			$scope.regYear = "";
			alert("Please fill Year again!");
		}
		
		/* Check CVV consists of 3 digits */
		var isnum = /^\d+$/.test($scope.regCVV);
		if (!(isnum))
		{
			$scope.inputValidity = false;
			$scope.regCVV = "";
			alert("Please fill CVV again!");

		}
		if($scope.regCVV.length != 3)
		{
			$scope.inputValidity = false;
			$scope.regCVV = "";
			alert("Please fill CVV again!");
		}		
		if(!$scope.regCardCompany)
		{
			$scope.inputValidity = false;
			alert("Please fill Card Company again!");
		}

		var val = JSON.stringify({username:loggedUser, bookID:desiredBook, cardCompany:$scope.regCardCompany, cardNumber:$scope.regCardNum, expiryMonth:$scope.regMonth, expiryYear:$scope.regYear, cvv:$scope.regCVV, fullName:$scope.regFullName});
		if ($scope.inputValidity)
			{
				$http.post("/Web2018/BuyBook", val).success(
						function(data, status, headers, config) {
							$scope.optBuy = false;
							$scope.showDetails = false;
							$scope.regCVV = "";
							$scope.regYear = "";
							$scope.regMonth = "";
							$scope.regCardNum = "";
							$scope.regCardCompany = "";
						}).error(
						function(data, status, headers, config) {
							$scope.errorBox = "Error";
							$scope.regCVV = "";
							$scope.regYear = "";
							$scope.regMonth = "";
							$scope.regCardNum = "";
							$scope.regCardCompany = "";
						})
			}
		if(	$scope.inputValidity == false)
			{
				/* Print Error Message */
				alert("Please try again!");
				$scope.inputValidity = true;
			}
	}
	$scope.addReview = function(currRBook){
		$scope.showRDetails = false;
		$scope.popUpAddReview = true;
	}
	$scope.submitReview = function(currRBook){
		var val = JSON.stringify({username:loggedUser, nickname:loggedNick, bookID:currRBook.bookId, text:$scope.regRev});
		$http.post("/Web2018/AddReview",val).success(
				function(data, status, headers, config) {
					$scope.showRDetails = true;
					$scope.popUpAddReview = false;
					$scope.readBook();
					$scope.showReadingBook(currRBook);
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
	}
	$scope.CancelReview = function(){
		$scope.showRDetails = true;
		$scope.popUpAddReview = false;
	}
	$scope.collapseReviews = function(){
		$scope.showReviews = true;
	}
	$scope.collapseRReviews = function(){
		$scope.showRReviews = true;
	}
	$scope.hideReviews = function(){
		$scope.showReviews = false;
	}
	$scope.hideRReviews = function(){
		$scope.showRReviews = false;
	}
}]);























