<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
function setSelected(rigthList){
	//var  oToList=document.getElementById(rigthList);
	//var  oToList=$("#rigthList");
	//alert(oToList);
	//var nToLen=oToList.options.length;
	//$("#rigthList").options.length;
	//var  str="";
	var nToLen = $("#rigthList option").length;
	for(var i=0;i<nToLen;i++){
		//str+=oToList.options[i].value+'*';
		//oToList.options[i].selected=true;
		 $("#rigthList").get(0).options[i].selected = true; 
	}
	//document.getElementById("firstForm:roleIds").value=str;
	//alert(document.getElementById("firstForm:roleIds").value+" AAA");
	//alert(firstForm+"  KKKK");
	//firstForm.submit();
	return true;
}


function moveOption(leftList,rigthList,bAll)
{
	var  oFromList=document.getElementById(leftList);
	var  oToList=document.getElementById(rigthList);
	var nFromLen=oFromList.options.length;
	var nToLen=oToList.options.length;
	var i=0;
	while(nFromLen>0){
	  if(oFromList.options[i].selected ||bAll){
	   oToList[nToLen++]=new Option(oFromList.options[i].text,oFromList.options[i].value);
	   oFromList.options[i]=null;
	  } else {
	     i++;
	  }
	   nFromLen--;
	 }
	sortOption(oToList);
}
	 
function sortOption(oList)
{
 if(oList.options.length>1)
 {
  var optionList=new Array();  
  for(var i=0;i<oList.options.length;i++)  
  optionList.push(oList.options[i]);
  optionList.sort(compare);
  oList.length=0;
  for(var i=0;i<optionList.length;i++)
  {
   	oList.options[i]=optionList[i];
	}
  }
}
 
 
function compare(a,b){
 	 if(a.text<b.text){
	 	return -1;
	 }else if(a.text>b.text){
	 	return 1;
	 }
	 return 0;
 }
</script> 
<div class="section">
	<div class="box">
		<div class="title">
			组列表 <span class="hide"></span>
		</div>
		<div class="content">
			<div class="dataTables_wrapper">
	    <div>
			<div class="dataTables_filter"></div>
		</div>
				<div style="width: 100%;font-size: 11px; " id="userListDiv">
					<table>
					 <tr>
                         <td width="40%" height="25" valign="middle" align="middle"><strong>未分配组</strong></td>
				                         <td width="5%" valign="middle">&nbsp;</td>
                         <td width="40%" valign="middle" align="middle"><strong>已属组</strong></td>
				                    </tr> 
                    <tr>
				                    	  <td>
	                    	  <select id="leftList" name="leftAllList" class="multiple" multiple="multiple" size="10">
						           <s:iterator value="allGroupList" var ='dep'>
		                    	  	<option value="<s:property value='#dep.id'/>"><s:property value='#dep.name'/></option>
		                    	  </s:iterator>
	                    	  </select>			                    	  	
                    	  </td>
                    	 <td>
                    	   <a href="#" onClick="javascript:moveOption('leftList','rigthList',true);"><strong> ->> </strong></a><br><br> 
                    	   <a href="#" onClick="javascript:moveOption('leftList','rigthList',false);"><strong> -> </strong></a><br><br>
	       				   <a href="#" onClick="javascript:moveOption('rigthList','leftList',false);"><strong> <- </strong></a><br><br>
					       <a href="#" onClick="javascript:moveOption('rigthList','leftList',true);"><strong>  <<-  </strong></a><br> <br>                  	 
                    	 </td>
				         
                    	 <td>
	                    	  <select id="rigthList" name="selectedGroups" class="multiple" multiple="multiple" size="10">
		                    	  <s:iterator value="user.groups" var ='dep'>
		                    	  	<option value="<s:property value='#dep.id'/>"><s:property value='#dep.name'/></option>
		                    	  </s:iterator>
	                    	  </select>			                    	  	
                    	  </td>
                    </tr>	
                    </table> 
				</div>
		</div>
	</div>
</div>

