dashboardApp
		.controller(
				'trendController',
				function($scope,$http,$rootScope, $filter, $log, $timeout) {
					$scope.plotCharts = null;
					$scope.selectedStart=null;
					$scope.selectedEnd=null;
					
					$scope.onInit = function() {
						alert("in init function");
						
					};
					
					$scope.showDiv = function(element) {
						document.getElementById(element).style.display='block';
					};
					
					$scope.hideDiv = function(element) {
						document.getElementById(element).style.display='none';
					};
					
					
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
						//$scope.minDate = "16/Feb/15";
						$scope.minDate = new Date('2015/02/16');
						$scope.maxDate = new Date();
						
					};
					
					$scope.toggleMin();
					
					$scope.minDate = new Date('2015/03/12');
					$scope.maxDate = new Date();
					
					$scope.minStartDate = 0; //fixed date
					  $scope.maxStartDate = $scope.maxDate; //init value
					  $scope.minEndDate = $scope.minDate; //init value
					  $scope.maxEndDate = $scope.maxDate; //fixed date same as $scope.maxStartDate init value
					  
					  $scope.$watch('minDate', function(v){
						    $scope.minEndDate = v;
						  });
						  $scope.$watch('maxDate', function(v){
						    $scope.maxStartDate = v;
						  });
						  
						  $scope.openFromDate = function() {
							    
							    $timeout(function() {
							      
							      $scope.startOpened = true;
							    });
							  };

							  $scope.openToDate = function() {
							    $timeout(function() {
							      $scope.endOpened = true;
							    });
							  };

					/*$scope.openFromDate = function() {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.startOpened = true;
					};

					$scope.openToDate = function() {
						$event.preventDefault();
						$event.stopPropagation();

						$scope.endOpened = true;
					};*/

					$scope.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.formats = [ 'dd-MMM-yyyy' ];
					//$scope.formats = [ 'dd/MMM/yy' ];
					$scope.format = $scope.formats[0];

					//** Date Picker END**//

					$scope.generateCharts = function() {
						//$scope.todate = $("#todate").val();
						//alert("selected todate is "+$scope.todate);
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
									/*$scope.selectedStart=monday.toISOString();
									$scope.selectedEnd=sunday.toISOString();*/
									$scope.selectedStart=monday.toDateString().slice(8,10)+'/'+monday.toDateString().slice(4,7)+'/'+monday.toDateString().slice(13,15);
									$scope.selectedEnd=sunday.toDateString().slice(8,10)+'/'+sunday.toDateString().slice(4,7)+'/'+sunday.toDateString().slice(13,15);
								} else if ($scope.range == 'monthly') {
									var date = new Date($("#todate").val() ), y = date
											.getFullYear(), m = date.getMonth();
									var firstDay = new Date(y, m, 1);
									var lastDay = new Date(y, m + 1, 0);
									/*$scope.selectedStart=firstDay.toISOString();
									$scope.selectedEnd=lastDay.toISOString();*/
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
							if(response.entity.length > 0){
							$scope.showDiv('row1Charts');
							
							
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
					        var total_urgent=0;
					        var total_high=0;
					        var total_medium=0;
					        var total_low=0;
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
					        	total_urgent+=parseInt(response.entity[i].urgent);
					        	total_high+=parseInt(response.entity[i].high);
					        	total_medium+=parseInt(response.entity[i].medium);
					        	total_low+=parseInt(response.entity[i].low);
					        	
					        	$scope.plotDefectsOpenClosed(rdate,open,closed);
								$scope.plotDefectDensity(rdate,defectDensity);
								$scope.plotBadFix(rdate,badFix);
								$scope.plotDefectAccept(rdate,defectAccept);
								$scope.plotDefectSeverityIndex(rdate,defectSeverityIndex);
								$scope.plotTestCaseStatChart(rdate,passed,failed);
								$scope.plotDefectSeverityBreakUp(total_urgent,total_high,total_medium,total_low);
								$scope.plotChart(actual,rdate);
								$scope.plotDefectAgeing();
					        }
							
							}
							else {
								$scope.hideDiv('row1Charts');
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
											
												   
												
											
												
										});
						$scope.trendReports = $http.get("dash/trendreports/getReleaseInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response) {
							//alert(JSON.stringify(response));
							$scope.plotDefectRootBreakUp(response);
							$scope.plotDefectTypeBreakUp(response);
						});
					};
					//******************** Highcharts *************************//

					$scope.plotChart = function(actual,rdate) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#testcaseexec')
								.highcharts(
										{
											title : {
												text : 'Test Case Execution Rate',
													style: {
											            fontSize: 'medium',
											            fontWeight: 'bold',
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
													text : 'Number of Testcases',
													style: {
											            fontSize: 'small',
											            color : '#428bca'
											        }
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
														data : [parseInt(35262),parseInt(35262)],
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#testcasestat')
								.highcharts(
										{
											title : {
												text : 'Test Case Status',
												style: {
										            fontSize: 'medium',
										            fontWeight: 'bold',
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
													text : 'Number of Testcases',
													style: {
											            fontSize: 'small',
											            color : '#428bca'
											        }
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#defectsopen')
								.highcharts(
										{
											title : {
												text : 'Defects',
												style: {
										            fontSize: 'medium',
										            fontWeight: 'bold',
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
													text : 'Number of Testcases',
													style: {
											            fontSize: 'small',
											            color : '#428bca'
											        }
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#defectdensity').highcharts(
								{
									title : {
										text : 'Defect Density',
										style: {
								            fontSize: 'medium',
								            fontWeight: 'bold',
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
											text : 'Defect Density',
											style: {
									            fontSize: 'small',
									            color : '#428bca'
									        }
												
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#defectdensityindex').highcharts(
								{
									title : {
										text : 'Defect Severity Index',
										style: {
								            fontSize: 'medium',
								            fontWeight: 'bold',
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
											text : 'Defect Severity Index',
											style: {
									            fontSize: 'small',
									            color : '#428bca'
									        }
											
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
										name : 'Defect Severity Index',
										data : defectSeverityIndex
									} ]
								});

					};
					//************************ Bad Fix  ***************************//
					$scope.plotBadFix = function(rdate,badFix) {

						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#badfix').highcharts(
								{
									
									title : {
										text : 'Bad Fix',
										style: {
								            fontSize: 'medium',
								            fontWeight: 'bold',
								            color : '#428bca'
								        },
										//x : -20
									//center
									},
									subtitle : {
										text : '(Re-opened)',
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
											text : 'Re-opened Defects (%)',
											style: {
									            fontSize: 'small',
									            color : '#428bca'
									        }
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#defectaccept').highcharts(
								{
									title : {
										text : 'Defect Acceptance Rate',
										style: {
								            fontSize: 'medium',
								            fontWeight: 'bold',
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
											text : 'Defect Acceptance Rate',
											style: {
									            fontSize: 'small',
									            color : '#428bca'
									        }
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
						});

						$('#defectageing')
								.highcharts(
										{
											 chart: {
										            type: 'column'
										        },
										        title: {
										            text: 'Defect Ageing',
										            style: {
													fontSize: 'medium',
													fontWeight: 'bold',
													color : '#428bca'
										            }
										        },
										        xAxis: {
										            categories: ['1D-4D','5D-8D', '9D-12D','>12D']
										        },
										        yAxis: {
										            min: 0,
										            title: {
										                text: 'Defects',
										                style: {
														fontSize: 'small',
														color : '#428bca'
														}
										            },
										            stackLabels: {
										                enabled: true,
										                style: {
										                    fontWeight: 'bold',
										                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
										                }
										            }
										        },
										        legend: {
										            align: 'center',
										            x: -30,
										            verticalAlign: 'bottom',
										            y: 25,
										            floating: true,
										            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
										            borderColor: '#CCC',
										            borderWidth: 1,
										            shadow: false
										        },
										        tooltip: {
										            formatter: function () {
										                return '<b>' + this.x + '</b><br/>' +
										                    this.series.name + ': ' + this.y + '<br/>' +
										                    'Total: ' + this.point.stackTotal;
										            }
										        },
										        plotOptions: {
										            column: {
										                stacking: 'normal',
										                dataLabels: {
										                    enabled: true,
										                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
										                    style: {
										                        textShadow: '0 0 3px black'
										                    }
										                }
										            }
										        },
										        series: [{
										            name : 'Urgent',
										            data : [2 ]
										        }, {
										            name : 'medium',
										            data: [1]
										        }]
										});
					};
					//************************ Downtime Breakup  ***************************//
					$scope.plotDowntimeBreakUp = function() {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
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
										            fontWeight: 'bold',
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
					$scope.plotDefectSeverityBreakUp = function(total_urgent,total_high,total_medium,total_low) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
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
										            fontWeight: 'bold',
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
																parseInt(total_urgent) ],
														[
																"high",
																parseInt(total_high)],
														[
																"medium",
																parseInt(total_medium)],
														[ "low", parseInt(total_low) ] ]
											} ]
										});

					};
					//************************ Defect Type Breakup  ***************************//
					$scope.plotDefectTypeBreakUp = function(response) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
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
										            fontWeight: 'bold',
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
												
												data : [ [ 'Data Conversion', response.entity[0].defectType.DataConversion ],
															[ 'Performance', response.entity[0].defectType.Performance ]

												]
											} ]
										});

					};

					//************************ Defect Rootcause Breakup ***************************//
					$scope.plotDefectRootBreakUp = function(response) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072' ]
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
										            fontWeight: 'bold',
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
												data : [ [ "implementation", response.entity[0].defectRootCause.implementation ],
												         [ "incorrectUnderstanding", response.entity[0].defectRootCause.incorrectUnderstanding ],
												         ["coding",response.entity[0].defectRootCause.coding]
														

												]
											} ]
										});

					};

				});