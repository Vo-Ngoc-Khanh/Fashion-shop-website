app.controller('OrderController', function($scope, $http, $location) {

    $scope.orders=[];
    $scope.order={};

    //get order
    $scope.getOrder = function() {
    var accessToken = localStorage.getItem('access_token');
    $http({
        method: 'GET',
        url: 'http://localhost:9902/api/v1/orders',
        headers: {
            'Authorization': 'Bearer ' + accessToken
        }
        })
        .then(function(response) {           
            $scope.orders=response.data;
        })
        .catch(function(error) {
            console.log(error);
        });
    };

    //get order by id
    $scope.getModalOrderDetail = function(orderId) {
        var accessToken = localStorage.getItem('access_token');
        $http({
            method: 'GET',
            url: 'http://localhost:9902/api/v1/orders/'+orderId,
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }
            })
            .then(function(response) {           
                $scope.order=response.data;
                $scope.order
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    $scope.updateStatusOrder = function(orderId, status){
        var accessToken = localStorage.getItem('access_token');
        var updateData = {
            status: status ,
            eddate: $scope.eddate,
            cancelReason: $scope.cancelReason
        }
        $http({
            method:'PUT',
            url:'http://localhost:9902/api/v1/orders/status/'+orderId,
            headers:{
                'Authorization': 'Bearer '+ accessToken
            },
            data: updateData    
            })
            .then(function(response){
                $scope.getOrder();
                toastr.success('Order status updated successfully.');
                $scope.cancelReason='',
                $scope.eddate= null
            })
            .catch(function(error){
                console.log(error);
                toastr.error('Order status updated failed.');
        });
    }

    $scope.isFormValid1 = false;
    $scope.checkFormValidity1 = function() {
      if ($scope.cancelReason) {
        $scope.isFormValid1 = true;
      } else {
        $scope.isFormValid1 = false;
      }
    };

    $scope.isFormValid2 = false;
    $scope.checkFormValidity2 = function() {
      if ($scope.eddate) {
        $scope.isFormValid2 = true;
      } else {
        $scope.isFormValid2 = false;
      }
    };

    $scope.filterStatus = "";
    $scope.searchText = "";
    $scope.filterDate = null; // Thêm giá trị mặc định cho bộ lọc ngày
    
    $scope.applyFilter = function() {
        $scope.filteredOrders = $scope.orders.filter(function(order) {
        var statusMatch = ($scope.filterStatus === "") || (order.status === $scope.filterStatus);
        var searchMatch = !$scope.searchText || order.user.fullname.toLowerCase().includes($scope.searchText.toLowerCase());
        
        return statusMatch && searchMatch;
        });
    };
        

    $scope.getOrder();
});
