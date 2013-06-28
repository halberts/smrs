package com.haier.openplatform.console.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.haier.openplatform.hmc.domain.MessageModule;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("InvokeSumBeans")
public class InvokeSumBeans extends MessageModule{


	private static final long serialVersionUID = -3594751012032817666L;
	/**
	 * 
	 */
	@XStreamAlias("sumBeans")
	private List<InvokeSumBean> sumBeans;

	public InvokeSumBeans(List<InvokeSumBean> sumBeans) {
		this.sumBeans = sumBeans;
	}
	
	public InvokeSumBeans(InvokeSumBean sumBeans){
		this.sumBeans = new ArrayList<InvokeSumBean>();
		this.sumBeans.add(sumBeans);
	}

	public List<InvokeSumBean> getSumBeans() {
		return sumBeans;
	}

	public void setSumBeans(List<InvokeSumBean> sumBeans) {
		this.sumBeans = sumBeans;
	}

	public void setSumBean(InvokeSumBean isb) {
		if (sumBeans == null) {
			sumBeans = new ArrayList<InvokeSumBean>();
		}
		sumBeans.add(isb);
	}

	public String toString() {

		StringBuilder s = new StringBuilder();

		for (Iterator<InvokeSumBean> iterator = sumBeans.iterator(); iterator.hasNext();) {
			InvokeSumBean i = (InvokeSumBean) iterator.next();
			s.append(i.toString());
		}

		return s.toString();
	}
}
