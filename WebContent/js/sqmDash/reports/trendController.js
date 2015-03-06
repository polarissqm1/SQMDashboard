dashboardApp
		.controller(
				'trendController',
				function($scope,$http,$rootScope, $filter, $log) {
					$scope.plotCharts = null;
					$scope.selectedStart=null;
					$scope.selectedEnd=null;
					//** DatePicker **//
					$scope.today = function() {
						$scope.dt = new Date();
					};
					//$scope.today();

					$scope.clear = function() {
						$scope.dt = null;
					};

					// Disable weekend selection
					$scope.disabled = function(date, mode) {
						return (mode === 'day' && (date.getDay() === 0 || date
								.getDay() === 6));
					};

					$scope.toggleMin = function() {
						/*$scope.minDate = $scope.minDate ? null : new Date();*/
						$scope.minDate = "16/Feb/15";
						$scope.maxDate = "05/Mar/15";
						
					};
					
					$scope.toggleMin();

					$scope.openFromDate = function($event, openedFromDate) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.openedFromDate = true;
					};

					$scope.openToDate = function($event, openedToDate) {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.openedToDate = true;
					};

					$scope.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.formats = [ 'dd/MMM/yy' ];
					$scope.format = $scope.formats[0];

					//** Date Picker END**//

					$scope.generateCharts = function() {
						if ($("#todate").val() == '') {
							if ($scope.range) {
								if ($scope.range == 'weekly') {
									var current = new Date(); // get current date    
									var weekstart = current.getDate()
											- current.getDay() + 1;
									var weekend = weekstart + 4; // end day is the first day + 6 
									var monday = new Date(current
											.setDate(weekstart));
									var sunday = new Date(current
											.setDate(weekend));
									$scope.selectedStart=monday.toDateString().slice(8,10)+'/'+monday.toDateString().slice(4,7)+'/'+monday.toDateString().slice(13,15);
									$scope.selectedEnd=sunday.toDateString().slice(8,10)+'/'+sunday.toDateString().slice(4,7)+'/'+sunday.toDateString().slice(13,15);
								} else if ($scope.range == 'monthly') {
									var date = new Date(), y = date
											.getFullYear(), m = date.getMonth();
									var firstDay = new Date(y, m, 1);
									var lastDay = new Date(y, m + 1, 0);
									$scope.selectedStart=firstDay.toDateString().slice(8,10)+'/'+firstDay.toDateString().slice(4,7)+'/'+firstDay.toDateString().slice(13,15);
									$scope.selectedEnd=lastDay.toDateString().slice(8,10)+'/'+lastDay.toDateString().slice(4,7)+'/'+lastDay.toDateString().slice(13,15);
								}
							}
						}
						else{
							var current = new Date($("#todate").val() ); // get current date    
							var weekstart = current.getDate()
									- current.getDay() + 1;
							var weekend = weekstart + 4; // end day is the first day + 6 
							var monday = new Date(current
									.setDate(weekstart));
							var sunday = new Date(current
									.setDate(weekend));
							$scope.selectedStart=monday.toDateString().slice(8,10)+'/'+monday.toDateString().slice(4,7)+'/'+monday.toDateString().slice(13,15);
							$scope.selectedEnd=sunday.toDateString().slice(8,10)+'/'+sunday.toDateString().slice(4,7)+'/'+sunday.toDateString().slice(13,15);
						}
						$scope.plotCharts();
					};

					$scope.plotCharts = function() {
				
						$scope.trendReports = $http.get("dash/trendreports/getTrendingInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response) {

											//alert(JSON.stringify(response));
					
											/*alert(JSON.stringify(response));
											
											alert("selected Project Name"+$rootScope.selectedProjectName);
											alert("selected Project file"+$rootScope.selectedProjectName);*/
											/* 
											$scope.plotTestCaseStatChart(response.entity.manualVO,response.entity.automationVO);
											$scope.plotDefectsOpenClosed(response.entity.statusAndSeverityVO[0].total, response.entity.statusAndSeverityVO[2].total);
											$scope.plotBadFix(response.entity.statusAndSeverityVO[0].total,response.entity.statusAndSeverityVO[5].total);
											$scope.plotDefectDensity(response.entity.statusAndSeverityVO[5].total,response.entity.testCaseExecutionStatusVO[6].count);
											scope.plotDefectAccept(response.entity.statusAndSeverityVO[3].total,response.entity.statusAndSeverityVO[5].total);
											$scope.plotDefectSeverityIndex();*/
											$scope.plotDefectsOpenClosed(response.entity);
											$scope.plotDefectDensity(response.entity);
											$scope.plotBadFix(response.entity);
											$scope.plotDefectAccept(response.entity);
											$scope.plotDefectSeverityIndex(response.entity);
											$scope.plotTestCaseStatChart(response.entity);
											$scope.plotDefectSeverityBreakUp(response.entity);
											$scope.plotChart(response.entity);
											$scope.plotDefectRootBreakUp();
											$scope.plotDefectTypeBreakUp();
											$scope.plotDefectAgeing();
										});
						
					};
					//******************** Highcharts *************************//

					$scope.plotChart = function(response) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#testcaseexec')
								.highcharts(
										{

											chart : {
												type : 'column',
												options3d : {
													enabled : true,
													alpha : 15,
													beta : 15,
													viewDistance : 25,
													depth : 40,

												},
												marginTop : 80,
												marginRight : 40
											},

											title : {
												text : 'Test Case Execution Rate'
											},
											credits : {
												enabled : false
											},

											xAxis : {
												categories : [  $scope.selectedStart+"-"+$scope.selectedEnd ]
											},

											yAxis : {
												allowDecimals : false,
												min : 0,
												title : {
													text : 'Number of Testcases'
												}
											},

											tooltip : {
												headerFormat : '<b>{point.key}</b><br>',
												pointFormat : '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
											},

											plotOptions : {
												column : {
													stacking : 'normal',
													depth : 25,
													colors : [ '#74C7FF' ],
													dataLabels : {
														enabled : true
													}
												}
											},

											series : [
													{
														name : 'Planned',
														data : [parseInt(35262) ],
														stack : 'male'
													},
													{
														name : 'Actual',
														data : [parseInt(response.actual) ],
														stack : 'female'
													} ]
										});
					};

					//***************** Test Case Status ******************//
					$scope.plotTestCaseStatChart = function(response) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#testcasestat')
								.highcharts(
										{

											chart : {
												type : 'column',
												options3d : {
													enabled : true,
													alpha : 15,
													beta : 15,
													viewDistance : 25,
													depth : 40,

												},
												marginTop : 80,
												marginRight : 40
											},

											title : {
												text : 'Test Case Status(Pass vs Fail)'
											},
											credits : {
												enabled : false
											},

											xAxis : {
												categories : [  $scope.selectedStart+"-"+$scope.selectedEnd ]
											},

											yAxis : {
												allowDecimals : false,
												min : 0,
												title : {
													text : 'Number of Testcases'
												}
											},

											tooltip : {
												headerFormat : '<b>{point.key}</b><br>',
												pointFormat : '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
											},

											plotOptions : {
												column : {
													stacking : 'normal',
													depth : 25,
													colors : [ '#E52325',
															'#74C7FF' ],
													dataLabels : {
														enabled : true
													}
												}
											},

											series : [
													{
														name : 'passed',
														data : [ parseInt(response.pass) ],
														stack : 'male'
													},
													{
														name : 'Fail',
														data : [ parseInt(response.failed) ],
														stack : 'female'
													} ]
										});
					};

					//************************ Defects Open  ***************************//

					$scope.plotDefectsOpenClosed = function(response) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectsopen')
								.highcharts(
										{

											chart : {
												type : 'column',
												options3d : {
													enabled : true,
													alpha : 15,
													beta : 15,
													viewDistance : 25,
													depth : 40,

												},
												marginTop : 80,
												marginRight : 40
											},

											title : {
												text : 'Defects - Open Vs Closed'
											},
											credits : {
												enabled : false
											},

											xAxis : {
												categories: [ $scope.selectedStart+"-"+$scope.selectedEnd]
											},

											yAxis : {
												allowDecimals : false,
												min : 0,
												title : {
													text : 'Number of Testcases'
												}
											},

											tooltip : {
												headerFormat : '<b>{point.key}</b><br>',
												pointFormat : '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
											},

											plotOptions : {
												column : {
													stacking : 'normal',
													depth : 25,
													colors : [ '#E52325',
															'#74C7FF' ],
													dataLabels : {
														enabled : true
													}
												}
											},

											series : [
													{
														name : 'Open',
														data : [ parseInt(response.open) ],
														stack : 'male'
													},
													{
														name : 'Closed',
														data : [ parseInt(response.closed) ],
														stack : 'female'
													} ]
										});
					};

					//************************ Defects Open  ***************************//

					//************************ Defect Density  ***************************//
					$scope.plotDefectDensity = function(response) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectdensity').highcharts(
								{
									title : {
										text : 'Defect Density',
										x : -20
									//center
									},
									credits : {
										enabled : false
									},

									xAxis : {
										categories : [  $scope.selectedStart+"-"+$scope.selectedEnd]
									},
									yAxis : {
										title : {
											text : 'Defect Density'
										}
									},
									plotOptions : {
										line : {
											dataLabels : {
												enabled : true
											},

										}
									},
									tooltip : {
										valueSuffix : '%'
									},

									series : [ {
										name : 'Defect Density',
										data : [ parseFloat(response.defectDensity) ]
									} ]
								});

					};
					//************************ Defects Density Index  ***************************//
					$scope.plotDefectSeverityIndex = function(response) {

						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectdensityindex').highcharts(
								{
									title : {
										text : 'Defect Severity Index',
										x : -20
									//center
									},
									credits : {
										enabled : false
									},
									subtitle : {
										text : '',
										x : -20
									},
									xAxis : {
										categories : [  $scope.selectedStart+"-"+$scope.selectedEnd ]
									},
									yAxis : {
										title : {
											text : 'Defect Severity Index'
										}
									},
									plotOptions : {
										line : {
											dataLabels : {
												enabled : true
											},

										}
									},
									tooltip : {
										valueSuffix : '%'
									},
									
									series : [ {
										name : 'DSI',
										data : [ parseFloat(response.defectSeverityIndex) ]
									} ]
								});

					};
					//************************ Bad Fix  ***************************//
					$scope.plotBadFix = function(response) {

						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#badfix').highcharts(
								{
									title : {
										text : 'Bad Fix(Re-Opened Defects)',
										x : -20
									//center
									},
									credits : {
										enabled : false
									},
									subtitle : {
										text : '',
										x : -20
									},
									xAxis : {
										categories : [ $scope.selectedStart+"-"+$scope.selectedEnd  ]
									},
									yAxis : {
										title : {
											text : 'Re-opened Defects'
										}
									},
									plotOptions : {
										line : {
											dataLabels : {
												enabled : true
											},

										}
									},

									tooltip : {
										valueSuffix : '%'
									},

									series : [ {
										name : 'Re-opened',
										data : [ response.badFix ]
									} ]
								});

					};
					//************************ Defect Acceptance Rate  ***************************//
					$scope.plotDefectAccept = function(response) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectaccept').highcharts(
								{
									title : {
										text : 'Defect Acceptance Rate',
										x : -20
									//center
									},
									credits : {
										enabled : false
									},
									subtitle : {
										text : '',
										x : -20
									},
									xAxis : {
										categories : [  $scope.selectedStart+"-"+$scope.selectedEnd ]
									},
									yAxis : {
										title : {
											text : 'Defect Acceptance Rate'
										}
									},
									plotOptions : {
										line : {
											dataLabels : {
												enabled : true
											},

										}

									},
									tooltip : {
										valueSuffix : '%'
									},

									series : [ {
										name : "Defect Accept",
										data : [ parseFloat(response.defectAcceptance) ]
									} ]
								});

					};
					//************************ Defect Ageing  ***************************//
					$scope.plotDefectAgeing = function() {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectageing')
								.highcharts(
										{
											chart : {
												type : 'column'
											},
											title : {
												text : 'Defect Ageing'
											},
											credits : {
												enabled : false
											},
											subtitle : {
												text : ''
											},
											xAxis : {
												categories : [ '1D-4D',
														'5D-8D', '9D-12D',
														'>12D', ]
											},
											yAxis : {
												min : 0,
												title : {
													text : 'Defects'
												}
											},
											tooltip : {
												headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
												
												footerFormat : '</table>',
												shared : true,
												useHTML : true
											},
											plotOptions : {
												column : {
													pointPadding : 0.2,
													borderWidth : 0
												}
											},
											series : [ {
												name : 'Urgent',
												data : [2 ],
												stack : 'male'
											},
											{
												name : 'medium',
												data : [1],
												stack : 'female'
											}
											]
										});
					};
					//************************ Downtime Breakup  ***************************//
					$scope.plotDowntimeBreakUp = function() {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#downtimebreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : 1,//null,
												plotShadow : false,
												margin : [ 0, 0, 0, 0 ],

											},
											title : {
												text : 'Downtime Breakup'
											},
											plotOptions : {
												pie : {

													allowPointSelect : true,
													showInLegend : true,
													cursor : 'pointer',
													dataLabels : {
														enabled : true,
														format : '{point.percentage:.1f} %',
														style : {
															color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																	|| 'black'
														}
													}
												}
											},
											series : [ {
												type : 'pie',
												/*name: 'Browser share',*/
												data : [ [ 'Firefox', 45.0 ],
														[ 'IE', 26.8 ], {
															name : 'Chrome',
															y : 12.8,
															sliced : true,
															selected : true
														}, [ 'Safari', 8.5 ],
														[ 'Opera', 6.2 ],
														[ 'Others', 0.7 ]

												]
											} ]
										});

					};
					//************************ Defects Severity Breakup  ***************************//
					$scope.plotDefectSeverityBreakUp = function(response) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectseveritybreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : 1,// null,
												plotShadow : false,
												margin : [ 0, 0, 0, 0 ]
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
											colors : [ '#8dd3c7', '#ffffb3',
													'#bebada', '#fb8072',
													'#80b1d3', '#fdb462',
													'b3de69' ],
											title : {
												text : 'Defect Severity Breakup'
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
														enabled : true,
														format : '{point.percentage:.1f} %'
													},
													showInLegend : true
												}
											},
											series : [ {
												type : 'pie',
												name : 'Execution',
												data : [
														[
																"urgent",
																parseInt(response.urgent) ],
														[
																"high",
																parseInt(response.high) ],
														[
																"medium",
																parseInt(response.medium) ],
														[ "low", 22 ] ]
											} ]
										});

					};
					//************************ Defect Type Breakup  ***************************//
					$scope.plotDefectTypeBreakUp = function() {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defecttypebreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : 1,//null,
												plotShadow : false,
												margin : [ 0, 0, 0, 0 ]

											},
											title : {
												text : 'Defect Type Breakup'
											},
											plotOptions : {
												pie : {

													allowPointSelect : true,
													showInLegend : true,
													cursor : 'pointer',
													dataLabels : {
														enabled : true,
														format : '{point.percentage:.1f} %',
														style : {
															color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																	|| 'black'
														}
													}
												}
											},
											series : [ {
												type : 'pie',
												
												data : [ [ 'Data Conversion', 45.0 ],
														[ 'Deferred Item', 26.8 ],
												        [ 'Enhancement', 8.5 ],
														[ 'Functional', 6.2 ]

												]
											} ]
										});

					};

					//************************ Defect Rootcause Breakup ***************************//
					$scope.plotDefectRootBreakUp = function() {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#ffffb3', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectRootbreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : 1,//null,
												plotShadow : false,
												margin : [ 0, 0, 0, 0 ],

											},
											title : {
												text : 'Defect Root Cause Breakup'
											},
											plotOptions : {
												pie : {

													allowPointSelect : true,
													showInLegend : true,
													cursor : 'pointer',
													dataLabels : {
														enabled : true,
														format : '{point.percentage:.1f} %',
														style : {
															color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																	|| 'black'
														}
													}
												}
											},
											series : [ {
												type : 'pie',
												/*name: 'Browser share',*/
												data : [ [ 'Automated Test Script', 45.0 ],
														[ 'Coding', 26.8 ],
														[ 'Requirements', 8.5 ],
														[ 'Incorrect Understanding', 6.2 ],
														

												]
											} ]
										});

					};

				});