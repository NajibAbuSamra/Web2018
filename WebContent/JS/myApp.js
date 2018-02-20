/**
 * 
 */
angular.module('myApp',[])
.controller('myAppCrtl', ['$scope','$http', function($scope,$http) {
    $scope.getMyCtrlScope = function() {
        return $scope;   
    }
	$scope.loggedIn=false;
	$scope.registered = true;
	$scope.isBrowsed = false;
	$scope.showDetails = false;
	$scope.optBuy = false;
	$scope.errorBox="";
	
	var loggedUser = "";
	var loggedPass = "";
	var desiredBook ="";
	/*LOGIN*/
	$scope.loginFunc = function(){
		var val = JSON.stringify({uName:$scope.uName , uPass:$scope.uPass});
		$http.post("/Web2018/Login",val).
		success(function(data,status,headers,config){
			$scope.loggedIn=true;
			loggedUser = data.username;
			loggedPass = data.password;
			 $scope.answer = loggedUser;
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

		$http.post("/Web2018/Register", val).success(
				function(data, status, headers, config) {
					$scope.loggedIn = true;
					loggedUser = data.username;
					loggedPass = $scope.regPass;
					 $scope.answer = loggedUser;
				}).error(
				function(data, status, headers, config) {
					$scope.errorBox = "Error";
					if (status == 400)
						$scope.errorBox = "User already exists";
				}).done(function(data) {});
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
					console.log(data);
					$scope.isBrowsed = true;
					$scope.browseBooks = data;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
					if(status == 401){
						/*wrong password, log user out and display error*/
					}
				})
	}
	
	/*Show Likes*/
	$scope.displayLikes = function(clicked_id){
		var val = JSON.stringify({bookid:clicked_id})
		$http.post("/Web2018/GetLikersByBook",val).success(
				function(data, status, headers, config) {
					/*Display Likes*/
					$scope.browseLikers = data;
				}).error(
				function(data, status, headers, config) {
					/*SHOW ERROR*/
				})
	}	
	$scope.showBook = function(clicked_id){
		console.log(clicked_id);
		$scope.showDetails = true;
		$scope.currBook = clicked_id;
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
		var val = JSON.stringify({username:loggedUser, bookId:desiredBook, cardCompany:$scope.regCardCompany, cardNumber:$scope.regCardNum, expiryMonth:$scope.regMonth, expiryYear:$scope.regYear, cvv:$scope.regCVV, fullName:$scope.regFullName});
		console.log(val);
		$http.post("/Web2018/BuyBook", val).success(
				function(data, status, headers, config) {
					$scope.optBuy = false;
					$scope.showDetails = true;
				}).error(
				function(data, status, headers, config) {
					$scope.errorBox = "Error";
						
				})
	}
}]);























