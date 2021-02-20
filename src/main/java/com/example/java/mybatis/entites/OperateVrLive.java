package com.example.java.mybatis.entites;

import java.util.Date;


import lombok.Data;

/**
 * 
 * @author liqinchun
 * @version 1.0.0
 */
@Data
public class OperateVrLive {
	private static final long serialVersionUID = 200003;

	private Long id;

	/**
	 * 用户权限所属组织id
	 */
	private Long userId = Long.parseLong("1");
	/**
	 * 组织id
	 */
	private Long groupId = Long.parseLong("1");
	/**
	 * 直播id
	 */
	private String channelId = "20200708161418_8ILF94AX";
	/**
	 * 直播名称
	 */
	private String name = "qq测试拉流直播";
	/**
	 * 直播介绍
	 */
	private String description = "qq测试直播介绍";
	/**
	 * 状态：1-待提交，2-待审核，3-审核通过，4-已驳回，5-待授权，7-已授权
	 */
	private Integer state = 7;
	/**
	 * 播放状态：0-未开始，1-直播中，2-暂停，3-结束
	 */
	private Integer liveStatus = -1;
	/**
	 * 授权组织id，冗余，多个用逗号分隔
	 */
	private String targetGroup = "1,2,6,7,8,9,11,12,13,14,15,18,21,23,24,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,71,72,73,74,75,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129";
	/**
	 * 封面地址
	 */
	private String imgUrl = "http://183.220.194.97:9999/api/upload/img/20200708/488807bcc9724bfdb66c08a590877fb51594195856422.png";
	/**
	 * 直播平台：0-咪咕，1-七牛
	 */
	private Integer platform = 0;
	/**
	 * 直播类型：0-推流直播，1-拉流直播，默认为0
	 */
	private Integer liveType = 1;
	/**
	 * 推流地址，推流直播时存在
	 */
	private String pushUrl = "rtmp://devlivepush.migucloud.com/live/8ILF94AX_C0";

	/**
	 * 拉流地址，拉流直播时存在
	 */
	private String pullUrl = "http://123.m3u8";
	/**
	 * 播放地址，json格式
	 */
	private String playUrl = "{\"list\":[{\"transType\":\"3\",\"urlFlv\":\"http://devlivepull.migucloud.com/live/8ILF94AX_C0_3.flv\",\"urlHls\":\"http://wshls.live.migucloud.com/live/8ILF94AX_C0_3/playlist.m3u8\",\"urlRtmp\":\"rtmp://devlivepull.migucloud.com/live/8ILF94AX_C0_3\"}]}";
	/**
	 * 画质：1-流畅，2-标清，3-高清，4-超清，5-音频，多个画质使用逗号分隔
	 */
	private String videoType = "3";
	/**
	 * 禁播状态：0-开启，1-禁播，默认为0
	 */
	private Integer forbidStatus = 0;
	/**
	 * 直播开始时间
	 */
	private Date startTime = new Date();
	/**
	 * 直播结束时间
	 */
	private Date endTime = new Date();
	/**
	 * 录制状态：0-不录制，1-录制，默认为0
	 */
	private Integer record = 1;
	/**
	 * 是否自动导入云点播：0-否，1-是
	 */
	private Integer demand = 0;
	/**
	 * cdn类型，0-低延时CDN，1-在线直播CDN
	 */
	private Integer cdnType = 0;
	/**
	 * 其他配置，json格式，预留
	 */
	private String otherConfig;
	/**
	 * 分类id，对应的是category表的id
	 */
	private Long labelId = Long.parseLong("5");
	/**
	 * 分类名称，冗余
	 */
	private String labelName = "教育";
	/**
	 * 直播来源方
	 */
	private String source = "qq测试直播来源";
	/**
	 * 业务配置：0-慕课，1-VR慕课，2-VR双教室，3-云渲染
	 */
	private Integer businessConf = 0;
	/**
	 * 在线观看人数
	 */
	private Integer num = 0;
	/**
	 * 创建时间
	 */
	private Date createTime = new Date();
	/**
	 * 上线时间
	 */
	private Date releaseTime = new Date();
	/**
	 * 更新时间
	 */
	private Date updateTime = new Date();
	/**
	 * 删除状态：0-已经删除，1-未删除
	 */
	private Integer delState = 1;
	/**
	 * 删除时间，当del_state=0时有效
	 */
	private Date deleteTime = new Date();

	public OperateVrLive() {
	}


}