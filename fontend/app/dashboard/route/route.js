// Khởi tạo ứng dụng AngularJS
var app = angular.module('myApp', ['ngRoute']);

// Định nghĩa các route
app.config(function($routeProvider) {
  $routeProvider
    .when('/dashboard', {
      templateUrl: '../dashboard/view/dashboard.html',
      title: 'Dashboard'
    })
    .when('/categories/list', {
      templateUrl: '../dashboard/view/category/list.html',
      controller: 'CategoriesController',
      title: 'List categories'
    })
    .when('/products/list', {
      templateUrl: '../dashboard/view/product/list.html',
      controller: 'ProductsController',
      title: 'List products'
    })
    .when('/products/add', {
      templateUrl: '../dashboard/view/product/add.html',
      controller: 'ProductsController',
      title: 'Add new'
    })
    .when('/products/edit/:productId', {
      templateUrl: '../dashboard/view/product/edit.html',
      controller: 'ProductsController',
      title: 'Edit product'
    })
    .when('/orders/list', {
      templateUrl: '../dashboard/view/order/list.html',
      controller: 'OrderController',
      title: 'List Orders'
    })
    .otherwise({
      redirectTo: '/dashboard'
    });
});

// Đặt title cho từng route
app.run(function($rootScope,$location) {
  $rootScope.$on('$routeChangeSuccess', function(event, current, previous) {
    $rootScope.title = current.$$route.title;
  });
});