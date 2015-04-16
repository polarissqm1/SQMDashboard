"use strict";
/**
 * Reports Monitoring Controller
 */

	dashboardApp.controller('dashboardController', function($scope, $http, $filter, $log,$rootScope) {
		
		$log.log('dashboardController controller is initialized');
		$scope.enableChart=false;
		/**
		 * Dashboard Controller
		 */
		if($rootScope.selectedReleaseName){
			plotCharts();
		}
		
		$scope.onLpLoad = function() {
			$('#pagers').hide();
		};
		
		//Flot Pie Chart
		$(document).ready(function() {
			
			$http.get("dash/dashboard/getApplicationsList").success(function(response){
				 $rootScope.applicationList =[];
				for(var i=0; i<response.entity.length; i++){
					$rootScope.applicationList=response.entity[i];
				}
				//alert("Application List is"+$rootScope.applicationList);
				//alert(JSON.stringify(response));
			});
			
			
			$("#applicationCombo").on("change", "#application", function(event) {
				$rootScope.selectedProjectName = $("#application").val();
				//alert($rootScope.selectedProjectName);
				
				if($rootScope.selectedProjectName == '') {
					//alert("no application selected");
					window.location.href='index.html'
				}
				else if($rootScope.selectedProjectName) {
				validateVersions();
				
				}
				
				
			});
			$("#releaseCombo").on("change", "#release", function(event) {
				//alert("Hi");
				$rootScope.selectedReleaseName = $("#release").val();
				plotCharts();
				
			});
			

		});
		
		
		
		function validateVersions(){

			var appValue=$("#application").val();
						
			var elem1 = document.getElementById('release');
			$http.get("dash/dashboard/getReleaseList?projectName=" + $rootScope.selectedProjectName)
			 .success(function(response){
			   // alert("Release List is"+JSON.stringify(response));
				 for(var i=0;i<response.entity.length;i++){
					 elem1.options[i]=new Option(response.entity[i]);
				 }
				 
				 $scope.init =$http.get("dash/dashboard/getLandingInfo?projectName=" + $rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName)
				 .success(function(response){
					 //alert(JSON.stringify(response));
					if($("#application").val() && $("#release").val() ){
							$scope.enableChart=true;
						}
						else{
							$scope.enableChart=false;
						}
						
					 /*plotRagChart(response.entity.ragVO);
					 plotManualChart(response.entity.manual);
					 
					 plotAutomatedChart(response.entity.automation);
					 plotDefectStatusChart(response.entity.severityVO);
					 plotDistributionChart(response.entity.effortsVO);
						*/
					if(response.entity.automation.Passed == 0 && response.entity.automation.Failed == 0 && response.entity.automation.noRun == 0 && response.entity.automation.Blocked == 0 && response.entity.automation.Deferred == 0){
		 				
						 
						 
						 noData("#automation-pie-chart","Automated Execution Status");
						 
						 
						 if(response.entity.manual.Passed == 0 && response.entity.manual.Failed == 0 && response.entity.manual.noRun == 0 && response.entity.manual.Blocked == 0 && response.entity.manual.Deferred == 0){
				 				
								
							 noData("#manual-pie-chart","Manual Execution Status");
						 }
						 else{
							 plotManualChart(response.entity.manual);
						 }
						 
						 plotRagChart();
						
						 /*plotDefectStatusChart(response.entity.severityVO);*/
						 plotDefectStatusChart(response.entity.statusAndSeverity);
						 plotDistributionChart(response.entity.effortsVO);
						 noData();
						
					 }
					 
					 else {
						 
				 
					 plotRagChart();
					 plotManualChart(response.entity.manual);
					 
					 plotAutomatedChart(response.entity.automation);
					 /*plotDefectStatusChart(response.entity.severityVO);*/
					 plotDefectStatusChart(response.entity.statusAndSeverity);
					 plotDistributionChart(response.entity.effortsVO);
					 }
						
					
					
				 });
				 plotCharts();
			
			 });
		}

		function plotCharts(){
			if($("#application").val() && $("#release").val() ){
				$scope.enableChart=true;
			}
			else{
				$scope.enableChart=false;
			}
			
			var selectedProjectName = $("#application").val();
			var selectedReleaseName = $("#release").val();
			$rootScope.selectedProjectName = $("#application").val();
			$rootScope.selectedReleaseName = $("#release").val();
			//alert(selectedProjectName+" "+selectedReleaseName);
			
			 $http.get("dash/dashboard/getLandingInfo?projectName=" + $rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName)
			 .success(function(response){
				
				/* plotRagChart();
				 plotManualChart(JSON.stringify(response.entity.manual));
				 plotAutomatedChart(response.entity.automation);
				 plotDefectStatusChart(response.entity.severityVO);
				 plotDefectStatusChart(response.entity.statusAndSeverity[6]);
				 plotDistributionChart(response.entity.effortsVO);*/
				 
				
				 
				 if(response.entity.automation.Passed == 0 && response.entity.automation.Failed == 0 && response.entity.automation.noRun == 0 && response.entity.automation.Blocked == 0 && response.entity.automation.Deferred == 0){
					 				
					 
					 
					 noData("#automation-pie-chart","Automated Execution Status");
					 
					 
					 if(response.entity.manual.Passed == 0 && response.entity.manual.Failed == 0 && response.entity.manual.noRun == 0 && response.entity.manual.Blocked == 0 && response.entity.manual.Deferred == 0){
			 				
							
						 noData("#manual-pie-chart","Manual Execution Status");
					 }
					 else{
						 plotManualChart(response.entity.manual);
					 }
					 
					 plotRagChart();
					
					 /*plotDefectStatusChart(response.entity.severityVO);*/
					 plotDefectStatusChart(response.entity.statusAndSeverity);
					 plotDistributionChart(response.entity.effortsVO);
					 noData();
					
				 }
				 
				 else {
					 
			 
				 plotRagChart();
				 plotManualChart(response.entity.manual);
				 
				 plotAutomatedChart(response.entity.automation);
				 /*plotDefectStatusChart(response.entity.severityVO);*/
				 plotDefectStatusChart(response.entity.statusAndSeverity);
				 plotDistributionChart(response.entity.effortsVO);
				 }
				
			 });		
			 
			
			
		}

		function plotRagChart(response)
		{
			response= {"user":"user1","status":"g"};
			if(response.status == "g"){
				var green =20;
				var red =0;
				var amber =0;
			}
			else if(response.status == "r"){
				var red =20;
				var green =0;
				var amber =0;
			}
			else{
				var amber =20;
				var green =0;
				var red =0;
			}
			var arr=[["Red",red],
			         ["Amber",amber],
			         ["Green",green]
			];
			
			
			$('#flot-pie-chart')
			.highcharts(
					{
						chart : {
							height : 300,
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
							/*backgroundColor: {
						         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
						         stops: [
						            [0, '#2a2a2b'],
						            [1, '#3e3e40']
						         ]
						}*/
						},
						credits : {
							enabled : !0,
							/*text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",*/
							position : {
								align : "right",
								x : -10,
								verticalAlign : "bottom",
								y : -5
							},
							style : {
								cursor : "pointer",
								color : "blue",
								fontSize : "9px"
							}
						},
						colors: ['Red', 'Yellow', 'Green'],
						title : {
							text : 'RAG Status',
								style: {
						            fontSize: 'medium',
						            fontWeight: 'bold',
						            color : '#428bca'
						        },
						},
						tooltip : {
							pointFormat : '<b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
									formatter: function() {
				                        return Math.round(this.percentage*100)/100 + ' %';
				                    },
				                    distance: -30
								},
								showInLegend : true
							}
						},
						series : [ {
							type : 'pie',
							name : 'Execution',
							data : arr
						} ]
					});}

		function plotManualChart(response){

			
			var manual = [
			              ["Passed",parseInt(response.Passed)],
			              ["Failed",parseInt(response.Failed)],
			              ["No Run",parseInt(response.noRun)],
			              ["Blocked",parseInt(response.Blocked)],
			              ["Deferred",parseInt(response.Deferred)]
			              ];
			
			
			$('#manual-pie-chart')
			.highcharts(
					{
						chart : {
							height : 300,
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
							/*backgroundColor: {
						         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
						         stops: [
						            [0, '#2a2a2b'],
						            [1, '#3e3e40']
						         ]
						}*/
						},
						credits : {
							enabled : !0,
							/*text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",*/
							position : {
								align : "right",
								x : -10,
								verticalAlign : "bottom",
								y : -5
							},
							style : {
								cursor : "pointer",
								color : "blue",
								fontSize : "9px"
							}
						},
						colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ],
						
						title : {
							text : 'Manual Execution Status',
								style: {
						            fontSize: 'medium',
						            fontWeight: 'bold',
						            color : '#428bca'
						        },
						},
						tooltip : {
							pointFormat :'<b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
									formatter: function() {
				                        return Math.round(this.percentage*100)/100 + ' %';
				                    },
				                    distance: -30
								},
								showInLegend : true
							}
						},
						series : [ {
							type : 'pie',
							name : 'Execution',
							data : manual
						} ]
					});
		}


		function plotAutomatedChart(response){

			var auto = [
			              ["Passed",parseInt(response.Passed)],
			              ["Failed",parseInt(response.Failed)],
			              ["No Run",parseInt(response.noRun)],
			              ["Blocked",parseInt(response.Blocked)],
			              ["Deferred",parseInt(response.Deferred)]
			              ];
			
			$('#automation-pie-chart')
			.highcharts(
					{
						chart : {
							height : 300,
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
							/*backgroundColor: {
						         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
						         stops: [
						            [0, '#2a2a2b'],
						            [1, '#3e3e40']
						         ]
						}*/
						},
						credits : {
							enabled : !0,
							/*text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",*/
							position : {
								align : "right",
								x : -10,
								verticalAlign : "bottom",
								y : -5
							},
							style : {
								cursor : "pointer",
								color : "blue",
								fontSize : "9px"
							}
						},
						colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ],
						
						title : {
							text : 'Automated Execution Status',
								style: {
						            fontSize: 'medium',
						            fontWeight: 'bold',
						            color : '#428bca'
						        },
						},
						tooltip : {
							pointFormat : '<b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
									formatter: function() {
				                        return Math.round(this.percentage*100)/100 + ' %';
				                    },
				                    distance: -30
								},
								showInLegend : true
							}
						},
						series : [ {
							type : 'pie',
							name : 'Execution',
							data : auto
						} ]
					});
			
		}

		function plotDefectStatusChart(response){
			var defect = [
			              ["Urgent",parseInt(response.Urgent)],
			              ["High",parseInt(response.High)],
			              ["Medium",parseInt(response.Medium)],
			              ["Low",parseInt(response.Low)]
			             
			              ];
			$('#opendefect-pie-chart')
			.highcharts(
					{
						chart : {
							height : 300,
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
							/*backgroundColor: {
						         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
						         stops: [
						            [0, '#2a2a2b'],
						            [1, '#3e3e40']
						         ]
						}*/
						},
						credits : {
							enabled : !0,
							/*text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",*/
							position : {
								align : "right",
								x : -10,
								verticalAlign : "bottom",
								y : -5
							},
							style : {
								cursor : "pointer",
								color : "blue",
								fontSize : "9px"
							}
						},
						colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
						,
						title : {
							text : 'Open Defect Status',
							style: {
					            fontSize: 'medium',
					            fontWeight: 'bold',
					            color : '#428bca'
					        },
						},
						tooltip : {
							pointFormat : ' <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
									formatter: function() {
				                        return Math.round(this.percentage*100)/100 + ' %';
				                    },
				                    distance: -30
								},
								showInLegend : true
							}
						},
						series : [ {
							type : 'pie',
							name : 'Execution',
							data : defect
						} ]
					});
			
		}

		function plotDistributionChart(response){
			var distribution = [
			              ["SQM",60],
			              ["Non_SQM",40]
			             
			              ];
			$('#overall-pie-chart')
			.highcharts(
					{
						chart : {
							height : 300,
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
							/*backgroundColor: {
						         linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
						         stops: [
						            [0, '#2a2a2b'],
						            [1, '#3e3e40']
						         ]
						}*/
						},
						credits : {
							enabled : !0,
							/*text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",*/
							position : {
								align : "right",
								x : -10,
								verticalAlign : "bottom",
								y : -5
							},
							style : {
								cursor : "pointer",
								color : "blue",
								fontSize : "9px"
							}
						},
						colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000' ]
						,
						title : {
							text : 'Overall Distribution',
							style: {
					            fontSize: 'medium',
					            fontWeight: 'bold',
					            color : '#428bca'
					        },
						},
						tooltip : {
							pointFormat : '<b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
									formatter: function() {
				                        return Math.round(this.percentage*100)/100 + ' %';
				                    },
				                    distance: -30
								},
								showInLegend : true
							}
						},
						series : [ {
							type : 'pie',
							name : 'Execution',
							data : distribution
						} ]
					});
			
		}

function noData(id,title){
			
			
			$(id).html("<div class='flot-chart-content'><div><text x='278' text-anchor='middle' class='highcharts-title' style='color:#428bca;font-size:medium;font-weight:bold;fill:#428bca;width:491px;' y='22'><tspan>"+title+"</tspan></text></div><img class='img-responsive' src='images/nodata3.jpg' width='650' height='400' align='middle'/></div>");

			
           
		}
		


		});