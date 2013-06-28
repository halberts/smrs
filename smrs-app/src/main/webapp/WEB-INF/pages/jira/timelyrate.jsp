<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="p" uri="/pagination-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link type="text/css" rel="stylesheet"
	href="${staticURL}/style/jquery.multiselect.css" />
<script src="${staticURL}/../scripts/jquery.multiselect.min.js"></script>
<script src="${staticURL}/scripts/jquery-ui-1.8.1.custom.min.js"></script>
<script src="${staticURL}/../scripts/jquery.ui.datepicker.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="breadcrumbs">
		<div>
			<div>
				<ul>
					<li class="first"></li>
					<li><a href="#">首页</a></li>
					<li><a href="#">jira项目监控</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section" id="cretial">
		<div class="box">
			<div class="title">
				jira项目监控 <span class="hide"></span>
			</div>
			<div class="content nopadding">
				<div class="tabs">
					<div class="tabmenu">
						<ul>
							<li><a href="#tabs-1" onclick="tabOnclick('tabs-1');">开发及时率</a></li>
							<li><a href="#tabs-2" onclick="tabOnclick('tabs-2');">开发管理</a></li>
							<li><a href="#tabs-3" onclick="tabOnclick('tabs-3');">用户满意度</a></li>
							<li><a href="#tabs-4" onclick="tabOnclick('tabs-4');">开发质量</a></li>
							<li><a href="#tabs-5" onclick="tabOnclick('tabs-5');">开发人天</a></li>
							<li><a href="#tabs-9" onclick="tabOnclick('tabs-9');">评测报告</a></li>
						</ul>
					</div>
					<div class="tab" id="tabs-1">
						<table id="TimelyRatetabs-1" class="sorting">
							<thead>
								<tr>
									<th>经办人</th>
									<th>完成量</th>
									<th>提报人天</th>
									<th>拖期需求</th>
									<th>拖期需求提报人天</th>
									<th>拖期天数</th>
									<th>拖期率(%)</th>
									<th>人天单价</th>
									<th>扣减额</th>
								</tr>
							</thead>
							<tbody>
								<!-- 数据行 -->
								<s:set var="sumCol" value="getSumByName()" />
								<s:set var="jlprice" value="adminPrice"/>
								<s:set var="Timelysum" value="0"/>
								<s:set var="Timelypricesum" value="0"/>
								<s:iterator value="pager.records" 
									status="status">
									<tr>
										<td title="<s:property value="dispalyName"/>"><s:property
												value="dispalyName" /></td>
										<td title="<s:property value="jjcount"/>"><s:property
												value="jjcount" /></td>
										<td title="<s:property value="formatDouble(timeworked)"/>"><s:property
												value="formatDouble(timeworked)" /></td>
										<td title="<s:property value="yqcount"/>"><s:property
												value="yqcount" /></td>
										<td title="<s:property value="formatDouble(yqtimeworked)"/>"><s:property
												value="formatDouble(yqtimeworked)" /></td>
										<td title="<s:property value="tqday"/>"><s:property
												value="tqday" /></td>
										<td title="<s:property value="tql"/>"><s:property
												value="formatDouble(tql)" />%</td>
										<td title="<s:property value="price"/>"><s:property
												value="price" /></td>
										<s:set value="#Timelypricesum=#Timelypricesum+price"/>
										<s:set value="#Timelysum=#Timelysum+jskjl(tql,yqtimeworked,tqday)"/>
										<td
											title="<s:property value="formatDouble(jskjl(tql,yqtimeworked,tqday))"/>"><s:property
												value="formatDouble(jskjl(tql,yqtimeworked,tqday))" /></td>
									</tr>
								</s:iterator>
							</tbody>
							<tfoot>
								<tr>
									<td>合计：</td>
									<td><s:property value="sumColModel.sumjjcount" /></td>
									<td><s:property value="formatDouble(sumColModel.sumtimeworked)" /></td>
									<td><s:property value="sumColModel.sumyqcount" /></td>
									<td><s:property value="formatDouble(sumColModel.sumyqtimeworked)" /></td>
									<td><s:property value="formatDouble(sumColModel.sumtqday)" /></td>
									<td><s:property value="formatDouble(sumColModel.sumtql)" />%</td>
									<td><s:property value="formatDouble(#Timelypricesum)" /></td>
									<td><s:property value="formatDouble(#Timelysum)" /></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab" id="tabs-2">
						<table id="TimelyRate" class="sorting">
							<thead>
								<tr>
									<th>序号</th>
									<th>问题类型</th>
									<th>次数</th>
									<th>人天均价</th>
									<th>扣减额</th>
								</tr>
							</thead>
							<tbody>
							
								<!-- 数据行 -->
								<tr>
									<td>1</td>
									<td>违规需求</td>
									<td><s:property value="ManagermentModel.wgsuecount" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="wgtotal" value="ManagermentModel.wgsuecount*#jlprice"/>
									<td><s:property value="#wgtotal"/>
									</td>
								</tr>
								<tr>
									<td>2</td>
									<td>无需求文档</td>
									<td><s:property value="ManagermentModel.lsuedoczcount" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="lstotal" value="ManagermentModel.lsuedoczcount*groupPrice*0.5"/>
									<td><s:property value="#lstotal"/>
									</td>
								</tr>
								<tr>
									<td>3</td>
									<td>需求文档未及时更新</td>
									<td><s:property value="sysPingceModel.xuqWupdate" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="lxtotal" value="formatDouble(sysPingceModel.xuqWupdate*groupPrice*0.5)"/>
									<td><s:property value="#lxtotal"/>
									</td>
								</tr>
								<tr>
									<td>4</td>
									<td>无技术文档</td>
									<td><s:property value="ManagermentModel.ltecdoczcount" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="lttotal" value="formatDouble(ManagermentModel.ltecdoczcount*groupPrice)"/>
									<td><s:property value="#lttotal"/></td>
								</tr>
								<tr>
									<td>5</td>
									<td>技术文档未及时更新</td>
									<td><s:property value="sysPingceModel.sjiWupdate" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="ljtotal" value="formatDouble(sysPingceModel.sjiWupdate*groupPrice)"/>
									<td><s:property value="#ljtotal"/></td>
								</tr>
								<tr>
									<td>6</td>
									<td>平台规范</td>
									<td><s:property value="ManagermentModel.lplatform" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="lptotal" value="formatDouble(ManagermentModel.lplatform*groupPrice*0.1)"/>
									<td><s:property value="#lptotal" /></td>
								</tr>
								<tr>
									<td>7</td>
									<td>发版管理规定</td>
									<td><s:property value="ManagermentModel.lvezcount" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="lvtotal" value="formatDouble(ManagermentModel.lvezcount*groupPrice*0.5)"/>
									<td><s:property value="#lvtotal" /></td>
								</tr>
								<tr>
									<td>8</td>
									<td>未提交版本数</td>
									<td><s:property value="sysPingceModel.daimWupdate" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="vetotal" value="formatDouble(sysPingceModel.daimWupdate*#jlprice)"/>
									<td><s:property value="#vetotal" /></td>
								</tr>
								<tr>
									<td>9</td>
									<td>未提交代码</td>
									<td><s:property value="sysPingceModel.DaimweiTijiao" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="ldtotal" value="sysPingceModel.DaimweiTijiao*1500"/>
									<td><s:property value="#ldtotal" /></td>
								</tr>
								<tr>
									<td>10</td>
									<td>工作日志、文档等不合规</td>
									<td><s:property value="sysPingceModel.workBgf" /></td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="lctotal" value="sysPingceModel.workBgf*#jlprice*0.3"/>
									<td><s:property value="#lctotal" /></td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td>合计：</td>
									<td> </td>
									<td> </td>
									<td> </td>
									<s:set name="kfsum" value="#lxtotal*1.0+#wgtotal*1.0+#lstotal*1.0+#lttotal*1.0+#lptotal*1.0+#lvtotal*1.0+#vetotal*1.0+#ldtotal*1.0+#lctotal*1.0+#ljtotal*1.0"/>
									<td><s:property value="#kfsum" /></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab" id="tabs-3">
						<table id="TimelyRatetabs-3" class="sorting">
							<thead>
								<tr>
									<th>序号</th>
									<th>经办人</th>
									<th>满意度低于80的需求个数</th>
									<th>投诉次数</th>
									<th>表扬次数</th>
									<th>经办人人天单价</th>
									<th>扣减额</th>
								</tr>
							</thead>
							<tbody>
								<!-- 数据行 -->
								<s:set name="no" value="1"/>
								<s:set name = "sasum" value="0"/>
								<s:set name = "sakje" value="0"/>
								<s:iterator value="pagerSatis.records" 
									status="status">
									<tr>
										<td ><s:property value="#no" /></td>
										<td title="<s:property value="displayname"/>"><s:property
												value="displayname" /></td>
										<td title="<s:property value="lessthan80"/>"><s:property
												value="lessthan80" /></td>
										<td title="<s:property value="sysPingceModel.touS"/>"><s:property
												value="sysPingceModel.touS" /></td>
										<td title="<s:property value="sysPingceModel.biaoY"/>"><s:property
												value="sysPingceModel.biaoY" /></td>
										<td title="<s:property value="price"/>"><s:property
												value="price" /></td>
										<s:set value="#sakje=lessthan80*price+sysPingceModel.touS*price-sysPingceModel.biaoY*1*price"/>
										<td><s:property value="formatDouble(#sakje)"/></td>
									</tr>
									<s:set value="#no=#no+1"/>
									<s:set value="#sasum=#sasum+#sakje"/>
								</s:iterator>
							</tbody>
							<tfoot>
								<tr>
									<td>合计：</td>
									<td> </td>
									<td> </td>
									<td> </td>
									<td></td>
									<td> </td>
									<td><s:property value="#sasum"/></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab" id="tabs-4">
						<table  class="sorting">
							<thead>
								<tr>
									<th>序号</th>
									<th>问题类型</th>
									<th>次数</th>
									<th>人天均价</th>
									<th>项目经理人天价格</th>
									<th>扣减额</th>
								</tr>
							</thead>
							<tbody>
							
								<!-- 数据行 -->
								<tr>
									<td>1</td>
									<td>系统宕机或业务无法执行
									<td><s:property value="sysPingceModel.dangJ" /></td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
									<s:set name="pqdtotal" value="sysPingceModel.dangJ*5000"/>
									<td><s:property value="#pqdtotal"/>
									</td>
								</tr>
								<tr>
									<td>2</td>
									<td>BUG</td>
									<td><s:property value="pagerQuality.bugcount" /></td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
									<s:set name="pqbtotal" value="pagerQuality.bugcount*groupPrice+pagerQuality.bugcount*adminPrice*0.5"/>
									<td><s:property value="#pqbtotal"/>
									</td>
								</tr>
								<tr>
									<td>3</td>
									<td>Error数</td>
									<td><s:property value="pagerQuality.errorcount" /></td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
										<s:set name = "errcount" value="0"/>
										<s:if test="pagerQuality.errorcount>30">
											<s:set value = "#errcount =pagerQuality.errorcount-30 "/>
										</s:if>
									<s:set name="pqetotal" value="#errcount*0.5*groupPrice"/>
									<td><s:property value="#pqetotal"/>
									</td>
								</tr>
								<tr>
									<td>4</td>
									<td>Warning数</td>
									<td><s:property value="pagerQuality.warningcount" /></td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
										<s:set name = "warcount" value="0"/>
										<s:if test="pagerQuality.warningcount>30">
											<s:set value = "#warcount =pagerQuality.warningcount-30 "/>
										</s:if>
									<s:set name="pqwtotal" value="#warcount*0.3*groupPrice"/>
									<td><s:property value="#pqwtotal"/></td>
								</tr>
								<tr>
									<td>5</td>
									<td>Info数</td>
									<td><s:property value="pagerQuality.infocount" /></td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
										<s:set name = "infcount" value="0"/>
										<s:if test="pagerQuality.infocount>30">
											<s:set value = "#infcount =pagerQuality.infocount-30 "/>
										</s:if>
										<s:set name="pqitotal" value="#infcount*0.1*groupPrice"/>
									<td><s:property value="#pqitotal"/></td>
								</tr>
								<tr>
									<td>6</td>
									<td>failure数</td>
									<td><s:property value="pagerQuality.failurecount" /></td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
										<s:set name = "faicount" value="0"/>
											<s:if test="pagerQuality.failurecount>30">
												<s:set value = "#faicount =pagerQuality.failurecount-30 "/>
											</s:if>
									<s:set name="pqftotal" value="#faicount*0.2*groupPrice"/>
									<td><s:property value="#pqftotal" /></td>
								</tr>
								<tr>
									<td>7</td>
									<td>单元测试覆盖率低于60%</td>
									<td>0</td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
									<td>0.0</td>
								</tr>
								<tr>
									<td>8</td>
									<td>未能构建成功</td>
									<td>0</td>
									<td><s:property value="groupPrice" /></td>
									<td><s:property value="adminPrice" /></td>
									<td>0.0</td>
								</tr>
								<tr>
									<td>9</td>
									<td>优秀项目</td>
									<td>0</td>
									<td><s:property value="groupPrice" /></td>
									<s:set name="ldtotal" value="10*1500"/>
									<td><s:property value="adminPrice" /></td>
									<td>0.0</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td>合计：</td>
									<td> </td>
									<td> </td>
									<td> </td>
									<td> </td>
									<s:set name="bugsum" value="#pqdtotal*1.0+#pqbtotal*1.0+#pqetotal*1.0+#pqwtotal*1.0+#pqitotal*1.0+#pqftotal*1.0"/>
									<td><s:property value="#bugsum" /></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab" id="tabs-5">
						<table id="TimelyRate" class="sorting">
							<thead>
								<tr>
									<th>序号</th>
									<th>经办人</th>
									<th>需求数量</th>
									<th>提报人天</th>
									<th>合同人天</th>
									<th>请假天数</th>
									<th>未申请请假天数</th>
									<th>人天价格</th>
									<th>项目经理人天价格</th>
									<th>扣减额</th>
								</tr>
							</thead>
							<tbody>
								<!-- 数据行 -->
								<s:set name="no" value="1"/>
								<s:set name="kfsum" value="0"/>
								<s:iterator value="pagerTimeWorked.records" 
									status="status">
									<tr>	
										<td><s:property value="#no" /></td>
										<td><s:property value="dispalyname"/></td>
										<td style="display:none;"><s:property value="id"/></td>
										<td><s:property value="suecount"/></td>
										<td><s:property value="submitdayscount"/></td>
										<td><s:property value="contractdayscount"/></td>
										<s:set value="checkKqDays(id,contractdayscount)"/>
										<td><s:property value="timeWorkedModel.vacationcount"/></td>
										<td><s:property value="timeWorkedModel.neglectworkcount"/></td>
										<td><s:property value="price"/></td>
										<td><s:property value="#jlprice"/></td>
										<s:set name="kfkje" value="neglectworkcount*2*price+neglectworkcount*2*adminPrice"/>
										<s:if test="timeWorkedModel.neglectworkcount>0">
											<s:set value = "#kfkje=#kfkje+timeWorkedModel.neglectworkcount*price"/>
										</s:if>
										<td><s:property value="#kfkje"/></td>
										<!--  计算逻辑：	未申请请假天数×2×人天价格+未申请请假天数×2×项目经理人天价格+
	同时如果提报人天小于合同人天，（合同人天-提报人天）×人天价格-->
									<!-- 	<td><s:property value="checkKqDays(id,contractdayscount)"/></td> -->
									</tr>
									<s:set value="#no=#no+1"/>
									<s:set value="#kfsum=#kfsum+#kfkje" />
								</s:iterator>
							</tbody>
							<tfoot>
								<tr>
									<td>合计：</td>
									<td> </td>
									<td> </td>
									<td> </td>
									<td></td>
									<td> </td>
									<td></td>
									<td></td>
									<td> </td>
									<td><s:property value="#kfsum"/></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab" id="tabs-9">
					<s:set name=""/>
						<table id="TimelyRatetabs-9" class="sorting">
								<thead>
									<tr>
										<th colspan="4">海尔集团信息系统开发SLA
												阶段测评报告</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td >测评系统全称：</td>
										<td  ><s:property value="jiraUserGroup.projectkey" /></td>
										<td>测评系统简称（UMP）：</td>
										<td  ><s:property value="jiraUserGroup.projectname" /></td>
									</tr>
									<tr>
										<td >供应商负责人：</td>
										<td ><s:property value="jiraUserGroup.displayname" /></td>
										<td>PSI项目经理：</td>
										<td></td>
									</tr>
									<tr>
										<td >测评周期：</td>
										<td ><s:property value="searchstart" /></td>
										<td>至</td>
										<td><s:property value="searchend" /></td>
									</tr>
									<tr>
										<td >测评时间：</td>
										<td colspan='3'> <s:property value="sysdate" /></td>
									</tr>
									<tr>
										<td >测评结果：</td>
										<td  colspan='3'  ></td>
									</tr>
									<tr>
										<td >开发及时率扣款额（元）：</td>
										<td  colspan='3'  ><s:property value="formatDouble(#Timelysum)" /></td>
									</tr>
									<tr>
										<td >开发管理扣款额（元）：</td>
										<s:set name="glsum" value ="formatDouble((lessthan80*groupPrice)*1.0+(noGood*groupPrice)*1.0-(good*2*groupPrice)*1.0)"/>
										<td  colspan='3'  ><s:property value="#glsum"/></td>
									</tr>
									<tr>
										<td >Bug统计扣款额（元）：</td>
										<td  colspan='3'  ><s:property value="#bugsum" /></td>
									</tr>
									<tr>
										<td >开发规范扣款额（元）：</td>
										<td  colspan='3'  >0</td>
									</tr>
									<tr>
										<td >开发人天扣款额（元）：</td>
										<td  colspan='3'  ><s:property value="#kfsum"/></td>
									</tr>
									<tr>
										<td >用户满意度扣款额（元）：</td>
										<td  colspan='3'  ><s:property value="#sasum"/></td>
									</tr>
									<tr>
										<td >月度评比扣款额（元）：</td>
										<td  colspan='3'  >0</td>
									</tr>
									<tr>
										<td >奖励升级扣款额（元）：</td>
										<td  colspan='3'  >0</td>
									</tr>
									<tr>
										<td >合计扣款额（元）：</td>
										
										<td  colspan='3'  ><s:property value="formatDouble(#Timelysum*1.0+#sasum*1.0+#kfsum*1.0+#bugsum*1.0+#glsum*1.0)" /></td>
									</tr>
									<tr>
									</tr>
									<tr>
										<td colspan='4'>建议优化点：<br/>
										<s:iterator value="suggest.records" status="status">
											<s:property value="suggestStr"/><br/>
										</s:iterator>
										</td>
									</tr>
									<tr>
									</tr>
									<tr>
										<td >会签角色：</td>
										<td >汇签职责：</td>
										<td colspan='2'>意见（如为空并签字视为无需整改同意付款）</td>
									</tr>
									<tr>
										<td >开发架构经营体：</td>
										<td >开发SLA指标达标以及非达标系统兑现准确</td>
										<td >签名<br/>
											 日期</td>
										<td ></td>
									</tr>
									<tr>
										<td >PSI项目经理：</td>
										<td >承诺填写内容属实，文档齐全，项目阶段目标达成</td>
										<td >签名<br/>
											 日期</td>
										<td ></td>
									</tr>
									<tr>
										<td >经营体长：</td>
										<td >承诺项目阶段目标已经达成，满足业务需求，确认付款</td>
										<td >签名<br/>
											 日期</td>
										<td ></td>
									</tr>
								</tbody>
								<tfoot>
								</tfoot>
							</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
function submitForm(action){
	var form = document.getElementById("searchUserForm");
	form.action="${dynamicURL}/jira/"+action+".action";
	form.submit();
}
var tabOnclick = function(tabId){
	for(int i = 0 ;i<10 ;i++){
		if(("tabs-"+i)==tabId){
			$("#"+tabId).show();
		}else{
			$("#"+("tabs-"+i)).hide();
		}
	}
	
}

</script>
<script type="text/javascript"> 

</script>  
</body>
</html>