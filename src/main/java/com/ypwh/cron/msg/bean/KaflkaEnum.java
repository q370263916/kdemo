package com.ypwh.cron.msg.bean;
 
public enum KaflkaEnum {

	ABONTOPIC(1,"arriveBonTopicNew","优惠券送达"),
	
	EBONTOPIC(2,"exppBonTopicNew","优惠券到期"),
	
	AREDTOPIC(3,"arriveRedTopicNew","红包送达"),

	EREDTOPIC(4,"exppRedTopicNew","红包到期"),

	AWARDTOPIC(5,"awardTopic","注册抽奖 开奖"),

	GETAWARDTOPIC(6,"getAwardTopic","注册抽奖 中奖"),

	NOREGISTERTOPIC(7,"noRegisterTopic","激活未注册用户"),

	GETCODETOPIC(8,"getCodeRedTopic","获取抽奖码"),

	GETFRITOPIC(9,"getFriTopic","获取助力抽奖码"),

	GETREDCODETOPIC(10,"getRegCodeTopic","获取红包抽奖码");
	private static final KaflkaEnum[] states = KaflkaEnum.values();
	
	private int code;
	
	private String topic;
	
	private String topicDesc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}

	private KaflkaEnum(int code, String topic, String topicDesc) {
		this.code = code;
		this.topic = topic;
		this.topicDesc = topicDesc;
	}
	
	/**
	 * 根据code返回枚举
	 * @param code
	 * @return
	 */
	public static KaflkaEnum getKaflkaEnumByCode(int code) {
		for (KaflkaEnum state:states) {
			if (state.getCode() == code) {
				return state;
			}
		}
		return null;
	}
	 
}
