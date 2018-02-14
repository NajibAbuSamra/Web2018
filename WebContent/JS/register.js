angular.module('registerUser', []).factory(
		'userRegister',
		[
				'$http',
				function($http) {

					// service method to be called upon text highlighting
					var register = function(username, email,
							address/* CHECK WITH REQUIREMENTS */, telephone,
							pass, passConfirm, nickname, description, photo) {

						/*VALIDITY CHECK HERE*/
						
						var val = JSON.stringify({
							uName : username,
							uPass : pass
							/*FILL REST*/
						});
						$http.post("/WebProject2018/Register", val).success(
								function(data, status, headers, config) {
									$scope.loggedIn = true;
								}).error(
								function(data, status, headers, config) {
									$scope.errorBox = "Error";
								});

					};

					return {
						register : register
					};

				} ]);