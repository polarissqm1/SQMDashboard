/**
 * 
 */

dashboardApp.controller('dailystatuscontroller', function($scope,$http,$rootScope) {
/*	alert($rootScope.selectedProjectName +'----------------'+
			$rootScope.selectedReleaseName );*/
	
	$scope.onDsrLoad = function() {
		$('#pagers').hide();
	};
	
	$(document).ready(function() {
		
		//$('#topchart').setHeight('200');
		
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
	//	alert(JSON.stringify(response));
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
		if(parseInt($scope.tcstatus[5].count)==0){
			$scope.dd='--';
		}else{
			$scope.dd=Math.round((parseInt($scope.names[5].Total)/parseInt($scope.tcstatus[5].count)*1000))/1000;	
		}
		
		$scope.dsi=Math.round(((4*parseInt($scope.names[5].Urgent)+3*parseInt($scope.names[5].High)+2*parseInt($scope.names[5].Medium)+parseInt($scope.names[5].Low))/parseInt($scope.names[5].Total)*100))/100;
		$scope.tcsData= [
		                  [$scope.tcstatus[0].status,   parseInt($scope.tcstatus[0].percentage)],
		                  [$scope.tcstatus[1].status,       parseInt($scope.tcstatus[1].percentage)],
		                  [$scope.tcstatus[2].status,   parseInt($scope.tcstatus[2].percentage)],
		                  [$scope.tcstatus[3].status,     parseInt($scope.tcstatus[3].percentage)],
		                  [$scope.tcstatus[4].status,   parseInt($scope.tcstatus[4].percentage)],
		                 /* [$scope.tcstatus[5].status,   parseInt($scope.tcstatus[5].percentage)]*/
		                 
		              ];
		if(parseInt($scope.tcstatus[0].percentage) == 0 && parseInt($scope.tcstatus[1].percentage) == 0 && parseInt($scope.tcstatus[2].percentage) == 0 && parseInt($scope.tcstatus[3].percentage) == 0 && parseInt($scope.tcstatus[4].percentage) == 0){
			
			$scope.noData("#tcsChart","Overall Testcase Excecution");
		}	
		//$scope.check();
		else{
			
			 $scope.plottcsChart(); 
		}
		
        if(parseInt($scope.names[0].Total) == 0 && parseInt($scope.names[1].Total) == 0 && parseInt($scope.names[2].Total) == 0 && parseInt($scope.names[3].Total) == 0 && parseInt($scope.names[4].Total) == 0){
			
			$scope.noData("#statusWise","Overall Defects - Status Wise");
		}	
		//$scope.check();
		else{
			
			$scope.plotstatusWiseChart();
		}
        if(parseInt($scope.names[6].Urgent) == 0 && parseInt($scope.names[6].High) == 0 && parseInt($scope.names[6].Medium) == 0 && parseInt($scope.names[6].Low) == 0){
			
			$scope.noData("#severityWise","Overall Defects - Severity Wise");
		}	
		//$scope.check();
		else{
			
			$scope.plotseverityWiseChart();
		}
		 
		 
		}	
	});
	
  	$scope.tcstatus ='';
	$scope.names ='';
	$scope.renderChart = function(){
        	$scope.tcstatus1 = $http.get("dash/dailyreports/getDailyReportsInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName).success(function(response){
        	//	alert(JSON.stringify(response));
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
        		if(parseInt($scope.tcstatus[5].count)==0){
        			$scope.dd= '--';
        		}
        		else{
        			$scope.dd=Math.round((parseInt($scope.names[5].Total)/parseInt($scope.tcstatus[5].count)*1000))/1000;	
        		}
        		$scope.dsi=Math.round(((4*parseInt($scope.names[5].Urgent)+3*parseInt($scope.names[5].High)+2*parseInt($scope.names[5].Medium)+parseInt($scope.names[5].Low))/parseInt($scope.names[5].Total)*100))/100;
        		$scope.tcsData= [
       		                  [$scope.tcstatus[0].status,   parseInt($scope.tcstatus[0].percentage)],
       		                  [$scope.tcstatus[1].status,       parseInt($scope.tcstatus[1].percentage)],
       		                  [$scope.tcstatus[2].status,   parseInt($scope.tcstatus[2].percentage)],
       		                  [$scope.tcstatus[3].status,     parseInt($scope.tcstatus[3].percentage)],
       		                  [$scope.tcstatus[4].status,   parseInt($scope.tcstatus[4].percentage)],
       		                  /*[$scope.tcstatus[5].status,   parseInt($scope.tcstatus[5].percentage)]*/
       		                 
       		              ];
        		//$scope.check();
        		if(parseInt($scope.tcstatus[0].percentage) == 0 && parseInt($scope.tcstatus[1].percentage) == 0 && parseInt($scope.tcstatus[2].percentage) == 0 && parseInt($scope.tcstatus[3].percentage) == 0 && parseInt($scope.tcstatus[4].percentage) == 0){
        			
        			$scope.noData("#tcsChart","Overall Testcase Excecution");
        		}	
        		//$scope.check();
        		else{
        			
        			 $scope.plottcsChart(); 
        		}
        		
                if(parseInt($scope.names[0].Total) == 0 && parseInt($scope.names[1].Total) == 0 && parseInt($scope.names[2].Total) == 0 && parseInt($scope.names[3].Total) == 0 && parseInt($scope.names[4].Total) == 0){
        			
        			$scope.noData("#statusWise","Overall Defects - Status Wise");
        		}	
        		//$scope.check();
        		else{
        			
        			$scope.plotstatusWiseChart();
        		}
                if(parseInt($scope.names[6].Urgent) == 0 && parseInt($scope.names[6].High) == 0 && parseInt($scope.names[6].Medium) == 0 && parseInt($scope.names[6].Low) == 0){
        			
        			$scope.noData("#severityWise","Overall Defects - Severity Wise");
        		}	
        		//$scope.check();
        		else{
        			
        			$scope.plotseverityWiseChart();
        		}
        	} 
        	});
	};
            
            $scope.plottcsChart = function(){
            	Highcharts.setOptions({
					colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
				});
            	$('#tcsChart').highcharts({
                    chart: {
                    	height: 165,
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
			                        return this.y + ' %';
			                    },
			                    distance: -20
                            }
                        }
                    },
                    legend: {
                        enabled: true,
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
        				labelFormatter: function() {
        					return this.name;
        				}
                    },
                    series: [{
                        type: 'pie',
                        data:$scope.tcsData
                    }]
                });
            	
        };
            
            $scope.getDatetime = new Date;
            
            $scope.plotstatusWiseChart = function(){
            	Highcharts.setOptions({
					colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
				});
            	$('#statusWise').highcharts({
                    chart: {
                    	height: 185,
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
                            		$scope.percent = this.y + ' %';
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
            	
        };
            
            $scope.plotseverityWiseChart = function(){
            	Highcharts.setOptions({
					colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
				});
            	$('#severityWise').highcharts({
                    chart: {
                    	height: 185,
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
			                        return this.y + ' %';
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
            	
        };
            $scope.noData = function(id,title){
            	
            	$(id).html("<div class='row text-center'><text x='278' text-anchor='middle' class='highcharts-title' style='color:#428bca;font-size:medium;font-weight:bold;fill:#428bca;width:491px;' y='22'><tspan>"+title+"</tspan></text></div><img class='img-responsive center-block' src='images/nodata3.jpg' width='168' height='168' align='middle'/>");

            	
            };
            
            
        });