  var app = angular.module('myApp', ['ngRoute']);

  app.config(function($routeProvider) {
    $routeProvider
    .when('/home', {
      templateUrl: '../client/view/home/home.html',
      controller: 'HomeController',
      title: 'Home page'
    })
    .when('/login', {
      templateUrl: '../client/view/auth/login.html',
      controller: 'LoginController',
      title: 'Login page'
    })
    .when('/view-all', {
      templateUrl: '../client/view/product/view-all.html',
      controller: 'HomeController',
      title: 'Shop',
    })
    .when('/detail/:productId', {
      templateUrl: '../client/view/product/detail.html',
      controller: 'HomeController',
      title: 'Detail product',
    })
    .when('/search/category', {
      templateUrl: '../client/view/product/search.html',
      controller: 'HomeController',
      title: 'Result search product'
    })  
    .when('/search', {
      templateUrl: '../client/view/product/search.html',
      controller: 'HomeController',
      title: 'Result search product'
    })    
    .when('/cart', {
      templateUrl: '../client/view/cart/cart.html',
      controller: 'HomeController',
      title: 'Your cart',
    })
    .when('/checkout', {
      templateUrl: '../client/view/checkout/checkout.html',
      controller: 'OrderController',
      title: 'Checkout',
    })
    .when('/my-account', {
      templateUrl: '../client/view/auth/my-account.html',
      controller: 'OrderController',
      title: 'My Account'
    })
    .when('/contact', {
      templateUrl: '../client/view/contact/contact.html',
      title: 'Contact page'
    })
    .when('/about', {
      templateUrl: '../client/view/about/about.html',
      title: 'About page'
    })
    .when('/404', {
      templateUrl: '../client/view/error/404.html',
      title: 'Page not found'
    })
    .otherwise({
      redirectTo: '/404'
    });
  });

  app.run(function($rootScope, $anchorScroll) {
    $rootScope.$on('$routeChangeSuccess', function(event, current, previous) {
      $rootScope.title = current.$$route.title;
      $anchorScroll();
    });
});
