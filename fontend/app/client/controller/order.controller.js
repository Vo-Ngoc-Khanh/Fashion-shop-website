app.controller('OrderController', function($scope, $http, $location) {

    $scope.orders=[];
    $scope.order={};
    $scope.ordered = true;
    var cart = $scope.cart.length;
    //order
    $scope.processOrder = function() {
    var orderData = {
        totalAmount: $scope.calculateTotal(),
        address: $scope.profile.address,
        phone: $scope.profile.phone,
        orderDetail: []
    };

    angular.forEach($scope.cart, function(item) {
        orderData.orderDetail.push({
            productId: item.id,
            quantity: item.quantity
        });
    });
    var accessToken = localStorage.getItem('access_token');
    $http({
        method: 'POST',
        url: 'http://localhost:9902/api/v1/orders',
        headers: {
        'Authorization': 'Bearer ' + accessToken
        },
        data: orderData
    })
        .then(function(response) {
            $scope.deleteAllItemsFromCart();
            $location.path("/home");
            toastr.success(response.data.message);
        })
        .catch(function(error) {
            toastr.error(error.data.message);
        });
    };

    //get order
    $scope.getOrder = function() {
    var accessToken = localStorage.getItem('access_token');
    $http({
        method: 'GET',
        url: 'http://localhost:9902/api/v1/orders/user',
        headers: {
            'Authorization': 'Bearer ' + accessToken
        }
        })
        .then(function(response) {           
            $scope.orders=response.data;
            if(cart === 0){
                $scope.ordered = false;
            }
        })
        .catch(function(error) {
            console.log(error);
        });
    };

    $scope.updateStatusOrder = function(orderId, status){
        var accessToken = localStorage.getItem('access_token');
        var updateData = {
            status: status ,
            cancelReason: $scope.cancelReason
        }
        console.log(updateData);
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
                $scope.cancelReason=null
            })
            .catch(function(error){
                console.log(error);
                toastr.error('Order status updated failed.');
        });
    };
    $scope.isFormValid1 = false;
    $scope.checkFormValidity1 = function() {
      if ($scope.cancelReason) {
        $scope.isFormValid1 = true;
      } else {
        $scope.isFormValid1 = false;
      }
    };

    //get order by id
    $scope.getModalOrderDetail = function(orderId) {
        var accessToken = localStorage.getItem('access_token');
        $http({
            method: 'GET',
            url: 'http://localhost:9902/api/v1/orders/user/'+orderId,
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }
            })
            .then(function(response) {           
                $scope.order=response.data;
            })
            .catch(function(error) {
                console.log(error);
            });
        };

    $scope.getOrder();
});
