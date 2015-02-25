dashboardApp.controller('navigationController', function($scope, $location, $routeParams , $anchorScroll, $modal, $timeout, $log) {

	$log.log('navigation controller initialized');

	$scope.tabList = [

	/*{displayLabel : 'Onboarding', href : '', tabIndex : '1', hasSubMenu : true, subMenuItems : [
		{displayLabel : 'Producers', href : '/producerList', tabIndex : '', type : 'submenu'},
		{displayLabel : 'Events/Templates', href : '/eventTypes', tabIndex : '', type : 'submenu'}
	]},
*/
	{displayLabel : 'Dashboard', href : '/dashboard', tabIndex : '1', hasSubMenu : false},
	{displayLabel : 'Reports', href : '/dailystatus', tabIndex : '2', hasSubMenu :   true, subMenuItems : [
   		{displayLabel : 'Release', href : '/producerList', tabIndex : '', type : 'submenu'},
   		{displayLabel : 'JIRA', href : '/producerList', tabIndex : '', type : 'submenu'},
   		{displayLabel : 'Defects', href : '/producerList', tabIndex : '', type : 'submenu'},
   		{displayLabel : 'Daily Status Report', href : '/producerList', tabIndex : '', type : 'submenu'},
   		{displayLabel : 'Reporting & Metrics', href : '/producerList', tabIndex : '', type : 'submenu'}
	]},
	{displayLabel : 'Reports', href : '/reportsMonitoring', tabIndex : '3', hasSubMenu :   true, subMenuItems : [
			{displayLabel : 'Admin List', href : '/producerList', tabIndex : '', type : 'submenu'},
			{displayLabel : 'User List', href : '/producerList', tabIndex : '', type : 'submenu'}
	]},
	{displayLabel : 'Logout', href : '/generalView', tabIndex : '4', hasSubMenu : false}


	];

	$scope.subMenuItems = [];
	//$scope.selectedTabIndex = 1;

	$scope.loadTab = function(tab){

		$log.log('load view is invoked:'+tab);

        var view = null;
        if (tab.hasSubMenu) {
            $scope.subMenuItems = tab.subMenuItems;
            view = $scope.subMenuItems[0].href;
        }
        else {
            $scope.subMenuItems = [];
            view = tab.href;
        }

		$location.path(view);
	}

	$scope.loadView = function(href){
		$log.log('load view is invoked:'+href);
		$location.path(href);
	}


	function initApp()
	{
	    $scope.loadTab($scope.tabList[0]);
	}

	initApp();


});