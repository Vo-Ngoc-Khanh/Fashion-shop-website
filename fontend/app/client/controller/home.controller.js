app.factory('CartService', function() {
  var cart = [];
  // Kiểm tra xem đã có giỏ hàng trong localStorage chưa
  var storedCart = localStorage.getItem('cart');
  if (storedCart) {
    cart = JSON.parse(storedCart);
  }
  // Lưu giỏ hàng vào localStorage
  var saveCart = function() {
    localStorage.setItem('cart', JSON.stringify(cart));
  };

  return {
    getCart: function() {
      return cart;
    },
    addToCart: function(product, quantity) {
      var index = cart.findIndex(function(item) {
        return item.id === product.productId;
      });
    
      if (index !== -1) {
        cart[index].quantity += quantity;
      } else {
        var item = {
          id: product.productId,
          name: product.name,
          image: product.image,
          price: product.price,
          quantity: quantity
        };
        cart.push(item);
      }
      saveCart();
    },    
    deleteItemFromCart: function(index) {
      cart.splice(index, 1);
      saveCart();
    },
    deleteAllItemsFromCart: function() {
      cart = [];
      saveCart(); 
    },
    updateQuantity: function(index, quantity) {
      if (cart[index]) { // Kiểm tra xem phần tử trong giỏ hàng tồn tại tại chỉ mục đã chỉ định
        if (quantity <= 0) {
          cart.splice(index, 1);
        } else {
          cart[index].quantity = quantity;
        }
        saveCart();
      }
    }
  };
  });


  app.controller('HomeController', function($scope, $http, $routeParams, $location, CartService) {

  $scope.products = [];
  $scope.product = {};
  $scope.categories = [];
  $scope.search = [];
  $scope.profile ={};
  $scope.auth = false;
  $scope.admin = false;

  var productIdDetail = $routeParams.productId;
  var categoryName= $location.search().categoryName;
  var keyword= $location.search().keyword;


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
    $location.path('/home');
  };
  // Hàm truy vấn danh sách sản phẩm
  $scope.getAllProducts = function() {
    $http.get("http://localhost:9902/api/v1/products/active")
      .then(function(response) {
        $scope.products = response.data;
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  $scope.getCategories = function() {
    $http.get("http://localhost:9902/api/v1/categories")
      .then(function(response) {
        $scope.categories = response.data;
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  // Hàm truy vấn thông tin sản phẩm theo ID
  $scope.getProductById = function() {
    if (productIdDetail) {
      $http.get("http://localhost:9902/api/v1/products/" + productIdDetail)
        .then(function(response) {
          $scope.product = response.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  };

  //search by category
  $scope.findProductByCategory = function() {
    if (categoryName) {
      $http.get("http://localhost:9902/api/v1/products/category", { params: { categoryName: categoryName } })
        .then(function(response) {
          $scope.search = response.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  };

  //search by keyword
  $scope.findProductByKeyword = function() {
    if (keyword) {
      $http.get("http://localhost:9902/api/v1/products/keyword", { params: { keyword: keyword } })
        .then(function(response) {
          $scope.search = response.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  };

  $scope.gotoSearchPage = function(){
    $location.path('/search').search({ keyword: $scope.keyword });
  }

  $scope.gotoCheckoutPage = function(){

    ($scope.auth) ? $location.path('/checkout') : window.location.href = '/dashboard/view/auth/login.html';
  }

  $scope.quantity = 1;
  $scope.addToCart = function(product) {
    var quantity = parseInt($scope.quantity);
    CartService.addToCart(product, quantity);
    toastr.success('Add cart successfully');
  };
  

  $scope.deleteItemFromCart = function(index) {
    CartService.deleteItemFromCart(index);
  };

  $scope.deleteAllItemsFromCart = function() {
    CartService.deleteAllItemsFromCart();
  };

  $scope.updateQuantity = function(index, quantity) {
    CartService.updateQuantity(index, quantity);
  };

  $scope.calculateTotal = function() {
    var total = 0;
    for (var i = 0; i < $scope.cart.length; i++) {
      total += $scope.cart[i].price * $scope.cart[i].quantity;
    }
    return total;
  };

  $scope.getModalProductDetail = function(productId) {
      $http.get("http://localhost:9902/api/v1/products/" + productId)
      .then(function(response) {
          $scope.product = response.data;
      })
      .catch(function(error) {
          console.log(error);
          $scope.product=null;
      });
  };

  // Sử dụng $watch để theo dõi thay đổi trong giỏ hàng
  $scope.$watch(function() {
    return CartService.getCart();
  }, function(newCart) {
    $scope.cart = newCart;
  }, true);

  $scope.getAllProducts();
  $scope.getProductById();
  $scope.getCategories();
  $scope.findProductByCategory();
  $scope.findProductByKeyword();
  $scope.getProfile();
});

