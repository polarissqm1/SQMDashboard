"use strict";
/**
 * Reports Monitoring Controller
 */

	dashboardApp.controller('dashboardController', function($scope, $http, $filter, $log,$rootScope) {

		$log.log('dashboardController controller is initialized');

		/**
		 * Dashboard Controller
		 */

		//Flot Pie Chart
		$(document).ready(function() {

			$("#applicationCombo").on("change", "#application", function(event) {
				
				validateVersions();
				
				
				
			});
			$("#releaseCombo").on("change", "#release", function(event) {
				//alert("Hi");
				plotCharts();
				
			});
			




		});

		function validateVersions(){

			var appValue=$("#application").val();
			
			var elem1 = document.getElementById('release');
			if(appValue=="PMP"){
				
		    	elem1.options[1]=new Option("pmpRelease1");
		    	elem1.options[2]=new Option("pmpRelease2");
				
			}else if(appValue=="iwatch"){
				elem1.options[1]=new Option("iwatchRelease1");
		    	elem1.options[2]=new Option("iwatchRelease2");
				
			}else{
				elem1.options[1]=new Option("cfprRelease1");
		    	elem1.options[2]=new Option("cfprRelease2");
			}
			
		}

		function plotCharts(){
			
			
			var selectedProjectName = $("#application").val();
			var selectedReleaseName = $("#release").val();
			$rootScope.selectedProjectName = $("#application").val();
			$rootScope.selectedReleaseName = $("#release").val();
			//alert(selectedProjectName+" "+selectedReleaseName);
			
			 $http.get("dash/dashboard/getLandingInfo?projectName=" + selectedProjectName+"&releaseName="+selectedReleaseName)
			 .success(function(response){
				 plotRagChart();
				 plotManualChart(response.entity.manualVO);
				 plotAutomatedChart(response.entity.automationVO);
				 plotDefectStatusChart(response.entity.severityVO);
				 plotDistributionChart(response.entity.effortsVO);
				 
			 });
			
			
		}

		function plotRagChart()
		{

			var arr=[["Red",20],
			         ["Amber",30],
			         ["Green",25]
			];
			$('#flot-pie-chart')
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : 1,// null,
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
							text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",
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
						colors : [ '#4B610B', '#8A0829', '#084B8A', '#B45F04',
								'#2E9AFE', '#4B088A' ],
						title : {
							text : 'RAG'
							/*style: {
						         color: '#E0E0E3',
						         textTransform: 'uppercase',
						         fontSize: '20px'
						      }*/
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
				                    format: '{point.percentage:.1f} %'
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
			              ["passed",parseInt(response.passed)],
			              ["failed",parseInt(response.failed)],
			              ["noRun",parseInt(response.noRun)],
			              ["blocked",parseInt(response.blocked)],
			              ["deferred",parseInt(response.deferred)]
			              ];
			
			$('#manual-pie-chart')
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : 1,// null,
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
							text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",
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
						colors : [ '#4B610B', '#8A0829', '#084B8A', '#B45F04',
								'#2E9AFE', '#4B088A' ],
						title : {
							text : 'Manual Execution status'
							/*style: {
						         color: '#E0E0E3',
						         textTransform: 'uppercase',
						         fontSize: '20px'
						      }*/
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
				                    format: '{point.percentage:.1f} %'
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
			              ["passed",parseInt(response.passed)],
			              ["failed",parseInt(response.failed)],
			              ["noRun",parseInt(response.noRun)],
			              ["blocked",parseInt(response.blocked)],
			              ["deferred",parseInt(response.deferred)]
			              ];
			$('#automation-pie-chart')
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : 1,// null,
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
							text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",
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
						colors : [ '#4B610B', '#8A0829', '#084B8A', '#B45F04',
								'#2E9AFE', '#4B088A' ],
						title : {
							text : 'Automated Execution status'
							/*style: {
						         color: '#E0E0E3',
						         textTransform: 'uppercase',
						         fontSize: '20px'
						      }*/
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
				                    format: '{point.percentage:.1f} %'
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
			              ["urgent",parseInt(response.urgent)],
			              ["high",parseInt(response.high)],
			              ["low",parseInt(response.low)],
			              ["medium",parseInt(response.medium)]
			             
			              ];
			$('#opendefect-pie-chart')
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : 1,// null,
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
							text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",
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
						colors : [ '#4B610B', '#8A0829', '#084B8A', '#B45F04',
								'#2E9AFE', '#4B088A' ],
						title : {
							text : 'Open Defect status'
							/*style: {
						         color: '#E0E0E3',
						         textTransform: 'uppercase',
						         fontSize: '20px'
						      }*/
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
				                    format: '{point.percentage:.1f} %'
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
			              ["sqm",parseInt(response.sqm)],
			              ["non_sqm",parseInt(response.non_sqm)]
			             
			              ];
			$('#overall-pie-chart')
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : 1,// null,
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
							text : "JPMorgan",
							href : "http://www.jpmorganchase.com/",
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
						colors : [ '#4B610B', '#8A0829', '#084B8A', '#B45F04',
								'#2E9AFE', '#4B088A' ],
						title : {
							text : 'OverAll Distribution'
							/*style: {
						         color: '#E0E0E3',
						         textTransform: 'uppercase',
						         fontSize: '20px'
						      }*/
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled: true,
				                    format: '{point.percentage:.1f} %'
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


		});