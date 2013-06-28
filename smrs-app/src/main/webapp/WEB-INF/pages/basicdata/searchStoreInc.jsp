<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="section">
	<div class="box">
		<div class="title">
			查询结果
			<span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper"><div><div class="dataTables_filter"></div></div>
			<table class="sorting"> 
				<thead>
					<tr>
						<th>名称</th>
						<th>编码</th>   
						<th>状态</th>
						<th>开店时间</th>
						<th>关店时间</th>						
						<th>操作</th>
					</tr>
				</thead>
				
			    <tbody>
					<!-- 数据行 -->
					<s:iterator value="pager.records" var="store" status="status">
						<tr>
							<td ><a	href='#' onclick="updateStore(<s:property value="id"/>)"><s:property value="name" /></a></td>
							<td><s:property value="storeCode" /></td>
							<td><s:property value="@com.smrs.enums.StatusEnum@toEnum(status).description" /></td>
							<td><s:date name="openDate" format="yyyy-MM-dd HH:mm:ss" /></td>
							<td><s:date name="closeDate" format="yyyy-MM-dd HH:mm:ss" /></td>							
							<td>
								<img title="删除" border="0" src="${staticURL}/images/trash.png" 	onclick="delStore(<s:property value="id"/>)">								
							</td>
						</tr>
					</s:iterator>
				</tbody>

				</table>
				<p:pagination pager="pager" formId="searchForm" theme="default"></p:pagination>
				</div>
		</div>
	</div>
</div>


