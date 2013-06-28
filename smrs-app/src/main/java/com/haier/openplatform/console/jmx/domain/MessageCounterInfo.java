package com.haier.openplatform.console.jmx.domain;

import org.hornetq.utils.json.JSONObject;

public class MessageCounterInfo {

	// Attributes ----------------------------------------------------

	private final String name;

	private final String subscription;

	private final boolean durable;

	private final long count;

	private final long countDelta;

	private final int depth;

	private final int depthDelta;

	private final String lastAddTimestamp;

	private final String udpateTimestamp;
	   
	// Constructors --------------------------------------------------

	   public MessageCounterInfo(final String name,
	                             final String subscription,
	                             final boolean durable,
	                             final long count,
	                             final long countDelta,
	                             final int depth,
	                             final int depthDelta,
	                             final String lastAddTimestamp,
	                             final String udpateTimestamp)
	   {
	      this.name = name;
	      this.subscription = subscription;
	      this.durable = durable;
	      this.count = count;
	      this.countDelta = countDelta;
	      this.depth = depth;
	      this.depthDelta = depthDelta;
	      this.lastAddTimestamp = lastAddTimestamp;
	      this.udpateTimestamp = udpateTimestamp;
	   }

	/**
	 * Returns an array of RoleInfo corresponding to the JSON serialization returned
	 * by {@link QueueControl#listMessageCounter()}.
	 */
	public static MessageCounterInfo fromJSON(final String jsonString) throws Exception {
		JSONObject data = new JSONObject(jsonString);
		String name = data.getString("destinationName");
		String subscription = data.getString("destinationSubscription");
		boolean durable = data.getBoolean("destinationDurable");
		long count = data.getLong("count");
		long countDelta = data.getLong("countDelta");
		int depth = data.getInt("messageCount");
		int depthDelta = data.getInt("messageCountDelta");
		String lastAddTimestamp = data.getString("lastAddTimestamp");
		String updateTimestamp = data.getString("updateTimestamp");

		return new MessageCounterInfo(name, subscription, durable, count, countDelta, depth, depthDelta,
				lastAddTimestamp, updateTimestamp);
	}

	/**
	 * Returns the name of the queue.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the subscription.
	 */
	public String getSubscription() {
		return subscription;
	}

	/**
	 * Returns whether the queue is durable.
	 */
	public boolean isDurable() {
		return durable;
	}

	/**
	 * Returns the number of messages added to the queue since it was created.
	 */
	public long getCount() {
		return count;
	}

	/**
	 * Returns the number of messages added to the queue since the last counter sample.
	 */
	public long getCountDelta() {
		return countDelta;
	}

	/**
	 * Returns the number of messages currently in the queue.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Returns the number of messages in the queue since last counter sample.
	 */
	public int getDepthDelta() {
		return depthDelta;
	}

	/**
	 * Returns the timestamp of the last time a message was added to the queue.
	 */
	public String getLastAddTimestamp() {
		return lastAddTimestamp;
	}

	/**
	 * Returns the timestamp of the last time the queue was updated.
	 */
	public String getUdpateTimestamp() {
		return udpateTimestamp;
	}
}
