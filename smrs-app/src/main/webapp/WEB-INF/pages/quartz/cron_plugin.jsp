<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.haier.openplatform.console.util.CronExpressionEx"%>
<%@page import="com.haier.openplatform.console.util.DateFormatUtil"%>  
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style>
.usual {
	background: none repeat scroll 0 0 #181818; 
	border: 1px solid #GGGGGG;
	color: #111111;
	margin: 0px auto;
	padding: 0px; 20px;
	width: 100%;
}

.usual li {
	float: left;
	list-style: none outside none;
}

.usual ul a {
	background: none repeat scroll 0 0 #444444;
	color: #000000;
	display: block;
	font: 10px ;
	margin: 1px 1px 1px 0;
	padding: 6px 10px;
	text-decoration: none !important;
}

.usual ul a:hover {
	background: none repeat scroll 0 0 #111111;
	color: #FFFFFF;
}

.usual ul a.selected {
	background: none repeat scroll 0 0 snow;
	border-bottom: 1px solid snow;
	color: #000000;
	cursor: default;
	margin-bottom: 0;
}

.usual div {
	background: none repeat scroll 0 0 snow;
	clear: left;
	font: 12px Georgia;
	padding: 10px 0px 2px;
}

.usual div a {
	color: #000000;
	font-weight: bold;
}

#usual2 {
	background: none repeat scroll 0 0 #F0F0F0; 
}

#usual2 a {
	background: none repeat scroll 0 0 #F0F0F0;
	border-top: 1px solid #ACD6FF;
	border-left:  1px solid #ACD6FF;
	border-right:  medium solid #ACD6FF; 
}

#usual2 a:hover {
	background: none repeat scroll 0 0 #ACD6FF;
}

#usual2 a.selected {
	background: none repeat scroll 0 0 #ACD6FF;
} 

#freedom {
	color: #6688DD;
	float: left;
	font: italic 1.3em Cambria, Times New Roman, serif;
	width: 90px;
}

#freedom li a {
	background: none repeat scroll 0 0 #222222;
	display: block;
	font-size: 0.7em;
	font-style: normal;
	font-weight: bold;
	margin: 1px;
	padding: 2px 6px 4px;
	text-align: left;
	text-decoration: none;
	width: 80px;
}

#freedom li a:hover {
	background: none repeat scroll 0 0 #0A0A0A;
	width: 86px;
}

#freedom li a.selected {
	background: none repeat scroll 0 0 #FFFFFF;
	color: #000000;
	cursor: default;
	width: 90px;
}

.tabContainer {
	background: none repeat scroll 0 0 #181818;
	border: 1px solid #222222;
	margin: 20px auto;
	padding-top: 2px;
	width: 400px;
}

.tabContainer h3 {
	color: #FF6600;
	padding-bottom: 4px;
}

.tabContainer p {
	padding: 2px 12px 10px;
	text-align: left;
}

#three {
	border-bottom: 1px solid #444444;
	border-right: 1px solid #444444;
	left: 0;
	position: absolute;
	top: 0;
}

#four {
	bottom: 10px;
	color: #DD6666;
	font: 2em Cambria, Times New Roman, serif;
	position: fixed;
	right: 20px;
}

.imagebox {
	background: none repeat scroll 0 0 #040404;
	border: 1px solid #1A1A1A;
	height: 90px;
	margin: 10px auto;
	width: 100px;
}

.imagebox a {
	background: none repeat scroll 0 0 #111111;
	display: block;
	float: left;
	height: 25px;
	line-height: 23px;
	text-decoration: none;
	width: 25px;
}

.imagebox a:hover {
	background: none repeat scroll 0 0 #000000;
}

.imagebox a.selected {
	background: none repeat scroll 0 0 snow;
	color: #222222;
	font-weight: bold;
}

.imagebox img {
	clear: both;
	margin-top: 6px;
}

#adv2 {
	background: none repeat scroll 0 0 #181818;
	margin: 6px auto;
	width: 500px;
}

#adv2 ul {
	display: block;
	float: left;
	height: 50px;
	width: 50px;
}

#adv2 li {
	float: left;
}

#adv2 li.split {
	clear: both;
}

#adv2 li a {
	background: none repeat scroll 0 0 #222222;
	display: block;
	height: 25px;
	line-height: 22px;
	text-decoration: none;
	width: 25px;
}

#adv2 li a:hover {
	background: none repeat scroll 0 0 #0A0A0A;
}

#adv2 li a.selected {
	background: none repeat scroll 0 0 snow;
	color: #111111;
	font-weight: bold;
}

#adv2 span {
	background: none repeat scroll 0 0 #181818;
	display: block;
	float: right;
	height: 50px;
	line-height: 45px;
	width: 450px;
}

#adv3 a {
	color: snow;
	font-size: 2em;
	font-weight: bold;
	margin: 6px;
	text-decoration: none;
}

#adv3 a:hover {
	color: #6688DD;
}

#adv3 p {
	color: #888888;
	font-style: italic;
	margin: 5px;
}

#adv3 p a {
	color: #CCCCCC;
	font-size: 1em;
	font-weight: bold;
	margin: 0;
	text-decoration: underline;
}

#adv3 p a:hover {
	color: #FFFFFF;
}

#message {
	background: none repeat scroll 0 0 #0D0D0D;
	border: 6px solid #222222;
	color: #FFCC44;
	font-size: 1.3em;
	height: 40px;
	line-height: 2em;
	margin: 10px auto;
	width: 240px;
}
.aui_content {
	padding: 0px 0px;
}
</style>
<script type="text/javascript">

function changeMinEnable(){
	document.getElementById("minuteLoop").checked = false;
	var checkMins = document.getElementsByName("checkBoxMin");
	for(var i=0;i<checkMins.length;i++){ 
		checkMins[i].disabled = "";
	}
}
function changeMinLoopEnable(){
	document.getElementById("minuRadio").checked = false;
	var checkMins = document.getElementsByName("checkBoxMin");
	for(var i=0;i<checkMins.length;i++){ 
		checkMins[i].disabled = "disabled";
		checkMins[i].checked = false;
	} 
}

function changeHourEnable(){
	document.getElementById("hourLoop").checked = false;
	var checkHours = document.getElementsByName("checkBoxHour");
	for(var i=0;i<checkHours.length;i++){ 
		checkHours[i].disabled = "";
	}
}
function changeHourLoopEnable(){
	document.getElementById("hourRadio").checked = false;
	var checkHours = document.getElementsByName("checkBoxHour");
	for(var i=0;i<checkHours.length;i++){ 
		checkHours[i].disabled = "disabled";
		checkHours[i].checked = false;
	} 
}

function changeDayEnable(){
	document.getElementById("dayLoop").checked = false;
	var checkDays = document.getElementsByName("checkBoxDay");
	for(var i=0;i<checkDays.length;i++){ 
		checkDays[i].disabled = "";
	}
}
function changeDayLoopEnable(){
	document.getElementById("dayRadio").checked = false;
	var checkDays = document.getElementsByName("checkBoxDay");
	for(var i=0;i<checkDays.length;i++){ 
		checkDays[i].disabled = "disabled";
		checkDays[i].checked = false;
	} 
}

function changeMonthEnable(){
	document.getElementById("monthLoop").checked = false;
	var checkMonths = document.getElementsByName("checkBoxMonth");
	for(var i=0;i<checkMonths.length;i++){ 
		checkMonths[i].disabled = "";
	}
}
function changeMonthLoopEnable(){
	document.getElementById("monthRadio").checked = false;
	var checkMonths = document.getElementsByName("checkBoxMonth");
	for(var i=0;i<checkMonths.length;i++){ 
		checkMonths[i].disabled = "disabled";
		checkMonths[i].checked = false;
	} 
}

function changeWeekEnable(){
	document.getElementById("weekLoop").checked = false;
	var checkWeeks = document.getElementsByName("checkBoxWeek");
	for(var i=0;i<checkWeeks.length;i++){ 
		checkWeeks[i].disabled = "";
	}
}
function changeWeekLoopEnable(){
	document.getElementById("weekRadio").checked = false;
	var checkWeeks = document.getElementsByName("checkBoxWeek");
	for(var i=0;i<checkWeeks.length;i++){ 
		checkWeeks[i].disabled = "disabled";
		checkWeeks[i].checked = false;
	} 
}

function useWeek(){
	var userWeekCheck = document.getElementById("useWeek").checked;
	if(userWeekCheck){
		var weekLoop = document.getElementById("weekLoop");
		var weekRadio = document.getElementById("weekRadio");
		weekRadio.disabled = ""; 
		weekLoop.checked = true;
		weekLoop.disabled = "";
		var checkWeeks = document.getElementsByName("checkBoxWeek");
		for(var i=0;i<checkWeeks.length;i++){ 
			checkWeeks[i].disabled = "";
		}
	}else{
		var weekLoop = document.getElementById("weekLoop");
		var weekRadio = document.getElementById("weekRadio");
		weekRadio.disabled = "disabled";
		weekLoop.disabled = "disabled";
		weekLoop.checked = false;
		weekRadio.checked = false;
		var checkWeeks = document.getElementsByName("checkBoxWeek");
		for(var i=0;i<checkWeeks.length;i++){ 
			checkWeeks[i].disabled = "disabled";
		}
	}
}

function createCron(){ 
	var exMin = true;
	var exHour = true;
	var exDay = true;
	var exMonth = true;
	var exWeek = true;
	$("#cronsecond").val(0); 
	var minuLoopChecked = document.getElementById("minuteLoop").checked;
	//设置分
	if(minuLoopChecked){
		exMin = false; 
		var fromMin = $("#FromMin").val();
		var toMin = $("#ToMin").val();
		//alert(fromMin+"/"+toMin);
		document.getElementById("cronminute").value = fromMin+"/"+toMin;  
		if(fromMin>toMin){
			alert("起始分钟必须小于结束分钟");
			document.getElementById("cronexpress").value = "";
		}
	}else{
		var checkedMin = "";
		var checkMins = document.getElementsByName("checkBoxMin"); 
		for(var i=0;i<checkMins.length;i++){ 
			var checkmin = checkMins[i].checked;
			if(checkmin){
				exMin = false;
				if(checkedMin == ""){
					checkedMin = checkMins[i].value;
				}else{
					checkedMin = checkedMin+","+checkMins[i].value;
				}
			}
		} 
		document.getElementById("cronminute").value = checkedMin; 
	}
	//设置小时
	var hourLoopChecked = document.getElementById("hourLoop").checked;
	if(hourLoopChecked){
		document.getElementById("cronhour").value = "*";
		exHour = false;
	}else{
		var checkedHour = "";
		var checkHours = document.getElementsByName("checkBoxHour"); 
		for(var i=0;i<checkHours.length;i++){ 
			var checkhour = checkHours[i].checked;
			if(checkhour){
				exHour = false;
				if(checkedHour == ""){
					checkedHour = checkHours[i].value;
				}else{
					checkedHour = checkedHour+","+checkHours[i].value;
				}
			}
		} 
		document.getElementById("cronhour").value = checkedHour;  
	}
	//设置天
	var useWeekCheck = document.getElementById("useWeek").checked;
	var weekLoopChecked = document.getElementById("weekLoop").checked;
	if (useWeekCheck) { 
		document.getElementById("cronday").value = "?";
		exDay = false;
		if(weekLoopChecked){
			document.getElementById("cronweek").value = "*";
			exWeek = false;
		}else{
			var checkedWeek = "";
			var checkWeeks = document.getElementsByName("checkBoxWeek"); 
			for(var i=0;i<checkWeeks.length;i++){ 
				var checkweek = checkWeeks[i].checked;
				if(checkweek){
					exWeek = false;
					if(checkedWeek == ""){
						checkedWeek = checkWeeks[i].value;
					}else{
						checkedWeek = checkedWeek+","+checkWeeks[i].value;
					}
				}
			} 
			document.getElementById("cronweek").value = checkedWeek;   
		}
	}else{
		document.getElementById("cronweek").value = "?"; 
		exWeek = false;
		var dayLoopChecked = document.getElementById("dayLoop").checked;
		if(dayLoopChecked){
			document.getElementById("cronday").value = "*";
			exDay = false;
		}else{
			var checkedDay = "";
			var checkDays = document.getElementsByName("checkBoxDay"); 
			for(var i=0;i<checkDays.length;i++){ 
				var checkday = checkDays[i].checked;
				if(checkday){
					exDay = false;
					if(checkedDay == ""){
						checkedDay = checkDays[i].value;
					}else{
						checkedDay = checkedDay+","+checkDays[i].value;
					}
				}
			} 
			document.getElementById("cronday").value = checkedDay;   
		}
	}
	//设置月
	var monthChecked = document.getElementById("monthLoop").checked;
	if(monthChecked){
		document.getElementById("cronmonth").value = "*"; 
		exMonth = false;
	}else{
		var checkedMonth = "";
			var checkMonths = document.getElementsByName("checkBoxMonth"); 
			for(var i=0;i<checkMonths.length;i++){ 
				var checkmonth = checkMonths[i].checked;
				if(checkmonth){
					exMonth = false;
					if(checkedMonth == ""){
						checkedMonth = checkMonths[i].value;
					}else{
						checkedMonth = checkedMonth+","+checkMonths[i].value;
					}
				}
			} 
			document.getElementById("cronmonth").value = checkedMonth;   
	}
	if(exMin){
		alert("分钟不允许为空");
		document.getElementById("cronexpress").value = "";
	}else if(exHour){
		alert("小时不允许为空");
		document.getElementById("cronexpress").value = "";
	}else if(exDay){
		alert("天不允许为空");
		document.getElementById("cronexpress").value = "";
	}else if(exWeek){
		alert("周不允许为空");
		document.getElementById("cronexpress").value = "";
	}else if(exMonth){
		alert("月不允许为空");
		document.getElementById("cronexpress").value = "";
	}
	var secondvalue = replaceSpaces(document.getElementById("cronsecond").value);
	var minvalue = replaceSpaces(document.getElementById("cronminute").value);
	var hourvalue = replaceSpaces(document.getElementById("cronhour").value);
	var dayvalue = replaceSpaces(document.getElementById("cronday").value);
	var monthvalue = replaceSpaces(document.getElementById("cronmonth").value);
	var weekvalue = replaceSpaces(document.getElementById("cronweek").value);
	document.getElementById("cronexpress").value = secondvalue+" "+minvalue+" "+hourvalue+" "+dayvalue+" "+monthvalue+" "+weekvalue;
	var cronStr = document.getElementById("cronexpress").value;
	<%-- CronExpressionEx exp = new CronExpressionEx(cronStr);
    jTF_Cron_Exp.setText(exp.toString());

    jTA_Schedule_Next.setText("");
    java.util.Date dd = new java.util.Date();
    jTF_Schedule_Start.setText(DateFormatUtil.format("yyyy-MM-dd HH:mm:ss", dd));
    for (int i = 1; i <= 8; i++) {
      dd = exp.getNextValidTimeAfter(dd);
      jTA_Schedule_Next.append(i + ": " + DateFormatUtil.format("yyyy-MM-dd HH:mm:ss", dd) + "\n");
      dd = new java.util.Date(dd.getTime() + 1000);
    }--%>
}
<% java.util.Date dd = new java.util.Date(); %>
function executeOrder(){
	document.getElementById("startTime").value = '<%=DateCommonUtil.format("yyyy-MM-dd HH:mm:ss", dd)%>'; 
	var str = document.getElementById("cronexpress").value; 
	$.ajaxSetup({
		cache : false
	});
	$.getJSON("${dynamicURL}/quartz/drawCron.action?cronStr=" + str,
			function call(data) {
				ifModified: true;
				cache: false,
				/* for(var i=0;i<data.executeTime.length;i++){
					document.getElementById("cronExecuteList").text.append(data.executeTime[i]+"\n");
				}  */
				//alert(data.exeTime[0]);
				$("#cronExecuteList").text("");
				$("#cronExecuteList").append(data.exeTime[0]+"<br/>");
				$("#cronExecuteList").append(data.exeTime[1]+"<br/>");	
				$("#cronExecuteList").append(data.exeTime[2]+"<br/>");
				$("#cronExecuteList").append(data.exeTime[3]+"<br/>");
				$("#cronExecuteList").append(data.exeTime[4]+"<br/>");
				$("#cronExecuteList").append(data.exeTime[5]+"<br/>");
				$("#cronExecuteList").append(data.exeTime[6]+"<br/>");
				$("#cronExecuteList").append(data.exeTime[7]+"<br/>"); 
	});
}

function replaceSpaces(str){
	while(str.indexOf(" ")!=-1){
	 str=str.replace(" ","");
	} 
	return str;
}

/* 	function test(){
	//document.getElementById("allcount").style.display = "block";
	art.dialog({
		title : '选择时间',
		content : document.getElementById('allcount'),
		esc : true,
		height : '100px',
		width : '300px',
		esc : true
	}); 
} */

function usecron(){
	document.getElementById("triggerTimeExp").value = document.getElementById("cronexpress").value;
	$("#selectCron").dialog("close");
} 

function openCronPlus(){
	$("#selectCron").dialog("open");
}
</script> 
<sj:dialog 
    	id="selectCron" 
    	autoOpen="false" 
    	modal="true" 
    	title="Cron表达式生成插件"
    	width="720"  
    	onCloseTopics="dialogclosetopic"  
    	cssStyle="background-color: #F0F0F0;"
    >
     <fieldset style="font-size: 12px;background-color: #F0F0F0;width: 690px;">
		<legend>日期,时间</legend>
		<div class="tabs"> 
		    <div class="tabmenu">
				<ul> 
					<li><a href="#oneMin">分</a></li> 
					<li><a href="#twoHour">时</a></li>
					<li><a href="#threeDay">天</a></li> 
					<li><a href="#fourMonth">月</a></li> 
					<li><a href="#fiveWeek">周</a></li>  
				</ul>
			</div>
		    	<div id="oneMin" style="border: 1px solid #1A1A1A;background-color: #F0F0F0;">
				<div style="font-size: 12px;background-color: #F0F0F0;">
					<input type="radio" name="minuteLoop" id="minuteLoop" onclick="changeMinLoopEnable()" checked="checked"/>
					周期 从
					<select id="FromMin" style="width:10px;background-color: #F0F0F0;">
						<option>0</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
						<option>6</option><option>7</option><option>8</option><option>9</option><option>10</option><option>11</option>
						<option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option>
						<option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option>
						<option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option>
						<option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option>
						<option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option>
						<option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option>
						<option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option>
						<option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option>
					</select>
					分.到
					<select id="ToMin"> 
						<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
						<option>6</option><option>7</option><option>8</option><option>9</option><option>10</option><option>11</option>
						<option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option>
						<option>18</option><option>19</option><option>20</option><option>21</option><option>22</option><option>23</option>
						<option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option>
						<option>30</option><option>31</option><option>32</option><option>33</option><option>34</option><option>35</option>
						<option>36</option><option>37</option><option>38</option><option>39</option><option>40</option><option>41</option>
						<option>42</option><option>43</option><option>44</option><option>45</option><option>46</option><option>47</option>
						<option>48</option><option>49</option><option>50</option><option>51</option><option>52</option><option>53</option>
						<option>54</option><option>55</option><option>56</option><option>57</option><option>58</option><option>59</option>
					</select>
					分一直执行
				</div>
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="minuRadio" id="minuRadio" onclick="changeMinEnable()"/>自定义</div>
				<fieldset style="margin-left: 22px;">
				<div style="background-color: #F0F0F0;">
				<table style="background-color: #F0F0F0;font-family: sans-serif;">
					<tr>
					<td nowrap="nowrap"><input type="checkbox" value="0" name="checkBoxMin" disabled="disabled"/>0</td>
					<td nowrap="nowrap"><input type="checkbox" value="1" name="checkBoxMin" disabled="disabled"/>1</td>
					<td nowrap="nowrap"><input type="checkbox" value="2" name="checkBoxMin" disabled="disabled"/>2</td>
					<td nowrap="nowrap"><input type="checkbox" value="3" name="checkBoxMin" disabled="disabled"/>3</td>
					<td nowrap="nowrap"><input type="checkbox" value="4" name="checkBoxMin" disabled="disabled"/>4</td>
					<td nowrap="nowrap"><input type="checkbox" value="5" name="checkBoxMin" disabled="disabled"/>5</td>
					<td nowrap="nowrap"><input type="checkbox" value="6" name="checkBoxMin" disabled="disabled"/>6</td>
					<td nowrap="nowrap"><input type="checkbox" value="7" name="checkBoxMin" disabled="disabled"/>7</td>
					<td nowrap="nowrap"><input type="checkbox" value="8" name="checkBoxMin" disabled="disabled"/>8</td>
					<td nowrap="nowrap"><input type="checkbox" value="9" name="checkBoxMin" disabled="disabled"/>9</td>
					<td nowrap="nowrap"><input type="checkbox" value="10" name="checkBoxMin" disabled="disabled"/>10</td>
					<td nowrap="nowrap"><input type="checkbox" value="11" name="checkBoxMin" disabled="disabled"/>11</td>
					<td nowrap="nowrap"><input type="checkbox" value="12" name="checkBoxMin" disabled="disabled"/>12</td>
					<td nowrap="nowrap"><input type="checkbox" value="13" name="checkBoxMin" disabled="disabled"/>13</td>
					<td nowrap="nowrap"><input type="checkbox" value="14" name="checkBoxMin" disabled="disabled"/>14</td> 
					</tr>
					<tr>
					<td nowrap="nowrap"><input type="checkbox" value="15" name="checkBoxMin" disabled="disabled"/>15</td>
					<td nowrap="nowrap"><input type="checkbox" value="16" name="checkBoxMin" disabled="disabled"/>16</td>
					<td nowrap="nowrap"><input type="checkbox" value="17" name="checkBoxMin" disabled="disabled"/>17</td>
					<td nowrap="nowrap"><input type="checkbox" value="18" name="checkBoxMin" disabled="disabled"/>18</td>
					<td nowrap="nowrap"><input type="checkbox" value="19" name="checkBoxMin" disabled="disabled"/>19</td>
					<td nowrap="nowrap"><input type="checkbox" value="20" name="checkBoxMin" disabled="disabled"/>20</td>
					<td nowrap="nowrap"><input type="checkbox" value="21" name="checkBoxMin" disabled="disabled"/>21</td>
					<td nowrap="nowrap"><input type="checkbox" value="22" name="checkBoxMin" disabled="disabled"/>22</td>
					<td nowrap="nowrap"><input type="checkbox" value="23" name="checkBoxMin" disabled="disabled"/>23</td>
					<td nowrap="nowrap"><input type="checkbox" value="24" name="checkBoxMin" disabled="disabled"/>24</td>
					<td nowrap="nowrap"><input type="checkbox" value="25" name="checkBoxMin" disabled="disabled"/>25</td>
					<td nowrap="nowrap"><input type="checkbox" value="26" name="checkBoxMin" disabled="disabled"/>26</td>
					<td nowrap="nowrap"><input type="checkbox" value="27" name="checkBoxMin" disabled="disabled"/>27</td>
					<td nowrap="nowrap"><input type="checkbox" value="28" name="checkBoxMin" disabled="disabled"/>28</td>
					<td nowrap="nowrap"><input type="checkbox" value="29" name="checkBoxMin" disabled="disabled"/>29</td> 
					</tr>
					<tr>
					<td nowrap="nowrap"><input type="checkbox" value="30" name="checkBoxMin" disabled="disabled"/>30</td>
					<td nowrap="nowrap"><input type="checkbox" value="31" name="checkBoxMin" disabled="disabled"/>31</td>
					<td nowrap="nowrap"><input type="checkbox" value="32" name="checkBoxMin" disabled="disabled"/>32</td>
					<td nowrap="nowrap"><input type="checkbox" value="33" name="checkBoxMin" disabled="disabled"/>33</td>
					<td nowrap="nowrap"><input type="checkbox" value="34" name="checkBoxMin" disabled="disabled"/>34</td>
					<td nowrap="nowrap"><input type="checkbox" value="35" name="checkBoxMin" disabled="disabled"/>35</td>
					<td nowrap="nowrap"><input type="checkbox" value="36" name="checkBoxMin" disabled="disabled"/>36</td>
					<td nowrap="nowrap"><input type="checkbox" value="37" name="checkBoxMin" disabled="disabled"/>37</td>
					<td nowrap="nowrap"><input type="checkbox" value="38" name="checkBoxMin" disabled="disabled"/>38</td>
					<td nowrap="nowrap"><input type="checkbox" value="39" name="checkBoxMin" disabled="disabled"/>39</td>
					<td nowrap="nowrap"><input type="checkbox" value="40" name="checkBoxMin" disabled="disabled"/>40</td>
					<td nowrap="nowrap"><input type="checkbox" value="41" name="checkBoxMin" disabled="disabled"/>41</td>
					<td nowrap="nowrap"><input type="checkbox" value="42" name="checkBoxMin" disabled="disabled"/>42</td>
					<td nowrap="nowrap"><input type="checkbox" value="43" name="checkBoxMin" disabled="disabled"/>43</td>
					<td nowrap="nowrap"><input type="checkbox" value="44" name="checkBoxMin" disabled="disabled"/>44</td> 
					</tr>
					<tr>
					<td nowrap="nowrap"><input type="checkbox" value="45" name="checkBoxMin" disabled="disabled"/>45</td>
					<td nowrap="nowrap"><input type="checkbox" value="46" name="checkBoxMin" disabled="disabled"/>46</td>
					<td nowrap="nowrap"><input type="checkbox" value="47" name="checkBoxMin" disabled="disabled"/>47</td>
					<td nowrap="nowrap"><input type="checkbox" value="48" name="checkBoxMin" disabled="disabled"/>48</td>
					<td nowrap="nowrap"><input type="checkbox" value="49" name="checkBoxMin" disabled="disabled"/>49</td>
					<td nowrap="nowrap"><input type="checkbox" value="50" name="checkBoxMin" disabled="disabled"/>50</td>
					<td nowrap="nowrap"><input type="checkbox" value="51" name="checkBoxMin" disabled="disabled"/>51</td>
					<td nowrap="nowrap"><input type="checkbox" value="52" name="checkBoxMin" disabled="disabled"/>52</td>
					<td nowrap="nowrap"><input type="checkbox" value="53" name="checkBoxMin" disabled="disabled"/>53</td>
					<td nowrap="nowrap"><input type="checkbox" value="54" name="checkBoxMin" disabled="disabled"/>54</td>
					<td nowrap="nowrap"><input type="checkbox" value="55" name="checkBoxMin" disabled="disabled"/>55</td>
					<td nowrap="nowrap"><input type="checkbox" value="56" name="checkBoxMin" disabled="disabled"/>56</td>
					<td nowrap="nowrap"><input type="checkbox" value="57" name="checkBoxMin" disabled="disabled"/>57</td>
					<td nowrap="nowrap"><input type="checkbox" value="58" name="checkBoxMin" disabled="disabled"/>58</td>
					<td nowrap="nowrap"><input type="checkbox" value="59" name="checkBoxMin" disabled="disabled"/>59</td> 
					</tr>
				</table> 
				</div>
				</fieldset>
				</div>
				<div id="twoHour" style="border: 1px solid #1A1A1A;display: inherit;background-color: #F0F0F0;">
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="hourLoop" id="hourLoop" onclick="changeHourLoopEnable()" checked="checked"/>每小时</div>
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="hourRadio" id="hourRadio" onclick="changeHourEnable()"/>自定义</div>
				<fieldset style="margin-left: 22px;">
				<div style="font-size: 12px;background-color: #F0F0F0;font-family: sans-serif;">
				<table style="width: 600px;">
					<tr>
					<td>AM</td>
					<td nowrap="nowrap"><input type="checkbox" value="0" name="checkBoxHour" disabled="disabled"/>0</td>
					<td nowrap="nowrap"><input type="checkbox" value="1" name="checkBoxHour" disabled="disabled"/>1</td>
					<td nowrap="nowrap"><input type="checkbox" value="2" name="checkBoxHour" disabled="disabled"/>2</td>
					<td nowrap="nowrap"><input type="checkbox" value="3" name="checkBoxHour" disabled="disabled"/>3</td>
					<td nowrap="nowrap"><input type="checkbox" value="4" name="checkBoxHour" disabled="disabled"/>4</td>
					<td nowrap="nowrap"><input type="checkbox" value="5" name="checkBoxHour" disabled="disabled"/>5</td>
					<td nowrap="nowrap"><input type="checkbox" value="6" name="checkBoxHour" disabled="disabled"/>6</td>
					<td nowrap="nowrap"><input type="checkbox" value="7" name="checkBoxHour" disabled="disabled"/>7</td>
					<td nowrap="nowrap"><input type="checkbox" value="8" name="checkBoxHour" disabled="disabled"/>8</td>
					<td nowrap="nowrap"><input type="checkbox" value="9" name="checkBoxHour" disabled="disabled"/>9</td>
					<td nowrap="nowrap"><input type="checkbox" value="10" name="checkBoxHour" disabled="disabled"/>10</td>
					<td nowrap="nowrap"><input type="checkbox" value="11" name="checkBoxHour" disabled="disabled"/>11</td> 
					</tr>
					<tr>
					<td>PM</td>
					<td nowrap="nowrap"><input type="checkbox" value="12" name="checkBoxHour" disabled="disabled"/>12</td>
					<td nowrap="nowrap"><input type="checkbox" value="13" name="checkBoxHour" disabled="disabled"/>13</td>
					<td nowrap="nowrap"><input type="checkbox" value="14" name="checkBoxHour" disabled="disabled"/>14</td>
					<td nowrap="nowrap"><input type="checkbox" value="15" name="checkBoxHour" disabled="disabled"/>15</td>
					<td nowrap="nowrap"><input type="checkbox" value="16" name="checkBoxHour" disabled="disabled"/>16</td>
					<td nowrap="nowrap"><input type="checkbox" value="17" name="checkBoxHour" disabled="disabled"/>17</td>
					<td nowrap="nowrap"><input type="checkbox" value="18" name="checkBoxHour" disabled="disabled"/>18</td>
					<td nowrap="nowrap"><input type="checkbox" value="19" name="checkBoxHour" disabled="disabled"/>19</td>
					<td nowrap="nowrap"><input type="checkbox" value="20" name="checkBoxHour" disabled="disabled"/>20</td>
					<td nowrap="nowrap"><input type="checkbox" value="21" name="checkBoxHour" disabled="disabled"/>21</td>
					<td nowrap="nowrap"><input type="checkbox" value="22" name="checkBoxHour" disabled="disabled"/>22</td>
					<td nowrap="nowrap"><input type="checkbox" value="23" name="checkBoxHour" disabled="disabled"/>23</td> 
					</tr> 
				</table> 
				</div>
				</fieldset>
			</div>
			<div id="threeDay" style="border: 1px solid #1A1A1A;background-color: #F0F0F0;">
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="dayLoop" id="dayLoop" onclick="changeDayLoopEnable()" checked="checked"/>每天</div>
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="dayRadio" id="dayRadio" onclick="changeDayEnable()"/>自定义</div>
				<fieldset style="margin-left: 22px;background-color: #F0F0F0;">
				<div style="font-size: 12px;background-color: #F0F0F0;font-family: sans-serif;">
				<table>
					<tr> 
					<td nowrap="nowrap"><input type="checkbox" value="1" name="checkBoxDay" disabled="disabled"/>1</td>
					<td nowrap="nowrap"><input type="checkbox" value="2" name="checkBoxDay" disabled="disabled"/>2</td>
					<td nowrap="nowrap"><input type="checkbox" value="3" name="checkBoxDay" disabled="disabled"/>3</td>
					<td nowrap="nowrap"><input type="checkbox" value="4" name="checkBoxDay" disabled="disabled"/>4</td>
					<td nowrap="nowrap"><input type="checkbox" value="5" name="checkBoxDay" disabled="disabled"/>5</td>
					<td nowrap="nowrap"><input type="checkbox" value="6" name="checkBoxDay" disabled="disabled"/>6</td>
					<td nowrap="nowrap"><input type="checkbox" value="7" name="checkBoxDay" disabled="disabled"/>7</td>
					<td nowrap="nowrap"><input type="checkbox" value="8" name="checkBoxDay" disabled="disabled"/>8</td>
					<td nowrap="nowrap"><input type="checkbox" value="9" name="checkBoxDay" disabled="disabled"/>9</td>
					<td nowrap="nowrap"><input type="checkbox" value="10" name="checkBoxDay" disabled="disabled"/>10</td>
					<td nowrap="nowrap"><input type="checkbox" value="11" name="checkBoxDay" disabled="disabled"/>11</td> 
					<td nowrap="nowrap"><input type="checkbox" value="12" name="checkBoxDay" disabled="disabled"/>12</td>
					<td nowrap="nowrap"><input type="checkbox" value="13" name="checkBoxDay" disabled="disabled"/>13</td>
					<td nowrap="nowrap"><input type="checkbox" value="14" name="checkBoxDay" disabled="disabled"/>14</td>
					<td nowrap="nowrap"><input type="checkbox" value="15" name="checkBoxDay" disabled="disabled"/>15</td>
					</tr>
					<tr> 
					<td nowrap="nowrap"><input type="checkbox" value="16" name="checkBoxDay" disabled="disabled"/>16</td>
					<td nowrap="nowrap"><input type="checkbox" value="17" name="checkBoxDay" disabled="disabled"/>17</td>
					<td nowrap="nowrap"><input type="checkbox" value="18" name="checkBoxDay" disabled="disabled"/>18</td>
					<td nowrap="nowrap"><input type="checkbox" value="19" name="checkBoxDay" disabled="disabled"/>19</td>
					<td nowrap="nowrap"><input type="checkbox" value="20" name="checkBoxDay" disabled="disabled"/>20</td>
					<td nowrap="nowrap"><input type="checkbox" value="21" name="checkBoxDay" disabled="disabled"/>21</td>
					<td nowrap="nowrap"><input type="checkbox" value="22" name="checkBoxDay" disabled="disabled"/>22</td>
					<td nowrap="nowrap"><input type="checkbox" value="23" name="checkBoxDay" disabled="disabled"/>23</td> 
					<td nowrap="nowrap"><input type="checkbox" value="24" name="checkBoxDay" disabled="disabled"/>24</td> 
					<td nowrap="nowrap"><input type="checkbox" value="25" name="checkBoxDay" disabled="disabled"/>25</td> 
					<td nowrap="nowrap"><input type="checkbox" value="26" name="checkBoxDay" disabled="disabled"/>26</td> 
					<td nowrap="nowrap"><input type="checkbox" value="27" name="checkBoxDay" disabled="disabled"/>27</td> 
					<td nowrap="nowrap"><input type="checkbox" value="28" name="checkBoxDay" disabled="disabled"/>28</td> 
					<td nowrap="nowrap"><input type="checkbox" value="29" name="checkBoxDay" disabled="disabled"/>29</td> 
					<td nowrap="nowrap"><input type="checkbox" value="30" name="checkBoxDay" disabled="disabled"/>30</td> 
					</tr> 
					<tr>
					<td colspan="15"><input type="checkbox" value="" name="checkBoxDay" disabled="disabled"/>31</td> 
					</tr> 
				</table> 
				</div>
				</fieldset>
			</div>
			<div id="fourMonth" style="border: 1px solid #1A1A1A;background-color: #F0F0F0;">
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="monthLoop" id="monthLoop" onclick="changeMonthLoopEnable()" checked="checked"/>每月</div>
				<div style="font-size: 12px;background-color: #F0F0F0;">
				<input type="radio" name="monthRadio" id="monthRadio" onclick="changeMonthEnable()"/>自定义</div>
				<fieldset style="margin-left: 22px;background-color: #F0F0F0;">
				<div style="font-size: 12px;background-color: #F0F0F0;font-family: sans-serif;">
				<table>
					<tr> 
					<td><input type="checkbox" value="1" name="checkBoxMonth" disabled="disabled"/>1</td>
					<td><input type="checkbox" value="2" name="checkBoxMonth" disabled="disabled"/>2</td>
					<td><input type="checkbox" value="3" name="checkBoxMonth" disabled="disabled"/>3</td>
					<td><input type="checkbox" value="4" name="checkBoxMonth" disabled="disabled"/>4</td>
					<td><input type="checkbox" value="5" name="checkBoxMonth" disabled="disabled"/>5</td>
					<td><input type="checkbox" value="6" name="checkBoxMonth" disabled="disabled"/>6</td>
					<td><input type="checkbox" value="7" name="checkBoxMonth" disabled="disabled"/>7</td>
					<td><input type="checkbox" value="8" name="checkBoxMonth" disabled="disabled"/>8</td>
					<td><input type="checkbox" value="9" name="checkBoxMonth" disabled="disabled"/>9</td>
					<td><input type="checkbox" value="10" name="checkBoxMonth" disabled="disabled"/>10</td>
					<td><input type="checkbox" value="11" name="checkBoxMonth" disabled="disabled"/>11</td> 
					<td><input type="checkbox" value="12" name="checkBoxMonth" disabled="disabled"/>12</td> 
					</tr>  
				</table> 
				</div>
				</fieldset>
			</div>
			<div id="fiveWeek" style="border: 1px solid #1A1A1A;background-color: #F0F0F0;"> 
			<div style="font-size：12px;background-color: #F0F0F0;"><input type="checkbox" name="useWeek" id="useWeek" onclick="useWeek()"/>使用周</div>
			<div style="font-size：12px;background-color: #F0F0F0;border: 1px solid #1A1A1A">
				<div style="font-size：12px;background-color: #F0F0F0;">
						<input type="radio" name="weekLoop" id="weekLoop" onclick="changeWeekLoopEnable()" disabled="disabled"/>每周
				</div>
				<div style="font-size：12px;background-color: #F0F0F0;">
						<input type="radio" name="weekRadio" id="weekRadio" onclick="changeWeekEnable()" disabled="disabled"/>自定义
				</div>
					<fieldset style="margin-left: 22px;">
				<div style="font-size：12px;background-color: #F0F0F0;">
				<table>
					<tr> 
					<td><input type="checkbox" value="1" name="checkBoxWeek" disabled="disabled"/>周日</td>
					<td><input type="checkbox" value="2" name="checkBoxWeek" disabled="disabled"/>周一</td>
					<td><input type="checkbox" value="3" name="checkBoxWeek" disabled="disabled"/>周二</td>
					<td><input type="checkbox" value="4" name="checkBoxWeek" disabled="disabled"/>周三</td>
					<td><input type="checkbox" value="5" name="checkBoxWeek" disabled="disabled"/>周四</td>
					<td><input type="checkbox" value="6" name="checkBoxWeek" disabled="disabled"/>周五</td>
					<td><input type="checkbox" value="7" name="checkBoxWeek" disabled="disabled"/>周六</td>  
					</tr>  
				</table> 
				</div> 
				</fieldset>
			</div>
			</div>
			</div>
				</fieldset> 
		
		<!-- CORN表达式 -->
	<fieldset style="font-size: 12px;margin-top: 20px;width: 600px;background-color: #F0F0F0;">
		<legend>表达式</legend>
		<div>
			<table width="100%" border="0" style="font-size:12px;padding-left:30px; ">
				<tbody>
					<tr>
						<td nowrap="nowrap" rowspan="2" style="vertical-align: bottom;">
							字段:
						</td>
						<td>
							秒
						</td>
						<td>
							分
						</td>
						<td>
							时
						</td>
						<td>
							天
						</td>
						<td>
							月
						</td>
						<td>
							周
						</td>
					</tr>
					<tr> 
						<td>
							<input id="cronsecond" type="text" style="width: 70px;background-color: #F0F0F0;border-style: groove"/>
						</td>
						<td>
							<input id="cronminute" type="text" style="width: 70px;background-color: #F0F0F0;border-style: groove"/>
						</td>
						<td>
							<input id="cronhour" type="text" style="width: 70px;background-color: #F0F0F0;border-style: groove"/>
						</td>
						<td>
							<input id="cronday" type="text" style="width: 70px;background-color: #F0F0F0;border-style: groove"/>
						</td>
						<td>
							<input id="cronmonth" type="text" style="width: 70px;background-color: #F0F0F0;border-style: groove"/>
						</td>
						<td>
							<input id="cronweek" type="text" style="width: 70px;background-color: #F0F0F0;border-style: groove"/>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap">cron表达式</td>
						<td colspan="5"><input id="cronexpress" type="text" style="width: 99%;background-color: #F0F0F0;border-style: groove"/></td>
						<td> <input type="button" style="" value="查看时间顺序" onclick="executeOrder()"/></td>
					</tr>
				</tbody>
			</table>
			<fieldset style="font-size: 12px;">
				<legend>计划执行时间</legend>
					<table style="width: 100%">
						<tr>
							<td style="width: 15%">
								开始时间:
							</td>
							<td><input id="startTime" type="text" style="width: 95%;background-color: #F0F0F0;border-style: groove"/></td>
						</tr>
						<tr>
							<td style="width: 15%">
								执行时间列表:
							</td>
							<td>
								<div id="cronExecuteList" style="height:70px; width: 95%;overflow-y: scroll;background-color: #F0F0F0;border-style: groove"></div>
							</td>
						</tr>
					</table>
				</fieldset>
		</div> 
		<input type="button" value="生成cron" onclick="createCron()"/>
		<input type="button" value="确定" onclick="usecron()"/>
		</fieldset> 
    	
    </sj:dialog>