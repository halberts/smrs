package com.jof.dao.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jof.model.demo.Customer;

@Component(value="custumerDao")
public class CustumerDao {
	public Integer count(){
		return 10;
	}
	
	public List<Customer> find(int from,int to){
		List<Customer> list = new ArrayList<Customer>();
		Customer customer = new Customer();
		customer.setCity("city");
		customer.setCountry("country");
		customer.setCreditLimit(908d);
		customer.setId(1);
		customer.setName("name");
		list.add(customer);		
		return list;
	}
}
