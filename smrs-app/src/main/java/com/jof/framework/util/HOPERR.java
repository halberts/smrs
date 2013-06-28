package com.jof.framework.util;

/**
 * ALL sub-system error need subclass this interface <br/>
 * First parameter
 * <ul>
 * <li>SYS: Used for only HOP issue code</li>
 * <li>OMS: Used for only OMS issue code, like: OMS_1_******</li>
 * <li>HMMS: Used for only HMMS issue code. like: HMMS_1_******</li>
 * </ul>
 * Second parameter
 * <ul>
 * <li>P1 issue please use format SYS_1_******</li>
 * <li>P2 issue please use format SYS_2_******</li>
 * <li>Priority is 1-5, P1 is highest, P5 is lowest</li>
 * </ul>
 * 
 * @author mk
 * 
 */
public interface HOPERR {

	String SYS_1_000001 = "SYS_1_000001::FATAL Internal error";

}
