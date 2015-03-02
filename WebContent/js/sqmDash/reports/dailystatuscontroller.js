/**
 * 
 */

dashboardApp.controller('dailystatuscontroller', function($scope,$http,$rootScope) {
/*	alert($rootScope.selectedProjectName +'----------------'+
			$rootScope.selectedReleaseName );*/
	$(document).ready(function() {
		
		$("#applicationCombo").on("change", "#application", function(event) {
			$rootScope.selectedProjectName = $("#application").val();
			
			$scope.renderChart();
			
		});
		$("#releaseCombo").on("change", "#release", function(event) {
			//alert("Hi");
			$rootScope.selectedReleaseName = $("#release").val();
			
			$scope.renderChart();
			
		});

	});
	$scope.tcstatus1 = $http.get("dash/dashboard/getLandingInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName).success(function(response){
		//alert(JSON.stringify(response.entity.statusAndSeverityVO));
		$scope.tcstatus =response.entity.testCaseExecutionStatusVO;
		$scope.names =response.entity.statusAndSeverityVO;   

		//$scope.check();
		 $scope.plottcsChart(); 
		 $scope.plotstatusWiseChart();
		 $scope.plotseverityWiseChart();
		
	});
  	$scope.tcstatus ='';
	$scope.names ='';
	$scope.renderChart = function(){
        	$scope.tcstatus1 = $http.get("dash/dashboard/getLandingInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName).success(function(response){
        		//alert(JSON.stringify(response.entity.statusAndSeverityVO));
        		$scope.tcstatus =response.entity.testCaseExecutionStatusVO;
        		$scope.names =response.entity.statusAndSeverityVO;   
        
        		//$scope.check();
        		 $scope.plottcsChart(); 
        		 $scope.plotstatusWiseChart();
        		 $scope.plotseverityWiseChart();
        		
        	});
	}
            
        	/*$scope.check=function(){
        		alert(JSON.stringify($scope.names));
        		alert($scope.tcstatus[0].count);	
        	}
        	*/
           /*	$scope.names = [
           	                {status: "Open/New/Reopened/Assigned", urgent: 121 , high: 192, medium: 142, low: 40, total: 379},
           	                {status: "Fixed/Ready for Re-test", urgent: 50, high: 76, medium: 67, low: 21, total: 172},
           	                {status: "Closed", urgent: 250, high: 3514, medium: 3054, low: 1495, total: 8683},
           	                {status: "Duplicate/Rejected", urgent: 76, high: 282, medium: 299, low: 62, total: 672},
           	                {status: "Deferred", urgent: 2, high: 90, medium: 93, low: 40, total: 225},
           	                {status: "Total", urgent: 664, high: 4154, medium: 3655, low: 1658, total: 10191},
           	                {status: "Percentage(%)", urgent: 6.56, high: 41.01, medium: 36.08, low: 16.37, total: 100.00}
           	            ];
        	
        	
           	$scope.tcstatus = [{status: "Passed", count: 24493, percentage: "78.35%"},
                               {status: "Failed", count: 3753, percentage: "50%"},
                               {status: "Not Run", count: 2028, percentage: "10%"},
                               {status: "N/A", count: 72, percentage: "50%"},
                               {status: "Deferred", count: 268, percentage: "15%"},
                   			{status: "Blocked", count: 648, percentage: "58%"},
                   			{status: "Total", count: 31262, percentage: "85%"}];
        	
        	$scope.tcstatus = [{status: "Passed", count: 24493, percentage: "78.35%"},
                            {status: "Failed", count: 3753, percentage: "50%"},
                            {status: "Not Run", count: 2028, percentage: "10%"},
                            {status: "N/A", count: 72, percentage: "50%"},
                            {status: "Deferred", count: 268, percentage: "15%"},
                			{status: "Blocked", count: 648, percentage: "58%"},
                			{status: "Total", count: 31262, percentage: "85%"}];*/
            
        	/*$scope.names = [
        	                {status: "Open/New/Reopened/Assigned", urgent: 121 , high: 192, medium: 142, low: 40, total: 379},
        	                {status: "Fixed/Ready for Re-test", urgent: 50, high: 76, medium: 67, low: 21, total: 172},
        	                {status: "Closed", urgent: 250, high: 3514, medium: 3054, low: 1495, total: 8683},
        	                {status: "Duplicate/Rejected", urgent: 76, high: 282, medium: 299, low: 62, total: 672},
        	                {status: "Deferred", urgent: 2, high: 90, medium: 93, low: 40, total: 225},
        	                {status: "Total", urgent: 664, high: 4154, medium: 3655, low: 1658, total: 10191},
        	                {status: "Percentage(%)", urgent: 6.56, high: 41.01, medium: 36.08, low: 16.37, total: 100.00}
        	            ];*/
           
            $scope.plottcsChart = function(){
            	Highcharts.setOptions({
            		colors: ['#8dd3c7', '#ffffb3', '#bebada', '#fb8072', '#80b1d3', '#fdb462', 'b3de69']
            	});
            	$('#tcsChart').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: 1,//null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Overall Testcase Excecution'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            showInLegend : true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '{point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        /*name: 'Browser share',*/
                        data: [
                            [$scope.tcstatus[0].status,   parseInt($scope.tcstatus[0].percentage)],
                            [$scope.tcstatus[1].status,       parseInt($scope.tcstatus[1].percentage)],
                            [$scope.tcstatus[2].status,   parseInt($scope.tcstatus[2].percentage)],
                            [$scope.tcstatus[3].status,     parseInt($scope.tcstatus[3].percentage)],
                            [$scope.tcstatus[4].status,   parseInt($scope.tcstatus[4].percentage)],
                            [$scope.tcstatus[5].status,   parseInt($scope.tcstatus[5].percentage)]
                           
                        ]
                    }]
                });
            	
        }
            
            $scope.getDatetime = new Date;
            
            $scope.plotstatusWiseChart = function(){
            	Highcharts.setOptions({
            		colors: ['#8dd3c7', '#ffffb3', '#bebada', '#fb8072', '#80b1d3', '#fdb462', 'b3de69']
            	});
            	$('#statusWise').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: 1,//null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Overall Defects- Status Wise'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            showInLegend : true,
                            cursor: 'pointer',
                            dataLabels: {
                            	enabled: true,
                                format: '{point.percentage:.1f} %',
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        /*name: 'Browser share',*/
                        data: [
                            [$scope.names[0].statusSeverity,    parseInt($scope.names[0].total)],
                            [$scope.names[1].statusSeverity,       parseInt($scope.names[1].total)],
                            [$scope.names[2].statusSeverity,    parseInt($scope.names[2].total)],
                            [$scope.names[3].statusSeverity,     parseInt($scope.names[3].total)],
                            [$scope.names[4].statusSeverity,  parseInt($scope.names[4].total)]
                        ]
                    }]
                });
            	
        }
            
            $scope.plotseverityWiseChart = function(){
            	Highcharts.setOptions({
            		colors: ['#8dd3c7', '#ffffb3', '#bebada', '#fb8072', '#80b1d3', '#fdb462', 'b3de69']
            	});
            	$('#severityWise').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: 1,//null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Overall Defects- Severity Wise'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            showInLegend : true,
                            cursor: 'pointer',
                            dataLabels: {
                            	enabled: true,
                                format: '{point.percentage:.1f} %',
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        /*name: 'Browser share',*/
                        data: [
                            ['urgent',    parseInt($scope.names[6].urgent)],
                            ['high',        parseInt($scope.names[6].high)],
                            ['medium',     parseInt($scope.names[6].medium)],
                            ['low',     parseInt($scope.names[6].low)]
                        ]
                    }]
                });
            	
        }
            
        });