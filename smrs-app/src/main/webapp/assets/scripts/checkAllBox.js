/**
 * checkBox全选
 * @param flag 	是否被选中
 * @param singleCheckName 单选框的name属性
 */
//全选按钮事件
function checkAll(flag, singleCheckName) {
	var _items = document.getElementsByName(singleCheckName);//取得所有子选项的数组 
	for ( var i = 0; i < _items.length; i++) {
		_items[i].checked = flag;//将所有子选项checkbox的状态和全选checkbox状态设置成一致，即要么同时选中，要么同时取消。
	}
}
//每个单选按钮事件
function chkeckSingle(singleCheckName) {
	var flag = true;//设置默认的全选标识状态为true
	var _items = document.getElementsByName(singleCheckName);
	var itemvalue = "";
	for ( var i = 0; i < _items.length; i++) {
		if (!_items[i].checked) {
			flag = false;//如果子选项中有一个未选中，则设置全选标识状态为false
			//break;
		}
	}
	document.getElementById('alltriggercheck').checked = flag;//重新设置全选状态。
}