app.controller('DashboardController', function($scope, $http) {
    $scope.profile ={};
    $scope.auth = false;
    $scope.admin = false;

    //profile
    $scope.getProfile = function() {
        var accessToken = localStorage.getItem('access_token');
        if (!$scope.auth && accessToken !== null) {    
        $http({
            method: 'GET',
            url: 'http://localhost:9902/api/v1/auth/profile',
            headers: {
            'Authorization': 'Bearer ' + accessToken
            }
        })
            .then(function(response) {
            $scope.profile = response.data;
            $scope.auth = true;
            if(response.data.roleName === "ROLE_ADMIN"){
                $scope.admin = true;
            }
            })
            .catch(function(error) {
            console.log(error);
            $scope.profile = null;
            });
        }
    };
    //logout 
    $scope.logout = function() {
        // Xóa access_token từ local storage
        localStorage.removeItem('access_token');
        $scope.auth = false;
        $scope.admin = false;
        window.location.href = '/client/index.html#!/home';
    };

    $scope.getProfile();
});

