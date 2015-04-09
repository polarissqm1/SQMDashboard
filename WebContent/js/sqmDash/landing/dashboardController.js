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
			
			/*$http.get("dash/dashboard/getApplicationsList").success(function(response){
				$rootScope.applicationList=response.entity;
				alert("Application List is"+$rootScope.applicationList);
				alert(JSON.stringify(response));
			});*/

			$("#applicationCombo").on("change", "#application", function(event) {
				$rootScope.selectedProjectName = $("#application").val();
				validateVersions();
				
				
				
			});
			$("#releaseCombo").on("change", "#release", function(event) {
				//alert("Hi");
				$rootScope.selectedReleaseName = $("#release").val();
				plotCharts();
				
			});
			

		});
		
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
			 plotManualChart(response.entity.manualVO);
			 
			 plotAutomatedChart(response.entity.automationVO);
			 plotDefectStatusChart(response.entity.severityVO);
			 plotDistributionChart(response.entity.effortsVO);
				*/
			if(response.entity.automationVO.passed == 0 && response.entity.automationVO.failed == 0 && response.entity.automationVO.noRun == 0 && response.entity.automationVO.blocked == 0 && response.entity.automationVO.deferred == 0){
 				
				 
				 
				 noData("#automation-pie-chart","Automated Execution Status");
				 
				 
				 if(response.entity.manualVO.passed == 0 && response.entity.manualVO.failed == 0 && response.entity.manualVO.noRun == 0 && response.entity.manualVO.blocked == 0 && response.entity.manualVO.deferred == 0){
		 				
						
					 noData("#manual-pie-chart","Manual Execution Status");
				 }
				 else{
					 plotManualChart(response.entity.manualVO);
				 }
				 
				 plotRagChart();
				
				 /*plotDefectStatusChart(response.entity.severityVO);*/
				 plotDefectStatusChart(response.entity.statusAndSeverityVO[6]);
				 plotDistributionChart(response.entity.effortsVO);
				 noData();
				
			 }
			 
			 else {
				 
		 
			 plotRagChart();
			 plotManualChart(response.entity.manualVO);
			 
			 plotAutomatedChart(response.entity.automationVO);
			 /*plotDefectStatusChart(response.entity.severityVO);*/
			 plotDefectStatusChart(response.entity.statusAndSeverityVO[6]);
			 plotDistributionChart(response.entity.effortsVO);
			 }
				
			
			
		 });
		
		function validateVersions(){

			var appValue=$("#application").val();
						
			var elem1 = document.getElementById('release');
			/*$http.get("dash/dashboard/getReleaseList?projectName=" + $rootScope.selectedProjectName)
			 .success(function(response){
			    //  alert("Release List is"+JSON.stringify(response));
				 for(var i=0;i<response.entity.length;i++){
					 elem1.options[i]=new Option(response.entity[i]);
			*/
			//elem1.options[1]=new Option("CFP Reporting: Sprint 2");
			//elem1.options[2]=new Option("CFP Reporting: Sprint 3");
			//elem1.options[1]=new Option("Cleint On Boarding Dashboard March 27 2015 release");
			elem1.options[1]=new Option("Plexus 5.2 upgrade April release");
			/*elem1.options[3]=new Option("Cleint On Boarding Doc Mgmt March 27 2015 release");
			elem1.options[4]=new Option("CFP Reporting: March Release");
			elem1.options[5]=new Option("ReMit Mar 20th release");*/
			/*elem1.options[8]=new Option("iWatch: March release");*/
			elem1.options[2]=new Option("iManage Mar 20 Rel");
			//elem1.options[7]=new Option("OTCC - Mar 27 release");
				 
			 
			/*if(appValue=="PMP"){
				
		    	elem1.options[1]=new Option("pmpRelease1");
		    	elem1.options[2]=new Option("pmpRelease2");
				
			}else if(appValue=="iwatch"){
				elem1.options[1]=new Option("iwatchRelease1");
		    	elem1.options[2]=new Option("iwatchRelease2");
		    	
			}else if(appValue=="CFPR"){
				elem1.options[1]=new Option("cfprRelease1");
		    	elem1.options[2]=new Option("cfprRelease2");
			}else if(appValue=="CFT_POST_TRADE"){
				elem1.options[1]=new Option("CFPReportingSprint2");

			}else{
				elem1=null;
			}*/
	/*	}
	});*/
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
				 plotManualChart(JSON.stringify(response.entity.manualVO));
				 plotAutomatedChart(response.entity.automationVO);
				 plotDefectStatusChart(response.entity.severityVO);
				 plotDefectStatusChart(response.entity.statusAndSeverityVO[6]);
				 plotDistributionChart(response.entity.effortsVO);*/
				 
				
				 
				 if(response.entity.automationVO.passed == 0 && response.entity.automationVO.failed == 0 && response.entity.automationVO.noRun == 0 && response.entity.automationVO.blocked == 0 && response.entity.automationVO.deferred == 0){
					 				
					 
					 
					 noData("#automation-pie-chart","Automated Execution Status");
					 
					 
					 if(response.entity.manualVO.passed == 0 && response.entity.manualVO.failed == 0 && response.entity.manualVO.noRun == 0 && response.entity.manualVO.blocked == 0 && response.entity.manualVO.deferred == 0){
			 				
							
						 noData("#manual-pie-chart","Manual Execution Status");
					 }
					 else{
						 plotManualChart(response.entity.manualVO);
					 }
					 
					 plotRagChart();
					
					 /*plotDefectStatusChart(response.entity.severityVO);*/
					 plotDefectStatusChart(response.entity.statusAndSeverityVO[6]);
					 plotDistributionChart(response.entity.effortsVO);
					 noData();
					
				 }
				 
				 else {
					 
			 
				 plotRagChart();
				 plotManualChart(response.entity.manualVO);
				 
				 plotAutomatedChart(response.entity.automationVO);
				 /*plotDefectStatusChart(response.entity.severityVO);*/
				 plotDefectStatusChart(response.entity.statusAndSeverityVO[6]);
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
			              ["Passed",parseInt(response.passed)],
			              ["Failed",parseInt(response.failed)],
			              ["No Run",parseInt(response.noRun)],
			              ["Blocked",parseInt(response.blocked)],
			              ["Deferred",parseInt(response.deferred)]
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
			              ["Passed",parseInt(response.passed)],
			              ["Failed",parseInt(response.failed)],
			              ["No Run",parseInt(response.noRun)],
			              ["Blocked",parseInt(response.blocked)],
			              ["Deferred",parseInt(response.deferred)]
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