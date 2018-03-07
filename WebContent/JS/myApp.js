/**
 * 
 */
angular.module('myApp',[])
.controller('myAppCrtl', ['$scope','$http', '$window', function($scope,$http,$window) {
    $scope.getMyCtrlScope = function() {
        return $scope;   
    }
	$scope.loggedIn=false;
	$scope.liked = false;
	$scope.registered = true;
	$scope.isBrowsed = false;
	$scope.isReading = false;
	$scope.reading = false;
	$scope.unverifiedRevs = true;
	$scope.showDetails = false;
	$scope.viewBuys = true;
	$scope.dispTransactions = false;
	$scope.showUserDetailsInRead = false;
	$scope.admin = false;
	$scope.isisHovering = false;
	$scope.optBuy = false;
	$scope.showRDetails = false;
	$scope.inputValidity = true;
	$scope.regValidity = true;
	$scope.errorBox="";
	$scope.popUpAddReview = false;
	$scope.showReviews = false;
	$scope.showRReviews = false;
	$scope.pressed = false;

	
	var loggedUser = "";
	var loggedPass = "";
	var desiredBook ="";
	var loggedNick = "";
	/*LOGIN*/
	$scope.loginFunc = function(){
		var val = JSON.stringify({uName:$scope.uName , uPass:$scope.uPass});
		$http.post("/BooksForAll/Login",val).
		success(function(data,status,headers,config){
			$scope.loggedIn=true;
			$scope.user = data.nickname;
			loggedUser = data.username;
			loggedPass = data.password;
			loggedNick = data.nickname;
			$scope.isBrowsed = false;
			$scope.showContent = false;
			$scope.isReading = false;
			$scope.showDetails = false;
			if(data.type == 1) 
				$scope.admin = true;
			}).
		error(function(data,status,headers,config){
			if (status == 404)
			{
				alert("User does not exist, please register!");
				$scope.uName = "";
				$scope.uPass = "";
			} 
			if (status == 403)
			{
				alert("Incorrect password! please try again!");
				$scope.uPass = "";
			}
			if(status==500)
			{
				alert("Server error");
			}
		});
	}
	/*REGISTER*/
	$scope.registerFunc = function() {
		/*VALIDITY CHECK HERE*/
		if ($scope.regPic == "" || $scope.regPic == null)
			$scope.regPic = "defaultUserImg.png";

		if ($scope.regAddress == "" || $scope.regAddress == null)
			$scope.regAddress = "Address not Specified";				

		if ($scope.regDesc == "" || $scope.regDesc == null)
			$scope.regDesc = "Empty description";		
		
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
			$http.post("/BooksForAll/Register", val).success(
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
						if (status == 403) {
							alert("Username not available!");
							$scope.regUname = "";
						}
						if (status == 500) {
							alert("Unexpected error!");
							$scope.regUname = "";
							$scope.regNick = "";
							$scope.regPass = "";
							$scope.regEmail = "";
							$scope.regAddress = "";
							$scope.regTelephone = "";
							$scope.regPic = "";
							$scope.regDesc = "";
						}
						if (status == 400) {
							alert("Invalid registeration!");
							$scope.regUname = "";
							$scope.regNick = "";
							$scope.regPass = "";
							$scope.regEmail = "";
							$scope.regAddress = "";
							$scope.regTelephone = "";
							$scope.regPic = "";
							$scope.regDesc = "";
						}
					});
		}
		regValidity = true;
	};
	/*Used to hide registeration div if user presses login*/
	$scope.toggleRegister = function(){
		$scope.registered = !($scope.registered);
		$scope.regUname = "";
		$scope.regNick = "";
		$scope.regPass = "";
		$scope.regEmail = "";
		$scope.regAddress = "";
		$scope.regTelephone = "";
		$scope.regPic = "";
		$scope.regDesc = "";
		$scope.uName = "";
		$scope.uPass = "";
	}
	/*LOGOUT*/
	$scope.logout = function(){
		$scope.loggedIn = false;
	}
	
	
	/*Return list of not owned ebooks*/
	$scope.browse = function(){
		var val = JSON.stringify({uName:loggedUser , uPass:loggedPass})
		$http.post("/BooksForAll/GetAvailableBooks",val).success(
				function(data, status, headers, config) {
					/*PUT BOOKS IN PROPER DIV*/
					$scope.isBrowsed = true;
					$scope.isReading = false;
					$scope.browseBooks = data;
					$scope.optBuy = false;
					$scope.showRDetails = false;
					$scope.viewBuys = true;	
					$scope.dispTransactions = false;
					$scope.showUserDetailsInRead = false;
					$scope.showContent = false;
					$scope.unverifiedRevs = true;
					$scope.showUnapprovedReviews = false;
					$scope.isBrowsingUsers = false;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 403)
					{
						alert("Invalid User");
						$scope.logout();
					}
					if(status==500)
					{
						alert("Server Error");
					}
					if(status==401)
					{
						alert("Unauthorized user, invalid password");
						$scope.logout();
					}
				})
	}
	
	/* Return list of owned books */
	$scope.readBook =function() {
		var val = JSON.stringify({uName:loggedUser , uPass:loggedPass})
		/* Change getAvailableBooks with GetMyBooks*/
		$http.post("/BooksForAll/GetMyBooks",val).success(
				function(data, status, headers, config) {
					/*PUT BOOKS IN PROPER DIV*/
					$scope.isReading = true;
					$scope.isBrowsed = false;
					$scope.optBuy = false;
					$scope.showContent = false;
					$scope.isBrowsingUsers = false;
					$scope.dispTransactions = false;
					$scope.viewBuys = true;	
					$scope.unverifiedRevs = true;
					$scope.showUnapprovedReviews = false;
					$scope.myBooks = data;
					$scope.showUserDetailsInRead = false;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 403)
					{
						alert("Invalid User");
						$scope.logout();
					}
					if(status==500)
					{
						alert("Server Error");
					}
					if(status==401)
					{
						alert("Unauthorized user, invalid password");
						$scope.logout();
					}
				})
	}
	
	/* Return list of active users */
	$scope.browseUsers = function() {
		var val = JSON.stringify({uName:loggedUser , uPass:loggedPass})
		/* Change getAvailableBooks with GetMyBooks*/
		$http.post("/BooksForAll/GetAllUsers",val).success(
				function(data, status, headers, config) {
					/*PUT BOOKS IN PROPER DIV*/
					$scope.isBrowsingUsers = true;
					$scope.isBrowsed = false;
					$scope.isReading = false;
					$scope.optBuy = false;
					$scope.showContent = false;
					$scope.showRDetails = false;
					$scope.showUserDetailsInRead = false;
					$scope.showContent = false;
					$scope.viewBuys = true;
					$scope.dispTransactions = false;
					$scope.users = data;
					$scope.unverifiedRevs = true;
					$scope.showUnapprovedReviews = false;
					console.log(data);
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 401){
						alert("Unauthorized user");
					}
					if(status == 400)
					{
						alert("Bad request");
						$scope.logout();
					}
					if(status==500)
					{
						alert("Server Error");
					}
				})

	}
	
	/*Show Likes*/
	$scope.displayLikes = function(bookID){
		var val = JSON.stringify({bookid:bookID})
		$http.post("/BooksForAll/GetLikersByBook",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.browseLikers = data;
					$scope.isHovering = true;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status==500)
					{
						alert("Server Error");
					}
					if(status==404)
					{
						alert("Book not found");
					}
					if(status==400)
					{
						alert("Invalid Book id");
					}
				})
	}	
	
	$scope.hideLikes = function() {
		$scope.pressed = false;
	}
	/* When user likes an owned book it is added to the likes of that book*/
	$scope.updateLikers = function(book) {
		var val = JSON.stringify({username:loggedUser, bookid:book.bookId})
		$http.post("/BooksForAll/AddLike",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.liked = true;
					$scope.displayLikes(book.bookId);
					$scope.currRBook.likes = $scope.currRBook.likes + 1; 
				}).error(
				function(data, status, headers, config) {
					if(status == 403)
					{
						alert("Invalid User");
						$scope.logout();
					}
					if(status==500)
					{
						alert("Server Error");
					}
					if(status==404)
					{
						alert("Book not found");
					}
				})
	}
	
	/* Unlike a book and update*/
	$scope.removeLike = function(book) {
		var val = JSON.stringify({username:loggedUser, bookid:book.bookId})
		$http.post("/BooksForAll/RemoveLike",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.liked = false;
					$scope.browseLikers = data;
					$scope.currRBook.likes = $scope.currRBook.likes - 1; 
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 400)
					{
						alert("Like not found");
						$scope.liked = false;	
					}
					if(status == 403)
					{
						alert("User not found");
						$scope.logout();	
					}
					if(status==500)
					{
						alert("Server Error");
					}
					if(status==404)
					{
						alert("Like not found");
						$scope.liked = false;
					}					
				})
	}
	
	$scope.dontDispLikes = function(){
		$scope.isHovering = false;
	}
	
	/* When user presses on a book in the list, more info is shown*/
	$scope.showBook = function(clicked_id){
		$scope.booksReviews = clicked_id.reviews;
		$scope.showDetails = true;
		$scope.currBook = clicked_id;
		$scope.showReviews = false;
		$scope.optBuy = false;
		$scope.counter = 1;
		$scope.isBrowsingUsers = false;
		$scope.showUserDetails = false;
		$scope.dispTransactions = false;
		$scope.unverifiedRevs = true;
		$scope.showUnapprovedReviews = false;
		$scope.showContent = false;
		$scope.showUserDetailsInRead = false;
		$scope.viewBuys = true;	
		$scope.pressed = false;
	}	
	
	/* When admin presses on user this function is triggered*/
	$scope.showUser = function(user, flag){
		if (flag == "1") {
			$scope.showDetails = false;
			$scope.showUserDetails = true;
		}
		else {
			$scope.showUserDetailsInRead = true;
			$scope.showDetails = false;
		}
		$scope.unverifiedRevs = true;
		$scope.showUnapprovedReviews = false;
		$scope.currUser = user;
		$scope.dispTransactions = false;
		$scope.showReviews = false;
		$scope.optBuy = false;
		$scope.showContent = false;
		$scope.pressed = false;
		$scope.dispTransactions = false;
		$scope.viewBuys = true;	


		console.log(flag);
		console.log(user);
	}	
	
	/* Used to hide user details if admin presses on his name in likers*/
	$scope.retFromViewingUserInLikers = function(){
		$scope.showDetails = true;
		$scope.showUserDetailsInRead = false;
	}	
	
	
	$scope.showReadingBook = function(book_being_read){
		$scope.showRDetails = true;
		$scope.popUpAddReview = false;
		$scope.dispTransactions = false;
		$scope.popUpAddReview = false;
		$scope.currRBook = book_being_read;
		$scope.showContent = false;
		$scope.isBrowsingUsers = false;
		$scope.isReading = true;
		$scope.viewBuys = true;	

		
		var val = JSON.stringify({bookid:book_being_read.bookId})
		$http.post("/BooksForAll/GetLikersByBook",val).success(
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
	/* Triggered when admin presses the delete button on user profile */
	$scope.deleteUser = function(user, flag) {
		var val = JSON.stringify({username:user.username})
		$http.post("/BooksForAll/RemoveUser",val).success(
				function(data, status, headers, config) {
					if (flag == "1") {
						$scope.browseUsers();
					} else {
						$scope.browse();						
					}
					$scope.showUserDetails = false;
					$scope.showDetails = false;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 404)
					{
						alert("User not found");
						$scope.liked = false;	
					}
					if(status==500)
					{
						alert("Server Error");
					}	
				})
	}
	/* Returns the transactions of the specific book*/
	$scope.recentTransactions = function(bookId) {
		var val = JSON.stringify({bookid:bookId})
		$http.post("/BooksForAll/GetTransactionsByBook",val).success(
				function(data, status, headers, config) {
					$scope.dispTransactions = true;
					$scope.transactions = data;
					$scope.viewBuys = false;
					$scope.showReviews = false;
					$scope.pressed = false;
					$scope.showUnapprovedReviews = false;
					$scope.unverifiedRevs = true;
					console.log(data);
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status==500)
					{
						alert("Server Error");
					}
					if(status == 404)
					{
						alert("Book not found");
					}
					if(status == 400)
					{
						alert("Invalid book id");
					}
				})
	}
	/* This function can only be triggered by admin and it displays the people who bought that book*/
	$scope.dispLikesforAdmin = function(bookID){
		var val = JSON.stringify({bookid:bookID})
		$http.post("/BooksForAll/GetLikersByBook",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.browseLikersForAdmin = data;
					$scope.dispTransactions = false;
					$scope.viewBuys = true;	
					$scope.popUpAddReview = false;
					$scope.showUnapprovedReviews = false;
					$scope.showReviews = false;
					$scope.unverifiedRevs = true;
					$scope.pressed = true;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
	}
	/*Used to hide the transaction div*/
	$scope.hideTransactions = function(){
		$scope.viewBuys = true;	
		$scope.dispTransactions = false;
	}
	/*Shows the div which contains the buy filling information*/
	$scope.buyBook = function(bookId) {
		$scope.optBuy = true;
		$scope.showDetails = false;
		desiredBook = bookId;
	}
	/* if user decides not to buy, the buying div is hidden*/
	$scope.retToBrowseBooks = function() {
		$scope.optBuy = false;
		$scope.showDetails = true;
	}

	/* Triggered when user payment and billing information are filled*/
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
		console.log("before");
		var val = JSON.stringify({username:loggedUser, bookID:desiredBook, cardCompany:$scope.regCardCompany, cardNumber:$scope.regCardNum, expiryMonth:$scope.regMonth, expiryYear:$scope.regYear, cvv:$scope.regCVV, fullName:$scope.regFullName, address:$scope.regAddressCity});
		if ($scope.inputValidity)
			{
				console.log("After validity");
				$http.post("/BooksForAll/BuyBook", val).success(
						function(data, status, headers, config) {
							$scope.optBuy = false;
							$scope.showDetails = false;
							$scope.regCVV = "";
							$scope.regYear = "";
							$scope.regMonth = "";
							$scope.regCardNum = "";
							$scope.regCardCompany = "";
							$scope.browse();
						}).error(
						function(data, status, headers, config) {
							$scope.errorBox = "Error";
							$scope.regCVV = "";
							$scope.regYear = "";
							$scope.regMonth = "";
							$scope.regCardNum = "";
							$scope.regCardCompany = "";
							if(status == 403)
							{
								alert("Invalid User");
								$scope.logout();
							}
							if(status==500)
							{
								alert("Server Error");
							}
							if(status==404)
							{
								alert("Book does not exist");
							}
							if(status==400)
							{
								alert("Invalid Transaction or Book already owned");
							}							
						})
			}
		if(	$scope.inputValidity == false)
			{
				/* Print Error Message */
				alert("Please try again!");
				$scope.inputValidity = true;
			}
	}
	/* Displays the review div*/
	$scope.addReview = function(currRBook){
		$scope.showRDetails = false;
		$scope.popUpAddReview = true;
		$scope.showContent = false;
	}
	/* When user writes a review and submits it, this function is triggered */
	$scope.submitReview = function(currRBook){
		if($scope.regRev == "" || $scope.regRev == null) {
			alert("Please write review before submitting!");
		} else {
			var val = JSON.stringify({username:loggedUser, nickname:loggedNick, bookID:currRBook.bookId, text:$scope.regRev});
			$http.post("/BooksForAll/AddReview",val).success(
					function(data, status, headers, config) {
						$scope.showRDetails = true;
						$scope.popUpAddReview = false;
						$scope.readBook();
						$scope.showReadingBook(currRBook);
						$scope.regRev = "";
					}).error(
					function(data, status, headers, config) {
						/*SHOW ERROR*/
						if(status == 403)
						{
							alert("Invalid User");
							$scope.logout();
						}
						if(status==500)
						{
							alert("Server Error");
						}
						if(status==404)
						{
							alert("Book not found");
						}
						if(status==400)
						{
							alert("Invalid review");
						}
					})
		}
	}
	/* Triggered when user wants to go back from writing a review */
	$scope.CancelReview = function(){
		$scope.showRDetails = true;
		$scope.popUpAddReview = false;
	}
	/* Shows the review in browsing not owned books*/
	$scope.collapseReviews = function(){
		$scope.showReviews = true;
		$scope.viewBuys = true;	
		$scope.pressed = false;
		$scope.unverifiedRevs = true;
		$scope.showUnapprovedReviews = false;
		$scope.dispTransactions = false;
	}
	/* shows the reviews in the owned books*/
	$scope.collapseRReviews = function(){
		$scope.showRReviews = true;
		$scope.showContent = false;
	}
	/* hides the reviews in the not owned books*/
	$scope.hideReviews = function(){
		$scope.showReviews = false;
	}
	/* hides the reviews in the owned books*/
	$scope.hideRReviews = function(){
		$scope.showRReviews = false;
	}
	/* Opens book for reading*/
	$scope.openBookForReading = function (currRBook) {
		$scope.showContentOfBook = currRBook.link;
		$scope.showRDetails = false;
		$scope.isReading = false;
		$scope.reading = true;
	}
	/* Saves scrolling reading position when user exits from reading the book*/
	$scope.saveSpot = function (currRBook) {
		var pos = $(".ReadWrapper").scrollTop();
		var val = JSON.stringify({username:loggedUser, bookid:currRBook.bookId, ypos:pos});
		$http.post("/BooksForAll/SaveScrollPosition",val).success(
				function(data, status, headers, config) {
					$scope.showRDetails = true;
					$scope.isReading = true;
					$scope.reading = false;
					$(".ReadWrapper").scrollTop(0);
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 400)
					{
						alert("Invalid username or book id");
					}
					if(status==500)
					{
						alert("Server Error");
					}	
				})
	}
	/* Triggered when user decides to read from last place (loads where he left)*/
	$scope.openBookForReadingFromLastPosi = function (currRBook) {
		var val = JSON.stringify({username:loggedUser, bookid:currRBook.bookId, ypos:0});
		$http.post("/BooksForAll/GetScrollPosition",val).success(
				function(data, status, headers, config) {
					$(".ReadWrapper").scrollTop(data);
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 404)
					{
						alert("No saved position");
					}
					if(status==500)
					{
						alert("Server Error");
					}
					if(status == 400)
					{
						alert("Invalid username or book id");
					}
				})
	}
	$scope.hideUnverifiedReviews = function() {
		$scope.unverifiedRevs = true;
		$scope.showUnapprovedReviews = false;
	}
	/* Return list of unverified reviews for admin*/
	$scope.unverifiedReviews = function(bookId) {
		var val = JSON.stringify({bookid : bookId});
		$http.post("/BooksForAll/GetUnapprovedReviewsForBook",val).success(
				function(data, status, headers, config) {
					$scope.showUnapprovedReviews = true;
					$scope.unverifiedRevs = false;
					$scope.unapprovedReviews = data;
					$scope.pressed = false;
					$scope.dispTransactions = false;
					$scope.showReviews = false;
				}).error(
				function(data, status, headers, config) {
					if(status==500)
					{
						alert("Server Error");
					}
					if(status==400)
					{
						alert("Invalid book id");
					}
					if(status==404)
					{
						alert("Book not found");
					}
				})		
	}
	/* Triggered when admin approves a review */
	$scope.approveReview = function(reviewIndex) {
		if ($scope.revIndex == "" || $scope.revIndex == null) {
			$scope.revIndex = "";
			alert("Please insert the index of the review!");			
		} else {
			var val = JSON.stringify({reviewid : $scope.unapprovedReviews[$scope.revIndex-1].id});
			var bookID = $scope.unapprovedReviews[$scope.revIndex-1].bookID;
			$http.post("/BooksForAll/VerifyReview",val).success(
					function(data, status, headers, config) {
						$scope.revIndex = "";
						$scope.unverifiedReviews(bookID);
					}).error(
					function(data, status, headers, config) {
						/*SHOW ERROR*/
						if(status == 404)
						{
							alert("Review not found");
						}
						if(status==500)
						{
							alert("Server Error");
						}
					})		
		}
	}
}]);
