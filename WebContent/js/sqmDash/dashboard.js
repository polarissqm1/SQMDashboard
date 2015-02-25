var dashboardApp = angular.module("dashboardApp", ['ngRoute', 'ngAnimate', 'ui.bootstrap', 'ngTable']); //, 'ngGrid'

  
var Server_URL = '';



dashboardApp.config(function($routeProvider) {

    $routeProvider
    .when('/dashboard', {
		templateUrl : 'pages/landingpage.html',
		controller : 'dashboardController'
	});
	$routeProvider.when('/dailystatus', {
		templateUrl : 'pages/dailyStatus.html',
		controller : 'dailystatuscontroller'
	});
	/*$routeProvider.when('/login', {
		templateUrl : 'pages/login',
		controller : loginController
	});
	$routeProvider.when('/login', {
		templateUrl : 'pages/login',
		controller : loginController
	});

	$routeProvider.otherwise({
		redirectTo : 'pages/login'
	});*/

    

});