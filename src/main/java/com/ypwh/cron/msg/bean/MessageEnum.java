package com.ypwh.cron.msg.bean;
 
public enum MessageEnum {

	SYS_MSG_ABON1(1,"m1","叮！你被优惠券砸中啦！快来剁手吧！"),
	SYS_MSG_ABON2(2,"m2","有人@你，又有优惠券从天而降，来瞧瞧你的钱包吧~"),
	SYS_MSG_ABON3(3,"m3","爱你就要把你放在心上！专属优惠券已经悄悄送到你的账户啦~"),

	SYS_MSG_ARED1(4,"m4","叮！你被红包砸中啦！快来剁手吧！"),
	SYS_MSG_ARED2(5,"m5","有人@你，又有红包从天而降，来瞧瞧你的钱包吧~"),
	SYS_MSG_ARED3(6,"m6","爱你就要把你放在心上！专属红包已经悄悄送到你的账户啦~"),

	SYS_MSG_EBON1(7,"m7","温馨提示：别忘了账户中的优惠券哦~再不用就要失效啦！"),
	SYS_MSG_EBON2(8,"m8","亲，您的特权专享优惠券就要到期了，一定不要错过啊~"),
	SYS_MSG_EBON3(9,"m9","江湖告急！您的专属优惠券即将失效，用了就是省钱→"),

	SYS_MSG_ERED1(10,"m10","温馨提示：别忘了账户中的红包哦~再不用就要失效啦！"),
	SYS_MSG_ERED2(11,"m11","亲，您的特权专享红包就要到期了，一定不要错过啊~"),
	SYS_MSG_ERED3(12,"m12"," 江湖告急！您的专属红包即将失效，用了就是省钱→");


	private static final MessageEnum[] states = MessageEnum.values();

	private int code;

	private String name;

	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	private MessageEnum(int code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}
	
	/**
	 * 根据code返回枚举
	 * @param code
	 * @return
	 */
	public static MessageEnum getKaflkaEnumByCode(int code) {
		for (MessageEnum state:states) {
			if (state.getCode() == code) {
				return state;
			}
		}
		return null;
	}
	 
}
