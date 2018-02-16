/**
 * 
 */
angular.module('myApp',[])
.controller('myAppCrtl', ['$scope','$http', function($scope,$http) {
	$scope.loggedIn=false;
	$scope.registered = true;
	$scope.errorBox="";
	$scope.loginFunc = function(){
		var val = JSON.stringify({uName:$scope.uName , uPass:$scope.uPass});
		$http.post("/Web2018/Login",val).
		success(function(data,status,headers,config){
			$scope.loggedIn=true;
			$scope.errorBox = "loged-in";
			}).
		error(function(data,status,headers,config){
			$scope.errorBox="Error";
			});
	}
	$scope.registerFunc = function() {
		/*VALIDITY CHECK HERE*/
		
		var val = JSON.stringify({username:$scope.regUname , email:$scope.regEmail ,  address:$scope.regAddress, phone:$scope.regTelephone, 
			password:$scope.regPass, nickname:$scope.regNick, picture:$scope.regPic, description:$scope.regDesc, type:0});

		$http.post("/Web2018/Register", val).success(
				function(data, status, headers, config) {
					$scope.loggedIn = true;
					$scope.errorBox = "registered";
				}).error(
				function(data, status, headers, config) {
					$scope.errorBox = "Error";
				}).done(function(data) {
					var retVal = data[0].statusMessage;
				});
	};
	
	$scope.toggleRegister = function(){
		$scope.registered = !($scope.registered);
	}
}]);
