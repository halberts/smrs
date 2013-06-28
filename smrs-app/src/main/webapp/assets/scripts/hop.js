﻿/*-------------------------------------
hop.js

version:	1.0

-------------------------------------*/

/*
 * JQuery zTree core 3.2
 * http://code.google.com/p/jquerytree/
 *
 * Copyright (c) 2010 Hunter.z (baby666.cn)
 *
 * Licensed same as jquery - MIT License
 * http://www.opensource.org/licenses/mit-license.php
 *
 * email: hunter.z@263.net
 * Date: 2012-05-13
 */



/**
 * common function for jqgrid
 */

function getOptionsFromSelectTag(id){
	$("#"+id).hide();
	var opt = $("#"+id).html(); 
	return opt;
}



(function($){
	var settings = {}, roots = {}, caches = {}, zId = 0,
	//default consts of core
	_consts = {
		event: {
			NODECREATED: "ztree_nodeCreated",
			CLICK: "ztree_click",
			EXPAND: "ztree_expand",
			COLLAPSE: "ztree_collapse",
			ASYNC_SUCCESS: "ztree_async_success",
			ASYNC_ERROR: "ztree_async_error"
		},
		id: {
			A: "_a",
			ICON: "_ico",
			SPAN: "_span",
			SWITCH: "_switch",
			UL: "_ul"
		},
		line: {
			ROOT: "root",
			ROOTS: "roots",
			CENTER: "center",
			BOTTOM: "bottom",
			NOLINE: "noline",
			LINE: "line"
		},
		folder: {
			OPEN: "open",
			CLOSE: "close",
			DOCU: "docu"
		},
		node: {
			CURSELECTED: "curSelectedNode"
		}
	},
	//default setting of core
	_setting = {
		treeId: "",
		treeObj: null,
		view: {
			addDiyDom: null,
			autoCancelSelected: true,
			dblClickExpand: true,
			expandSpeed: "fast",
			fontCss: {},
			nameIsHTML: false,
			selectedMulti: true,
			showIcon: true,
			showLine: true,
			showTitle: true
		},
		data: {
			key: {
				children: "children",
				name: "title",
				title: "",
				url: "url"
			},
			simpleData: {
				enable: false,
				idKey: "id",
				pIdKey: "pId",
				rootPId: null
			},
			keep: {
				parent: false,
				leaf: false
			}
		},
		async: {
			enable: false,
			contentType: "application/x-www-form-urlencoded",
			type: "post",
			dataType: "text",
			url: "",
			autoParam: [],
			otherParam: [],
			dataFilter: null
		},
		callback: {
			beforeAsync:null,
			beforeClick:null,
			beforeRightClick:null,
			beforeMouseDown:null,
			beforeMouseUp:null,
			beforeExpand:null,
			beforeCollapse:null,
			beforeRemove:null,

			onAsyncError:null,
			onAsyncSuccess:null,
			onNodeCreated:null,
			onClick:null,
			onRightClick:null,
			onMouseDown:null,
			onMouseUp:null,
			onExpand:null,
			onCollapse:null,
			onRemove:null
		}
	},
	//default root of core
	//zTree use root to save full data
	_initRoot = function (setting) {
		var r = data.getRoot(setting);
		if (!r) {
			r = {};
			data.setRoot(setting, r);
		}
		r.children = [];
		r.expandTriggerFlag = false;
		r.curSelectedList = [];
		r.noSelection = true;
		r.createdNodes = [];
	},
	//default cache of core
	_initCache = function(setting) {
		var c = data.getCache(setting);
		if (!c) {
			c = {};
			data.setCache(setting, c);
		}
		c.nodes = [];
		c.doms = [];
	},
	//default bindEvent of core
	_bindEvent = function(setting) {
		var o = setting.treeObj,
		c = consts.event;
		o.unbind(c.NODECREATED);
		o.bind(c.NODECREATED, function (event, treeId, node) {
			tools.apply(setting.callback.onNodeCreated, [event, treeId, node]);
		});

		o.unbind(c.CLICK);
		o.bind(c.CLICK, function (event, srcEvent, treeId, node, clickFlag) {
			tools.apply(setting.callback.onClick, [srcEvent, treeId, node, clickFlag]);
		});

		o.unbind(c.EXPAND);
		o.bind(c.EXPAND, function (event, treeId, node) {
			tools.apply(setting.callback.onExpand, [event, treeId, node]);
		});

		o.unbind(c.COLLAPSE);
		o.bind(c.COLLAPSE, function (event, treeId, node) {
			tools.apply(setting.callback.onCollapse, [event, treeId, node]);
		});

		o.unbind(c.ASYNC_SUCCESS);
		o.bind(c.ASYNC_SUCCESS, function (event, treeId, node, msg) {
			tools.apply(setting.callback.onAsyncSuccess, [event, treeId, node, msg]);
		});

		o.unbind(c.ASYNC_ERROR);
		o.bind(c.ASYNC_ERROR, function (event, treeId, node, XMLHttpRequest, textStatus, errorThrown) {
			tools.apply(setting.callback.onAsyncError, [event, treeId, node, XMLHttpRequest, textStatus, errorThrown]);
		});
	},
	//default event proxy of core
	_eventProxy = function(event) {
		var target = event.target,
		setting = settings[event.data.treeId],
		tId = "", node = null,
		nodeEventType = "", treeEventType = "",
		nodeEventCallback = null, treeEventCallback = null,
		tmp = null;

		if (tools.eqs(event.type, "mousedown")) {
			treeEventType = "mousedown";
		} else if (tools.eqs(event.type, "mouseup")) {
			treeEventType = "mouseup";
		} else if (tools.eqs(event.type, "contextmenu")) {
			treeEventType = "contextmenu";
		} else if (tools.eqs(event.type, "click")) {
			if (tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.SWITCH) !== null) {
				tId = target.parentNode.id;
				nodeEventType = "switchNode";
			} else {
				tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
				if (tmp) {
					tId = tmp.parentNode.id;
					nodeEventType = "clickNode";
				}
			}
		} else if (tools.eqs(event.type, "dblclick")) {
			treeEventType = "dblclick";
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {
				tId = tmp.parentNode.id;
				nodeEventType = "switchNode";
			}
		}
		if (treeEventType.length > 0 && tId.length == 0) {
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {tId = tmp.parentNode.id;}
		}
		// event to node
		if (tId.length>0) {
			node = data.getNodeCache(setting, tId);
			switch (nodeEventType) {
				case "switchNode" :
					if (!node.isParent) {
						nodeEventType = "";
					} else if (tools.eqs(event.type, "click") 
						|| (tools.eqs(event.type, "dblclick") && tools.apply(setting.view.dblClickExpand, [setting.treeId, node], setting.view.dblClickExpand))) {
						nodeEventCallback = handler.onSwitchNode;
					} else {
						nodeEventType = "";
					}
					break;
				case "clickNode" :
					nodeEventCallback = handler.onClickNode;
					break;
			}
		}
		// event to zTree
		switch (treeEventType) {
			case "mousedown" :
				treeEventCallback = handler.onZTreeMousedown;
				break;
			case "mouseup" :
				treeEventCallback = handler.onZTreeMouseup;
				break;
			case "dblclick" :
				treeEventCallback = handler.onZTreeDblclick;
				break;
			case "contextmenu" :
				treeEventCallback = handler.onZTreeContextmenu;
				break;
		}
		var proxyResult = {
			stop: false,
			node: node,
			nodeEventType: nodeEventType,
			nodeEventCallback: nodeEventCallback,
			treeEventType: treeEventType,
			treeEventCallback: treeEventCallback
		};
		return proxyResult
	},
	//default init node of core
	_initNode = function(setting, level, n, parentNode, isFirstNode, isLastNode, openFlag) {
		if (!n) return;
		var childKey = setting.data.key.children;
		n.level = level;
		n.tId = setting.treeId + "_" + (++zId);
		n.parentTId = parentNode ? parentNode.tId : null;
		if (n[childKey] && n[childKey].length > 0) {
			if (typeof n.open == "string") n.open = tools.eqs(n.open, "true");
			n.open = !!n.open;
			n.isParent = true;
			n.zAsync = true;
		} else {
			n.open = false;
			if (typeof n.isParent == "string") n.isParent = tools.eqs(n.isParent, "true");
			n.isParent = !!n.isParent;
			n.zAsync = !n.isParent;
		}
		n.isFirstNode = isFirstNode;
		n.isLastNode = isLastNode;
		n.getParentNode = function() {return data.getNodeCache(setting, n.parentTId);};
		n.getPreNode = function() {return data.getPreNode(setting, n);};
		n.getNextNode = function() {return data.getNextNode(setting, n);};
		n.isAjaxing = false;
		data.fixPIdKeyValue(setting, n);
	},
	_init = {
		bind: [_bindEvent],
		caches: [_initCache],
		nodes: [_initNode],
		proxys: [_eventProxy],
		roots: [_initRoot],
		beforeA: [],
		afterA: [],
		innerBeforeA: [],
		innerAfterA: [],
		zTreeTools: []
	},
	//method of operate data
	data = {
		addNodeCache: function(setting, node) {
			data.getCache(setting).nodes[node.tId] = node;
		},
		addAfterA: function(afterA) {
			_init.afterA.push(afterA);
		},
		addBeforeA: function(beforeA) {
			_init.beforeA.push(beforeA);
		},
		addInnerAfterA: function(innerAfterA) {
			_init.innerAfterA.push(innerAfterA);
		},
		addInnerBeforeA: function(innerBeforeA) {
			_init.innerBeforeA.push(innerBeforeA);
		},
		addInitBind: function(bindEvent) {
			_init.bind.push(bindEvent);
		},
		addInitCache: function(initCache) {
			_init.caches.push(initCache);
		},
		addInitNode: function(initNode) {
			_init.nodes.push(initNode);
		},
		addInitProxy: function(initProxy) {
			_init.proxys.push(initProxy);
		},
		addInitRoot: function(initRoot) {
			_init.roots.push(initRoot);
		},
		addNodesData: function(setting, parentNode, nodes) {
			var childKey = setting.data.key.children;
			if (!parentNode[childKey]) parentNode[childKey] = [];
			if (parentNode[childKey].length > 0) {
				parentNode[childKey][parentNode[childKey].length - 1].isLastNode = false;
				view.setNodeLineIcos(setting, parentNode[childKey][parentNode[childKey].length - 1]);
			}
			parentNode.isParent = true;
			parentNode[childKey] = parentNode[childKey].concat(nodes);
		},
		addSelectedNode: function(setting, node) {
			var root = data.getRoot(setting);
			if (!data.isSelectedNode(setting, node)) {
				root.curSelectedList.push(node);
			}
		},
		addCreatedNode: function(setting, node) {
			if (!!setting.callback.onNodeCreated || !!setting.view.addDiyDom) {
				var root = data.getRoot(setting);
				root.createdNodes.push(node);
			}
		},
		addZTreeTools: function(zTreeTools) {
			_init.zTreeTools.push(zTreeTools);
		},
		exSetting: function(s) {
			$.extend(true, _setting, s);
		},
		fixPIdKeyValue: function(setting, node) {
			if (setting.data.simpleData.enable) {
				node[setting.data.simpleData.pIdKey] = node.parentTId ? node.getParentNode()[setting.data.simpleData.idKey] : setting.data.simpleData.rootPId;
			}
		},
		getAfterA: function(setting, node, array) {
			for (var i=0, j=_init.afterA.length; i<j; i++) {
				_init.afterA[i].apply(this, arguments);
			}
		},
		getBeforeA: function(setting, node, array) {
			for (var i=0, j=_init.beforeA.length; i<j; i++) {
				_init.beforeA[i].apply(this, arguments);
			}
		},
		getInnerAfterA: function(setting, node, array) {
			for (var i=0, j=_init.innerAfterA.length; i<j; i++) {
				_init.innerAfterA[i].apply(this, arguments);
			}
		},
		getInnerBeforeA: function(setting, node, array) {
			for (var i=0, j=_init.innerBeforeA.length; i<j; i++) {
				_init.innerBeforeA[i].apply(this, arguments);
			}
		},
		getCache: function(setting) {
			return caches[setting.treeId];
		},
		getNextNode: function(setting, node) {
			if (!node) return null;
			var childKey = setting.data.key.children,
			p = node.parentTId ? node.getParentNode() : data.getRoot(setting);
			if (node.isLastNode) {
				return null;
			} else if (node.isFirstNode) {
				return p[childKey][1];
			} else {
				for (var i=1, l=p[childKey].length-1; i<l; i++) {
					if (p[childKey][i] === node) {
						return p[childKey][i+1];
					}
				}
			}
			return null;
		},
		getNodeByParam: function(setting, nodes, key, value) {
			if (!nodes || !key) return null;
			var childKey = setting.data.key.children;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i][key] == value) {
					return nodes[i];
				}
				var tmp = data.getNodeByParam(setting, nodes[i][childKey], key, value);
				if (tmp) return tmp;
			}
			return null;
		},
		getNodeCache: function(setting, tId) {
			if (!tId) return null;
			var n = caches[setting.treeId].nodes[tId];
			return n ? n : null;
		},
		getNodes: function(setting) {
			return data.getRoot(setting)[setting.data.key.children];
		},
		getNodesByParam: function(setting, nodes, key, value) {
			if (!nodes || !key) return [];
			var childKey = setting.data.key.children,
			result = [];
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i][key] == value) {
					result.push(nodes[i]);
				}
				result = result.concat(data.getNodesByParam(setting, nodes[i][childKey], key, value));
			}
			return result;
		},
		getNodesByParamFuzzy: function(setting, nodes, key, value) {
			if (!nodes || !key) return [];
			var childKey = setting.data.key.children,
			result = [];
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (typeof nodes[i][key] == "string" && nodes[i][key].indexOf(value)>-1) {
					result.push(nodes[i]);
				}
				result = result.concat(data.getNodesByParamFuzzy(setting, nodes[i][childKey], key, value));
			}
			return result;
		},
		getNodesByFilter: function(setting, nodes, filter, isSingle) {
			if (!nodes) return (isSingle ? null : []);
			var childKey = setting.data.key.children,
			result = isSingle ? null : [];
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (tools.apply(filter, [nodes[i]], false)) {
					if (isSingle) {return nodes[i];}
					result.push(nodes[i]);
				}
				var tmpResult = data.getNodesByFilter(setting, nodes[i][childKey], filter, isSingle);
				if (isSingle && !!tmpResult) {return tmpResult;}
				result = isSingle ? tmpResult : result.concat(tmpResult);
			}
			return result;
		},
		getPreNode: function(setting, node) {
			if (!node) return null;
			var childKey = setting.data.key.children,
			p = node.parentTId ? node.getParentNode() : data.getRoot(setting);
			if (node.isFirstNode) {
				return null;
			} else if (node.isLastNode) {
				return p[childKey][p[childKey].length-2];
			} else {
				for (var i=1, l=p[childKey].length-1; i<l; i++) {
					if (p[childKey][i] === node) {
						return p[childKey][i-1];
					}
				}
			}
			return null;
		},
		getRoot: function(setting) {
			return setting ? roots[setting.treeId] : null;
		},
		getSetting: function(treeId) {
			return settings[treeId];
		},
		getSettings: function() {
			return settings;
		},
		getTitleKey: function(setting) {
			return setting.data.key.title === "" ? setting.data.key.name : setting.data.key.title;
		},
		getZTreeTools: function(treeId) {
			var r = this.getRoot(this.getSetting(treeId));
			return r ? r.treeTools : null;
		},
		initCache: function(setting) {
			for (var i=0, j=_init.caches.length; i<j; i++) {
				_init.caches[i].apply(this, arguments);
			}
		},
		initNode: function(setting, level, node, parentNode, preNode, nextNode) {
			for (var i=0, j=_init.nodes.length; i<j; i++) {
				_init.nodes[i].apply(this, arguments);
			}
		},
		initRoot: function(setting) {
			for (var i=0, j=_init.roots.length; i<j; i++) {
				_init.roots[i].apply(this, arguments);
			}
		},
		isSelectedNode: function(setting, node) {
			var root = data.getRoot(setting);
			for (var i=0, j=root.curSelectedList.length; i<j; i++) {
				if(node === root.curSelectedList[i]) return true;
			}
			return false;
		},
		removeNodeCache: function(setting, node) {
			var childKey = setting.data.key.children;
			if (node[childKey]) {
				for (var i=0, l=node[childKey].length; i<l; i++) {
					arguments.callee(setting, node[childKey][i]);
				}
			}
			delete data.getCache(setting).nodes[node.tId];
		},
		removeSelectedNode: function(setting, node) {
			var root = data.getRoot(setting);
			for (var i=0, j=root.curSelectedList.length; i<j; i++) {
				if(node === root.curSelectedList[i] || !data.getNodeCache(setting, root.curSelectedList[i].tId)) {
					root.curSelectedList.splice(i, 1);
					i--;j--;
				}
			}
		},
		setCache: function(setting, cache) {
			caches[setting.treeId] = cache;
		},
		setRoot: function(setting, root) {
			roots[setting.treeId] = root;
		},
		setZTreeTools: function(setting, zTreeTools) {
			for (var i=0, j=_init.zTreeTools.length; i<j; i++) {
				_init.zTreeTools[i].apply(this, arguments);
			}
		},
		transformToArrayFormat: function (setting, nodes) {
			if (!nodes) return [];
			var childKey = setting.data.key.children,
			r = [];
			if (tools.isArray(nodes)) {
				for (var i=0, l=nodes.length; i<l; i++) {
					r.push(nodes[i]);
					if (nodes[i][childKey])
						r = r.concat(data.transformToArrayFormat(setting, nodes[i][childKey]));
				}
			} else {
				r.push(nodes);
				if (nodes[childKey])
					r = r.concat(data.transformToArrayFormat(setting, nodes[childKey]));
			}
			return r;
		},
		transformTozTreeFormat: function(setting, sNodes) {
			var i,l,
			key = setting.data.simpleData.idKey,
			parentKey = setting.data.simpleData.pIdKey,
			childKey = setting.data.key.children;
			if (!key || key=="" || !sNodes) return [];

			if (tools.isArray(sNodes)) {
				var r = [];
				var tmpMap = [];
				for (i=0, l=sNodes.length; i<l; i++) {
					tmpMap[sNodes[i][key]] = sNodes[i];
				}
				for (i=0, l=sNodes.length; i<l; i++) {
					if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] != sNodes[i][parentKey]) {
						if (!tmpMap[sNodes[i][parentKey]][childKey])
							tmpMap[sNodes[i][parentKey]][childKey] = [];
						tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
					} else {
						r.push(sNodes[i]);
					}
				}
				return r;
			}else {
				return [sNodes];
			}
		}
	},
	//method of event proxy
	event = {
		bindEvent: function(setting) {
			for (var i=0, j=_init.bind.length; i<j; i++) {
				_init.bind[i].apply(this, arguments);
			}
		},
		bindTree: function(setting) {
			var eventParam = {
				treeId: setting.treeId
			},
			o = setting.treeObj;
			o.unbind('click', event.proxy);
			o.bind('click', eventParam, event.proxy);
			o.unbind('dblclick', event.proxy);
			o.bind('dblclick', eventParam, event.proxy);
			o.unbind('mouseover', event.proxy);
			o.bind('mouseover', eventParam, event.proxy);
			o.unbind('mouseout', event.proxy);
			o.bind('mouseout', eventParam, event.proxy);
			o.unbind('mousedown', event.proxy);
			o.bind('mousedown', eventParam, event.proxy);
			o.unbind('mouseup', event.proxy);
			o.bind('mouseup', eventParam, event.proxy);
			o.unbind('contextmenu', event.proxy);
			o.bind('contextmenu', eventParam, event.proxy);
		},
		doProxy: function(e) {
			var results = [];
			for (var i=0, j=_init.proxys.length; i<j; i++) {
				var proxyResult = _init.proxys[i].apply(this, arguments);
				results.push(proxyResult);
				if (proxyResult.stop) {
					break;
				}
			}
			return results;
		},
		proxy: function(e) {
			var setting = data.getSetting(e.data.treeId);
			if (!tools.uCanDo(setting, e)) return true;
			var results = event.doProxy(e),
			r = true, x = false;
			for (var i=0, l=results.length; i<l; i++) {
				var proxyResult = results[i];
				if (proxyResult.nodeEventCallback) {
					x = true;
					r = proxyResult.nodeEventCallback.apply(proxyResult, [e, proxyResult.node]) && r;
				}
				if (proxyResult.treeEventCallback) {
					x = true;
					r = proxyResult.treeEventCallback.apply(proxyResult, [e, proxyResult.node]) && r;
				}
			}
			try{
				if (x && $("input:focus").length == 0) {
					tools.noSel(setting);
				}
			} catch(e) {}
			return r;
		}
	},
	//method of event handler
	handler = {
		onSwitchNode: function (event, node) {
			var setting = settings[event.data.treeId];
			if (node.open) {
				if (tools.apply(setting.callback.beforeCollapse, [setting.treeId, node], true) == false) return true;
				data.getRoot(setting).expandTriggerFlag = true;
				view.switchNode(setting, node);
			} else {
				if (tools.apply(setting.callback.beforeExpand, [setting.treeId, node], true) == false) return true;
				data.getRoot(setting).expandTriggerFlag = true;
				view.switchNode(setting, node);
			}
			return true;
		},
		onClickNode: function (event, node) {
			var setting = settings[event.data.treeId],
			clickFlag = ( (setting.view.autoCancelSelected && event.ctrlKey) && data.isSelectedNode(setting, node)) ? 0 : (setting.view.autoCancelSelected && event.ctrlKey && setting.view.selectedMulti) ? 2 : 1;
			if (tools.apply(setting.callback.beforeClick, [setting.treeId, node, clickFlag], true) == false) return true;
			if (clickFlag === 0) {
				view.cancelPreSelectedNode(setting, node);
			} else {
				view.selectNode(setting, node, clickFlag === 2);
			}
			setting.treeObj.trigger(consts.event.CLICK, [event, setting.treeId, node, clickFlag]);
			return true;
		},
		onZTreeMousedown: function(event, node) {
			var setting = settings[event.data.treeId];
			if (tools.apply(setting.callback.beforeMouseDown, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onMouseDown, [event, setting.treeId, node]);
			}
			return true;
		},
		onZTreeMouseup: function(event, node) {
			var setting = settings[event.data.treeId];
			if (tools.apply(setting.callback.beforeMouseUp, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onMouseUp, [event, setting.treeId, node]);
			}
			return true;
		},
		onZTreeDblclick: function(event, node) {
			var setting = settings[event.data.treeId];
			if (tools.apply(setting.callback.beforeDblClick, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onDblClick, [event, setting.treeId, node]);
			}
			return true;
		},
		onZTreeContextmenu: function(event, node) {
			var setting = settings[event.data.treeId];
			if (tools.apply(setting.callback.beforeRightClick, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onRightClick, [event, setting.treeId, node]);
			}
			return (typeof setting.callback.onRightClick) != "function";
		}
	},
	//method of tools for zTree
	tools = {
		apply: function(fun, param, defaultValue) {
			if ((typeof fun) == "function") {
				return fun.apply(zt, param?param:[]);
			}
			return defaultValue;
		},
		canAsync: function(setting, node) {
			var childKey = setting.data.key.children;
			return (setting.async.enable && node && node.isParent && !(node.zAsync || (node[childKey] && node[childKey].length > 0)));
		},
		clone: function (jsonObj) {
			var buf;
			if (jsonObj instanceof Array) {
				buf = [];
				var i = jsonObj.length;
				while (i--) {
					buf[i] = arguments.callee(jsonObj[i]);
				}
				return buf;
			}else if (typeof jsonObj == "function"){
				return jsonObj;
			}else if (jsonObj instanceof Object){
				buf = {};
				for (var k in jsonObj) {
					buf[k] = arguments.callee(jsonObj[k]);
				}
				return buf;
			}else{
				return jsonObj;
			}
		},
		eqs: function(str1, str2) {
			return str1.toLowerCase() === str2.toLowerCase();
		},
		isArray: function(arr) {
			return Object.prototype.toString.apply(arr) === "[object Array]";
		},
		getMDom: function (setting, curDom, targetExpr) {
			if (!curDom) return null;
			while (curDom && curDom.id !== setting.treeId) {
				for (var i=0, l=targetExpr.length; curDom.tagName && i<l; i++) {
					if (tools.eqs(curDom.tagName, targetExpr[i].tagName) && curDom.getAttribute(targetExpr[i].attrName) !== null) {
						return curDom;
					}
				}
				curDom = curDom.parentNode;
			}
			return null;
		},
		noSel: function(setting) {
			var r = data.getRoot(setting);
			if (r.noSelection) {
				try {
					window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
				} catch(e){}
			}
		},
		uCanDo: function(setting, e) {
			return true;
		}
	},
	//method of operate ztree dom
	view = {
		addNodes: function(setting, parentNode, newNodes, isSilent) {
			if (setting.data.keep.leaf && parentNode && !parentNode.isParent) {
				return;
			}
			if (!tools.isArray(newNodes)) {
				newNodes = [newNodes];
			}
			if (setting.data.simpleData.enable) {
				newNodes = data.transformTozTreeFormat(setting, newNodes);
			}
			if (parentNode) {
				var target_switchObj = $("#" + parentNode.tId + consts.id.SWITCH),
				target_icoObj = $("#" + parentNode.tId + consts.id.ICON),
				target_ulObj = $("#" + parentNode.tId + consts.id.UL);

				if (!parentNode.open) {
					view.replaceSwitchClass(parentNode, target_switchObj, consts.folder.CLOSE);
					view.replaceIcoClass(parentNode, target_icoObj, consts.folder.CLOSE);
					parentNode.open = false;
					target_ulObj.css({
						"display": "none"
					});
				}

				data.addNodesData(setting, parentNode, newNodes);
				view.createNodes(setting, parentNode.level + 1, newNodes, parentNode);
				if (!isSilent) {
					view.expandCollapseParentNode(setting, parentNode, true);
				}
			} else {
				data.addNodesData(setting, data.getRoot(setting), newNodes);
				view.createNodes(setting, 0, newNodes, null);
			}
		},
		appendNodes: function(setting, level, nodes, parentNode, initFlag, openFlag) {
			if (!nodes) return [];
			var html = [],
			childKey = setting.data.key.children,
			nameKey = setting.data.key.name,
			titleKey = data.getTitleKey(setting);
			for (var i = 0, l = nodes.length; i < l; i++) {
				var node = nodes[i],
				tmpPNode = (parentNode) ? parentNode: data.getRoot(setting),
				tmpPChild = tmpPNode[childKey],
				isFirstNode = ((tmpPChild.length == nodes.length) && (i == 0)),
				isLastNode = (i == (nodes.length - 1));
				if (initFlag) {
					data.initNode(setting, level, node, parentNode, isFirstNode, isLastNode, openFlag);
					data.addNodeCache(setting, node);
				}

				var childHtml = [];
				if (node[childKey] && node[childKey].length > 0) {
					//make child html first, because checkType
					childHtml = view.appendNodes(setting, level + 1, node[childKey], node, initFlag, openFlag && node.open);
				}
				if (openFlag) {
					var url = view.makeNodeUrl(setting, node),
					fontcss = view.makeNodeFontCss(setting, node),
					fontStyle = [];
					for (var f in fontcss) {
						fontStyle.push(f, ":", fontcss[f], ";");
					}
					html.push("<li id='", node.tId, "' class='level", node.level,"' tabindex='0' hidefocus='true' treenode>",
						"<span id='", node.tId, consts.id.SWITCH,
						"' title='' class='", view.makeNodeLineClass(setting, node), "' treeNode", consts.id.SWITCH,"></span>");
					data.getBeforeA(setting, node, html);
					html.push("<a id='", node.tId, consts.id.A, "' class='level", node.level,"' treeNode", consts.id.A," onclick=\"", (node.click || ''),
						"\" ", ((url != null && url.length > 0) ? "href='" + url + "'" : ""), " target='",view.makeNodeTarget(node),"' style='", fontStyle.join(''),
						"'");
					if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle) && node[titleKey]) {html.push("title='", node[titleKey].replace(/'/g,"&#39;").replace(/</g,'&lt;').replace(/>/g,'&gt;'),"'");}
					html.push(">");
					data.getInnerBeforeA(setting, node, html);
					var name = setting.view.nameIsHTML ? node[nameKey] : node[nameKey].replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
					html.push("<span id='", node.tId, consts.id.ICON,
						"' title='' treeNode", consts.id.ICON," class='", view.makeNodeIcoClass(setting, node), "' style='", view.makeNodeIcoStyle(setting, node), "'></span><span id='", node.tId, consts.id.SPAN,
						"'>",name,"</span>");
					data.getInnerAfterA(setting, node, html);
					html.push("</a>");
					data.getAfterA(setting, node, html);
					if (node.isParent && node.open) {
						view.makeUlHtml(setting, node, html, childHtml.join(''));
					}
					html.push("</li>");
					data.addCreatedNode(setting, node);
				}
			}
			return html;
		},
		appendParentULDom: function(setting, node) {
			var html = [],
			nObj = $("#" + node.tId),
			ulObj = $("#" + node.tId + consts.id.UL),
			childKey = setting.data.key.children,
			childHtml = view.appendNodes(setting, node.level+1, node[childKey], node, false, true);
			view.makeUlHtml(setting, node, html, childHtml.join(''));
			if (!nObj.get(0) && !!node.parentTId) {
				view.appendParentULDom(setting, node.getParentNode());
				nObj = $("#" + node.tId);
			}
			if (ulObj.get(0)) {
				ulObj.remove();
			}

			nObj.append(html.join(''));
			view.createNodeCallback(setting);
		},
		asyncNode: function(setting, node, isSilent, callback) {
			var i, l;
			if (node && !node.isParent) {
				tools.apply(callback);
				return false;
			} else if (node && node.isAjaxing) {
				return false;
			} else if (tools.apply(setting.callback.beforeAsync, [setting.treeId, node], true) == false) {
				tools.apply(callback);
				return false;
			}
			if (node) {
				node.isAjaxing = true;
				var icoObj = $("#" + node.tId + consts.id.ICON);
				icoObj.attr({"style":"", "class":"button ico_loading"});
			}

			var isJson = (setting.async.contentType == "application/json"), tmpParam = isJson ? "{" : "", jTemp="";
			for (i = 0, l = setting.async.autoParam.length; node && i < l; i++) {
				var pKey = setting.async.autoParam[i].split("="), spKey = pKey;
				if (pKey.length>1) {
					spKey = pKey[1];
					pKey = pKey[0];
				}
				if (isJson) {
					jTemp = (typeof node[pKey] == "string") ? '"' : '';
					tmpParam += '"' + spKey + ('":' + jTemp + node[pKey]).replace(/'/g,'\\\'') + jTemp + ',';
				} else {
					tmpParam += spKey + ("=" + node[pKey]).replace(/&/g,'%26') + "&";
				}
			}
			if (tools.isArray(setting.async.otherParam)) {
				for (i = 0, l = setting.async.otherParam.length; i < l; i += 2) {
					if (isJson) {
						jTemp = (typeof setting.async.otherParam[i + 1] == "string") ? '"' : '';
						tmpParam += '"' + setting.async.otherParam[i] + ('":' + jTemp + setting.async.otherParam[i + 1]).replace(/'/g,'\\\'') + jTemp + ",";
					} else {
						tmpParam += setting.async.otherParam[i] + ("=" + setting.async.otherParam[i + 1]).replace(/&/g,'%26') + "&";
					}
				}
			} else {
				for (var p in setting.async.otherParam) {
					if (isJson) {
						jTemp = (typeof setting.async.otherParam[p] == "string") ? '"' : '';
						tmpParam += '"' + p + ('":' + jTemp + setting.async.otherParam[p]).replace(/'/g,'\\\'') + jTemp + ",";
					} else {
						tmpParam += p + ("=" + setting.async.otherParam[p]).replace(/&/g,'%26') + "&";
					}
				}
			}
			if (tmpParam.length > 1) tmpParam = tmpParam.substring(0, tmpParam.length-1);
			if (isJson) tmpParam += "}";

			$.ajax({
				contentType: setting.async.contentType,
				type: setting.async.type,
				url: tools.apply(setting.async.url, [setting.treeId, node], setting.async.url),
				data: tmpParam,
				dataType: setting.async.dataType,
				success: function(msg) {
					var newNodes = [];
					try {
						if (!msg || msg.length == 0) {
							newNodes = [];
						} else if (typeof msg == "string") {
							newNodes = eval("(" + msg + ")");
						} else {
							newNodes = msg;
						}
					} catch(err) {}

					if (node) {
						node.isAjaxing = null;
						node.zAsync = true;
					}
					view.setNodeLineIcos(setting, node);
					if (newNodes && newNodes != "") {
						newNodes = tools.apply(setting.async.dataFilter, [setting.treeId, node, newNodes], newNodes);
						view.addNodes(setting, node, !!newNodes ? tools.clone(newNodes) : [], !!isSilent);
					} else {
						view.addNodes(setting, node, [], !!isSilent);
					}
					setting.treeObj.trigger(consts.event.ASYNC_SUCCESS, [setting.treeId, node, msg]);
					tools.apply(callback);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					if (node) node.isAjaxing = null;
					view.setNodeLineIcos(setting, node);
					setting.treeObj.trigger(consts.event.ASYNC_ERROR, [setting.treeId, node, XMLHttpRequest, textStatus, errorThrown]);
				}
			});
			return true;
		},
		cancelPreSelectedNode: function (setting, node) {
			var list = data.getRoot(setting).curSelectedList;
			for (var i=0, j=list.length-1; j>=i; j--) {
				if (!node || node === list[j]) {
					$("#" + list[j].tId + consts.id.A).removeClass(consts.node.CURSELECTED);
					view.setNodeName(setting, list[j]);
					if (node) {
						data.removeSelectedNode(setting, node);
						break;
					}
				}
			}
			if (!node) data.getRoot(setting).curSelectedList = [];
		},
		createNodeCallback: function(setting) {
			if (!!setting.callback.onNodeCreated || !!setting.view.addDiyDom) {
				var root = data.getRoot(setting);
				while (root.createdNodes.length>0) {
					var node = root.createdNodes.shift();
					tools.apply(setting.view.addDiyDom, [setting.treeId, node]);
					if (!!setting.callback.onNodeCreated) {
						setting.treeObj.trigger(consts.event.NODECREATED, [setting.treeId, node]);
					}
				}
			}
		},
		createNodes: function(setting, level, nodes, parentNode) {
			if (!nodes || nodes.length == 0) return;
			var root = data.getRoot(setting),
			childKey = setting.data.key.children,
			openFlag = !parentNode || parentNode.open || !!$("#" + parentNode[childKey][0].tId).get(0);
			root.createdNodes = [];
			var zTreeHtml = view.appendNodes(setting, level, nodes, parentNode, true, openFlag);
			if (!parentNode) {
				setting.treeObj.append(zTreeHtml.join(''));
			} else {
				var ulObj = $("#" + parentNode.tId + consts.id.UL);
				if (ulObj.get(0)) {
					ulObj.append(zTreeHtml.join(''));
				}
			}
			view.createNodeCallback(setting);
		},
		expandCollapseNode: function(setting, node, expandFlag, animateFlag, callback) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children;
			if (!node) {
				tools.apply(callback, []);
				return;
			}
			if (root.expandTriggerFlag) {
				var _callback = callback;
				callback = function(){
					if (_callback) _callback();
					if (node.open) {
						setting.treeObj.trigger(consts.event.EXPAND, [setting.treeId, node]);
					} else {
						setting.treeObj.trigger(consts.event.COLLAPSE, [setting.treeId, node]);
					}
				};
				root.expandTriggerFlag = false;
			}
			if (node.open == expandFlag) {
				tools.apply(callback, []);
				return;
			}
			if (!node.open && node.isParent && ((!$("#" + node.tId + consts.id.UL).get(0)) || (node[childKey] && node[childKey].length>0 && !$("#" + node[childKey][0].tId).get(0)))) {
				view.appendParentULDom(setting, node);
			}
			var ulObj = $("#" + node.tId + consts.id.UL),
			switchObj = $("#" + node.tId + consts.id.SWITCH),
			icoObj = $("#" + node.tId + consts.id.ICON);

			if (node.isParent) {
				node.open = !node.open;
				if (node.iconOpen && node.iconClose) {
					icoObj.attr("style", view.makeNodeIcoStyle(setting, node));
				}

				if (node.open) {
					view.replaceSwitchClass(node, switchObj, consts.folder.OPEN);
					view.replaceIcoClass(node, icoObj, consts.folder.OPEN);
					if (animateFlag == false || setting.view.expandSpeed == "") {
						ulObj.show();
						tools.apply(callback, []);
					} else {
						if (node[childKey] && node[childKey].length > 0) {
							ulObj.slideDown(setting.view.expandSpeed, callback);
						} else {
							ulObj.show();
							tools.apply(callback, []);
						}
					}
				} else {
					view.replaceSwitchClass(node, switchObj, consts.folder.CLOSE);
					view.replaceIcoClass(node, icoObj, consts.folder.CLOSE);
					if (animateFlag == false || setting.view.expandSpeed == "" || !(node[childKey] && node[childKey].length > 0)) {
						ulObj.hide();
						tools.apply(callback, []);
					} else {
						ulObj.slideUp(setting.view.expandSpeed, callback);
					}
				}
			} else {
				tools.apply(callback, []);
			}
		},
		expandCollapseParentNode: function(setting, node, expandFlag, animateFlag, callback) {
			if (!node) return;
			if (!node.parentTId) {
				view.expandCollapseNode(setting, node, expandFlag, animateFlag, callback);
				return;
			} else {
				view.expandCollapseNode(setting, node, expandFlag, animateFlag);
			}
			if (node.parentTId) {
				view.expandCollapseParentNode(setting, node.getParentNode(), expandFlag, animateFlag, callback);
			}
		},
		expandCollapseSonNode: function(setting, node, expandFlag, animateFlag, callback) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children,
			treeNodes = (node) ? node[childKey]: root[childKey],
			selfAnimateSign = (node) ? false : animateFlag,
			expandTriggerFlag = data.getRoot(setting).expandTriggerFlag;
			data.getRoot(setting).expandTriggerFlag = false;
			if (treeNodes) {
				for (var i = 0, l = treeNodes.length; i < l; i++) {
					if (treeNodes[i]) view.expandCollapseSonNode(setting, treeNodes[i], expandFlag, selfAnimateSign);
				}
			}
			data.getRoot(setting).expandTriggerFlag = expandTriggerFlag;
			view.expandCollapseNode(setting, node, expandFlag, animateFlag, callback );
		},
		makeNodeFontCss: function(setting, node) {
			var fontCss = tools.apply(setting.view.fontCss, [setting.treeId, node], setting.view.fontCss);
			return (fontCss && ((typeof fontCss) != "function")) ? fontCss : {};
		},
		makeNodeIcoClass: function(setting, node) {
			var icoCss = ["ico"];
			if (!node.isAjaxing) {
				icoCss[0] = (node.iconSkin ? node.iconSkin + "_" : "") + icoCss[0];
				if (node.isParent) {
					icoCss.push(node.open ? consts.folder.OPEN : consts.folder.CLOSE);
				} else {
					icoCss.push(consts.folder.DOCU);
				}
			}
			return "button " + icoCss.join('_');
		},
		makeNodeIcoStyle: function(setting, node) {
			var icoStyle = [];
			if (!node.isAjaxing) {
				var icon = (node.isParent && node.iconOpen && node.iconClose) ? (node.open ? node.iconOpen : node.iconClose) : node.icon;
				if (icon) icoStyle.push("background:url(", icon, ") 0 0 no-repeat;");
				if (setting.view.showIcon == false || !tools.apply(setting.view.showIcon, [setting.treeId, node], true)) {
					icoStyle.push("width:0px;height:0px;");
				}
			}
			return icoStyle.join('');
		},
		makeNodeLineClass: function(setting, node) {
			var lineClass = [];
			if (setting.view.showLine) {
				if (node.level == 0 && node.isFirstNode && node.isLastNode) {
					lineClass.push(consts.line.ROOT);
				} else if (node.level == 0 && node.isFirstNode) {
					lineClass.push(consts.line.ROOTS);
				} else if (node.isLastNode) {
					lineClass.push(consts.line.BOTTOM);
				} else {
					lineClass.push(consts.line.CENTER);
				}
			} else {
				lineClass.push(consts.line.NOLINE);
			}
			if (node.isParent) {
				lineClass.push(node.open ? consts.folder.OPEN : consts.folder.CLOSE);
			} else {
				lineClass.push(consts.folder.DOCU);
			}
			return view.makeNodeLineClassEx(node) + lineClass.join('_');
		},
		makeNodeLineClassEx: function(node) {
			return "button level" + node.level + " switch ";
		},
		makeNodeTarget: function(node) {
			return (node.target || "_blank");
		},
		makeNodeUrl: function(setting, node) {
			var urlKey = setting.data.key.url;
			return node[urlKey] ? node[urlKey] : null;
		},
		makeUlHtml: function(setting, node, html, content) {
			html.push("<ul id='", node.tId, consts.id.UL, "' class='level", node.level, " ", view.makeUlLineClass(setting, node), "' style='display:", (node.open ? "block": "none"),"'>");
			html.push(content);
			html.push("</ul>");
		},
		makeUlLineClass: function(setting, node) {
			return ((setting.view.showLine && !node.isLastNode) ? consts.line.LINE : "");
		},
		removeChildNodes: function(setting, node) {
			if (!node) return;
			var childKey = setting.data.key.children,
			nodes = node[childKey];
			if (!nodes) return;

			for (var i = 0, l = nodes.length; i < l; i++) {
				data.removeNodeCache(setting, nodes[i]);
			}
			data.removeSelectedNode(setting);
			delete node[childKey];

			if (!setting.data.keep.parent) {
				node.isParent = false;
				node.open = false;
				var tmp_switchObj = $("#" + node.tId + consts.id.SWITCH),
				tmp_icoObj = $("#" + node.tId + consts.id.ICON);
				view.replaceSwitchClass(node, tmp_switchObj, consts.folder.DOCU);
				view.replaceIcoClass(node, tmp_icoObj, consts.folder.DOCU);
				$("#" + node.tId + consts.id.UL).remove();
			} else {
				$("#" + node.tId + consts.id.UL).empty();
			}
		},
		removeNode: function(setting, node) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children,
			parentNode = (node.parentTId) ? node.getParentNode() : root;

			node.isFirstNode = false;
			node.isLastNode = false;
			node.getPreNode = function() {return null;};
			node.getNextNode = function() {return null;};

			$("#" + node.tId).remove();
			data.removeNodeCache(setting, node);
			data.removeSelectedNode(setting, node);

			for (var i = 0, l = parentNode[childKey].length; i < l; i++) {
				if (parentNode[childKey][i].tId == node.tId) {
					parentNode[childKey].splice(i, 1);
					break;
				}
			}
			var tmp_ulObj,tmp_switchObj,tmp_icoObj;

			//repair nodes old parent
			if (!setting.data.keep.parent && parentNode[childKey].length < 1) {
				//old parentNode has no child nodes
				parentNode.isParent = false;
				parentNode.open = false;
				tmp_ulObj = $("#" + parentNode.tId + consts.id.UL);
				tmp_switchObj = $("#" + parentNode.tId + consts.id.SWITCH);
				tmp_icoObj = $("#" + parentNode.tId + consts.id.ICON);
				view.replaceSwitchClass(parentNode, tmp_switchObj, consts.folder.DOCU);
				view.replaceIcoClass(parentNode, tmp_icoObj, consts.folder.DOCU);
				tmp_ulObj.css("display", "none");

			} else if (setting.view.showLine && parentNode[childKey].length > 0) {
				//old parentNode has child nodes
				var newLast = parentNode[childKey][parentNode[childKey].length - 1];
				newLast.isLastNode = true;
				newLast.isFirstNode = (parentNode[childKey].length == 1);
				tmp_ulObj = $("#" + newLast.tId + consts.id.UL);
				tmp_switchObj = $("#" + newLast.tId + consts.id.SWITCH);
				tmp_icoObj = $("#" + newLast.tId + consts.id.ICON);
				if (parentNode == root) {
					if (parentNode[childKey].length == 1) {
						//node was root, and ztree has only one root after move node
						view.replaceSwitchClass(newLast, tmp_switchObj, consts.line.ROOT);
					} else {
						var tmp_first_switchObj = $("#" + parentNode[childKey][0].tId + consts.id.SWITCH);
						view.replaceSwitchClass(parentNode[childKey][0], tmp_first_switchObj, consts.line.ROOTS);
						view.replaceSwitchClass(newLast, tmp_switchObj, consts.line.BOTTOM);
					}
				} else {
					view.replaceSwitchClass(newLast, tmp_switchObj, consts.line.BOTTOM);
				}
				tmp_ulObj.removeClass(consts.line.LINE);
			}
		},
		replaceIcoClass: function(node, obj, newName) {
			if (!obj || node.isAjaxing) return;
			var tmpName = obj.attr("class");
			if (tmpName == undefined) return;
			var tmpList = tmpName.split("_");
			switch (newName) {
				case consts.folder.OPEN:
				case consts.folder.CLOSE:
				case consts.folder.DOCU:
					tmpList[tmpList.length-1] = newName;
					break;
			}
			obj.attr("class", tmpList.join("_"));
		},
		replaceSwitchClass: function(node, obj, newName) {
			if (!obj) return;
			var tmpName = obj.attr("class");
			if (tmpName == undefined) return;
			var tmpList = tmpName.split("_");
			switch (newName) {
				case consts.line.ROOT:
				case consts.line.ROOTS:
				case consts.line.CENTER:
				case consts.line.BOTTOM:
				case consts.line.NOLINE:
					tmpList[0] = view.makeNodeLineClassEx(node) + newName;
					break;
				case consts.folder.OPEN:
				case consts.folder.CLOSE:
				case consts.folder.DOCU:
					tmpList[1] = newName;
					break;
			}
			obj.attr("class", tmpList.join("_"));
			if (newName !== consts.folder.DOCU) {
				obj.removeAttr("disabled");
			} else {
				obj.attr("disabled", "disabled");
			}
		},
		selectNode: function(setting, node, addFlag) {
			if (!addFlag) {
				view.cancelPreSelectedNode(setting);
			}
			$("#" + node.tId + consts.id.A).addClass(consts.node.CURSELECTED);
			data.addSelectedNode(setting, node);
		},
		setNodeFontCss: function(setting, treeNode) {
			var aObj = $("#" + treeNode.tId + consts.id.A),
			fontCss = view.makeNodeFontCss(setting, treeNode);
			if (fontCss) {
				aObj.css(fontCss);
			}
		},
		setNodeLineIcos: function(setting, node) {
			if (!node) return;
			var switchObj = $("#" + node.tId + consts.id.SWITCH),
			ulObj = $("#" + node.tId + consts.id.UL),
			icoObj = $("#" + node.tId + consts.id.ICON),
			ulLine = view.makeUlLineClass(setting, node);
			if (ulLine.length==0) {
				ulObj.removeClass(consts.line.LINE);
			} else {
				ulObj.addClass(ulLine);
			}
			switchObj.attr("class", view.makeNodeLineClass(setting, node));
			if (node.isParent) {
				switchObj.removeAttr("disabled");
			} else {
				switchObj.attr("disabled", "disabled");
			}
			icoObj.removeAttr("style");
			icoObj.attr("style", view.makeNodeIcoStyle(setting, node));
			icoObj.attr("class", view.makeNodeIcoClass(setting, node));
		},
		setNodeName: function(setting, node) {
			var nameKey = setting.data.key.name,
			titleKey = data.getTitleKey(setting),
			nObj = $("#" + node.tId + consts.id.SPAN);
			nObj.empty();
			if (setting.view.nameIsHTML) {
				nObj.html(node[nameKey]);
			} else {
				nObj.text(node[nameKey]);
			}
			if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle) && node[titleKey]) {
				var aObj = $("#" + node.tId + consts.id.A);
				aObj.attr("title", node[titleKey]);
			}
		},
		setNodeTarget: function(node) {
			var aObj = $("#" + node.tId + consts.id.A);
			aObj.attr("target", view.makeNodeTarget(node));
		},
		setNodeUrl: function(setting, node) {
			var aObj = $("#" + node.tId + consts.id.A),
			url = view.makeNodeUrl(setting, node);
			if (url == null || url.length == 0) {
				aObj.removeAttr("href");
			} else {
				aObj.attr("href", url);
			}
		},
		switchNode: function(setting, node) {
			if (node.open || !tools.canAsync(setting, node)) {
				view.expandCollapseNode(setting, node, !node.open);
			} else if (setting.async.enable) {
				if (!view.asyncNode(setting, node)) {
					view.expandCollapseNode(setting, node, !node.open);
					return;
				}
			} else if (node) {
				view.expandCollapseNode(setting, node, !node.open);
			}
		}
	};
	// zTree defind
	$.fn.zTree = {
		consts : _consts,
		_z : {
			tools: tools,
			view: view,
			event: event,
			data: data
		},
		getZTreeObj: function(treeId) {
			var o = data.getZTreeTools(treeId);
			return o ? o : null;
		},
		init: function(obj, zSetting, zNodes) {
			var setting = tools.clone(_setting);
			$.extend(true, setting, zSetting);
			setting.treeId = obj.attr("id");
			setting.treeObj = obj;
			setting.treeObj.empty();
			settings[setting.treeId] = setting;
			if ($.browser.msie && parseInt($.browser.version)<7) {
				setting.view.expandSpeed = "";
			}

			data.initRoot(setting);
			var root = data.getRoot(setting),
			childKey = setting.data.key.children;
			zNodes = zNodes ? tools.clone(tools.isArray(zNodes)? zNodes : [zNodes]) : [];
			if (setting.data.simpleData.enable) {
				root[childKey] = data.transformTozTreeFormat(setting, zNodes);
			} else {
				root[childKey] = zNodes;
			}

			data.initCache(setting);
			event.bindTree(setting);
			event.bindEvent(setting);
			
			var zTreeTools = {
				setting : setting,
				addNodes : function(parentNode, newNodes, isSilent) {
					if (!newNodes) return null;
					if (!parentNode) parentNode = null;
					if (parentNode && !parentNode.isParent && setting.data.keep.leaf) return null;
					var xNewNodes = tools.clone(tools.isArray(newNodes)? newNodes: [newNodes]);
					function addCallback() {
						view.addNodes(setting, parentNode, xNewNodes, (isSilent==true));
					}

					if (tools.canAsync(setting, parentNode)) {
						view.asyncNode(setting, parentNode, isSilent, addCallback);
					} else {
						addCallback();
					}
					return xNewNodes;
				},
				cancelSelectedNode : function(node) {
					view.cancelPreSelectedNode(this.setting, node);
				},
				expandAll : function(expandFlag) {
					expandFlag = !!expandFlag;
					view.expandCollapseSonNode(this.setting, null, expandFlag, true);
					return expandFlag;
				},
				expandNode : function(node, expandFlag, sonSign, focus, callbackFlag) {
					if (!node || !node.isParent) return null;
					if (expandFlag !== true && expandFlag !== false) {
						expandFlag = !node.open;
					}
					callbackFlag = !!callbackFlag;

					if (callbackFlag && expandFlag && (tools.apply(setting.callback.beforeExpand, [setting.treeId, node], true) == false)) {
						return null;
					} else if (callbackFlag && !expandFlag && (tools.apply(setting.callback.beforeCollapse, [setting.treeId, node], true) == false)) {
						return null;
					}
					if (expandFlag && node.parentTId) {
						view.expandCollapseParentNode(this.setting, node.getParentNode(), expandFlag, false);
					}
					if (expandFlag === node.open && !sonSign) {
						return null;
					}
					
					data.getRoot(setting).expandTriggerFlag = callbackFlag;
					if (sonSign) {
						view.expandCollapseSonNode(this.setting, node, expandFlag, true, function() {
							if (focus !== false) {$("#" + node.tId).focus().blur();}
						});
					} else {
						node.open = !expandFlag;
						view.switchNode(this.setting, node);
						if (focus !== false) {$("#" + node.tId).focus().blur();}
					}
					return expandFlag;
				},
				getNodes : function() {
					return data.getNodes(this.setting);
				},
				getNodeByParam : function(key, value, parentNode) {
					if (!key) return null;
					return data.getNodeByParam(this.setting, parentNode?parentNode[this.setting.data.key.children]:data.getNodes(this.setting), key, value);
				},
				getNodeByTId : function(tId) {
					return data.getNodeCache(this.setting, tId);
				},
				getNodesByParam : function(key, value, parentNode) {
					if (!key) return null;
					return data.getNodesByParam(this.setting, parentNode?parentNode[this.setting.data.key.children]:data.getNodes(this.setting), key, value);
				},
				getNodesByParamFuzzy : function(key, value, parentNode) {
					if (!key) return null;
					return data.getNodesByParamFuzzy(this.setting, parentNode?parentNode[this.setting.data.key.children]:data.getNodes(this.setting), key, value);
				},
				getNodesByFilter: function(filter, isSingle, parentNode) {
					isSingle = !!isSingle;
					if (!filter || (typeof filter != "function")) return (isSingle ? null : []);
					return data.getNodesByFilter(this.setting, parentNode?parentNode[this.setting.data.key.children]:data.getNodes(this.setting), filter, isSingle);
				},
				getNodeIndex : function(node) {
					if (!node) return null;
					var childKey = setting.data.key.children,
					parentNode = (node.parentTId) ? node.getParentNode() : data.getRoot(this.setting);
					for (var i=0, l = parentNode[childKey].length; i < l; i++) {
						if (parentNode[childKey][i] == node) return i;
					}
					return -1;
				},
				getSelectedNodes : function() {
					var r = [], list = data.getRoot(this.setting).curSelectedList;
					for (var i=0, l=list.length; i<l; i++) {
						r.push(list[i]);
					}
					return r;
				},
				isSelectedNode : function(node) {
					return data.isSelectedNode(this.setting, node);
				},
				reAsyncChildNodes : function(parentNode, reloadType, isSilent) {
					if (!this.setting.async.enable) return;
					var isRoot = !parentNode;
					if (isRoot) {
						parentNode = data.getRoot(this.setting);
					}
					if (reloadType=="refresh") {
						parentNode[this.setting.data.key.children] = [];
						if (isRoot) {
							this.setting.treeObj.empty();
						} else {
							var ulObj = $("#" + parentNode.tId + consts.id.UL);
							ulObj.empty();
						}
					}
					view.asyncNode(this.setting, isRoot? null:parentNode, !!isSilent);
				},
				refresh : function() {
					this.setting.treeObj.empty();
					var root = data.getRoot(this.setting),
					nodes = root[this.setting.data.key.children]
					data.initRoot(this.setting);
					root[this.setting.data.key.children] = nodes
					data.initCache(this.setting);
					view.createNodes(this.setting, 0, root[this.setting.data.key.children]);
				},
				removeChildNodes : function(node) {
					if (!node) return null;
					var childKey = setting.data.key.children,
					nodes = node[childKey];
					view.removeChildNodes(setting, node);
					return nodes ? nodes : null;
				},
				removeNode : function(node, callbackFlag) {
					if (!node) return;
					callbackFlag = !!callbackFlag;
					if (callbackFlag && tools.apply(setting.callback.beforeRemove, [setting.treeId, node], true) == false) return;
					view.removeNode(setting, node);
					if (callbackFlag) {
						this.setting.treeObj.trigger(consts.event.REMOVE, [setting.treeId, node]);
					}
				},
				selectNode : function(node, addFlag) {
					if (!node) return;
					if (tools.uCanDo(this.setting)) {
						addFlag = setting.view.selectedMulti && addFlag;
						if (node.parentTId) {
							view.expandCollapseParentNode(this.setting, node.getParentNode(), true, false, function() {
								$("#" + node.tId).focus().blur();
							});
						} else {
							$("#" + node.tId).focus().blur();
						}
						view.selectNode(this.setting, node, addFlag);
					}
				},
				transformTozTreeNodes : function(simpleNodes) {
					return data.transformTozTreeFormat(this.setting, simpleNodes);
				},
				transformToArray : function(nodes) {
					return data.transformToArrayFormat(this.setting, nodes);
				},
				updateNode : function(node, checkTypeFlag) {
					if (!node) return;
					var nObj = $("#" + node.tId);
					if (nObj.get(0) && tools.uCanDo(this.setting)) {
						view.setNodeName(this.setting, node);
						view.setNodeTarget(node);
						view.setNodeUrl(this.setting, node);
						view.setNodeLineIcos(this.setting, node);
						view.setNodeFontCss(this.setting, node);
					}
				}
			}
			root.treeTools = zTreeTools;
			data.setZTreeTools(setting, zTreeTools);

			if (root[childKey] && root[childKey].length > 0) {
				view.createNodes(setting, 0, root[childKey]);
			} else if (setting.async.enable && setting.async.url && setting.async.url !== '') {
				view.asyncNode(setting);
			}
			return zTreeTools;
		}
	};

	var zt = $.fn.zTree,
	consts = zt.consts;
})(jQuery);

/*
 * JQuery zTree excheck 3.2
 * http://code.google.com/p/jquerytree/
 *
 * Copyright (c) 2010 Hunter.z (baby666.cn)
 *
 * Licensed same as jquery - MIT License
 * http://www.opensource.org/licenses/mit-license.php
 *
 * email: hunter.z@263.net
 * Date: 2012-05-13
 */
(function($){
	//default consts of excheck
	var _consts = {
		event: {
			CHECK: "ztree_check"
		},
		id: {
			CHECK: "_check"
		},
		checkbox: {
			STYLE: "checkbox",
			DEFAULT: "chk",
			DISABLED: "disable",
			FALSE: "false",
			TRUE: "true",
			FULL: "full",
			PART: "part",
			FOCUS: "focus"
		},
		radio: {
			STYLE: "radio",
			TYPE_ALL: "all",
			TYPE_LEVEL: "level"
		}
	},
	//default setting of excheck
	_setting = {
		check: {
			enable: false,
			autoCheckTrigger: false,
			chkStyle: _consts.checkbox.STYLE,
			nocheckInherit: false,
			radioType: _consts.radio.TYPE_LEVEL,
			chkboxType: {
				"Y": "ps",
				"N": "ps"
			}
		},
		data: {
			key: {
				checked: "checked"
			}
		},
		callback: {
			beforeCheck:null,
			onCheck:null
		}
	},
	//default root of excheck
	_initRoot = function (setting) {
		var r = data.getRoot(setting);
		r.radioCheckedList = [];
	},
	//default cache of excheck
	_initCache = function(treeId) {},
	//default bind event of excheck
	_bindEvent = function(setting) {
		var o = setting.treeObj,
		c = consts.event;
		o.unbind(c.CHECK);
		o.bind(c.CHECK, function (event, treeId, node) {
			tools.apply(setting.callback.onCheck, [event, treeId, node]);
		});
	},
	//default event proxy of excheck
	_eventProxy = function(e) {
		var target = e.target,
		setting = data.getSetting(e.data.treeId),
		tId = "", node = null,
		nodeEventType = "", treeEventType = "",
		nodeEventCallback = null, treeEventCallback = null;

		if (tools.eqs(e.type, "mouseover")) {
			if (setting.check.enable && tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.CHECK) !== null) {
				tId = target.parentNode.id;
				nodeEventType = "mouseoverCheck";
			}
		} else if (tools.eqs(e.type, "mouseout")) {
			if (setting.check.enable && tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.CHECK) !== null) {
				tId = target.parentNode.id;
				nodeEventType = "mouseoutCheck";
			}
		} else if (tools.eqs(e.type, "click")) {
			if (setting.check.enable && tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.CHECK) !== null) {
				tId = target.parentNode.id;
				nodeEventType = "checkNode";
			}
		}
		if (tId.length>0) {
			node = data.getNodeCache(setting, tId);
			switch (nodeEventType) {
				case "checkNode" :
					nodeEventCallback = _handler.onCheckNode;
					break;
				case "mouseoverCheck" :
					nodeEventCallback = _handler.onMouseoverCheck;
					break;
				case "mouseoutCheck" :
					nodeEventCallback = _handler.onMouseoutCheck;
					break;
			}
		}
		var proxyResult = {
			stop: false,
			node: node,
			nodeEventType: nodeEventType,
			nodeEventCallback: nodeEventCallback,
			treeEventType: treeEventType,
			treeEventCallback: treeEventCallback
		};
		return proxyResult
	},
	//default init node of excheck
	_initNode = function(setting, level, n, parentNode, isFirstNode, isLastNode, openFlag) {
		if (!n) return;
		var checkedKey = setting.data.key.checked;
		if (typeof n[checkedKey] == "string") n[checkedKey] = tools.eqs(n[checkedKey], "true");
		n[checkedKey] = !!n[checkedKey];
		n.checkedOld = n[checkedKey];
		n.nocheck = !!n.nocheck || (setting.check.nocheckInherit && parentNode && !!parentNode.nocheck);
		n.chkDisabled = !!n.chkDisabled || (parentNode && !!parentNode.chkDisabled);
		if (typeof n.halfCheck == "string") n.halfCheck = tools.eqs(n.halfCheck, "true");
		n.halfCheck = !!n.halfCheck;
		n.check_Child_State = -1;
		n.check_Focus = false;
		n.getCheckStatus = function() {return data.getCheckStatus(setting, n);};
		if (isLastNode) {
			data.makeChkFlag(setting, parentNode);
		}
	},
	//add dom for check
	_beforeA = function(setting, node, html) {
		var checkedKey = setting.data.key.checked;
		if (setting.check.enable) {
			data.makeChkFlag(setting, node);
			if (setting.check.chkStyle == consts.radio.STYLE && setting.check.radioType == consts.radio.TYPE_ALL && node[checkedKey] ) {
				var r = data.getRoot(setting);
				r.radioCheckedList.push(node);
			}
			html.push("<span ID='", node.tId, consts.id.CHECK, "' class='", view.makeChkClass(setting, node), "' treeNode", consts.id.CHECK, (node.nocheck === true?" style='display:none;'":""),"></span>");
		}
	},
	//update zTreeObj, add method of check
	_zTreeTools = function(setting, zTreeTools) {
		zTreeTools.checkNode = function(node, checked, checkTypeFlag, callbackFlag) {
			var checkedKey = this.setting.data.key.checked;
			if (node.chkDisabled === true) return;
			if (checked !== true && checked !== false) {
				checked = !node[checkedKey];
			}
			callbackFlag = !!callbackFlag;
			
			if (node[checkedKey] === checked && !checkTypeFlag) {
				return;
			} else if (callbackFlag && tools.apply(this.setting.callback.beforeCheck, [this.setting.treeId, node], true) == false) {
				return;
			}
			if (tools.uCanDo(this.setting) && this.setting.check.enable && node.nocheck !== true) {
				node[checkedKey] = checked;
				var checkObj = $("#" + node.tId + consts.id.CHECK);
				if (checkTypeFlag || this.setting.check.chkStyle === consts.radio.STYLE) view.checkNodeRelation(this.setting, node);
				view.setChkClass(this.setting, checkObj, node);
				view.repairParentChkClassWithSelf(this.setting, node);
				if (callbackFlag) {
					setting.treeObj.trigger(consts.event.CHECK, [setting.treeId, node]);
				}
			}
		}

		zTreeTools.checkAllNodes = function(checked) {
			view.repairAllChk(this.setting, !!checked);
		}

		zTreeTools.getCheckedNodes = function(checked) {
			var childKey = this.setting.data.key.children;
			checked = (checked !== false);
			return data.getTreeCheckedNodes(this.setting, data.getRoot(setting)[childKey], checked);
		}

		zTreeTools.getChangeCheckedNodes = function() {
			var childKey = this.setting.data.key.children;
			return data.getTreeChangeCheckedNodes(this.setting, data.getRoot(setting)[childKey]);
		}

		zTreeTools.setChkDisabled = function(node, disabled) {
			disabled = !!disabled;
			view.repairSonChkDisabled(this.setting, node, disabled);
			if (!disabled) view.repairParentChkDisabled(this.setting, node, disabled);
		}

		var _updateNode = zTreeTools.updateNode;
		zTreeTools.updateNode = function(node, checkTypeFlag) {
			if (_updateNode) _updateNode.apply(zTreeTools, arguments);
			if (!node || !this.setting.check.enable) return;
			var nObj = $("#" + node.tId);
			if (nObj.get(0) && tools.uCanDo(this.setting)) {
				var checkObj = $("#" + node.tId + consts.id.CHECK);
				if (checkTypeFlag == true || this.setting.check.chkStyle === consts.radio.STYLE) view.checkNodeRelation(this.setting, node);
				view.setChkClass(this.setting, checkObj, node);
				view.repairParentChkClassWithSelf(this.setting, node);
			}
		}
	},
	//method of operate data
	_data = {
		getRadioCheckedList: function(setting) {
			var checkedList = data.getRoot(setting).radioCheckedList;
			for (var i=0, j=checkedList.length; i<j; i++) {
				if(!data.getNodeCache(setting, checkedList[i].tId)) {
					checkedList.splice(i, 1);
					i--; j--;
				}
			}
			return checkedList;
		},
		getCheckStatus: function(setting, node) {
			if (!setting.check.enable || node.nocheck) return null;
			var checkedKey = setting.data.key.checked,
			r = {
				checked: node[checkedKey],
				half: node.halfCheck ? node.halfCheck : (setting.check.chkStyle == consts.radio.STYLE ? (node.check_Child_State === 2) : (node[checkedKey] ? (node.check_Child_State > -1 && node.check_Child_State < 2) : (node.check_Child_State > 0)))
			};
			return r;
		},
		getTreeCheckedNodes: function(setting, nodes, checked, results) {
			if (!nodes) return [];
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked;
			results = !results ? [] : results;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i].nocheck !== true && nodes[i][checkedKey] == checked) {
					results.push(nodes[i]);
				}
				data.getTreeCheckedNodes(setting, nodes[i][childKey], checked, results);
			}
			return results;
		},
		getTreeChangeCheckedNodes: function(setting, nodes, results) {
			if (!nodes) return [];
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked;
			results = !results ? [] : results;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i].nocheck !== true && nodes[i][checkedKey] != nodes[i].checkedOld) {
					results.push(nodes[i]);
				}
				data.getTreeChangeCheckedNodes(setting, nodes[i][childKey], results);
			}
			return results;
		},
		makeChkFlag: function(setting, node) {
			if (!node) return;
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			chkFlag = -1;
			if (node[childKey]) {
				var start = false;
				for (var i = 0, l = node[childKey].length; i < l; i++) {
					var cNode = node[childKey][i];
					var tmp = -1;
					if (setting.check.chkStyle == consts.radio.STYLE) {
						if (cNode.nocheck === true) {
							tmp = cNode.check_Child_State;
						} else if (cNode.halfCheck === true) {
							tmp = 2;
						} else if (cNode.nocheck !== true && cNode[checkedKey]) {
							tmp = 2;
						} else {
							tmp = cNode.check_Child_State > 0 ? 2:0;
						}
						if (tmp == 2) {
							chkFlag = 2; break;
						} else if (tmp == 0){
							chkFlag = 0;
						}
					} else if (setting.check.chkStyle == consts.checkbox.STYLE) {
						if (cNode.nocheck === true) {
							tmp = cNode.check_Child_State;
						} else if (cNode.halfCheck === true) {
							tmp = 1;
						} else if (cNode.nocheck !== true && cNode[checkedKey] ) {
							tmp = (cNode.check_Child_State === -1 || cNode.check_Child_State === 2) ? 2 : 1;
						} else {
							tmp = (cNode.check_Child_State > 0) ? 1 : 0;
						}
						if (tmp === 1) {
							chkFlag = 1; break;
						} else if (tmp === 2 && start && tmp !== chkFlag) {
							chkFlag = 1; break;
						} else if (chkFlag === 2 && tmp > -1 && tmp < 2) {
							chkFlag = 1; break;
						} else if (tmp > -1) {
							chkFlag = tmp;
						}
						if (!start) start = (cNode.nocheck !== true);
					}
				}
			}
			node.check_Child_State = chkFlag;
		}
	},
	//method of event proxy
	_event = {

	},
	//method of event handler
	_handler = {
		onCheckNode: function (event, node) {
			if (node.chkDisabled === true) return false;
			var setting = data.getSetting(event.data.treeId),
			checkedKey = setting.data.key.checked;
			if (tools.apply(setting.callback.beforeCheck, [setting.treeId, node], true) == false) return true;
			node[checkedKey] = !node[checkedKey];
			view.checkNodeRelation(setting, node);
			var checkObj = $("#" + node.tId + consts.id.CHECK);
			view.setChkClass(setting, checkObj, node);
			view.repairParentChkClassWithSelf(setting, node);
			setting.treeObj.trigger(consts.event.CHECK, [setting.treeId, node]);
			return true;
		},
		onMouseoverCheck: function(event, node) {
			if (node.chkDisabled === true) return false;
			var setting = data.getSetting(event.data.treeId),
			checkObj = $("#" + node.tId + consts.id.CHECK);
			node.check_Focus = true;
			view.setChkClass(setting, checkObj, node);
			return true;
		},
		onMouseoutCheck: function(event, node) {
			if (node.chkDisabled === true) return false;
			var setting = data.getSetting(event.data.treeId),
			checkObj = $("#" + node.tId + consts.id.CHECK);
			node.check_Focus = false;
			view.setChkClass(setting, checkObj, node);
			return true;
		}
	},
	//method of tools for zTree
	_tools = {

	},
	//method of operate ztree dom
	_view = {
		checkNodeRelation: function(setting, node) {
			var pNode, i, l,
			childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			r = consts.radio;
			if (setting.check.chkStyle == r.STYLE) {
				var checkedList = data.getRadioCheckedList(setting);
				if (node[checkedKey]) {
					if (setting.check.radioType == r.TYPE_ALL) {
						for (i = checkedList.length-1; i >= 0; i--) {
							pNode = checkedList[i];
							pNode[checkedKey] = false;
							checkedList.splice(i, 1);

							view.setChkClass(setting, $("#" + pNode.tId + consts.id.CHECK), pNode);
							if (pNode.parentTId != node.parentTId) {
								view.repairParentChkClassWithSelf(setting, pNode);
							}
						}
						checkedList.push(node);
					} else {
						var parentNode = (node.parentTId) ? node.getParentNode() : data.getRoot(setting);
						for (i = 0, l = parentNode[childKey].length; i < l; i++) {
							pNode = parentNode[childKey][i];
							if (pNode[checkedKey] && pNode != node) {
								pNode[checkedKey] = false;
								view.setChkClass(setting, $("#" + pNode.tId + consts.id.CHECK), pNode);
							}
						}
					}
				} else if (setting.check.radioType == r.TYPE_ALL) {
					for (i = 0, l = checkedList.length; i < l; i++) {
						if (node == checkedList[i]) {
							checkedList.splice(i, 1);
							break;
						}
					}
				}

			} else {
				if (node[checkedKey] && (!node[childKey] || node[childKey].length==0 || setting.check.chkboxType.Y.indexOf("s") > -1)) {
					view.setSonNodeCheckBox(setting, node, true);
				}
				if (!node[checkedKey] && (!node[childKey] || node[childKey].length==0 || setting.check.chkboxType.N.indexOf("s") > -1)) {
					view.setSonNodeCheckBox(setting, node, false);
				}
				if (node[checkedKey] && setting.check.chkboxType.Y.indexOf("p") > -1) {
					view.setParentNodeCheckBox(setting, node, true);
				}
				if (!node[checkedKey] && setting.check.chkboxType.N.indexOf("p") > -1) {
					view.setParentNodeCheckBox(setting, node, false);
				}
			}
		},
		makeChkClass: function(setting, node) {
			var checkedKey = setting.data.key.checked,
			c = consts.checkbox, r = consts.radio,
			fullStyle = "";
			if (node.chkDisabled === true) {
				fullStyle = c.DISABLED;
			} else if (node.halfCheck) {
				fullStyle = c.PART;
			} else if (setting.check.chkStyle == r.STYLE) {
				fullStyle = (node.check_Child_State < 1)? c.FULL:c.PART;
			} else {
				fullStyle = node[checkedKey] ? ((node.check_Child_State === 2 || node.check_Child_State === -1) ? c.FULL:c.PART) : ((node.check_Child_State < 1)? c.FULL:c.PART);
			}
			var chkName = setting.check.chkStyle + "_" + (node[checkedKey] ? c.TRUE : c.FALSE) + "_" + fullStyle;
			chkName = (node.check_Focus && node.chkDisabled !== true) ? chkName + "_" + c.FOCUS : chkName;
			return "button " + c.DEFAULT + " " + chkName;
		},
		repairAllChk: function(setting, checked) {
			if (setting.check.enable && setting.check.chkStyle === consts.checkbox.STYLE) {
				var checkedKey = setting.data.key.checked,
				childKey = setting.data.key.children,
				root = data.getRoot(setting);
				for (var i = 0, l = root[childKey].length; i<l ; i++) {
					var node = root[childKey][i];
					if (node.nocheck !== true) {
						node[checkedKey] = checked;
					}
					view.setSonNodeCheckBox(setting, node, checked);
				}
			}
		},
		repairChkClass: function(setting, node) {
			if (!node) return;
			data.makeChkFlag(setting, node);
			var checkObj = $("#" + node.tId + consts.id.CHECK);
			view.setChkClass(setting, checkObj, node);
		},
		repairParentChkClass: function(setting, node) {
			if (!node || !node.parentTId) return;
			var pNode = node.getParentNode();
			view.repairChkClass(setting, pNode);
			view.repairParentChkClass(setting, pNode);
		},
		repairParentChkClassWithSelf: function(setting, node) {
			if (!node) return;
			var childKey = setting.data.key.children;
			if (node[childKey] && node[childKey].length > 0) {
				view.repairParentChkClass(setting, node[childKey][0]);
			} else {
				view.repairParentChkClass(setting, node);
			}
		},
		repairSonChkDisabled: function(setting, node, chkDisabled) {
			if (!node) return;
			var childKey = setting.data.key.children;
			if (node.chkDisabled != chkDisabled) {
				node.chkDisabled = chkDisabled;
				if (node.nocheck !== true) view.repairChkClass(setting, node);
			}
			if (node[childKey]) {
				for (var i = 0, l = node[childKey].length; i < l; i++) {
					var sNode = node[childKey][i];
					view.repairSonChkDisabled(setting, sNode, chkDisabled);
				}
			}
		},
		repairParentChkDisabled: function(setting, node, chkDisabled) {
			if (!node) return;
			if (node.chkDisabled != chkDisabled) {
				node.chkDisabled = chkDisabled;
				if (node.nocheck !== true) view.repairChkClass(setting, node);
			}
			view.repairParentChkDisabled(setting, node.getParentNode(), chkDisabled);
		},
		setChkClass: function(setting, obj, node) {
			if (!obj) return;
			if (node.nocheck === true) {
				obj.hide();
			} else {
				obj.show();
			}
			obj.removeClass();
			obj.addClass(view.makeChkClass(setting, node));
		},
		setParentNodeCheckBox: function(setting, node, value, srcNode) {
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			checkObj = $("#" + node.tId + consts.id.CHECK);
			if (!srcNode) srcNode = node;
			data.makeChkFlag(setting, node);
			if (node.nocheck !== true && node.chkDisabled !== true) {
				node[checkedKey] = value;
				view.setChkClass(setting, checkObj, node);
				if (setting.check.autoCheckTrigger && node != srcNode && node.nocheck !== true) {
					setting.treeObj.trigger(consts.event.CHECK, [setting.treeId, node]);
				}
			}
			if (node.parentTId) {
				var pSign = true;
				if (!value) {
					var pNodes = node.getParentNode()[childKey];
					for (var i = 0, l = pNodes.length; i < l; i++) {
						if ((pNodes[i].nocheck !== true && pNodes[i][checkedKey])
						|| (pNodes[i].nocheck === true && pNodes[i].check_Child_State > 0)) {
							pSign = false;
							break;
						}
					}
				}
				if (pSign) {
					view.setParentNodeCheckBox(setting, node.getParentNode(), value, srcNode);
				}
			}
		},
		setSonNodeCheckBox: function(setting, node, value, srcNode) {
			if (!node) return;
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			checkObj = $("#" + node.tId + consts.id.CHECK);
			if (!srcNode) srcNode = node;

			var hasDisable = false;
			if (node[childKey]) {
				for (var i = 0, l = node[childKey].length; i < l && node.chkDisabled !== true; i++) {
					var sNode = node[childKey][i];
					view.setSonNodeCheckBox(setting, sNode, value, srcNode);
					if (sNode.chkDisabled === true) hasDisable = true;
				}
			}
			
			if (node != data.getRoot(setting) && node.chkDisabled !== true) {
				if (hasDisable && node.nocheck !== true) {
					data.makeChkFlag(setting, node);
				}
				if (node.nocheck !== true) {
					node[checkedKey] = value;
					if (!hasDisable) node.check_Child_State = (node[childKey] && node[childKey].length > 0) ? (value ? 2 : 0) : -1;
				} else {
					node.check_Child_State = -1;
				}
				view.setChkClass(setting, checkObj, node);
				if (setting.check.autoCheckTrigger && node != srcNode && node.nocheck !== true) {
					setting.treeObj.trigger(consts.event.CHECK, [setting.treeId, node]);
				}
			}

		}
	},

	_z = {
		tools: _tools,
		view: _view,
		event: _event,
		data: _data
	};
	$.extend(true, $.fn.zTree.consts, _consts);
	$.extend(true, $.fn.zTree._z, _z);

	var zt = $.fn.zTree,
	tools = zt._z.tools,
	consts = zt.consts,
	view = zt._z.view,
	data = zt._z.data,
	event = zt._z.event;

	data.exSetting(_setting);
	data.addInitBind(_bindEvent);
	data.addInitCache(_initCache);
	data.addInitNode(_initNode);
	data.addInitProxy(_eventProxy);
	data.addInitRoot(_initRoot);
	data.addBeforeA(_beforeA);
	data.addZTreeTools(_zTreeTools);

	var _createNodes = view.createNodes;
	view.createNodes = function(setting, level, nodes, parentNode) {
		if (_createNodes) _createNodes.apply(view, arguments);
		if (!nodes) return;
		view.repairParentChkClassWithSelf(setting, parentNode);
	}
	var _removeNode = view.removeNode;
	view.removeNode = function(setting, node) {
		var parentNode = node.getParentNode();
		if (_removeNode) _removeNode.apply(view, arguments);
		if (!node || !parentNode) return;
		view.repairChkClass(setting, parentNode);
		view.repairParentChkClass(setting, parentNode);
	}
})(jQuery);


/*grid.locale-cn.js*/
;(function($){
/**
 * jqGrid Chinese Translation for v4.2
 * henryyan 2011.11.30
 * http://www.wsria.com
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * 
 * update 2011.11.30
 *		add double u3000 SPACE for search:odata to fix SEARCH box display err when narrow width from only use of eq/ne/cn/in/lt/gt operator under IE6/7
**/
$.jgrid = $.jgrid || {};
$.extend($.jgrid,{
	defaults : {
		recordtext: "{0} - {1}\u3000共 {2} 条",	// 共字前是全角空格
		emptyrecords: "无数据显示",
		loadtext: "读取中...",
		pgtext : " {0} 共 {1} 页"
	},
	search : {
		caption: "搜索...",
		Find: "查找",
		Reset: "重置",
		odata : ['等于\u3000\u3000', '不等\u3000\u3000', '小于\u3000\u3000', '小于等于','大于\u3000\u3000','大于等于', 
			'开始于','不开始于','属于\u3000\u3000','不属于','结束于','不结束于','包含\u3000\u3000','不包含','空值于\u3000\u3000','非空值'],
		groupOps: [	{ op: "AND", text: "所有" },	{ op: "OR",  text: "任一" }	],
		matchText: " 匹配",
		rulesText: " 规则"
	},
	edit : {
		addCaption: "添加记录",
		editCaption: "编辑记录",
		bSubmit: "提交",
		bCancel: "取消",
		bClose: "关闭",
		saveData: "数据已改变，是否保存？",
		bYes : "是",
		bNo : "否",
		bExit : "取消",
		msg: {
			required:"此字段必需",
			number:"请输入有效数字",
			minValue:"输值必须大于等于 ",
			maxValue:"输值必须小于等于 ",
			email: "这不是有效的e-mail地址",
			integer: "请输入有效整数",
			date: "请输入有效时间",
			url: "无效网址。前缀必须为 ('http://' 或 'https://')",
			nodefined : " 未定义！",
			novalue : " 需要返回值！",
			customarray : "自定义函数需要返回数组！",
			customfcheck : "Custom function should be present in case of custom checking!"
			
		}
	},
	view : {
		caption: "查看记录",
		bClose: "关闭"
	},
	del : {
		caption: "删除",
		msg: "删除所选记录？",
		bSubmit: "删除",
		bCancel: "取消"
	},
	nav : {
		edittext: "",
		edittitle: "编辑所选记录",
		addtext:"",
		addtitle: "添加新记录",
		deltext: "",
		deltitle: "删除所选记录",
		searchtext: "",
		searchtitle: "查找",
		refreshtext: "",
		refreshtitle: "刷新表格",
		alertcap: "注意",
		alerttext: "请选择记录",
		viewtext: "",
		viewtitle: "查看所选记录"
	},
	col : {
		caption: "选择列",
		bSubmit: "确定",
		bCancel: "取消"
	},
	errors : {
		errcap : "错误",
		nourl : "没有设置url",
		norecords: "没有要处理的记录",
		model : "colNames 和 colModel 长度不等！"
	},
	formatter : {
		integer : {thousandsSeparator: " ", defaultValue: '0'},
		number : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'},
		currency : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0.00'},
		date : {
			dayNames:   [
				"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat",
		         "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
			],
			monthNames: [
				"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
				"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
			],
			AmPm : ["am","pm","AM","PM"],
			S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th'},
			srcformat: 'Y-m-d',
			newformat: 'm-d-Y',
			masks : {
				ISO8601Long:"Y-m-d H:i:s",
				ISO8601Short:"Y-m-d",
				ShortDate: "Y/j/n",
				LongDate: "l, F d, Y",
				FullDateTime: "l, F d, Y g:i:s A",
				MonthDay: "F d",
				ShortTime: "g:i A",
				LongTime: "g:i:s A",
				SortableDateTime: "Y-m-d\\TH:i:s",
				UniversalSortableDateTime: "Y-m-d H:i:sO",
				YearMonth: "F, Y"
			},
			reformatAfterEdit : false
		},
		baseLinkUrl: '',
		showAction: '',
		target: '',
		checkbox : {disabled:true},
		idName : 'id'
	}
});
})(jQuery);


/*jquery.jqGrid.min.js*/
/* 
* jqGrid  4.3.3 - jQuery Grid 
* Copyright (c) 2008, Tony Tomov, tony@trirand.com 
* Dual licensed under the MIT and GPL licenses 
* http://www.opensource.org/licenses/mit-license.php 
* http://www.gnu.org/licenses/gpl-2.0.html 
* Date:2012-05-31 
* Modules: grid.base.js; jquery.fmatter.js; grid.custom.js; grid.common.js; grid.formedit.js; grid.filter.js; grid.inlinedit.js; grid.celledit.js; jqModal.js; jqDnR.js; grid.subgrid.js; grid.grouping.js; grid.treegrid.js; grid.import.js; JsonXml.js; grid.tbltogrid.js; grid.jqueryui.js; 
*/
/*
 jqGrid  4.3.3  - jQuery Grid
 Copyright (c) 2008, Tony Tomov, tony@trirand.com
 Dual licensed under the MIT and GPL licenses
 http://www.opensource.org/licenses/mit-license.php
 http://www.gnu.org/licenses/gpl-2.0.html
 Date: 2012-05-31
*/
(function(b){b.jgrid=b.jgrid||{};b.extend(b.jgrid,{version:"4.3.3",htmlDecode:function(b){return b&&("&nbsp;"==b||"&#160;"==b||1===b.length&&160===b.charCodeAt(0))?"":!b?b:(""+b).replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&quot;/g,'"').replace(/&amp;/g,"&")},htmlEncode:function(b){return!b?b:(""+b).replace(/&/g,"&amp;").replace(/\"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;")},format:function(d){var f=b.makeArray(arguments).slice(1);void 0===d&&(d="");return d.replace(/\{(\d+)\}/g,
function(b,e){return f[e]})},getCellIndex:function(d){d=b(d);if(d.is("tr"))return-1;d=(!d.is("td")&&!d.is("th")?d.closest("td,th"):d)[0];return b.browser.msie?b.inArray(d,d.parentNode.cells):d.cellIndex},stripHtml:function(b){var b=b+"",f=/<("[^"]*"|'[^']*'|[^'">])*>/gi;return b?(b=b.replace(f,""))&&"&nbsp;"!==b&&"&#160;"!==b?b.replace(/\"/g,"'"):"":b},stripPref:function(d,f){var c=b.type(d);if("string"==c||"number"==c)d=""+d,f=""!==d?(""+f).replace(""+d,""):f;return f},stringToDoc:function(b){var f;
if("string"!==typeof b)return b;try{f=(new DOMParser).parseFromString(b,"text/xml")}catch(c){f=new ActiveXObject("Microsoft.XMLDOM"),f.async=!1,f.loadXML(b)}return f&&f.documentElement&&"parsererror"!=f.documentElement.tagName?f:null},parse:function(d){"while(1);"==d.substr(0,9)&&(d=d.substr(9));"/*"==d.substr(0,2)&&(d=d.substr(2,d.length-4));d||(d="{}");return!0===b.jgrid.useJSON&&"object"===typeof JSON&&"function"===typeof JSON.parse?JSON.parse(d):eval("("+d+")")},parseDate:function(d,f){var c=
{m:1,d:1,y:1970,h:0,i:0,s:0,u:0},e,a,i;e=/[\\\/:_;.,\t\T\s-]/;if(f&&null!==f&&void 0!==f){f=b.trim(f);f=f.split(e);void 0!==b.jgrid.formatter.date.masks[d]&&(d=b.jgrid.formatter.date.masks[d]);var d=d.split(e),g=b.jgrid.formatter.date.monthNames,h=b.jgrid.formatter.date.AmPm,j=function(a,b){0===a?12===b&&(b=0):12!==b&&(b+=12);return b};e=0;for(a=d.length;e<a;e++)"M"==d[e]&&(i=b.inArray(f[e],g),-1!==i&&12>i&&(f[e]=i+1,c.m=f[e])),"F"==d[e]&&(i=b.inArray(f[e],g),-1!==i&&11<i&&(f[e]=i+1-12,c.m=f[e])),
"a"==d[e]&&(i=b.inArray(f[e],h),-1!==i&&2>i&&f[e]==h[i]&&(f[e]=i,c.h=j(f[e],c.h))),"A"==d[e]&&(i=b.inArray(f[e],h),-1!==i&&1<i&&f[e]==h[i]&&(f[e]=i-2,c.h=j(f[e],c.h))),void 0!==f[e]&&(c[d[e].toLowerCase()]=parseInt(f[e],10));c.m=parseInt(c.m,10)-1;e=c.y;70<=e&&99>=e?c.y=1900+c.y:0<=e&&69>=e&&(c.y=2E3+c.y);void 0!==c.j&&(c.d=c.j);void 0!==c.n&&(c.m=parseInt(c.n,10)-1)}return new Date(c.y,c.m,c.d,c.h,c.i,c.s,c.u)},jqID:function(b){return(""+b).replace(/[!"#$%&'()*+,.\/:;<=>?@\[\\\]\^`{|}~]/g,"\\$&")},
guid:1,uidPref:"jqg",randId:function(d){return(d?d:b.jgrid.uidPref)+b.jgrid.guid++},getAccessor:function(b,f){var c,e,a=[],i;if("function"===typeof f)return f(b);c=b[f];if(void 0===c)try{if("string"===typeof f&&(a=f.split(".")),i=a.length)for(c=b;c&&i--;)e=a.shift(),c=c[e]}catch(g){}return c},getXmlData:function(d,f,c){var e="string"===typeof f?f.match(/^(.*)\[(\w+)\]$/):null;if("function"===typeof f)return f(d);if(e&&e[2])return e[1]?b(e[1],d).attr(e[2]):b(d).attr(e[2]);d=b(f,d);return c?d:0<d.length?
b(d).text():void 0},cellWidth:function(){var d=b("<div class='ui-jqgrid' style='left:10000px'><table class='ui-jqgrid-btable' style='width:5px;'><tr class='jqgrow'><td style='width:5px;'></td></tr></table></div>"),f=d.appendTo("body").find("td").width();d.remove();return 5!==f},ajaxOptions:{},from:function(d){return new function(f,c){"string"==typeof f&&(f=b.data(f));var e=this,a=f,i=!0,d=!1,h=c,j=/[\$,%]/g,m=null,k=null,n=0,p=!1,o="",u=[],w=!0;if("object"==typeof f&&f.push)0<f.length&&(w="object"!=
typeof f[0]?!1:!0);else throw"data provides is not an array";this._hasData=function(){return null===a?!1:0===a.length?!1:!0};this._getStr=function(a){var b=[];d&&b.push("jQuery.trim(");b.push("String("+a+")");d&&b.push(")");i||b.push(".toLowerCase()");return b.join("")};this._strComp=function(a){return"string"==typeof a?".toString()":""};this._group=function(a,b){return{field:a.toString(),unique:b,items:[]}};this._toStr=function(a){d&&(a=b.trim(a));a=a.toString().replace(/\\/g,"\\\\").replace(/\"/g,
'\\"');return i?a:a.toLowerCase()};this._funcLoop=function(e){var c=[];b.each(a,function(a,b){c.push(e(b))});return c};this._append=function(a){var b;h=null===h?"":h+(""===o?" && ":o);for(b=0;b<n;b++)h+="(";p&&(h+="!");h+="("+a+")";p=!1;o="";n=0};this._setCommand=function(a,b){m=a;k=b};this._resetNegate=function(){p=!1};this._repeatCommand=function(a,b){return null===m?e:null!==a&&null!==b?m(a,b):null===k||!w?m(a):m(k,a)};this._equals=function(a,b){return 0===e._compare(a,b,1)};this._compare=function(a,
b,e){var c=Object.prototype.toString;void 0===e&&(e=1);void 0===a&&(a=null);void 0===b&&(b=null);if(null===a&&null===b)return 0;if(null===a&&null!==b)return 1;if(null!==a&&null===b)return-1;if("[object Date]"===c.call(a)&&"[object Date]"===c.call(b))return a<b?-e:a>b?e:0;!i&&"number"!==typeof a&&"number"!==typeof b&&(a=(""+a).toLowerCase(),b=(""+b).toLowerCase());return a<b?-e:a>b?e:0};this._performSort=function(){0!==u.length&&(a=e._doSort(a,0))};this._doSort=function(a,b){var c=u[b].by,i=u[b].dir,
d=u[b].type,f=u[b].datefmt;if(b==u.length-1)return e._getOrder(a,c,i,d,f);b++;c=e._getGroup(a,c,i,d,f);i=[];for(d=0;d<c.length;d++)for(var f=e._doSort(c[d].items,b),g=0;g<f.length;g++)i.push(f[g]);return i};this._getOrder=function(a,c,i,d,f){var g=[],h=[],m="a"==i?1:-1,k,n;void 0===d&&(d="text");n="float"==d||"number"==d||"currency"==d||"numeric"==d?function(a){a=parseFloat((""+a).replace(j,""));return isNaN(a)?0:a}:"int"==d||"integer"==d?function(a){return a?parseFloat((""+a).replace(j,"")):0}:"date"==
d||"datetime"==d?function(a){return b.jgrid.parseDate(f,a).getTime()}:b.isFunction(d)?d:function(a){a||(a="");return b.trim((""+a).toUpperCase())};b.each(a,function(a,e){k=""!==c?b.jgrid.getAccessor(e,c):e;void 0===k&&(k="");k=n(k,e);h.push({vSort:k,index:a})});h.sort(function(a,b){a=a.vSort;b=b.vSort;return e._compare(a,b,m)});for(var d=0,p=a.length;d<p;)i=h[d].index,g.push(a[i]),d++;return g};this._getGroup=function(a,c,d,i,f){var g=[],h=null,j=null,k;b.each(e._getOrder(a,c,d,i,f),function(a,d){k=
b.jgrid.getAccessor(d,c);void 0===k&&(k="");e._equals(j,k)||(j=k,null!==h&&g.push(h),h=e._group(c,k));h.items.push(d)});null!==h&&g.push(h);return g};this.ignoreCase=function(){i=!1;return e};this.useCase=function(){i=!0;return e};this.trim=function(){d=!0;return e};this.noTrim=function(){d=!1;return e};this.execute=function(){var d=h,c=[];if(null===d)return e;b.each(a,function(){eval(d)&&c.push(this)});a=c;return e};this.data=function(){return a};this.select=function(d){e._performSort();if(!e._hasData())return[];
e.execute();if(b.isFunction(d)){var c=[];b.each(a,function(a,b){c.push(d(b))});return c}return a};this.hasMatch=function(){if(!e._hasData())return!1;e.execute();return 0<a.length};this.andNot=function(a,b,d){p=!p;return e.and(a,b,d)};this.orNot=function(a,b,d){p=!p;return e.or(a,b,d)};this.not=function(a,b,d){return e.andNot(a,b,d)};this.and=function(a,b,d){o=" && ";return void 0===a?e:e._repeatCommand(a,b,d)};this.or=function(a,b,d){o=" || ";return void 0===a?e:e._repeatCommand(a,b,d)};this.orBegin=
function(){n++;return e};this.orEnd=function(){null!==h&&(h+=")");return e};this.isNot=function(a){p=!p;return e.is(a)};this.is=function(a){e._append("this."+a);e._resetNegate();return e};this._compareValues=function(a,d,c,i,f){var g;g=w?"jQuery.jgrid.getAccessor(this,'"+d+"')":"this";void 0===c&&(c=null);var h=c,k=void 0===f.stype?"text":f.stype;if(null!==c)switch(k){case "int":case "integer":h=isNaN(Number(h))||""===h?"0":h;g="parseInt("+g+",10)";h="parseInt("+h+",10)";break;case "float":case "number":case "numeric":h=
(""+h).replace(j,"");h=isNaN(Number(h))||""===h?"0":h;g="parseFloat("+g+")";h="parseFloat("+h+")";break;case "date":case "datetime":h=""+b.jgrid.parseDate(f.newfmt||"Y-m-d",h).getTime();g='jQuery.jgrid.parseDate("'+f.srcfmt+'",'+g+").getTime()";break;default:g=e._getStr(g),h=e._getStr('"'+e._toStr(h)+'"')}e._append(g+" "+i+" "+h);e._setCommand(a,d);e._resetNegate();return e};this.equals=function(a,b,d){return e._compareValues(e.equals,a,b,"==",d)};this.notEquals=function(a,b,d){return e._compareValues(e.equals,
a,b,"!==",d)};this.isNull=function(a,b,d){return e._compareValues(e.equals,a,null,"===",d)};this.greater=function(a,b,d){return e._compareValues(e.greater,a,b,">",d)};this.less=function(a,b,d){return e._compareValues(e.less,a,b,"<",d)};this.greaterOrEquals=function(a,b,d){return e._compareValues(e.greaterOrEquals,a,b,">=",d)};this.lessOrEquals=function(a,b,d){return e._compareValues(e.lessOrEquals,a,b,"<=",d)};this.startsWith=function(a,c){var i=void 0===c||null===c?a:c,i=d?b.trim(i.toString()).length:
i.toString().length;w?e._append(e._getStr("jQuery.jgrid.getAccessor(this,'"+a+"')")+".substr(0,"+i+") == "+e._getStr('"'+e._toStr(c)+'"')):(i=d?b.trim(c.toString()).length:c.toString().length,e._append(e._getStr("this")+".substr(0,"+i+") == "+e._getStr('"'+e._toStr(a)+'"')));e._setCommand(e.startsWith,a);e._resetNegate();return e};this.endsWith=function(a,c){var i=void 0===c||null===c?a:c,i=d?b.trim(i.toString()).length:i.toString().length;w?e._append(e._getStr("jQuery.jgrid.getAccessor(this,'"+a+
"')")+".substr("+e._getStr("jQuery.jgrid.getAccessor(this,'"+a+"')")+".length-"+i+","+i+') == "'+e._toStr(c)+'"'):e._append(e._getStr("this")+".substr("+e._getStr("this")+'.length-"'+e._toStr(a)+'".length,"'+e._toStr(a)+'".length) == "'+e._toStr(a)+'"');e._setCommand(e.endsWith,a);e._resetNegate();return e};this.contains=function(a,b){w?e._append(e._getStr("jQuery.jgrid.getAccessor(this,'"+a+"')")+'.indexOf("'+e._toStr(b)+'",0) > -1'):e._append(e._getStr("this")+'.indexOf("'+e._toStr(a)+'",0) > -1');
e._setCommand(e.contains,a);e._resetNegate();return e};this.groupBy=function(b,d,c,i){return!e._hasData()?null:e._getGroup(a,b,d,c,i)};this.orderBy=function(a,d,c,i){d=void 0===d||null===d?"a":b.trim(d.toString().toLowerCase());if(null===c||void 0===c)c="text";if(null===i||void 0===i)i="Y-m-d";if("desc"==d||"descending"==d)d="d";if("asc"==d||"ascending"==d)d="a";u.push({by:a,dir:d,type:c,datefmt:i});return e};return e}(d,null)},extend:function(d){b.extend(b.fn.jqGrid,d);this.no_legacy_api||b.fn.extend(d)}});
b.fn.jqGrid=function(d){if("string"==typeof d){var f=b.jgrid.getAccessor(b.fn.jqGrid,d);if(!f)throw"jqGrid - No such method: "+d;var c=b.makeArray(arguments).slice(1);return f.apply(this,c)}return this.each(function(){if(!this.grid){var e=b.extend(!0,{url:"",height:150,page:1,rowNum:10,rowTotal:null,records:0,pager:"",pgbuttons:!0,pginput:!0,colModel:[],rowList:[10,20,30,40,50],colNames:[],sortorder:"asc",sortname:"",datatype:"json",mtype:"GET",altRows:!1,selarrrow:[],savedRow:[],shrinkToFit:!0,xmlReader:{},jsonReader:{},
subGrid:!1,subGridModel:[],reccount:0,lastpage:0,lastsort:0,selrow:null,beforeSelectRow:null,onSelectRow:null,onSortCol:null,ondblClickRow:null,onRightClickRow:null,onPaging:null,onSelectAll:null,loadComplete:null,gridComplete:null,loadError:null,loadBeforeSend:null,afterInsertRow:null,beforeRequest:null,beforeProcessing:null,onHeaderClick:null,viewrecords:1,loadonce:!1,multiselect:!1,multikey:!1,editurl:null,search:!1,caption:"",hidegrid:!0,hiddengrid:!1,postData:{},userData:{},treeGrid:!1,treeGridModel:"nested",
treeReader:{},treeANode:-1,ExpandColumn:null,tree_root_level:0,prmNames:{page:"page",rows:"rows",sort:"sidx",order:"sord",search:"_search",nd:"nd",id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del",subgridid:"id",npage:null,totalrows:"totalrows"},forceFit:!1,gridstate:"visible",cellEdit:!1,cellsubmit:"remote",nv:0,loadui:"enable",toolbar:[!1,""],scroll:!1,multiboxonly:!1,deselectAfterSort:!0,scrollrows:!1,autowidth:!1,scrollOffset:18,cellLayout:5,subGridWidth:20,multiselectWidth:20,gridview:!1,
rownumWidth:25,rownumbers:!1,pagerpos:"center",recordpos:"right",footerrow:!1,userDataOnFooter:!1,hoverrows:!0,altclass:"ui-priority-secondary",viewsortcols:[!1,"vertical",!0],resizeclass:"",autoencode:!1,remapColumns:[],ajaxGridOptions:{},direction:"ltr",toppager:!1,headertitles:!1,scrollTimeout:40,data:[],_index:{},grouping:!1,groupingView:{groupField:[],groupOrder:[],groupText:[],groupColumnShow:[],groupSummary:[],showSummaryOnHide:!1,sortitems:[],sortnames:[],groupDataSorted:!1,summary:[],summaryval:[],
plusicon:"ui-icon-circlesmall-plus",minusicon:"ui-icon-circlesmall-minus"},ignoreCase:!1,cmTemplate:{},idPrefix:""},b.jgrid.defaults,d||{}),a=this,c={headers:[],cols:[],footers:[],dragStart:function(c,d,f){this.resizing={idx:c,startX:d.clientX,sOL:f[0]};this.hDiv.style.cursor="col-resize";this.curGbox=b("#rs_m"+b.jgrid.jqID(e.id),"#gbox_"+b.jgrid.jqID(e.id));this.curGbox.css({display:"block",left:f[0],top:f[1],height:f[2]});b(a).triggerHandler("jqGridResizeStart",[d,c]);b.isFunction(e.resizeStart)&&
e.resizeStart.call(this,d,c);document.onselectstart=function(){return!1}},dragMove:function(a){if(this.resizing){var b=a.clientX-this.resizing.startX,a=this.headers[this.resizing.idx],c="ltr"===e.direction?a.width+b:a.width-b,d;33<c&&(this.curGbox.css({left:this.resizing.sOL+b}),!0===e.forceFit?(d=this.headers[this.resizing.idx+e.nv],b="ltr"===e.direction?d.width-b:d.width+b,33<b&&(a.newWidth=c,d.newWidth=b)):(this.newWidth="ltr"===e.direction?e.tblwidth+b:e.tblwidth-b,a.newWidth=c))}},dragEnd:function(){this.hDiv.style.cursor=
"default";if(this.resizing){var c=this.resizing.idx,d=this.headers[c].newWidth||this.headers[c].width,d=parseInt(d,10);this.resizing=!1;b("#rs_m"+b.jgrid.jqID(e.id)).css("display","none");e.colModel[c].width=d;this.headers[c].width=d;this.headers[c].el.style.width=d+"px";this.cols[c].style.width=d+"px";0<this.footers.length&&(this.footers[c].style.width=d+"px");!0===e.forceFit?(d=this.headers[c+e.nv].newWidth||this.headers[c+e.nv].width,this.headers[c+e.nv].width=d,this.headers[c+e.nv].el.style.width=
d+"px",this.cols[c+e.nv].style.width=d+"px",0<this.footers.length&&(this.footers[c+e.nv].style.width=d+"px"),e.colModel[c+e.nv].width=d):(e.tblwidth=this.newWidth||e.tblwidth,b("table:first",this.bDiv).css("width",e.tblwidth+"px"),b("table:first",this.hDiv).css("width",e.tblwidth+"px"),this.hDiv.scrollLeft=this.bDiv.scrollLeft,e.footerrow&&(b("table:first",this.sDiv).css("width",e.tblwidth+"px"),this.sDiv.scrollLeft=this.bDiv.scrollLeft));b(a).triggerHandler("jqGridResizeStop",[d,c]);b.isFunction(e.resizeStop)&&
e.resizeStop.call(this,d,c)}this.curGbox=null;document.onselectstart=function(){return!0}},populateVisible:function(){c.timer&&clearTimeout(c.timer);c.timer=null;var a=b(c.bDiv).height();if(a){var d=b("table:first",c.bDiv),f,F;if(d[0].rows.length)try{F=(f=d[0].rows[1])?b(f).outerHeight()||c.prevRowHeight:c.prevRowHeight}catch(g){F=c.prevRowHeight}if(F){c.prevRowHeight=F;var h=e.rowNum;f=c.scrollTop=c.bDiv.scrollTop;var j=Math.round(d.position().top)-f,k=j+d.height();F*=h;var y,t,K;if(k<a&&0>=j&&(void 0===
e.lastpage||parseInt((k+f+F-1)/F,10)<=e.lastpage))t=parseInt((a-k+F-1)/F,10),0<=k||2>t||!0===e.scroll?(y=Math.round((k+f)/F)+1,j=-1):j=1;0<j&&(y=parseInt(f/F,10)+1,t=parseInt((f+a)/F,10)+2-y,K=!0);if(t&&!(e.lastpage&&y>e.lastpage||1==e.lastpage||y===e.page&&y===e.lastpage))c.hDiv.loading?c.timer=setTimeout(c.populateVisible,e.scrollTimeout):(e.page=y,K&&(c.selectionPreserver(d[0]),c.emptyRows(c.bDiv,!1,!1)),c.populate(t))}}},scrollGrid:function(a){if(e.scroll){var b=c.bDiv.scrollTop;void 0===c.scrollTop&&
(c.scrollTop=0);b!=c.scrollTop&&(c.scrollTop=b,c.timer&&clearTimeout(c.timer),c.timer=setTimeout(c.populateVisible,e.scrollTimeout))}c.hDiv.scrollLeft=c.bDiv.scrollLeft;e.footerrow&&(c.sDiv.scrollLeft=c.bDiv.scrollLeft);a&&a.stopPropagation()},selectionPreserver:function(a){var c=a.p,d=c.selrow,e=c.selarrrow?b.makeArray(c.selarrrow):null,f=a.grid.bDiv.scrollLeft,g=function(){var i;c.selrow=null;c.selarrrow=[];if(c.multiselect&&e&&0<e.length)for(i=0;i<e.length;i++)e[i]!=d&&b(a).jqGrid("setSelection",
e[i],!1,null);d&&b(a).jqGrid("setSelection",d,!1,null);a.grid.bDiv.scrollLeft=f;b(a).unbind(".selectionPreserver",g)};b(a).bind("jqGridGridComplete.selectionPreserver",g)}};if("TABLE"!=this.tagName.toUpperCase())alert("Element is not a table");else{b(this).empty().attr("tabindex","1");this.p=e;this.p.useProp=!!b.fn.prop;var f,h;if(0===this.p.colNames.length)for(f=0;f<this.p.colModel.length;f++)this.p.colNames[f]=this.p.colModel[f].label||this.p.colModel[f].name;if(this.p.colNames.length!==this.p.colModel.length)alert(b.jgrid.errors.model);
else{var j=b("<div class='ui-jqgrid-view'></div>"),m,k=b.browser.msie?!0:!1;a.p.direction=b.trim(a.p.direction.toLowerCase());-1==b.inArray(a.p.direction,["ltr","rtl"])&&(a.p.direction="ltr");h=a.p.direction;b(j).insertBefore(this);b(this).appendTo(j).removeClass("scroll");var n=b("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");b(n).insertBefore(j).attr({id:"gbox_"+this.id,dir:h});b(j).appendTo(n).attr("id","gview_"+this.id);m=k&&6>=b.browser.version?'<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>':
"";b("<div class='ui-widget-overlay jqgrid-overlay' id='lui_"+this.id+"'></div>").append(m).insertBefore(j);b("<div class='loading ui-state-default ui-state-active' id='load_"+this.id+"'>"+this.p.loadtext+"</div>").insertBefore(j);b(this).attr({cellspacing:"0",cellpadding:"0",border:"0",role:"grid","aria-multiselectable":!!this.p.multiselect,"aria-labelledby":"gbox_"+this.id});var p=function(a,b){a=parseInt(a,10);return isNaN(a)?b?b:0:a},o=function(d,e,f,g,h,j){var J=a.p.colModel[d],k=J.align,y='style="',
t=J.classes,K=J.name,r=[];k&&(y=y+("text-align:"+k+";"));J.hidden===true&&(y=y+"display:none;");if(e===0)y=y+("width: "+c.headers[d].width+"px;");else if(J.cellattr&&b.isFunction(J.cellattr))if((d=J.cellattr.call(a,h,f,g,J,j))&&typeof d==="string"){d=d.replace(/style/i,"style").replace(/title/i,"title");if(d.indexOf("title")>-1)J.title=false;d.indexOf("class")>-1&&(t=void 0);r=d.split("style");if(r.length===2){r[1]=b.trim(r[1].replace("=",""));if(r[1].indexOf("'")===0||r[1].indexOf('"')===0)r[1]=
r[1].substring(1);y=y+r[1].replace(/'/gi,'"')}else y=y+'"'}if(!r.length){r[0]="";y=y+'"'}y=y+((t!==void 0?' class="'+t+'"':"")+(J.title&&f?' title="'+b.jgrid.stripHtml(f)+'"':""));y=y+(' aria-describedby="'+a.p.id+"_"+K+'"');return y+r[0]},u=function(c){return c===void 0||c===null||c===""?"&#160;":a.p.autoencode?b.jgrid.htmlEncode(c):c+""},w=function(c,d,e,f,g){var i=a.p.colModel[e];if(typeof i.formatter!=="undefined"){c={rowId:c,colModel:i,gid:a.p.id,pos:e};d=b.isFunction(i.formatter)?i.formatter.call(a,
d,c,f,g):b.fmatter?b.fn.fmatter.call(a,i.formatter,d,c,f,g):u(d)}else d=u(d);return d},N=function(a,b,c,d,e){b=w(a,b,c,e,"add");return'<td role="gridcell" '+o(c,d,b,e,a,true)+">"+b+"</td>"},D=function(b,c,d){var e='<input role="checkbox" type="checkbox" id="jqg_'+a.p.id+"_"+b+'" class="cbox" name="jqg_'+a.p.id+"_"+b+'"/>';return'<td role="gridcell" '+o(c,d,"",null,b,true)+">"+e+"</td>"},O=function(a,b,c,d){c=(parseInt(c,10)-1)*parseInt(d,10)+1+b;return'<td role="gridcell" class="ui-state-default jqgrid-rownum" '+
o(a,b,c,null,b,true)+">"+c+"</td>"},V=function(b){var c,d=[],e=0,f;for(f=0;f<a.p.colModel.length;f++){c=a.p.colModel[f];if(c.name!=="cb"&&c.name!=="subgrid"&&c.name!=="rn"){d[e]=b=="local"?c.name:b=="xml"||b==="xmlstring"?c.xmlmap||c.name:c.jsonmap||c.name;e++}}return d},W=function(c){var d=a.p.remapColumns;if(!d||!d.length)d=b.map(a.p.colModel,function(a,b){return b});c&&(d=b.map(d,function(a){return a<c?null:a-c}));return d},P=function(c,d,e){if(a.p.deepempty)b("#"+b.jgrid.jqID(a.p.id)+" tbody:first tr:gt(0)").remove();
else{var f=b("#"+b.jgrid.jqID(a.p.id)+" tbody:first tr:first")[0];b("#"+b.jgrid.jqID(a.p.id)+" tbody:first").empty().append(f)}if(d&&a.p.scroll){b(">div:first",c).css({height:"auto"}).children("div:first").css({height:0,display:"none"});c.scrollTop=0}if(e===true&&a.p.treeGrid===true){a.p.data=[];a.p._index={}}},T=function(){var c=a.p.data.length,d,e,f;d=a.p.rownumbers===true?1:0;e=a.p.multiselect===true?1:0;f=a.p.subGrid===true?1:0;d=a.p.keyIndex===false||a.p.loadonce===true?a.p.localReader.id:a.p.colModel[a.p.keyIndex+
e+f+d].name;for(e=0;e<c;e++){f=b.jgrid.getAccessor(a.p.data[e],d);a.p._index[f]=e}},I=function(c,d,e,f,g){var i="-1",h="",j,d=d?"display:none;":"",e="ui-widget-content jqgrow ui-row-"+a.p.direction+e,f=b.isFunction(a.p.rowattr)?a.p.rowattr.call(a,f,g):{};if(!b.isEmptyObject(f)){if(f.hasOwnProperty("id")){c=f.id;delete f.id}if(f.hasOwnProperty("tabindex")){i=f.tabindex;delete f.tabindex}if(f.hasOwnProperty("style")){d=d+f.style;delete f.style}if(f.hasOwnProperty("class")){e=e+(" "+f["class"]);delete f["class"]}try{delete f.role}catch(k){}for(j in f)f.hasOwnProperty(j)&&
(h=h+(" "+j+"="+f[j]))}return'<tr role="row" id="'+c+'" tabindex="'+i+'" class="'+e+'"'+(d===""?"":' style="'+d+'"')+h+">"},$=function(c,d,e,f,g){var i=new Date,h=a.p.datatype!="local"&&a.p.loadonce||a.p.datatype=="xmlstring",j=a.p.xmlReader,k=a.p.datatype=="local"?"local":"xml";if(h){a.p.data=[];a.p._index={};a.p.localReader.id="_id_"}a.p.reccount=0;if(b.isXMLDoc(c)){if(a.p.treeANode===-1&&!a.p.scroll){P(d,false,true);e=1}else e=e>1?e:1;var t,K,r=0,m,A=a.p.multiselect===true?1:0,n=a.p.subGrid===
true?1:0,p=a.p.rownumbers===true?1:0,L,o=[],w,l={},q,v,B=[],u=a.p.altRows===true?" "+a.p.altclass:"",z;j.repeatitems||(o=V(k));L=a.p.keyIndex===false?b.isFunction(j.id)?j.id.call(a,c):j.id:a.p.keyIndex;if(o.length>0&&!isNaN(L)){a.p.remapColumns&&a.p.remapColumns.length&&(L=b.inArray(L,a.p.remapColumns));L=o[L]}k=(L+"").indexOf("[")===-1?o.length?function(a,c){return b(L,a).text()||c}:function(a,c){return b(j.cell,a).eq(L).text()||c}:function(a,b){return a.getAttribute(L.replace(/[\[\]]/g,""))||b};
a.p.userData={};a.p.page=b.jgrid.getXmlData(c,j.page)||0;a.p.lastpage=b.jgrid.getXmlData(c,j.total);if(a.p.lastpage===void 0)a.p.lastpage=1;a.p.records=b.jgrid.getXmlData(c,j.records)||0;b.isFunction(j.userdata)?a.p.userData=j.userdata.call(a,c)||{}:b.jgrid.getXmlData(c,j.userdata,true).each(function(){a.p.userData[this.getAttribute("name")]=b(this).text()});c=b.jgrid.getXmlData(c,j.root,true);(c=b.jgrid.getXmlData(c,j.row,true))||(c=[]);var s=c.length,G=0,R={},x=parseInt(a.p.rowNum,10);if(s>0&&a.p.page<=
0)a.p.page=1;if(c&&s){var C=a.p.scroll?b.jgrid.randId():1;g&&(x=x*(g+1));for(var g=b.isFunction(a.p.afterInsertRow),E=a.p.grouping&&a.p.groupingView.groupCollapse===true;G<s;){q=c[G];v=k(q,C+G);v=a.p.idPrefix+v;t=e===0?0:e+1;z=(t+G)%2==1?u:"";var H=B.length;B.push("");p&&B.push(O(0,G,a.p.page,a.p.rowNum));A&&B.push(D(v,p,G));n&&B.push(b(a).jqGrid("addSubGridCell",A+p,G+e));if(j.repeatitems){w||(w=W(A+n+p));var M=b.jgrid.getXmlData(q,j.cell,true);b.each(w,function(b){var c=M[this];if(!c)return false;
m=c.textContent||c.text;l[a.p.colModel[b+A+n+p].name]=m;B.push(N(v,m,b+A+n+p,G+e,q))})}else for(t=0;t<o.length;t++){m=b.jgrid.getXmlData(q,o[t]);l[a.p.colModel[t+A+n+p].name]=m;B.push(N(v,m,t+A+n+p,G+e,q))}B[H]=I(v,E,z,l,q);B.push("</tr>");if(a.p.grouping){R=b(a).jqGrid("groupingPrepare",B,R,l,G);B=[]}if(h||a.p.treeGrid===true){l._id_=v;a.p.data.push(l);a.p._index[v]=a.p.data.length-1}if(a.p.gridview===false){b("tbody:first",d).append(B.join(""));b(a).triggerHandler("jqGridAfterInsertRow",[v,l,q]);
g&&a.p.afterInsertRow.call(a,v,l,q);B=[]}l={};r++;G++;if(r==x)break}}if(a.p.gridview===true){K=a.p.treeANode>-1?a.p.treeANode:0;if(a.p.grouping){b(a).jqGrid("groupingRender",R,a.p.colModel.length);R=null}else a.p.treeGrid===true&&K>0?b(a.rows[K]).after(B.join("")):b("tbody:first",d).append(B.join(""))}if(a.p.subGrid===true)try{b(a).jqGrid("addSubGrid",A+p)}catch(S){}a.p.totaltime=new Date-i;if(r>0&&a.p.records===0)a.p.records=s;B=null;if(a.p.treeGrid===true)try{b(a).jqGrid("setTreeNode",K+1,r+K+1)}catch(T){}if(!a.p.treeGrid&&
!a.p.scroll)a.grid.bDiv.scrollTop=0;a.p.reccount=r;a.p.treeANode=-1;a.p.userDataOnFooter&&b(a).jqGrid("footerData","set",a.p.userData,true);if(h){a.p.records=s;a.p.lastpage=Math.ceil(s/x)}f||a.updatepager(false,true);if(h)for(;r<s;){q=c[r];v=k(q,r+C);v=a.p.idPrefix+v;if(j.repeatitems){w||(w=W(A+n+p));var Q=b.jgrid.getXmlData(q,j.cell,true);b.each(w,function(b){var c=Q[this];if(!c)return false;m=c.textContent||c.text;l[a.p.colModel[b+A+n+p].name]=m})}else for(t=0;t<o.length;t++){m=b.jgrid.getXmlData(q,
o[t]);l[a.p.colModel[t+A+n+p].name]=m}l._id_=v;a.p.data.push(l);a.p._index[v]=a.p.data.length-1;l={};r++}}},aa=function(c,d,e,f,g){var i=new Date;if(c){if(a.p.treeANode===-1&&!a.p.scroll){P(d,false,true);e=1}else e=e>1?e:1;var h,j=a.p.datatype!="local"&&a.p.loadonce||a.p.datatype=="jsonstring";if(j){a.p.data=[];a.p._index={};a.p.localReader.id="_id_"}a.p.reccount=0;if(a.p.datatype=="local"){d=a.p.localReader;h="local"}else{d=a.p.jsonReader;h="json"}var k=0,t,m,r=[],n,A=a.p.multiselect?1:0,p=a.p.subGrid?
1:0,o=a.p.rownumbers===true?1:0,l,w,s={},u,q,v=[],B=a.p.altRows===true?" "+a.p.altclass:"",z;a.p.page=b.jgrid.getAccessor(c,d.page)||0;l=b.jgrid.getAccessor(c,d.total);a.p.lastpage=l===void 0?1:l;a.p.records=b.jgrid.getAccessor(c,d.records)||0;a.p.userData=b.jgrid.getAccessor(c,d.userdata)||{};d.repeatitems||(n=r=V(h));h=a.p.keyIndex===false?b.isFunction(d.id)?d.id.call(a,c):d.id:a.p.keyIndex;if(r.length>0&&!isNaN(h)){a.p.remapColumns&&a.p.remapColumns.length&&(h=b.inArray(h,a.p.remapColumns));h=
r[h]}(w=b.jgrid.getAccessor(c,d.root))||(w=[]);l=w.length;c=0;if(l>0&&a.p.page<=0)a.p.page=1;var x=parseInt(a.p.rowNum,10),C=a.p.scroll?b.jgrid.randId():1;g&&(x=x*(g+1));for(var G=b.isFunction(a.p.afterInsertRow),R={},E=a.p.grouping&&a.p.groupingView.groupCollapse===true;c<l;){g=w[c];q=b.jgrid.getAccessor(g,h);if(q===void 0){q=C+c;if(r.length===0&&d.cell){t=b.jgrid.getAccessor(g,d.cell);q=t!==void 0?t[h]||q:q}}q=a.p.idPrefix+q;t=e===1?0:e;z=(t+c)%2==1?B:"";var H=v.length;v.push("");o&&v.push(O(0,
c,a.p.page,a.p.rowNum));A&&v.push(D(q,o,c));p&&v.push(b(a).jqGrid("addSubGridCell",A+o,c+e));if(d.repeatitems){d.cell&&(g=b.jgrid.getAccessor(g,d.cell));n||(n=W(A+p+o))}for(m=0;m<n.length;m++){t=b.jgrid.getAccessor(g,n[m]);v.push(N(q,t,m+A+p+o,c+e,g));s[a.p.colModel[m+A+p+o].name]=t}v[H]=I(q,E,z,s,g);v.push("</tr>");if(a.p.grouping){R=b(a).jqGrid("groupingPrepare",v,R,s,c);v=[]}if(j||a.p.treeGrid===true){s._id_=q;a.p.data.push(s);a.p._index[q]=a.p.data.length-1}if(a.p.gridview===false){b("#"+b.jgrid.jqID(a.p.id)+
" tbody:first").append(v.join(""));b(a).triggerHandler("jqGridAfterInsertRow",[q,s,g]);G&&a.p.afterInsertRow.call(a,q,s,g);v=[]}s={};k++;c++;if(k==x)break}if(a.p.gridview===true){u=a.p.treeANode>-1?a.p.treeANode:0;a.p.grouping?b(a).jqGrid("groupingRender",R,a.p.colModel.length):a.p.treeGrid===true&&u>0?b(a.rows[u]).after(v.join("")):b("#"+b.jgrid.jqID(a.p.id)+" tbody:first").append(v.join(""))}if(a.p.subGrid===true)try{b(a).jqGrid("addSubGrid",A+o)}catch(M){}a.p.totaltime=new Date-i;if(k>0&&a.p.records===
0)a.p.records=l;if(a.p.treeGrid===true)try{b(a).jqGrid("setTreeNode",u+1,k+u+1)}catch(Q){}if(!a.p.treeGrid&&!a.p.scroll)a.grid.bDiv.scrollTop=0;a.p.reccount=k;a.p.treeANode=-1;a.p.userDataOnFooter&&b(a).jqGrid("footerData","set",a.p.userData,true);if(j){a.p.records=l;a.p.lastpage=Math.ceil(l/x)}f||a.updatepager(false,true);if(j)for(;k<l&&w[k];){g=w[k];q=b.jgrid.getAccessor(g,h);if(q===void 0){q=C+k;r.length===0&&d.cell&&(q=b.jgrid.getAccessor(g,d.cell)[h]||q)}if(g){q=a.p.idPrefix+q;if(d.repeatitems){d.cell&&
(g=b.jgrid.getAccessor(g,d.cell));n||(n=W(A+p+o))}for(m=0;m<n.length;m++){t=b.jgrid.getAccessor(g,n[m]);s[a.p.colModel[m+A+p+o].name]=t}s._id_=q;a.p.data.push(s);a.p._index[q]=a.p.data.length-1;s={}}k++}}},ma=function(){function c(d){var e=0,g,h,i,j,U;if(d.groups!==void 0){(h=d.groups.length&&d.groupOp.toString().toUpperCase()==="OR")&&l.orBegin();for(g=0;g<d.groups.length;g++){e>0&&h&&l.or();try{c(d.groups[g])}catch(k){alert(k)}e++}h&&l.orEnd()}if(d.rules!==void 0){if(e>0){h=l.select();l=b.jgrid.from(h);
a.p.ignoreCase&&(l=l.ignoreCase())}try{(i=d.rules.length&&d.groupOp.toString().toUpperCase()==="OR")&&l.orBegin();for(g=0;g<d.rules.length;g++){U=d.rules[g];j=d.groupOp.toString().toUpperCase();if(p[U.op]&&U.field){e>0&&j&&j==="OR"&&(l=l.or());l=p[U.op](l,j)(U.field,U.data,f[U.field])}e++}i&&l.orEnd()}catch(na){alert(na)}}}var d,e=false,f={},g=[],h=[],i,j,k;if(b.isArray(a.p.data)){var m=a.p.grouping?a.p.groupingView:false,n,r;b.each(a.p.colModel,function(){j=this.sorttype||"text";if(j=="date"||j==
"datetime"){if(this.formatter&&typeof this.formatter==="string"&&this.formatter=="date"){i=this.formatoptions&&this.formatoptions.srcformat?this.formatoptions.srcformat:b.jgrid.formatter.date.srcformat;k=this.formatoptions&&this.formatoptions.newformat?this.formatoptions.newformat:b.jgrid.formatter.date.newformat}else i=k=this.datefmt||"Y-m-d";f[this.name]={stype:j,srcfmt:i,newfmt:k}}else f[this.name]={stype:j,srcfmt:"",newfmt:""};if(a.p.grouping){r=0;for(n=m.groupField.length;r<n;r++)if(this.name==
m.groupField[r]){var c=this.name;if(typeof this.index!="undefined")c=this.index;g[r]=f[this.name];h[r]=c}}if(!e&&(this.index==a.p.sortname||this.name==a.p.sortname)){d=this.name;e=true}});if(a.p.treeGrid)b(a).jqGrid("SortTree",d,a.p.sortorder,f[d].stype,f[d].srcfmt);else{var p={eq:function(a){return a.equals},ne:function(a){return a.notEquals},lt:function(a){return a.less},le:function(a){return a.lessOrEquals},gt:function(a){return a.greater},ge:function(a){return a.greaterOrEquals},cn:function(a){return a.contains},
nc:function(a,b){return b==="OR"?a.orNot().contains:a.andNot().contains},bw:function(a){return a.startsWith},bn:function(a,b){return b==="OR"?a.orNot().startsWith:a.andNot().startsWith},en:function(a,b){return b==="OR"?a.orNot().endsWith:a.andNot().endsWith},ew:function(a){return a.endsWith},ni:function(a,b){return b==="OR"?a.orNot().equals:a.andNot().equals},"in":function(a){return a.equals},nu:function(a){return a.isNull},nn:function(a,b){return b==="OR"?a.orNot().isNull:a.andNot().isNull}},l=b.jgrid.from(a.p.data);
a.p.ignoreCase&&(l=l.ignoreCase());if(a.p.search===true){var o=a.p.postData.filters;if(o){typeof o=="string"&&(o=b.jgrid.parse(o));c(o)}else try{l=p[a.p.postData.searchOper](l)(a.p.postData.searchField,a.p.postData.searchString,f[a.p.postData.searchField])}catch(w){}}if(a.p.grouping){for(r=0;r<n;r++)l.orderBy(h[r],m.groupOrder[r],g[r].stype,g[r].srcfmt);m.groupDataSorted=true}d&&a.p.sortorder&&e&&(a.p.sortorder.toUpperCase()=="DESC"?l.orderBy(a.p.sortname,"d",f[d].stype,f[d].srcfmt):l.orderBy(a.p.sortname,
"a",f[d].stype,f[d].srcfmt));var o=l.select(),s=parseInt(a.p.rowNum,10),u=o.length,x=parseInt(a.p.page,10),z=Math.ceil(u/s),q={},o=o.slice((x-1)*s,x*s),f=l=null;q[a.p.localReader.total]=z;q[a.p.localReader.page]=x;q[a.p.localReader.records]=u;q[a.p.localReader.root]=o;q[a.p.localReader.userdata]=a.p.userData;o=null;return q}}},ca=function(){a.grid.hDiv.loading=true;if(!a.p.hiddengrid)switch(a.p.loadui){case "enable":b("#load_"+b.jgrid.jqID(a.p.id)).show();break;case "block":b("#lui_"+b.jgrid.jqID(a.p.id)).show();
b("#load_"+b.jgrid.jqID(a.p.id)).show()}},Q=function(){a.grid.hDiv.loading=false;switch(a.p.loadui){case "enable":b("#load_"+b.jgrid.jqID(a.p.id)).hide();break;case "block":b("#lui_"+b.jgrid.jqID(a.p.id)).hide();b("#load_"+b.jgrid.jqID(a.p.id)).hide()}},H=function(c){if(!a.grid.hDiv.loading){var d=a.p.scroll&&c===false,e={},f,g=a.p.prmNames;if(a.p.page<=0)a.p.page=1;if(g.search!==null)e[g.search]=a.p.search;g.nd!==null&&(e[g.nd]=(new Date).getTime());if(g.rows!==null)e[g.rows]=a.p.rowNum;if(g.page!==
null)e[g.page]=a.p.page;if(g.sort!==null)e[g.sort]=a.p.sortname;if(g.order!==null)e[g.order]=a.p.sortorder;if(a.p.rowTotal!==null&&g.totalrows!==null)e[g.totalrows]=a.p.rowTotal;var h=b.isFunction(a.p.loadComplete),i=h?a.p.loadComplete:null,j=0,c=c||1;if(c>1)if(g.npage!==null){e[g.npage]=c;j=c-1;c=1}else i=function(b){a.p.page++;a.grid.hDiv.loading=false;h&&a.p.loadComplete.call(a,b);H(c-1)};else g.npage!==null&&delete a.p.postData[g.npage];if(a.p.grouping){b(a).jqGrid("groupingSetup");a.p.groupingView.groupDataSorted===
true&&(e[g.sort]=a.p.groupingView.groupField[0]+" "+a.p.groupingView.groupOrder[0]+", "+e[g.sort])}b.extend(a.p.postData,e);var k=!a.p.scroll?1:a.rows.length-1,e=b(a).triggerHandler("jqGridBeforeRequest");if(!(e===false||e==="stop"))if(b.isFunction(a.p.datatype))a.p.datatype.call(a,a.p.postData,"load_"+a.p.id);else{if(b.isFunction(a.p.beforeRequest)){e=a.p.beforeRequest.call(a);e===void 0&&(e=true);if(e===false)return}f=a.p.datatype.toLowerCase();switch(f){case "json":case "jsonp":case "xml":case "script":b.ajax(b.extend({url:a.p.url,
type:a.p.mtype,dataType:f,data:b.isFunction(a.p.serializeGridData)?a.p.serializeGridData.call(a,a.p.postData):a.p.postData,success:function(e,g,h){if(b.isFunction(a.p.beforeProcessing)&&a.p.beforeProcessing.call(a,e,g,h)===false)Q();else{f==="xml"?$(e,a.grid.bDiv,k,c>1,j):aa(e,a.grid.bDiv,k,c>1,j);b(a).triggerHandler("jqGridLoadComplete",[e]);i&&i.call(a,e);b(a).triggerHandler("jqGridAfterLoadComplete",[e]);d&&a.grid.populateVisible();if(a.p.loadonce||a.p.treeGrid)a.p.datatype="local";c===1&&Q()}},
error:function(d,e,f){b.isFunction(a.p.loadError)&&a.p.loadError.call(a,d,e,f);c===1&&Q()},beforeSend:function(c,d){var e=true;b.isFunction(a.p.loadBeforeSend)&&(e=a.p.loadBeforeSend.call(a,c,d));e===void 0&&(e=true);if(e===false)return false;ca()}},b.jgrid.ajaxOptions,a.p.ajaxGridOptions));break;case "xmlstring":ca();e=b.jgrid.stringToDoc(a.p.datastr);$(e,a.grid.bDiv);b(a).triggerHandler("jqGridLoadComplete",[e]);h&&a.p.loadComplete.call(a,e);b(a).triggerHandler("jqGridAfterLoadComplete",[e]);a.p.datatype=
"local";a.p.datastr=null;Q();break;case "jsonstring":ca();e=typeof a.p.datastr=="string"?b.jgrid.parse(a.p.datastr):a.p.datastr;aa(e,a.grid.bDiv);b(a).triggerHandler("jqGridLoadComplete",[e]);h&&a.p.loadComplete.call(a,e);b(a).triggerHandler("jqGridAfterLoadComplete",[e]);a.p.datatype="local";a.p.datastr=null;Q();break;case "local":case "clientside":ca();a.p.datatype="local";e=ma();aa(e,a.grid.bDiv,k,c>1,j);b(a).triggerHandler("jqGridLoadComplete",[e]);i&&i.call(a,e);b(a).triggerHandler("jqGridAfterLoadComplete",
[e]);d&&a.grid.populateVisible();Q()}}}},da=function(c){b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv)[a.p.useProp?"prop":"attr"]("checked",c);if(a.p.frozenColumns&&a.p.id+"_frozen")b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.fhDiv)[a.p.useProp?"prop":"attr"]("checked",c)};m=function(c,e){var d="",f="<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>",g="",i,j,k,m,l=function(c){var e;b.isFunction(a.p.onPaging)&&(e=a.p.onPaging.call(a,c));a.p.selrow=null;
if(a.p.multiselect){a.p.selarrrow=[];da(false)}a.p.savedRow=[];return e=="stop"?false:true},c=c.substr(1),e=e+("_"+c);i="pg_"+c;j=c+"_left";k=c+"_center";m=c+"_right";b("#"+b.jgrid.jqID(c)).append("<div id='"+i+"' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;height:100%;' role='row'><tbody><tr><td id='"+j+"' align='left'></td><td id='"+k+"' align='center' style='white-space:pre;'></td><td id='"+m+"' align='right'></td></tr></tbody></table></div>").attr("dir",
"ltr");if(a.p.rowList.length>0){g="<td dir='"+h+"'>";g=g+"<select class='ui-pg-selbox' role='listbox'>";for(j=0;j<a.p.rowList.length;j++)g=g+('<option role="option" value="'+a.p.rowList[j]+'"'+(a.p.rowNum==a.p.rowList[j]?' selected="selected"':"")+">"+a.p.rowList[j]+"</option>");g=g+"</select></td>"}h=="rtl"&&(f=f+g);a.p.pginput===true&&(d="<td dir='"+h+"'>"+b.jgrid.format(a.p.pgtext||"","<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>","<span id='sp_1_"+b.jgrid.jqID(c)+
"'></span>")+"</td>");if(a.p.pgbuttons===true){j=["first"+e,"prev"+e,"next"+e,"last"+e];h=="rtl"&&j.reverse();f=f+("<td id='"+j[0]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>");f=f+("<td id='"+j[1]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>");f=f+(d!==""?"<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>"+d+"<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>":
"")+("<td id='"+j[2]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>");f=f+("<td id='"+j[3]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>")}else d!==""&&(f=f+d);h=="ltr"&&(f=f+g);f=f+"</tr></tbody></table>";a.p.viewrecords===true&&b("td#"+c+"_"+a.p.recordpos,"#"+i).append("<div dir='"+h+"' style='text-align:"+a.p.recordpos+"' class='ui-paging-info'></div>");b("td#"+c+"_"+a.p.pagerpos,"#"+i).append(f);g=b(".ui-jqgrid").css("font-size")||
"11px";b(document.body).append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+g+";visibility:hidden;' ></div>");f=b(f).clone().appendTo("#testpg").width();b("#testpg").remove();if(f>0){d!==""&&(f=f+50);b("td#"+c+"_"+a.p.pagerpos,"#"+i).width(f)}a.p._nvtd=[];a.p._nvtd[0]=f?Math.floor((a.p.width-f)/2):Math.floor(a.p.width/3);a.p._nvtd[1]=0;f=null;b(".ui-pg-selbox","#"+i).bind("change",function(){a.p.page=Math.round(a.p.rowNum*(a.p.page-1)/this.value-0.5)+1;a.p.rowNum=
this.value;a.p.pager&&b(".ui-pg-selbox",a.p.pager).val(this.value);a.p.toppager&&b(".ui-pg-selbox",a.p.toppager).val(this.value);if(!l("records"))return false;H();return false});if(a.p.pgbuttons===true){b(".ui-pg-button","#"+i).hover(function(){if(b(this).hasClass("ui-state-disabled"))this.style.cursor="default";else{b(this).addClass("ui-state-hover");this.style.cursor="pointer"}},function(){if(!b(this).hasClass("ui-state-disabled")){b(this).removeClass("ui-state-hover");this.style.cursor="default"}});
b("#first"+b.jgrid.jqID(e)+", #prev"+b.jgrid.jqID(e)+", #next"+b.jgrid.jqID(e)+", #last"+b.jgrid.jqID(e)).click(function(){var b=p(a.p.page,1),c=p(a.p.lastpage,1),d=false,f=true,g=true,i=true,h=true;if(c===0||c===1)h=i=g=f=false;else if(c>1&&b>=1)if(b===1)g=f=false;else{if(b===c)h=i=false}else if(c>1&&b===0){h=i=false;b=c-1}if(this.id==="first"+e&&f){a.p.page=1;d=true}if(this.id==="prev"+e&&g){a.p.page=b-1;d=true}if(this.id==="next"+e&&i){a.p.page=b+1;d=true}if(this.id==="last"+e&&h){a.p.page=c;d=
true}if(d){if(!l(this.id))return false;H()}return false})}a.p.pginput===true&&b("input.ui-pg-input","#"+i).keypress(function(c){if((c.charCode?c.charCode:c.keyCode?c.keyCode:0)==13){a.p.page=b(this).val()>0?b(this).val():a.p.page;if(!l("user"))return false;H();return false}return this})};var ja=function(c,e,d,f){if(a.p.colModel[e].sortable&&!(a.p.savedRow.length>0)){if(!d){if(a.p.lastsort==e)if(a.p.sortorder=="asc")a.p.sortorder="desc";else{if(a.p.sortorder=="desc")a.p.sortorder="asc"}else a.p.sortorder=
a.p.colModel[e].firstsortorder||"asc";a.p.page=1}if(f){if(a.p.lastsort==e&&a.p.sortorder==f&&!d)return;a.p.sortorder=f}d=a.grid.headers[a.p.lastsort].el;f=a.grid.headers[e].el;b("span.ui-grid-ico-sort",d).addClass("ui-state-disabled");b(d).attr("aria-selected","false");b("span.ui-icon-"+a.p.sortorder,f).removeClass("ui-state-disabled");b(f).attr("aria-selected","true");if(!a.p.viewsortcols[0]&&a.p.lastsort!=e){b("span.s-ico",d).hide();b("span.s-ico",f).show()}c=c.substring(5+a.p.id.length+1);a.p.sortname=
a.p.colModel[e].index||c;d=a.p.sortorder;if(b(a).triggerHandler("jqGridSortCol",[c,e,d])==="stop")a.p.lastsort=e;else if(b.isFunction(a.p.onSortCol)&&a.p.onSortCol.call(a,c,e,d)=="stop")a.p.lastsort=e;else{if(a.p.datatype=="local")a.p.deselectAfterSort&&b(a).jqGrid("resetSelection");else{a.p.selrow=null;a.p.multiselect&&da(false);a.p.selarrrow=[];a.p.savedRow=[]}if(a.p.scroll){d=a.grid.bDiv.scrollLeft;P(a.grid.bDiv,true,false);a.grid.hDiv.scrollLeft=d}a.p.subGrid&&a.p.datatype=="local"&&b("td.sgexpanded",
"#"+b.jgrid.jqID(a.p.id)).each(function(){b(this).trigger("click")});H();a.p.lastsort=e;if(a.p.sortname!=c&&e)a.p.lastsort=e}}},oa=function(c){var e,d={},f=b.jgrid.cellWidth()?0:a.p.cellLayout;for(e=d[0]=d[1]=d[2]=0;e<=c;e++)a.p.colModel[e].hidden===false&&(d[0]=d[0]+(a.p.colModel[e].width+f));a.p.direction=="rtl"&&(d[0]=a.p.width-d[0]);d[0]=d[0]-a.grid.bDiv.scrollLeft;b(a.grid.cDiv).is(":visible")&&(d[1]=d[1]+(b(a.grid.cDiv).height()+parseInt(b(a.grid.cDiv).css("padding-top"),10)+parseInt(b(a.grid.cDiv).css("padding-bottom"),
10)));if(a.p.toolbar[0]===true&&(a.p.toolbar[1]=="top"||a.p.toolbar[1]=="both"))d[1]=d[1]+(b(a.grid.uDiv).height()+parseInt(b(a.grid.uDiv).css("border-top-width"),10)+parseInt(b(a.grid.uDiv).css("border-bottom-width"),10));a.p.toppager&&(d[1]=d[1]+(b(a.grid.topDiv).height()+parseInt(b(a.grid.topDiv).css("border-bottom-width"),10)));d[2]=d[2]+(b(a.grid.bDiv).height()+b(a.grid.hDiv).height());return d},ka=function(c){var d,e=a.grid.headers,f=b.jgrid.getCellIndex(c);for(d=0;d<e.length;d++)if(c===e[d].el){f=
d;break}return f};this.p.id=this.id;-1==b.inArray(a.p.multikey,["shiftKey","altKey","ctrlKey"])&&(a.p.multikey=!1);a.p.keyIndex=!1;for(f=0;f<a.p.colModel.length;f++)a.p.colModel[f]=b.extend(!0,{},a.p.cmTemplate,a.p.colModel[f].template||{},a.p.colModel[f]),!1===a.p.keyIndex&&!0===a.p.colModel[f].key&&(a.p.keyIndex=f);a.p.sortorder=a.p.sortorder.toLowerCase();!0===a.p.grouping&&(a.p.scroll=!1,a.p.rownumbers=!1,a.p.treeGrid=!1,a.p.gridview=!0);if(!0===this.p.treeGrid){try{b(this).jqGrid("setTreeGrid")}catch(qa){}"local"!=
a.p.datatype&&(a.p.localReader={id:"_id_"})}if(this.p.subGrid)try{b(a).jqGrid("setSubGrid")}catch(ra){}this.p.multiselect&&(this.p.colNames.unshift("<input role='checkbox' id='cb_"+this.p.id+"' class='cbox' type='checkbox'/>"),this.p.colModel.unshift({name:"cb",width:b.jgrid.cellWidth()?a.p.multiselectWidth+a.p.cellLayout:a.p.multiselectWidth,sortable:!1,resizable:!1,hidedlg:!0,search:!1,align:"center",fixed:!0}));this.p.rownumbers&&(this.p.colNames.unshift(""),this.p.colModel.unshift({name:"rn",
width:a.p.rownumWidth,sortable:!1,resizable:!1,hidedlg:!0,search:!1,align:"center",fixed:!0}));a.p.xmlReader=b.extend(!0,{root:"datas",row:"row",page:"rows>page",total:"rows>total",records:"rows>records",repeatitems:!0,cell:"cell",id:"[id]",userdata:"userdata",subgrid:{root:"datas",row:"row",repeatitems:!0,cell:"cell"}},a.p.xmlReader);a.p.jsonReader=b.extend(!0,{root:"datas",page:"page",total:"total",records:"records",repeatitems:!1,cell:"cell",id:"trid",userdata:"userdata",subgrid:{root:"datas",repeatitems:!1,
cell:"cell"}},a.p.jsonReader);a.p.localReader=b.extend(!0,{root:"datas",page:"page",total:"total",records:"records",repeatitems:!1,cell:"cell",id:"id",userdata:"userdata",subgrid:{root:"datas",repeatitems:!1,cell:"cell"}},a.p.localReader);a.p.scroll&&(a.p.pgbuttons=!1,a.p.pginput=!1,a.p.rowList=[]);a.p.data.length&&T();var x="<thead><tr class='ui-jqgrid-labels' role='rowheader'>",la,M,ea,ba,fa,z,l,X;M=X="";if(!0===a.p.shrinkToFit&&!0===a.p.forceFit)for(f=a.p.colModel.length-1;0<=f;f--)if(!a.p.colModel[f].hidden){a.p.colModel[f].resizable=
!1;break}"horizontal"==a.p.viewsortcols[1]&&(X=" ui-i-asc",M=" ui-i-desc");la=k?"class='ui-th-div-ie'":"";X="<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc"+X+" ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-"+h+"'></span>"+("<span sort='desc' class='ui-grid-ico-sort ui-icon-desc"+M+" ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-"+h+"'></span></span>");for(f=0;f<this.p.colNames.length;f++)M=a.p.headertitles?' title="'+b.jgrid.stripHtml(a.p.colNames[f])+
'"':"",x+="<th id='"+a.p.id+"_"+a.p.colModel[f].name+"' role='columnheader' class='ui-state-default ui-th-column ui-th-"+h+"'"+M+">",M=a.p.colModel[f].index||a.p.colModel[f].name,x+="<div id='jqgh_"+a.p.id+"_"+a.p.colModel[f].name+"' "+la+">"+a.p.colNames[f],a.p.colModel[f].width=a.p.colModel[f].width?parseInt(a.p.colModel[f].width,10):150,"boolean"!==typeof a.p.colModel[f].title&&(a.p.colModel[f].title=!0),M==a.p.sortname&&(a.p.lastsort=f),x+=X+"</div></th>";X=null;b(this).append(x+"</tr></thead>");
b("thead tr:first th",this).hover(function(){b(this).addClass("ui-state-hover")},function(){b(this).removeClass("ui-state-hover")});if(this.p.multiselect){var ga=[],Y;b("#cb_"+b.jgrid.jqID(a.p.id),this).bind("click",function(){a.p.selarrrow=[];var c=a.p.frozenColumns===true?a.p.id+"_frozen":"";if(this.checked){b(a.rows).each(function(d){if(d>0&&!b(this).hasClass("ui-subgrid")&&!b(this).hasClass("jqgroup")&&!b(this).hasClass("ui-state-disabled")){b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(this.id))[a.p.useProp?
"prop":"attr"]("checked",true);b(this).addClass("ui-state-highlight").attr("aria-selected","true");a.p.selarrrow.push(this.id);a.p.selrow=this.id;if(c){b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(this.id),a.grid.fbDiv)[a.p.useProp?"prop":"attr"]("checked",true);b("#"+b.jgrid.jqID(this.id),a.grid.fbDiv).addClass("ui-state-highlight")}}});Y=true;ga=[]}else{b(a.rows).each(function(d){if(d>0&&!b(this).hasClass("ui-subgrid")&&!b(this).hasClass("ui-state-disabled")){b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+
b.jgrid.jqID(this.id))[a.p.useProp?"prop":"attr"]("checked",false);b(this).removeClass("ui-state-highlight").attr("aria-selected","false");ga.push(this.id);if(c){b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(this.id),a.grid.fbDiv)[a.p.useProp?"prop":"attr"]("checked",false);b("#"+b.jgrid.jqID(this.id),a.grid.fbDiv).removeClass("ui-state-highlight")}}});a.p.selrow=null;Y=false}b(a).triggerHandler("jqGridSelectAll",[Y?a.p.selarrrow:ga,Y]);b.isFunction(a.p.onSelectAll)&&a.p.onSelectAll.call(a,Y?a.p.selarrrow:
ga,Y)})}!0===a.p.autowidth&&(x=b(n).innerWidth(),a.p.width=0<x?x:"nw");(function(){var d=0,e=b.jgrid.cellWidth()?0:p(a.p.cellLayout,0),f=0,g,h=p(a.p.scrollOffset,0),j,k=false,m,l=0,n=0,o;b.each(a.p.colModel,function(){if(typeof this.hidden==="undefined")this.hidden=false;this.widthOrg=j=p(this.width,0);if(this.hidden===false){d=d+(j+e);this.fixed?l=l+(j+e):f++;n++}});isNaN(a.p.width)?a.p.width=c.width=d:c.width=a.p.width;a.p.tblwidth=d;if(a.p.shrinkToFit===false&&a.p.forceFit===true)a.p.forceFit=
false;if(a.p.shrinkToFit===true&&f>0){m=c.width-e*f-l;if(!isNaN(a.p.height)){m=m-h;k=true}d=0;b.each(a.p.colModel,function(b){if(this.hidden===false&&!this.fixed){this.width=j=Math.round(m*this.width/(a.p.tblwidth-e*f-l));d=d+j;g=b}});o=0;k?c.width-l-(d+e*f)!==h&&(o=c.width-l-(d+e*f)-h):!k&&Math.abs(c.width-l-(d+e*f))!==1&&(o=c.width-l-(d+e*f));a.p.colModel[g].width=a.p.colModel[g].width+o;a.p.tblwidth=d+o+e*f+l;if(a.p.tblwidth>a.p.width){a.p.colModel[g].width=a.p.colModel[g].width-(a.p.tblwidth-
parseInt(a.p.width,10));a.p.tblwidth=a.p.width}}})();b(n).css("width",c.width+"px").append("<div class='ui-jqgrid-resize-mark' id='rs_m"+a.p.id+"'>&#160;</div>");b(j).css("width",c.width+"px");var x=b("thead:first",a).get(0),S="";a.p.footerrow&&(S+="<table role='grid' style='width:"+a.p.tblwidth+"px' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-"+h+"'>");var j=b("tr:first",x),Z="<tr class='jqgfirstrow' role='row' style='height:auto'>";
a.p.disableClick=!1;b("th",j).each(function(d){ea=a.p.colModel[d].width;if(typeof a.p.colModel[d].resizable==="undefined")a.p.colModel[d].resizable=true;if(a.p.colModel[d].resizable){ba=document.createElement("span");b(ba).html("&#160;").addClass("ui-jqgrid-resize ui-jqgrid-resize-"+h);b.browser.opera||b(ba).css("cursor","col-resize");b(this).addClass(a.p.resizeclass)}else ba="";b(this).css("width",ea+"px").prepend(ba);var e="";if(a.p.colModel[d].hidden){b(this).css("display","none");e="display:none;"}Z=
Z+("<td role='gridcell' style='height:0px;width:"+ea+"px;"+e+"'></td>");c.headers[d]={width:ea,el:this};fa=a.p.colModel[d].sortable;if(typeof fa!=="boolean")fa=a.p.colModel[d].sortable=true;e=a.p.colModel[d].name;e=="cb"||e=="subgrid"||e=="rn"||a.p.viewsortcols[2]&&b(">div",this).addClass("ui-jqgrid-sortable");if(fa)if(a.p.viewsortcols[0]){b("div span.s-ico",this).show();d==a.p.lastsort&&b("div span.ui-icon-"+a.p.sortorder,this).removeClass("ui-state-disabled")}else if(d==a.p.lastsort){b("div span.s-ico",
this).show();b("div span.ui-icon-"+a.p.sortorder,this).removeClass("ui-state-disabled")}a.p.footerrow&&(S=S+("<td role='gridcell' "+o(d,0,"",null,"",false)+">&#160;</td>"))}).mousedown(function(d){if(b(d.target).closest("th>span.ui-jqgrid-resize").length==1){var e=ka(this);if(a.p.forceFit===true){var f=a.p,g=e,h;for(h=e+1;h<a.p.colModel.length;h++)if(a.p.colModel[h].hidden!==true){g=h;break}f.nv=g-e}c.dragStart(e,d,oa(e));return false}}).click(function(c){if(a.p.disableClick)return a.p.disableClick=
false;var d="th>div.ui-jqgrid-sortable",e,f;a.p.viewsortcols[2]||(d="th>div>span>span.ui-grid-ico-sort");c=b(c.target).closest(d);if(c.length==1){d=ka(this);if(!a.p.viewsortcols[2]){e=true;f=c.attr("sort")}ja(b("div",this)[0].id,d,e,f);return false}});if(a.p.sortable&&b.fn.sortable)try{b(a).jqGrid("sortableColumns",j)}catch(sa){}a.p.footerrow&&(S+="</tr></tbody></table>");Z+="</tr>";this.appendChild(document.createElement("tbody"));b(this).addClass("ui-jqgrid-btable").append(Z);var Z=null,j=b("<table class='ui-jqgrid-htable' style='width:"+
a.p.tblwidth+"px' role='grid' aria-labelledby='gbox_"+this.id+"' cellspacing='0' cellpadding='0' border='0'></table>").append(x),C=a.p.caption&&!0===a.p.hiddengrid?!0:!1;f=b("<div class='ui-jqgrid-hbox"+("rtl"==h?"-rtl":"")+"'></div>");x=null;c.hDiv=document.createElement("div");b(c.hDiv).css({width:c.width+"px"}).addClass("ui-state-default ui-jqgrid-hdiv").append(f);b(f).append(j);j=null;C&&b(c.hDiv).hide();a.p.pager&&("string"==typeof a.p.pager?"#"!=a.p.pager.substr(0,1)&&(a.p.pager="#"+a.p.pager):
a.p.pager="#"+b(a.p.pager).attr("id"),b(a.p.pager).css({width:c.width+"px"}).appendTo(n).addClass("ui-state-default ui-jqgrid-pager ui-corner-bottom"),C&&b(a.p.pager).hide(),m(a.p.pager,""));!1===a.p.cellEdit&&!0===a.p.hoverrows&&b(a).bind("mouseover",function(a){l=b(a.target).closest("tr.jqgrow");b(l).attr("class")!=="ui-subgrid"&&b(l).addClass("ui-state-hover")}).bind("mouseout",function(a){l=b(a.target).closest("tr.jqgrow");b(l).removeClass("ui-state-hover")});var s,E,ha;b(a).before(c.hDiv).click(function(c){z=
c.target;l=b(z,a.rows).closest("tr.jqgrow");if(b(l).length===0||l[0].className.indexOf("ui-state-disabled")>-1||(b(z,a).closest("table.ui-jqgrid-btable").attr("id")||"").replace("_frozen","")!==a.id)return this;var d=b(z).hasClass("cbox"),e=b(a).triggerHandler("jqGridBeforeSelectRow",[l[0].id,c]);(e=e===false||e==="stop"?false:true)&&b.isFunction(a.p.beforeSelectRow)&&(e=a.p.beforeSelectRow.call(a,l[0].id,c));if(!(z.tagName=="A"||(z.tagName=="INPUT"||z.tagName=="TEXTAREA"||z.tagName=="OPTION"||z.tagName==
"SELECT")&&!d)&&e===true){s=l[0].id;E=b.jgrid.getCellIndex(z);ha=b(z).closest("td,th").html();b(a).triggerHandler("jqGridCellSelect",[s,E,ha,c]);b.isFunction(a.p.onCellSelect)&&a.p.onCellSelect.call(a,s,E,ha,c);if(a.p.cellEdit===true)if(a.p.multiselect&&d)b(a).jqGrid("setSelection",s,true,c);else{s=l[0].rowIndex;try{b(a).jqGrid("editCell",s,E,true)}catch(f){}}else if(a.p.multikey)if(c[a.p.multikey])b(a).jqGrid("setSelection",s,true,c);else{if(a.p.multiselect&&d){d=b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+
s).is(":checked");b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+s)[a.p.useProp?"prop":"attr"]("checked",d)}}else{if(a.p.multiselect&&a.p.multiboxonly&&!d){var g=a.p.frozenColumns?a.p.id+"_frozen":"";b(a.p.selarrrow).each(function(c,d){var e=a.rows.namedItem(d);b(e).removeClass("ui-state-highlight");b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(d))[a.p.useProp?"prop":"attr"]("checked",false);if(g){b("#"+b.jgrid.jqID(d),"#"+b.jgrid.jqID(g)).removeClass("ui-state-highlight");b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+
b.jgrid.jqID(d),"#"+b.jgrid.jqID(g))[a.p.useProp?"prop":"attr"]("checked",false)}});a.p.selarrrow=[]}b(a).jqGrid("setSelection",s,true,c)}}}).bind("reloadGrid",function(c,d){if(a.p.treeGrid===true)a.p.datatype=a.p.treedatatype;d&&d.current&&a.grid.selectionPreserver(a);if(a.p.datatype=="local"){b(a).jqGrid("resetSelection");a.p.data.length&&T()}else if(!a.p.treeGrid){a.p.selrow=null;if(a.p.multiselect){a.p.selarrrow=[];da(false)}a.p.savedRow=[]}a.p.scroll&&P(a.grid.bDiv,true,false);if(d&&d.page){var e=
d.page;if(e>a.p.lastpage)e=a.p.lastpage;e<1&&(e=1);a.p.page=e;a.grid.bDiv.scrollTop=a.grid.prevRowHeight?(e-1)*a.grid.prevRowHeight*a.p.rowNum:0}if(a.grid.prevRowHeight&&a.p.scroll){delete a.p.lastpage;a.grid.populateVisible()}else a.grid.populate();return false}).dblclick(function(c){z=c.target;l=b(z,a.rows).closest("tr.jqgrow");if(b(l).length!==0){s=l[0].rowIndex;E=b.jgrid.getCellIndex(z);b(a).triggerHandler("jqGridDblClickRow",[b(l).attr("id"),s,E,c]);b.isFunction(this.p.ondblClickRow)&&a.p.ondblClickRow.call(a,
b(l).attr("id"),s,E,c)}}).bind("contextmenu",function(c){z=c.target;l=b(z,a.rows).closest("tr.jqgrow");if(b(l).length!==0){a.p.multiselect||b(a).jqGrid("setSelection",l[0].id,true,c);s=l[0].rowIndex;E=b.jgrid.getCellIndex(z);b(a).triggerHandler("jqGridRightClickRow",[b(l).attr("id"),s,E,c]);b.isFunction(this.p.onRightClickRow)&&a.p.onRightClickRow.call(a,b(l).attr("id"),s,E,c)}});c.bDiv=document.createElement("div");k&&"auto"===(""+a.p.height).toLowerCase()&&(a.p.height="100%");b(c.bDiv).append(b('<div style="position:relative;'+
(k&&8>b.browser.version?"height:0.01%;":"")+'"></div>').append("<div></div>").append(this)).addClass("ui-jqgrid-bdiv").css({height:a.p.height+(isNaN(a.p.height)?"":"px"),width:c.width+"px"}).scroll(c.scrollGrid);b("table:first",c.bDiv).css({width:a.p.tblwidth+"px"});k?(2==b("tbody",this).size()&&b("tbody:gt(0)",this).remove(),a.p.multikey&&b(c.bDiv).bind("selectstart",function(){return false})):a.p.multikey&&b(c.bDiv).bind("mousedown",function(){return false});C&&b(c.bDiv).hide();c.cDiv=document.createElement("div");
var ia=!0===a.p.hidegrid?b("<a role='link' href='javascript:void(0)'/>").addClass("ui-jqgrid-titlebar-close HeaderButton").hover(function(){ia.addClass("ui-state-hover")},function(){ia.removeClass("ui-state-hover")}).append("<span class='ui-icon ui-icon-circle-triangle-n'></span>").css("rtl"==h?"left":"right","0px"):"";b(c.cDiv).append(ia).append("<span class='ui-jqgrid-title"+("rtl"==h?"-rtl":"")+"'>"+a.p.caption+"</span>").addClass("ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix");
b(c.cDiv).insertBefore(c.hDiv);a.p.toolbar[0]&&(c.uDiv=document.createElement("div"),"top"==a.p.toolbar[1]?b(c.uDiv).insertBefore(c.hDiv):"bottom"==a.p.toolbar[1]&&b(c.uDiv).insertAfter(c.hDiv),"both"==a.p.toolbar[1]?(c.ubDiv=document.createElement("div"),b(c.uDiv).insertBefore(c.hDiv).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id),b(c.ubDiv).insertAfter(c.hDiv).addClass("ui-userdata ui-state-default").attr("id","tb_"+this.id),C&&b(c.ubDiv).hide()):b(c.uDiv).width(c.width).addClass("ui-userdata ui-state-default").attr("id",
"t_"+this.id),C&&b(c.uDiv).hide());a.p.toppager&&(a.p.toppager=b.jgrid.jqID(a.p.id)+"_toppager",c.topDiv=b("<div id='"+a.p.toppager+"'></div>")[0],a.p.toppager="#"+a.p.toppager,b(c.topDiv).insertBefore(c.hDiv).addClass("ui-state-default ui-jqgrid-toppager").width(c.width),m(a.p.toppager,"_t"));a.p.footerrow&&(c.sDiv=b("<div class='ui-jqgrid-sdiv'></div>")[0],f=b("<div class='ui-jqgrid-hbox"+("rtl"==h?"-rtl":"")+"'></div>"),b(c.sDiv).append(f).insertAfter(c.hDiv).width(c.width),b(f).append(S),c.footers=
b(".ui-jqgrid-ftable",c.sDiv)[0].rows[0].cells,a.p.rownumbers&&(c.footers[0].className="ui-state-default jqgrid-rownum"),C&&b(c.sDiv).hide());f=null;if(a.p.caption){var pa=a.p.datatype;!0===a.p.hidegrid&&(b(".ui-jqgrid-titlebar-close",c.cDiv).click(function(d){var e=b.isFunction(a.p.onHeaderClick),f=".ui-jqgrid-bdiv, .ui-jqgrid-hdiv, .ui-jqgrid-pager, .ui-jqgrid-sdiv",g,h=this;if(a.p.toolbar[0]===true){a.p.toolbar[1]=="both"&&(f=f+(", #"+b(c.ubDiv).attr("id")));f=f+(", #"+b(c.uDiv).attr("id"))}g=
b(f,"#gview_"+b.jgrid.jqID(a.p.id)).length;a.p.gridstate=="visible"?b(f,"#gbox_"+b.jgrid.jqID(a.p.id)).slideUp("fast",function(){g--;if(g===0){b("span",h).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");a.p.gridstate="hidden";b("#gbox_"+b.jgrid.jqID(a.p.id)).hasClass("ui-resizable")&&b(".ui-resizable-handle","#gbox_"+b.jgrid.jqID(a.p.id)).hide();b(a).triggerHandler("jqGridHeaderClick",[a.p.gridstate,d]);e&&(C||a.p.onHeaderClick.call(a,a.p.gridstate,d))}}):a.p.gridstate==
"hidden"&&b(f,"#gbox_"+b.jgrid.jqID(a.p.id)).slideDown("fast",function(){g--;if(g===0){b("span",h).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");if(C){a.p.datatype=pa;H();C=false}a.p.gridstate="visible";b("#gbox_"+b.jgrid.jqID(a.p.id)).hasClass("ui-resizable")&&b(".ui-resizable-handle","#gbox_"+b.jgrid.jqID(a.p.id)).show();b(a).triggerHandler("jqGridHeaderClick",[a.p.gridstate,d]);e&&(C||a.p.onHeaderClick.call(a,a.p.gridstate,d))}});return false}),C&&(a.p.datatype=
"local",b(".ui-jqgrid-titlebar-close",c.cDiv).trigger("click")))}else b(c.cDiv).hide();b(c.hDiv).after(c.bDiv).mousemove(function(a){if(c.resizing){c.dragMove(a);return false}});b(".ui-jqgrid-labels",c.hDiv).bind("selectstart",function(){return false});b(document).mouseup(function(){if(c.resizing){c.dragEnd();return false}return true});a.formatCol=o;a.sortData=ja;a.updatepager=function(c,d){var e,f,g,h,i,j,k,m="",l=a.p.pager?"_"+b.jgrid.jqID(a.p.pager.substr(1)):"",n=a.p.toppager?"_"+a.p.toppager.substr(1):
"";g=parseInt(a.p.page,10)-1;g<0&&(g=0);g=g*parseInt(a.p.rowNum,10);i=g+a.p.reccount;if(a.p.scroll){e=b("tbody:first > tr:gt(0)",a.grid.bDiv);g=i-e.length;a.p.reccount=e.length;if(f=e.outerHeight()||a.grid.prevRowHeight){e=g*f;f=parseInt(a.p.records,10)*f;b(">div:first",a.grid.bDiv).css({height:f}).children("div:first").css({height:e,display:e?"":"none"})}a.grid.bDiv.scrollLeft=a.grid.hDiv.scrollLeft}m=a.p.pager?a.p.pager:"";if(m=m+(a.p.toppager?m?","+a.p.toppager:a.p.toppager:"")){k=b.jgrid.formatter.integer||
{};e=p(a.p.page);f=p(a.p.lastpage);b(".selbox",m)[this.p.useProp?"prop":"attr"]("disabled",false);if(a.p.pginput===true){b(".ui-pg-input",m).val(a.p.page);h=a.p.toppager?"#sp_1"+l+",#sp_1"+n:"#sp_1"+l;b(h).html(b.fmatter?b.fmatter.util.NumberFormat(a.p.lastpage,k):a.p.lastpage)}if(a.p.viewrecords)if(a.p.reccount===0)b(".ui-paging-info",m).html(a.p.emptyrecords);else{h=g+1;j=a.p.records;if(b.fmatter){h=b.fmatter.util.NumberFormat(h,k);i=b.fmatter.util.NumberFormat(i,k);j=b.fmatter.util.NumberFormat(j,
k)}b(".ui-paging-info",m).html(b.jgrid.format(a.p.recordtext,h,i,j))}if(a.p.pgbuttons===true){e<=0&&(e=f=0);if(e==1||e===0){b("#first"+l+", #prev"+l).addClass("ui-state-disabled").removeClass("ui-state-hover");a.p.toppager&&b("#first_t"+n+", #prev_t"+n).addClass("ui-state-disabled").removeClass("ui-state-hover")}else{b("#first"+l+", #prev"+l).removeClass("ui-state-disabled");a.p.toppager&&b("#first_t"+n+", #prev_t"+n).removeClass("ui-state-disabled")}if(e==f||e===0){b("#next"+l+", #last"+l).addClass("ui-state-disabled").removeClass("ui-state-hover");
a.p.toppager&&b("#next_t"+n+", #last_t"+n).addClass("ui-state-disabled").removeClass("ui-state-hover")}else{b("#next"+l+", #last"+l).removeClass("ui-state-disabled");a.p.toppager&&b("#next_t"+n+", #last_t"+n).removeClass("ui-state-disabled")}}}c===true&&a.p.rownumbers===true&&b("td.jqgrid-rownum",a.rows).each(function(a){b(this).html(g+1+a)});d&&a.p.jqgdnd&&b(a).jqGrid("gridDnD","updateDnD");b(a).triggerHandler("jqGridGridComplete");b.isFunction(a.p.gridComplete)&&a.p.gridComplete.call(a);b(a).triggerHandler("jqGridAfterGridComplete")};
a.refreshIndex=T;a.setHeadCheckBox=da;a.constructTr=I;a.formatter=function(a,b,c,d,e){return w(a,b,c,d,e)};b.extend(c,{populate:H,emptyRows:P});this.grid=c;a.addXmlData=function(b){$(b,a.grid.bDiv)};a.addJSONData=function(b){aa(b,a.grid.bDiv)};this.grid.cols=this.rows[0].cells;H();a.p.hiddengrid=!1;b(window).unload(function(){a=null})}}}})};b.jgrid.extend({getGridParam:function(b){var f=this[0];return!f||!f.grid?void 0:b?"undefined"!=typeof f.p[b]?f.p[b]:null:f.p},setGridParam:function(d){return this.each(function(){this.grid&&
"object"===typeof d&&b.extend(!0,this.p,d)})},getDataIDs:function(){var d=[],f=0,c,e=0;this.each(function(){if((c=this.rows.length)&&0<c)for(;f<c;)b(this.rows[f]).hasClass("jqgrow")&&(d[e]=this.rows[f].id,e++),f++});return d},setSelection:function(d,f,c){return this.each(function(){var e,a,i,g,h,j;if(void 0!==d&&(f=!1===f?!1:!0,(a=this.rows.namedItem(d+""))&&a.className&&!(-1<a.className.indexOf("ui-state-disabled"))))(!0===this.p.scrollrows&&(i=this.rows.namedItem(d).rowIndex,0<=i&&(e=b(this.grid.bDiv)[0].clientHeight,
g=b(this.grid.bDiv)[0].scrollTop,h=this.rows[i].offsetTop,i=this.rows[i].clientHeight,h+i>=e+g?b(this.grid.bDiv)[0].scrollTop=h-(e+g)+i+g:h<e+g&&h<g&&(b(this.grid.bDiv)[0].scrollTop=h))),!0===this.p.frozenColumns&&(j=this.p.id+"_frozen"),this.p.multiselect)?(this.setHeadCheckBox(!1),this.p.selrow=a.id,g=b.inArray(this.p.selrow,this.p.selarrrow),-1===g?("ui-subgrid"!==a.className&&b(a).addClass("ui-state-highlight").attr("aria-selected","true"),e=!0,this.p.selarrrow.push(this.p.selrow)):("ui-subgrid"!==
a.className&&b(a).removeClass("ui-state-highlight").attr("aria-selected","false"),e=!1,this.p.selarrrow.splice(g,1),h=this.p.selarrrow[0],this.p.selrow=void 0===h?null:h),b("#jqg_"+b.jgrid.jqID(this.p.id)+"_"+b.jgrid.jqID(a.id))[this.p.useProp?"prop":"attr"]("checked",e),j&&(-1===g?b("#"+b.jgrid.jqID(d),"#"+b.jgrid.jqID(j)).addClass("ui-state-highlight"):b("#"+b.jgrid.jqID(d),"#"+b.jgrid.jqID(j)).removeClass("ui-state-highlight"),b("#jqg_"+b.jgrid.jqID(this.p.id)+"_"+b.jgrid.jqID(d),"#"+b.jgrid.jqID(j))[this.p.useProp?
"prop":"attr"]("checked",e)),b(this).triggerHandler("jqGridSelectRow",[a.id,e,c]),this.p.onSelectRow&&f&&this.p.onSelectRow.call(this,a.id,e,c)):"ui-subgrid"!==a.className&&(this.p.selrow!=a.id?(b(this.rows.namedItem(this.p.selrow)).removeClass("ui-state-highlight").attr({"aria-selected":"false",tabindex:"-1"}),b(a).addClass("ui-state-highlight").attr({"aria-selected":"true",tabindex:"0"}),j&&(b("#"+b.jgrid.jqID(this.p.selrow),"#"+b.jgrid.jqID(j)).removeClass("ui-state-highlight"),b("#"+b.jgrid.jqID(d),
"#"+b.jgrid.jqID(j)).addClass("ui-state-highlight")),e=!0):e=!1,this.p.selrow=a.id,b(this).triggerHandler("jqGridSelectRow",[a.id,e,c]),this.p.onSelectRow&&f&&this.p.onSelectRow.call(this,a.id,e,c))})},resetSelection:function(d){return this.each(function(){var f=this,c,e,a;!0===f.p.frozenColumns&&(a=f.p.id+"_frozen");if("undefined"!==typeof d){e=d===f.p.selrow?f.p.selrow:d;b("#"+b.jgrid.jqID(f.p.id)+" tbody:first tr#"+b.jgrid.jqID(e)).removeClass("ui-state-highlight").attr("aria-selected","false");
a&&b("#"+b.jgrid.jqID(e),"#"+b.jgrid.jqID(a)).removeClass("ui-state-highlight");if(f.p.multiselect){b("#jqg_"+b.jgrid.jqID(f.p.id)+"_"+b.jgrid.jqID(e),"#"+b.jgrid.jqID(f.p.id))[f.p.useProp?"prop":"attr"]("checked",!1);if(a)b("#jqg_"+b.jgrid.jqID(f.p.id)+"_"+b.jgrid.jqID(e),"#"+b.jgrid.jqID(a))[f.p.useProp?"prop":"attr"]("checked",!1);f.setHeadCheckBox(!1)}e=null}else f.p.multiselect?(b(f.p.selarrrow).each(function(d,e){c=f.rows.namedItem(e);b(c).removeClass("ui-state-highlight").attr("aria-selected",
"false");b("#jqg_"+b.jgrid.jqID(f.p.id)+"_"+b.jgrid.jqID(e))[f.p.useProp?"prop":"attr"]("checked",!1);a&&(b("#"+b.jgrid.jqID(e),"#"+b.jgrid.jqID(a)).removeClass("ui-state-highlight"),b("#jqg_"+b.jgrid.jqID(f.p.id)+"_"+b.jgrid.jqID(e),"#"+b.jgrid.jqID(a))[f.p.useProp?"prop":"attr"]("checked",!1))}),f.setHeadCheckBox(!1),f.p.selarrrow=[]):f.p.selrow&&(b("#"+b.jgrid.jqID(f.p.id)+" tbody:first tr#"+b.jgrid.jqID(f.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected","false"),a&&b("#"+b.jgrid.jqID(f.p.selrow),
"#"+b.jgrid.jqID(a)).removeClass("ui-state-highlight"),f.p.selrow=null);!0===f.p.cellEdit&&0<=parseInt(f.p.iCol,10)&&0<=parseInt(f.p.iRow,10)&&(b("td:eq("+f.p.iCol+")",f.rows[f.p.iRow]).removeClass("edit-cell ui-state-highlight"),b(f.rows[f.p.iRow]).removeClass("selected-row ui-state-hover"));f.p.savedRow=[]})},getRowData:function(d){var f={},c,e=!1,a,i=0;this.each(function(){var g=this,h,j;if("undefined"==typeof d)e=!0,c=[],a=g.rows.length;else{j=g.rows.namedItem(d);if(!j)return f;a=2}for(;i<a;)e&&
(j=g.rows[i]),b(j).hasClass("jqgrow")&&(b('td[role="gridcell"]',j).each(function(a){h=g.p.colModel[a].name;if("cb"!==h&&"subgrid"!==h&&"rn"!==h)if(!0===g.p.treeGrid&&h==g.p.ExpandColumn)f[h]=b.jgrid.htmlDecode(b("span:first",this).html());else try{f[h]=b.unformat.call(g,this,{rowId:j.id,colModel:g.p.colModel[a]},a)}catch(c){f[h]=b.jgrid.htmlDecode(b(this).html())}}),e&&(c.push(f),f={})),i++});return c?c:f},delRowData:function(d){var f=!1,c,e;this.each(function(){if(c=this.rows.namedItem(d)){if(b(c).remove(),
this.p.records--,this.p.reccount--,this.updatepager(!0,!1),f=!0,this.p.multiselect&&(e=b.inArray(d,this.p.selarrrow),-1!=e&&this.p.selarrrow.splice(e,1)),d==this.p.selrow)this.p.selrow=null}else return!1;if("local"==this.p.datatype){var a=this.p._index[b.jgrid.stripPref(this.p.idPrefix,d)];"undefined"!=typeof a&&(this.p.data.splice(a,1),this.refreshIndex())}if(!0===this.p.altRows&&f){var i=this.p.altclass;b(this.rows).each(function(a){1==a%2?b(this).addClass(i):b(this).removeClass(i)})}});return f},
setRowData:function(d,f,c){var e,a=!0,i;this.each(function(){if(!this.grid)return!1;var g=this,h,j,m=typeof c,k={};j=g.rows.namedItem(d);if(!j)return!1;if(f)try{if(b(this.p.colModel).each(function(a){e=this.name;void 0!==f[e]&&(k[e]=this.formatter&&"string"===typeof this.formatter&&"date"==this.formatter?b.unformat.date.call(g,f[e],this):f[e],h=g.formatter(d,f[e],a,f,"edit"),i=this.title?{title:b.jgrid.stripHtml(h)}:{},!0===g.p.treeGrid&&e==g.p.ExpandColumn?b("td:eq("+a+") > span:first",j).html(h).attr(i):
b("td:eq("+a+")",j).html(h).attr(i))}),"local"==g.p.datatype){var n=b.jgrid.stripPref(g.p.idPrefix,d),p=g.p._index[n];if(g.p.treeGrid)for(var o in g.p.treeReader)k.hasOwnProperty(g.p.treeReader[o])&&delete k[g.p.treeReader[o]];"undefined"!=typeof p&&(g.p.data[p]=b.extend(!0,g.p.data[p],k));k=null}}catch(u){a=!1}a&&("string"===m?b(j).addClass(c):"object"===m&&b(j).css(c),b(g).triggerHandler("jqGridAfterGridComplete"))});return a},addRowData:function(d,f,c,e){c||(c="last");var a=!1,i,g,h,j,m,k,n,p,
o="",u,w,N,D,O,V;f&&(b.isArray(f)?(u=!0,c="last",w=d):(f=[f],u=!1),this.each(function(){var W=f.length;m=this.p.rownumbers===true?1:0;h=this.p.multiselect===true?1:0;j=this.p.subGrid===true?1:0;if(!u)if(typeof d!="undefined")d=d+"";else{d=b.jgrid.randId();if(this.p.keyIndex!==false){w=this.p.colModel[this.p.keyIndex+h+j+m].name;typeof f[0][w]!="undefined"&&(d=f[0][w])}}N=this.p.altclass;for(var P=0,T="",I={},$=b.isFunction(this.p.afterInsertRow)?true:false;P<W;){D=f[P];g=[];if(u){try{d=D[w]}catch(aa){d=
b.jgrid.randId()}T=this.p.altRows===true?(this.rows.length-1)%2===0?N:"":""}V=d;d=this.p.idPrefix+d;if(m){o=this.formatCol(0,1,"",null,d,true);g[g.length]='<td role="gridcell" class="ui-state-default jqgrid-rownum" '+o+">0</td>"}if(h){p='<input role="checkbox" type="checkbox" id="jqg_'+this.p.id+"_"+d+'" class="cbox"/>';o=this.formatCol(m,1,"",null,d,true);g[g.length]='<td role="gridcell" '+o+">"+p+"</td>"}j&&(g[g.length]=b(this).jqGrid("addSubGridCell",h+m,1));for(n=h+j+m;n<this.p.colModel.length;n++){O=
this.p.colModel[n];i=O.name;I[i]=O.formatter&&typeof O.formatter==="string"&&O.formatter=="date"?b.unformat.date.call(this,D[i],O):D[i];p=this.formatter(d,b.jgrid.getAccessor(D,i),n,D,"edit");o=this.formatCol(n,1,p,D,d,true);g[g.length]='<td role="gridcell" '+o+">"+p+"</td>"}g.unshift(this.constructTr(d,false,T,I,I));g[g.length]="</tr>";if(this.rows.length===0)b("table:first",this.grid.bDiv).append(g.join(""));else switch(c){case "last":b(this.rows[this.rows.length-1]).after(g.join(""));k=this.rows.length-
1;break;case "first":b(this.rows[0]).after(g.join(""));k=1;break;case "after":(k=this.rows.namedItem(e))&&(b(this.rows[k.rowIndex+1]).hasClass("ui-subgrid")?b(this.rows[k.rowIndex+1]).after(g):b(k).after(g.join("")));k++;break;case "before":if(k=this.rows.namedItem(e)){b(k).before(g.join(""));k=k.rowIndex}k--}this.p.subGrid===true&&b(this).jqGrid("addSubGrid",h+m,k);this.p.records++;this.p.reccount++;b(this).triggerHandler("jqGridAfterInsertRow",[d,D,D]);$&&this.p.afterInsertRow.call(this,d,D,D);
P++;if(this.p.datatype=="local"){I[this.p.localReader.id]=V;this.p._index[V]=this.p.data.length;this.p.data.push(I);I={}}}this.p.altRows===true&&!u&&(c=="last"?(this.rows.length-1)%2==1&&b(this.rows[this.rows.length-1]).addClass(N):b(this.rows).each(function(a){a%2==1?b(this).addClass(N):b(this).removeClass(N)}));this.updatepager(true,true);a=true}));return a},footerData:function(d,f,c){function e(a){for(var b in a)if(a.hasOwnProperty(b))return!1;return!0}var a,i=!1,g={},h;"undefined"==typeof d&&
(d="get");"boolean"!=typeof c&&(c=!0);d=d.toLowerCase();this.each(function(){var j=this,m;if(!j.grid||!j.p.footerrow||"set"==d&&e(f))return!1;i=!0;b(this.p.colModel).each(function(e){a=this.name;"set"==d?void 0!==f[a]&&(m=c?j.formatter("",f[a],e,f,"edit"):f[a],h=this.title?{title:b.jgrid.stripHtml(m)}:{},b("tr.footrow td:eq("+e+")",j.grid.sDiv).html(m).attr(h),i=!0):"get"==d&&(g[a]=b("tr.footrow td:eq("+e+")",j.grid.sDiv).html())})});return"get"==d?g:i},showHideCol:function(d,f){return this.each(function(){var c=
this,e=!1,a=b.jgrid.cellWidth()?0:c.p.cellLayout,i;if(c.grid){"string"===typeof d&&(d=[d]);f="none"!=f?"":"none";var g=""===f?!0:!1,h=c.p.groupHeader&&("object"===typeof c.p.groupHeader||b.isFunction(c.p.groupHeader));h&&b(c).jqGrid("destroyGroupHeader",!1);b(this.p.colModel).each(function(h){if(-1!==b.inArray(this.name,d)&&this.hidden===g){if(!0===c.p.frozenColumns&&!0===this.frozen)return!0;b("tr",c.grid.hDiv).each(function(){b(this.cells[h]).css("display",f)});b(c.rows).each(function(){b(this).hasClass("jqgroup")||
b(this.cells[h]).css("display",f)});c.p.footerrow&&b("tr.footrow td:eq("+h+")",c.grid.sDiv).css("display",f);i=this.widthOrg?this.widthOrg:parseInt(this.width,10);c.p.tblwidth="none"===f?c.p.tblwidth-(i+a):c.p.tblwidth+(i+a);this.hidden=!g;e=!0;b(c).triggerHandler("jqGridShowHideCol",[g,this.name,h])}});!0===e&&b(c).jqGrid("setGridWidth",!0===c.p.shrinkToFit?c.p.tblwidth:c.p.width);h&&b(c).jqGrid("setGroupHeaders",c.p.groupHeader)}})},hideCol:function(d){return this.each(function(){b(this).jqGrid("showHideCol",
d,"none")})},showCol:function(d){return this.each(function(){b(this).jqGrid("showHideCol",d,"")})},remapColumns:function(d,f,c){function e(a){var c;c=a.length?b.makeArray(a):b.extend({},a);b.each(d,function(b){a[b]=c[this]})}function a(a,c){b(">tr"+(c||""),a).each(function(){var a=this,c=b.makeArray(a.cells);b.each(d,function(){var b=c[this];b&&a.appendChild(b)})})}var i=this.get(0);e(i.p.colModel);e(i.p.colNames);e(i.grid.headers);a(b("thead:first",i.grid.hDiv),c&&":not(.ui-jqgrid-labels)");f&&a(b("#"+
b.jgrid.jqID(i.p.id)+" tbody:first"),".jqgfirstrow, tr.jqgrow, tr.jqfoot");i.p.footerrow&&a(b("tbody:first",i.grid.sDiv));i.p.remapColumns&&(i.p.remapColumns.length?e(i.p.remapColumns):i.p.remapColumns=b.makeArray(d));i.p.lastsort=b.inArray(i.p.lastsort,d);i.p.treeGrid&&(i.p.expColInd=b.inArray(i.p.expColInd,d));b(i).triggerHandler("jqGridRemapColumns",[d,f,c])},setGridWidth:function(d,f){return this.each(function(){if(this.grid){var c=this,e,a=0,i=b.jgrid.cellWidth()?0:c.p.cellLayout,g,h=0,j=!1,
m=c.p.scrollOffset,k,n=0,p=0,o;"boolean"!=typeof f&&(f=c.p.shrinkToFit);if(!isNaN(d)){d=parseInt(d,10);c.grid.width=c.p.width=d;b("#gbox_"+b.jgrid.jqID(c.p.id)).css("width",d+"px");b("#gview_"+b.jgrid.jqID(c.p.id)).css("width",d+"px");b(c.grid.bDiv).css("width",d+"px");b(c.grid.hDiv).css("width",d+"px");c.p.pager&&b(c.p.pager).css("width",d+"px");c.p.toppager&&b(c.p.toppager).css("width",d+"px");!0===c.p.toolbar[0]&&(b(c.grid.uDiv).css("width",d+"px"),"both"==c.p.toolbar[1]&&b(c.grid.ubDiv).css("width",
d+"px"));c.p.footerrow&&b(c.grid.sDiv).css("width",d+"px");!1===f&&!0===c.p.forceFit&&(c.p.forceFit=!1);if(!0===f){b.each(c.p.colModel,function(){if(this.hidden===false){e=this.widthOrg?this.widthOrg:parseInt(this.width,10);a=a+(e+i);this.fixed?n=n+(e+i):h++;p++}});if(0===h)return;c.p.tblwidth=a;k=d-i*h-n;if(!isNaN(c.p.height)&&(b(c.grid.bDiv)[0].clientHeight<b(c.grid.bDiv)[0].scrollHeight||1===c.rows.length))j=!0,k-=m;var a=0,u=0<c.grid.cols.length;b.each(c.p.colModel,function(b){if(this.hidden===
false&&!this.fixed){e=this.widthOrg?this.widthOrg:parseInt(this.width,10);e=Math.round(k*e/(c.p.tblwidth-i*h-n));if(!(e<0)){this.width=e;a=a+e;c.grid.headers[b].width=e;c.grid.headers[b].el.style.width=e+"px";if(c.p.footerrow)c.grid.footers[b].style.width=e+"px";if(u)c.grid.cols[b].style.width=e+"px";g=b}}});if(!g)return;o=0;j?d-n-(a+i*h)!==m&&(o=d-n-(a+i*h)-m):1!==Math.abs(d-n-(a+i*h))&&(o=d-n-(a+i*h));c.p.colModel[g].width+=o;c.p.tblwidth=a+o+i*h+n;c.p.tblwidth>d?(j=c.p.tblwidth-parseInt(d,10),
c.p.tblwidth=d,e=c.p.colModel[g].width-=j):e=c.p.colModel[g].width;c.grid.headers[g].width=e;c.grid.headers[g].el.style.width=e+"px";u&&(c.grid.cols[g].style.width=e+"px");c.p.footerrow&&(c.grid.footers[g].style.width=e+"px")}c.p.tblwidth&&(b("table:first",c.grid.bDiv).css("width",c.p.tblwidth+"px"),b("table:first",c.grid.hDiv).css("width",c.p.tblwidth+"px"),c.grid.hDiv.scrollLeft=c.grid.bDiv.scrollLeft,c.p.footerrow&&b("table:first",c.grid.sDiv).css("width",c.p.tblwidth+"px"))}}})},setGridHeight:function(d){return this.each(function(){if(this.grid){var f=
b(this.grid.bDiv);f.css({height:d+(isNaN(d)?"":"px")});!0===this.p.frozenColumns&&b("#"+b.jgrid.jqID(this.p.id)+"_frozen").parent().height(f.height()-16);this.p.height=d;this.p.scroll&&this.grid.populateVisible()}})},setCaption:function(d){return this.each(function(){this.p.caption=d;b("span.ui-jqgrid-title, span.ui-jqgrid-title-rtl",this.grid.cDiv).html(d);b(this.grid.cDiv).show()})},setLabel:function(d,f,c,e){return this.each(function(){var a=-1;if(this.grid&&"undefined"!=typeof d&&(b(this.p.colModel).each(function(b){if(this.name==
d)return a=b,!1}),0<=a)){var i=b("tr.ui-jqgrid-labels th:eq("+a+")",this.grid.hDiv);if(f){var g=b(".s-ico",i);b("[id^=jqgh_]",i).empty().html(f).append(g);this.p.colNames[a]=f}c&&("string"===typeof c?b(i).addClass(c):b(i).css(c));"object"===typeof e&&b(i).attr(e)}})},setCell:function(d,f,c,e,a,i){return this.each(function(){var g=-1,h,j;if(this.grid&&(isNaN(f)?b(this.p.colModel).each(function(a){if(this.name==f)return g=a,!1}):g=parseInt(f,10),0<=g&&(h=this.rows.namedItem(d)))){var m=b("td:eq("+g+
")",h);if(""!==c||!0===i)h=this.formatter(d,c,g,h,"edit"),j=this.p.colModel[g].title?{title:b.jgrid.stripHtml(h)}:{},this.p.treeGrid&&0<b(".tree-wrap",b(m)).length?b("span",b(m)).html(h).attr(j):b(m).html(h).attr(j),"local"==this.p.datatype&&(h=this.p.colModel[g],c=h.formatter&&"string"===typeof h.formatter&&"date"==h.formatter?b.unformat.date.call(this,c,h):c,j=this.p._index[d],"undefined"!=typeof j&&(this.p.data[j][h.name]=c));"string"===typeof e?b(m).addClass(e):e&&b(m).css(e);"object"===typeof a&&
b(m).attr(a)}})},getCell:function(d,f){var c=!1;this.each(function(){var e=-1;if(this.grid&&(isNaN(f)?b(this.p.colModel).each(function(a){if(this.name===f)return e=a,!1}):e=parseInt(f,10),0<=e)){var a=this.rows.namedItem(d);if(a)try{c=b.unformat.call(this,b("td:eq("+e+")",a),{rowId:a.id,colModel:this.p.colModel[e]},e)}catch(i){c=b.jgrid.htmlDecode(b("td:eq("+e+")",a).html())}}});return c},getCol:function(d,f,c){var e=[],a,i=0,g,h,j,f="boolean"!=typeof f?!1:f;"undefined"==typeof c&&(c=!1);this.each(function(){var m=
-1;if(this.grid&&(isNaN(d)?b(this.p.colModel).each(function(a){if(this.name===d)return m=a,!1}):m=parseInt(d,10),0<=m)){var k=this.rows.length,n=0;if(k&&0<k){for(;n<k;){if(b(this.rows[n]).hasClass("jqgrow")){try{a=b.unformat.call(this,b(this.rows[n].cells[m]),{rowId:this.rows[n].id,colModel:this.p.colModel[m]},m)}catch(p){a=b.jgrid.htmlDecode(this.rows[n].cells[m].innerHTML)}c?(j=parseFloat(a),i+=j,0===n?h=g=j:(g=Math.min(g,j),h=Math.max(h,j))):f?e.push({id:this.rows[n].id,value:a}):e.push(a)}n++}if(c)switch(c.toLowerCase()){case "sum":e=
i;break;case "avg":e=i/k;break;case "count":e=k;break;case "min":e=g;break;case "max":e=h}}}});return e},clearGridData:function(d){return this.each(function(){if(this.grid){"boolean"!=typeof d&&(d=!1);if(this.p.deepempty)b("#"+b.jgrid.jqID(this.p.id)+" tbody:first tr:gt(0)").remove();else{var f=b("#"+b.jgrid.jqID(this.p.id)+" tbody:first tr:first")[0];b("#"+b.jgrid.jqID(this.p.id)+" tbody:first").empty().append(f)}this.p.footerrow&&d&&b(".ui-jqgrid-ftable td",this.grid.sDiv).html("&#160;");this.p.selrow=
null;this.p.selarrrow=[];this.p.savedRow=[];this.p.records=0;this.p.page=1;this.p.lastpage=0;this.p.reccount=0;this.p.data=[];this.p._index={};this.updatepager(!0,!1)}})},getInd:function(b,f){var c=!1,e;this.each(function(){(e=this.rows.namedItem(b))&&(c=!0===f?e:e.rowIndex)});return c},bindKeys:function(d){var f=b.extend({onEnter:null,onSpace:null,onLeftKey:null,onRightKey:null,scrollingRows:!0},d||{});return this.each(function(){var c=this;b("body").is("[role]")||b("body").attr("role","application");
c.p.scrollrows=f.scrollingRows;b(c).keydown(function(e){var a=b(c).find("tr[tabindex=0]")[0],d,g,h,j=c.p.treeReader.expanded_field;if(a)if(h=c.p._index[a.id],37===e.keyCode||38===e.keyCode||39===e.keyCode||40===e.keyCode){if(38===e.keyCode){g=a.previousSibling;d="";if(g)if(b(g).is(":hidden"))for(;g;){if(g=g.previousSibling,!b(g).is(":hidden")&&b(g).hasClass("jqgrow")){d=g.id;break}}else d=g.id;b(c).jqGrid("setSelection",d,!0,e)}if(40===e.keyCode){g=a.nextSibling;d="";if(g)if(b(g).is(":hidden"))for(;g;){if(g=
g.nextSibling,!b(g).is(":hidden")&&b(g).hasClass("jqgrow")){d=g.id;break}}else d=g.id;b(c).jqGrid("setSelection",d,!0,e)}37===e.keyCode&&(c.p.treeGrid&&c.p.data[h][j]&&b(a).find("div.treeclick").trigger("click"),b(c).triggerHandler("jqGridKeyLeft",[c.p.selrow]),b.isFunction(f.onLeftKey)&&f.onLeftKey.call(c,c.p.selrow));39===e.keyCode&&(c.p.treeGrid&&!c.p.data[h][j]&&b(a).find("div.treeclick").trigger("click"),b(c).triggerHandler("jqGridKeyRight",[c.p.selrow]),b.isFunction(f.onRightKey)&&f.onRightKey.call(c,
c.p.selrow))}else 13===e.keyCode?(b(c).triggerHandler("jqGridKeyEnter",[c.p.selrow]),b.isFunction(f.onEnter)&&f.onEnter.call(c,c.p.selrow)):32===e.keyCode&&(b(c).triggerHandler("jqGridKeySpace",[c.p.selrow]),b.isFunction(f.onSpace)&&f.onSpace.call(c,c.p.selrow))})})},unbindKeys:function(){return this.each(function(){b(this).unbind("keydown")})},getLocalRow:function(b){var f=!1,c;this.each(function(){"undefined"!==typeof b&&(c=this.p._index[b],0<=c&&(f=this.p.data[c]))});return f}})})(jQuery);
(function(b){b.fmatter={};b.extend(b.fmatter,{isBoolean:function(a){return"boolean"===typeof a},isObject:function(a){return a&&("object"===typeof a||b.isFunction(a))||!1},isString:function(a){return"string"===typeof a},isNumber:function(a){return"number"===typeof a&&isFinite(a)},isNull:function(a){return null===a},isUndefined:function(a){return"undefined"===typeof a},isValue:function(a){return this.isObject(a)||this.isString(a)||this.isNumber(a)||this.isBoolean(a)},isEmpty:function(a){if(!this.isString(a)&&
this.isValue(a))return!1;if(!this.isValue(a))return!0;a=b.trim(a).replace(/\&nbsp\;/ig,"").replace(/\&#160\;/ig,"");return""===a}});b.fn.fmatter=function(a,c,f,d,e){var g=c,f=b.extend({},b.jgrid.formatter,f);try{g=b.fn.fmatter[a].call(this,c,f,d,e)}catch(h){}return g};b.fmatter.util={NumberFormat:function(a,c){b.fmatter.isNumber(a)||(a*=1);if(b.fmatter.isNumber(a)){var f=0>a,d=a+"",e=c.decimalSeparator?c.decimalSeparator:".",g;if(b.fmatter.isNumber(c.decimalPlaces)){var h=c.decimalPlaces,d=Math.pow(10,
h),d=Math.round(a*d)/d+"";g=d.lastIndexOf(".");if(0<h){0>g?(d+=e,g=d.length-1):"."!==e&&(d=d.replace(".",e));for(;d.length-1-g<h;)d+="0"}}if(c.thousandsSeparator){h=c.thousandsSeparator;g=d.lastIndexOf(e);g=-1<g?g:d.length;for(var e=d.substring(g),i=-1,j=g;0<j;j--){i++;if(0===i%3&&j!==g&&(!f||1<j))e=h+e;e=d.charAt(j-1)+e}d=e}d=c.prefix?c.prefix+d:d;return d=c.suffix?d+c.suffix:d}return a},DateFormat:function(a,c,f,d){var e=/^\/Date\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\)\/$/,g="string"===
typeof c?c.match(e):null,e=function(a,b){a=""+a;for(b=parseInt(b,10)||2;a.length<b;)a="0"+a;return a},h={m:1,d:1,y:1970,h:0,i:0,s:0,u:0},i=0,j,k=["i18n"];k.i18n={dayNames:d.dayNames,monthNames:d.monthNames};a in d.masks&&(a=d.masks[a]);if(!isNaN(c-0)&&"u"==(""+a).toLowerCase())i=new Date(1E3*parseFloat(c));else if(c.constructor===Date)i=c;else if(null!==g)i=new Date(parseInt(g[1],10)),g[3]&&(a=60*Number(g[5])+Number(g[6]),a*="-"==g[4]?1:-1,a-=i.getTimezoneOffset(),i.setTime(Number(Number(i)+6E4*a)));
else{c=(""+c).split(/[\\\/:_;.,\t\T\s-]/);a=a.split(/[\\\/:_;.,\t\T\s-]/);g=0;for(j=a.length;g<j;g++)"M"==a[g]&&(i=b.inArray(c[g],k.i18n.monthNames),-1!==i&&12>i&&(c[g]=i+1)),"F"==a[g]&&(i=b.inArray(c[g],k.i18n.monthNames),-1!==i&&11<i&&(c[g]=i+1-12)),c[g]&&(h[a[g].toLowerCase()]=parseInt(c[g],10));h.f&&(h.m=h.f);if(0===h.m&&0===h.y&&0===h.d)return"&#160;";h.m=parseInt(h.m,10)-1;i=h.y;70<=i&&99>=i?h.y=1900+h.y:0<=i&&69>=i&&(h.y=2E3+h.y);i=new Date(h.y,h.m,h.d,h.h,h.i,h.s,h.u)}f in d.masks?f=d.masks[f]:
f||(f="Y-m-d");a=i.getHours();c=i.getMinutes();h=i.getDate();g=i.getMonth()+1;j=i.getTimezoneOffset();var l=i.getSeconds(),r=i.getMilliseconds(),n=i.getDay(),m=i.getFullYear(),o=(n+6)%7+1,p=(new Date(m,g-1,h)-new Date(m,0,1))/864E5,q={d:e(h),D:k.i18n.dayNames[n],j:h,l:k.i18n.dayNames[n+7],N:o,S:d.S(h),w:n,z:p,W:5>o?Math.floor((p+o-1)/7)+1:Math.floor((p+o-1)/7)||(4>((new Date(m-1,0,1)).getDay()+6)%7?53:52),F:k.i18n.monthNames[g-1+12],m:e(g),M:k.i18n.monthNames[g-1],n:g,t:"?",L:"?",o:"?",Y:m,y:(""+
m).substring(2),a:12>a?d.AmPm[0]:d.AmPm[1],A:12>a?d.AmPm[2]:d.AmPm[3],B:"?",g:a%12||12,G:a,h:e(a%12||12),H:e(a),i:e(c),s:e(l),u:r,e:"?",I:"?",O:(0<j?"-":"+")+e(100*Math.floor(Math.abs(j)/60)+Math.abs(j)%60,4),P:"?",T:((""+i).match(/\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g)||[""]).pop().replace(/[^-+\dA-Z]/g,""),Z:"?",c:"?",r:"?",U:Math.floor(i/1E3)};return f.replace(/\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,
function(a){return a in q?q[a]:a.substring(1)})}};b.fn.fmatter.defaultFormat=function(a,c){return b.fmatter.isValue(a)&&""!==a?a:c.defaultValue?c.defaultValue:"&#160;"};b.fn.fmatter.email=function(a,c){return b.fmatter.isEmpty(a)?b.fn.fmatter.defaultFormat(a,c):'<a href="mailto:'+a+'">'+a+"</a>"};b.fn.fmatter.checkbox=function(a,c){var f=b.extend({},c.checkbox),d;void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));d=!0===f.disabled?'disabled="disabled"':
"";if(b.fmatter.isEmpty(a)||b.fmatter.isUndefined(a))a=b.fn.fmatter.defaultFormat(a,f);a=(a+"").toLowerCase();return'<input type="checkbox" '+(0>a.search(/(false|0|no|off)/i)?" checked='checked' ":"")+' value="'+a+'" offval="no" '+d+"/>"};b.fn.fmatter.link=function(a,c){var f={target:c.target},d="";void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));f.target&&(d="target="+f.target);return!b.fmatter.isEmpty(a)?"<a "+d+' href="'+a+'">'+
a+"</a>":b.fn.fmatter.defaultFormat(a,c)};b.fn.fmatter.showlink=function(a,c){var f={baseLinkUrl:c.baseLinkUrl,showAction:c.showAction,addParam:c.addParam||"",target:c.target,idName:c.idName},d="";void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));f.target&&(d="target="+f.target);f=f.baseLinkUrl+f.showAction+"?"+f.idName+"="+c.rowId+f.addParam;return b.fmatter.isString(a)||b.fmatter.isNumber(a)?"<a "+d+' href="'+f+'">'+a+"</a>":b.fn.fmatter.defaultFormat(a,
c)};b.fn.fmatter.integer=function(a,c){var f=b.extend({},c.integer);void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));return b.fmatter.isEmpty(a)?f.defaultValue:b.fmatter.util.NumberFormat(a,f)};b.fn.fmatter.number=function(a,c){var f=b.extend({},c.number);void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));return b.fmatter.isEmpty(a)?f.defaultValue:b.fmatter.util.NumberFormat(a,
f)};b.fn.fmatter.currency=function(a,c){var f=b.extend({},c.currency);void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));return b.fmatter.isEmpty(a)?f.defaultValue:b.fmatter.util.NumberFormat(a,f)};b.fn.fmatter.date=function(a,c,f,d){f=b.extend({},c.date);void 0!==c.colModel&&!b.fmatter.isUndefined(c.colModel.formatoptions)&&(f=b.extend({},f,c.colModel.formatoptions));return!f.reformatAfterEdit&&"edit"==d||b.fmatter.isEmpty(a)?b.fn.fmatter.defaultFormat(a,
c):b.fmatter.util.DateFormat(f.srcformat,a,f.newformat,f)};b.fn.fmatter.select=function(a,c){var a=a+"",f=!1,d=[],e,g;b.fmatter.isUndefined(c.colModel.formatoptions)?b.fmatter.isUndefined(c.colModel.editoptions)||(f=c.colModel.editoptions.value,e=void 0===c.colModel.editoptions.separator?":":c.colModel.editoptions.separator,g=void 0===c.colModel.editoptions.delimiter?";":c.colModel.editoptions.delimiter):(f=c.colModel.formatoptions.value,e=void 0===c.colModel.formatoptions.separator?":":c.colModel.formatoptions.separator,
g=void 0===c.colModel.formatoptions.delimiter?";":c.colModel.formatoptions.delimiter);if(f){var h=!0===c.colModel.editoptions.multiple?!0:!1,i=[];h&&(i=a.split(","),i=b.map(i,function(a){return b.trim(a)}));if(b.fmatter.isString(f))for(var j=f.split(g),k=0,l=0;l<j.length;l++)if(g=j[l].split(e),2<g.length&&(g[1]=b.map(g,function(a,b){if(b>0)return a}).join(e)),h)-1<b.inArray(g[0],i)&&(d[k]=g[1],k++);else{if(b.trim(g[0])==b.trim(a)){d[0]=g[1];break}}else b.fmatter.isObject(f)&&(h?d=b.map(i,function(a){return f[a]}):
d[0]=f[a]||"")}a=d.join(", ");return""===a?b.fn.fmatter.defaultFormat(a,c):a};b.fn.fmatter.rowactions=function(a,c,f,d){var e={keys:!1,onEdit:null,onSuccess:null,afterSave:null,onError:null,afterRestore:null,extraparam:{},url:null,restoreAfterError:!0,mtype:"POST",delOptions:{},editOptions:{}},a=b.jgrid.jqID(a),c=b.jgrid.jqID(c),d=b("#"+c)[0].p.colModel[d];b.fmatter.isUndefined(d.formatoptions)||(e=b.extend(e,d.formatoptions));b.fmatter.isUndefined(b("#"+c)[0].p.editOptions)||(e.editOptions=b("#"+
c)[0].p.editOptions);b.fmatter.isUndefined(b("#"+c)[0].p.delOptions)||(e.delOptions=b("#"+c)[0].p.delOptions);var g=b("#"+c)[0],d=function(d){b.isFunction(e.afterRestore)&&e.afterRestore.call(g,d);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide()};if(b("#"+a,"#"+c).hasClass("jqgrid-new-row")){var h=g.p.prmNames;e.extraparam[h.oper]=h.addoper}h={keys:e.keys,
oneditfunc:e.onEdit,successfunc:e.onSuccess,url:e.url,extraparam:e.extraparam,aftersavefunc:function(d,f){b.isFunction(e.afterSave)&&e.afterSave.call(g,d,f);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide()},errorfunc:e.onError,afterrestorefunc:d,restoreAfterError:e.restoreAfterError,mtype:e.mtype};switch(f){case "edit":b("#"+c).jqGrid("editRow",
a,h);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").hide();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").show();b(g).triggerHandler("jqGridAfterGridComplete");break;case "save":b("#"+c).jqGrid("saveRow",a,h)&&(b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show(),b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide(),
b(g).triggerHandler("jqGridAfterGridComplete"));break;case "cancel":b("#"+c).jqGrid("restoreRow",a,d);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide();b(g).triggerHandler("jqGridAfterGridComplete");break;case "del":b("#"+c).jqGrid("delGridRow",a,e.delOptions);break;case "formedit":b("#"+c).jqGrid("setSelection",a),b("#"+c).jqGrid("editGridRow",a,
e.editOptions)}};b.fn.fmatter.actions=function(a,c){var f={keys:!1,editbutton:!0,delbutton:!0,editformbutton:!1};b.fmatter.isUndefined(c.colModel.formatoptions)||(f=b.extend(f,c.colModel.formatoptions));var d=c.rowId,e="",g;if("undefined"==typeof d||b.fmatter.isEmpty(d))return"";f.editformbutton?(g="onclick=jQuery.fn.fmatter.rowactions('"+d+"','"+c.gid+"','formedit',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ",e=e+"<div title='"+
b.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+g+"><span class='ui-icon ui-icon-pencil'></span></div>"):f.editbutton&&(g="onclick=jQuery.fn.fmatter.rowactions('"+d+"','"+c.gid+"','edit',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover') ",e=e+"<div title='"+b.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+g+"><span class='ui-icon ui-icon-pencil'></span></div>");
f.delbutton&&(g="onclick=jQuery.fn.fmatter.rowactions('"+d+"','"+c.gid+"','del',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ",e=e+"<div title='"+b.jgrid.nav.deltitle+"' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' "+g+"><span class='ui-icon ui-icon-trash'></span></div>");g="onclick=jQuery.fn.fmatter.rowactions('"+d+"','"+c.gid+"','save',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";
e=e+"<div title='"+b.jgrid.edit.bSubmit+"' style='float:left;display:none' class='ui-pg-div ui-inline-save' "+g+"><span class='ui-icon ui-icon-disk'></span></div>";g="onclick=jQuery.fn.fmatter.rowactions('"+d+"','"+c.gid+"','cancel',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";e=e+"<div title='"+b.jgrid.edit.bCancel+"' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel' "+g+"><span class='ui-icon ui-icon-cancel'></span></div>";
return"<div style='margin-left:8px;'>"+e+"</div>"};b.unformat=function(a,c,f,d){var e,g=c.colModel.formatter,h=c.colModel.formatoptions||{},i=/([\.\*\_\'\(\)\{\}\+\?\\])/g,j=c.colModel.unformat||b.fn.fmatter[g]&&b.fn.fmatter[g].unformat;if("undefined"!==typeof j&&b.isFunction(j))e=j.call(this,b(a).text(),c,a);else if(!b.fmatter.isUndefined(g)&&b.fmatter.isString(g))switch(e=b.jgrid.formatter||{},g){case "integer":h=b.extend({},e.integer,h);c=h.thousandsSeparator.replace(i,"\\$1");e=b(a).text().replace(RegExp(c,
"g"),"");break;case "number":h=b.extend({},e.number,h);c=h.thousandsSeparator.replace(i,"\\$1");e=b(a).text().replace(RegExp(c,"g"),"").replace(h.decimalSeparator,".");break;case "currency":h=b.extend({},e.currency,h);c=h.thousandsSeparator.replace(i,"\\$1");c=RegExp(c,"g");e=b(a).text();h.prefix&&h.prefix.length&&(e=e.substr(h.prefix.length));h.suffix&&h.suffix.length&&(e=e.substr(0,e.length-h.suffix.length));e=e.replace(c,"").replace(h.decimalSeparator,".");break;case "checkbox":h=c.colModel.editoptions?
c.colModel.editoptions.value.split(":"):["Yes","No"];e=b("input",a).is(":checked")?h[0]:h[1];break;case "select":e=b.unformat.select(a,c,f,d);break;case "actions":return"";default:e=b(a).text()}return void 0!==e?e:!0===d?b(a).text():b.jgrid.htmlDecode(b(a).html())};b.unformat.select=function(a,c,f,d){f=[];a=b(a).text();if(!0===d)return a;var d=b.extend({},!b.fmatter.isUndefined(c.colModel.formatoptions)?c.colModel.formatoptions:c.colModel.editoptions),c=void 0===d.separator?":":d.separator,e=void 0===
d.delimiter?";":d.delimiter;if(d.value){var g=d.value,d=!0===d.multiple?!0:!1,h=[];d&&(h=a.split(","),h=b.map(h,function(a){return b.trim(a)}));if(b.fmatter.isString(g))for(var i=g.split(e),j=0,k=0;k<i.length;k++)if(e=i[k].split(c),2<e.length&&(e[1]=b.map(e,function(a,b){if(b>0)return a}).join(c)),d)-1<b.inArray(e[1],h)&&(f[j]=e[0],j++);else{if(b.trim(e[1])==b.trim(a)){f[0]=e[0];break}}else if(b.fmatter.isObject(g)||b.isArray(g))d||(h[0]=a),f=b.map(h,function(a){var c;b.each(g,function(b,d){if(d==
a){c=b;return false}});if(typeof c!="undefined")return c});return f.join(", ")}return a||""};b.unformat.date=function(a,c){var f=b.jgrid.formatter.date||{};b.fmatter.isUndefined(c.formatoptions)||(f=b.extend({},f,c.formatoptions));return!b.fmatter.isEmpty(a)?b.fmatter.util.DateFormat(f.newformat,a,f.srcformat,f):b.fn.fmatter.defaultFormat(a,c)}})(jQuery);
(function(a){a.jgrid.extend({getColProp:function(a){var d={},c=this[0];if(!c.grid)return!1;for(var c=c.p.colModel,e=0;e<c.length;e++)if(c[e].name==a){d=c[e];break}return d},setColProp:function(b,d){return this.each(function(){if(this.grid&&d)for(var c=this.p.colModel,e=0;e<c.length;e++)if(c[e].name==b){a.extend(this.p.colModel[e],d);break}})},sortGrid:function(a,d,c){return this.each(function(){var e=-1;if(this.grid){a||(a=this.p.sortname);for(var h=0;h<this.p.colModel.length;h++)if(this.p.colModel[h].index==
a||this.p.colModel[h].name==a){e=h;break}-1!=e&&(h=this.p.colModel[e].sortable,"boolean"!==typeof h&&(h=!0),"boolean"!==typeof d&&(d=!1),h&&this.sortData("jqgh_"+this.p.id+"_"+a,e,d,c))}})},GridDestroy:function(){return this.each(function(){if(this.grid){this.p.pager&&a(this.p.pager).remove();try{a("#gbox_"+a.jgrid.jqID(this.id)).remove()}catch(b){}}})},GridUnload:function(){return this.each(function(){if(this.grid){var b=a(this).attr("id"),d=a(this).attr("class");this.p.pager&&a(this.p.pager).empty().removeClass("ui-state-default ui-jqgrid-pager corner-bottom");
var c=document.createElement("table");a(c).attr({id:b});c.className=d;b=a.jgrid.jqID(this.id);a(c).removeClass("ui-jqgrid-btable");1===a(this.p.pager).parents("#gbox_"+b).length?(a(c).insertBefore("#gbox_"+b).show(),a(this.p.pager).insertBefore("#gbox_"+b)):a(c).insertBefore("#gbox_"+b).show();a("#gbox_"+b).remove()}})},setGridState:function(b){return this.each(function(){this.grid&&("hidden"==b?(a(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv","#gview_"+a.jgrid.jqID(this.p.id)).slideUp("fast"),this.p.pager&&
a(this.p.pager).slideUp("fast"),this.p.toppager&&a(this.p.toppager).slideUp("fast"),!0===this.p.toolbar[0]&&("both"==this.p.toolbar[1]&&a(this.grid.ubDiv).slideUp("fast"),a(this.grid.uDiv).slideUp("fast")),this.p.footerrow&&a(".ui-jqgrid-sdiv","#gbox_"+a.jgrid.jqID(this.p.id)).slideUp("fast"),a(".ui-jqgrid-titlebar-close span",this.grid.cDiv).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s"),this.p.gridstate="hidden"):"visible"==b&&(a(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv",
"#gview_"+a.jgrid.jqID(this.p.id)).slideDown("fast"),this.p.pager&&a(this.p.pager).slideDown("fast"),this.p.toppager&&a(this.p.toppager).slideDown("fast"),!0===this.p.toolbar[0]&&("both"==this.p.toolbar[1]&&a(this.grid.ubDiv).slideDown("fast"),a(this.grid.uDiv).slideDown("fast")),this.p.footerrow&&a(".ui-jqgrid-sdiv","#gbox_"+a.jgrid.jqID(this.p.id)).slideDown("fast"),a(".ui-jqgrid-titlebar-close span",this.grid.cDiv).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n"),
this.p.gridstate="visible"))})},filterToolbar:function(b){b=a.extend({autosearch:!0,searchOnEnter:!0,beforeSearch:null,afterSearch:null,beforeClear:null,afterClear:null,searchurl:"",stringResult:!1,groupOp:"AND",defaultSearch:"bw"},b||{});return this.each(function(){function d(b,c){var d=a(b);d[0]&&jQuery.each(c,function(){void 0!==this.data?d.bind(this.type,this.data,this.fn):d.bind(this.type,this.fn)})}var c=this;if(!this.ftoolbar){var e=function(){var d={},j=0,g,f,h={},m;a.each(c.p.colModel,function(){f=
this.index||this.name;m=this.searchoptions&&this.searchoptions.sopt?this.searchoptions.sopt[0]:"select"==this.stype?"eq":b.defaultSearch;if(g=a("#gs_"+a.jgrid.jqID(this.name),!0===this.frozen&&!0===c.p.frozenColumns?c.grid.fhDiv:c.grid.hDiv).val())d[f]=g,h[f]=m,j++;else try{delete c.p.postData[f]}catch(e){}});var e=0<j?!0:!1;if(!0===b.stringResult||"local"==c.p.datatype){var k='{"groupOp":"'+b.groupOp+'","rules":[',l=0;a.each(d,function(a,b){0<l&&(k+=",");k+='{"field":"'+a+'",';k+='"op":"'+h[a]+'",';
k+='"data":"'+(b+"").replace(/\\/g,"\\\\").replace(/\"/g,'\\"')+'"}';l++});k+="]}";a.extend(c.p.postData,{filters:k});a.each(["searchField","searchString","searchOper"],function(a,b){c.p.postData.hasOwnProperty(b)&&delete c.p.postData[b]})}else a.extend(c.p.postData,d);var p;c.p.searchurl&&(p=c.p.url,a(c).jqGrid("setGridParam",{url:c.p.searchurl}));var r="stop"===a(c).triggerHandler("jqGridToolbarBeforeSearch")?!0:!1;!r&&a.isFunction(b.beforeSearch)&&(r=b.beforeSearch.call(c));r||a(c).jqGrid("setGridParam",
{search:e}).trigger("reloadGrid",[{page:1}]);p&&a(c).jqGrid("setGridParam",{url:p});a(c).triggerHandler("jqGridToolbarAfterSearch");a.isFunction(b.afterSearch)&&b.afterSearch.call(c)},h=a("<tr class='ui-search-toolbar' role='rowheader'></tr>"),g;a.each(c.p.colModel,function(){var i=this,j,q,f,n;q=a("<th role='columnheader' class='ui-state-default ui-th-column ui-th-"+c.p.direction+"'></th>");j=a("<div style='width:100%;position:relative;height:100%;padding-right:0.3em;'></div>");!0===this.hidden&&
a(q).css("display","none");this.search=!1===this.search?!1:!0;"undefined"==typeof this.stype&&(this.stype="text");f=a.extend({},this.searchoptions||{});if(this.search)switch(this.stype){case "select":if(n=this.surl||f.dataUrl)a.ajax(a.extend({url:n,dataType:"html",success:function(c){if(f.buildSelect!==void 0)(c=f.buildSelect(c))&&a(j).append(c);else a(j).append(c);f.defaultValue!==void 0&&a("select",j).val(f.defaultValue);a("select",j).attr({name:i.index||i.name,id:"gs_"+i.name});f.attr&&a("select",
j).attr(f.attr);a("select",j).css({width:"100%"});f.dataInit!==void 0&&f.dataInit(a("select",j)[0]);f.dataEvents!==void 0&&d(a("select",j)[0],f.dataEvents);b.autosearch===true&&a("select",j).change(function(){e();return false});c=null}},a.jgrid.ajaxOptions,c.p.ajaxSelectOptions||{}));else{var m,o,k;i.searchoptions?(m=void 0===i.searchoptions.value?"":i.searchoptions.value,o=void 0===i.searchoptions.separator?":":i.searchoptions.separator,k=void 0===i.searchoptions.delimiter?";":i.searchoptions.delimiter):
i.editoptions&&(m=void 0===i.editoptions.value?"":i.editoptions.value,o=void 0===i.editoptions.separator?":":i.editoptions.separator,k=void 0===i.editoptions.delimiter?";":i.editoptions.delimiter);if(m){n=document.createElement("select");n.style.width="100%";a(n).attr({name:i.index||i.name,id:"gs_"+i.name});var l;if("string"===typeof m){m=m.split(k);for(var p=0;p<m.length;p++)l=m[p].split(o),k=document.createElement("option"),k.value=l[0],k.innerHTML=l[1],n.appendChild(k)}else if("object"===typeof m)for(l in m)m.hasOwnProperty(l)&&
(k=document.createElement("option"),k.value=l,k.innerHTML=m[l],n.appendChild(k));void 0!==f.defaultValue&&a(n).val(f.defaultValue);f.attr&&a(n).attr(f.attr);void 0!==f.dataInit&&f.dataInit(n);void 0!==f.dataEvents&&d(n,f.dataEvents);a(j).append(n);!0===b.autosearch&&a(n).change(function(){e();return false})}}break;case "text":o=void 0!==f.defaultValue?f.defaultValue:"",a(j).append("<input type='text' style='width:95%;padding:0px;' name='"+(i.index||i.name)+"' id='gs_"+i.name+"' value='"+o+"'/>"),
f.attr&&a("input",j).attr(f.attr),void 0!==f.dataInit&&f.dataInit(a("input",j)[0]),void 0!==f.dataEvents&&d(a("input",j)[0],f.dataEvents),!0===b.autosearch&&(b.searchOnEnter?a("input",j).keypress(function(a){if((a.charCode?a.charCode:a.keyCode?a.keyCode:0)==13){e();return false}return this}):a("input",j).keydown(function(a){switch(a.which){case 13:return false;case 9:case 16:case 37:case 38:case 39:case 40:case 27:break;default:g&&clearTimeout(g);g=setTimeout(function(){e()},500)}}))}a(q).append(j);
a(h).append(q)});a("table thead",c.grid.hDiv).append(h);this.ftoolbar=!0;this.triggerToolbar=e;this.clearToolbar=function(d){var j={},g=0,f,d="boolean"!=typeof d?!0:d;a.each(c.p.colModel,function(){var b;this.searchoptions&&void 0!==this.searchoptions.defaultValue&&(b=this.searchoptions.defaultValue);f=this.index||this.name;switch(this.stype){case "select":a("#gs_"+a.jgrid.jqID(this.name)+" option",!0===this.frozen&&!0===c.p.frozenColumns?c.grid.fhDiv:c.grid.hDiv).each(function(c){if(c===0)this.selected=
true;if(a(this).val()==b){this.selected=true;return false}});if(void 0!==b)j[f]=b,g++;else try{delete c.p.postData[f]}catch(d){}break;case "text":if(a("#gs_"+a.jgrid.jqID(this.name),!0===this.frozen&&!0===c.p.frozenColumns?c.grid.fhDiv:c.grid.hDiv).val(b),void 0!==b)j[f]=b,g++;else try{delete c.p.postData[f]}catch(e){}}});var h=0<g?!0:!1;if(!0===b.stringResult||"local"==c.p.datatype){var e='{"groupOp":"'+b.groupOp+'","rules":[',o=0;a.each(j,function(a,b){0<o&&(e+=",");e+='{"field":"'+a+'",';e+='"op":"eq",';
e+='"data":"'+(b+"").replace(/\\/g,"\\\\").replace(/\"/g,'\\"')+'"}';o++});e+="]}";a.extend(c.p.postData,{filters:e});a.each(["searchField","searchString","searchOper"],function(a,b){c.p.postData.hasOwnProperty(b)&&delete c.p.postData[b]})}else a.extend(c.p.postData,j);var k;c.p.searchurl&&(k=c.p.url,a(c).jqGrid("setGridParam",{url:c.p.searchurl}));var l="stop"===a(c).triggerHandler("jqGridToolbarBeforeClear")?!0:!1;!l&&a.isFunction(b.beforeClear)&&(l=b.beforeClear.call(c));l||d&&a(c).jqGrid("setGridParam",
{search:h}).trigger("reloadGrid",[{page:1}]);k&&a(c).jqGrid("setGridParam",{url:k});a(c).triggerHandler("jqGridToolbarAfterClear");a.isFunction(b.afterClear)&&b.afterClear()};this.toggleToolbar=function(){var b=a("tr.ui-search-toolbar",c.grid.hDiv),d=!0===c.p.frozenColumns?a("tr.ui-search-toolbar",c.grid.fhDiv):!1;"none"==b.css("display")?(b.show(),d&&d.show()):(b.hide(),d&&d.hide())}}})},destroyGroupHeader:function(b){"undefined"==typeof b&&(b=!0);return this.each(function(){var d,c,e,h,g,i;c=this.grid;
var j=a("table.ui-jqgrid-htable thead",c.hDiv),q=this.p.colModel;if(c){a(this).unbind(".setGroupHeaders");d=a("<tr>",{role:"rowheader"}).addClass("ui-jqgrid-labels");h=c.headers;c=0;for(e=h.length;c<e;c++){g=q[c].hidden?"none":"";g=a(h[c].el).width(h[c].width).css("display",g);try{g.removeAttr("rowSpan")}catch(f){g.attr("rowSpan",1)}d.append(g);i=g.children("span.ui-jqgrid-resize");0<i.length&&(i[0].style.height="");g.children("div")[0].style.top=""}a(j).children("tr.ui-jqgrid-labels").remove();a(j).prepend(d);
!0===b&&a(this).jqGrid("setGridParam",{groupHeader:null})}})},setGroupHeaders:function(b){b=a.extend({useColSpanStyle:!1,groupHeaders:[]},b||{});return this.each(function(){this.p.groupHeader=b;var d,c,e=0,h,g,i,j,q,f=this.p.colModel,n=f.length,m=this.grid.headers,o=a("table.ui-jqgrid-htable",this.grid.hDiv),k=o.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header");h=o.children("thead");var l=o.find(".jqg-first-row-header");null===l.html()?l=a("<tr>",{role:"row",
"aria-hidden":"true"}).addClass("jqg-first-row-header").css("height","auto"):l.empty();var p,r=function(a,b){for(var c=0,d=b.length;c<d;c++)if(b[c].startColumnName===a)return c;return-1};a(this).prepend(h);h=a("<tr>",{role:"rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header");for(d=0;d<n;d++)if(i=m[d].el,j=a(i),c=f[d],g={height:"0px",width:m[d].width+"px",display:c.hidden?"none":""},a("<th>",{role:"gridcell"}).css(g).addClass("ui-first-th-"+this.p.direction).appendTo(l),i.style.width="",
g=r(c.name,b.groupHeaders),0<=g){g=b.groupHeaders[g];e=g.numberOfColumns;q=g.titleText;for(g=c=0;g<e&&d+g<n;g++)f[d+g].hidden||c++;g=a("<th>").attr({role:"columnheader"}).addClass("ui-state-default ui-th-column-header ui-th-"+this.p.direction).css({height:"22px","border-top":"0px none"}).html(q);0<c&&g.attr("colspan",""+c);this.p.headertitles&&g.attr("title",g.text());0===c&&g.hide();j.before(g);h.append(i);e-=1}else 0===e?b.useColSpanStyle?j.attr("rowspan","2"):(a("<th>",{role:"columnheader"}).addClass("ui-state-default ui-th-column-header ui-th-"+
this.p.direction).css({display:c.hidden?"none":"","border-top":"0px none"}).insertBefore(j),h.append(i)):(h.append(i),e--);f=a(this).children("thead");f.prepend(l);h.insertAfter(k);o.append(f);b.useColSpanStyle&&(o.find("span.ui-jqgrid-resize").each(function(){var b=a(this).parent();b.is(":visible")&&(this.style.cssText="height: "+b.height()+"px !important; cursor: col-resize;")}),o.find("div.ui-jqgrid-sortable").each(function(){var b=a(this),c=b.parent();c.is(":visible")&&c.is(":has(span.ui-jqgrid-resize)")&&
b.css("top",(c.height()-b.outerHeight())/2+"px")}));p=f.find("tr.jqg-first-row-header");a(this).bind("jqGridResizeStop.setGroupHeaders",function(a,b,c){p.find("th").eq(c).width(b)})})},setFrozenColumns:function(){return this.each(function(){if(this.grid){var b=this,d=b.p.colModel,c=0,e=d.length,h=-1,g=!1;if(!(!0===b.p.subGrid||!0===b.p.treeGrid||!0===b.p.cellEdit||b.p.sortable||b.p.scroll||b.p.grouping)){b.p.rownumbers&&c++;for(b.p.multiselect&&c++;c<e;){if(!0===d[c].frozen)g=!0,h=c;else break;c++}if(0<=
h&&g){d=b.p.caption?a(b.grid.cDiv).outerHeight():0;c=a(".ui-jqgrid-htable","#gview_"+a.jgrid.jqID(b.p.id)).height();b.p.toppager&&(d+=a(b.grid.topDiv).outerHeight());!0===b.p.toolbar[0]&&"bottom"!=b.p.toolbar[1]&&(d+=a(b.grid.uDiv).outerHeight());b.grid.fhDiv=a('<div style="position:absolute;left:0px;top:'+d+"px;height:"+c+'px;" class="frozen-div ui-state-default ui-jqgrid-hdiv"></div>');b.grid.fbDiv=a('<div style="position:absolute;left:0px;top:'+(parseInt(d,10)+parseInt(c,10)+1)+'px;overflow-y:hidden" class="frozen-bdiv ui-jqgrid-bdiv"></div>');
a("#gview_"+a.jgrid.jqID(b.p.id)).append(b.grid.fhDiv);d=a(".ui-jqgrid-htable","#gview_"+a.jgrid.jqID(b.p.id)).clone(!0);if(b.p.groupHeader){a("tr.jqg-first-row-header, tr.jqg-third-row-header",d).each(function(){a("th:gt("+h+")",this).remove()});var i=-1,j=-1;a("tr.jqg-second-row-header th",d).each(function(){var b=parseInt(a(this).attr("colspan"),10);b&&(i+=b,j++);if(i===h)return!1});i!==h&&(j=h);a("tr.jqg-second-row-header",d).each(function(){a("th:gt("+j+")",this).remove()})}else a("tr",d).each(function(){a("th:gt("+
h+")",this).remove()});a(d).width(1);a(b.grid.fhDiv).append(d).mousemove(function(a){if(b.grid.resizing)return b.grid.dragMove(a),!1});a(b).bind("jqGridResizeStop.setFrozenColumns",function(c,d,e){c=a(".ui-jqgrid-htable",b.grid.fhDiv);a("th:eq("+e+")",c).width(d);c=a(".ui-jqgrid-btable",b.grid.fbDiv);a("tr:first td:eq("+e+")",c).width(d)});a(b).bind("jqGridOnSortCol.setFrozenColumns",function(c,d){var e=a("tr.ui-jqgrid-labels:last th:eq("+b.p.lastsort+")",b.grid.fhDiv),g=a("tr.ui-jqgrid-labels:last th:eq("+
d+")",b.grid.fhDiv);a("span.ui-grid-ico-sort",e).addClass("ui-state-disabled");a(e).attr("aria-selected","false");a("span.ui-icon-"+b.p.sortorder,g).removeClass("ui-state-disabled");a(g).attr("aria-selected","true");!b.p.viewsortcols[0]&&b.p.lastsort!=d&&(a("span.s-ico",e).hide(),a("span.s-ico",g).show())});a("#gview_"+a.jgrid.jqID(b.p.id)).append(b.grid.fbDiv);jQuery(b.grid.bDiv).scroll(function(){jQuery(b.grid.fbDiv).scrollTop(jQuery(this).scrollTop())});!0===b.p.hoverrows&&a("#"+a.jgrid.jqID(b.p.id)).unbind("mouseover").unbind("mouseout");
a(b).bind("jqGridAfterGridComplete.setFrozenColumns",function(){a("#"+a.jgrid.jqID(b.p.id)+"_frozen").remove();jQuery(b.grid.fbDiv).height(jQuery(b.grid.bDiv).height()-16);var c=a("#"+a.jgrid.jqID(b.p.id)).clone(!0);a("tr",c).each(function(){a("td:gt("+h+")",this).remove()});a(c).width(1).attr("id",b.p.id+"_frozen");a(b.grid.fbDiv).append(c);!0===b.p.hoverrows&&(a("tr.jqgrow",c).hover(function(){a(this).addClass("ui-state-hover");a("#"+a.jgrid.jqID(this.id),"#"+a.jgrid.jqID(b.p.id)).addClass("ui-state-hover")},
function(){a(this).removeClass("ui-state-hover");a("#"+a.jgrid.jqID(this.id),"#"+a.jgrid.jqID(b.p.id)).removeClass("ui-state-hover")}),a("tr.jqgrow","#"+a.jgrid.jqID(b.p.id)).hover(function(){a(this).addClass("ui-state-hover");a("#"+a.jgrid.jqID(this.id),"#"+a.jgrid.jqID(b.p.id)+"_frozen").addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover");a("#"+a.jgrid.jqID(this.id),"#"+a.jgrid.jqID(b.p.id)+"_frozen").removeClass("ui-state-hover")}));c=null});b.p.frozenColumns=!0}}}})},
destroyFrozenColumns:function(){return this.each(function(){if(this.grid&&!0===this.p.frozenColumns){a(this.grid.fhDiv).remove();a(this.grid.fbDiv).remove();this.grid.fhDiv=null;this.grid.fbDiv=null;a(this).unbind(".setFrozenColumns");if(!0===this.p.hoverrows){var b;a("#"+a.jgrid.jqID(this.p.id)).bind("mouseover",function(d){b=a(d.target).closest("tr.jqgrow");"ui-subgrid"!==a(b).attr("class")&&a(b).addClass("ui-state-hover")}).bind("mouseout",function(d){b=a(d.target).closest("tr.jqgrow");a(b).removeClass("ui-state-hover")})}this.p.frozenColumns=
!1}})}})})(jQuery);
(function(a){a.extend(a.jgrid,{showModal:function(a){a.w.show()},closeModal:function(a){a.w.hide().attr("aria-hidden","true");a.o&&a.o.remove()},hideModal:function(d,b){b=a.extend({jqm:!0,gb:""},b||{});if(b.onClose){var c=b.onClose(d);if("boolean"==typeof c&&!c)return}if(a.fn.jqm&&!0===b.jqm)a(d).attr("aria-hidden","true").jqmHide();else{if(""!==b.gb)try{a(".jqgrid-overlay:first",b.gb).hide()}catch(f){}a(d).hide().attr("aria-hidden","true")}},findPos:function(a){var b=0,c=0;if(a.offsetParent){do b+=
a.offsetLeft,c+=a.offsetTop;while(a=a.offsetParent)}return[b,c]},createModal:function(d,b,c,f,g,h,i){var e=document.createElement("div"),l,j=this,i=a.extend({},i||{});l="rtl"==a(c.gbox).attr("dir")?!0:!1;e.className="ui-widget ui-widget-content ui-corner-all ui-jqdialog";e.id=d.themodal;var k=document.createElement("div");k.className="ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";k.id=d.modalhead;a(k).append("<span class='ui-jqdialog-title'>"+c.caption+"</span>");var n=a("<a href='javascript:void(0)' class='ui-jqdialog-titlebar-close ui-corner-all'></a>").hover(function(){n.addClass("ui-state-hover")},
function(){n.removeClass("ui-state-hover")}).append("<span class='ui-icon ui-icon-closethick'></span>");a(k).append(n);l?(e.dir="rtl",a(".ui-jqdialog-title",k).css("float","right"),a(".ui-jqdialog-titlebar-close",k).css("left","0.3em")):(e.dir="ltr",a(".ui-jqdialog-title",k).css("float","left"),a(".ui-jqdialog-titlebar-close",k).css("right","0.3em"));var m=document.createElement("div");a(m).addClass("ui-jqdialog-content ui-widget-content").attr("id",d.modalcontent);a(m).append(b);e.appendChild(m);
a(e).prepend(k);!0===h?a("body").append(e):"string"==typeof h?a(h).append(e):a(e).insertBefore(f);a(e).css(i);"undefined"===typeof c.jqModal&&(c.jqModal=!0);b={};if(a.fn.jqm&&!0===c.jqModal)0===c.left&&0===c.top&&c.overlay&&(i=[],i=a.jgrid.findPos(g),c.left=i[0]+4,c.top=i[1]+4),b.top=c.top+"px",b.left=c.left;else if(0!==c.left||0!==c.top)b.left=c.left,b.top=c.top+"px";a("a.ui-jqdialog-titlebar-close",k).click(function(){var b=a("#"+a.jgrid.jqID(d.themodal)).data("onClose")||c.onClose,e=a("#"+a.jgrid.jqID(d.themodal)).data("gbox")||
c.gbox;j.hideModal("#"+a.jgrid.jqID(d.themodal),{gb:e,jqm:c.jqModal,onClose:b});return false});if(0===c.width||!c.width)c.width=300;if(0===c.height||!c.height)c.height=200;c.zIndex||(f=a(f).parents("*[role=dialog]").filter(":first").css("z-index"),c.zIndex=f?parseInt(f,10)+2:950);f=0;l&&b.left&&!h&&(f=a(c.gbox).width()-(!isNaN(c.width)?parseInt(c.width,10):0)-8,b.left=parseInt(b.left,10)+parseInt(f,10));b.left&&(b.left+="px");a(e).css(a.extend({width:isNaN(c.width)?"auto":c.width+"px",height:isNaN(c.height)?
"auto":c.height+"px",zIndex:c.zIndex,overflow:"hidden"},b)).attr({tabIndex:"-1",role:"dialog","aria-labelledby":d.modalhead,"aria-hidden":"true"});"undefined"==typeof c.drag&&(c.drag=!0);"undefined"==typeof c.resize&&(c.resize=!0);if(c.drag)if(a(k).css("cursor","move"),a.fn.jqDrag)a(e).jqDrag(k);else try{a(e).draggable({handle:a("#"+a.jgrid.jqID(k.id))})}catch(o){}if(c.resize)if(a.fn.jqResize)a(e).append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se'></div>"),
a("#"+a.jgrid.jqID(d.themodal)).jqResize(".jqResize",d.scrollelm?"#"+a.jgrid.jqID(d.scrollelm):!1);else try{a(e).resizable({handles:"se, sw",alsoResize:d.scrollelm?"#"+a.jgrid.jqID(d.scrollelm):!1})}catch(p){}!0===c.closeOnEscape&&a(e).keydown(function(b){if(b.which==27){b=a("#"+a.jgrid.jqID(d.themodal)).data("onClose")||c.onClose;j.hideModal(this,{gb:c.gbox,jqm:c.jqModal,onClose:b})}})},viewModal:function(d,b){b=a.extend({toTop:!0,overlay:10,modal:!1,overlayClass:"ui-widget-overlay",onShow:a.jgrid.showModal,
onHide:a.jgrid.closeModal,gbox:"",jqm:!0,jqM:!0},b||{});if(a.fn.jqm&&!0===b.jqm)b.jqM?a(d).attr("aria-hidden","false").jqm(b).jqmShow():a(d).attr("aria-hidden","false").jqmShow();else{""!==b.gbox&&(a(".jqgrid-overlay:first",b.gbox).show(),a(d).data("gbox",b.gbox));a(d).show().attr("aria-hidden","false");try{a(":input:visible",d)[0].focus()}catch(c){}}},info_dialog:function(d,b,c,f){var g={width:290,height:"auto",dataheight:"auto",drag:!0,resize:!1,caption:"<b>"+d+"</b>",left:250,top:170,zIndex:1E3,
jqModal:!0,modal:!1,closeOnEscape:!0,align:"center",buttonalign:"center",buttons:[]};a.extend(g,f||{});var h=g.jqModal,i=this;a.fn.jqm&&!h&&(h=!1);d="";if(0<g.buttons.length)for(f=0;f<g.buttons.length;f++)"undefined"==typeof g.buttons[f].id&&(g.buttons[f].id="info_button_"+f),d+="<a href='javascript:void(0)' id='"+g.buttons[f].id+"' class='fm-button ui-state-default ui-corner-all'>"+g.buttons[f].text+"</a>";f=isNaN(g.dataheight)?g.dataheight:g.dataheight+"px";b="<div id='info_id'>"+("<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:"+
f+";"+("text-align:"+g.align+";")+"'>"+b+"</div>");b+=c?"<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+g.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a href='javascript:void(0)' id='closedialog' class='fm-button ui-state-default ui-corner-all'>"+c+"</a>"+d+"</div>":""!==d?"<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+g.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>"+
d+"</div>":"";b+="</div>";try{"false"==a("#info_dialog").attr("aria-hidden")&&a.jgrid.hideModal("#info_dialog",{jqm:h}),a("#info_dialog").remove()}catch(e){}a.jgrid.createModal({themodal:"info_dialog",modalhead:"info_head",modalcontent:"info_content",scrollelm:"infocnt"},b,g,"","",!0);d&&a.each(g.buttons,function(b){a("#"+a.jgrid.jqID(this.id),"#info_id").bind("click",function(){g.buttons[b].onClick.call(a("#info_dialog"));return!1})});a("#closedialog","#info_id").click(function(){i.hideModal("#info_dialog",
{jqm:h});return!1});a(".fm-button","#info_dialog").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});a.isFunction(g.beforeOpen)&&g.beforeOpen();a.jgrid.viewModal("#info_dialog",{onHide:function(a){a.w.hide().remove();a.o&&a.o.remove()},modal:g.modal,jqm:h});a.isFunction(g.afterOpen)&&g.afterOpen();try{a("#info_dialog").focus()}catch(l){}},createEl:function(d,b,c,f,g){function h(b,d){a.isFunction(d.dataInit)&&d.dataInit.call(l,b);d.dataEvents&&
a.each(d.dataEvents,function(){void 0!==this.data?a(b).bind(this.type,this.data,this.fn):a(b).bind(this.type,this.fn)});return d}function i(b,d,c){var e="dataInit,dataEvents,dataUrl,buildSelect,sopt,searchhidden,defaultValue,attr".split(",");"undefined"!=typeof c&&a.isArray(c)&&a.merge(e,c);a.each(d,function(d,c){-1===a.inArray(d,e)&&a(b).attr(d,c)});d.hasOwnProperty("id")||a(b).attr("id",a.jgrid.randId())}var e="",l=this;switch(d){case "textarea":e=document.createElement("textarea");f?b.cols||a(e).css({width:"98%"}):
b.cols||(b.cols=20);b.rows||(b.rows=2);if("&nbsp;"==c||"&#160;"==c||1==c.length&&160==c.charCodeAt(0))c="";e.value=c;i(e,b);b=h(e,b);a(e).attr({role:"textbox",multiline:"true"});break;case "checkbox":e=document.createElement("input");e.type="checkbox";b.value?(d=b.value.split(":"),c===d[0]&&(e.checked=!0,e.defaultChecked=!0),e.value=d[0],a(e).attr("offval",d[1])):(d=c.toLowerCase(),0>d.search(/(false|0|no|off|undefined)/i)&&""!==d?(e.checked=!0,e.defaultChecked=!0,e.value=c):e.value="on",a(e).attr("offval",
"off"));i(e,b,["value"]);b=h(e,b);a(e).attr("role","checkbox");break;case "select":e=document.createElement("select");e.setAttribute("role","select");f=[];!0===b.multiple?(d=!0,e.multiple="multiple",a(e).attr("aria-multiselectable","true")):d=!1;if("undefined"!=typeof b.dataUrl)a.ajax(a.extend({url:b.dataUrl,type:"GET",dataType:"html",context:{elem:e,options:b,vl:c},success:function(d){var b=[],c=this.elem,e=this.vl,f=a.extend({},this.options),g=f.multiple===true;a.isFunction(f.buildSelect)&&(d=f.buildSelect.call(l,
d));if(d=a(d).html()){a(c).append(d);i(c,f);f=h(c,f);if(typeof f.size==="undefined")f.size=g?3:1;if(g){b=e.split(",");b=a.map(b,function(b){return a.trim(b)})}else b[0]=a.trim(e);setTimeout(function(){a("option",c).each(function(d){if(d===0&&c.multiple)this.selected=false;a(this).attr("role","option");if(a.inArray(a.trim(a(this).text()),b)>-1||a.inArray(a.trim(a(this).val()),b)>-1)this.selected="selected"})},0)}}},g||{}));else if(b.value){var j;"undefined"===typeof b.size&&(b.size=d?3:1);d&&(f=c.split(","),
f=a.map(f,function(b){return a.trim(b)}));"function"===typeof b.value&&(b.value=b.value());var k,n,m=void 0===b.separator?":":b.separator,g=void 0===b.delimiter?";":b.delimiter;if("string"===typeof b.value){k=b.value.split(g);for(j=0;j<k.length;j++){n=k[j].split(m);2<n.length&&(n[1]=a.map(n,function(a,b){if(b>0)return a}).join(m));g=document.createElement("option");g.setAttribute("role","option");g.value=n[0];g.innerHTML=n[1];e.appendChild(g);if(!d&&(a.trim(n[0])==a.trim(c)||a.trim(n[1])==a.trim(c)))g.selected=
"selected";if(d&&(-1<a.inArray(a.trim(n[1]),f)||-1<a.inArray(a.trim(n[0]),f)))g.selected="selected"}}else if("object"===typeof b.value)for(j in m=b.value,m)if(m.hasOwnProperty(j)){g=document.createElement("option");g.setAttribute("role","option");g.value=j;g.innerHTML=m[j];e.appendChild(g);if(!d&&(a.trim(j)==a.trim(c)||a.trim(m[j])==a.trim(c)))g.selected="selected";if(d&&(-1<a.inArray(a.trim(m[j]),f)||-1<a.inArray(a.trim(j),f)))g.selected="selected"}i(e,b,["value"]);b=h(e,b)}break;case "text":case "password":case "button":j=
"button"==d?"button":"textbox";e=document.createElement("input");e.type=d;e.value=c;i(e,b);b=h(e,b);"button"!=d&&(f?b.size||a(e).css({width:"98%"}):b.size||(b.size=20));a(e).attr("role",j);break;case "image":case "file":e=document.createElement("input");e.type=d;i(e,b);b=h(e,b);break;case "custom":e=document.createElement("span");try{if(a.isFunction(b.custom_element))if(m=b.custom_element.call(l,c,b))m=a(m).addClass("customelement").attr({id:b.id,name:b.name}),a(e).empty().append(m);else throw"e2";
else throw"e1";}catch(o){"e1"==o&&a.jgrid.info_dialog(a.jgrid.errors.errcap,"function 'custom_element' "+a.jgrid.edit.msg.nodefined,a.jgrid.edit.bClose),"e2"==o?a.jgrid.info_dialog(a.jgrid.errors.errcap,"function 'custom_element' "+a.jgrid.edit.msg.novalue,a.jgrid.edit.bClose):a.jgrid.info_dialog(a.jgrid.errors.errcap,"string"===typeof o?o:o.message,a.jgrid.edit.bClose)}}return e},checkDate:function(a,b){var c={},f,a=a.toLowerCase();f=-1!=a.indexOf("/")?"/":-1!=a.indexOf("-")?"-":-1!=a.indexOf(".")?
".":"/";a=a.split(f);b=b.split(f);if(3!=b.length)return!1;f=-1;for(var g,h=-1,i=-1,e=0;e<a.length;e++)g=isNaN(b[e])?0:parseInt(b[e],10),c[a[e]]=g,g=a[e],-1!=g.indexOf("y")&&(f=e),-1!=g.indexOf("m")&&(i=e),-1!=g.indexOf("d")&&(h=e);g="y"==a[f]||"yyyy"==a[f]?4:"yy"==a[f]?2:-1;var e=function(a){for(var b=1;b<=a;b++){this[b]=31;if(4==b||6==b||9==b||11==b)this[b]=30;2==b&&(this[b]=29)}return this}(12),l;if(-1===f)return!1;l=c[a[f]].toString();2==g&&1==l.length&&(g=1);if(l.length!=g||0===c[a[f]]&&"00"!=
b[f]||-1===i)return!1;l=c[a[i]].toString();if(1>l.length||1>c[a[i]]||12<c[a[i]]||-1===h)return!1;l=c[a[h]].toString();return 1>l.length||1>c[a[h]]||31<c[a[h]]||2==c[a[i]]&&c[a[h]]>(0===c[a[f]]%4&&(0!==c[a[f]]%100||0===c[a[f]]%400)?29:28)||c[a[h]]>e[c[a[i]]]?!1:!0},isEmpty:function(a){return a.match(/^\s+$/)||""===a?!0:!1},checkTime:function(d){var b=/^(\d{1,2}):(\d{2})([ap]m)?$/;if(!a.jgrid.isEmpty(d))if(d=d.match(b)){if(d[3]){if(1>d[1]||12<d[1])return!1}else if(23<d[1])return!1;if(59<d[2])return!1}else return!1;
return!0},checkValues:function(d,b,c,f,g){var h,i;if("undefined"===typeof f)if("string"==typeof b){f=0;for(g=c.p.colModel.length;f<g;f++)if(c.p.colModel[f].name==b){h=c.p.colModel[f].editrules;b=f;try{i=c.p.colModel[f].formoptions.label}catch(e){}break}}else 0<=b&&(h=c.p.colModel[b].editrules);else h=f,i=void 0===g?"_":g;if(h){i||(i=c.p.colNames[b]);if(!0===h.required&&a.jgrid.isEmpty(d))return[!1,i+": "+a.jgrid.edit.msg.required,""];f=!1===h.required?!1:!0;if(!0===h.number&&!(!1===f&&a.jgrid.isEmpty(d))&&
isNaN(d))return[!1,i+": "+a.jgrid.edit.msg.number,""];if("undefined"!=typeof h.minValue&&!isNaN(h.minValue)&&parseFloat(d)<parseFloat(h.minValue))return[!1,i+": "+a.jgrid.edit.msg.minValue+" "+h.minValue,""];if("undefined"!=typeof h.maxValue&&!isNaN(h.maxValue)&&parseFloat(d)>parseFloat(h.maxValue))return[!1,i+": "+a.jgrid.edit.msg.maxValue+" "+h.maxValue,""];if(!0===h.email&&!(!1===f&&a.jgrid.isEmpty(d))&&(g=/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
!g.test(d)))return[!1,i+": "+a.jgrid.edit.msg.email,""];if(!0===h.integer&&!(!1===f&&a.jgrid.isEmpty(d))&&(isNaN(d)||0!==d%1||-1!=d.indexOf(".")))return[!1,i+": "+a.jgrid.edit.msg.integer,""];if(!0===h.date&&!(!1===f&&a.jgrid.isEmpty(d))&&(b=c.p.colModel[b].formatoptions&&c.p.colModel[b].formatoptions.newformat?c.p.colModel[b].formatoptions.newformat:c.p.colModel[b].datefmt||"Y-m-d",!$jgrid.checkDate(b,d)))return[!1,i+": "+a.jgrid.edit.msg.date+" - "+b,""];if(!0===h.time&&!(!1===f&&a.jgrid.isEmpty(d))&&
!a.jgrid.checkTime(d))return[!1,i+": "+a.jgrid.edit.msg.date+" - hh:mm (am/pm)",""];if(!0===h.url&&!(!1===f&&a.jgrid.isEmpty(d))&&(g=/^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i,!g.test(d)))return[!1,i+": "+a.jgrid.edit.msg.url,""];if(!0===h.custom&&!(!1===f&&a.jgrid.isEmpty(d)))return a.isFunction(h.custom_func)?(d=h.custom_func.call(c,d,i),a.isArray(d)?d:[!1,a.jgrid.edit.msg.customarray,""]):[!1,a.jgrid.edit.msg.customfcheck,
""]}return[!0,"",""]}})})(jQuery);
(function(a){var c={};a.jgrid.extend({searchGrid:function(c){c=a.extend({recreateFilter:!1,drag:!0,sField:"searchField",sValue:"searchString",sOper:"searchOper",sFilter:"filters",loadDefaults:!0,beforeShowSearch:null,afterShowSearch:null,onInitializeSearch:null,afterRedraw:null,afterChange:null,closeAfterSearch:!1,closeAfterReset:!1,closeOnEscape:!1,searchOnEnter:!1,multipleSearch:!1,multipleGroup:!1,top:0,left:0,jqModal:!0,modal:!1,resize:!0,width:450,height:"auto",dataheight:"auto",showQuery:!1,
errorcheck:!0,sopt:null,stringResult:void 0,onClose:null,onSearch:null,onReset:null,toTop:!0,overlay:30,columns:[],tmplNames:null,tmplFilters:null,tmplLabel:" Template: ",showOnLoad:!1,layer:null},a.jgrid.search,c||{});return this.each(function(){function d(b){r=a(e).triggerHandler("jqGridFilterBeforeShow",[b]);"undefined"===typeof r&&(r=!0);r&&a.isFunction(c.beforeShowSearch)&&(r=c.beforeShowSearch.call(e,b));r&&(a.jgrid.viewModal("#"+a.jgrid.jqID(t.themodal),{gbox:"#gbox_"+a.jgrid.jqID(l),jqm:c.jqModal,
modal:c.modal,overlay:c.overlay,toTop:c.toTop}),a(e).triggerHandler("jqGridFilterAfterShow",[b]),a.isFunction(c.afterShowSearch)&&c.afterShowSearch.call(e,b))}var e=this;if(e.grid){var l="fbox_"+e.p.id,r=!0,t={themodal:"searchmod"+l,modalhead:"searchhd"+l,modalcontent:"searchcnt"+l,scrollelm:l},s=e.p.postData[c.sFilter];"string"===typeof s&&(s=a.jgrid.parse(s));!0===c.recreateFilter&&a("#"+a.jgrid.jqID(t.themodal)).remove();if(null!==a("#"+a.jgrid.jqID(t.themodal)).html())d(a("#fbox_"+a.jgrid.jqID(+e.p.id)));
else{var p=a("<div><div id='"+l+"' class='searchFilter' style='overflow:auto'></div></div>").insertBefore("#gview_"+a.jgrid.jqID(e.p.id)),g="left",f="";"rtl"==e.p.direction&&(g="right",f=" style='text-align:c'",p.attr("dir","rtl"));var n=a.extend([],e.p.colModel),w="<a href='javascript:void(0)' id='"+l+"_search' class='fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset'><span class='ui-icon ui-icon-search'></span>"+c.Find+"</a>",b="<a href='javascript:void(0)' id='"+l+"_reset' class='fm-button ui-state-default ui-corner-all fm-button-icon-left ui-search'><span class='ui-icon ui-icon-arrowreturnthick-1-w'></span>"+
c.Reset+"</a>",m="",h="",k,j=!1,o=-1;c.showQuery&&(m="<a href='javascript:void(0)' id='"+l+"_query' class='fm-button ui-state-default ui-corner-all fm-button-icon-left'><span class='ui-icon ui-icon-comment'></span>Query</a>");c.columns.length?n=c.columns:a.each(n,function(a,b){if(!b.label)b.label=e.p.colNames[a];if(!j){var c=typeof b.search==="undefined"?true:b.search,d=b.hidden===true;if(b.searchoptions&&b.searchoptions.searchhidden===true&&c||c&&!d){j=true;k=b.index||b.name;o=a}}});if(!s&&k||!1===
c.multipleSearch){var y="eq";0<=o&&n[o].searchoptions&&n[o].searchoptions.sopt?y=n[o].searchoptions.sopt[0]:c.sopt&&c.sopt.length&&(y=c.sopt[0]);s={groupOp:"AND",rules:[{field:k,op:y,data:""}]}}j=!1;c.tmplNames&&c.tmplNames.length&&(j=!0,h=c.tmplLabel,h+="<select class='ui-template'>",h+="<option value='default'>Default</option>",a.each(c.tmplNames,function(a,b){h=h+("<option value='"+a+"'>"+b+"</option>")}),h+="</select>");g="<table class='EditTable' style='border:0px none;margin-top:5px' id='"+
l+"_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='EditButton' style='text-align:"+g+"'>"+b+h+"</td><td class='EditButton' "+f+">"+m+w+"</td></tr></tbody></table>";l=a.jgrid.jqID(l);a("#"+l).jqFilter({columns:n,filter:c.loadDefaults?s:null,showQuery:c.showQuery,errorcheck:c.errorcheck,sopt:c.sopt,groupButton:c.multipleGroup,ruleButtons:c.multipleSearch,afterRedraw:c.afterRedraw,_gridsopt:a.jgrid.search.odata,ajaxSelectOptions:e.p.ajaxSelectOptions,
groupOps:c.groupOps,onChange:function(){this.p.showQuery&&a(".query",this).html(this.toUserFriendlyString());a.isFunction(c.afterChange)&&c.afterChange.call(e,a("#"+l),c)},direction:e.p.direction});p.append(g);j&&c.tmplFilters&&c.tmplFilters.length&&a(".ui-template",p).bind("change",function(){var b=a(this).val();b=="default"?a("#"+l).jqFilter("addFilter",s):a("#"+l).jqFilter("addFilter",c.tmplFilters[parseInt(b,10)]);return false});!0===c.multipleGroup&&(c.multipleSearch=!0);a(e).triggerHandler("jqGridFilterInitialize",
[a("#"+l)]);a.isFunction(c.onInitializeSearch)&&c.onInitializeSearch.call(e,a("#"+l));c.gbox="#gbox_"+l;c.layer?a.jgrid.createModal(t,p,c,"#gview_"+a.jgrid.jqID(e.p.id),a("#gbox_"+a.jgrid.jqID(e.p.id))[0],"#"+a.jgrid.jqID(c.layer),{position:"relative"}):a.jgrid.createModal(t,p,c,"#gview_"+a.jgrid.jqID(e.p.id),a("#gbox_"+a.jgrid.jqID(e.p.id))[0]);(c.searchOnEnter||c.closeOnEscape)&&a("#"+a.jgrid.jqID(t.themodal)).keydown(function(b){var d=a(b.target);if(c.searchOnEnter&&b.which===13&&!d.hasClass("add-group")&&
!d.hasClass("add-rule")&&!d.hasClass("delete-group")&&!d.hasClass("delete-rule")&&(!d.hasClass("fm-button")||!d.is("[id$=_query]"))){a("#"+l+"_search").focus().click();return false}if(c.closeOnEscape&&b.which===27){a("#"+a.jgrid.jqID(t.modalhead)).find(".ui-jqdialog-titlebar-close").focus().click();return false}});m&&a("#"+l+"_query").bind("click",function(){a(".queryresult",p).toggle();return false});void 0===c.stringResult&&(c.stringResult=c.multipleSearch);a("#"+l+"_search").bind("click",function(){var b=
a("#"+l),d={},h,q=b.jqFilter("filterData");if(c.errorcheck){b[0].hideError();c.showQuery||b.jqFilter("toSQLString");if(b[0].p.error){b[0].showError();return false}}if(c.stringResult){try{h=xmlJsonClass.toJson(q,"","",false)}catch(f){try{h=JSON.stringify(q)}catch(g){}}if(typeof h==="string"){d[c.sFilter]=h;a.each([c.sField,c.sValue,c.sOper],function(){d[this]=""})}}else if(c.multipleSearch){d[c.sFilter]=q;a.each([c.sField,c.sValue,c.sOper],function(){d[this]=""})}else{d[c.sField]=q.rules[0].field;
d[c.sValue]=q.rules[0].data;d[c.sOper]=q.rules[0].op;d[c.sFilter]=""}e.p.search=true;a.extend(e.p.postData,d);a(e).triggerHandler("jqGridFilterSearch");a.isFunction(c.onSearch)&&c.onSearch.call(e);a(e).trigger("reloadGrid",[{page:1}]);c.closeAfterSearch&&a.jgrid.hideModal("#"+a.jgrid.jqID(t.themodal),{gb:"#gbox_"+a.jgrid.jqID(e.p.id),jqm:c.jqModal,onClose:c.onClose});return false});a("#"+l+"_reset").bind("click",function(){var b={},d=a("#"+l);e.p.search=false;c.multipleSearch===false?b[c.sField]=
b[c.sValue]=b[c.sOper]="":b[c.sFilter]="";d[0].resetFilter();j&&a(".ui-template",p).val("default");a.extend(e.p.postData,b);a(e).triggerHandler("jqGridFilterReset");a.isFunction(c.onReset)&&c.onReset.call(e);a(e).trigger("reloadGrid",[{page:1}]);return false});d(a("#"+l));a(".fm-button:not(.ui-state-disabled)",p).hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")})}}})},editGridRow:function(u,d){d=a.extend({top:0,left:0,width:300,height:"auto",dataheight:"auto",
modal:!1,overlay:30,drag:!0,resize:!0,url:null,mtype:"POST",clearAfterAdd:!0,closeAfterEdit:!1,reloadAfterSubmit:!0,onInitializeForm:null,beforeInitData:null,beforeShowForm:null,afterShowForm:null,beforeSubmit:null,afterSubmit:null,onclickSubmit:null,afterComplete:null,onclickPgButtons:null,afterclickPgButtons:null,editData:{},recreateForm:!1,jqModal:!0,closeOnEscape:!1,addedrow:"first",topinfo:"",bottominfo:"",saveicon:[],closeicon:[],savekey:[!1,13],navkeys:[!1,38,40],checkOnSubmit:!1,checkOnUpdate:!1,
_savedData:{},processing:!1,onClose:null,ajaxEditOptions:{},serializeEditData:null,viewPagerButtons:!0},a.jgrid.edit,d||{});c[a(this)[0].p.id]=d;return this.each(function(){function e(){a(j+" > tbody > tr > td > .FormElement").each(function(){var d=a(".customelement",this);if(d.length){var c=a(d[0]).attr("name");a.each(b.p.colModel,function(){if(this.name===c&&this.editoptions&&a.isFunction(this.editoptions.custom_value)){try{if(i[c]=this.editoptions.custom_value.call(b,a("#"+a.jgrid.jqID(c),j),"get"),
void 0===i[c])throw"e1";}catch(d){"e1"===d?a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,d.message,jQuery.jgrid.edit.bClose)}return!0}})}else{switch(a(this).get(0).type){case "checkbox":a(this).is(":checked")?i[this.name]=a(this).val():(d=a(this).attr("offval"),i[this.name]=d);break;case "select-one":i[this.name]=a("option:selected",this).val();B[this.name]=a("option:selected",
this).text();break;case "select-multiple":i[this.name]=a(this).val();i[this.name]=i[this.name]?i[this.name].join(","):"";var e=[];a("option:selected",this).each(function(b,d){e[b]=a(d).text()});B[this.name]=e.join(",");break;case "password":case "text":case "textarea":case "button":i[this.name]=a(this).val()}b.p.autoencode&&(i[this.name]=a.jgrid.htmlEncode(i[this.name]))}});return!0}function l(d,e,h,q){var i,f,g,k=0,j,o,l,p=[],n=!1,u="",m;for(m=1;m<=q;m++)u+="<td class='CaptionTD'>&#160;</td><td class='DataTD'>&#160;</td>";
"_empty"!=d&&(n=a(e).jqGrid("getInd",d));a(e.p.colModel).each(function(m){i=this.name;o=(f=this.editrules&&!0===this.editrules.edithidden?!1:!0===this.hidden?!0:!1)?"style='display:none'":"";if("cb"!==i&&"subgrid"!==i&&!0===this.editable&&"rn"!==i){if(!1===n)j="";else if(i==e.p.ExpandColumn&&!0===e.p.treeGrid)j=a("td:eq("+m+")",e.rows[n]).text();else{try{j=a.unformat.call(e,a("td:eq("+m+")",e.rows[n]),{rowId:d,colModel:this},m)}catch(r){j=this.edittype&&"textarea"==this.edittype?a("td:eq("+m+")",
e.rows[n]).text():a("td:eq("+m+")",e.rows[n]).html()}if(!j||"&nbsp;"==j||"&#160;"==j||1==j.length&&160==j.charCodeAt(0))j=""}var v=a.extend({},this.editoptions||{},{id:i,name:i}),s=a.extend({},{elmprefix:"",elmsuffix:"",rowabove:!1,rowcontent:""},this.formoptions||{}),t=parseInt(s.rowpos,10)||k+1,y=parseInt(2*(parseInt(s.colpos,10)||1),10);"_empty"==d&&v.defaultValue&&(j=a.isFunction(v.defaultValue)?v.defaultValue.call(b):v.defaultValue);this.edittype||(this.edittype="text");b.p.autoencode&&(j=a.jgrid.htmlDecode(j));
l=a.jgrid.createEl.call(b,this.edittype,v,j,!1,a.extend({},a.jgrid.ajaxOptions,e.p.ajaxSelectOptions||{}));""===j&&"checkbox"==this.edittype&&(j=a(l).attr("offval"));""===j&&"select"==this.edittype&&(j=a("option:eq(0)",l).text());if(c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate)c[b.p.id]._savedData[i]=j;a(l).addClass("FormElement");("text"==this.edittype||"textarea"==this.edittype)&&a(l).addClass("ui-widget-content ui-corner-all");g=a(h).find("tr[rowpos="+t+"]");s.rowabove&&(v=a("<tr><td class='contentinfo' colspan='"+
2*q+"'>"+s.rowcontent+"</td></tr>"),a(h).append(v),v[0].rp=t);0===g.length&&(g=a("<tr "+o+" rowpos='"+t+"'></tr>").addClass("FormData").attr("id","tr_"+i),a(g).append(u),a(h).append(g),g[0].rp=t);a("td:eq("+(y-2)+")",g[0]).html("undefined"===typeof s.label?e.p.colNames[m]:s.label);a("td:eq("+(y-1)+")",g[0]).append(s.elmprefix).append(l).append(s.elmsuffix);p[k]=m;k++}});if(0<k&&(m=a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+(2*q-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='"+
e.p.id+"_id' value='"+d+"'/></td></tr>"),m[0].rp=k+999,a(h).append(m),c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate))c[b.p.id]._savedData[e.p.id+"_id"]=d;return p}function r(d,e,h){var i,q=0,g,f,k,o,l;if(c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate)c[b.p.id]._savedData={},c[b.p.id]._savedData[e.p.id+"_id"]=d;var m=e.p.colModel;if("_empty"==d)a(m).each(function(){i=this.name;k=a.extend({},this.editoptions||{});if((f=a("#"+a.jgrid.jqID(i),"#"+h))&&f.length&&null!==f[0])if(o="",k.defaultValue?
(o=a.isFunction(k.defaultValue)?k.defaultValue.call(b):k.defaultValue,"checkbox"==f[0].type?(l=o.toLowerCase(),0>l.search(/(false|0|no|off|undefined)/i)&&""!==l?(f[0].checked=!0,f[0].defaultChecked=!0,f[0].value=o):(f[0].checked=!1,f[0].defaultChecked=!1)):f.val(o)):"checkbox"==f[0].type?(f[0].checked=!1,f[0].defaultChecked=!1,o=a(f).attr("offval")):f[0].type&&"select"==f[0].type.substr(0,6)?f[0].selectedIndex=0:f.val(o),!0===c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate)c[b.p.id]._savedData[i]=
o}),a("#id_g","#"+h).val(d);else{var n=a(e).jqGrid("getInd",d,!0);n&&(a('td[role="gridcell"]',n).each(function(f){i=m[f].name;if("cb"!==i&&"subgrid"!==i&&"rn"!==i&&!0===m[f].editable){if(i==e.p.ExpandColumn&&!0===e.p.treeGrid)g=a(this).text();else try{g=a.unformat.call(e,a(this),{rowId:d,colModel:m[f]},f)}catch(j){g="textarea"==m[f].edittype?a(this).text():a(this).html()}b.p.autoencode&&(g=a.jgrid.htmlDecode(g));if(!0===c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate)c[b.p.id]._savedData[i]=g;i=
a.jgrid.jqID(i);switch(m[f].edittype){case "password":case "text":case "button":case "image":case "textarea":if("&nbsp;"==g||"&#160;"==g||1==g.length&&160==g.charCodeAt(0))g="";a("#"+i,"#"+h).val(g);break;case "select":var k=g.split(","),k=a.map(k,function(b){return a.trim(b)});a("#"+i+" option","#"+h).each(function(){this.selected=!m[f].editoptions.multiple&&(a.trim(g)==a.trim(a(this).text())||k[0]==a.trim(a(this).text())||k[0]==a.trim(a(this).val()))?!0:m[f].editoptions.multiple?-1<a.inArray(a.trim(a(this).text()),
k)||-1<a.inArray(a.trim(a(this).val()),k)?!0:!1:!1});break;case "checkbox":g+="";m[f].editoptions&&m[f].editoptions.value?m[f].editoptions.value.split(":")[0]==g?(a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("checked",!0),a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("defaultChecked",!0)):(a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("checked",!1),a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("defaultChecked",!1)):(g=g.toLowerCase(),0>g.search(/(false|0|no|off|undefined)/i)&&""!==g?(a("#"+i,"#"+h)[b.p.useProp?
"prop":"attr"]("checked",!0),a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("defaultChecked",!0)):(a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("checked",!1),a("#"+i,"#"+h)[b.p.useProp?"prop":"attr"]("defaultChecked",!1)));break;case "custom":try{if(m[f].editoptions&&a.isFunction(m[f].editoptions.custom_value))m[f].editoptions.custom_value.call(b,a("#"+i,"#"+h),"set",g);else throw"e1";}catch(o){"e1"==o?a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose):
a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,o.message,jQuery.jgrid.edit.bClose)}}q++}}),0<q&&a("#id_g",j).val(d))}}function t(){a.each(b.p.colModel,function(a,b){b.editoptions&&!0===b.editoptions.NullIfEmpty&&i.hasOwnProperty(b.name)&&""===i[b.name]&&(i[b.name]="null")})}function s(){var e,f=[!0,"",""],g={},q=b.p.prmNames,k,l,n,p,v,u=a(b).triggerHandler("jqGridAddEditBeforeCheckValues",[a("#"+h),z]);u&&"object"===typeof u&&(i=u);a.isFunction(c[b.p.id].beforeCheckValues)&&(u=c[b.p.id].beforeCheckValues.call(b,
i,a("#"+h),"_empty"==i[b.p.id+"_id"]?q.addoper:q.editoper))&&"object"===typeof u&&(i=u);for(n in i)if(i.hasOwnProperty(n)&&(f=a.jgrid.checkValues.call(b,i[n],n,b),!1===f[0]))break;t();f[0]&&(g=a(b).triggerHandler("jqGridAddEditClickSubmit",[c[b.p.id],i,z]),void 0===g&&a.isFunction(c[b.p.id].onclickSubmit)&&(g=c[b.p.id].onclickSubmit.call(b,c[b.p.id],i)||{}),f=a(b).triggerHandler("jqGridAddEditBeforeSubmit",[i,a("#"+h),z]),void 0===f&&(f=[!0,"",""]),f[0]&&a.isFunction(c[b.p.id].beforeSubmit)&&(f=c[b.p.id].beforeSubmit.call(b,
i,a("#"+h))));if(f[0]&&!c[b.p.id].processing){c[b.p.id].processing=!0;a("#sData",j+"_2").addClass("ui-state-active");l=q.oper;k=q.id;i[l]="_empty"==a.trim(i[b.p.id+"_id"])?q.addoper:q.editoper;i[l]!=q.addoper?i[k]=i[b.p.id+"_id"]:void 0===i[k]&&(i[k]=i[b.p.id+"_id"]);delete i[b.p.id+"_id"];i=a.extend(i,c[b.p.id].editData,g);if(!0===b.p.treeGrid)for(v in i[l]==q.addoper&&(p=a(b).jqGrid("getGridParam","selrow"),i["adjacency"==b.p.treeGridModel?b.p.treeReader.parent_id_field:"parent_id"]=p),b.p.treeReader)b.p.treeReader.hasOwnProperty(v)&&
(g=b.p.treeReader[v],i.hasOwnProperty(g)&&!(i[l]==q.addoper&&"parent_id_field"===v)&&delete i[g]);i[k]=a.jgrid.stripPref(b.p.idPrefix,i[k]);v=a.extend({url:c[b.p.id].url?c[b.p.id].url:a(b).jqGrid("getGridParam","editurl"),type:c[b.p.id].mtype,data:a.isFunction(c[b.p.id].serializeEditData)?c[b.p.id].serializeEditData.call(b,i):i,complete:function(g,n){i[k]=b.p.idPrefix+i[k];if(n!="success"){f[0]=false;f[1]=a(b).triggerHandler("jqGridAddEditErrorTextFormat",[g,z]);f[1]=a.isFunction(c[b.p.id].errorTextFormat)?
c[b.p.id].errorTextFormat.call(b,g):n+" Status: '"+g.statusText+"'. Error code: "+g.status}else{f=a(b).triggerHandler("jqGridAddEditAfterSubmit",[g,i,z]);f===void 0&&(f=[true,"",""]);f[0]&&a.isFunction(c[b.p.id].afterSubmit)&&(f=c[b.p.id].afterSubmit.call(b,g,i))}if(f[0]===false){a("#FormError>td",j).html(f[1]);a("#FormError",j).show()}else{a.each(b.p.colModel,function(){if(B[this.name]&&this.formatter&&this.formatter=="select")try{delete B[this.name]}catch(a){}});i=a.extend(i,B);b.p.autoencode&&
a.each(i,function(b,d){i[b]=a.jgrid.htmlDecode(d)});if(i[l]==q.addoper){f[2]||(f[2]=a.jgrid.randId());i[k]=f[2];if(c[b.p.id].closeAfterAdd){if(c[b.p.id].reloadAfterSubmit)a(b).trigger("reloadGrid");else if(b.p.treeGrid===true)a(b).jqGrid("addChildNode",f[2],p,i);else{a(b).jqGrid("addRowData",f[2],i,d.addedrow);a(b).jqGrid("setSelection",f[2])}a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose})}else if(c[b.p.id].clearAfterAdd){c[b.p.id].reloadAfterSubmit?
a(b).trigger("reloadGrid"):b.p.treeGrid===true?a(b).jqGrid("addChildNode",f[2],p,i):a(b).jqGrid("addRowData",f[2],i,d.addedrow);r("_empty",b,h)}else c[b.p.id].reloadAfterSubmit?a(b).trigger("reloadGrid"):b.p.treeGrid===true?a(b).jqGrid("addChildNode",f[2],p,i):a(b).jqGrid("addRowData",f[2],i,d.addedrow)}else{if(c[b.p.id].reloadAfterSubmit){a(b).trigger("reloadGrid");c[b.p.id].closeAfterEdit||setTimeout(function(){a(b).jqGrid("setSelection",i[k])},1E3)}else b.p.treeGrid===true?a(b).jqGrid("setTreeRow",
i[k],i):a(b).jqGrid("setRowData",i[k],i);c[b.p.id].closeAfterEdit&&a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose})}if(a.isFunction(c[b.p.id].afterComplete)){e=g;setTimeout(function(){a(b).triggerHandler("jqGridAddEditAfterComplete",[e,i,a("#"+h),z]);c[b.p.id].afterComplete.call(b,e,i,a("#"+h));e=null},500)}if(c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate){a("#"+h).data("disabled",false);if(c[b.p.id]._savedData[b.p.id+"_id"]!=
"_empty")for(var v in c[b.p.id]._savedData)i[v]&&(c[b.p.id]._savedData[v]=i[v])}}c[b.p.id].processing=false;a("#sData",j+"_2").removeClass("ui-state-active");try{a(":input:visible","#"+h)[0].focus()}catch(u){}}},a.jgrid.ajaxOptions,c[b.p.id].ajaxEditOptions);!v.url&&!c[b.p.id].useDataProxy&&(a.isFunction(b.p.dataProxy)?c[b.p.id].useDataProxy=!0:(f[0]=!1,f[1]+=" "+a.jgrid.errors.nourl));f[0]&&(c[b.p.id].useDataProxy?(g=b.p.dataProxy.call(b,v,"set_"+b.p.id),"undefined"==typeof g&&(g=[!0,""]),!1===g[0]?
(f[0]=!1,f[1]=g[1]||"Error deleting the selected row!"):(v.data.oper==q.addoper&&c[b.p.id].closeAfterAdd&&a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose}),v.data.oper==q.editoper&&c[b.p.id].closeAfterEdit&&a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose}))):a.ajax(v))}!1===f[0]&&(a("#FormError>td",j).html(f[1]),a("#FormError",j).show())}function p(a,b){var d=!1,
c;for(c in a)if(a[c]!=b[c]){d=!0;break}return d}function g(){var d=!0;a("#FormError",j).hide();if(c[b.p.id].checkOnUpdate&&(i={},B={},e(),F=a.extend({},i,B),M=p(F,c[b.p.id]._savedData)))a("#"+h).data("disabled",!0),a(".confirm","#"+o.themodal).show(),d=!1;return d}function f(){if("_empty"!==u&&"undefined"!==typeof b.p.savedRow&&0<b.p.savedRow.length&&a.isFunction(a.fn.jqGrid.restoreRow))for(var d=0;d<b.p.savedRow.length;d++)if(b.p.savedRow[d].id==u){a(b).jqGrid("restoreRow",u);break}}function n(b,
d){0===b?a("#pData",j+"_2").addClass("ui-state-disabled"):a("#pData",j+"_2").removeClass("ui-state-disabled");b==d?a("#nData",j+"_2").addClass("ui-state-disabled"):a("#nData",j+"_2").removeClass("ui-state-disabled")}function w(){var d=a(b).jqGrid("getDataIDs"),c=a("#id_g",j).val();return[a.inArray(c,d),d]}var b=this;if(b.grid&&u){var m=b.p.id,h="FrmGrid_"+m,k="TblGrid_"+m,j="#"+a.jgrid.jqID(k),o={themodal:"editmod"+m,modalhead:"edithd"+m,modalcontent:"editcnt"+m,scrollelm:h},y=a.isFunction(c[b.p.id].beforeShowForm)?
c[b.p.id].beforeShowForm:!1,A=a.isFunction(c[b.p.id].afterShowForm)?c[b.p.id].afterShowForm:!1,x=a.isFunction(c[b.p.id].beforeInitData)?c[b.p.id].beforeInitData:!1,E=a.isFunction(c[b.p.id].onInitializeForm)?c[b.p.id].onInitializeForm:!1,q=!0,v=1,H=0,i,B,F,M,z,h=a.jgrid.jqID(h);"new"===u?(u="_empty",z="add",d.caption=c[b.p.id].addCaption):(d.caption=c[b.p.id].editCaption,z="edit");!0===d.recreateForm&&null!==a("#"+a.jgrid.jqID(o.themodal)).html()&&a("#"+a.jgrid.jqID(o.themodal)).remove();var I=!0;
d.checkOnUpdate&&d.jqModal&&!d.modal&&(I=!1);if(null!==a("#"+a.jgrid.jqID(o.themodal)).html()){q=a(b).triggerHandler("jqGridAddEditBeforeInitData",[a("#"+a.jgrid.jqID(h))]);"undefined"==typeof q&&(q=!0);q&&x&&(q=x.call(b,a("#"+h)));if(!1===q)return;f();a(".ui-jqdialog-title","#"+a.jgrid.jqID(o.modalhead)).html(d.caption);a("#FormError",j).hide();c[b.p.id].topinfo?(a(".topinfo",j).html(c[b.p.id].topinfo),a(".tinfo",j).show()):a(".tinfo",j).hide();c[b.p.id].bottominfo?(a(".bottominfo",j+"_2").html(c[b.p.id].bottominfo),
a(".binfo",j+"_2").show()):a(".binfo",j+"_2").hide();r(u,b,h);"_empty"==u||!c[b.p.id].viewPagerButtons?a("#pData, #nData",j+"_2").hide():a("#pData, #nData",j+"_2").show();!0===c[b.p.id].processing&&(c[b.p.id].processing=!1,a("#sData",j+"_2").removeClass("ui-state-active"));!0===a("#"+h).data("disabled")&&(a(".confirm","#"+a.jgrid.jqID(o.themodal)).hide(),a("#"+h).data("disabled",!1));a(b).triggerHandler("jqGridAddEditBeforeShowForm",[a("#"+h),z]);y&&y.call(b,a("#"+h));a("#"+a.jgrid.jqID(o.themodal)).data("onClose",
c[b.p.id].onClose);a.jgrid.viewModal("#"+a.jgrid.jqID(o.themodal),{gbox:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,jqM:!1,overlay:d.overlay,modal:d.modal});I||a(".jqmOverlay").click(function(){if(!g())return false;a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose});return false});a(b).triggerHandler("jqGridAddEditAfterShowForm",[a("#"+h),z]);A&&A.call(b,a("#"+h))}else{var G=isNaN(d.dataheight)?d.dataheight:d.dataheight+"px",G=a("<form name='FormPost' id='"+
h+"' class='FormGrid' onSubmit='return false;' style='width:100%;overflow:auto;position:relative;height:"+G+";'></form>").data("disabled",!1),C=a("<table id='"+k+"' class='EditTable' cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),q=a(b).triggerHandler("jqGridAddEditBeforeInitData",[a("#"+h),z]);"undefined"==typeof q&&(q=!0);q&&x&&(q=x.call(b,a("#"+h)));if(!1===q)return;f();a(b.p.colModel).each(function(){var a=this.formoptions;v=Math.max(v,a?a.colpos||0:0);H=Math.max(H,a?a.rowpos||
0:0)});a(G).append(C);x=a("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='"+2*v+"'></td></tr>");x[0].rp=0;a(C).append(x);x=a("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='"+2*v+"'>"+c[b.p.id].topinfo+"</td></tr>");x[0].rp=0;a(C).append(x);var q=(x="rtl"==b.p.direction?!0:!1)?"nData":"pData",D=x?"pData":"nData";l(u,b,C,v);var q="<a href='javascript:void(0)' id='"+q+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>",
D="<a href='javascript:void(0)' id='"+D+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>",J="<a href='javascript:void(0)' id='sData' class='fm-button ui-state-default ui-corner-all'>"+d.bSubmit+"</a>",K="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+d.bCancel+"</a>",k="<table border='0' cellspacing='0' cellpadding='0' class='EditTable' id='"+k+"_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr id='Act_Buttons'><td class='navButton'>"+
(x?D+q:q+D)+"</td><td class='EditButton'>"+J+K+"</td></tr>"+("<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>"+c[b.p.id].bottominfo+"</td></tr>"),k=k+"</tbody></table>";if(0<H){var L=[];a.each(a(C)[0].rows,function(a,b){L[a]=b});L.sort(function(a,b){return a.rp>b.rp?1:a.rp<b.rp?-1:0});a.each(L,function(b,d){a("tbody",C).append(d)})}d.gbox="#gbox_"+a.jgrid.jqID(m);var N=!1;!0===d.closeOnEscape&&(d.closeOnEscape=!1,N=!0);k=a("<span></span>").append(G).append(k);a.jgrid.createModal(o,
k,d,"#gview_"+a.jgrid.jqID(b.p.id),a("#gbox_"+a.jgrid.jqID(b.p.id))[0]);x&&(a("#pData, #nData",j+"_2").css("float","right"),a(".EditButton",j+"_2").css("text-align","left"));c[b.p.id].topinfo&&a(".tinfo",j).show();c[b.p.id].bottominfo&&a(".binfo",j+"_2").show();k=k=null;a("#"+a.jgrid.jqID(o.themodal)).keydown(function(e){var f=e.target;if(a("#"+h).data("disabled")===true)return false;if(c[b.p.id].savekey[0]===true&&e.which==c[b.p.id].savekey[1]&&f.tagName!="TEXTAREA"){a("#sData",j+"_2").trigger("click");
return false}if(e.which===27){if(!g())return false;N&&a.jgrid.hideModal(this,{gb:d.gbox,jqm:d.jqModal,onClose:c[b.p.id].onClose});return false}if(c[b.p.id].navkeys[0]===true){if(a("#id_g",j).val()=="_empty")return true;if(e.which==c[b.p.id].navkeys[1]){a("#pData",j+"_2").trigger("click");return false}if(e.which==c[b.p.id].navkeys[2]){a("#nData",j+"_2").trigger("click");return false}}});d.checkOnUpdate&&(a("a.ui-jqdialog-titlebar-close span","#"+a.jgrid.jqID(o.themodal)).removeClass("jqmClose"),a("a.ui-jqdialog-titlebar-close",
"#"+a.jgrid.jqID(o.themodal)).unbind("click").click(function(){if(!g())return false;a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose});return false}));d.saveicon=a.extend([!0,"left","ui-icon-disk"],d.saveicon);d.closeicon=a.extend([!0,"left","ui-icon-close"],d.closeicon);!0===d.saveicon[0]&&a("#sData",j+"_2").addClass("right"==d.saveicon[1]?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+d.saveicon[2]+
"'></span>");!0===d.closeicon[0]&&a("#cData",j+"_2").addClass("right"==d.closeicon[1]?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+d.closeicon[2]+"'></span>");if(c[b.p.id].checkOnSubmit||c[b.p.id].checkOnUpdate)J="<a href='javascript:void(0)' id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+d.bYes+"</a>",D="<a href='javascript:void(0)' id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+d.bNo+"</a>",K="<a href='javascript:void(0)' id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+
d.bExit+"</a>",k=d.zIndex||999,k++,a("<div class='ui-widget-overlay jqgrid-overlay confirm' style='z-index:"+k+";display:none;'>&#160;"+(a.browser.msie&&6==a.browser.version?'<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>':"")+"</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:"+(k+1)+"'>"+d.saveData+"<br/><br/>"+J+D+K+"</div>").insertAfter("#"+h),a("#sNew","#"+a.jgrid.jqID(o.themodal)).click(function(){s();
a("#"+h).data("disabled",false);a(".confirm","#"+a.jgrid.jqID(o.themodal)).hide();return false}),a("#nNew","#"+a.jgrid.jqID(o.themodal)).click(function(){a(".confirm","#"+a.jgrid.jqID(o.themodal)).hide();a("#"+h).data("disabled",false);setTimeout(function(){a(":input","#"+h)[0].focus()},0);return false}),a("#cNew","#"+a.jgrid.jqID(o.themodal)).click(function(){a(".confirm","#"+a.jgrid.jqID(o.themodal)).hide();a("#"+h).data("disabled",false);a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+
a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose});return false});a(b).triggerHandler("jqGridAddEditInitializeForm",[a("#"+h),z]);E&&E.call(b,a("#"+h));"_empty"==u||!c[b.p.id].viewPagerButtons?a("#pData,#nData",j+"_2").hide():a("#pData,#nData",j+"_2").show();a(b).triggerHandler("jqGridAddEditBeforeShowForm",[a("#"+h),z]);y&&y.call(b,a("#"+h));a("#"+a.jgrid.jqID(o.themodal)).data("onClose",c[b.p.id].onClose);a.jgrid.viewModal("#"+a.jgrid.jqID(o.themodal),{gbox:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,
overlay:d.overlay,modal:d.modal});I||a(".jqmOverlay").click(function(){if(!g())return false;a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose});return false});a(b).triggerHandler("jqGridAddEditAfterShowForm",[a("#"+h),z]);A&&A.call(b,a("#"+h));a(".fm-button","#"+a.jgrid.jqID(o.themodal)).hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});a("#sData",j+"_2").click(function(){i={};B=
{};a("#FormError",j).hide();e();if(i[b.p.id+"_id"]=="_empty")s();else if(d.checkOnSubmit===true){F=a.extend({},i,B);if(M=p(F,c[b.p.id]._savedData)){a("#"+h).data("disabled",true);a(".confirm","#"+a.jgrid.jqID(o.themodal)).show()}else s()}else s();return false});a("#cData",j+"_2").click(function(){if(!g())return false;a.jgrid.hideModal("#"+a.jgrid.jqID(o.themodal),{gb:"#gbox_"+a.jgrid.jqID(m),jqm:d.jqModal,onClose:c[b.p.id].onClose});return false});a("#nData",j+"_2").click(function(){if(!g())return false;
a("#FormError",j).hide();var c=w();c[0]=parseInt(c[0],10);if(c[0]!=-1&&c[1][c[0]+1]){a(b).triggerHandler("jqGridAddEditClickPgButtons",["next",a("#"+h),c[1][c[0]]]);a.isFunction(d.onclickPgButtons)&&d.onclickPgButtons.call(b,"next",a("#"+h),c[1][c[0]]);r(c[1][c[0]+1],b,h);a(b).jqGrid("setSelection",c[1][c[0]+1]);a(b).triggerHandler("jqGridAddEditAfterClickPgButtons",["next",a("#"+h),c[1][c[0]]]);a.isFunction(d.afterclickPgButtons)&&d.afterclickPgButtons.call(b,"next",a("#"+h),c[1][c[0]+1]);n(c[0]+
1,c[1].length-1)}return false});a("#pData",j+"_2").click(function(){if(!g())return false;a("#FormError",j).hide();var c=w();if(c[0]!=-1&&c[1][c[0]-1]){a(b).triggerHandler("jqGridAddEditClickPgButtons",["prev",a("#"+h),c[1][c[0]]]);a.isFunction(d.onclickPgButtons)&&d.onclickPgButtons.call(b,"prev",a("#"+h),c[1][c[0]]);r(c[1][c[0]-1],b,h);a(b).jqGrid("setSelection",c[1][c[0]-1]);a(b).triggerHandler("jqGridAddEditAfterClickPgButtons",["prev",a("#"+h),c[1][c[0]]]);a.isFunction(d.afterclickPgButtons)&&
d.afterclickPgButtons.call(b,"prev",a("#"+h),c[1][c[0]-1]);n(c[0]-1,c[1].length-1)}return false})}y=w();n(y[0],y[1].length-1)}})},viewGridRow:function(c,d){d=a.extend({top:0,left:0,width:0,height:"auto",dataheight:"auto",modal:!1,overlay:30,drag:!0,resize:!0,jqModal:!0,closeOnEscape:!1,labelswidth:"30%",closeicon:[],navkeys:[!1,38,40],onClose:null,beforeShowForm:null,beforeInitData:null,viewPagerButtons:!0},a.jgrid.view,d||{});return this.each(function(){function e(){(!0===d.closeOnEscape||!0===d.navkeys[0])&&
setTimeout(function(){a(".ui-jqdialog-titlebar-close","#"+a.jgrid.jqID(m.modalhead)).focus()},0)}function l(b,c,e,f){for(var g,h,k,j=0,o,m,l=[],n=!1,p="<td class='CaptionTD form-view-label ui-widget-content' width='"+d.labelswidth+"'>&#160;</td><td class='DataTD form-view-data ui-helper-reset ui-widget-content'>&#160;</td>",u="",s=["integer","number","currency"],r=0,t=0,y,x,w,A=1;A<=f;A++)u+=1==A?p:"<td class='CaptionTD form-view-label ui-widget-content'>&#160;</td><td class='DataTD form-view-data ui-widget-content'>&#160;</td>";
a(c.p.colModel).each(function(){h=this.editrules&&!0===this.editrules.edithidden?!1:!0===this.hidden?!0:!1;!h&&"right"===this.align&&(this.formatter&&-1!==a.inArray(this.formatter,s)?r=Math.max(r,parseInt(this.width,10)):t=Math.max(t,parseInt(this.width,10)))});y=0!==r?r:0!==t?t:0;n=a(c).jqGrid("getInd",b);a(c.p.colModel).each(function(b){g=this.name;x=!1;m=(h=this.editrules&&!0===this.editrules.edithidden?!1:!0===this.hidden?!0:!1)?"style='display:none'":"";w="boolean"!=typeof this.viewable?!0:this.viewable;
if("cb"!==g&&"subgrid"!==g&&"rn"!==g&&w){o=!1===n?"":g==c.p.ExpandColumn&&!0===c.p.treeGrid?a("td:eq("+b+")",c.rows[n]).text():a("td:eq("+b+")",c.rows[n]).html();x="right"===this.align&&0!==y?!0:!1;a.extend({},this.editoptions||{},{id:g,name:g});var d=a.extend({},{rowabove:!1,rowcontent:""},this.formoptions||{}),q=parseInt(d.rowpos,10)||j+1,p=parseInt(2*(parseInt(d.colpos,10)||1),10);if(d.rowabove){var r=a("<tr><td class='contentinfo' colspan='"+2*f+"'>"+d.rowcontent+"</td></tr>");a(e).append(r);
r[0].rp=q}k=a(e).find("tr[rowpos="+q+"]");0===k.length&&(k=a("<tr "+m+" rowpos='"+q+"'></tr>").addClass("FormData").attr("id","trv_"+g),a(k).append(u),a(e).append(k),k[0].rp=q);a("td:eq("+(p-2)+")",k[0]).html("<b>"+("undefined"===typeof d.label?c.p.colNames[b]:d.label)+"</b>");a("td:eq("+(p-1)+")",k[0]).append("<span>"+o+"</span>").attr("id","v_"+g);x&&a("td:eq("+(p-1)+") span",k[0]).css({"text-align":"right",width:y+"px"});l[j]=b;j++}});0<j&&(b=a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+
(2*f-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='id' value='"+b+"'/></td></tr>"),b[0].rp=j+99,a(e).append(b));return l}function r(b,c){var d,e,f=0,g,h;if(h=a(c).jqGrid("getInd",b,!0))a("td",h).each(function(b){d=c.p.colModel[b].name;e=c.p.colModel[b].editrules&&!0===c.p.colModel[b].editrules.edithidden?!1:!0===c.p.colModel[b].hidden?!0:!1;"cb"!==d&&"subgrid"!==d&&"rn"!==d&&(g=d==c.p.ExpandColumn&&!0===c.p.treeGrid?a(this).text():a(this).html(),a.extend({},c.p.colModel[b].editoptions||
{}),d=a.jgrid.jqID("v_"+d),a("#"+d+" span","#"+n).html(g),e&&a("#"+d,"#"+n).parents("tr:first").hide(),f++)}),0<f&&a("#id_g","#"+n).val(b)}function t(b,c){0===b?a("#pData","#"+n+"_2").addClass("ui-state-disabled"):a("#pData","#"+n+"_2").removeClass("ui-state-disabled");b==c?a("#nData","#"+n+"_2").addClass("ui-state-disabled"):a("#nData","#"+n+"_2").removeClass("ui-state-disabled")}function s(){var b=a(p).jqGrid("getDataIDs"),c=a("#id_g","#"+n).val();return[a.inArray(c,b),b]}var p=this;if(p.grid&&
c){var g=p.p.id,f="ViewGrid_"+a.jgrid.jqID(g),n="ViewTbl_"+a.jgrid.jqID(g),w="ViewGrid_"+g,b="ViewTbl_"+g,m={themodal:"viewmod"+g,modalhead:"viewhd"+g,modalcontent:"viewcnt"+g,scrollelm:f},h=a.isFunction(d.beforeInitData)?d.beforeInitData:!1,k=!0,j=1,o=0;if(null!==a("#"+a.jgrid.jqID(m.themodal)).html()){h&&(k=h.call(p,a("#"+f)),"undefined"==typeof k&&(k=!0));if(!1===k)return;a(".ui-jqdialog-title","#"+a.jgrid.jqID(m.modalhead)).html(d.caption);a("#FormError","#"+n).hide();r(c,p);a.isFunction(d.beforeShowForm)&&
d.beforeShowForm.call(p,a("#"+f));a.jgrid.viewModal("#"+a.jgrid.jqID(m.themodal),{gbox:"#gbox_"+a.jgrid.jqID(g),jqm:d.jqModal,jqM:!1,overlay:d.overlay,modal:d.modal});e()}else{var y=isNaN(d.dataheight)?d.dataheight:d.dataheight+"px",w=a("<form name='FormPost' id='"+w+"' class='FormGrid' style='width:100%;overflow:auto;position:relative;height:"+y+";'></form>"),A=a("<table id='"+b+"' class='EditTable' cellspacing='1' cellpadding='2' border='0' style='table-layout:fixed'><tbody></tbody></table>");h&&
(k=h.call(p,a("#"+f)),"undefined"==typeof k&&(k=!0));if(!1===k)return;a(p.p.colModel).each(function(){var a=this.formoptions;j=Math.max(j,a?a.colpos||0:0);o=Math.max(o,a?a.rowpos||0:0)});a(w).append(A);l(c,p,A,j);b="rtl"==p.p.direction?!0:!1;h="<a href='javascript:void(0)' id='"+(b?"nData":"pData")+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>";k="<a href='javascript:void(0)' id='"+(b?"pData":"nData")+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>";
y="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+d.bClose+"</a>";if(0<o){var x=[];a.each(a(A)[0].rows,function(a,b){x[a]=b});x.sort(function(a,b){return a.rp>b.rp?1:a.rp<b.rp?-1:0});a.each(x,function(b,c){a("tbody",A).append(c)})}d.gbox="#gbox_"+a.jgrid.jqID(g);var E=!1;!0===d.closeOnEscape&&(d.closeOnEscape=!1,E=!0);w=a("<span></span>").append(w).append("<table border='0' class='EditTable' id='"+n+"_2'><tbody><tr id='Act_Buttons'><td class='navButton' width='"+
d.labelswidth+"'>"+(b?k+h:h+k)+"</td><td class='EditButton'>"+y+"</td></tr></tbody></table>");a.jgrid.createModal(m,w,d,"#gview_"+a.jgrid.jqID(p.p.id),a("#gview_"+a.jgrid.jqID(p.p.id))[0]);b&&(a("#pData, #nData","#"+n+"_2").css("float","right"),a(".EditButton","#"+n+"_2").css("text-align","left"));d.viewPagerButtons||a("#pData, #nData","#"+n+"_2").hide();w=null;a("#"+m.themodal).keydown(function(b){if(b.which===27){E&&a.jgrid.hideModal(this,{gb:d.gbox,jqm:d.jqModal,onClose:d.onClose});return false}if(d.navkeys[0]===
true){if(b.which===d.navkeys[1]){a("#pData","#"+n+"_2").trigger("click");return false}if(b.which===d.navkeys[2]){a("#nData","#"+n+"_2").trigger("click");return false}}});d.closeicon=a.extend([!0,"left","ui-icon-close"],d.closeicon);!0===d.closeicon[0]&&a("#cData","#"+n+"_2").addClass("right"==d.closeicon[1]?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+d.closeicon[2]+"'></span>");a.isFunction(d.beforeShowForm)&&d.beforeShowForm.call(p,a("#"+f));a.jgrid.viewModal("#"+
a.jgrid.jqID(m.themodal),{gbox:"#gbox_"+a.jgrid.jqID(g),jqm:d.jqModal,modal:d.modal});a(".fm-button:not(.ui-state-disabled)","#"+n+"_2").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});e();a("#cData","#"+n+"_2").click(function(){a.jgrid.hideModal("#"+a.jgrid.jqID(m.themodal),{gb:"#gbox_"+a.jgrid.jqID(g),jqm:d.jqModal,onClose:d.onClose});return false});a("#nData","#"+n+"_2").click(function(){a("#FormError","#"+n).hide();var b=s();b[0]=parseInt(b[0],
10);if(b[0]!=-1&&b[1][b[0]+1]){a.isFunction(d.onclickPgButtons)&&d.onclickPgButtons.call(p,"next",a("#"+f),b[1][b[0]]);r(b[1][b[0]+1],p);a(p).jqGrid("setSelection",b[1][b[0]+1]);a.isFunction(d.afterclickPgButtons)&&d.afterclickPgButtons.call(p,"next",a("#"+f),b[1][b[0]+1]);t(b[0]+1,b[1].length-1)}e();return false});a("#pData","#"+n+"_2").click(function(){a("#FormError","#"+n).hide();var b=s();if(b[0]!=-1&&b[1][b[0]-1]){a.isFunction(d.onclickPgButtons)&&d.onclickPgButtons.call(p,"prev",a("#"+f),b[1][b[0]]);
r(b[1][b[0]-1],p);a(p).jqGrid("setSelection",b[1][b[0]-1]);a.isFunction(d.afterclickPgButtons)&&d.afterclickPgButtons.call(p,"prev",a("#"+f),b[1][b[0]-1]);t(b[0]-1,b[1].length-1)}e();return false})}w=s();t(w[0],w[1].length-1)}})},delGridRow:function(u,d){d=a.extend({top:0,left:0,width:240,height:"auto",dataheight:"auto",modal:!1,overlay:30,drag:!0,resize:!0,url:"",mtype:"POST",reloadAfterSubmit:!0,beforeShowForm:null,beforeInitData:null,afterShowForm:null,beforeSubmit:null,onclickSubmit:null,afterSubmit:null,
jqModal:!0,closeOnEscape:!1,delData:{},delicon:[],cancelicon:[],onClose:null,ajaxDelOptions:{},processing:!1,serializeDelData:null,useDataProxy:!1},a.jgrid.del,d||{});c[a(this)[0].p.id]=d;return this.each(function(){var e=this;if(e.grid&&u){var l=a.isFunction(c[e.p.id].beforeShowForm),r=a.isFunction(c[e.p.id].afterShowForm),t=a.isFunction(c[e.p.id].beforeInitData)?c[e.p.id].beforeInitData:!1,s=e.p.id,p={},g=!0,f="DelTbl_"+a.jgrid.jqID(s),n,w,b,m,h="DelTbl_"+s,k={themodal:"delmod"+s,modalhead:"delhd"+
s,modalcontent:"delcnt"+s,scrollelm:f};jQuery.isArray(u)&&(u=u.join());if(null!==a("#"+a.jgrid.jqID(k.themodal)).html()){t&&(g=t.call(e,a("#"+f)),"undefined"==typeof g&&(g=!0));if(!1===g)return;a("#DelData>td","#"+f).text(u);a("#DelError","#"+f).hide();!0===c[e.p.id].processing&&(c[e.p.id].processing=!1,a("#dData","#"+f).removeClass("ui-state-active"));l&&c[e.p.id].beforeShowForm.call(e,a("#"+f));a.jgrid.viewModal("#"+a.jgrid.jqID(k.themodal),{gbox:"#gbox_"+a.jgrid.jqID(s),jqm:c[e.p.id].jqModal,jqM:!1,
overlay:c[e.p.id].overlay,modal:c[e.p.id].modal})}else{var j=isNaN(c[e.p.id].dataheight)?c[e.p.id].dataheight:c[e.p.id].dataheight+"px",h="<div id='"+h+"' class='formdata' style='width:100%;overflow:auto;position:relative;height:"+j+";'><table class='DelTable'><tbody><tr id='DelError' style='display:none'><td class='ui-state-error'></td></tr>"+("<tr id='DelData' style='display:none'><td >"+u+"</td></tr>"),h=h+('<tr><td class="delmsg" style="white-space:pre;">'+c[e.p.id].msg+"</td></tr><tr><td >&#160;</td></tr>"),
h=h+"</tbody></table></div>"+("<table cellspacing='0' cellpadding='0' border='0' class='EditTable' id='"+f+"_2'><tbody><tr><td><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='DelButton EditButton'>"+("<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>"+d.bSubmit+"</a>")+"&#160;"+("<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>"+d.bCancel+"</a>")+"</td></tr></tbody></table>");d.gbox="#gbox_"+
a.jgrid.jqID(s);a.jgrid.createModal(k,h,d,"#gview_"+a.jgrid.jqID(e.p.id),a("#gview_"+a.jgrid.jqID(e.p.id))[0]);t&&(g=t.call(e,a("#"+f)),"undefined"==typeof g&&(g=!0));if(!1===g)return;a(".fm-button","#"+f+"_2").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});d.delicon=a.extend([!0,"left","ui-icon-scissors"],c[e.p.id].delicon);d.cancelicon=a.extend([!0,"left","ui-icon-cancel"],c[e.p.id].cancelicon);!0===d.delicon[0]&&a("#dData","#"+f+"_2").addClass("right"==
d.delicon[1]?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+d.delicon[2]+"'></span>");!0===d.cancelicon[0]&&a("#eData","#"+f+"_2").addClass("right"==d.cancelicon[1]?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+d.cancelicon[2]+"'></span>");a("#dData","#"+f+"_2").click(function(){var g=[true,""];p={};var h=a("#DelData>td","#"+f).text();a.isFunction(c[e.p.id].onclickSubmit)&&(p=c[e.p.id].onclickSubmit.call(e,c[e.p.id],h)||{});a.isFunction(c[e.p.id].beforeSubmit)&&
(g=c[e.p.id].beforeSubmit.call(e,h));if(g[0]&&!c[e.p.id].processing){c[e.p.id].processing=true;b=e.p.prmNames;n=a.extend({},c[e.p.id].delData,p);m=b.oper;n[m]=b.deloper;w=b.id;h=(""+h).split(",");if(!h.length)return false;for(var j in h)h.hasOwnProperty(j)&&(h[j]=a.jgrid.stripPref(e.p.idPrefix,h[j]));n[w]=h.join();a(this).addClass("ui-state-active");j=a.extend({url:c[e.p.id].url?c[e.p.id].url:a(e).jqGrid("getGridParam","editurl"),type:c[e.p.id].mtype,data:a.isFunction(c[e.p.id].serializeDelData)?
c[e.p.id].serializeDelData.call(e,n):n,complete:function(b,j){if(j!="success"){g[0]=false;g[1]=a.isFunction(c[e.p.id].errorTextFormat)?c[e.p.id].errorTextFormat.call(e,b):j+" Status: '"+b.statusText+"'. Error code: "+b.status}else a.isFunction(c[e.p.id].afterSubmit)&&(g=c[e.p.id].afterSubmit.call(e,b,n));if(g[0]===false){a("#DelError>td","#"+f).html(g[1]);a("#DelError","#"+f).show()}else{if(c[e.p.id].reloadAfterSubmit&&e.p.datatype!="local")a(e).trigger("reloadGrid");else{if(e.p.treeGrid===true)try{a(e).jqGrid("delTreeNode",
e.p.idPrefix+h[0])}catch(m){}else for(var l=0;l<h.length;l++)a(e).jqGrid("delRowData",e.p.idPrefix+h[l]);e.p.selrow=null;e.p.selarrrow=[]}a.isFunction(c[e.p.id].afterComplete)&&setTimeout(function(){c[e.p.id].afterComplete.call(e,b,h)},500)}c[e.p.id].processing=false;a("#dData","#"+f+"_2").removeClass("ui-state-active");g[0]&&a.jgrid.hideModal("#"+a.jgrid.jqID(k.themodal),{gb:"#gbox_"+a.jgrid.jqID(s),jqm:d.jqModal,onClose:c[e.p.id].onClose})}},a.jgrid.ajaxOptions,c[e.p.id].ajaxDelOptions);if(!j.url&&
!c[e.p.id].useDataProxy)if(a.isFunction(e.p.dataProxy))c[e.p.id].useDataProxy=true;else{g[0]=false;g[1]=g[1]+(" "+a.jgrid.errors.nourl)}if(g[0])if(c[e.p.id].useDataProxy){j=e.p.dataProxy.call(e,j,"del_"+e.p.id);typeof j=="undefined"&&(j=[true,""]);if(j[0]===false){g[0]=false;g[1]=j[1]||"Error deleting the selected row!"}else a.jgrid.hideModal("#"+a.jgrid.jqID(k.themodal),{gb:"#gbox_"+a.jgrid.jqID(s),jqm:d.jqModal,onClose:c[e.p.id].onClose})}else a.ajax(j)}if(g[0]===false){a("#DelError>td","#"+f).html(g[1]);
a("#DelError","#"+f).show()}return false});a("#eData","#"+f+"_2").click(function(){a.jgrid.hideModal("#"+a.jgrid.jqID(k.themodal),{gb:"#gbox_"+a.jgrid.jqID(s),jqm:c[e.p.id].jqModal,onClose:c[e.p.id].onClose});return false});l&&c[e.p.id].beforeShowForm.call(e,a("#"+f));a.jgrid.viewModal("#"+a.jgrid.jqID(k.themodal),{gbox:"#gbox_"+a.jgrid.jqID(s),jqm:c[e.p.id].jqModal,overlay:c[e.p.id].overlay,modal:c[e.p.id].modal})}r&&c[e.p.id].afterShowForm.call(e,a("#"+f));!0===c[e.p.id].closeOnEscape&&setTimeout(function(){a(".ui-jqdialog-titlebar-close",
"#"+a.jgrid.jqID(k.modalhead)).focus()},0)}})},navGrid:function(c,d,e,l,r,t,s){d=a.extend({edit:!0,editicon:"ui-icon-pencil",add:!0,addicon:"ui-icon-plus",del:!0,delicon:"ui-icon-trash",search:!0,searchicon:"ui-icon-search",refresh:!0,refreshicon:"ui-icon-refresh",refreshstate:"firstpage",view:!1,viewicon:"ui-icon-document",position:"left",closeOnEscape:!0,beforeRefresh:null,afterRefresh:null,cloneToTop:!1,alertwidth:200,alertheight:"auto",alerttop:null,alertleft:null,alertzIndex:null},a.jgrid.nav,
d||{});return this.each(function(){if(!this.nav){var p={themodal:"alertmod",modalhead:"alerthd",modalcontent:"alertcnt"},g=this,f;if(g.grid&&"string"==typeof c){null===a("#"+p.themodal).html()&&(!d.alerttop&&!d.alertleft&&("undefined"!=typeof window.innerWidth?(d.alertleft=window.innerWidth,d.alerttop=window.innerHeight):"undefined"!=typeof document.documentElement&&"undefined"!=typeof document.documentElement.clientWidth&&0!==document.documentElement.clientWidth?(d.alertleft=document.documentElement.clientWidth,
d.alerttop=document.documentElement.clientHeight):(d.alertleft=1024,d.alerttop=768),d.alertleft=d.alertleft/2-parseInt(d.alertwidth,10)/2,d.alerttop=d.alerttop/2-25),a.jgrid.createModal(p,"<div>"+d.alerttext+"</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",{gbox:"#gbox_"+a.jgrid.jqID(g.p.id),jqModal:!0,drag:!0,resize:!0,caption:d.alertcap,top:d.alerttop,left:d.alertleft,width:d.alertwidth,height:d.alertheight,closeOnEscape:d.closeOnEscape,zIndex:d.alertzIndex},"","",!0));
var n=1;d.cloneToTop&&g.p.toppager&&(n=2);for(var w=0;w<n;w++){var b=a("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"),m,h;0===w?(m=c,h=g.p.id,m==g.p.toppager&&(h+="_top",n=1)):(m=g.p.toppager,h=g.p.id+"_top");"rtl"==g.p.direction&&a(b).attr("dir","rtl").css("float","right");d.add&&(l=l||{},f=a("<td class='ui-pg-button ui-corner-all'></td>"),a(f).append("<div class='ui-pg-div'><span class='ui-icon "+
d.addicon+"'></span>"+d.addtext+"</div>"),a("tr",b).append(f),a(f,b).attr({title:d.addtitle||"",id:l.id||"add_"+h}).click(function(){a(this).hasClass("ui-state-disabled")||(a.isFunction(d.addfunc)?d.addfunc.call(g):a(g).jqGrid("editGridRow","new",l));return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")}),f=null);d.edit&&(f=a("<td class='ui-pg-button ui-corner-all'></td>"),e=e||{},a(f).append("<div class='ui-pg-div'><span class='ui-icon "+
d.editicon+"'></span>"+d.edittext+"</div>"),a("tr",b).append(f),a(f,b).attr({title:d.edittitle||"",id:e.id||"edit_"+h}).click(function(){if(!a(this).hasClass("ui-state-disabled")){var b=g.p.selrow;if(b)a.isFunction(d.editfunc)?d.editfunc.call(g,b):a(g).jqGrid("editGridRow",b,e);else{a.jgrid.viewModal("#"+p.themodal,{gbox:"#gbox_"+a.jgrid.jqID(g.p.id),jqm:true});a("#jqg_alrt").focus()}}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")}),
f=null);d.view&&(f=a("<td class='ui-pg-button ui-corner-all'></td>"),s=s||{},a(f).append("<div class='ui-pg-div'><span class='ui-icon "+d.viewicon+"'></span>"+d.viewtext+"</div>"),a("tr",b).append(f),a(f,b).attr({title:d.viewtitle||"",id:s.id||"view_"+h}).click(function(){if(!a(this).hasClass("ui-state-disabled")){var b=g.p.selrow;if(b)a.isFunction(d.viewfunc)?d.viewfunc.call(g,b):a(g).jqGrid("viewGridRow",b,s);else{a.jgrid.viewModal("#"+p.themodal,{gbox:"#gbox_"+a.jgrid.jqID(g.p.id),jqm:true});a("#jqg_alrt").focus()}}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||
a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")}),f=null);d.del&&(f=a("<td class='ui-pg-button ui-corner-all'></td>"),r=r||{},a(f).append("<div class='ui-pg-div'><span class='ui-icon "+d.delicon+"'></span>"+d.deltext+"</div>"),a("tr",b).append(f),a(f,b).attr({title:d.deltitle||"",id:r.id||"del_"+h}).click(function(){if(!a(this).hasClass("ui-state-disabled")){var b;if(g.p.multiselect){b=g.p.selarrrow;b.length===0&&(b=null)}else b=g.p.selrow;if(b)a.isFunction(d.delfunc)?
d.delfunc.call(g,b):a(g).jqGrid("delGridRow",b,r);else{a.jgrid.viewModal("#"+p.themodal,{gbox:"#gbox_"+a.jgrid.jqID(g.p.id),jqm:true});a("#jqg_alrt").focus()}}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")}),f=null);(d.add||d.edit||d.del||d.view)&&a("tr",b).append("<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>");d.search&&(f=a("<td class='ui-pg-button ui-corner-all'></td>"),
t=t||{},a(f).append("<div class='ui-pg-div'><span class='ui-icon "+d.searchicon+"'></span>"+d.searchtext+"</div>"),a("tr",b).append(f),a(f,b).attr({title:d.searchtitle||"",id:t.id||"search_"+h}).click(function(){a(this).hasClass("ui-state-disabled")||(a.isFunction(d.searchfunc)?d.searchfunc.call(g,t):a(g).jqGrid("searchGrid",t));return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")}),t.showOnLoad&&
!0===t.showOnLoad&&a(f,b).click(),f=null);d.refresh&&(f=a("<td class='ui-pg-button ui-corner-all'></td>"),a(f).append("<div class='ui-pg-div'><span class='ui-icon "+d.refreshicon+"'></span>"+d.refreshtext+"</div>"),a("tr",b).append(f),a(f,b).attr({title:d.refreshtitle||"",id:"refresh_"+h}).click(function(){if(!a(this).hasClass("ui-state-disabled")){a.isFunction(d.beforeRefresh)&&d.beforeRefresh.call(g);g.p.search=false;try{var b=g.p.id;g.p.postData.filters="";a("#fbox_"+a.jgrid.jqID(b)).jqFilter("resetFilter");
a.isFunction(g.clearToolbar)&&g.clearToolbar.call(g,false)}catch(c){}switch(d.refreshstate){case "firstpage":a(g).trigger("reloadGrid",[{page:1}]);break;case "current":a(g).trigger("reloadGrid",[{current:true}])}a.isFunction(d.afterRefresh)&&d.afterRefresh.call(g)}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")}),f=null);f=a(".ui-jqgrid").css("font-size")||"11px";a("body").append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+
f+";visibility:hidden;' ></div>");f=a(b).clone().appendTo("#testpg2").width();a("#testpg2").remove();a(m+"_"+d.position,m).append(b);g.p._nvtd&&(f>g.p._nvtd[0]&&(a(m+"_"+d.position,m).width(f),g.p._nvtd[0]=f),g.p._nvtd[1]=f);b=f=f=null;this.nav=!0}}}})},navButtonAdd:function(c,d){d=a.extend({caption:"newButton",title:"",buttonicon:"ui-icon-newwin",onClickButton:null,position:"last",cursor:"pointer"},d||{});return this.each(function(){if(this.grid){"string"===typeof c&&0!==c.indexOf("#")&&(c="#"+a.jgrid.jqID(c));
var e=a(".navtable",c)[0],l=this;if(e&&!(d.id&&null!==a("#"+a.jgrid.jqID(d.id),e).html())){var r=a("<td></td>");"NONE"==d.buttonicon.toString().toUpperCase()?a(r).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'>"+d.caption+"</div>"):a(r).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'><span class='ui-icon "+d.buttonicon+"'></span>"+d.caption+"</div>");d.id&&a(r).attr("id",d.id);"first"==d.position?0===e.rows[0].cells.length?a("tr",e).append(r):a("tr td:eq(0)",
e).before(r):a("tr",e).append(r);a(r,e).attr("title",d.title||"").click(function(c){a(this).hasClass("ui-state-disabled")||a.isFunction(d.onClickButton)&&d.onClickButton.call(l,c);return!1}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")})}}})},navSeparatorAdd:function(c,d){d=a.extend({sepclass:"ui-separator",sepcontent:""},d||{});return this.each(function(){if(this.grid){"string"===typeof c&&0!==c.indexOf("#")&&
(c="#"+a.jgrid.jqID(c));var e=a(".navtable",c)[0];if(e){var l="<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='"+d.sepclass+"'></span>"+d.sepcontent+"</td>";a("tr",e).append(l)}}})},GridToForm:function(c,d){return this.each(function(){var e=this;if(e.grid){var l=a(e).jqGrid("getRowData",c);if(l)for(var r in l)a("[name="+a.jgrid.jqID(r)+"]",d).is("input:radio")||a("[name="+a.jgrid.jqID(r)+"]",d).is("input:checkbox")?a("[name="+a.jgrid.jqID(r)+"]",d).each(function(){if(a(this).val()==
l[r])a(this)[e.p.useProp?"prop":"attr"]("checked",!0);else a(this)[e.p.useProp?"prop":"attr"]("checked",!1)}):a("[name="+a.jgrid.jqID(r)+"]",d).val(l[r])}})},FormToGrid:function(c,d,e,l){return this.each(function(){if(this.grid){e||(e="set");l||(l="first");var r=a(d).serializeArray(),t={};a.each(r,function(a,c){t[c.name]=c.value});"add"==e?a(this).jqGrid("addRowData",c,t,l):"set"==e&&a(this).jqGrid("setRowData",c,t)}})}})})(jQuery);
(function(a){a.fn.jqFilter=function(d){if("string"===typeof d){var n=a.fn.jqFilter[d];if(!n)throw"jqFilter - No such method: "+d;var u=a.makeArray(arguments).slice(1);return n.apply(this,u)}var o=a.extend(!0,{filter:null,columns:[],onChange:null,afterRedraw:null,checkValues:null,error:!1,errmsg:"",errorcheck:!0,showQuery:!0,sopt:null,ops:[{name:"eq",description:"equal",operator:"="},{name:"ne",description:"not equal",operator:"<>"},{name:"lt",description:"less",operator:"<"},{name:"le",description:"less or equal",
operator:"<="},{name:"gt",description:"greater",operator:">"},{name:"ge",description:"greater or equal",operator:">="},{name:"bw",description:"begins with",operator:"LIKE"},{name:"bn",description:"does not begin with",operator:"NOT LIKE"},{name:"in",description:"in",operator:"IN"},{name:"ni",description:"not in",operator:"NOT IN"},{name:"ew",description:"ends with",operator:"LIKE"},{name:"en",description:"does not end with",operator:"NOT LIKE"},{name:"cn",description:"contains",operator:"LIKE"},{name:"nc",
description:"does not contain",operator:"NOT LIKE"},{name:"nu",description:"is null",operator:"IS NULL"},{name:"nn",description:"is not null",operator:"IS NOT NULL"}],numopts:"eq,ne,lt,le,gt,ge,nu,nn,in,ni".split(","),stropts:"eq,ne,bw,bn,ew,en,cn,nc,nu,nn,in,ni".split(","),_gridsopt:[],groupOps:[{op:"AND",text:"AND"},{op:"OR",text:"OR"}],groupButton:!0,ruleButtons:!0,direction:"ltr"},a.jgrid.filter,d||{});return this.each(function(){if(!this.filter){this.p=o;if(null===this.p.filter||void 0===this.p.filter)this.p.filter=
{groupOp:this.p.groupOps[0].op,rules:[],groups:[]};var d,n=this.p.columns.length,f,t=/msie/i.test(navigator.userAgent)&&!window.opera;if(this.p._gridsopt.length)for(d=0;d<this.p._gridsopt.length;d++)this.p.ops[d].description=this.p._gridsopt[d];this.p.initFilter=a.extend(!0,{},this.p.filter);if(n){for(d=0;d<n;d++)if(f=this.p.columns[d],f.stype?f.inputtype=f.stype:f.inputtype||(f.inputtype="text"),f.sorttype?f.searchtype=f.sorttype:f.searchtype||(f.searchtype="string"),void 0===f.hidden&&(f.hidden=
!1),f.label||(f.label=f.name),f.index&&(f.name=f.index),f.hasOwnProperty("searchoptions")||(f.searchoptions={}),!f.hasOwnProperty("searchrules"))f.searchrules={};this.p.showQuery&&a(this).append("<table class='queryresult ui-widget ui-widget-content' style='display:block;max-width:440px;border:0px none;' dir='"+this.p.direction+"'><tbody><tr><td class='query'></td></tr></tbody></table>");var r=function(g,k){var b=[!0,""];if(a.isFunction(k.searchrules))b=k.searchrules(g,k);else if(a.jgrid&&a.jgrid.checkValues)try{b=
a.jgrid.checkValues(g,-1,null,k.searchrules,k.label)}catch(c){}b&&b.length&&!1===b[0]&&(o.error=!b[0],o.errmsg=b[1])};this.onchange=function(){this.p.error=!1;this.p.errmsg="";return a.isFunction(this.p.onChange)?this.p.onChange.call(this,this.p):!1};this.reDraw=function(){a("table.group:first",this).remove();var g=this.createTableForGroup(o.filter,null);a(this).append(g);a.isFunction(this.p.afterRedraw)&&this.p.afterRedraw.call(this,this.p)};this.createTableForGroup=function(g,k){var b=this,c,e=
a("<table class='group ui-widget ui-widget-content' style='border:0px none;'><tbody></tbody></table>"),d="left";"rtl"==this.p.direction&&(d="right",e.attr("dir","rtl"));null===k&&e.append("<tr class='error' style='display:none;'><th colspan='5' class='ui-state-error' align='"+d+"'></th></tr>");var h=a("<tr></tr>");e.append(h);d=a("<th colspan='5' align='"+d+"'></th>");h.append(d);if(!0===this.p.ruleButtons){var i=a("<select class='opsel'></select>");d.append(i);var h="",f;for(c=0;c<o.groupOps.length;c++)f=
g.groupOp===b.p.groupOps[c].op?" selected='selected'":"",h+="<option value='"+b.p.groupOps[c].op+"'"+f+">"+b.p.groupOps[c].text+"</option>";i.append(h).bind("change",function(){g.groupOp=a(i).val();b.onchange()})}h="<span></span>";this.p.groupButton&&(h=a("<input type='button' value='+ {}' title='Add subgroup' class='add-group'/>"),h.bind("click",function(){if(g.groups===void 0)g.groups=[];g.groups.push({groupOp:o.groupOps[0].op,rules:[],groups:[]});b.reDraw();b.onchange();return false}));d.append(h);
if(!0===this.p.ruleButtons){var h=a("<input type='button' value='+' title='Add rule' class='add-rule ui-add'/>"),l;h.bind("click",function(){if(g.rules===void 0)g.rules=[];for(c=0;c<b.p.columns.length;c++){var a=typeof b.p.columns[c].search==="undefined"?true:b.p.columns[c].search,e=b.p.columns[c].hidden===true;if(b.p.columns[c].searchoptions.searchhidden===true&&a||a&&!e){l=b.p.columns[c];break}}g.rules.push({field:l.name,op:(l.searchoptions.sopt?l.searchoptions.sopt:b.p.sopt?b.p.sopt:l.searchtype===
"string"?b.p.stropts:b.p.numopts)[0],data:""});b.reDraw();return false});d.append(h)}null!==k&&(h=a("<input type='button' value='-' title='Delete group' class='delete-group'/>"),d.append(h),h.bind("click",function(){for(c=0;c<k.groups.length;c++)if(k.groups[c]===g){k.groups.splice(c,1);break}b.reDraw();b.onchange();return false}));if(void 0!==g.groups)for(c=0;c<g.groups.length;c++)d=a("<tr></tr>"),e.append(d),h=a("<td class='first'></td>"),d.append(h),h=a("<td colspan='4'></td>"),h.append(this.createTableForGroup(g.groups[c],
g)),d.append(h);void 0===g.groupOp&&(g.groupOp=b.p.groupOps[0].op);if(void 0!==g.rules)for(c=0;c<g.rules.length;c++)e.append(this.createTableRowForRule(g.rules[c],g));return e};this.createTableRowForRule=function(g,d){var b=this,c=a("<tr></tr>"),e,f,h,i,j="",l;c.append("<td class='first'></td>");var m=a("<td class='columns'></td>");c.append(m);var n=a("<select></select>"),p,q=[];m.append(n);n.bind("change",function(){g.field=a(n).val();h=a(this).parents("tr:first");for(e=0;e<b.p.columns.length;e++)if(b.p.columns[e].name===
g.field){i=b.p.columns[e];break}if(i){i.searchoptions.id=a.jgrid.randId();t&&"text"===i.inputtype&&!i.searchoptions.size&&(i.searchoptions.size=10);var c=a.jgrid.createEl(i.inputtype,i.searchoptions,"",!0,b.p.ajaxSelectOptions,!0);a(c).addClass("input-elm");f=i.searchoptions.sopt?i.searchoptions.sopt:b.p.sopt?b.p.sopt:"string"===i.searchtype?b.p.stropts:b.p.numopts;var d="",k=0;q=[];a.each(b.p.ops,function(){q.push(this.name)});for(e=0;e<f.length;e++)p=a.inArray(f[e],q),-1!==p&&(0===k&&(g.op=b.p.ops[p].name),
d+="<option value='"+b.p.ops[p].name+"'>"+b.p.ops[p].description+"</option>",k++);a(".selectopts",h).empty().append(d);a(".selectopts",h)[0].selectedIndex=0;a.browser.msie&&9>a.browser.version&&(d=parseInt(a("select.selectopts",h)[0].offsetWidth)+1,a(".selectopts",h).width(d),a(".selectopts",h).css("width","auto"));a(".data",h).empty().append(c);a(".input-elm",h).bind("change",function(c){var d=a(this).hasClass("ui-autocomplete-input")?200:0;setTimeout(function(){var d=c.target;g.data=d.nodeName.toUpperCase()===
"SPAN"&&i.searchoptions&&a.isFunction(i.searchoptions.custom_value)?i.searchoptions.custom_value(a(d).children(".customelement:first"),"get"):d.value;b.onchange()},d)});setTimeout(function(){g.data=a(c).val();b.onchange()},0)}});for(e=m=0;e<b.p.columns.length;e++){l="undefined"===typeof b.p.columns[e].search?!0:b.p.columns[e].search;var r=!0===b.p.columns[e].hidden;if(!0===b.p.columns[e].searchoptions.searchhidden&&l||l&&!r)l="",g.field===b.p.columns[e].name&&(l=" selected='selected'",m=e),j+="<option value='"+
b.p.columns[e].name+"'"+l+">"+b.p.columns[e].label+"</option>"}n.append(j);j=a("<td class='operators'></td>");c.append(j);i=o.columns[m];i.searchoptions.id=a.jgrid.randId();t&&"text"===i.inputtype&&!i.searchoptions.size&&(i.searchoptions.size=10);var m=a.jgrid.createEl(i.inputtype,i.searchoptions,g.data,!0,b.p.ajaxSelectOptions,!0),s=a("<select class='selectopts'></select>");j.append(s);s.bind("change",function(){g.op=a(s).val();h=a(this).parents("tr:first");var c=a(".input-elm",h)[0];if(g.op==="nu"||
g.op==="nn"){g.data="";c.value="";c.setAttribute("readonly","true");c.setAttribute("disabled","true")}else{c.removeAttribute("readonly");c.removeAttribute("disabled")}b.onchange()});f=i.searchoptions.sopt?i.searchoptions.sopt:b.p.sopt?b.p.sopt:"string"===i.searchtype?o.stropts:b.p.numopts;j="";a.each(b.p.ops,function(){q.push(this.name)});for(e=0;e<f.length;e++)p=a.inArray(f[e],q),-1!==p&&(l=g.op===b.p.ops[p].name?" selected='selected'":"",j+="<option value='"+b.p.ops[p].name+"'"+l+">"+b.p.ops[p].description+
"</option>");s.append(j);j=a("<td class='data'></td>");c.append(j);j.append(m);a(m).addClass("input-elm").bind("change",function(){g.data=i.inputtype==="custom"?i.searchoptions.custom_value(a(this).children(".customelement:first"),"get"):a(this).val();b.onchange()});j=a("<td></td>");c.append(j);!0===this.p.ruleButtons&&(m=a("<input type='button' value='-' title='Delete rule' class='delete-rule ui-del'/>"),j.append(m),m.bind("click",function(){for(e=0;e<d.rules.length;e++)if(d.rules[e]===g){d.rules.splice(e,
1);break}b.reDraw();b.onchange();return false}));return c};this.getStringForGroup=function(a){var d="(",b;if(void 0!==a.groups)for(b=0;b<a.groups.length;b++){1<d.length&&(d+=" "+a.groupOp+" ");try{d+=this.getStringForGroup(a.groups[b])}catch(c){alert(c)}}if(void 0!==a.rules)try{for(b=0;b<a.rules.length;b++)1<d.length&&(d+=" "+a.groupOp+" "),d+=this.getStringForRule(a.rules[b])}catch(e){alert(e)}d+=")";return"()"===d?"":d};this.getStringForRule=function(d){var f="",b="",c,e;for(c=0;c<this.p.ops.length;c++)if(this.p.ops[c].name===
d.op){f=this.p.ops[c].operator;b=this.p.ops[c].name;break}for(c=0;c<this.p.columns.length;c++)if(this.p.columns[c].name===d.field){e=this.p.columns[c];break}c=d.data;if("bw"===b||"bn"===b)c+="%";if("ew"===b||"en"===b)c="%"+c;if("cn"===b||"nc"===b)c="%"+c+"%";if("in"===b||"ni"===b)c=" ("+c+")";o.errorcheck&&r(d.data,e);return-1!==a.inArray(e.searchtype,["int","integer","float","number","currency"])||"nn"===b||"nu"===b?d.field+" "+f+" "+c:d.field+" "+f+' "'+c+'"'};this.resetFilter=function(){this.p.filter=
a.extend(!0,{},this.p.initFilter);this.reDraw();this.onchange()};this.hideError=function(){a("th.ui-state-error",this).html("");a("tr.error",this).hide()};this.showError=function(){a("th.ui-state-error",this).html(this.p.errmsg);a("tr.error",this).show()};this.toUserFriendlyString=function(){return this.getStringForGroup(o.filter)};this.toString=function(){function a(b){var c="(",e;if(void 0!==b.groups)for(e=0;e<b.groups.length;e++)1<c.length&&(c="OR"===b.groupOp?c+" || ":c+" && "),c+=a(b.groups[e]);
if(void 0!==b.rules)for(e=0;e<b.rules.length;e++){1<c.length&&(c="OR"===b.groupOp?c+" || ":c+" && ");var f=b.rules[e];if(d.p.errorcheck){for(var h=void 0,i=void 0,h=0;h<d.p.columns.length;h++)if(d.p.columns[h].name===f.field){i=d.p.columns[h];break}i&&r(f.data,i)}c+=f.op+"(item."+f.field+",'"+f.data+"')"}c+=")";return"()"===c?"":c}var d=this;return a(this.p.filter)};this.reDraw();if(this.p.showQuery)this.onchange();this.filter=!0}}})};a.extend(a.fn.jqFilter,{toSQLString:function(){var a="";this.each(function(){a=
this.toUserFriendlyString()});return a},filterData:function(){var a;this.each(function(){a=this.p.filter});return a},getParameter:function(a){return void 0!==a&&this.p.hasOwnProperty(a)?this.p[a]:this.p},resetFilter:function(){return this.each(function(){this.resetFilter()})},addFilter:function(a){"string"===typeof a&&(a=jQuery.jgrid.parse(a));this.each(function(){this.p.filter=a;this.reDraw();this.onchange()})}})})(jQuery);
(function(a){a.jgrid.inlineEdit=a.jgrid.inlineEdit||{};a.jgrid.extend({editRow:function(d,b,c,o,h,e,n,l,f){var j={},g=a.makeArray(arguments).slice(1);if("object"===a.type(g[0]))j=g[0];else if("undefined"!==typeof b&&(j.keys=b),a.isFunction(c)&&(j.oneditfunc=c),a.isFunction(o)&&(j.successfunc=o),"undefined"!==typeof h&&(j.url=h),"undefined"!==typeof e&&(j.extraparam=e),a.isFunction(n)&&(j.aftersavefunc=n),a.isFunction(l)&&(j.errorfunc=l),a.isFunction(f))j.afterrestorefunc=f;j=a.extend(!0,{keys:!1,
oneditfunc:null,successfunc:null,url:null,extraparam:{},aftersavefunc:null,errorfunc:null,afterrestorefunc:null,restoreAfterError:!0,mtype:"POST"},a.jgrid.inlineEdit,j);return this.each(function(){var b=this,f,c,g=0,h=null,l={},e,k;if(b.grid&&(e=a(b).jqGrid("getInd",d,!0),!1!==e&&"0"==(a(e).attr("editable")||"0")&&!a(e).hasClass("not-editable-row")))k=b.p.colModel,a('td[role="gridcell"]',e).each(function(e){f=k[e].name;var j=!0===b.p.treeGrid&&f==b.p.ExpandColumn;if(j)c=a("span:first",this).html();
else try{c=a.unformat.call(b,this,{rowId:d,colModel:k[e]},e)}catch(o){c=k[e].edittype&&"textarea"==k[e].edittype?a(this).text():a(this).html()}if("cb"!=f&&"subgrid"!=f&&"rn"!=f&&(b.p.autoencode&&(c=a.jgrid.htmlDecode(c)),l[f]=c,!0===k[e].editable)){null===h&&(h=e);j?a("span:first",this).html(""):a(this).html("");var t=a.extend({},k[e].editoptions||{},{id:d+"_"+f,name:f});k[e].edittype||(k[e].edittype="text");if("&nbsp;"==c||"&#160;"==c||1==c.length&&160==c.charCodeAt(0))c="";t=a.jgrid.createEl.call(b,
k[e].edittype,t,c,!0,a.extend({},a.jgrid.ajaxOptions,b.p.ajaxSelectOptions||{}));a(t).addClass("editable");j?a("span:first",this).append(t):a(this).append(t);"select"==k[e].edittype&&"undefined"!==typeof k[e].editoptions&&!0===k[e].editoptions.multiple&&"undefined"===typeof k[e].editoptions.dataUrl&&a.browser.msie&&a(t).width(a(t).width());g++}}),0<g&&(l.id=d,b.p.savedRow.push(l),a(e).attr("editable","1"),a("td:eq("+h+") input",e).focus(),!0===j.keys&&a(e).bind("keydown",function(c){if(27===c.keyCode){a(b).jqGrid("restoreRow",
d,j.afterrestorefunc);if(b.p._inlinenav)try{a(b).jqGrid("showAddEditButtons")}catch(f){}return!1}if(13===c.keyCode){if("TEXTAREA"==c.target.tagName)return!0;if(a(b).jqGrid("saveRow",d,j)&&b.p._inlinenav)try{a(b).jqGrid("showAddEditButtons")}catch(e){}return!1}}),a(b).triggerHandler("jqGridInlineEditRow",[d,j]),a.isFunction(j.oneditfunc)&&j.oneditfunc.call(b,d))})},saveRow:function(d,b,c,o,h,e,n){var l=a.makeArray(arguments).slice(1),f={};if("object"===a.type(l[0]))f=l[0];else if(a.isFunction(b)&&
(f.successfunc=b),"undefined"!==typeof c&&(f.url=c),"undefined"!==typeof o&&(f.extraparam=o),a.isFunction(h)&&(f.aftersavefunc=h),a.isFunction(e)&&(f.errorfunc=e),a.isFunction(n))f.afterrestorefunc=n;var f=a.extend(!0,{successfunc:null,url:null,extraparam:{},aftersavefunc:null,errorfunc:null,afterrestorefunc:null,restoreAfterError:!0,mtype:"POST"},a.jgrid.inlineEdit,f),j=!1,g=this[0],m,i={},v={},r={},u,s,q;if(!g.grid)return j;q=a(g).jqGrid("getInd",d,!0);if(!1===q)return j;l=a(q).attr("editable");
f.url=f.url?f.url:g.p.editurl;if("1"===l){var k;a('td[role="gridcell"]',q).each(function(b){k=g.p.colModel[b];m=k.name;if("cb"!=m&&"subgrid"!=m&&!0===k.editable&&"rn"!=m&&!a(this).hasClass("not-editable-cell")){switch(k.edittype){case "checkbox":var c=["Yes","No"];k.editoptions&&(c=k.editoptions.value.split(":"));i[m]=a("input",this).is(":checked")?c[0]:c[1];break;case "text":case "password":case "textarea":case "button":i[m]=a("input, textarea",this).val();break;case "select":if(k.editoptions.multiple){var c=
a("select",this),d=[];i[m]=a(c).val();i[m]=i[m]?i[m].join(","):"";a("select option:selected",this).each(function(b,c){d[b]=a(c).text()});v[m]=d.join(",")}else i[m]=a("select option:selected",this).val(),v[m]=a("select option:selected",this).text();k.formatter&&"select"==k.formatter&&(v={});break;case "custom":try{if(k.editoptions&&a.isFunction(k.editoptions.custom_value)){if(i[m]=k.editoptions.custom_value.call(g,a(".customelement",this),"get"),void 0===i[m])throw"e2";}else throw"e1";}catch(e){"e1"==
e&&a.jgrid.info_dialog(a.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.nodefined,a.jgrid.edit.bClose),"e2"==e?a.jgrid.info_dialog(a.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.novalue,a.jgrid.edit.bClose):a.jgrid.info_dialog(a.jgrid.errors.errcap,e.message,a.jgrid.edit.bClose)}}s=a.jgrid.checkValues(i[m],b,g);if(!1===s[0])return s[1]=i[m]+" "+s[1],!1;g.p.autoencode&&(i[m]=a.jgrid.htmlEncode(i[m]));"clientArray"!==f.url&&k.editoptions&&!0===k.editoptions.NullIfEmpty&&
""===i[m]&&(r[m]="null")}});if(!1===s[0]){try{var p=a.jgrid.findPos(a("#"+a.jgrid.jqID(d),g.grid.bDiv)[0]);a.jgrid.info_dialog(a.jgrid.errors.errcap,s[1],a.jgrid.edit.bClose,{left:p[0],top:p[1]})}catch(x){alert(s[1])}return j}var w,l=g.p.prmNames;w=l.oper;p=l.id;i&&(i[w]=l.editoper,i[p]=d,"undefined"==typeof g.p.inlineData&&(g.p.inlineData={}),i=a.extend({},i,g.p.inlineData,f.extraparam));if("clientArray"==f.url){i=a.extend({},i,v);g.p.autoencode&&a.each(i,function(b,c){i[b]=a.jgrid.htmlDecode(c)});
p=a(g).jqGrid("setRowData",d,i);a(q).attr("editable","0");for(l=0;l<g.p.savedRow.length;l++)if(g.p.savedRow[l].id==d){u=l;break}0<=u&&g.p.savedRow.splice(u,1);a(g).triggerHandler("jqGridInlineAfterSaveRow",[d,p,i,f]);a.isFunction(f.aftersavefunc)&&f.aftersavefunc.call(g,d,p);j=!0;a(q).unbind("keydown")}else a("#lui_"+a.jgrid.jqID(g.p.id)).show(),r=a.extend({},i,r),r[p]=a.jgrid.stripPref(g.p.idPrefix,r[p]),a.ajax(a.extend({url:f.url,data:a.isFunction(g.p.serializeRowData)?g.p.serializeRowData.call(g,
r):r,type:f.mtype,async:!1,complete:function(b,c){a("#lui_"+a.jgrid.jqID(g.p.id)).hide();if(c==="success"){var e=true,h;h=a(g).triggerHandler("jqGridInlineSuccessSaveRow",[b,d,f]);a.isArray(h)||(h=[true,i]);h[0]&&a.isFunction(f.successfunc)&&(h=f.successfunc.call(g,b));if(a.isArray(h)){e=h[0];i=h[1]?h[1]:i}else e=h;if(e===true){g.p.autoencode&&a.each(i,function(b,c){i[b]=a.jgrid.htmlDecode(c)});i=a.extend({},i,v);a(g).jqGrid("setRowData",d,i);a(q).attr("editable","0");for(e=0;e<g.p.savedRow.length;e++)if(g.p.savedRow[e].id==
d){u=e;break}u>=0&&g.p.savedRow.splice(u,1);a(g).triggerHandler("jqGridInlineAfterSaveRow",[d,b,i,f]);a.isFunction(f.aftersavefunc)&&f.aftersavefunc.call(g,d,b);j=true;a(q).unbind("keydown")}else{a(g).triggerHandler("jqGridInlineErrorSaveRow",[d,b,c,null,f]);a.isFunction(f.errorfunc)&&f.errorfunc.call(g,d,b,c,null);f.restoreAfterError===true&&a(g).jqGrid("restoreRow",d,f.afterrestorefunc)}}},error:function(b,c,e){a("#lui_"+a.jgrid.jqID(g.p.id)).hide();a(g).triggerHandler("jqGridInlineErrorSaveRow",
[d,b,c,e,f]);if(a.isFunction(f.errorfunc))f.errorfunc.call(g,d,b,c,e);else try{a.jgrid.info_dialog(a.jgrid.errors.errcap,'<div class="ui-state-error">'+b.responseText+"</div>",a.jgrid.edit.bClose,{buttonalign:"right"})}catch(h){alert(b.responseText)}f.restoreAfterError===true&&a(g).jqGrid("restoreRow",d,f.afterrestorefunc)}},a.jgrid.ajaxOptions,g.p.ajaxRowOptions||{}))}return j},restoreRow:function(d,b){var c=a.makeArray(arguments).slice(1),o={};"object"===a.type(c[0])?o=c[0]:a.isFunction(b)&&(o.afterrestorefunc=
b);o=a.extend(!0,a.jgrid.inlineEdit,o);return this.each(function(){var b=this,c,n,l={};if(b.grid){n=a(b).jqGrid("getInd",d,true);if(n!==false){for(var f=0;f<b.p.savedRow.length;f++)if(b.p.savedRow[f].id==d){c=f;break}if(c>=0){if(a.isFunction(a.fn.datepicker))try{a("input.hasDatepicker","#"+a.jgrid.jqID(n.id)).datepicker("hide")}catch(j){}a.each(b.p.colModel,function(){this.editable===true&&this.name in b.p.savedRow[c]&&(l[this.name]=b.p.savedRow[c][this.name])});a(b).jqGrid("setRowData",d,l);a(n).attr("editable",
"0").unbind("keydown");b.p.savedRow.splice(c,1);a("#"+a.jgrid.jqID(d),"#"+a.jgrid.jqID(b.p.id)).hasClass("jqgrid-new-row")&&setTimeout(function(){a(b).jqGrid("delRowData",d)},0)}a(b).triggerHandler("jqGridInlineAfterRestoreRow",[d]);a.isFunction(o.afterrestorefunc)&&o.afterrestorefunc.call(b,d)}}})},addRow:function(d){d=a.extend(!0,{rowID:"new_row",initdata:{},position:"first",useDefValues:!0,useFormatter:!1,addRowParams:{extraparam:{}}},d||{});return this.each(function(){if(this.grid){var b=this;
!0===d.useDefValues&&a(b.p.colModel).each(function(){if(this.editoptions&&this.editoptions.defaultValue){var c=this.editoptions.defaultValue,c=a.isFunction(c)?c.call(b):c;d.initdata[this.name]=c}});a(b).jqGrid("addRowData",d.rowID,d.initdata,d.position);d.rowID=b.p.idPrefix+d.rowID;a("#"+a.jgrid.jqID(d.rowID),"#"+a.jgrid.jqID(b.p.id)).addClass("jqgrid-new-row");if(d.useFormatter)a("#"+a.jgrid.jqID(d.rowID)+" .ui-inline-edit","#"+a.jgrid.jqID(b.p.id)).click();else{var c=b.p.prmNames;d.addRowParams.extraparam[c.oper]=
c.addoper;a(b).jqGrid("editRow",d.rowID,d.addRowParams);a(b).jqGrid("setSelection",d.rowID)}}})},inlineNav:function(d,b){b=a.extend({edit:!0,editicon:"ui-icon-pencil",add:!0,addicon:"ui-icon-plus",save:!0,saveicon:"ui-icon-disk",cancel:!0,cancelicon:"ui-icon-cancel",addParams:{useFormatter:!1,rowID:"new_row"},editParams:{},restoreAfterSelect:!0},a.jgrid.nav,b||{});return this.each(function(){if(this.grid){var c=this,o,h=a.jgrid.jqID(c.p.id);c.p._inlinenav=!0;if(!0===b.addParams.useFormatter){var e=
c.p.colModel,n;for(n=0;n<e.length;n++)if(e[n].formatter&&"actions"===e[n].formatter){e[n].formatoptions&&(e=a.extend({keys:!1,onEdit:null,onSuccess:null,afterSave:null,onError:null,afterRestore:null,extraparam:{},url:null},e[n].formatoptions),b.addParams.addRowParams={keys:e.keys,oneditfunc:e.onEdit,successfunc:e.onSuccess,url:e.url,extraparam:e.extraparam,aftersavefunc:e.afterSavef,errorfunc:e.onError,afterrestorefunc:e.afterRestore});break}}b.add&&a(c).jqGrid("navButtonAdd",d,{caption:b.addtext,
title:b.addtitle,buttonicon:b.addicon,id:c.p.id+"_iladd",onClickButton:function(){a(c).jqGrid("addRow",b.addParams);b.addParams.useFormatter||(a("#"+h+"_ilsave").removeClass("ui-state-disabled"),a("#"+h+"_ilcancel").removeClass("ui-state-disabled"),a("#"+h+"_iladd").addClass("ui-state-disabled"),a("#"+h+"_iledit").addClass("ui-state-disabled"))}});b.edit&&a(c).jqGrid("navButtonAdd",d,{caption:b.edittext,title:b.edittitle,buttonicon:b.editicon,id:c.p.id+"_iledit",onClickButton:function(){var d=a(c).jqGrid("getGridParam",
"selrow");d?(a(c).jqGrid("editRow",d,b.editParams),a("#"+h+"_ilsave").removeClass("ui-state-disabled"),a("#"+h+"_ilcancel").removeClass("ui-state-disabled"),a("#"+h+"_iladd").addClass("ui-state-disabled"),a("#"+h+"_iledit").addClass("ui-state-disabled")):(a.jgrid.viewModal("#alertmod",{gbox:"#gbox_"+h,jqm:!0}),a("#jqg_alrt").focus())}});b.save&&(a(c).jqGrid("navButtonAdd",d,{caption:b.savetext||"",title:b.savetitle||"Save row",buttonicon:b.saveicon,id:c.p.id+"_ilsave",onClickButton:function(){var d=
c.p.savedRow[0].id;if(d){var e=c.p.prmNames,j=e.oper;b.editParams.extraparam||(b.editParams.extraparam={});b.editParams.extraparam[j]=a("#"+a.jgrid.jqID(d),"#"+h).hasClass("jqgrid-new-row")?e.addoper:e.editoper;a(c).jqGrid("saveRow",d,b.editParams)&&a(c).jqGrid("showAddEditButtons")}else a.jgrid.viewModal("#alertmod",{gbox:"#gbox_"+h,jqm:!0}),a("#jqg_alrt").focus()}}),a("#"+h+"_ilsave").addClass("ui-state-disabled"));b.cancel&&(a(c).jqGrid("navButtonAdd",d,{caption:b.canceltext||"",title:b.canceltitle||
"Cancel row editing",buttonicon:b.cancelicon,id:c.p.id+"_ilcancel",onClickButton:function(){var d=c.p.savedRow[0].id;if(d){a(c).jqGrid("restoreRow",d,b.editParams);a(c).jqGrid("showAddEditButtons")}else{a.jgrid.viewModal("#alertmod",{gbox:"#gbox_"+h,jqm:true});a("#jqg_alrt").focus()}}}),a("#"+h+"_ilcancel").addClass("ui-state-disabled"));!0===b.restoreAfterSelect&&(o=a.isFunction(c.p.beforeSelectRow)?c.p.beforeSelectRow:!1,c.p.beforeSelectRow=function(d,e){var h=true;if(c.p.savedRow.length>0&&c.p._inlinenav===
true&&d!==c.p.selrow&&c.p.selrow!==null){c.p.selrow==b.addParams.rowID?a(c).jqGrid("delRowData",c.p.selrow):a(c).jqGrid("restoreRow",c.p.selrow,b.editParams);a(c).jqGrid("showAddEditButtons")}o&&(h=o.call(c,d,e));return h})}})},showAddEditButtons:function(){return this.each(function(){if(this.grid){var d=a.jgrid.jqID(this.p.id);a("#"+d+"_ilsave").addClass("ui-state-disabled");a("#"+d+"_ilcancel").addClass("ui-state-disabled");a("#"+d+"_iladd").removeClass("ui-state-disabled");a("#"+d+"_iledit").removeClass("ui-state-disabled")}})}})})(jQuery);
(function(b){b.jgrid.extend({editCell:function(d,f,a){return this.each(function(){var c=this,g,e,h,i;if(c.grid&&!0===c.p.cellEdit){f=parseInt(f,10);c.p.selrow=c.rows[d].id;c.p.knv||b(c).jqGrid("GridNav");if(0<c.p.savedRow.length){if(!0===a&&d==c.p.iRow&&f==c.p.iCol)return;b(c).jqGrid("saveCell",c.p.savedRow[0].id,c.p.savedRow[0].ic)}else window.setTimeout(function(){b("#"+b.jgrid.jqID(c.p.knv)).attr("tabindex","-1").focus()},0);i=c.p.colModel[f];g=i.name;if(!("subgrid"==g||"cb"==g||"rn"==g)){h=b("td:eq("+
f+")",c.rows[d]);if(!0===i.editable&&!0===a&&!h.hasClass("not-editable-cell")){0<=parseInt(c.p.iCol,10)&&0<=parseInt(c.p.iRow,10)&&(b("td:eq("+c.p.iCol+")",c.rows[c.p.iRow]).removeClass("edit-cell ui-state-highlight"),b(c.rows[c.p.iRow]).removeClass("selected-row ui-state-hover"));b(h).addClass("edit-cell ui-state-highlight");b(c.rows[d]).addClass("selected-row ui-state-hover");try{e=b.unformat.call(c,h,{rowId:c.rows[d].id,colModel:i},f)}catch(k){e=i.edittype&&"textarea"==i.edittype?b(h).text():b(h).html()}c.p.autoencode&&
(e=b.jgrid.htmlDecode(e));i.edittype||(i.edittype="text");c.p.savedRow.push({id:d,ic:f,name:g,v:e});if("&nbsp;"===e||"&#160;"===e||1===e.length&&160===e.charCodeAt(0))e="";if(b.isFunction(c.p.formatCell)){var j=c.p.formatCell.call(c,c.rows[d].id,g,e,d,f);void 0!==j&&(e=j)}var j=b.extend({},i.editoptions||{},{id:d+"_"+g,name:g}),n=b.jgrid.createEl.call(c,i.edittype,j,e,!0,b.extend({},b.jgrid.ajaxOptions,c.p.ajaxSelectOptions||{}));b(c).triggerHandler("jqGridBeforeEditCell",[c.rows[d].id,g,e,d,f]);
b.isFunction(c.p.beforeEditCell)&&c.p.beforeEditCell.call(c,c.rows[d].id,g,e,d,f);b(h).html("").append(n).attr("tabindex","0");window.setTimeout(function(){b(n).focus()},0);b("input, select, textarea",h).bind("keydown",function(a){a.keyCode===27&&(b("input.hasDatepicker",h).length>0?b(".ui-datepicker").is(":hidden")?b(c).jqGrid("restoreCell",d,f):b("input.hasDatepicker",h).datepicker("hide"):b(c).jqGrid("restoreCell",d,f));if(a.keyCode===13){b(c).jqGrid("saveCell",d,f);return false}if(a.keyCode===
9){if(c.grid.hDiv.loading)return false;a.shiftKey?b(c).jqGrid("prevCell",d,f):b(c).jqGrid("nextCell",d,f)}a.stopPropagation()});b(c).triggerHandler("jqGridAfterEditCell",[c.rows[d].id,g,e,d,f]);b.isFunction(c.p.afterEditCell)&&c.p.afterEditCell.call(c,c.rows[d].id,g,e,d,f)}else 0<=parseInt(c.p.iCol,10)&&0<=parseInt(c.p.iRow,10)&&(b("td:eq("+c.p.iCol+")",c.rows[c.p.iRow]).removeClass("edit-cell ui-state-highlight"),b(c.rows[c.p.iRow]).removeClass("selected-row ui-state-hover")),h.addClass("edit-cell ui-state-highlight"),
b(c.rows[d]).addClass("selected-row ui-state-hover"),e=h.html().replace(/\&#160\;/ig,""),b(c).triggerHandler("jqGridSelectCell",[c.rows[d].id,g,e,d,f]),b.isFunction(c.p.onSelectCell)&&c.p.onSelectCell.call(c,c.rows[d].id,g,e,d,f);c.p.iCol=f;c.p.iRow=d}}})},saveCell:function(d,f){return this.each(function(){var a=this,c;if(a.grid&&!0===a.p.cellEdit){c=1<=a.p.savedRow.length?0:null;if(null!==c){var g=b("td:eq("+f+")",a.rows[d]),e,h,i=a.p.colModel[f],k=i.name,j=b.jgrid.jqID(k);switch(i.edittype){case "select":if(i.editoptions.multiple){var j=
b("#"+d+"_"+j,a.rows[d]),n=[];(e=b(j).val())?e.join(","):e="";b("option:selected",j).each(function(a,c){n[a]=b(c).text()});h=n.join(",")}else e=b("#"+d+"_"+j+" option:selected",a.rows[d]).val(),h=b("#"+d+"_"+j+" option:selected",a.rows[d]).text();i.formatter&&(h=e);break;case "checkbox":var l=["Yes","No"];i.editoptions&&(l=i.editoptions.value.split(":"));h=e=b("#"+d+"_"+j,a.rows[d]).is(":checked")?l[0]:l[1];break;case "password":case "text":case "textarea":case "button":h=e=b("#"+d+"_"+j,a.rows[d]).val();
break;case "custom":try{if(i.editoptions&&b.isFunction(i.editoptions.custom_value)){e=i.editoptions.custom_value.call(a,b(".customelement",g),"get");if(void 0===e)throw"e2";h=e}else throw"e1";}catch(o){"e1"==o&&b.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose),"e2"==o?b.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):b.jgrid.info_dialog(jQuery.jgrid.errors.errcap,
o.message,jQuery.jgrid.edit.bClose)}}if(h!==a.p.savedRow[c].v){if(c=b(a).triggerHandler("jqGridBeforeSaveCell",[a.rows[d].id,k,e,d,f]))h=e=c;if(b.isFunction(a.p.beforeSaveCell)&&(c=a.p.beforeSaveCell.call(a,a.rows[d].id,k,e,d,f)))h=e=c;var p=b.jgrid.checkValues(e,f,a);if(!0===p[0]){c=b(a).triggerHandler("jqGridBeforeSubmitCell",[a.rows[d].id,k,e,d,f])||{};b.isFunction(a.p.beforeSubmitCell)&&((c=a.p.beforeSubmitCell.call(a,a.rows[d].id,k,e,d,f))||(c={}));0<b("input.hasDatepicker",g).length&&b("input.hasDatepicker",
g).datepicker("hide");if("remote"==a.p.cellsubmit)if(a.p.cellurl){var m={};a.p.autoencode&&(e=b.jgrid.htmlEncode(e));m[k]=e;l=a.p.prmNames;i=l.id;j=l.oper;m[i]=b.jgrid.stripPref(a.p.idPrefix,a.rows[d].id);m[j]=l.editoper;m=b.extend(c,m);b("#lui_"+b.jgrid.jqID(a.p.id)).show();a.grid.hDiv.loading=!0;b.ajax(b.extend({url:a.p.cellurl,data:b.isFunction(a.p.serializeCellData)?a.p.serializeCellData.call(a,m):m,type:"POST",complete:function(c,i){b("#lui_"+a.p.id).hide();a.grid.hDiv.loading=false;if(i=="success"){var j=
b(a).triggerHandler("jqGridAfterSubmitCell",[a,c,m.id,k,e,d,f])||[true,""];j[0]===true&&b.isFunction(a.p.afterSubmitCell)&&(j=a.p.afterSubmitCell.call(a,c,m.id,k,e,d,f));if(j[0]===true){b(g).empty();b(a).jqGrid("setCell",a.rows[d].id,f,h,false,false,true);b(g).addClass("dirty-cell");b(a.rows[d]).addClass("edited");b(a).triggerHandler("jqGridAfterSaveCell",[a.rows[d].id,k,e,d,f]);b.isFunction(a.p.afterSaveCell)&&a.p.afterSaveCell.call(a,a.rows[d].id,k,e,d,f);a.p.savedRow.splice(0,1)}else{b.jgrid.info_dialog(b.jgrid.errors.errcap,
j[1],b.jgrid.edit.bClose);b(a).jqGrid("restoreCell",d,f)}}},error:function(c,e,h){b("#lui_"+b.jgrid.jqID(a.p.id)).hide();a.grid.hDiv.loading=false;b(a).triggerHandler("jqGridErrorCell",[c,e,h]);b.isFunction(a.p.errorCell)?a.p.errorCell.call(a,c,e,h):b.jgrid.info_dialog(b.jgrid.errors.errcap,c.status+" : "+c.statusText+"<br/>"+e,b.jgrid.edit.bClose);b(a).jqGrid("restoreCell",d,f)}},b.jgrid.ajaxOptions,a.p.ajaxCellOptions||{}))}else try{b.jgrid.info_dialog(b.jgrid.errors.errcap,b.jgrid.errors.nourl,
b.jgrid.edit.bClose),b(a).jqGrid("restoreCell",d,f)}catch(q){}"clientArray"==a.p.cellsubmit&&(b(g).empty(),b(a).jqGrid("setCell",a.rows[d].id,f,h,!1,!1,!0),b(g).addClass("dirty-cell"),b(a.rows[d]).addClass("edited"),b(a).triggerHandler("jqGridAfterSaveCell",[a.rows[d].id,k,e,d,f]),b.isFunction(a.p.afterSaveCell)&&a.p.afterSaveCell.call(a,a.rows[d].id,k,e,d,f),a.p.savedRow.splice(0,1))}else try{window.setTimeout(function(){b.jgrid.info_dialog(b.jgrid.errors.errcap,e+" "+p[1],b.jgrid.edit.bClose)},
100),b(a).jqGrid("restoreCell",d,f)}catch(r){}}else b(a).jqGrid("restoreCell",d,f)}b.browser.opera?b("#"+b.jgrid.jqID(a.p.knv)).attr("tabindex","-1").focus():window.setTimeout(function(){b("#"+b.jgrid.jqID(a.p.knv)).attr("tabindex","-1").focus()},0)}})},restoreCell:function(d,f){return this.each(function(){var a=this,c;if(a.grid&&!0===a.p.cellEdit){c=1<=a.p.savedRow.length?0:null;if(null!==c){var g=b("td:eq("+f+")",a.rows[d]);if(b.isFunction(b.fn.datepicker))try{b("input.hasDatepicker",g).datepicker("hide")}catch(e){}b(g).empty().attr("tabindex",
"-1");b(a).jqGrid("setCell",a.rows[d].id,f,a.p.savedRow[c].v,!1,!1,!0);b(a).triggerHandler("jqGridAfterRestoreCell",[a.rows[d].id,a.p.savedRow[c].v,d,f]);b.isFunction(a.p.afterRestoreCell)&&a.p.afterRestoreCell.call(a,a.rows[d].id,a.p.savedRow[c].v,d,f);a.p.savedRow.splice(0,1)}window.setTimeout(function(){b("#"+a.p.knv).attr("tabindex","-1").focus()},0)}})},nextCell:function(d,f){return this.each(function(){var a=!1;if(this.grid&&!0===this.p.cellEdit){for(var c=f+1;c<this.p.colModel.length;c++)if(!0===
this.p.colModel[c].editable){a=c;break}!1!==a?b(this).jqGrid("editCell",d,a,!0):0<this.p.savedRow.length&&b(this).jqGrid("saveCell",d,f)}})},prevCell:function(d,f){return this.each(function(){var a=!1;if(this.grid&&!0===this.p.cellEdit){for(var c=f-1;0<=c;c--)if(!0===this.p.colModel[c].editable){a=c;break}!1!==a?b(this).jqGrid("editCell",d,a,!0):0<this.p.savedRow.length&&b(this).jqGrid("saveCell",d,f)}})},GridNav:function(){return this.each(function(){function d(c,d,e){if("v"==e.substr(0,1)){var f=
b(a.grid.bDiv)[0].clientHeight,g=b(a.grid.bDiv)[0].scrollTop,l=a.rows[c].offsetTop+a.rows[c].clientHeight,o=a.rows[c].offsetTop;"vd"==e&&l>=f&&(b(a.grid.bDiv)[0].scrollTop=b(a.grid.bDiv)[0].scrollTop+a.rows[c].clientHeight);"vu"==e&&o<g&&(b(a.grid.bDiv)[0].scrollTop=b(a.grid.bDiv)[0].scrollTop-a.rows[c].clientHeight)}"h"==e&&(e=b(a.grid.bDiv)[0].clientWidth,f=b(a.grid.bDiv)[0].scrollLeft,g=a.rows[c].cells[d].offsetLeft,a.rows[c].cells[d].offsetLeft+a.rows[c].cells[d].clientWidth>=e+parseInt(f,10)?
b(a.grid.bDiv)[0].scrollLeft=b(a.grid.bDiv)[0].scrollLeft+a.rows[c].cells[d].clientWidth:g<f&&(b(a.grid.bDiv)[0].scrollLeft=b(a.grid.bDiv)[0].scrollLeft-a.rows[c].cells[d].clientWidth))}function f(b,c){var d,e;if("lft"==c){d=b+1;for(e=b;0<=e;e--)if(!0!==a.p.colModel[e].hidden){d=e;break}}if("rgt"==c){d=b-1;for(e=b;e<a.p.colModel.length;e++)if(!0!==a.p.colModel[e].hidden){d=e;break}}return d}var a=this;if(a.grid&&!0===a.p.cellEdit){a.p.knv=a.p.id+"_kn";var c=b("<span style='width:0px;height:0px;background-color:black;' tabindex='0'><span tabindex='-1' style='width:0px;height:0px;background-color:grey' id='"+
a.p.knv+"'></span></span>"),g,e;b(c).insertBefore(a.grid.cDiv);b("#"+a.p.knv).focus().keydown(function(c){e=c.keyCode;"rtl"==a.p.direction&&(37===e?e=39:39===e&&(e=37));switch(e){case 38:0<a.p.iRow-1&&(d(a.p.iRow-1,a.p.iCol,"vu"),b(a).jqGrid("editCell",a.p.iRow-1,a.p.iCol,!1));break;case 40:a.p.iRow+1<=a.rows.length-1&&(d(a.p.iRow+1,a.p.iCol,"vd"),b(a).jqGrid("editCell",a.p.iRow+1,a.p.iCol,!1));break;case 37:0<=a.p.iCol-1&&(g=f(a.p.iCol-1,"lft"),d(a.p.iRow,g,"h"),b(a).jqGrid("editCell",a.p.iRow,g,
!1));break;case 39:a.p.iCol+1<=a.p.colModel.length-1&&(g=f(a.p.iCol+1,"rgt"),d(a.p.iRow,g,"h"),b(a).jqGrid("editCell",a.p.iRow,g,!1));break;case 13:0<=parseInt(a.p.iCol,10)&&0<=parseInt(a.p.iRow,10)&&b(a).jqGrid("editCell",a.p.iRow,a.p.iCol,!0);break;default:return!0}return!1})}})},getChangedCells:function(d){var f=[];d||(d="all");this.each(function(){var a=this,c;a.grid&&!0===a.p.cellEdit&&b(a.rows).each(function(g){var e={};b(this).hasClass("edited")&&(b("td",this).each(function(f){c=a.p.colModel[f].name;
if("cb"!==c&&"subgrid"!==c)if("dirty"==d){if(b(this).hasClass("dirty-cell"))try{e[c]=b.unformat.call(a,this,{rowId:a.rows[g].id,colModel:a.p.colModel[f]},f)}catch(i){e[c]=b.jgrid.htmlDecode(b(this).html())}}else try{e[c]=b.unformat.call(a,this,{rowId:a.rows[g].id,colModel:a.p.colModel[f]},f)}catch(k){e[c]=b.jgrid.htmlDecode(b(this).html())}}),e.id=this.id,f.push(e))})});return f}})})(jQuery);
(function(b){b.fn.jqm=function(a){var k={overlay:50,closeoverlay:!0,overlayClass:"jqmOverlay",closeClass:"jqmClose",trigger:".jqModal",ajax:d,ajaxText:"",target:d,modal:d,toTop:d,onShow:d,onHide:d,onLoad:d};return this.each(function(){if(this._jqm)return i[this._jqm].c=b.extend({},i[this._jqm].c,a);g++;this._jqm=g;i[g]={c:b.extend(k,b.jqm.params,a),a:d,w:b(this).addClass("jqmID"+g),s:g};k.trigger&&b(this).jqmAddTrigger(k.trigger)})};b.fn.jqmAddClose=function(a){return n(this,a,"jqmHide")};b.fn.jqmAddTrigger=
function(a){return n(this,a,"jqmShow")};b.fn.jqmShow=function(a){return this.each(function(){b.jqm.open(this._jqm,a)})};b.fn.jqmHide=function(a){return this.each(function(){b.jqm.close(this._jqm,a)})};b.jqm={hash:{},open:function(a,k){var c=i[a],e=c.c,l="."+e.closeClass,h=parseInt(c.w.css("z-index")),h=0<h?h:3E3,f=b("<div></div>").css({height:"100%",width:"100%",position:"fixed",left:0,top:0,"z-index":h-1,opacity:e.overlay/100});if(c.a)return d;c.t=k;c.a=!0;c.w.css("z-index",h);e.modal?(j[0]||setTimeout(function(){o("bind")},
1),j.push(a)):0<e.overlay?e.closeoverlay&&c.w.jqmAddClose(f):f=d;c.o=f?f.addClass(e.overlayClass).prependTo("body"):d;if(p&&(b("html,body").css({height:"100%",width:"100%"}),f)){var f=f.css({position:"absolute"})[0],g;for(g in{Top:1,Left:1})f.style.setExpression(g.toLowerCase(),"(_=(document.documentElement.scroll"+g+" || document.body.scroll"+g+"))+'px'")}e.ajax?(h=e.target||c.w,f=e.ajax,h="string"==typeof h?b(h,c.w):b(h),f="@"==f.substr(0,1)?b(k).attr(f.substring(1)):f,h.html(e.ajaxText).load(f,
function(){e.onLoad&&e.onLoad.call(this,c);l&&c.w.jqmAddClose(b(l,c.w));q(c)})):l&&c.w.jqmAddClose(b(l,c.w));e.toTop&&c.o&&c.w.before('<span id="jqmP'+c.w[0]._jqm+'"></span>').insertAfter(c.o);e.onShow?e.onShow(c):c.w.show();q(c);return d},close:function(a){a=i[a];if(!a.a)return d;a.a=d;j[0]&&(j.pop(),j[0]||o("unbind"));a.c.toTop&&a.o&&b("#jqmP"+a.w[0]._jqm).after(a.w).remove();if(a.c.onHide)a.c.onHide(a);else a.w.hide(),a.o&&a.o.remove();return d},params:{}};var g=0,i=b.jqm.hash,j=[],p=b.browser.msie&&
"6.0"==b.browser.version,d=!1,q=function(a){var d=b('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0});p&&(a.o?a.o.html('<p style="width:100%;height:100%"/>').prepend(d):b("iframe.jqm",a.w)[0]||a.w.prepend(d));r(a)},r=function(a){try{b(":input:visible",a.w)[0].focus()}catch(d){}},o=function(a){b(document)[a]("keypress",m)[a]("keydown",m)[a]("mousedown",m)},m=function(a){var d=i[j[j.length-1]];(a=!b(a.target).parents(".jqmID"+d.s)[0])&&r(d);return!a},n=function(a,
g,c){return a.each(function(){var a=this._jqm;b(g).each(function(){this[c]||(this[c]=[],b(this).click(function(){for(var a in{jqmShow:1,jqmHide:1})for(var b in this[a])if(i[this[a][b]])i[this[a][b]].w[a](this);return d}));this[c].push(a)})})}})(jQuery);
(function(b){b.fn.jqDrag=function(a){return h(this,a,"d")};b.fn.jqResize=function(a,b){return h(this,a,"r",b)};b.jqDnR={dnr:{},e:0,drag:function(a){"d"==d.k?e.css({left:d.X+a.pageX-d.pX,top:d.Y+a.pageY-d.pY}):(e.css({width:Math.max(a.pageX-d.pX+d.W,0),height:Math.max(a.pageY-d.pY+d.H,0)}),f&&g.css({width:Math.max(a.pageX-f.pX+f.W,0),height:Math.max(a.pageY-f.pY+f.H,0)}));return!1},stop:function(){b(document).unbind("mousemove",c.drag).unbind("mouseup",c.stop)}};var c=b.jqDnR,d=c.dnr,e=c.e,g,f,h=function(a,
c,h,l){return a.each(function(){c=c?b(c,a):a;c.bind("mousedown",{e:a,k:h},function(a){var c=a.data,i={};e=c.e;g=l?b(l):!1;if("relative"!=e.css("position"))try{e.position(i)}catch(h){}d={X:i.left||j("left")||0,Y:i.top||j("top")||0,W:j("width")||e[0].scrollWidth||0,H:j("height")||e[0].scrollHeight||0,pX:a.pageX,pY:a.pageY,k:c.k};f=g&&"d"!=c.k?{X:i.left||k("left")||0,Y:i.top||k("top")||0,W:g[0].offsetWidth||k("width")||0,H:g[0].offsetHeight||k("height")||0,pX:a.pageX,pY:a.pageY,k:c.k}:!1;if(b("input.hasDatepicker",
e[0])[0])try{b("input.hasDatepicker",e[0]).datepicker("hide")}catch(m){}b(document).mousemove(b.jqDnR.drag).mouseup(b.jqDnR.stop);return!1})})},j=function(a){return parseInt(e.css(a),10)||!1},k=function(a){return parseInt(g.css(a),10)||!1}})(jQuery);
(function(b){b.jgrid.extend({setSubGrid:function(){return this.each(function(){var d;this.p.subGridOptions=b.extend({plusicon:"ui-icon-plus",minusicon:"ui-icon-minus",openicon:"ui-icon-carat-1-sw",expandOnLoad:!1,delayOnLoad:50,selectOnExpand:!1,reloadOnExpand:!0},this.p.subGridOptions||{});this.p.colNames.unshift("");this.p.colModel.unshift({name:"subgrid",width:b.browser.safari?this.p.subGridWidth+this.p.cellLayout:this.p.subGridWidth,sortable:!1,resizable:!1,hidedlg:!0,search:!1,fixed:!0});d=this.p.subGridModel;
if(d[0]){d[0].align=b.extend([],d[0].align||[]);for(var c=0;c<d[0].name.length;c++)d[0].align[c]=d[0].align[c]||"left"}})},addSubGridCell:function(b,c){var a="",m,n;this.each(function(){a=this.formatCol(b,c);n=this.p.id;m=this.p.subGridOptions.plusicon});return'<td role="gridcell" aria-describedby="'+n+'_subgrid" class="ui-sgcollapsed sgcollapsed" '+a+"><a href='javascript:void(0);'><span class='ui-icon "+m+"'></span></a></td>"},addSubGrid:function(d,c){return this.each(function(){var a=this;if(a.grid){var m=
function(c,d,h){d=b("<td align='"+a.p.subGridModel[0].align[h]+"'></td>").html(d);b(c).append(d)},n=function(c,d){var h,f,o,e=b("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),i=b("<tr></tr>");for(f=0;f<a.p.subGridModel[0].name.length;f++)h=b("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-"+a.p.direction+"'></th>"),b(h).html(a.p.subGridModel[0].name[f]),b(h).width(a.p.subGridModel[0].width[f]),b(i).append(h);b(e).append(i);c&&(o=a.p.xmlReader.subgrid,b(o.root+
" "+o.row,c).each(function(){i=b("<tr class='ui-widget-content ui-subtblcell'></tr>");if(!0===o.repeatitems)b(o.cell,this).each(function(a){m(i,b(this).text()||"&#160;",a)});else{var c=a.p.subGridModel[0].mapping||a.p.subGridModel[0].name;if(c)for(f=0;f<c.length;f++)m(i,b(c[f],this).text()||"&#160;",f)}b(e).append(i)}));h=b("table:first",a.grid.bDiv).attr("id")+"_";b("#"+b.jgrid.jqID(h+d)).append(e);a.grid.hDiv.loading=!1;b("#load_"+b.jgrid.jqID(a.p.id)).hide();return!1},p=function(c,d){var h,f,e,
g,i,k=b("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),j=b("<tr></tr>");for(f=0;f<a.p.subGridModel[0].name.length;f++)h=b("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-"+a.p.direction+"'></th>"),b(h).html(a.p.subGridModel[0].name[f]),b(h).width(a.p.subGridModel[0].width[f]),b(j).append(h);b(k).append(j);if(c&&(g=a.p.jsonReader.subgrid,h=c[g.root],"undefined"!==typeof h))for(f=0;f<h.length;f++){e=h[f];j=b("<tr class='ui-widget-content ui-subtblcell'></tr>");
if(!0===g.repeatitems){g.cell&&(e=e[g.cell]);for(i=0;i<e.length;i++)m(j,e[i]||"&#160;",i)}else{var l=a.p.subGridModel[0].mapping||a.p.subGridModel[0].name;if(l.length)for(i=0;i<l.length;i++)m(j,e[l[i]]||"&#160;",i)}b(k).append(j)}f=b("table:first",a.grid.bDiv).attr("id")+"_";b("#"+b.jgrid.jqID(f+d)).append(k);a.grid.hDiv.loading=!1;b("#load_"+b.jgrid.jqID(a.p.id)).hide();return!1},t=function(c){var d,e,f,g;d=b(c).attr("id");e={nd_:(new Date).getTime()};e[a.p.prmNames.subgridid]=d;if(!a.p.subGridModel[0])return!1;
if(a.p.subGridModel[0].params)for(g=0;g<a.p.subGridModel[0].params.length;g++)for(f=0;f<a.p.colModel.length;f++)a.p.colModel[f].name===a.p.subGridModel[0].params[g]&&(e[a.p.colModel[f].name]=b("td:eq("+f+")",c).text().replace(/\&#160\;/ig,""));if(!a.grid.hDiv.loading)switch(a.grid.hDiv.loading=!0,b("#load_"+b.jgrid.jqID(a.p.id)).show(),a.p.subgridtype||(a.p.subgridtype=a.p.datatype),b.isFunction(a.p.subgridtype)?a.p.subgridtype.call(a,e):a.p.subgridtype=a.p.subgridtype.toLowerCase(),a.p.subgridtype){case "xml":case "json":b.ajax(b.extend({type:a.p.mtype,
url:a.p.subGridUrl,dataType:a.p.subgridtype,data:b.isFunction(a.p.serializeSubGridData)?a.p.serializeSubGridData.call(a,e):e,complete:function(c){a.p.subgridtype==="xml"?n(c.responseXML,d):p(b.jgrid.parse(c.responseText),d)}},b.jgrid.ajaxOptions,a.p.ajaxSubgridOptions||{}))}return!1},e,k,q,r=0,g,j;b.each(a.p.colModel,function(){(!0===this.hidden||"rn"===this.name||"cb"===this.name)&&r++});var s=a.rows.length,l=1;void 0!==c&&0<c&&(l=c,s=c+1);for(;l<s;)b(a.rows[l]).hasClass("jqgrow")&&b(a.rows[l].cells[d]).bind("click",
function(){var c=b(this).parent("tr")[0];j=c.nextSibling;if(b(this).hasClass("sgcollapsed")){k=a.p.id;e=c.id;if(a.p.subGridOptions.reloadOnExpand===true||a.p.subGridOptions.reloadOnExpand===false&&!b(j).hasClass("ui-subgrid")){q=d>=1?"<td colspan='"+d+"'>&#160;</td>":"";g=b(a).triggerHandler("jqGridSubGridBeforeExpand",[k+"_"+e,e]);(g=g===false||g==="stop"?false:true)&&b.isFunction(a.p.subGridBeforeExpand)&&(g=a.p.subGridBeforeExpand.call(a,k+"_"+e,e));if(g===false)return false;b(c).after("<tr role='row' class='ui-subgrid'>"+
q+"<td class='ui-widget-content subgrid-cell'><span class='ui-icon "+a.p.subGridOptions.openicon+"'></span></td><td colspan='"+parseInt(a.p.colNames.length-1-r,10)+"' class='ui-widget-content subgrid-data'><div id="+k+"_"+e+" class='tablediv'></div></td></tr>");b(a).triggerHandler("jqGridSubGridRowExpanded",[k+"_"+e,e]);b.isFunction(a.p.subGridRowExpanded)?a.p.subGridRowExpanded.call(a,k+"_"+e,e):t(c)}else b(j).show();b(this).html("<a href='javascript:void(0);'><span class='ui-icon "+a.p.subGridOptions.minusicon+
"'></span></a>").removeClass("sgcollapsed").addClass("sgexpanded");a.p.subGridOptions.selectOnExpand&&b(a).jqGrid("setSelection",e)}else if(b(this).hasClass("sgexpanded")){g=b(a).triggerHandler("jqGridSubGridRowColapsed",[k+"_"+e,e]);if((g=g===false||g==="stop"?false:true)&&b.isFunction(a.p.subGridRowColapsed)){e=c.id;g=a.p.subGridRowColapsed.call(a,k+"_"+e,e)}if(g===false)return false;a.p.subGridOptions.reloadOnExpand===true?b(j).remove(".ui-subgrid"):b(j).hasClass("ui-subgrid")&&b(j).hide();b(this).html("<a href='javascript:void(0);'><span class='ui-icon "+
a.p.subGridOptions.plusicon+"'></span></a>").removeClass("sgexpanded").addClass("sgcollapsed")}return false}),!0===a.p.subGridOptions.expandOnLoad&&b(a.rows[l].cells[d]).trigger("click"),l++;a.subGridXml=function(a,b){n(a,b)};a.subGridJson=function(a,b){p(a,b)}}})},expandSubGridRow:function(d){return this.each(function(){if((this.grid||d)&&!0===this.p.subGrid){var c=b(this).jqGrid("getInd",d,!0);c&&(c=b("td.sgcollapsed",c)[0])&&b(c).trigger("click")}})},collapseSubGridRow:function(d){return this.each(function(){if((this.grid||
d)&&!0===this.p.subGrid){var c=b(this).jqGrid("getInd",d,!0);c&&(c=b("td.sgexpanded",c)[0])&&b(c).trigger("click")}})},toggleSubGridRow:function(d){return this.each(function(){if((this.grid||d)&&!0===this.p.subGrid){var c=b(this).jqGrid("getInd",d,!0);if(c){var a=b("td.sgcollapsed",c)[0];a?b(a).trigger("click"):(a=b("td.sgexpanded",c)[0])&&b(a).trigger("click")}}})}})})(jQuery);
(function(e){e.jgrid.extend({groupingSetup:function(){return this.each(function(){var a=this.p.groupingView;if(null!==a&&("object"===typeof a||e.isFunction(a)))if(a.groupField.length){"undefined"==typeof a.visibiltyOnNextGrouping&&(a.visibiltyOnNextGrouping=[]);for(var b=0;b<a.groupField.length;b++)if(a.groupOrder[b]||(a.groupOrder[b]="asc"),a.groupText[b]||(a.groupText[b]="{0}"),"boolean"!=typeof a.groupColumnShow[b]&&(a.groupColumnShow[b]=!0),"boolean"!=typeof a.groupSummary[b]&&(a.groupSummary[b]=
!1),!0===a.groupColumnShow[b]?(a.visibiltyOnNextGrouping[b]=!0,e(this).jqGrid("showCol",a.groupField[b])):(a.visibiltyOnNextGrouping[b]=e("#"+e.jgrid.jqID(this.p.id+"_"+a.groupField[b])).is(":visible"),e(this).jqGrid("hideCol",a.groupField[b])),a.sortitems[b]=[],a.sortnames[b]=[],a.summaryval[b]=[],a.groupSummary[b]){a.summary[b]=[];for(var c=this.p.colModel,d=0,g=c.length;d<g;d++)c[d].summaryType&&a.summary[b].push({nm:c[d].name,st:c[d].summaryType,v:""})}}else this.p.grouping=!1;else this.p.grouping=
!1})},groupingPrepare:function(a,b,c){this.each(function(){for(var d=this.p.groupingView,g=this,h=d.groupField.length,f=[],i=0;i<h;i++)f.push(c[d.groupField[i]]);f[0]+="";h=f[0].toString().split(" ").join("");b.hasOwnProperty(h)?b[h].push(a):(b[h]=[],b[h].push(a),d.sortitems[0].push(h),d.sortnames[0].push(e.trim(f[0].toString())),d.summaryval[0][h]=e.extend(!0,[],d.summary[0]));d.groupSummary[0]&&e.each(d.summaryval[0][h],function(){this.v=e.isFunction(this.st)?this.st.call(g,this.v,this.nm,c):e(g).jqGrid("groupingCalculations."+
this.st,this.v,this.nm,c)})});return b},groupingToggle:function(a){this.each(function(){var b=this.p.groupingView,c=a.lastIndexOf("_"),d=a.substring(0,c+1),c=parseInt(a.substring(c+1),10)+1,g=b.minusicon,h=b.plusicon,f=e("#"+e.jgrid.jqID(a)),f=f.length?f[0].nextSibling:null,i=e("#"+e.jgrid.jqID(a)+" span.tree-wrap-"+this.p.direction),m=!1;if(i.hasClass(g)){if(b.showSummaryOnHide&&b.groupSummary[0]){if(f)for(;f&&!e(f).hasClass("jqfoot");)e(f).hide(),f=f.nextSibling}else if(f)for(;f&&e(f).attr("id")!=
d+(""+c);)e(f).hide(),f=f.nextSibling;i.removeClass(g).addClass(h);m=!0}else{if(f)for(;f&&e(f).attr("id")!=d+(""+c);)e(f).show(),f=f.nextSibling;i.removeClass(h).addClass(g)}e(this).triggerHandler("jqGridGroupingClickGroup",[a,m]);e.isFunction(this.p.onClickGroup)&&this.p.onClickGroup.call(this,a,m)});return!1},groupingRender:function(a,b){return this.each(function(){var c=this,d=c.p.groupingView,g="",h="",f,i=d.groupCollapse?d.plusicon:d.minusicon,m,q,j;d.groupDataSorted||(d.sortitems[0].sort(),
d.sortnames[0].sort(),"desc"==d.groupOrder[0].toLowerCase()&&(d.sortitems[0].reverse(),d.sortnames[0].reverse()));i+=" tree-wrap-"+c.p.direction;for(j=0;j<b;){if(c.p.colModel[j].name==d.groupField[0]){q=j;break}j++}e.each(d.sortitems[0],function(j,n){f=c.p.id+"ghead_"+j;h="<span style='cursor:pointer;' class='ui-icon "+i+"' onclick=\"jQuery('#"+e.jgrid.jqID(c.p.id)+"').jqGrid('groupingToggle','"+f+"');return false;\"></span>";try{m=c.formatter(f,d.sortnames[0][j],q,d.sortitems[0])}catch(u){m=d.sortnames[0][j]}g=
g+('<tr id="'+f+'" role="row" class= "ui-widget-content jqgroup ui-row-'+c.p.direction+'"><td colspan="'+b+'">'+h+e.jgrid.format(d.groupText[0],m,a[n].length)+"</td></tr>");for(var k=0;k<a[n].length;k++)g=g+a[n][k].join("");if(d.groupSummary[0]){k="";d.groupCollapse&&!d.showSummaryOnHide&&(k=' style="display:none;"');g=g+("<tr"+k+' role="row" class="ui-widget-content jqfoot ui-row-'+c.p.direction+'">');for(var k=d.summaryval[0][n],o=c.p.colModel,p,r=a[n].length,l=0;l<b;l++){var s="<td "+c.formatCol(l,
1,"")+">&#160;</td>",t="{0}";e.each(k,function(){if(this.nm==o[l].name){if(o[l].summaryTpl)t=o[l].summaryTpl;if(this.st=="avg"&&this.v&&r>0)this.v=this.v/r;try{p=c.formatter("",this.v,l,this)}catch(a){p=this.v}s="<td "+c.formatCol(l,1,"")+">"+e.jgrid.format(t,p)+"</td>";return false}});g=g+s}g=g+"</tr>"}});e("#"+e.jgrid.jqID(c.p.id)+" tbody:first").append(g);g=null})},groupingGroupBy:function(a,b){return this.each(function(){"string"==typeof a&&(a=[a]);var c=this.p.groupingView;this.p.grouping=!0;
"undefined"==typeof c.visibiltyOnNextGrouping&&(c.visibiltyOnNextGrouping=[]);var d;for(d=0;d<c.groupField.length;d++)!c.groupColumnShow[d]&&c.visibiltyOnNextGrouping[d]&&e(this).jqGrid("showCol",c.groupField[d]);for(d=0;d<a.length;d++)c.visibiltyOnNextGrouping[d]=e("#"+e.jgrid.jqID(this.p.id)+"_"+e.jgrid.jqID(a[d])).is(":visible");this.p.groupingView=e.extend(this.p.groupingView,b||{});c.groupField=a;e(this).trigger("reloadGrid")})},groupingRemove:function(a){return this.each(function(){"undefined"==
typeof a&&(a=!0);this.p.grouping=!1;if(!0===a){for(var b=this.p.groupingView,c=0;c<b.groupField.length;c++)!b.groupColumnShow[c]&&b.visibiltyOnNextGrouping[c]&&e(this).jqGrid("showCol",b.groupField);e("tr.jqgroup, tr.jqfoot","#"+e.jgrid.jqID(this.p.id)+" tbody:first").remove();e("tr.jqgrow:hidden","#"+e.jgrid.jqID(this.p.id)+" tbody:first").show()}else e(this).trigger("reloadGrid")})},groupingCalculations:{sum:function(a,b,c){return parseFloat(a||0)+parseFloat(c[b]||0)},min:function(a,b,c){return""===
a?parseFloat(c[b]||0):Math.min(parseFloat(a),parseFloat(c[b]||0))},max:function(a,b,c){return""===a?parseFloat(c[b]||0):Math.max(parseFloat(a),parseFloat(c[b]||0))},count:function(a,b,c){""===a&&(a=0);return c.hasOwnProperty(b)?a+1:0},avg:function(a,b,c){return parseFloat(a||0)+parseFloat(c[b]||0)}}})})(jQuery);
(function(d){d.jgrid.extend({setTreeNode:function(a,b){return this.each(function(){var c=this;if(c.grid&&c.p.treeGrid)for(var e=c.p.expColInd,g=c.p.treeReader.expanded_field,h=c.p.treeReader.leaf_field,f=c.p.treeReader.level_field,l=c.p.treeReader.icon_field,j=c.p.treeReader.loaded,i,p,m,k;a<b;)k=c.p.data[c.p._index[c.rows[a].id]],"nested"==c.p.treeGridModel&&!k[h]&&(i=parseInt(k[c.p.treeReader.left_field],10),p=parseInt(k[c.p.treeReader.right_field],10),k[h]=p===i+1?"true":"false",c.rows[a].cells[c.p._treeleafpos].innerHTML=
k[h]),i=parseInt(k[f],10),0===c.p.tree_root_level?(m=i+1,p=i):(m=i,p=i-1),m="<div class='tree-wrap tree-wrap-"+c.p.direction+"' style='width:"+18*m+"px;'>",m+="<div style='"+("rtl"==c.p.direction?"right:":"left:")+18*p+"px;' class='ui-icon ",void 0!==k[j]&&(k[j]="true"==k[j]||!0===k[j]?!0:!1),"true"==k[h]||!0===k[h]?(m+=(void 0!==k[l]&&""!==k[l]?k[l]:c.p.treeIcons.leaf)+" tree-leaf treeclick",k[h]=!0,p="leaf"):(k[h]=!1,p=""),k[g]=("true"==k[g]||!0===k[g]?!0:!1)&&k[j],m=!1===k[g]?m+(!0===k[h]?"'":
c.p.treeIcons.plus+" tree-plus treeclick'"):m+(!0===k[h]?"'":c.p.treeIcons.minus+" tree-minus treeclick'"),m+="></div></div>",d(c.rows[a].cells[e]).wrapInner("<span class='cell-wrapper"+p+"'></span>").prepend(m),i!==parseInt(c.p.tree_root_level,10)&&((k=(k=d(c).jqGrid("getNodeParent",k))&&k.hasOwnProperty(g)?k[g]:!0)||d(c.rows[a]).css("display","none")),d(c.rows[a].cells[e]).find("div.treeclick").bind("click",function(a){a=d(a.target||a.srcElement,c.rows).closest("tr.jqgrow")[0].id;a=c.p._index[a];
if(!c.p.data[a][h])if(c.p.data[a][g]){d(c).jqGrid("collapseRow",c.p.data[a]);d(c).jqGrid("collapseNode",c.p.data[a])}else{d(c).jqGrid("expandRow",c.p.data[a]);d(c).jqGrid("expandNode",c.p.data[a])}return false}),!0===c.p.ExpandColClick&&d(c.rows[a].cells[e]).find("span.cell-wrapper").css("cursor","pointer").bind("click",function(a){var a=d(a.target||a.srcElement,c.rows).closest("tr.jqgrow")[0].id,b=c.p._index[a];if(!c.p.data[b][h])if(c.p.data[b][g]){d(c).jqGrid("collapseRow",c.p.data[b]);d(c).jqGrid("collapseNode",
c.p.data[b])}else{d(c).jqGrid("expandRow",c.p.data[b]);d(c).jqGrid("expandNode",c.p.data[b])}d(c).jqGrid("setSelection",a);return false}),a++})},setTreeGrid:function(){return this.each(function(){var a=this,b=0,c=!1,e,g,h=[];if(a.p.treeGrid){a.p.treedatatype||d.extend(a.p,{treedatatype:a.p.datatype});a.p.subGrid=!1;a.p.altRows=!1;a.p.pgbuttons=!1;a.p.pginput=!1;a.p.gridview=!0;null===a.p.rowTotal&&(a.p.rowNum=1E4);a.p.multiselect=!1;a.p.rowList=[];a.p.expColInd=0;a.p.treeIcons=d.extend({plus:"ui-icon-triangle-1-"+
("rtl"==a.p.direction?"w":"e"),minus:"ui-icon-triangle-1-s",leaf:"ui-icon-radio-off"},a.p.treeIcons||{});"nested"==a.p.treeGridModel?a.p.treeReader=d.extend({level_field:"level",left_field:"lft",right_field:"rgt",leaf_field:"isLeaf",expanded_field:"expanded",loaded:"loaded",icon_field:"icon"},a.p.treeReader):"adjacency"==a.p.treeGridModel&&(a.p.treeReader=d.extend({level_field:"level",parent_id_field:"parent",leaf_field:"isLeaf",expanded_field:"expanded",loaded:"loaded",icon_field:"icon"},a.p.treeReader));
for(g in a.p.colModel)if(a.p.colModel.hasOwnProperty(g)){e=a.p.colModel[g].name;e==a.p.ExpandColumn&&!c&&(c=!0,a.p.expColInd=b);b++;for(var f in a.p.treeReader)a.p.treeReader[f]==e&&h.push(e)}d.each(a.p.treeReader,function(c,e){if(e&&d.inArray(e,h)===-1){if(c==="leaf_field")a.p._treeleafpos=b;b++;a.p.colNames.push(e);a.p.colModel.push({name:e,width:1,hidden:true,sortable:false,resizable:false,hidedlg:true,editable:true,search:false})}})}})},expandRow:function(a){this.each(function(){var b=this;if(b.grid&&
b.p.treeGrid){var c=d(b).jqGrid("getNodeChildren",a),e=b.p.treeReader.expanded_field,g=b.rows;d(c).each(function(){var a=d.jgrid.getAccessor(this,b.p.localReader.id);d(g.namedItem(a)).css("display","");this[e]&&d(b).jqGrid("expandRow",this)})}})},collapseRow:function(a){this.each(function(){var b=this;if(b.grid&&b.p.treeGrid){var c=d(b).jqGrid("getNodeChildren",a),e=b.p.treeReader.expanded_field,g=b.rows;d(c).each(function(){var a=d.jgrid.getAccessor(this,b.p.localReader.id);d(g.namedItem(a)).css("display",
"none");this[e]&&d(b).jqGrid("collapseRow",this)})}})},getRootNodes:function(){var a=[];this.each(function(){var b=this;if(b.grid&&b.p.treeGrid)switch(b.p.treeGridModel){case "nested":var c=b.p.treeReader.level_field;d(b.p.data).each(function(){parseInt(this[c],10)===parseInt(b.p.tree_root_level,10)&&a.push(this)});break;case "adjacency":var e=b.p.treeReader.parent_id_field;d(b.p.data).each(function(){(null===this[e]||"null"==(""+this[e]).toLowerCase())&&a.push(this)})}});return a},getNodeDepth:function(a){var b=
null;this.each(function(){if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":b=parseInt(a[this.p.treeReader.level_field],10)-parseInt(this.p.tree_root_level,10);break;case "adjacency":b=d(this).jqGrid("getNodeAncestors",a).length}});return b},getNodeParent:function(a){var b=null;this.each(function(){if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":var c=this.p.treeReader.left_field,e=this.p.treeReader.right_field,g=this.p.treeReader.level_field,h=parseInt(a[c],
10),f=parseInt(a[e],10),l=parseInt(a[g],10);d(this.p.data).each(function(){if(parseInt(this[g],10)===l-1&&parseInt(this[c],10)<h&&parseInt(this[e],10)>f)return b=this,!1});break;case "adjacency":var j=this.p.treeReader.parent_id_field,i=this.p.localReader.id;d(this.p.data).each(function(){if(this[i]==a[j])return b=this,!1})}});return b},getNodeChildren:function(a){var b=[];this.each(function(){if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":var c=this.p.treeReader.left_field,
e=this.p.treeReader.right_field,g=this.p.treeReader.level_field,h=parseInt(a[c],10),f=parseInt(a[e],10),l=parseInt(a[g],10);d(this.p.data).each(function(){parseInt(this[g],10)===l+1&&parseInt(this[c],10)>h&&parseInt(this[e],10)<f&&b.push(this)});break;case "adjacency":var j=this.p.treeReader.parent_id_field,i=this.p.localReader.id;d(this.p.data).each(function(){this[j]==a[i]&&b.push(this)})}});return b},getFullTreeNode:function(a){var b=[];this.each(function(){var c;if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":var e=
this.p.treeReader.left_field,g=this.p.treeReader.right_field,h=this.p.treeReader.level_field,f=parseInt(a[e],10),l=parseInt(a[g],10),j=parseInt(a[h],10);d(this.p.data).each(function(){parseInt(this[h],10)>=j&&parseInt(this[e],10)>=f&&parseInt(this[e],10)<=l&&b.push(this)});break;case "adjacency":if(a){b.push(a);var i=this.p.treeReader.parent_id_field,p=this.p.localReader.id;d(this.p.data).each(function(a){c=b.length;for(a=0;a<c;a++)if(b[a][p]==this[i]){b.push(this);break}})}}});return b},getNodeAncestors:function(a){var b=
[];this.each(function(){if(this.grid&&this.p.treeGrid)for(var c=d(this).jqGrid("getNodeParent",a);c;)b.push(c),c=d(this).jqGrid("getNodeParent",c)});return b},isVisibleNode:function(a){var b=!0;this.each(function(){if(this.grid&&this.p.treeGrid){var c=d(this).jqGrid("getNodeAncestors",a),e=this.p.treeReader.expanded_field;d(c).each(function(){b=b&&this[e];if(!b)return!1})}});return b},isNodeLoaded:function(a){var b;this.each(function(){if(this.grid&&this.p.treeGrid){var c=this.p.treeReader.leaf_field;
b=void 0!==a?void 0!==a.loaded?a.loaded:a[c]||0<d(this).jqGrid("getNodeChildren",a).length?!0:!1:!1}});return b},expandNode:function(a){return this.each(function(){if(this.grid&&this.p.treeGrid){var b=this.p.treeReader.expanded_field,c=this.p.treeReader.parent_id_field,e=this.p.treeReader.loaded,g=this.p.treeReader.level_field,h=this.p.treeReader.left_field,f=this.p.treeReader.right_field;if(!a[b]){var l=d.jgrid.getAccessor(a,this.p.localReader.id),j=d("#"+d.jgrid.jqID(l),this.grid.bDiv)[0],i=this.p._index[l];
d(this).jqGrid("isNodeLoaded",this.p.data[i])?(a[b]=!0,d("div.treeclick",j).removeClass(this.p.treeIcons.plus+" tree-plus").addClass(this.p.treeIcons.minus+" tree-minus")):this.grid.hDiv.loading||(a[b]=!0,d("div.treeclick",j).removeClass(this.p.treeIcons.plus+" tree-plus").addClass(this.p.treeIcons.minus+" tree-minus"),this.p.treeANode=j.rowIndex,this.p.datatype=this.p.treedatatype,"nested"==this.p.treeGridModel?d(this).jqGrid("setGridParam",{postData:{nodeid:l,n_left:a[h],n_right:a[f],n_level:a[g]}}):
d(this).jqGrid("setGridParam",{postData:{nodeid:l,parentid:a[c],n_level:a[g]}}),d(this).trigger("reloadGrid"),a[e]=!0,"nested"==this.p.treeGridModel?d(this).jqGrid("setGridParam",{postData:{nodeid:"",n_left:"",n_right:"",n_level:""}}):d(this).jqGrid("setGridParam",{postData:{nodeid:"",parentid:"",n_level:""}}))}}})},collapseNode:function(a){return this.each(function(){if(this.grid&&this.p.treeGrid){var b=this.p.treeReader.expanded_field;a[b]&&(a[b]=!1,b=d.jgrid.getAccessor(a,this.p.localReader.id),
b=d("#"+d.jgrid.jqID(b),this.grid.bDiv)[0],d("div.treeclick",b).removeClass(this.p.treeIcons.minus+" tree-minus").addClass(this.p.treeIcons.plus+" tree-plus"))}})},SortTree:function(a,b,c,e){return this.each(function(){if(this.grid&&this.p.treeGrid){var g,h,f,l=[],j=this,i;g=d(this).jqGrid("getRootNodes");g=d.jgrid.from(g);g.orderBy(a,b,c,e);i=g.select();g=0;for(h=i.length;g<h;g++)f=i[g],l.push(f),d(this).jqGrid("collectChildrenSortTree",l,f,a,b,c,e);d.each(l,function(a){var b=d.jgrid.getAccessor(this,
j.p.localReader.id);d("#"+d.jgrid.jqID(j.p.id)+" tbody tr:eq("+a+")").after(d("tr#"+d.jgrid.jqID(b),j.grid.bDiv))});l=i=g=null}})},collectChildrenSortTree:function(a,b,c,e,g,h){return this.each(function(){if(this.grid&&this.p.treeGrid){var f,l,j,i;f=d(this).jqGrid("getNodeChildren",b);f=d.jgrid.from(f);f.orderBy(c,e,g,h);i=f.select();f=0;for(l=i.length;f<l;f++)j=i[f],a.push(j),d(this).jqGrid("collectChildrenSortTree",a,j,c,e,g,h)}})},setTreeRow:function(a,b){var c=!1;this.each(function(){this.grid&&
this.p.treeGrid&&(c=d(this).jqGrid("setRowData",a,b))});return c},delTreeNode:function(a){return this.each(function(){var b=this.p.localReader.id,c=this.p.treeReader.left_field,e=this.p.treeReader.right_field,g,h,f;if(this.grid&&this.p.treeGrid){var l=this.p._index[a];if(void 0!==l){g=parseInt(this.p.data[l][e],10);h=g-parseInt(this.p.data[l][c],10)+1;l=d(this).jqGrid("getFullTreeNode",this.p.data[l]);if(0<l.length)for(var j=0;j<l.length;j++)d(this).jqGrid("delRowData",l[j][b]);if("nested"===this.p.treeGridModel){b=
d.jgrid.from(this.p.data).greater(c,g,{stype:"integer"}).select();if(b.length)for(f in b)b.hasOwnProperty(f)&&(b[f][c]=parseInt(b[f][c],10)-h);b=d.jgrid.from(this.p.data).greater(e,g,{stype:"integer"}).select();if(b.length)for(f in b)b.hasOwnProperty(f)&&(b[f][e]=parseInt(b[f][e],10)-h)}}}})},addChildNode:function(a,b,c){var e=this[0];if(c){var g=e.p.treeReader.expanded_field,h=e.p.treeReader.leaf_field,f=e.p.treeReader.level_field,l=e.p.treeReader.parent_id_field,j=e.p.treeReader.left_field,i=e.p.treeReader.right_field,
p=e.p.treeReader.loaded,m,k,q,s,o;m=0;var r=b,t;if("undefined"===typeof a||null===a){o=e.p.data.length-1;if(0<=o)for(;0<=o;)m=Math.max(m,parseInt(e.p.data[o][e.p.localReader.id],10)),o--;a=m+1}var u=d(e).jqGrid("getInd",b);t=!1;if(void 0===b||null===b||""===b)r=b=null,m="last",s=e.p.tree_root_level,o=e.p.data.length+1;else if(m="after",k=e.p._index[b],q=e.p.data[k],b=q[e.p.localReader.id],s=parseInt(q[f],10)+1,o=d(e).jqGrid("getFullTreeNode",q),o.length?(r=o=o[o.length-1][e.p.localReader.id],o=d(e).jqGrid("getInd",
r)+1):o=d(e).jqGrid("getInd",b)+1,q[h])t=!0,q[g]=!0,d(e.rows[u]).find("span.cell-wrapperleaf").removeClass("cell-wrapperleaf").addClass("cell-wrapper").end().find("div.tree-leaf").removeClass(e.p.treeIcons.leaf+" tree-leaf").addClass(e.p.treeIcons.minus+" tree-minus"),e.p.data[k][h]=!1,q[p]=!0;k=o+1;c[g]=!1;c[p]=!0;c[f]=s;c[h]=!0;"adjacency"===e.p.treeGridModel&&(c[l]=b);if("nested"===e.p.treeGridModel){var n;if(null!==b){h=parseInt(q[i],10);f=d.jgrid.from(e.p.data);f=f.greaterOrEquals(i,h,{stype:"integer"});
f=f.select();if(f.length)for(n in f)f.hasOwnProperty(n)&&(f[n][j]=f[n][j]>h?parseInt(f[n][j],10)+2:f[n][j],f[n][i]=f[n][i]>=h?parseInt(f[n][i],10)+2:f[n][i]);c[j]=h;c[i]=h+1}else{h=parseInt(d(e).jqGrid("getCol",i,!1,"max"),10);f=d.jgrid.from(e.p.data).greater(j,h,{stype:"integer"}).select();if(f.length)for(n in f)f.hasOwnProperty(n)&&(f[n][j]=parseInt(f[n][j],10)+2);f=d.jgrid.from(e.p.data).greater(i,h,{stype:"integer"}).select();if(f.length)for(n in f)f.hasOwnProperty(n)&&(f[n][i]=parseInt(f[n][i],
10)+2);c[j]=h+1;c[i]=h+2}}if(null===b||d(e).jqGrid("isNodeLoaded",q)||t)d(e).jqGrid("addRowData",a,c,m,r),d(e).jqGrid("setTreeNode",o,k);q&&!q[g]&&d(e.rows[u]).find("div.treeclick").click()}}})})(jQuery);
(function(c){c.jgrid.extend({jqGridImport:function(a){a=c.extend({imptype:"xml",impstring:"",impurl:"",mtype:"GET",impData:{},xmlGrid:{config:"roots>grid",data:"roots>rows"},jsonGrid:{config:"grid",data:"data"},ajaxOptions:{}},a||{});return this.each(function(){var d=this,b=function(a,b){var e=c(b.xmlGrid.config,a)[0],h=c(b.xmlGrid.data,a)[0],f;if(xmlJsonClass.xml2json&&c.jgrid.parse){var e=xmlJsonClass.xml2json(e," "),e=c.jgrid.parse(e),g;for(g in e)e.hasOwnProperty(g)&&(f=e[g]);h?(h=e.grid.datatype,
e.grid.datatype="xmlstring",e.grid.datastr=a,c(d).jqGrid(f).jqGrid("setGridParam",{datatype:h})):c(d).jqGrid(f)}else alert("xml2json or parse are not present")},g=function(a,b){if(a&&"string"==typeof a){var e=!1;c.jgrid.useJSON&&(c.jgrid.useJSON=!1,e=!0);var f=c.jgrid.parse(a);e&&(c.jgrid.useJSON=!0);e=f[b.jsonGrid.config];if(f=f[b.jsonGrid.data]){var g=e.datatype;e.datatype="jsonstring";e.datastr=f;c(d).jqGrid(e).jqGrid("setGridParam",{datatype:g})}else c(d).jqGrid(e)}};switch(a.imptype){case "xml":c.ajax(c.extend({url:a.impurl,
type:a.mtype,data:a.impData,dataType:"xml",complete:function(f,g){"success"==g&&(b(f.responseXML,a),c(d).triggerHandler("jqGridImportComplete",[f,a]),c.isFunction(a.importComplete)&&a.importComplete(f))}},a.ajaxOptions));break;case "xmlstring":if(a.impstring&&"string"==typeof a.impstring){var f=c.jgrid.stringToDoc(a.impstring);f&&(b(f,a),c(d).triggerHandler("jqGridImportComplete",[f,a]),c.isFunction(a.importComplete)&&a.importComplete(f),a.impstring=null);f=null}break;case "json":c.ajax(c.extend({url:a.impurl,
type:a.mtype,data:a.impData,dataType:"json",complete:function(b){try{g(b.responseText,a),c(d).triggerHandler("jqGridImportComplete",[b,a]),c.isFunction(a.importComplete)&&a.importComplete(b)}catch(f){}}},a.ajaxOptions));break;case "jsonstring":a.impstring&&"string"==typeof a.impstring&&(g(a.impstring,a),c(d).triggerHandler("jqGridImportComplete",[a.impstring,a]),c.isFunction(a.importComplete)&&a.importComplete(a.impstring),a.impstring=null)}})},jqGridExport:function(a){var a=c.extend({exptype:"xmlstring",
root:"grid",ident:"\t"},a||{}),d=null;this.each(function(){if(this.grid){var b=c.extend(!0,{},c(this).jqGrid("getGridParam"));b.rownumbers&&(b.colNames.splice(0,1),b.colModel.splice(0,1));b.multiselect&&(b.colNames.splice(0,1),b.colModel.splice(0,1));b.subGrid&&(b.colNames.splice(0,1),b.colModel.splice(0,1));b.knv=null;if(b.treeGrid)for(var g in b.treeReader)b.treeReader.hasOwnProperty(g)&&(b.colNames.splice(b.colNames.length-1),b.colModel.splice(b.colModel.length-1));switch(a.exptype){case "xmlstring":d=
"<"+a.root+">"+xmlJsonClass.json2xml(b,a.ident)+"</"+a.root+">";break;case "jsonstring":d="{"+xmlJsonClass.toJson(b,a.root,a.ident,!1)+"}",void 0!==b.postData.filters&&(d=d.replace(/filters":"/,'filters":'),d=d.replace(/}]}"/,"}]}"))}}});return d},excelExport:function(a){a=c.extend({exptype:"remote",url:null,oper:"oper",tag:"excel",exportOptions:{}},a||{});return this.each(function(){if(this.grid){var d;"remote"==a.exptype&&(d=c.extend({},this.p.postData),d[a.oper]=a.tag,d=jQuery.param(d),d=-1!=a.url.indexOf("?")?
a.url+"&"+d:a.url+"?"+d,window.location=d)}})}})})(jQuery);
var xmlJsonClass={xml2json:function(a,b){if(9===a.nodeType)a=a.documentElement;var g=this.toJson(this.toObj(this.removeWhite(a)),a.nodeName,"\t");return"{\n"+b+(b?g.replace(/\t/g,b):g.replace(/\t|\n/g,""))+"\n}"},json2xml:function(a,b){var g=function(a,b,e){var d="",f,i;if(a instanceof Array)if(0===a.length)d+=e+"<"+b+">__EMPTY_ARRAY_</"+b+">\n";else for(f=0,i=a.length;f<i;f+=1)var l=e+g(a[f],b,e+"\t")+"\n",d=d+l;else if("object"===typeof a){f=!1;d+=e+"<"+b;for(i in a)a.hasOwnProperty(i)&&("@"===
i.charAt(0)?d+=" "+i.substr(1)+'="'+a[i].toString()+'"':f=!0);d+=f?">":"/>";if(f){for(i in a)a.hasOwnProperty(i)&&("#text"===i?d+=a[i]:"#cdata"===i?d+="<![CDATA["+a[i]+"]]\>":"@"!==i.charAt(0)&&(d+=g(a[i],i,e+"\t")));d+=("\n"===d.charAt(d.length-1)?e:"")+"</"+b+">"}}else"function"===typeof a?d+=e+"<"+b+"><![CDATA["+a+"]]\></"+b+">":(void 0===a&&(a=""),d='""'===a.toString()||0===a.toString().length?d+(e+"<"+b+">__EMPTY_STRING_</"+b+">"):d+(e+"<"+b+">"+a.toString()+"</"+b+">"));return d},f="",e;for(e in a)a.hasOwnProperty(e)&&
(f+=g(a[e],e,""));return b?f.replace(/\t/g,b):f.replace(/\t|\n/g,"")},toObj:function(a){var b={},g=/function/i;if(1===a.nodeType){if(a.attributes.length){var f;for(f=0;f<a.attributes.length;f+=1)b["@"+a.attributes[f].nodeName]=(a.attributes[f].nodeValue||"").toString()}if(a.firstChild){var e=f=0,h=!1,c;for(c=a.firstChild;c;c=c.nextSibling)1===c.nodeType?h=!0:3===c.nodeType&&c.nodeValue.match(/[^ \f\n\r\t\v]/)?f+=1:4===c.nodeType&&(e+=1);if(h)if(2>f&&2>e){this.removeWhite(a);for(c=a.firstChild;c;c=
c.nextSibling)3===c.nodeType?b["#text"]=this.escape(c.nodeValue):4===c.nodeType?g.test(c.nodeValue)?b[c.nodeName]=[b[c.nodeName],c.nodeValue]:b["#cdata"]=this.escape(c.nodeValue):b[c.nodeName]?b[c.nodeName]instanceof Array?b[c.nodeName][b[c.nodeName].length]=this.toObj(c):b[c.nodeName]=[b[c.nodeName],this.toObj(c)]:b[c.nodeName]=this.toObj(c)}else a.attributes.length?b["#text"]=this.escape(this.innerXml(a)):b=this.escape(this.innerXml(a));else if(f)a.attributes.length?b["#text"]=this.escape(this.innerXml(a)):
(b=this.escape(this.innerXml(a)),"__EMPTY_ARRAY_"===b?b="[]":"__EMPTY_STRING_"===b&&(b=""));else if(e)if(1<e)b=this.escape(this.innerXml(a));else for(c=a.firstChild;c;c=c.nextSibling)if(g.test(a.firstChild.nodeValue)){b=a.firstChild.nodeValue;break}else b["#cdata"]=this.escape(c.nodeValue)}!a.attributes.length&&!a.firstChild&&(b=null)}else 9===a.nodeType?b=this.toObj(a.documentElement):alert("unhandled node type: "+a.nodeType);return b},toJson:function(a,b,g,f){void 0===f&&(f=!0);var e=b?'"'+b+'"':
"",h="\t",c="\n";f||(c=h="");if("[]"===a)e+=b?":[]":"[]";else if(a instanceof Array){var j,d,k=[];for(d=0,j=a.length;d<j;d+=1)k[d]=this.toJson(a[d],"",g+h,f);e+=(b?":[":"[")+(1<k.length?c+g+h+k.join(","+c+g+h)+c+g:k.join(""))+"]"}else if(null===a)e+=(b&&":")+"null";else if("object"===typeof a){j=[];for(d in a)a.hasOwnProperty(d)&&(j[j.length]=this.toJson(a[d],d,g+h,f));e+=(b?":{":"{")+(1<j.length?c+g+h+j.join(","+c+g+h)+c+g:j.join(""))+"}"}else e="string"===typeof a?e+((b&&":")+'"'+a.replace(/\\/g,
"\\\\").replace(/\"/g,'\\"')+'"'):e+((b&&":")+a.toString());return e},innerXml:function(a){var b="";if("innerHTML"in a)b=a.innerHTML;else for(var g=function(a){var b="",h;if(1===a.nodeType){b+="<"+a.nodeName;for(h=0;h<a.attributes.length;h+=1)b+=" "+a.attributes[h].nodeName+'="'+(a.attributes[h].nodeValue||"").toString()+'"';if(a.firstChild){b+=">";for(h=a.firstChild;h;h=h.nextSibling)b+=g(h);b+="</"+a.nodeName+">"}else b+="/>"}else 3===a.nodeType?b+=a.nodeValue:4===a.nodeType&&(b+="<![CDATA["+a.nodeValue+
"]]\>");return b},a=a.firstChild;a;a=a.nextSibling)b+=g(a);return b},escape:function(a){return a.replace(/[\\]/g,"\\\\").replace(/[\"]/g,'\\"').replace(/[\n]/g,"\\n").replace(/[\r]/g,"\\r")},removeWhite:function(a){a.normalize();var b;for(b=a.firstChild;b;)if(3===b.nodeType)if(b.nodeValue.match(/[^ \f\n\r\t\v]/))b=b.nextSibling;else{var g=b.nextSibling;a.removeChild(b);b=g}else 1===b.nodeType&&this.removeWhite(b),b=b.nextSibling;return a}};
function tableToGrid(j,k){jQuery(j).each(function(){if(!this.grid){jQuery(this).width("99%");var b=jQuery(this).width(),c=jQuery("tr td:first-child input[type=checkbox]:first",jQuery(this)),a=jQuery("tr td:first-child input[type=radio]:first",jQuery(this)),c=0<c.length,a=!c&&0<a.length,i=c||a,d=[],e=[];jQuery("th",jQuery(this)).each(function(){0===d.length&&i?(d.push({name:"__selection__",index:"__selection__",width:0,hidden:!0}),e.push("__selection__")):(d.push({name:jQuery(this).attr("id")||jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),
index:jQuery(this).attr("id")||jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),width:jQuery(this).width()||150}),e.push(jQuery(this).html()))});var f=[],g=[],h=[];jQuery("tbody > tr",jQuery(this)).each(function(){var b={},a=0;jQuery("td",jQuery(this)).each(function(){if(0===a&&i){var c=jQuery("input",jQuery(this)),e=c.attr("value");g.push(e||f.length);c.is(":checked")&&h.push(e);b[d[a].name]=c.attr("value")}else b[d[a].name]=jQuery(this).html();a++});0<a&&f.push(b)});
jQuery(this).empty();jQuery(this).addClass("scroll");jQuery(this).jqGrid(jQuery.extend({datatype:"local",width:b,colNames:e,colModel:d,multiselect:c},k||{}));for(b=0;b<f.length;b++)a=null,0<g.length&&(a=g[b])&&a.replace&&(a=encodeURIComponent(a).replace(/[.\-%]/g,"_")),null===a&&(a=b+1),jQuery(this).jqGrid("addRowData",a,f[b]);for(b=0;b<h.length;b++)jQuery(this).jqGrid("setSelection",h[b])}})};
(function(b){b.browser.msie&&8==b.browser.version&&(b.expr[":"].hidden=function(b){return 0===b.offsetWidth||0===b.offsetHeight||"none"==b.style.display});b.jgrid._multiselect=!1;if(b.ui&&b.ui.multiselect){if(b.ui.multiselect.prototype._setSelected){var m=b.ui.multiselect.prototype._setSelected;b.ui.multiselect.prototype._setSelected=function(a,e){var c=m.call(this,a,e);if(e&&this.selectedList){var d=this.element;this.selectedList.find("li").each(function(){b(this).data("optionLink")&&b(this).data("optionLink").remove().appendTo(d)})}return c}}b.ui.multiselect.prototype.destroy&&
(b.ui.multiselect.prototype.destroy=function(){this.element.show();this.container.remove();b.Widget===void 0?b.widget.prototype.destroy.apply(this,arguments):b.Widget.prototype.destroy.apply(this,arguments)});b.jgrid._multiselect=!0}b.jgrid.extend({sortableColumns:function(a){return this.each(function(){function e(){c.p.disableClick=true}var c=this,d=b.jgrid.jqID(c.p.id),d={tolerance:"pointer",axis:"x",scrollSensitivity:"1",items:">th:not(:has(#jqgh_"+d+"_cb,#jqgh_"+d+"_rn,#jqgh_"+d+"_subgrid),:hidden)",
placeholder:{element:function(a){return b(document.createElement(a[0].nodeName)).addClass(a[0].className+" ui-sortable-placeholder ui-state-highlight").removeClass("ui-sortable-helper")[0]},update:function(b,a){a.height(b.currentItem.innerHeight()-parseInt(b.currentItem.css("paddingTop")||0,10)-parseInt(b.currentItem.css("paddingBottom")||0,10));a.width(b.currentItem.innerWidth()-parseInt(b.currentItem.css("paddingLeft")||0,10)-parseInt(b.currentItem.css("paddingRight")||0,10))}},update:function(a,
g){var d=b(g.item).parent(),d=b(">th",d),e={},i=c.p.id+"_";b.each(c.p.colModel,function(b){e[this.name]=b});var h=[];d.each(function(){var a=b(">div",this).get(0).id.replace(/^jqgh_/,"").replace(i,"");a in e&&h.push(e[a])});b(c).jqGrid("remapColumns",h,true,true);b.isFunction(c.p.sortable.update)&&c.p.sortable.update(h);setTimeout(function(){c.p.disableClick=false},50)}};if(c.p.sortable.options)b.extend(d,c.p.sortable.options);else if(b.isFunction(c.p.sortable))c.p.sortable={update:c.p.sortable};
if(d.start){var g=d.start;d.start=function(b,a){e();g.call(this,b,a)}}else d.start=e;if(c.p.sortable.exclude)d.items=d.items+(":not("+c.p.sortable.exclude+")");a.sortable(d).data("sortable").floating=true})},columnChooser:function(a){function e(a,c){a&&(typeof a=="string"?b.fn[a]&&b.fn[a].apply(c,b.makeArray(arguments).slice(2)):b.isFunction(a)&&a.apply(c,b.makeArray(arguments).slice(2)))}var c=this;if(!b("#colchooser_"+b.jgrid.jqID(c[0].p.id)).length){var d=b('<div id="colchooser_'+c[0].p.id+'" style="position:relative;overflow:hidden"><div><select multiple="multiple"></select></div></div>'),
g=b("select",d),a=b.extend({width:420,height:240,classname:null,done:function(b){b&&c.jqGrid("remapColumns",b,true)},msel:"multiselect",dlog:"dialog",dialog_opts:{minWidth:470},dlog_opts:function(a){var c={};c[a.bSubmit]=function(){a.apply_perm();a.cleanup(false)};c[a.bCancel]=function(){a.cleanup(true)};return b.extend(true,{buttons:c,close:function(){a.cleanup(true)},modal:a.modal?a.modal:false,resizable:a.resizable?a.resizable:true,width:a.width+20},a.dialog_opts||{})},apply_perm:function(){b("option",
g).each(function(){this.selected?c.jqGrid("showCol",k[this.value].name):c.jqGrid("hideCol",k[this.value].name)});var d=[];b("option:selected",g).each(function(){d.push(parseInt(this.value,10))});b.each(d,function(){delete f[k[parseInt(this,10)].name]});b.each(f,function(){var b=parseInt(this,10);var a=d,c=b;if(c>=0){var g=a.slice(),e=g.splice(c,Math.max(a.length-c,c));if(c>a.length)c=a.length;g[c]=b;d=g.concat(e)}else d=void 0});a.done&&a.done.call(c,d)},cleanup:function(b){e(a.dlog,d,"destroy");
e(a.msel,g,"destroy");d.remove();b&&a.done&&a.done.call(c)},msel_opts:{}},b.jgrid.col,a||{});if(b.ui&&b.ui.multiselect&&a.msel=="multiselect"){if(!b.jgrid._multiselect){alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");return}a.msel_opts=b.extend(b.ui.multiselect.defaults,a.msel_opts)}a.caption&&d.attr("title",a.caption);if(a.classname){d.addClass(a.classname);g.addClass(a.classname)}if(a.width){b(">div",d).css({width:a.width,margin:"0 auto"});g.css("width",
a.width)}if(a.height){b(">div",d).css("height",a.height);g.css("height",a.height-10)}var k=c.jqGrid("getGridParam","colModel"),p=c.jqGrid("getGridParam","colNames"),f={},j=[];g.empty();b.each(k,function(b){f[this.name]=b;this.hidedlg?this.hidden||j.push(b):g.append("<option value='"+b+"' "+(this.hidden?"":"selected='selected'")+">"+jQuery.jgrid.stripHtml(p[b])+"</option>")});var i=b.isFunction(a.dlog_opts)?a.dlog_opts.call(c,a):a.dlog_opts;e(a.dlog,d,i);i=b.isFunction(a.msel_opts)?a.msel_opts.call(c,
a):a.msel_opts;e(a.msel,g,i)}},sortableRows:function(a){return this.each(function(){var e=this;if(e.grid&&!e.p.treeGrid&&b.fn.sortable){a=b.extend({cursor:"move",axis:"y",items:".jqgrow"},a||{});if(a.start&&b.isFunction(a.start)){a._start_=a.start;delete a.start}else a._start_=false;if(a.update&&b.isFunction(a.update)){a._update_=a.update;delete a.update}else a._update_=false;a.start=function(c,d){b(d.item).css("border-width","0px");b("td",d.item).each(function(b){this.style.width=e.grid.cols[b].style.width});
if(e.p.subGrid){var g=b(d.item).attr("id");try{b(e).jqGrid("collapseSubGridRow",g)}catch(k){}}a._start_&&a._start_.apply(this,[c,d])};a.update=function(c,d){b(d.item).css("border-width","");e.p.rownumbers===true&&b("td.jqgrid-rownum",e.rows).each(function(a){b(this).html(a+1+(parseInt(e.p.page,10)-1)*parseInt(e.p.rowNum,10))});a._update_&&a._update_.apply(this,[c,d])};b("tbody:first",e).sortable(a);b("tbody:first",e).disableSelection()}})},gridDnD:function(a){return this.each(function(){function e(){var a=
b.data(c,"dnd");b("tr.jqgrow:not(.ui-draggable)",c).draggable(b.isFunction(a.drag)?a.drag.call(b(c),a):a.drag)}var c=this;if(c.grid&&!c.p.treeGrid&&b.fn.draggable&&b.fn.droppable){b("#jqgrid_dnd").html()===null&&b("body").append("<table id='jqgrid_dnd' class='ui-jqgrid-dnd'></table>");if(typeof a=="string"&&a=="updateDnD"&&c.p.jqgdnd===true)e();else{a=b.extend({drag:function(a){return b.extend({start:function(d,e){if(c.p.subGrid){var f=b(e.helper).attr("id");try{b(c).jqGrid("collapseSubGridRow",f)}catch(j){}}for(f=
0;f<b.data(c,"dnd").connectWith.length;f++)b(b.data(c,"dnd").connectWith[f]).jqGrid("getGridParam","reccount")=="0"&&b(b.data(c,"dnd").connectWith[f]).jqGrid("addRowData","jqg_empty_row",{});e.helper.addClass("ui-state-highlight");b("td",e.helper).each(function(b){this.style.width=c.grid.headers[b].width+"px"});a.onstart&&b.isFunction(a.onstart)&&a.onstart.call(b(c),d,e)},stop:function(d,e){if(e.helper.dropped&&!a.dragcopy){var f=b(e.helper).attr("id");f===void 0&&(f=b(this).attr("id"));b(c).jqGrid("delRowData",
f)}for(f=0;f<b.data(c,"dnd").connectWith.length;f++)b(b.data(c,"dnd").connectWith[f]).jqGrid("delRowData","jqg_empty_row");a.onstop&&b.isFunction(a.onstop)&&a.onstop.call(b(c),d,e)}},a.drag_opts||{})},drop:function(a){return b.extend({accept:function(a){if(!b(a).hasClass("jqgrow"))return a;a=b(a).closest("table.ui-jqgrid-btable");if(a.length>0&&b.data(a[0],"dnd")!==void 0){a=b.data(a[0],"dnd").connectWith;return b.inArray("#"+b.jgrid.jqID(this.id),a)!=-1?true:false}return false},drop:function(d,e){if(b(e.draggable).hasClass("jqgrow")){var f=
b(e.draggable).attr("id"),f=e.draggable.parent().parent().jqGrid("getRowData",f);if(!a.dropbyname){var j=0,i={},h,n=b("#"+b.jgrid.jqID(this.id)).jqGrid("getGridParam","colModel");try{for(var o in f){h=n[j].name;h=="cb"||h=="rn"||h=="subgrid"||f.hasOwnProperty(o)&&n[j]&&(i[h]=f[o]);j++}f=i}catch(m){}}e.helper.dropped=true;if(a.beforedrop&&b.isFunction(a.beforedrop)){h=a.beforedrop.call(this,d,e,f,b("#"+b.jgrid.jqID(c.p.id)),b(this));typeof h!="undefined"&&h!==null&&typeof h=="object"&&(f=h)}if(e.helper.dropped){var l;
if(a.autoid)if(b.isFunction(a.autoid))l=a.autoid.call(this,f);else{l=Math.ceil(Math.random()*1E3);l=a.autoidprefix+l}b("#"+b.jgrid.jqID(this.id)).jqGrid("addRowData",l,f,a.droppos)}a.ondrop&&b.isFunction(a.ondrop)&&a.ondrop.call(this,d,e,f)}}},a.drop_opts||{})},onstart:null,onstop:null,beforedrop:null,ondrop:null,drop_opts:{activeClass:"ui-state-active",hoverClass:"ui-state-hover"},drag_opts:{revert:"invalid",helper:"clone",cursor:"move",appendTo:"#jqgrid_dnd",zIndex:5E3},dragcopy:false,dropbyname:false,
droppos:"first",autoid:true,autoidprefix:"dnd_"},a||{});if(a.connectWith){a.connectWith=a.connectWith.split(",");a.connectWith=b.map(a.connectWith,function(a){return b.trim(a)});b.data(c,"dnd",a);c.p.reccount!="0"&&!c.p.jqgdnd&&e();c.p.jqgdnd=true;for(var d=0;d<a.connectWith.length;d++)b(a.connectWith[d]).droppable(b.isFunction(a.drop)?a.drop.call(b(c),a):a.drop)}}}})},gridResize:function(a){return this.each(function(){var e=this,c=b.jgrid.jqID(e.p.id);if(e.grid&&b.fn.resizable){a=b.extend({},a||
{});if(a.alsoResize){a._alsoResize_=a.alsoResize;delete a.alsoResize}else a._alsoResize_=false;if(a.stop&&b.isFunction(a.stop)){a._stop_=a.stop;delete a.stop}else a._stop_=false;a.stop=function(d,g){b(e).jqGrid("setGridParam",{height:b("#gview_"+c+" .ui-jqgrid-bdiv").height()});b(e).jqGrid("setGridWidth",g.size.width,a.shrinkToFit);a._stop_&&a._stop_.call(e,d,g)};a.alsoResize=a._alsoResize_?eval("("+("{'#gview_"+c+" .ui-jqgrid-bdiv':true,'"+a._alsoResize_+"':true}")+")"):b(".ui-jqgrid-bdiv","#gview_"+
c);delete a._alsoResize_;b("#gbox_"+c).resizable(a)}})}})})(jQuery);

