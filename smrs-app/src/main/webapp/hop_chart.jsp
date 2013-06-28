<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Hop图表示例</title>
<link rel="stylesheet" type="text/css" href="${staticURL}/style/jquery.jqplot.min.css" />
<script src="${staticURL}/scripts/artdialog/jquery.artDialog.js?skin=aero"></script>
<script src="${staticURL}/scripts/jqplot/jquery.jqplot.min.js"></script>
<script src="${staticURL}/scripts/jqplot/excanvas.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.barRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pieRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.pointLabels.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.highlighter.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.cursor.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.meterGaugeRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.bubbleRenderer.min.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.ohlcRenderer.js"></script>
<script src="${staticURL}/scripts/jqplot/jqplot.plotImage.min.js"></script>


<style>
table.jqplot-table-legend td{
	padding: 2px;
	white-space: nowrap;
}

</style>

</head>

<body>
	<table>
		<tr>
			<td><div id="chart" style="margin-top:20px; margin-left:20px; width:300px; height:250px;"></div></td>
			<td><div id="chart6" style="margin-top:20px; margin-left:150px; width:300px; height:250px;"></div></td>
		</tr>
		<tr>
			<td><div id="chart2" style="margin-top:20px; margin-left:20px; width:300px; height:250px;"></div></td>
			<td><div id="chart7" style="margin-top:20px; margin-left:150px; width:300px; height:250px;"></div></td>
		</tr>
		<tr>
			<td><div id="chart3" style="margin-top:20px; margin-left:20px; width:300px; height:250px;"></div></td>
			<td><div id="chart8" style="margin-top:20px; margin-left:150px; width:300px; height:250px;"></div></td>
		</tr>
		<tr>
			<td><div id="chart4" style="margin-top:20px; margin-left:20px; width:300px; height:250px;"></div></td>
			<td><div id="chart9" style="margin-top:20px; margin-left:150px; width:300px; height:250px;"></div></td>
		</tr>
		<tr>
			<td><div id="chart5" style="margin-top:20px; margin-left:20px; width:300px; height:250px;"></div></td>
			<td><div id="chart10" style="margin-top:20px; margin-left:150px; width:300px; height:250px;"></div></td>
		</tr>
	</table> 
<script type="text/javascript">
	
	/**
	 * 1. 直线图
	 *var line1=[['23-May-08', 578.55], ['20-Jun-08', 566.5], ['25-Jul-08', 480.88], ['22-Aug-08', 509.84],
	 *		      ['26-Sep-08', 454.13], ['24-Oct-08', 379.75], ['21-Nov-08', 303], ['26-Dec-08', 308.56],
	 *		      ['23-Jan-09', 299.14], ['20-Feb-09', 346.51], ['20-Mar-09', 325.99], ['24-Apr-09', 386.15]];
	 * plot4 = $.jqplot('chart4_line', [line1], {
	 */
 	var line = <s:property value="linedata" />;
 	$.jqplot('chart', [line], {
	      title:'直线图',
	      axes:{
	        xaxis:{
	          renderer:$.jqplot.DateAxisRenderer //x轴展示形式为日期
	        },
	        yaxis:{
	          tickOptions:{
	            formatString:'$%.2f'
	            }
	        }
	      },
	      highlighter: {	//鼠标经过高亮显示每个点
	        show: true,
	        sizeAdjust: 7.5
	      }
	  });
	  
	   
	   
 	/**
	 * 2. 横向条形图
	 * plot2 = $.jqplot('chart2_bar', [[[12,1], [4,2], [6,3], [3,4]], [[5,1], [1,2], [3,3], [4,4]]], {
	 */
 	$.jqplot('chart2',  <s:property value="bardata" />, {
	 	title: '横向条形组图',
	 	animate: true,
        seriesDefaults: {
            renderer:$.jqplot.BarRenderer,
            pointLabels: { show: true},
            shadowAngle: 135,	//阴影偏移角度
            rendererOptions: {
                barDirection: 'horizontal'//横向条 （这种图各点要为二维，即有x,y坐标，且根据y坐标分组。）
            }
        },
        axes: {
            yaxis: { //Y轴展示形式，Y坐标各组要相同，以便分组
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ['1月', '2月', '3月', '4月']
            }
        },
        legend: {
        	show: true,
        	placement: 'outside',
        	labels: ['冰箱', '空调']
        }
    });

	   
 	
 	/**
	 * 3. 柱状图
	 *  var s1 = [12, 62, 37, 80, 98];
	  * var s2 = [27, 35, 63, 52, 79];
	 *  plot1 = $.jqplot('chart1_column', [s1, s2], {
	 */
    var ticks = ['2007', '2008', '2009', '2011', '2012'];
    
    plot1 = $.jqplot('chart3', <s:property value="columndata" />, {
    	title: '柱状图',
    	animate: true,
        seriesDefaults: {
            renderer:$.jqplot.BarRenderer,
            pointLabels: { 
            	show: true
            }
        },
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks
            }
        },
        legend: {
        	show: true,
        	placement: 'outside',
        	labels: ['冰箱', '空调']
        }
    });
    
    /**
	 * 4, 横向条状图
	 */
	plot3 = $.jqplot('chart4', [[[2,1], [6,2], [7,3], [10,4]], [[7,1], [5,2],[3,3],[2,4]], [[14,1], [9,2], [9,3], [8,4]]], {
        title: '横向条状图',
        stackSeries: true,	//表示显示为栈的形式，false则显示为普通横向条
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            rendererOptions: {
                barDirection: 'horizontal'
            },
            pointLabels: {show: true, formatString: '%d'}
        },
        legend: {
            show: true,
            placement: 'outside',
            labels: ['冰箱', '空调', '洗衣机']
        },
        axes: {
            yaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ['5月', '6月', '7月', '8月']
            }
        }
    });
    
	/**
	 * plot5, 可放大直线图
	 */
	var goog = [["6/22/2009 16:00:00",425.32],
		["6/8/2009 16:00:00",424.84],
		["5/26/2009 16:00:00",417.23],
		["5/11/2009 16:00:00",390],
		["4/27/2009 16:00:00",393.69],
		["4/13/2009 16:00:00",392.24],
		["3/30/2009 16:00:00",369.78],
		["3/16/2009 16:00:00",330.16],
		["3/2/2009 16:00:00",308.57],
		["2/17/2009 16:00:00",346.45],
		["2/2/2009 16:00:00",371.28],
		["1/20/2009 16:00:00",324.7],
		["1/5/2009 16:00:00",315.07],
		["12/22/2008 16:00:00",300.36],
		["12/8/2008 16:00:00",315.76],
		["11/24/2008 16:00:00",292.96],
		["11/10/2008 16:00:00",310.02],
		["10/27/2008 16:00:00",359.36],
		["10/13/2008 16:00:00",372.54],
		["9/29/2008 16:00:00",386.91],
		["9/15/2008 16:00:00",449.15],
		["9/2/2008 16:00:00",444.25],
		["8/25/2008 16:00:00",463.29],
		["8/11/2008 16:00:00",510.15],
		["7/28/2008 16:00:00",467.86],
		["7/14/2008 16:00:00",481.32],
		["6/30/2008 16:00:00",537],
		["6/16/2008 16:00:00",546.43],
		["6/2/2008 16:00:00",567],
		["5/19/2008 16:00:00",544.62],
		["5/5/2008 16:00:00",573.2],
		["4/21/2008 16:00:00",544.06],
		["4/7/2008 16:00:00",457.45],
		["3/24/2008 16:00:00",438.08],
		["3/10/2008 16:00:00",437.92],
		["2/25/2008 16:00:00",471.18],
		["2/11/2008 16:00:00",529.64],
		["1/28/2008 16:00:00",515.9],
		["1/14/2008 16:00:00",600.25],
		["12/31/2007 16:00:00",657],
		["12/17/2007 16:00:00",696.69],
		["12/3/2007 16:00:00",714.87],
		["11/19/2007 16:00:00",676.7],
		["11/5/2007 16:00:00",663.97],	
		["10/22/2007 16:00:00",674.6],
		["10/8/2007 16:00:00",637.39],
		["9/24/2007 16:00:00",567.27],
		["9/10/2007 16:00:00",528.75],
		["8/27/2007 16:00:00",515.25]];
		
	  var plot5 = $.jqplot('chart5', [goog], { 
      title: '可放大直线图', 
      axes: { 
          xaxis: { 
              renderer: $.jqplot.DateAxisRenderer,
              min:'August 1, 2007 16:00:00', 
              tickInterval: '4 months', //间隔4个月
              tickOptions:{formatString:'%Y/%#m/%#d'} 
          }, 
          yaxis: { 
              tickOptions:{formatString:'$%.2f'} 
          } 
      }, 
      cursor:{ 
        show: true,
        zoom:true //可放大
      } 
  });
	  
	  /**
		 * plot6, 区域图
		 */
		var l2 = [11, 9, 5, 12, 14];
	    var l3 = [4, 8, 5, 3, 6];
	    var l4 = [12, 6, 13, 11, 2];    
	 
	     
	    var plot6 = $.jqplot('chart6',[l2, l3, l4],{
	       title: '区域图', 
	       stackSeries: true,
	       seriesDefaults: {
	           fill: true	//是否填充
	       },
	       axes: {
	           xaxis: {
	               renderer: $.jqplot.CategoryAxisRenderer,
	               ticks: ["Mon", "Tue", "Wed", "Thr", "Fri"]
	           }
	       },
	       legend: {
          	show: true,
          	placement: 'outside'
          }
	    });


		/**
		 * plot7, 饼图
		 */
      plot7 = jQuery.jqplot('chart7', 
      	[ [['电视', 760],
      	  ['冰箱', 2000],
      	  ['空调', 3621],
      	  ['洗衣机', 1453],
      	  ['手机', 900]]
				        	
      	],
      	{
      		title: '饼图',
      		seriesDefaults: {
      			shadow: true, //阴影效果
      			renderer: jQuery.jqplot.PieRenderer, 
      			rendererOptions: { 
      				showDataLabels: true 
      			} 
      		},
      		legend: {
      			show: true
      		}
      	}
      
      );


		/**
		 * plot8, 动态直线图
		 */
	  var s11 = [[2002, 112000], [2003, 122000], [2004, 104000], [2005, 99000], [2006, 121000], 
      [2007, 148000], [2008, 114000], [2009, 133000], [2010, 161000], [2011, 173000]];
      var s22 = [[2002, 10200], [2003, 10800], [2004, 11200], [2005, 11800], [2006, 12400], 
      [2007, 12800], [2008, 13200], [2009, 12600], [2010, 13100]];

      plot8 = $.jqplot("chart8", [s22, s11], {
      	title: '动态直线图(可放大)',
          // Turns on animatino for all series in this plot.
          animate: true,
          // Will animate plot on calls to plot1.replot({resetAxes:true})
          animateReplot: true,
          cursor: {
              show: true,
              zoom: true,
              looseZoom: true,
              showTooltip: false
          },
          series:[
              {
                  pointLabels: {
                      show: false
                  },
                  renderer: $.jqplot.BarRenderer,
                  showHighlight: false,
                  yaxis: 'y2axis',
                  rendererOptions: {
                      // Speed up the animation a little bit.
                      // This is a number of milliseconds.  
                      // Default for bar series is 3000.  
                      animation: {
                          speed: 2500
                      },
                      barWidth: 15,
                      barPadding: -15,
                      barMargin: 0,
                      highlightMouseOver: false
                  }
              }, 
              {
                  rendererOptions: {
                      // speed up the animation a little bit.
                      // This is a number of milliseconds.
                      // Default for a line series is 2500.
                      animation: {
                          speed: 2000
                      }
                  }
              }
          ],
          axesDefaults: {
              pad: 0
          },
          axes: {
              // These options will set up the x axis like a category axis.
              xaxis: {
                  tickInterval: 1,
                  drawMajorGridlines: false,
                  drawMinorGridlines: true,
                  drawMajorTickMarks: false,
                  rendererOptions: {
	                  tickInset: 0.5,
	                  minorTicks: 1
	              }
              },
              yaxis: {
                  tickOptions: {
                      formatString: "$%'d"
                  },
                  rendererOptions: {
                      forceTickAt0: true
                  }
              },
              y2axis: {
                  tickOptions: {
                      formatString: "$%'d"
                  },
                  rendererOptions: {
                      // align the ticks on the y2 axis with the y axis.
                      alignTicks: true,
                      forceTickAt0: true
                  }
              }
          },
          highlighter: {
              show: true, 
              showLabel: true, 
              tooltipAxes: 'y',
              sizeAdjust: 7.5 , tooltipLocation : 'ne'
          }
      });


		/**
		 * plot9, 仪表盘
		 */
		
		var p4 = [52200];
		plot9 = $.jqplot('chart9', [p4], {
			title: '仪表盘',
			seriesDefaults:{
				renderer: $.jqplot.MeterGaugeRenderer,
				rendererOptions: {
					label: 'Metric Tons per Year',
					labelPosition: 'bottom',	// inside/bottom
					labelHeightAdjust: -5,// label离默位置的偏移量，-上+下
					intervalOuterRadius: 85,	//有色圆弧外径
					ticks: [10000, 30000, 50000, 70000],	// 表盘示数
					intervals: [22000, 55000, 70000],	// 各颜色取值边界
					intervalColors: ['#66cc66', '#E7E658', '#cc6666']
				}
			}
			
		});


		/**
		 * plot10, 球状图
		 */
		 var arr = [[11, 123, 1236, "Acura"], [45, 92, 1067, "Alfa Romeo"], 
		    [24, 104, 1176, "AM General"], [50, 23, 610, "Aston Martin Lagonda"], 
		    [18, 17, 539, "Audi"], [7, 89, 864, "BMW"], [2, 13, 1026, "Bugatti"]];
		     
		    var plot10 = $.jqplot('chart10',[arr],{
		    	title: '球状图',
		        seriesDefaults:{
		            renderer: $.jqplot.BubbleRenderer,
		            rendererOptions: {
		                bubbleGradients: true
		            },
		            shadow: true
		        }
		    });
		
 
    
</script>


</body>




</html>