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
	$scope.tcstatus1 = $http.get("dash/dailyreports/getDailyReportsInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName).success(function(response){
		alert(JSON.stringify(response));
		if(!response.entity.testCaseExecutionStatusVO){
			$("#tcsChart").hide();
			$("#statusWise").hide();
			$("#severityWise").hide();
			$("#topGrid").hide();
			$("#bottomGrid").hide();
			$("#middle2").hide();
		}
		else if(!response.entity.statusAndSeverityVO){
			$("#tcsChart").hide();
			$("#statusWise").hide();
			$("#severityWise").hide();
			$("#topGrid").hide();
			$("#bottomGrid").hide();
			$("#middle2").hide();
		}else {
			$("#topGrid").show();
			$("#middle2").show();
			$("#bottomGrid").show();
			$("#tcsChart").show();
			$("#statusWise").show();
			$("#severityWise").show();
			
		$scope.tcstatus =response.entity.testCaseExecutionStatusVO;
		$scope.names =response.entity.statusAndSeverityVO;  
		$scope.dd=Math.round((parseInt($scope.names[5].Total)/parseInt($scope.tcstatus[5].count)*1000))/1000;
		$scope.dsi=Math.round(((4*parseInt($scope.names[5].Urgent)+3*parseInt($scope.names[5].High)+2*parseInt($scope.names[5].Medium)+parseInt($scope.names[5].Low))/parseInt($scope.names[5].Total)*100))/100;
		$scope.tcsData= [
		                  [$scope.tcstatus[0].status,   parseInt($scope.tcstatus[0].percentage)],
		                  [$scope.tcstatus[1].status,       parseInt($scope.tcstatus[1].percentage)],
		                  [$scope.tcstatus[2].status,   parseInt($scope.tcstatus[2].percentage)],
		                  [$scope.tcstatus[3].status,     parseInt($scope.tcstatus[3].percentage)],
		                  [$scope.tcstatus[4].status,   parseInt($scope.tcstatus[4].percentage)],
		                  [$scope.tcstatus[5].status,   parseInt($scope.tcstatus[5].percentage)]
		                 
		              ];
			
		//$scope.check();
		 $scope.plottcsChart(); 
		 $scope.plotstatusWiseChart();
		 $scope.plotseverityWiseChart();
		}	
	});
	
  	$scope.tcstatus ='';
	$scope.names ='';
	$scope.renderChart = function(){
        	$scope.tcstatus1 = $http.get("dash/dailyreports/getDailyReportsInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName).success(function(response){
        		alert(JSON.stringify(response));
        		if(!response.entity.testCaseExecutionStatusVO){
        			$("#tcsChart").hide();
        			$("#statusWise").hide();
        			$("#severityWise").hide();
        			$("#topGrid").hide();
        			$("#bottomGrid").hide();
        			$("#middle2").hide();
        		}
        		else if(!response.entity.statusAndSeverityVO){
        			$("#tcsChart").hide();
        			$("#statusWise").hide();
        			$("#severityWise").hide();
        			$("#topGrid").hide();
        			$("#bottomGrid").hide();
        			$("#middle2").hide();
        		}else{
        			$("#topGrid").show();
        			$("#middle2").show();
        			$("#bottomGrid").show();
        			$("#tcsChart").show();
        			$("#statusWise").show();
        			$("#severityWise").show();
        		$scope.tcstatus =response.entity.testCaseExecutionStatusVO;
        		$scope.names =response.entity.statusAndSeverityVO;   
        		$scope.dd=Math.round((parseInt($scope.names[5].Total)/parseInt($scope.tcstatus[5].count)*1000))/1000;
        		$scope.dsi=Math.round(((4*parseInt($scope.names[5].Urgent)+3*parseInt($scope.names[5].High)+2*parseInt($scope.names[5].Medium)+parseInt($scope.names[5].Low))/parseInt($scope.names[5].Total)*100))/100;
        		$scope.tcsData= [
       		                  [$scope.tcstatus[0].status,   parseInt($scope.tcstatus[0].percentage)],
       		                  [$scope.tcstatus[1].status,       parseInt($scope.tcstatus[1].percentage)],
       		                  [$scope.tcstatus[2].status,   parseInt($scope.tcstatus[2].percentage)],
       		                  [$scope.tcstatus[3].status,     parseInt($scope.tcstatus[3].percentage)],
       		                  [$scope.tcstatus[4].status,   parseInt($scope.tcstatus[4].percentage)],
       		                  [$scope.tcstatus[5].status,   parseInt($scope.tcstatus[5].percentage)]
       		                 
       		              ];
        		//$scope.check();
        		 $scope.plottcsChart(); 
        		 $scope.plotstatusWiseChart();
        		 $scope.plotseverityWiseChart();
        		}
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
					colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
				});
            	$('#tcsChart').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Overall Testcase Excecution',
                        style: {
				            fontSize: 'medium',
				            fontWeight: 'bold',
				            color : '#428bca'
				        },
                    },
                    tooltip : {
						pointFormat : '<b>{point.percentage:.1f}%</b>'
					},
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            showInLegend : true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                               // format: '{point.percentage:.1f} %'
                                formatter: function() {
			                        return Math.round(this.percentage*100)/100 + ' %';
			                    },
			                    distance: -30
                            }
                        }
                    },
                    legend: {
                        enabled: true,
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle'
        				/*labelFormatter: function() {
        					return this.name + ' ' + this.y + '%';
        				}*/
                    },
                    series: [{
                        type: 'pie',
                        data:$scope.tcsData
                    }]
                });
            	
        }
            
            $scope.getDatetime = new Date;
            
            $scope.plotstatusWiseChart = function(){
            	Highcharts.setOptions({
					colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
				});
            	$('#statusWise').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Overall Defects - Status Wise',
                        style: {
				            fontSize: 'medium',
				            fontWeight: 'bold',
				            color : '#428bca'
				        },
                    },
                    tooltip : {
						pointFormat : '<b>{point.percentage:.1f}%</b>'
					},
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            showInLegend : true,
                            cursor: 'pointer',
                            dataLabels: {
                            	enabled: true,
                                //format: '{point.percentage:.1f} %',
                            	formatter: function() {
                            		$scope.percent = Math.round(this.percentage*100)/100 + ' %'
			                        return $scope.percent;
			                    },
			                    distance: -30
                            }
                        }
                    },
                    legend: {
                        enabled: true,
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle'
        				/*labelFormatter: function() {
        					return this.name + ' ' + $scope.percent;
        				}*/
                    },
                    series: [{
                        type: 'pie',
                        data: [
                            [$scope.names[0].statusSeverity.slice(0,4),    parseInt($scope.names[0].Total)],
                            [$scope.names[1].statusSeverity.slice(0,5),       parseInt($scope.names[1].Total)],
                            [$scope.names[2].statusSeverity.slice(0,9),    parseInt($scope.names[2].Total)],
                            [$scope.names[3].statusSeverity,     parseInt($scope.names[3].Total)],
                            [$scope.names[4].statusSeverity,  parseInt($scope.names[4].Total)]
                        ]
                    }]
                });
            	
        }
            
            $scope.plotseverityWiseChart = function(){
            	Highcharts.setOptions({
					colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
				});
            	$('#severityWise').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: 'Overall Defects - Severity Wise',
                        style: {
				            fontSize: 'medium',
				            fontWeight: 'bold',
				            color : '#428bca'
				        },
                    },
                    tooltip : {
						pointFormat : '<b>{point.percentage:.1f}%</b>'
					},
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            showInLegend : true,
                            cursor: 'pointer',
                            dataLabels: {
                            	enabled: true,
                                //format: '{point.percentage:.1f} %',
                            	formatter: function() {
			                        return Math.round(this.percentage*100)/100 + ' %';
			                    },
			                    distance: -30
                            }
                        }
                    },
                    legend: {
                        enabled: true,
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle'
        				/*labelFormatter: function() {
        					return this.name + ' ' + this.y + '%';
        				}*/
                    },
                    series: [{
                        type: 'pie',
                        /*name: 'Browser share',*/
                        data: [
                            ['Urgent',    parseInt($scope.names[6].Urgent)],
                            ['High',        parseInt($scope.names[6].High)],
                            ['Medium',     parseInt($scope.names[6].Medium)],
                            ['Low',     parseInt($scope.names[6].Low)]
                        ]
                    }]
                });
            	
        }
            
        });