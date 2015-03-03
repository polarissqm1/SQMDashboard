dashboardApp.controller('trendController', function($scope,$http) {
	$scope.plotCharts= null;
	
	/*$scope.project=['Select Project','CFT_POST_TRADE'];
	$scope.domain=['Select Domain','IB_TECHNOLOGY'];*/
	
	//** DatePicker **//
	 $scope.today = function() {
		    $scope.dt = new Date();
		  };
		  $scope.today();

		  $scope.clear = function () {
		    $scope.dt = null;
		  };

		  // Disable weekend selection
		  $scope.disabled = function(date, mode) {
		    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
		  };

		  $scope.toggleMin = function() {
		    $scope.minDate = $scope.minDate ? null : new Date();
		  };
		  $scope.toggleMin();

		  $scope.openFromDate = function($event,openedFromDate) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope.openedFromDate = true;
		  };
		  
		  $scope.openToDate = function($event,openedToDate) {
			    $event.preventDefault();
			    $event.stopPropagation();

			    $scope.openedToDate = true;
			  };
		  
		  $scope.dateOptions = {
				    formatYear: 'yy',
				    startingDay: 1
				  };

				  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
				  $scope.format = $scope.formats[0];
				  
	//** Date Picker END**//
			
				  $scope.generateCharts = function() {
						
						$scope.plotCharts();
					};
					
					$scope.plotCharts=function(){
						
						 $scope.plotChart();
						 $scope.plotTestCaseStatChart();
						 $scope.plotDefectsOpenClosed();
						 $scope.plotDefectsOpenClosed();
						 $scope.plotDefectDensity();
						 $scope.plotDefectDensityIndex();
						 $scope.plotBadFix();
						 $scope.plotDefectAccept();
						 $scope.plotDefectAgeing();
						 $scope.plotDowntimeBreakUp();
						 $scope.plotDefectSeverityBreakUp();
						 $scope.plotDefectTypeBreakUp();
						 $scope.plotDefectRootBreakUp();
					};
	//******************** Highcharts *************************//
				
					
				  $scope.plotChart = function(){
					  
		            	$('#testcaseexec').highcharts({
		            		 
		                    chart: {
		                        type: 'column',
		                        options3d: {
		                            enabled: true,
		                            alpha: 15,
		                            beta: 15,
		                            viewDistance: 25,
		                            depth: 40,
		                           
		                        },
		                        marginTop: 80,
		                        marginRight: 40
		                    },
		             
		                    title: {
		                        text: 'Test Case Execution Rate'
		                    },credits:{enabled:false},
		             
		                    xAxis: {
		                        categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		                    },
		             
		                    yAxis: {
		                        allowDecimals: false,
		                        min: 0,
		                        title: {
		                            text: 'Number of Testcases'
		                        }
		                    },
		             
		                    tooltip: {
		                        headerFormat: '<b>{point.key}</b><br>',
		                        pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
		                    },
		             
		                    plotOptions: {
		                        column: {
		                            stacking: 'normal',
		                            depth: 25,
		                            colors:['#74C7FF'],
		                            dataLabels: {
		    	                        enabled: true
		    	                    }
		                        }
		                    },
		             
		                    series: [ {
		                        name: 'Planned',
		                        data: [29.9, 71.5, 106.4, 129.2, 144.0],
		                        stack: 'male'
		                    }, {
		                        name: 'Actual',
		                        data: [135.6, 148.5, 216.4, 194.1,0],
		                        stack: 'female'
		                    }]
		                });
		            };
		            
		            //***************** Test Case Status ******************//
		            $scope.plotTestCaseStatChart = function(){
		            	$('#testcasestat').highcharts({
		            		 
		                    chart: {
		                        type: 'column',
		                        options3d: {
		                            enabled: true,
		                            alpha: 15,
		                            beta: 15,
		                            viewDistance: 25,
		                            depth: 40,
		                           
		                        },
		                        marginTop: 80,
		                        marginRight: 40
		                    },
		             
		                    title: {
		                        text: 'Test Case Status'
		                    },credits:{enabled:false},
		             
		                    xAxis: {
		                        categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		                    },
		             
		                    yAxis: {
		                        allowDecimals: false,
		                        min: 0,
		                        title: {
		                            text: 'Number of Testcases'
		                        }
		                    },
		             
		                    tooltip: {
		                        headerFormat: '<b>{point.key}</b><br>',
		                        pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
		                    },
		             
		                    plotOptions: {
		                        column: {
		                            stacking: 'normal',
		                            depth: 25,
		                            colors:['#E52325','#74C7FF'],
		                            dataLabels: {
		    	                        enabled: true
		    	                    }
		                        }
		                    },
		             
		                    series: [ {
		                        name: 'Planned',
		                        data: [29.9, 71.5, 106.4, 129.2, 144.0],
		                        stack: 'male'
		                    }, {
		                        name: 'Actual',
		                        data: [135.6, 148.5, 216.4, 194.1,0],
		                        stack: 'female'
		                    }]
		                });
		            };
		            
		            //************************ Defects Open  ***************************//
		            
		            $scope.plotDefectsOpenClosed = function(){
		            	$('#defectsopen').highcharts({
		            		 
		                    chart: {
		                        type: 'column',
		                        options3d: {
		                            enabled: true,
		                            alpha: 15,
		                            beta: 15,
		                            viewDistance: 25,
		                            depth: 40,
		                           
		                        },
		                        marginTop: 80,
		                        marginRight: 40
		                    },
		             
		                    title: {
		                        text: 'Defects - Open Vs Closed'
		                    },credits:{enabled:false},
		             
		                    xAxis: {
		                        categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		                    },
		             
		                    yAxis: {
		                        allowDecimals: false,
		                        min: 0,
		                        title: {
		                            text: 'Number of Testcases'
		                        }
		                    },
		             
		                    tooltip: {
		                        headerFormat: '<b>{point.key}</b><br>',
		                        pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
		                    },
		             
		                    plotOptions: {
		                        column: {
		                            stacking: 'normal',
		                            depth: 25,
		                            colors:['#E52325','#74C7FF'],
		                            dataLabels: {
		    	                        enabled: true
		    	                    }
		                        }
		                    },
		             
		                    series: [ {
		                        name: 'Open',
		                        data: [29.9, 71.5, 106.4, 129.2, 144.0],
		                        stack: 'male'
		                    }, {
		                        name: 'Closed',
		                        data: [135.6, 148.5, 216.4, 194.1,0],
		                        stack: 'female'
		                    }]
		                });
		            };
		            
		          //************************ Defects Open  ***************************//
		            $scope.plotDefectsOpenClosed = function(){
		            	$('#defectsopen').highcharts({
		            		 
		                    chart: {
		                        type: 'column',
		                        options3d: {
		                            enabled: true,
		                            alpha: 15,
		                            beta: 15,
		                            viewDistance: 25,
		                            depth: 40,
		                           
		                        },
		                        marginTop: 80,
		                        marginRight: 40
		                    },
		             
		                    title: {
		                        text: 'Defects - Open Vs Closed'
		                    },credits:{enabled:false},
		             
		                    xAxis: {
		                        categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		                    },
		             
		                    yAxis: {
		                        allowDecimals: false,
		                        min: 0,
		                        title: {
		                            text: 'Number of Testcases'
		                        }
		                    },
		             
		                    tooltip: {
		                        headerFormat: '<b>{point.key}</b><br>',
		                        pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
		                    },
		             
		                    plotOptions: {
		                        column: {
		                            stacking: 'normal',
		                            depth: 25,
		                            colors:['#E52325','#74C7FF'],
		                            dataLabels: {
		    	                        enabled: true
		    	                    }
		                        }
		                    },
		             
		                    series: [ {
		                        name: 'Open',
		                        data: [29.9, 71.5, 106.4, 129.2, 144.0],
		                        stack: 'male'
		                    }, {
		                        name: 'Closed',
		                        data: [135.6, 148.5, 216.4, 194.1,0],
		                        stack: 'female'
		                    }]
		                });
		            };
		            
		          //************************ Defect Density  ***************************//
		            $scope.plotDefectDensity = function(){
		            	
	            		$('#defectdensity').highcharts({
	            	        title: {
	            	            text: 'Defect Density',
	            	            x: -20 //center
	            	        },
	            	        credits:{enabled:false},
	            	       
	            	        xAxis: {
	            	            categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
	            	        },
	            	        yAxis: {
	            	            title: {
	            	                text: 'Defect Density'
	            	            }
	            	            },
	            	            plotOptions: {
	            	                line: {
	            	                    dataLabels: {
	            	                        enabled: true
	            	                    },
	            	                   
	            	                }
	            	            },
	            	        tooltip: {
	            	            valueSuffix: '°C'
	            	        },
	            	        legend: {
	            	            layout: 'vertical',
	            	            align: 'right',
	            	            verticalAlign: 'middle',
	            	            borderWidth: 0
	            	        },
	            	        series: [{
	            	            name: 'Defect Density',
	            	            data: [0, 0, 0, 1, 1.67]
	            	        }]
	            	    });

	            };
		          //************************ Defects Density Index  ***************************//
		            $scope.plotDefectDensityIndex = function(){
		            	
		        		$('#defectdensityindex').highcharts({
		        	        title: {
		        	            text: 'Defect Severity Index',
		        	            x: -20 //center
		        	        },credits:{enabled:false},
		        	        subtitle: {
		        	            text: '',
		        	            x: -20
		        	        },
		        	        xAxis: {
		        	            categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		        	        },
		        	        yAxis: {
		        	            title: {
		        	                text: 'Defect Density'
		        	            }
		        	            },
		        	            plotOptions: {
		        	                line: {
		        	                    dataLabels: {
		        	                        enabled: true
		        	                    },
		        	                   
		        	                }
		        	            },
		        	        tooltip: {
		        	            valueSuffix: '°C'
		        	        },
		        	        legend: {
		        	            layout: 'vertical',
		        	            align: 'right',
		        	            verticalAlign: 'middle',
		        	            borderWidth: 0
		        	        },
		        	        series: [{
		        	            name: 'DSI',
		        	            data: [0, 0, 0, 1, 1.67]
		        	        }]
		        	    });

		        };
		          //************************ Bad Fix  ***************************//
		            $scope.plotBadFix = function(){
		            	
		        		$('#badfix').highcharts({
		        	        title: {
		        	            text: 'Bad Fix(Re-Opened Defects)',
		        	            x: -20 //center
		        	        },credits:{enabled:false},
		        	        subtitle: {
		        	            text: '',
		        	            x: -20
		        	        },
		        	        xAxis: {
		        	            categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		        	        },
		        	        yAxis: {
		        	            title: {
		        	                text: 'Defect Density'
		        	            }
		        	            },
		        	            plotOptions: {
		        	                line: {
		        	                    dataLabels: {
		        	                        enabled: true
		        	                    },
		        	                   
		        	                }
		        	            },
		        	        
		        	        tooltip: {
		        	            valueSuffix:'%'
		        	        },
		        	        legend: {
		        	            layout: 'vertical',
		        	            align: 'right',
		        	            verticalAlign: 'middle',
		        	            borderWidth: 0
		        	        },
		        	        series: [{
		        	            name: 'Re-opened',
		        	            data: [20, 40, 80, 90, 100]
		        	        }]
		        	    });

		        };
		          //************************ Defect Acceptance Rate  ***************************//
		            $scope.plotDefectAccept = function(){
		            	
		        		$('#defectaccept').highcharts({
		        	        title: {
		        	            text: 'Defect Acceptance Rate',
		        	            x: -20 //center
		        	        },credits:{enabled:false},
		        	        subtitle: {
		        	            text: '',
		        	            x: -20
		        	        },
		        	        xAxis: {
		        	            categories: ['29-oct', '30-oct', '31-oct', '1-Nov', '2-Nov']
		        	        },
		        	        yAxis: {
		        	            title: {
		        	                text: 'Defect Density'
		        	            }
		        	            },
		        	            plotOptions: {
		        	                line: {
		        	                    dataLabels: {
		        	                        enabled: true
		        	                    },
		        	                   
		        	                }
		        	            	            
		        	        },
		        	        tooltip: {
		        	            valueSuffix:'%'
		        	        },
		        	        legend: {
		        	            layout: 'vertical',
		        	            align: 'right',
		        	            verticalAlign: 'middle',
		        	            borderWidth: 0
		        	        },
		        	        series: [{
		        	            name: 'Tokyo',
		        	            data: [0, 0, 0, 0, 100]
		        	        }]
		        	    });

		        };
		          //************************ Defect Ageing  ***************************//
		            $scope.plotDefectAgeing = function(){
		            	
		            	$('#defectageing').highcharts({
		                    chart: {
		                        type: 'column'
		                    },
		                    title: {
		                        text: 'Defect Ageing'
		                    },credits:{enabled:false},
		                    subtitle: {
		                        text: ''
		                    },
		                    xAxis: {
		                        categories: [
		                            '1D-4D',
		                            '5D-8D',
		                            '9D-12D',
		                            '>12D',
		                        ]
		                    },
		                    yAxis: {
		                        min: 0,
		                        title: {
		                            text: 'Defects'
		                        }
		                    },
		                    tooltip: {
		                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
		                        footerFormat: '</table>',
		                        shared: true,
		                        useHTML: true
		                    },
		                    plotOptions: {
		                        column: {
		                            pointPadding: 0.2,
		                            borderWidth: 0
		                        }
		                    },
		                    series: [{
		                        name: 'Tokyo',
		                        data: [0, 2,2,0]
		                    }, {
		                        name: 'New York',
		                        data: [0,0,0,0]
		                     }]
		                });
		            };
		          //************************ Downtime Breakup  ***************************//
		            $scope.plotDowntimeBreakUp = function(){
		            	$('#downtimebreak').highcharts({
		            	    chart: {
		            	        plotBackgroundColor: null,
		            	        plotBorderWidth: 1,//null,
		            	        plotShadow: false,
		            	        margin: [0, 0, 0, 0],
		            	        
		            	    },
		            	    title: {
		            	        text: 'Downtime Breakup'
		            	    },
		            	    plotOptions: {
		            	        pie: {
		            	        	
		            	            allowPointSelect: true,
		            	            showInLegend : true,
		            	            cursor: 'pointer',
		            	            dataLabels: {
		            	                enabled: true,
		            	                format: '{point.percentage:.1f} %',
		            	                style: {
		            	                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		            	                }
		            	            }
		            	        }
		            	    },
		            	    series: [{
		            	        type: 'pie',
		            	        /*name: 'Browser share',*/
		            	        data: [
		            	            ['Firefox',   45.0],
		            	                ['IE',       26.8],
		            	                {
		            	                    name: 'Chrome',
		            	                    y: 12.8,
		            	                    sliced: true,
		            	                    selected: true
		            	                },
		            	                ['Safari',    8.5],
		            	                ['Opera',     6.2],
		            	                ['Others',   0.7]
		            	           
		            	        ]
		            	    }]
		            	});

		            	};
		          //************************ Defects Severity Breakup  ***************************//
		            $scope.plotDefectSeverityBreakUp = function(){
		            	$('#defectseveritybreak').highcharts({
		            	    chart: {
		            	        plotBackgroundColor: null,
		            	        plotBorderWidth: 1,//null,
		            	        plotShadow: false,
		            	        margin: [0, 0, 0, 0],
		            	        
		            	    },
		            	    title: {
		            	        text: 'Defect Severity Breakup'
		            	    },
		            	    plotOptions: {
		            	        pie: {
		            	        	
		            	            allowPointSelect: true,
		            	            showInLegend : true,
		            	            cursor: 'pointer',
		            	            dataLabels: {
		            	                enabled: true,
		            	                format: '{point.percentage:.1f} %',
		            	                style: {
		            	                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		            	                }
		            	            }
		            	        }
		            	    },
		            	    series: [{
		            	        type: 'pie',
		            	        /*name: 'Browser share',*/
		            	        data: [
		            	            ['Firefox',   45.0],
		            	                ['IE',       26.8],
		            	                {
		            	                    name: 'Chrome',
		            	                    y: 12.8,
		            	                    sliced: true,
		            	                    selected: true
		            	                },
		            	                ['Safari',    8.5],
		            	                ['Opera',     6.2],
		            	                ['Others',   0.7]
		            	           
		            	        ]
		            	    }]
		            	});

		            	};
		          //************************ Defect Type Breakup  ***************************//
		            $scope.plotDefectTypeBreakUp = function(){
		        		$('#defecttypebreak').highcharts({
		        		    chart: {
		        		        plotBackgroundColor: null,
		        		        plotBorderWidth: 1,//null,
		        		        plotShadow: false,
		        		        margin: [0, 0, 0, 0],
		        		        
		        		    },
		        		    title: {
		        		        text: 'Defect Type Breakup'
		        		    },
		        		    plotOptions: {
		        		        pie: {
		        		        	
		        		            allowPointSelect: true,
		        		            showInLegend : true,
		        		            cursor: 'pointer',
		        		            dataLabels: {
		        		                enabled: true,
		        		                format: '{point.percentage:.1f} %',
		        		                style: {
		        		                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		        		                }
		        		            }
		        		        }
		        		    },
		        		    series: [{
		        		        type: 'pie',
		        		        /*name: 'Browser share',*/
		        		        data: [
		        		            ['Firefox',   45.0],
		        		                ['IE',       26.8],
		        		                {
		        		                    name: 'Chrome',
		        		                    y: 12.8,
		        		                    sliced: true,
		        		                    selected: true
		        		                },
		        		                ['Safari',    8.5],
		        		                ['Opera',     6.2],
		        		                ['Others',   0.7]
		        		           
		        		        ]
		        		    }]
		        		});

		        		};
		            
		          //************************ Defect Rootcause Breakup ***************************//
		            $scope.plotDefectRootBreakUp = function(){
		    			$('#defectRootbreak').highcharts({
		    			    chart: {
		    			        plotBackgroundColor: null,
		    			        plotBorderWidth: 1,//null,
		    			        plotShadow: false,
		    			        margin: [0, 0, 0, 0],
		    			        
		    			    },
		    			    title: {
		    			        text: 'Defect Type Breakup'
		    			    },
		    			    plotOptions: {
		    			        pie: {
		    			        	
		    			            allowPointSelect: true,
		    			            showInLegend : true,
		    			            cursor: 'pointer',
		    			            dataLabels: {
		    			                enabled: true,
		    			                format: '{point.percentage:.1f} %',
		    			                style: {
		    			                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		    			                }
		    			            }
		    			        }
		    			    },
		    			    series: [{
		    			        type: 'pie',
		    			        /*name: 'Browser share',*/
		    			        data: [
		    			            ['Firefox',   45.0],
		    			                ['IE',       26.8],
		    			                {
		    			                    name: 'Chrome',
		    			                    y: 12.8,
		    			                    sliced: true,
		    			                    selected: true
		    			                },
		    			                ['Safari',    8.5],
		    			                ['Opera',     6.2],
		    			                ['Others',   0.7]
		    			           
		    			        ]
		    			    }]
		    			});

		    			};
		        
						
	
});