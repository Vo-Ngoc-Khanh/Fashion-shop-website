app.controller('ProductsController', function($scope, $routeParams, $http) {
  $scope.products = [];
  $scope.categories = [];
  $scope.product = {};
  var productIdEdit = $routeParams.productId;

  $scope.getProducts = function() {
    $http.get('http://localhost:9902/api/v1/products')
      .then(function(response) {
        $scope.products = response.data;
      })
      .catch(function(error) {
        console.log(error);
        $scope.products = [];
      });
  };

  $scope.getCategories = function() {
    $http.get('http://localhost:9902/api/v1/categories')
      .then(function(response) {
        $scope.categories = response.data;
      })
      .catch(function(error) {
        console.log(error);
        $scope.categories = [];
      });
  };

  $scope.createProduct = function() {
    var newProduct = {
      name: $scope.product.name,
      categoryId: $scope.product.categoryId,
      image: $scope.product.image,
      description: $scope.product.description,
      price: $scope.product.price,
      quantity: $scope.product.quantity,
      status: $scope.product.status,
    };

    var accessToken = localStorage.getItem('access_token');

    $http({
      method: 'POST',
      url: 'http://localhost:9902/api/v1/products',
      headers: {
        'Authorization': 'Bearer ' + accessToken
      },
      data: newProduct
    })
      .then(function(response) {
        toastr.success('Product added successfully.');
      })
      .catch(function(error) {
        console.log(error);
        toastr.error(error.data.message);
      });
  };

  $scope.deleteProduct = function(productId) {
    var accessToken = localStorage.getItem('access_token');

    $http({
      method: 'DELETE',
      url: 'http://localhost:9902/api/v1/products/' + productId,
      headers: {
        'Authorization': 'Bearer ' + accessToken
      }
    })
      .then(function(response) {
        $scope.getProducts();
        toastr.success('Product deleted successfully.');
      })
      .catch(function(error) {
        console.log(error);
        toastr.error('Failed to delete product.');
      });
  };

  $scope.getProductById = function() {
    if (productIdEdit) {
      var accessToken = localStorage.getItem('access_token');

      $http({
        method: 'GET',
        url: 'http://localhost:9902/api/v1/products/' + productIdEdit,
        headers: {
          'Authorization': 'Bearer ' + accessToken
        }
      })
        .then(function(response) {
          $scope.product = response.data;
          console.log($scope.product);
        })
        .catch(function(error) {
          console.log(error);
          $scope.product = null;
        });
    }
  };

  $scope.updateProduct = function() {
    var accessToken = localStorage.getItem('access_token');

    $http({
      method: 'PUT',
      url: 'http://localhost:9902/api/v1/products/' + productIdEdit,
      headers: {
        'Authorization': 'Bearer ' + accessToken
      },
      data: $scope.product
    })
      .then(function(response) {
        window.location.href = '#!/products/list';
        toastr.success('Changes saved successfully.');
      })
      .catch(function(error) {
        console.error('Error updating product:', error);
        toastr.error(error.data.message);
      });
  };

  $scope.updateProductStatus = function(product) {
    var accessToken = localStorage.getItem('access_token');

    $http({
      method: 'PUT',
      url: 'http://localhost:9902/api/v1/products/status/' + product.productId,
      headers: {
        'Authorization': 'Bearer ' + accessToken
      },
      data: { status: product.status }
    })
      .then(function(response) {
        $scope.getProducts();
        toastr.success('Product status updated successfully.');
      })
      .catch(function(error) {
        console.log(error);
        toastr.error(error.data.message);
      });
  };

  $scope.goToEditPage = function(productId) {
    window.location.href = '#!/products/edit/' + productId;
  };

  $scope.filterStatus = "";
  $scope.searchText = "";
  
  $scope.applyFilter = function() {
    $scope.filteredProducts = $scope.products.filter(function(product) {
      var statusMatch = ($scope.filterStatus === "") || (product.status === "active");
      var searchMatch = !$scope.searchText || product.name.toLowerCase().includes($scope.searchText.toLowerCase());
      return statusMatch && searchMatch;
    });
  };
  

  $scope.getCategories();
  $scope.getProducts();
  $scope.getProductById();
});
