dashboardApp
		.controller(
				'trendController',
				function($scope,$http,$rootScope, $filter, $log, $timeout) {
					$scope.plotCharts = null;
					$scope.selectedStart=null;
					$scope.selectedEnd=null;
					
					$scope.onInit = function() {
						//alert("in init function");
						
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
						$rootScope.fromdate = $("#fromdate").val();
						$rootScope.todate = $("#todate").val();
						var date = $("#todate").val();
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
								//	alert($scope.selectedStart);
								//	alert($scope.selectedEnd);
								} else if ($scope.range == 'monthly') {
									//console.log(date);
									d = date.slice(0,2);
									m = date.slice(3,6);
									y = date.slice(9,11);
									
									$scope.selectedStart = 1+'/'+m+'/'+y;
									$scope.selectedEnd = 31+'/'+m+'/'+y;
									
									/*var date = new Date($("#todate").val() ), y = date
											.getFullYear(), m = date.getMonth();
									var firstDay = new Date(y, m, 1);
									var lastDay = new Date(y, m + 1, 0);
									
									$scope.selectedStart=firstDay.toDateString().slice(8,10)+'/'+firstDay.toDateString().slice(4,7)+'/'+firstDay.toDateString().slice(13,15);
									$scope.selectedEnd=lastDay.toDateString().slice(8,10)+'/'+lastDay.toDateString().slice(4,7)+'/'+lastDay.toDateString().slice(13,15);
									*/
									/*alert($scope.selectedStart);
									alert($scope.selectedEnd);*/
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
				
						$scope.trendReports = $http.get("dash/trendreports/getTrendingInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response1) {
							$scope.trendReportsRelease = $http.get("dash/trendreports/getReleaseInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response2) {
											//alert(JSON.stringify(response));
								
								//alert("response1 is ------------------"+JSON.stringify(response1));
								//alert("response2 is ------------------"+JSON.stringify(response2));
								
								$scope.plotDefectRootBreakUp(response2);
								$scope.plotDefectTypeBreakUp(response2);
								$scope.plotDefectAgeing(response2);
							if(!response1.entity[0]){
								/*$scope.showDiv('row1Charts');
								$scope.showDiv('pagers');*/
							/*$scope.showDiv('row1Charts');*/
								
								/*$scope.enableTrendChart=false;*/
								$("#row1Charts").hide();
								$("#pagers").hide();
								
						
							
							}
							else {
								/*$scope.hideDiv('row1Charts');
								$scope.hideDiv('pagers');*/
								//$scope.enableTrendChart=true;
								$("#row1Charts").show();
								$("#pagers").show();
								
								
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
						        for(var i=0;i<response1.entity.length;i++){
						        	rdate.push(response1.entity[i].rdate);
						        	actual.push(parseInt(response1.entity[i].actual));
						        	passed.push(parseInt(response1.entity[i].pass));
						        	failed.push(parseInt(response1.entity[i].failed));
						        	open.push(parseInt(response1.entity[i].open));
						        	closed.push(parseInt(response1.entity[i].closed));
						        	defectDensity.push(Math.round(parseFloat(response1.entity[i].defectDensity)*100)/100);
						        	defectSeverityIndex.push(Math.round(parseFloat(response1.entity[i].defectSeverityIndex)*10)/10);
						        	badFix.push(Math.round(parseFloat(response1.entity[i].badFix)*10)/10);
						        	defectAccept.push(Math.round(parseFloat(response1.entity[i].defectAcceptance)*10)/10);
						        	total_urgent+=parseInt(response1.entity[i].urgent);
						        	total_high+=parseInt(response1.entity[i].high);
						        	total_medium+=parseInt(response1.entity[i].medium);
						        	total_low+=parseInt(response1.entity[i].low);
						        	
						        	
									
						        }
						        	
						        	
									
						        $scope.plotDefectsOpenClosed(rdate,open,closed);
								$scope.plotDefectDensity(rdate,defectDensity);
								$scope.plotBadFix(rdate,badFix);
								$scope.plotDefectAccept(rdate,defectAccept);
								$scope.plotDefectSeverityIndex(rdate,defectSeverityIndex);
								$scope.plotTestCaseStatChart(rdate,passed,failed);
								$scope.plotDefectSeverityBreakUp(total_urgent,total_high,total_medium,total_low);
								var plan=parseInt(response2.entity[0].planned);
								$scope.plotChart(actual,rdate,plan);
							}
					        
					        
					        
					       
											
												   
												
							});		
												
										});
						/*$scope.trendReports = $http.get("dash/trendreports/getReleaseInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response) {
							//alert(JSON.stringify(response));
							$scope.plotDefectRootBreakUp(response);
							$scope.plotDefectTypeBreakUp(response);
							$scope.plotDefectAgeing(response);
						});*/
					};
					//******************** Highcharts *************************//

					$scope.plotChart = function(actual,rdate,plan) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
														data : [plan],
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
							colors : [ '#8085e9', '#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
														//name : response.entity.status,
														data : passed
														
													},
													{
														name : 'Failed',
														data : failed
													
													} ]
										});
					};

					//************************ Defects Open  ***************************//

					$scope.plotDefectsOpenClosed = function(rdate,open,closed) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
														data : open
													},
													{
														name : 'Closed',
														data : closed
													} ]
										});
					};

					//************************ Defects Open  ***************************//

					//************************ Defect Density  ***************************//
					$scope.plotDefectDensity = function(rdate,defectDensity) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
						});

						$('#badfix').highcharts(
								{
									
									title : {
										text : 'Bad Fixes',
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
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
					$scope.plotDefectAgeing = function(response) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
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
										            categories: ['<5D','6D-10D','>10D']
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
										            verticalAlign: 'top',
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
										            data : [response.entity[0].oneDayToFour.Urgent,response.entity[0].fourToEight.Urgent,response.entity[0].greaterEight.Urgent ]
										        }, {
										            name : 'High',
										            data: [response.entity[0].oneDayToFour.High,response.entity[0].fourToEight.High,response.entity[0].greaterEight.High]
										        },{
										            name : 'Medium',
										            data: [response.entity[0].oneDayToFour.Medium,response.entity[0].fourToEight.Medium,response.entity[0].greaterEight.Medium]
										        },{
										            name : 'Low',
										            data: [response.entity[0].oneDayToFour.Low,response.entity[0].fourToEight.Low,response.entity[0].greaterEight.Low]
										        }
										        ]
										});
					};
					
					//************************ Defects Severity Breakup  ***************************//
					$scope.plotDefectSeverityBreakUp = function(total_urgent,total_high,total_medium,total_low) {
						Highcharts.setOptions({
							colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
						});

						$('#defectseveritybreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : null,
												plotShadow : false
											},
											credits : {
												enabled : false,
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
											
											title : {
												text : 'Defect Severity Breakup',
												style: {
										            fontSize: 'medium',
										            fontWeight: 'bold',
										            color : '#428bca'
										        }
											},
											tooltip : {
												pointFormat : '<b>{point.percentage:.1f}%</b>'
											},
											plotOptions : {
												pie : {
													allowPointSelect : true,
													cursor : 'pointer',
													dataLabels : {
														enabled : true,
														//format : '{point.percentage:.1f} %'
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
												data : [
														[
																"Urgent",
																parseInt(total_urgent) ],
														[
																"High",
																parseInt(total_high)],
														[
																"Medium",
																parseInt(total_medium)],
														[ "Low", parseInt(total_low) ] ]
											} ]
										});

					};
					//************************ Defect Type Breakup  ***************************//
					$scope.plotDefectTypeBreakUp = function(response) {
						Highcharts.setOptions({
							colors : [ '#8B8989','#BC8F8F','#FF7F50','#CD6839','#CDC5BF','#CDBA96','#D5B77A','#E8C782','#CDBE70','#AEBB51','#BEE554','#E8F1D4','#DFFFA5','#E55451']
						});

						$('#defecttypebreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : null,
												plotShadow : false
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
														formatter: function() {
									                        return Math.round(this.percentage*100)/100 + ' %';
									                    },
									                    distance: -30
													}
												}
											},
											series : [ {
												type : 'pie',
												
												data : [[ 'Test Execution', response.entity[0].defectType.TestExecution ],
														[ 'Performance', response.entity[0].defectType.Performance ],
														[ 'Environment', response.entity[0].defectType.Environment ],
														[ 'Functional', response.entity[0].defectType.Functional ],
														[ 'Enhancement', response.entity[0].defectType.Enhancement ],
														[ 'Others', response.entity[0].defectType.others ]
														
												]
											} ]
										});

					};

					//************************ Defect Rootcause Breakup ***************************//
					$scope.plotDefectRootBreakUp = function(response) {
						Highcharts.setOptions({
							colors : [ '#8B8989','#BC8F8F','#FF7F50','#CD6839','#CDC5BF','#CDBA96','#D5B77A','#E8C782','#CDBE70','#AEBB51','#BEE554','#E8F1D4','#DFFFA5','#E55451' ]
						});

						$('#defectRootbreak')
								.highcharts(
										{
											chart : {
												plotBackgroundColor : null,
												plotBorderWidth : null,
												plotShadow : false

											},
											title : {
												text : 'Defect Rootcause Breakup',
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
														formatter: function() {
									                        return Math.round(this.percentage*100)/100 + ' %';
									                    },
									                    distance: -30
													}
												}
											},
											series : [ {
												type : 'pie',
												data : [ [ "Implementation", response.entity[0].defectRootCause.implementation ],
												         [ "Environment", response.entity[0].defectRootCause.Environment ],
												         [ "Test Script", response.entity[0].defectRootCause.TestScript ],
												         [ "User Error", response.entity[0].defectRootCause.UserError_NotaDefect ],
												         [ "Requirements", response.entity[0].defectRootCause.Requirements ],
												         [ "Data", response.entity[0].defectRootCause.Data ]

												]
											} ]
										});

					};

				});