var app = angular.module('myApp', []);

app.controller('AuthController', function($scope, $http) {

  $scope.register = function() {

    if ($scope.register.password.length < 8) {
      toastr.error('Password must be at least 8 characters');
      return;
    }

    if ($scope.register.password !== $scope.register.confirmPassword) {
      toastr.error('Passwords do not match');
      return;
    }

    var registerData = {
      fullname: $scope.register.fullname,
      email: $scope.register.email,
      password: $scope.register.password
    };

    $http.post('http://localhost:9902/api/v1/auth/register', registerData)
      .then(function(response) {
        window.location.href = "/dashboard/view/auth/login.html";
        toastr.success(response.data.message);
      })
      .catch(function(error) {
        toastr.error(error.data.message);
        console.log(error);
      });
  };

  $scope.login = function() {
    if ($scope.login.password.length < 8) {
      toastr.error('Password must be at least 8 characters');
      return;
    }

    var loginData = {
      email: $scope.login.email,
      password: $scope.login.password
    };

    $http.post('http://localhost:9902/api/v1/auth/login', loginData)
      .then(function(response) {
        var accessToken = response.data.accessToken;
        localStorage.setItem('access_token', accessToken);
        window.location.href = "/client/index.html#!/home";
      })
      .catch(function(error) {
        toastr.error('Login failed. Please check your credentials and try again.');
      });
  };
});
