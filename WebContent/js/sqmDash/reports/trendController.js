dashboardApp
		.controller(
				'trendController',
				function($scope,$http,$rootScope, $filter, $log, $timeout) {
					
					$scope.plotCharts = null;
					$scope.selectedStart=null;
					$scope.selectedEnd=null;
					$scope.renderChart = null;
					
					$scope.onTRload = function() {
						//alert("in load function");
						$('#pagers').hide();
					}
					$scope.onInit = function() {
						//alert("in init function");
						$scope.act = [];
						$scope.rda = [];
						$scope.pla = [];
						$scope.passed_z=[];
						$scope.failed_z=[];
						$scope.open_z=[];
						$scope.closed_z=[];
						$scope.defectDensity_z=[];
						$scope.defectSeverityIndex_z=[];
						$scope.badFix_z=[];
						$scope.defectAccept_z=[];
						$scope.total_urgent_z=0;
						$scope.total_high_z=0;
						$scope.total_medium_z=0;
						$scope.total_low_z=0;
						$scope.response_z=null;
					};
					
					
					$(function($){
							
						    $( "#dialog" ).dialog({
						        autoOpen: false,
						        width: 500,
						        height: 'auto',
						        modal: true,
						        fluid: true, //new option
						        resizable: false,
						        //width : 'auto',
						        show: {
						          effect: "fadein",
						          duration: 300
						        },
						        hide: {
						          effect: "fadeout",
						          duration: 100
						        }
						      });
						    
						    $( "#info" ).dialog({
						        autoOpen: false,
						        width: 250,
						        height: 'auto',
						        modal: false,
						        fluid: true, //new option
						        resizable: false,
						       
						       
						        //width : 'auto',
						        show: {
						          effect: "fadein",
						          duration: 300
						        },
						        hide: {
						          effect: "fadeout",
						          duration: 100
						        }
						      });
						    
					
						}); // JQuery onReady END
						

						// on window resize run function
						$(window).resize(function () {
						    fluidDialog();
						});

						// catch dialog if opened within a viewport smaller than the dialog width
						$(document).on("dialogopen", ".ui-dialog", function (event, ui) {
						    fluidDialog();
						});

						function fluidDialog() {
						    var $visible = $(".ui-dialog:visible");
						    // each open dialog
						    $visible.each(function () {
						        var $this = $(this);
						        var dialog = $this.find(".ui-dialog-content").data("ui-dialog");
						        // if fluid option == true
						        if (dialog.options.fluid) {
						            var wWidth = $(window).width();
						            // check window width against dialog width
						            if (wWidth < (parseInt(dialog.options.maxWidth) + 50))  {
						                // keep dialog from filling entire screen
						                $this.css("max-width", "90%");
						            } else {
						                // fix maxWidth bug
						                $this.css("max-width", dialog.options.maxWidth + "px");
						            }
						            //reposition dialog
						            dialog.option("position", dialog.options.position);
						        }
						    });

						}
						

						$scope.renderChart = function(actual,rdate,plan){
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							var chart = $('#char')
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
												chart : {
													//height : 450,
													/*width : 150*/
												},
												credits : {
													enabled : false
												},

												xAxis : {
													categories :rdate
												},
												legend : {
													enabled: true
												},
												yAxis : {
													min: 0,
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
						
							
						}  // Render Chart END
					
					$scope.showDiv = function(element) {
						document.getElementById(element).style.display='block';
					};
					
					$scope.hideDiv = function(element) {
						document.getElementById(element).style.display='none';
					};
					
					$(document).ready(function() {
					    $("input[name$='weekly']").click(function() {
					        var test = $(this).val();

					        $("div.desc").hide();
					        $("#weekly" + test).show();
					    });
					    
					    
					});
					
					//*************** DatePicker ******************//
					
					$scope.weeklyDatepicker = function() {
						
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
						$scope.minDate = new Date('2015/03/01');
						$scope.maxDate = new Date();
						
					};
					
					$scope.toggleMin();
					
					/*$scope.minDate = new Date('2015/03/27');
					var date = new Date();
					$scope.maxEndDate = date.setDate((new Date('2015/03/27')).getDate()+6);
					$scope.maxDate = $scope.maxEndDate;
					
					$scope.minStartDate = 0; //fixed date
					  $scope.maxStartDate = $scope.maxDate; //init value
					  $scope.minEndDate = $scope.minDate; //init value
					  $scope.maxEndDate = $scope.maxDate; //fixed date same as $scope.maxStartDate init value
					  
					  $scope.$watch('minDate', function(v){
						    //$scope.minEndDate = v;
						  var date = new Date();
						    $scope.maxEndDate = date.setDate((new Date('2015/03/27')).getDate()+6);
						  });
						  $scope.$watch('maxDate', function(v){
						    $scope.maxStartDate = v;
						  });*/
					
					$scope.minDate = new Date();
					//var date = new Date();
					$scope.maxEndDate = $scope.minDate;
					$scope.maxDate = $scope.maxEndDate;
					
					//$scope.selectedDate = $("#fromdate").val() ? $scope.newFromDate : $scope.minDate;
					$scope.resetWeeklyDates = function() {
						
					$scope.minStartDate = new Date('2015/03/01'); //fixed date
					  $scope.maxStartDate = new Date(); //init value
					  $scope.minEndDate = $scope.selectedDate; //init value
					  $scope.maxEndDate = $scope.maxDate; //fixed date same as $scope.maxStartDate init value
					}
					$scope.resetWeeklyDates();
					  
					  $scope.$watch('minDate', function(v){
						    //$scope.minEndDate = v;
						  $scope.newFromDate = $("#fromdate").val();
						  d = $scope.newFromDate.slice(0,2);
						  m = $scope.newFromDate.slice(3,6);
						  y = $scope.newFromDate.slice(7,11);
						  var aaa = Date.getMonthNumberFromName(m);
						  var monNum = aaa+1;
						  console.log("current month is "+monNum);
						  $scope.currDate = y+'/'+monNum+'/'+d;
							console.log($scope.newFromDate);
							console.log("today is "+new Date($scope.currDate));
						  
						  var future = new Date($scope.currDate).add(6).days();
							console.log("future date is "+future);
							$scope.maxEndDate = future;
						  //var date = new Date();
						 // $scope.maxEndDate = date.setDate(future.getDate());
						    //$scope.maxEndDate = date.setDate((new Date('2015/03/13')).getDate()+6);
						  });
					 // $scope.resetWeeklyDates();
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

					
					$scope.dateOptions = {
						formatYear : 'yy',
						startingDay : 1
					};

					$scope.formats = [ 'dd-MMM-yyyy' ];
					$scope.format = $scope.formats[0];
					
					};
					
					
					$scope.monthlyDatepicker = function() {
						
						
						$scope.today1 = function() {
							$scope.dt1 = new Date();
						};
						//$scope.today();

						$scope.clear = function() {
							$scope.dt1 = null;
						};

						// Disable weekend selection
						$scope.disabled = function(date, mode) {
							return (mode === 'day' && (date.getDay() === 0 || date
									.getDay() === 6));
						};

						$scope.toggleMin1 = function() {
							/*$scope.minDate = $scope.minDate ? null : new Date();*/
							//$scope.minDate = "16/Feb/15";
							$scope.minDate1 = new Date('2015/02/14');
							$scope.maxDate1 = new Date();
							
						};
						
						$scope.toggleMin1();
						
						$scope.minDate1 = new Date();
						var date1 = new Date();
						$scope.maxEndDate1 = new Date();
						$scope.maxDate1 = $scope.maxEndDate1;
						
						//$scope.selectedDate = $("#fromdate").val() ? $scope.newFromDate : $scope.minDate;
						
						$scope.resetMonthlyDates = function() {
						$scope.minStartDate1 = new Date('2015/02/14'); //fixed date
						  $scope.maxStartDate1 = $scope.lastDate1; //init value
						  $scope.minEndDate1 = $scope.selectedDate1; //init value
						  $scope.maxEndDate1 = $scope.maxDate1; //fixed date same as $scope.maxStartDate init value
						}
						$scope.resetMonthlyDates();
						
						  $scope.$watch('minDate1', function(v){
							    //$scope.minEndDate = v;
							  $scope.newFromDate1 = $("#fromdate1").val();
							  d1 = $scope.newFromDate1.slice(0,2);
							  m1 = $scope.newFromDate1.slice(3,6);
							  y1 = $scope.newFromDate1.slice(7,11);
							  console.log("new monthly From date is "+$scope.newFromDate1);
							  $scope.currDate1 = y1+'/'+m1+'/'+d1;
							  var aaa = Date.getMonthNumberFromName(m1);
								console.log(aaa+2);
								console.log("today is "+new Date($scope.currDate1));
							  
							  var future1 = new Date($scope.currDate1).add(30).days();
								console.log("future date is "+future1);
							  //var date1 = new Date();
							 // $scope.maxEndDate1 = date1.setDate(future1.getDate());
								$scope.maxEndDate1 = future1;
								
							    //$scope.maxEndDate = date.setDate((new Date('2015/03/13')).getDate()+6);
							  });
							  $scope.$watch('maxDate1', function(v){
							    $scope.maxStartDate = v;
							  });
						
						/*$scope.minDate1 = new Date('2015/03/14');
						var date1 = new Date();
						$scope.maxEndDate1 = date1.setDate((new Date('2015/03/13')).getDate()+31);
						$scope.maxDate1 = $scope.maxEndDate1;
						
						$scope.minStartDate1 = 0; //fixed date
						  $scope.maxStartDate1 = $scope.maxDate1; //init value
						  $scope.minEndDate1 = $scope.minDate1; //init value
						  $scope.maxEndDate1 = $scope.maxDate1; //fixed date same as $scope.maxStartDate init value
						  
						  $scope.$watch('minDate1', function(v){
							    //$scope.minEndDate = v;
							  var date1 = new Date();
							    $scope.maxEndDate1 = date1.setDate((new Date('2015/03/14')).getDate()+30);
							  });
							  $scope.$watch('maxDate1', function(v){
							    $scope.maxStartDate1 = v;
							  });
							  */
							  $scope.openFromDate1 = function() {
								    
								    $timeout(function() {
								      
								      $scope.startOpened1 = true;
								    });
								  };

								  $scope.openToDate1 = function() {
								    $timeout(function() {
								      $scope.endOpened1 = true;
								    });
								  };

						
						$scope.dateOptions1 = {
							formatYear : 'yy',
							startingDay : 1
						};

						$scope.formats = [ 'dd-MMM-yyyy' ];
						$scope.format = $scope.formats[0];
						
						};
						
					//******************* Date Picker END ***********************//
					
						$scope.generateCharts = function() {
							 $scope.resetWeeklyDates();
							 $scope.resetMonthlyDates();
							$rootScope.fromdate = $("#fromdate").val();
							$rootScope.todate = $("#todate").val();
							var fdate = $("#fromdate").val();
							var tdate = $("#todate").val();
							var fdate1 = $("#fromdate1").val();
							var tdate1 = $("#todate1").val();
							
							if ($scope.range) {
								if ($scope.range == 'weekly') {
									
									fd = fdate.slice(0,2);
									fm = fdate.slice(3,6);
									fy = fdate.slice(9,11);
									
									td = tdate.slice(0,2);
									tm = tdate.slice(3,6);
									ty = tdate.slice(9,11);
									
									$scope.selectedStart = fd+'/'+fm+'/'+fy;
									$rootScope.fromDate = fd+'/'+fm+'/'+fy;
									$scope.selectedEnd = td+'/'+tm+'/'+ty;
									$rootScope.toDate = td+'/'+tm+'/'+ty;
									
									//alert("start date is "+$scope.selectedStart);
									//alert("end date is "+$scope.selectedEnd);
									/*var current = new Date($("#todate").val() ); // get current date    
									var weekstart = current.getDate()
											- current.getDay() + 1;
									var weekend = weekstart + 4; // end day is the first day + 6 
									var monday = new Date(current
											.setDate(weekstart));
									var sunday = new Date(current
											.setDate(weekend));
									
									$scope.selectedStart=monday.toDateString().slice(8,10)+'/'+monday.toDateString().slice(4,7)+'/'+monday.toDateString().slice(13,15);
									$scope.selectedEnd=sunday.toDateString().slice(8,10)+'/'+sunday.toDateString().slice(4,7)+'/'+sunday.toDateString().slice(13,15);
								*/
								}
								else if($scope.range == 'monthly') {
									//alert("monthly selected");
									$rootScope.fromdate = $("#fromdate1").val();
									$rootScope.todate = $("#todate1").val();
									fd1 = fdate1.slice(0,2);
									fm1 = fdate1.slice(3,6);
									fy1 = fdate1.slice(9,11);
									
									td1 = tdate1.slice(0,2);
									tm1 = tdate1.slice(3,6);
									ty1 = tdate1.slice(9,11);
									
									$scope.selectedStart = fd1+'/'+fm1+'/'+fy1;
									$rootScope.fromDate = fd1+'/'+fm1+'/'+fy1;
									$scope.selectedEnd = td1+'/'+tm1+'/'+ty1;
									$rootScope.toDate = td1+'/'+tm1+'/'+ty1;
									
									//alert("start date is "+$scope.selectedStart);
									//alert("end date is "+$scope.selectedEnd);
									}
								}
							$scope.plotCharts();
						};
						
						$scope.plotCharts = function() {
							//alert("plot charts function called");
							
							$scope.trendReports = $http.get("dash/trendreports/getTrendingInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response1) {
								$scope.trendReportsRelease = $http.get("dash/trendreports/getReleaseInfo?projectName="+$rootScope.selectedProjectName+"&releaseName="+$rootScope.selectedReleaseName+"&fromDate="+$scope.selectedStart+"&toDate="+$scope.selectedEnd).success(function(response2) {
												//alert(JSON.stringify(response));
									
									//alert("response1 is ------------------"+JSON.stringify(response1));
									
									response_z = response2;
									$scope.plotDefectRootBreakUp(response2);
									$scope.plotDefectTypeBreakUp(response2);
									$scope.plotDefectAgeing(response2);
									$scope.plotDefectAgeingOpen(response2);
									
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
							        
							    	act = actual;
						        	rda = rdate;
						        	pla = plan;
						        	passed_z = passed;
						        	failed_z = failed;
						            open_z = open;
								    closed_z = closed;
								    defectDensity_z =defectDensity;
								    defectSeverityIndex_z = defectSeverityIndex;
								    badFix_z= badFix;
								    defectAccept_z = defectAccept;
								    total_urgent_z = total_urgent;
								    total_high_z = total_high;
								    total_medium_z = total_medium;
								    total_low_z = total_low;
							        
if(rdate == 0 && open == 0 && closed == 0){
								    	
								    	noData("#defectsopen","Defects");
								    	
								    }
								    
								    else{
								    	$scope.plotDefectsOpenClosed(rdate,open,closed);
								    }
								    
								    if(rdate == 0 && defectDensity == 0){
								    	noData("#defectdensity","Defect Density");
								    }
								    
								    else{
							        
									$scope.plotDefectDensity(rdate,defectDensity);
									
								    }
								    
								    if(rdate == 0 && defectAccept == 0){
								    	noData("#defectaccept","Defect Acceptance Rate");
								    }
								    
								    else{
							        
								    	$scope.plotDefectAccept(rdate,defectAccept);
									
								    }
								    if(rdate == 0 && badFix == 0){
								    	noData("#badfix","Bad Fixes");
								    }
								    
								    else{
							        
								    	$scope.plotBadFix(rdate,badFix);
									
								    }
								    if(rdate == 0 && defectSeverityIndex == 0){
								    	noData("#defectdensityindex","Defect Severity Index");
								    }
								    
								    else{
							        
								    	$scope.plotDefectSeverityIndex(rdate,defectSeverityIndex);
									
								    }
								    if(rdate == 0 && passed == 0 && failed == 0){
								    	noData("#testcasestat","Test Case Status");
								    }
								    
								    else{
							        
								    	$scope.plotTestCaseStatChart(rdate,passed,failed);
									
								    }
								    if(total_urgent == 0 && total_high == 0 && total_medium == 0 && total_low == 0){
								    	noData("#defectseveritybreak","Defect Severity Breakup");
								    }
								    
								    else{
							        
								    	$scope.plotDefectSeverityBreakUp(total_urgent,total_high,total_medium,total_low);
									
								    }
								    var plan=parseInt(response2.entity[0].planned);
								    $scope.plotChart(actual,rdate,plan);
								    
								   
								  
								    
							     /*   $scope.plotDefectsOpenClosed(rdate,open,closed);
									$scope.plotDefectDensity(rdate,defectDensity);
									$scope.plotBadFix(rdate,badFix);
									$scope.plotDefectAccept(rdate,defectAccept);
									$scope.plotDefectSeverityIndex(rdate,defectSeverityIndex);
									$scope.plotTestCaseStatChart(rdate,passed,failed);
									$scope.plotDefectSeverityBreakUp(total_urgent,total_high,total_medium,total_low);
									var plan=parseInt(response2.entity[0].planned);
									$scope.plotChart(actual,rdate,plan);*/
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

							var chart = $('#testcaseexec')
									.highcharts(
											{
												title : {
													text : 'Test Case Execution Rate',
													align: 'left',
														style: {
												            fontSize: 'small',
												            fontWeight: 'bold',
												            color : '#428bca'
												        },
												},
												chart : {
													height : 150,
													/*width : 150*/
												},
												credits : {
													enabled : false
												},

												xAxis : {
													categories :rdate,
													labels: {
														style : {
															fontSize : '10px'
														}
													}
												},
												legend : {
													enabled: false
												},
												yAxis : {
													min: 0,
													title : {
														text : 'Number of Testcases',
														style: {
												            fontSize: 'smaller',
												            color : '#428bca',
												            display : 'none'
												        }
													}
												},
												plotOptions : {
													line : {
														dataLabels : {
															enabled : false
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
														} ],
													    exporting: {
													        buttons: {
													            customButton: {
													                /*text: 'zoomz',*/
													            	symbol: 'url(images/s2.png)',
													            	/*symbolX:5,
													                symbolY:0,*/
													                onclick: function () {
													                	$( "#dialog" ).dialog( "open" );
																		$scope.renderChart(act,rda,pla);
													                }
													            }
													        }
													    } //export button end
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
													align: 'left',
													style: {
														 fontSize: 'small',
											            fontWeight: 'bold',
											            color : '#428bca'
											        }
												},chart : {
													height : 150,
													/*width : 150*/
												},
												legend : {
													enabled: false
												},
												subtitle: {
												    text: '(Pass vs Fail)',
												    align: 'left',
												    style: {
											            fontSize: 'smaller',
											            color : '#428bca'
											        },
											        y: 25
												},
												
												credits : {
													enabled : true
												},

												xAxis : {
													categories : rdate,
													labels: {
														style : {
															fontSize : '10px'
														}
													}
												},

												yAxis : {
													min: 0,
													title : {
														text : 'Number of Testcases',
														style: {
												            fontSize: 'smaller',
												            color : '#428bca',
												            display : 'none'
												        }
													}
												},

												tooltip : {
													
												},

												plotOptions : {
													line : {
														dataLabels : {
															enabled : false
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
														
														} ],
													    exporting: {
													        buttons: {
													            customButton: {
													                /*text: 'zoomz',*/
													            	symbol: 'url(images/s2.png)',
													            	/*symbolX:5,
													                symbolY:0,*/
													                onclick: function () {
													        			$( "#dialog" ).dialog( "open" );
																		$scope.testCaseStatChart(rda,passed_z,failed_z);
													                }
													            }
													        }
													    } //export button end
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
													align: 'left',
													style: {
														 fontSize: 'small',
											            fontWeight: 'bold',
											            color : '#428bca'
											        }
												},chart : {
													height : 150,
													/*width : 150*/
												},

												legend : {
													enabled: false
												},
												subtitle : {
													text : '(Open vs Closed)',
													align: 'left',
														style: {
												            fontSize: 'smaller',
												            color : '#428bca'
												        },
												        y: 25
												},
												credits : {
													enabled : false
												},

												xAxis : {
													categories: rdate,
													labels: {
														style : {
															fontSize : '10px'
														}
													}
												},

												yAxis : {
													allowDecimals : false,
													min : 0,
													title : {
														text : 'Number of Testcases',
														style: {
												            fontSize: 'smaller',
												            color : '#428bca',
												            display : 'none'
												        }
													}
												},

												tooltip : {
												},
												plotOptions : {
													line : {
														dataLabels : {
															enabled : false
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
														} ],
													    exporting: {
													        buttons: {
													            customButton: {
													                /*text: 'zoomz',*/
													            	symbol: 'url(images/s2.png)',
													            	/*symbolX:5,
													                symbolY:0,*/
													                onclick: function () {
													                	$( "#dialog" ).dialog( "open" );
																		$scope.defectsOpenClosed(rda,open_z,closed_z);
													                }
													            }
													        }
													    } //export button end
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
											align: 'left',
											style: {
												 fontSize: 'small',
									            fontWeight: 'bold',
									            color : '#428bca'
									        },
											//x : -20
										//center
										},chart : {
											height : 150,
											/*width : 150*/
										},

										legend : {
											enabled: false
										},
										credits : {
											enabled : false
										},

										xAxis : {
											categories : rdate,
											labels: {
												style : {
													fontSize : '10px'
												}
											}
										},
										yAxis : {
											min: 0,
											title : {
												text : 'Defect Density',
												style: {
										            fontSize: 'smaller',
										            color : '#428bca',
										            display : 'none'
										        }
													
											}
										},
										plotOptions : {
											line : {
												dataLabels : {
													enabled : false
												},

											}
										},
										tooltip : {
											
										},

										series : [ {
											name : 'Defect Density',
											data : defectDensity
										} ],
									    exporting: {
									        buttons: {
									            customButton: {
									                /*text: 'zoomz',*/
									            	symbol: 'url(images/s2.png)',
									            	/*symbolX:5,
									                symbolY:0,*/
									                onclick: function () {
									                	$( "#dialog" ).dialog( "open" );
														$scope.defectDensity(rda,defectDensity_z);
									                }
									            },
									            infoButton: {
									                /*text: 'zoomz',*/
									            	
									            	symbol: 'url(images/info_button.gif)',
									            	
									            	onclick:function(e){
									            		 //alert("hELLO"); 
									            		 $("#info").dialog('option', 'title', 'Defect Density');
									            		 $( "#info" ).dialog( "open" );
									            		 $( "#info" ).html("<h5>Defect Density=Total defects/Total testcases</h5>");
									            	 }
									            	
									            }
									        }
									    } //export button end
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
											align: 'left',
											style: {
												 fontSize: 'small',
									            fontWeight: 'bold',
									            color : '#428bca'
									        },
											//x : -20
										//center
										},chart : {
											height : 150,
											/*width : 150*/
										},
										legend : {
											enabled: false
										},
										credits : {
											enabled : false
										},
										xAxis : {
											categories :rdate,
											labels: {
												style : {
													fontSize : '10px'
												}
											}
										},
										yAxis : {
											min: 0,
											title : {
												text : 'Defect Severity Index',
												style: {
										            fontSize: 'smaller',
										            color : '#428bca',
										            display : 'none'
										        }
												
											}
										},
										plotOptions : {
											line : {
												dataLabels : {
													enabled : false
												},

											}
										},
										tooltip : {
											
										},
										
										series : [ {
											name : 'Defect Severity Index',
											data : defectSeverityIndex
										} ],
									    exporting: {
									        buttons: {
									            customButton: {
									                /*text: 'zoomz',*/
									            	symbol: 'url(images/s2.png)',
									            	/*symbolX:5,
									                symbolY:0,*/
									                onclick: function () {
									                	$( "#dialog" ).dialog( "open" );
														$scope.defectSeverityIndex(rda,defectSeverityIndex_z);
									                }
									            },
									            infoButton: {
									                /*text: 'zoomz',*/
									            	x:-49,
									            	symbol: 'url(images/info_button.gif)',
									            	 onclick:function(){
									            		 //alert("hELLO");
									            		 $("#info").dialog('option', 'title', 'Defect Severity Index');
									            		 $( "#info" ).dialog( "open" );
									            		 $( "#info" ).html("<h5>DSI=sum of(defect severity level*severity count)/total defects</h5>");
									            	 }
									            	
									            }
									        }
									    } //export button end
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
											align: 'left',
											style: {
												 fontSize: 'small',
									            fontWeight: 'bold',
									            color : '#428bca'
									        },
											//x : -20
										//center
										},chart : {
											height : 150,
											/*width : 150*/
										},
										legend : {
											enabled: false
										},
										subtitle : {
											text : '(Re-opened)',
											align: 'left',
												style: {
										            fontSize: 'smaller',
										            color : '#428bca'
										        },
										        y: 25
										},
										credits : {
											enabled : false
										},
										/*subtitle : {
											text : '',
											x : -20
										},*/
										xAxis : {
											categories :rdate,
											labels: {
												style : {
													fontSize : '10px'
												}
											}
										},
										yAxis : {
											min: 0,
											title : {
												text : 'Re-opened Defects (%)',
												style: {
										            fontSize: 'smaller',
										            color : '#428bca',
										            display : 'none'
										        }
											}
										},
										plotOptions : {
											line : {
												dataLabels : {
													enabled : false
												},

											}
										},

										tooltip : {
											valueSuffix : '%'
										},

										series : [ {
											name : 'Re-opened',
											data : badFix
										} ],
									    exporting: {
									        buttons: {
									            customButton: {
									                /*text: 'zoomz',*/
									            	symbol: 'url(images/s2.png)',
									            	/*symbolX:5,
									                symbolY:0,*/
									                onclick: function () {
									                	$( "#dialog" ).dialog( "open" );
														$scope.badFix(rda,badFix_z);
									                }
									            },
									            infoButton: {
									                /*text: 'zoomz',*/
									            	
									            	symbol: 'url(images/info_button.gif)',
									            	 onclick:function(){
									            		 //alert("hELLO");
									            		 $("#info").dialog('option', 'title', 'Bad Fix');
									            		 $( "#info" ).dialog( "open" );
									            		 $( "#info" ).html("<h5>Bad Fix=(Re-opened Defects/total defects)*100</h5>");
									            	 }
									            	
									            }
									        }
									    } //export button end
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
											text : 'Defect Acceptance Rate   ',
											align: 'left',
											style: {
												 fontSize: 'small',
									            fontWeight: 'bold',
									            color : '#428bca'
									        },
											//x : -20
										//center
										},chart : {
											height : 150,
											/*width : 150*/
										},
										legend : {
											enabled: false
										},
										credits : {
											enabled : false
										},
										subtitle : {
											text : '',
											x : -20
										},
										xAxis : {
											categories : rdate,
											labels: {
												style : {
													fontSize : '10px'
												}
											}
										},
										yAxis : {
											min: 0,
											title : {
												text : 'Defect Acceptance Rate   ',
												style: {
										            fontSize: 'smaller',
										            color : '#428bca',
										            display : 'none'
										        }
											}
										},
										plotOptions : {
											line : {
												dataLabels : {
													enabled : false
												},

											}

										},
										tooltip : {
											
										},

										series : [ {
											name : "Defects Accepted",
											data : defectAccept
										} ],
									    exporting: {
									        buttons: {
									            customButton: {
									                /*text: 'zoomz',*/
									            	symbol: 'url(images/s2.png)',
									            	/*symbolX:5,
									                symbolY:0,*/
									                onclick: function () {
									                	$( "#dialog" ).dialog( "open" );
														$scope.defectAccept(rda,defectAccept_z);
									                }
									            },
									            infoButton: {
									                /*text: 'zoomz',*/
									            	x:-52,
									            	symbol: 'url(images/info_button.gif)',
									            	 onclick:function(){
									            		 //alert("hELLO"); 
									            		 $("#info").dialog('option', 'title', 'Defect Accept');
									            		 $( "#info" ).dialog( "open" );
									            		 $( "#info" ).html("<h5>Defects Accept=1-(defects rejected/total defects)</h5>");
									            	 }
									            	
									            }
									        }
									    } //export button end
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
											            type: 'column',
											            height : 150,
														/*width : 150*/
														},
											        title: {
											            text: 'Defect Ageing (Closed)',
											            align: 'left',
											            style: {
											            fontSize: 'small',
														fontWeight: 'bold',
														color : '#428bca'
											            }
											        },
											        xAxis: {
											            categories: ['1D-5D','6D-10D','>10D'],
											            labels: {
															style : {
																fontSize : '10px'
															}
														}
											        },
											        yAxis: {
											            min: 0,
											            title: {
											                text: 'Defects',
											                style: {
															fontSize: 'smaller',
															color : '#428bca',
															display : 'none'
															}
											            },
											            stackLabels: {
											                enabled: false,
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
											            shadow: false,
											            enabled: false
											        },
											        tooltip: {
											        	borderRadius: 10,
											            borderWidth: 3,
											            followPointer:true,
											        	formatter: function () {
											                return this.series.name + ': ' + this.y + '<br/>' +
											                    'Total: ' + this.point.stackTotal;
											            }
											        },
											        plotOptions: {
											            column: {
											                stacking: 'normal',
											                dataLabels: {
											                    enabled: false,
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
											        ],
												    exporting: {
												        buttons: {
												            customButton: {
												                /*text: 'zoomz',*/
												            	symbol: 'url(images/s2.png)',
												            	/*symbolX:5,
												                symbolY:0,*/
												                onclick: function () {
												                	$( "#dialog" ).dialog( "open" );
																	$scope.defectAgeing(response_z);
												                }
												            },
												            infoButton: {
												                /*text: 'zoomz',*/
												            	
												            	symbol: 'url(images/info_button.gif)',
												            	 onclick:function(){
												            		 //alert("hELLO");
												            		 $("#info").dialog('option', 'title', 'Defect Ageing');
												            		 $( "#info" ).dialog( "open" );
												            		 $( "#info" ).html("<h5>Defect ageing=(defect fixed date-defect detected date)</h5>");
												            	 }
												            	
												            }
												        }
												    } //export button end
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
													plotShadow : false,
													
														height : 150,
														/*width : 150*/
													
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
													text : 'Defect Severity Breakup   ',
													align: 'left',
													style: {
														 fontSize: 'small',
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
										                    distance: 0

														},
														showInLegend : false
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
												} ],
											    exporting: {
											        buttons: {
											            customButton: {
											                /*text: 'zoomz',*/
											            	symbol: 'url(images/s2.png)',
											            	/*symbolX:5,
											                symbolY:0,*/
											                onclick: function () {
											                	$( "#dialog" ).dialog( "open" );
																$scope.defectSeverityBreakUp(total_urgent_z,total_high_z,total_medium_z,total_low_z);
											                }
											            }
											        }
											    } //export button end
											});

						};
						//************************ Defect Type Breakup  ***************************//
						$scope.plotDefectTypeBreakUp = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#defecttypebreak')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													height : 150,
													/*width : 150*/
													
												},
												title : {
													text : 'Defect Type Breakup',
													align: 'left',
													style: {
														 fontSize: 'small',
											            fontWeight: 'bold',
											            color : '#428bca'
											        }
												},
												plotOptions : {
													pie : {

														allowPointSelect : true,
														showInLegend : false,
														cursor : 'pointer',
														dataLabels : {
															enabled : true,
															formatter: function() {
										                        return Math.round(this.percentage*100)/100 + ' %';
										                    },
										                    distance: 0
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
												} ],
											    exporting: {
											        buttons: {
											            customButton: {
											                /*text: 'zoomz',*/
											            	symbol: 'url(images/s2.png)',
											            	/*symbolX:5,
											                symbolY:0,*/
											                onclick: function () {
											                	$( "#dialog" ).dialog( "open" );
																$scope.defectTypeBreakUp(response_z);
											                }
											            }
											        }
											    } //export button end
											});

						};

						//************************ Defect Rootcause Breakup ***************************//
						$scope.plotDefectRootBreakUp = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69', '#7CFC00', '#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#defectRootbreak')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													height : 150,
													/*width : 150*/

												},
												title : {
													text : 'Defect Rootcause Breakup',
													align: 'left',
													style: {
														 fontSize: 'small',
											            fontWeight: 'bold',
											            color : '#428bca'
											        }
												},
												plotOptions : {
													pie : {

														allowPointSelect : true,
														showInLegend : false,
														cursor : 'pointer',
														dataLabels : {
															enabled : true,
															formatter: function() {
										                        return Math.round(this.percentage*100)/100 + ' %';
										                    },
										                    distance: 0
														}
													}
												},
												series : [ {
													type : 'pie',
													data : [
														[
																"Implementation",
																response.entity[0].defectRootCause.implementation ],
														[
																"Environment",
																response.entity[0].defectRootCause.Environment ],
														[
																"Test Script",
																response.entity[0].defectRootCause.TestScript ],
														[
																"User Error",
																response.entity[0].defectRootCause.UserError_NotaDefect ],
														[
																"Requirements",
																response.entity[0].defectRootCause.Requirements ],
														[
																"Data",
																response.entity[0].defectRootCause.Data ] ]
												} ],
											    exporting: {
											        buttons: {
											            customButton: {
											                /*text: 'zoomz',*/
											            	symbol: 'url(images/s2.png)',
											            	/*symbolX:5,
											                symbolY:0,*/
											                onclick: function () {
											                	$( "#dialog" ).dialog( "open" );
																$scope.defectRootBreakUp(response_z);
											                }
											            }
											        }
											    } //export button end
											});

						};
						
						/*************************Defect ageing Open****************************************/
						
						$scope.plotDefectAgeingOpen = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#defectageingopen')
									.highcharts(
											{
												 chart: {
											            type: 'column',
											            height : 150
											        },
											        title: {
											            text: 'Defect Ageing (Open)',
											            align: 'left',
											            style: {
														fontSize: 'small',
														fontWeight: 'bold',
														color : '#428bca'
											            }
											        },
											        xAxis: {
											            categories: ['1D-5D','6D-10D','>10D']
											        },
											        yAxis: {
											            min: 0,
											            title: {
											                text: 'Defects',
											                style: {
															fontSize: 'smaller',
															color : '#428bca',
															display : 'none'
															}
											            },
											            stackLabels: {
											                enabled: false,
											                style: {
											                    fontWeight: 'bold',
											                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
											                }
											            }
											        },
											        legend : {
											        	enabled : false
											        },
											        /*legend: {
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
											        */tooltip: {
											        	borderRadius: 10,
											            borderWidth: 3,
											        	formatter: function () {
											                return this.series.name + ': ' + this.y + '<br/>' +
											                    'Total: ' + this.point.stackTotal;
											            },
											            followPointer:true
											        },
											        plotOptions: {
											            column: {
											                stacking: 'normal',
											                dataLabels: {
											                    enabled: false,
											                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
											                    style: {
											                        textShadow: '0 0 3px black'
											                    },
											                    padding:0
											                    
											                }
											            },
											            series: {
										                    cursor: 'pointer'
										                },
											        },
											        series: [{
											            name : 'Urgent',
											            data : [response.entity[0].lessThanFive_Open.Urgent,response.entity[0].fiveToTen_Open.Urgent,response.entity[0].greaterTen_Open.Urgent ]
											        }, {
											            name : 'High',
											            data: [response.entity[0].lessThanFive_Open.High,response.entity[0].fiveToTen_Open.High,response.entity[0].greaterTen_Open.High]
											        },{
											            name : 'Medium',
											            data: [response.entity[0].lessThanFive_Open.Medium,response.entity[0].fiveToTen_Open.Medium,response.entity[0].greaterTen_Open.Medium]
											        },{
											            name : 'Low',
											            data: [response.entity[0].lessThanFive_Open.Low,response.entity[0].fiveToTen_Open.Low,response.entity[0].greaterTen_Open.Low]
											        }
											        ],
												    exporting: {
												        buttons: {
												            customButton: {
												                /*text: 'zoomz',*/
												            	symbol: 'url(images/s2.png)',
												            	/*symbolX:5,
												                symbolY:0,*/
												                onclick: function () {
												                	$( "#dialog" ).dialog( "open" );
																	$scope.defectAgeingOpen(response_z);
												                }
												            },
												            infoButton: {
												                /*text: 'zoomz',*/
												            	
												            	symbol: 'url(images/info_button.gif)',
												            	 onclick:function(){
												            		 //alert("hELLO");
												            		 $("#info").dialog('option', 'title', 'Defect Ageing');
												            		 $( "#info" ).dialog( "open" );
												            		 $( "#info" ).html("<h5>Defect ageing=(defect fixed date-defect detected date)</h5>");
												            	 }
												            	
												            }
												        }
												    } //export button end
											});
						};
						/**********************************************************************************************************************************/
						/**********************************************************************************************************************************/
						/**********************************************************************************************************************************/
						
						
						//***************** Test Case Status ******************//
						$scope.testCaseStatChart = function(rdate,passed,failed) {
							Highcharts.setOptions({
								colors : [ '#8085e9', '#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												title : {
													text : 'Test Case Status',
													style: {
														 fontSize: 'medium',
											            fontWeight: 'bold',
											            color : '#428bca'
											        }
												},chart : {
													/*height : 150,
													width : 150*/
												},
												legend : {
													enabled: true
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
													min: 0,
													title : {
														text : 'Number of Testcases',
														style: {
												            fontSize: 'smaller',
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

						$scope.defectsOpenClosed = function(rdate,open,closed) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												title : {
													text : 'Defects',
													style: {
														 fontSize: 'medium',
											            fontWeight: 'bold',
											            color : '#428bca'
											        }
												},chart : {
													/*height : 150,*/
													/*width : 150*/
												},

												legend : {
													enabled: true
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
						$scope.defectDensity = function(rdate,defectDensity) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char').highcharts(
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
										},chart : {
											/*height : 150,*/
											/*width : 150*/
										},

										legend : {
											enabled: true
										},
										credits : {
											enabled : false
										},

										xAxis : {
											categories : rdate
										},
										yAxis : {
											min: 0,
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
						$scope.defectSeverityIndex = function(rdate,defectSeverityIndex) {

							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char').highcharts(
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
										},chart : {
											/*height : 150,*/
											/*width : 150*/
										},
										legend : {
											enabled: true
										},
										credits : {
											enabled : false
										},
										xAxis : {
											categories :rdate
										},
										yAxis : {
											min: 0,
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
						$scope.badFix = function(rdate,badFix) {

							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char').highcharts(
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
										},chart : {
											/*height : 150,*/
											/*width : 150*/
										},
										legend : {
											enabled: true
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
											min: 0,
											title : {
												text : 'Re-opened Defects (%)',
												style: {
										            fontSize: 'smaller',
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
						$scope.defectAccept = function(rdate,defectAccept) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char').highcharts(
									{
										title : {
											text : 'Defect Acceptance Rate   ',
											style: {
												 fontSize: 'medium',
									            fontWeight: 'bold',
									            color : '#428bca'
									        },
											x : -20
										//center
										},chart : {
											/*height : 150,*/
											/*width : 150*/
										},
										legend : {
											enabled: true
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
											min: 0,
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
						$scope.defectAgeing = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												 chart: {
											            type: 'column',
											           /* height : 150,*/
														/*width : 150*/
														},
											        title: {
											            text: 'Defect Ageing (Closed)',
											            style: {
											            fontSize: 'medium',
														fontWeight: 'bold',
														color : '#428bca'
											            }
											        },
											        xAxis: {
											            categories: ['1D-5D','6D-10D','>10D'],
											            fontSize: 'small'
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
											        /*legend: {
											            align: 'center',
											            x: -30,
											            y: 25,
											            verticalAlign: 'bottom',
											            floating: true,
											            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
											            borderColor: '#CCC',
											            borderWidth: 1,
											            shadow: false,
											            enabled: true
											        },*/
											        tooltip: {
											        	borderRadius: 10,
											            borderWidth: 3,
											            followPointer:true,
											            formatter: function () {
											                return this.series.name + ': ' + this.y + '<br/>' +
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
						$scope.defectSeverityBreakUp = function(total_urgent,total_high,total_medium,total_low) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													
														/*height : 150,*/
														/*width : 150*/
													
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
						$scope.defectTypeBreakUp = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													/*height : 150,*/
													/*width : 150*/
													
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
													
													data : [
													        [ 'Test Execution', response.entity[0].defectType.TestExecution ],
														[ 'Performance', response.entity[0].defectType.Performance ],
														[ 'Environment', response.entity[0].defectType.Environment ],
														[ 'Functional', response.entity[0].defectType.Functional ],
														[ 'Enhancement', response.entity[0].defectType.Enhancement ],
														[ 'Others', response.entity[0].defectType.others ]
													       ]
												} ]
											});

						};


						$scope.defectRootBreakUp = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69', '#7CFC00', '#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false,
													/*height : 150,*/
													/*width : 150*/

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
													data : [[ "Implementation", response.entity[0].defectRootCause.implementation ],
													         [ "Environment", response.entity[0].defectRootCause.Environment ],
													         [ "Test Script", response.entity[0].defectRootCause.TestScript ],
													         [ "User Error", response.entity[0].defectRootCause.UserError_NotaDefect ],
													         [ "Requirements", response.entity[0].defectRootCause.Requirements ],
													         [ "Data", response.entity[0].defectRootCause.Data ]


													]
												} ]
											});
						};


						$scope.defectAgeingOpen = function(response) {
							Highcharts.setOptions({
								colors : [ '#8085e9','#8d4654', '#fdb462', '#b3de69','#fb8072','#CC3333', '#CC6600', '#003366', '#130000', '#097054', '#FF8000', '#FE59C2', '#FFF200' ]
							});

							$('#char')
									.highcharts(
											{
												 chart: {
											            type: 'column'
											        },
											        title: {
											            text: 'Defect Ageing (Open)',
											            style: {
														fontSize: 'medium',
														fontWeight: 'bold',
														color : '#428bca'
											            }
											        },
											        xAxis: {
											            categories: ['1D-5D','6D-10D','>10D']
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
											        /*legend: {
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
											        */tooltip: {
											        	borderRadius: 10,
											            borderWidth: 3,
											        	formatter: function () {
											                return this.series.name + ': ' + this.y + '<br/>' +
											                    'Total: ' + this.point.stackTotal;
											            },
											            followPointer:true
											        },
											        plotOptions: {
											            column: {
											                stacking: 'normal',
											                dataLabels: {
											                    enabled: true,
											                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
											                    style: {
											                        textShadow: '0 0 3px black'
											                    },
											                    padding:0
											                    
											                }
											            },
											            series: {
										                    cursor: 'pointer'
										                },
											        },
											        series: [{
											            name : 'Urgent',
											            data : [response.entity[0].lessThanFive_Open.Urgent,response.entity[0].fiveToTen_Open.Urgent,response.entity[0].greaterTen_Open.Urgent ]
											        }, {
											            name : 'High',
											            data: [response.entity[0].lessThanFive_Open.High,response.entity[0].fiveToTen_Open.High,response.entity[0].greaterTen_Open.High]
											        },{
											            name : 'Medium',
											            data: [response.entity[0].lessThanFive_Open.Medium,response.entity[0].fiveToTen_Open.Medium,response.entity[0].greaterTen_Open.Medium]
											        },{
											            name : 'Low',
											            data: [response.entity[0].lessThanFive_Open.Low,response.entity[0].fiveToTen_Open.Low,response.entity[0].greaterTen_Open.Low]
											        }
											        ]
											});
						};
						
function noData(id,title){
							
							
							$(id).html("<div class='flot-chart-content'><div><text x='278' text-anchor='middle' class='highcharts-title' style='color:#428bca;font-size:medium;font-weight:bold;fill:#428bca;width:491px;' y='22'><tspan>"+title+"</tspan></text></div><img class='img-responsive' src='images/nodata3.jpg' width='650' height='400' align='middle'/></div>");

							
				           
						}						
						

						
});
				