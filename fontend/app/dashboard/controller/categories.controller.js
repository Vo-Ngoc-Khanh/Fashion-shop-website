app.controller('CategoriesController', function($scope, $http) {
  $scope.categories = [];
  $scope.categoryedit = {};
  $scope.showbutton = true;

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

  $scope.getCategoryById = function(categoryId) {
    var accessToken = localStorage.getItem('access_token');
    $http({
      method: 'GET',
      url: 'http://localhost:9902/api/v1/categories/' + categoryId,
      headers: {
        'Authorization': 'Bearer ' + accessToken
      }
    })
      .then(function(response) {
        $scope.categoryedit = response.data;
      })
      .catch(function(error) {
        console.log(error);
      });
  };

  $scope.addCategory = function() {
    var newCategory = {
      categoryName: $scope.category.categoryName
    };

    var accessToken = localStorage.getItem('access_token');

    $http({
      method: 'POST',
      url: 'http://localhost:9902/api/v1/categories',
      headers: {
        'Authorization': 'Bearer ' + accessToken
      },
      data: newCategory
    })
      .then(function(response) {
        $scope.getCategories();
        $scope.category.categoryName = '';
        toastr.success('Category added successfully.');
      })
      .catch(function(error) {
        console.log(error);
        toastr.error(error.data.message);
      });
  };

  $scope.updateCategory = function(category) {
    var updateCategory = {
      categoryName: category.categoryName
    };
    var accessToken = localStorage.getItem('access_token');
    $http({
      method: 'PUT',
      url: 'http://localhost:9902/api/v1/categories/' + category.categoryId,
      headers: {
        'Authorization': 'Bearer ' + accessToken
      },
      data: updateCategory
    })
      .then(function(response) {
        $scope.getCategories();
        toastr.success('Changes saved successfully.');
      })
      .catch(function(error) {
        console.log(error);
        toastr.error('Failed to save changes.');
      });
  };

  $scope.deleteCategory = function(categoryId) {
    var accessToken = localStorage.getItem('access_token');

    $http({
      method: 'DELETE',
      url: 'http://localhost:9902/api/v1/categories/' + categoryId,
      headers: {
        'Authorization': 'Bearer ' + accessToken
      }
    })
      .then(function(response) {
        $scope.getCategories();
        toastr.success('Category deleted successfully.');
      })
      .catch(function(error) {
        console.log(error);
        toastr.error('Failed to delete category.');
      });
  };

  $scope.isFormValid = false;
  $scope.checkFormValidity = function() {
    if ($scope.categoryedit && $scope.categoryedit.categoryName) {
      $scope.isFormValid = true;
    } else {
      $scope.isFormValid = false;
    }
  };
  

  // Load categories initially
  $scope.getCategories();
});
