/**
 * 
 */
angular.module('loginApp',[])
.controller('loginCtr', ['$scope','$http', function($scope,$http) {
	$scope.loggedIn=false;
	$scope.errorBox="";
	$scope.loginFunc = function(){
		var val = JSON.stringify({uName:$scope.uName , uPass:$scope.uPass});
		$http.post("/WebProject2018/Login",val).
		success(function(data,status,headers,config){
			$scope.loggedIn=true;
			}).
		error(function(data,status,headers,config){
			$scope.errorBox="Error";
			});
	}
	
}]);