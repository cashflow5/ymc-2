package com.yougou.kaidian.order.constant;

/**
 * 异常类型
 * @author liuwenjun
 * create time 2011-5-18
 */
public enum ExceptionType{

	EDIT_PRO_STYLESIZE{
		public String getName(){
			return "商品资料修改(只限码数，颜色更改)";
	}},
	EDIT_DELIVERY_INFO{
		public String getName(){
			return "送货信息修改";
		}
	},
	CANCEL_ORDER{
		public String getName(){
			return "订单取消";
		}
	},
	SPLIT_ORDER{
        public String getName(){
            return "分单异常";
        }
    },
	NO_PRO_REFUND{
		public String getName(){
			return "商品缺货";
		}
	},
	HOUSE_LACK{
        public String getName(){
            return "仓库缺货";
        }
    },
    COMFIRM_ORDER{
        public String getName(){
            return "审核订单异常";
        }
    },
    CHOOSE_HOUSE{
        public String getName(){
            return "选择仓库";
        }
    },
    CHOOSE_LOGICTIS{
        public String getName(){
            return "选择物流公司";
        }
    },
    GET_LOGICTIS{
        public String getName(){
            return "获取物流公司异常";
        }
    },
    PROVING_ORDER{
        public String getName(){
            return "可疑订单";
        }
    },
    BLACK_ORDER{
        public String getName(){
            return "已在灰名单内的订单";
        }
    },
    VALID_INFO{
        public String getName(){
            return "必要信息不完整或超出购买数";
        }
    },
    PRICE_EXCEPTION{
        public String getName(){
            return "价格异常";
        }
    },
    MESSAGE_EXCEPTION{
        public String getName(){
            return "订单留言";
        }
    },
    LOGICTIS_BEYOND_EXCEPTION{
        public String getName(){
            return "物流公司超区异常";
        }
    },
    REFUSE_ORDER{
        public String getName(){
            return "拒绝订单";
        }
    },
    FILTER_FAIL{
        public String getName(){
            return "系统过滤失败";
        }
    },
	OTHER{
		public String getName(){
			return "其他";
		}
	};
	
	public abstract String getName();
	
}
