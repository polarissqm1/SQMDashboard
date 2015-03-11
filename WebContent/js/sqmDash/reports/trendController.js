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
						$scope.maxDate = new Date();
						
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
						if ($("#todate").val()) {
							if ($scope.range) {
								if ($scope.range == 'weekly') {
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
								} else if ($scope.range == 'monthly') {
									var date = new Date($("#todate").val() ), y = date
											.getFullYear(), m = date.getMonth();
									var firstDay = new Date(y, m, 1);
									var lastDay = new Date(y, m + 1, 0);
									$scope.selectedStart=firstDay.toDateString().slice(8,10)+'/'+firstDay.toDateString().slice(4,7)+'/'+firstDay.toDateString().slice(13,15);
									$scope.selectedEnd=lastDay.toDateString().slice(8,10)+'/'+lastDay.toDateString().slice(4,7)+'/'+lastDay.toDateString().slice(13,15);
								}
							}
						}
						/*else{
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
						}*/
						$scope.plotCharts();
					};

					$scope.plotCharts = function() {
				
						$scope.trendReports = $http.get("dash/trendreports/getTrendingInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response) {

											//alert(JSON.stringify(response));
					        var rdate=[];
					        var actual=[];
					        var passed=[];
					        var failed=[];
					        var open=[];
					        var closed=[];
					        var defectDensity=[];
					        var defectSeverityIndex=[];
					        var badFix=[];
					        var defectAccept=[];
					        var urgent=[];
					        var high=[];
					        var medium=[];
					        var low=[];
					        for(var i=0;i<response.entity.length;i++){
					        	rdate.push(response.entity[i].rdate);
					        	actual.push(parseInt(response.entity[i].actual));
					        	passed.push(parseInt(response.entity[i].pass));
					        	failed.push(parseInt(response.entity[i].failed));
					        	open.push(parseInt(response.entity[i].open));
					        	closed.push(parseInt(response.entity[i].closed));
					        	defectDensity.push(Math.round(parseFloat(response.entity[i].defectDensity)*10)/10);
					        	defectSeverityIndex.push(Math.round(parseFloat(response.entity[i].defectSeverityIndex)*10)/10);
					        	badFix.push(Math.round(parseFloat(response.entity[i].badFix)*10)/10);
					        	defectAccept.push(Math.round(parseFloat(response.entity[i].defectAcceptance)*10)/10);
					        	urgent.push(parseInt(response.entity[i].urgent));
					        	high.push(parseInt(response.entity[i].high));
					        	medium.push(parseInt(response.entity[i].medium));
					        	low.push(parseInt(response.entity[i].low));
					        }
					        /*alert("+++++++++++"+rdate);
					        alert("+++++++++++"+actual);
					        alert("+++++++++++"+passed);
					        alert("+++++++++++"+failed);
					        alert(defectDensity);*/
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
											
												   
												
											$scope.plotDefectsOpenClosed(rdate,open,closed);
											$scope.plotDefectDensity(rdate,defectDensity);
											$scope.plotBadFix(rdate,badFix);
											$scope.plotDefectAccept(rdate,defectAccept);
											$scope.plotDefectSeverityIndex(rdate,defectSeverityIndex);
											$scope.plotTestCaseStatChart(rdate,passed,failed);
											$scope.plotDefectSeverityBreakUp(response);
											$scope.plotChart(actual,rdate);
											$scope.plotDefectRootBreakUp();
											$scope.plotDefectTypeBreakUp();
											$scope.plotDefectAgeing();
												
										});
						$scope.trendReports = $http.get("dash/trendreports/getReleaseInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response) {
							//alert(JSON.stringify(response));
						});
					};
					//******************** Highcharts *************************//

					$scope.plotChart = function(actual,rdate) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7','#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#testcaseexec')
								.highcharts(
										{
											title : {
												text : 'Test Case Execution Rate',
													style: {
											            fontSize: 'medium',
											            color : '#428bca'
											        },
											},
											credits : {
												enabled : false
											},

											xAxis : {
												categories :rdate
											},

											yAxis : {
												title : {
													text : 'Number of Testcases'
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
												},
											series : [
													{
														name : 'Planned',
														data : [parseInt(35262),parseInt(35262),parseInt(35262),parseInt(35262)],
														stack : 'male'
													},
													{
														name : 'Actual',
														data : actual,
														stack : 'female'
													} ]
										});
					};

					//***************** Test Case Status ******************//
					$scope.plotTestCaseStatChart = function(rdate,passed,failed) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#testcasestat')
								.highcharts(
										{
											title : {
												text : 'Test Case Status',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
											},
											subtitle: {
											    text: '(Pass vs Fail)',
											    style: {
										            fontSize: 'small',
										            color : '#428bca'
										        }
											},
											
											credits : {
												enabled : true
											},

											xAxis : {
												categories : rdate
											},

											yAxis : {
												title : {
													text : 'Number of Testcases'
												}
											},

											tooltip : {
												
											},

											plotOptions : {
												line : {
													dataLabels : {
														enabled : true
													},

												}
											},

											series : [
													{
														name : 'Passed',
														data : passed,
														stack : 'male'
													},
													{
														name : 'Failed',
														data : failed,
														stack : 'female'
													} ]
										});
					};

					//************************ Defects Open  ***************************//

					$scope.plotDefectsOpenClosed = function(rdate,open,closed) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectsopen')
								.highcharts(
										{
											title : {
												text : 'Defects',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
											},
											subtitle : {
												text : '(Open vs Closed)',
													style: {
											            fontSize: 'small',
											            color : '#428bca'
											        }
											},
											credits : {
												enabled : false
											},

											xAxis : {
												categories: rdate
											},

											yAxis : {
												allowDecimals : false,
												min : 0,
												title : {
													text : 'Number of Testcases'
												}
											},

											tooltip : {
											},
											plotOptions : {
												line : {
													dataLabels : {
														enabled : true
													},

												}
											},

											series : [
													{
														name : 'Opened',
														data : open,
														stack : 'male'
													},
													{
														name : 'Closed',
														data : closed,
														stack : 'female'
													} ]
										});
					};

					//************************ Defects Open  ***************************//

					//************************ Defect Density  ***************************//
					$scope.plotDefectDensity = function(rdate,defectDensity) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectdensity').highcharts(
								{
									title : {
										text : 'Defect Density',
										style: {
								            fontSize: 'medium',
								            color : '#428bca'
								        },
										x : -20
									//center
									},
									credits : {
										enabled : false
									},

									xAxis : {
										categories : rdate
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
										
									},

									series : [ {
										name : 'Defect Density',
										data : defectDensity
									} ]
								});

					};
					//************************ Defects Density Index  ***************************//
					$scope.plotDefectSeverityIndex = function(rdate,defectSeverityIndex) {

						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectdensityindex').highcharts(
								{
									title : {
										text : 'Defect Severity Index',
										style: {
								            fontSize: 'medium',
								            color : '#428bca'
								        },
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
										categories :rdate
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
										
									},
									
									series : [ {
										name : 'DSI',
										data : defectSeverityIndex
									} ]
								});

					};
					//************************ Bad Fix  ***************************//
					$scope.plotBadFix = function(rdate,badFix) {

						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#badfix').highcharts(
								{
									
									title : {
										text : 'Bad Fix',
										style: {
								            fontSize: 'medium',
								            color : '#428bca'
								        },
										//x : -20
									//center
									},
									subtitle : {
										text : '(Open vs Closed)',
											style: {
									            fontSize: 'small',
									            color : '#428bca'
									        }
									},
									credits : {
										enabled : false
									},
									/*subtitle : {
										text : '',
										x : -20
									},*/
									xAxis : {
										categories :rdate
									},
									yAxis : {
										title : {
											text : 'Re-opened Defects (%)'
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
										data : badFix
									} ]
								});

					};
					//************************ Defect Acceptance Rate  ***************************//
					$scope.plotDefectAccept = function(rdate,defectAccept) {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectaccept').highcharts(
								{
									title : {
										text : 'Defect Acceptance Rate',
										style: {
								            fontSize: 'medium',
								            color : '#428bca'
								        },
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
										categories : rdate
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
										
									},

									series : [ {
										name : "Defects Accepted",
										data : defectAccept
									} ]
								});

					};
					//************************ Defect Ageing  ***************************//
					$scope.plotDefectAgeing = function() {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
									'#fb8072', '#80b1d3', '#fdb462', 'b3de69' ]
						});

						$('#defectageing')
								.highcharts(
										{
											chart : {
												type : 'column'
											},
											title : {
												text : 'Defect Ageing',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
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
							colors : [ '#8dd3c7', '#bebada',
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
												text : 'Downtime Breakup',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
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
							colors : [ '#8dd3c7', '#bebada',
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
											colors : [ '#8dd3c7',
													'#bebada', '#fb8072',
													'#80b1d3', '#fdb462',
													'b3de69' ],
											title : {
												text : 'Defect Severity Breakup',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
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
																parseInt(response.entity[0].urgent),parseInt(response.entity[1].urgent),parseInt(response.entity[2].urgent),parseInt(response.entity[3].urgent) ],
														[
																"high",
																parseInt(response.entity[0].high),parseInt(response.entity[1].high),parseInt(response.entity[2].high),parseInt(response.entity[3].high)],
														[
																"medium",
																parseInt(response.entity[0].medium),parseInt(response.entity[1].medium),parseInt(response.entity[2].medium),parseInt(response.entity[3].medium)],
														[ "low", 22 ] ]
											} ]
										});

					};
					//************************ Defect Type Breakup  ***************************//
					$scope.plotDefectTypeBreakUp = function() {
						Highcharts.setOptions({
							colors : [ '#8dd3c7', '#bebada',
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
												text : 'Defect Type Breakup',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
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
							colors : [ '#8dd3c7', '#bebada',
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
												text : 'Defect Root Cause Breakup',
												style: {
										            fontSize: 'medium',
										            color : '#428bca'
										        }
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
												data : [ [ 'Automated Test Script', 45.0 ],
														[ 'Coding', 26.8 ],
														[ 'Requirements', 8.5 ],
														[ 'Incorrect Understanding', 6.2 ],
														

												]
											} ]
										});

					};

				});