package com.player.mothercollege.bean;

/**
 * Created by Administrator on 2016/11/3.
 */
public class CirleNameDetailsBean {
    /**
     * groupId : g1
     * groupInfo : 只有守静的人，才能发现生活中的幸福和美。浮躁的人、脚步匆忙的人总是会错过很多美好的东西。我们或许会经历人生岁月的蹉跎或道路的泥泞坎坷，但保持淡泊的处世态度，泰然处之，就能在纷繁中找寻心的超然和安宁，不受世俗的干扰和冲击，人生也更豁然开朗。成大事者：守愚、守静、守时、守信。守静，每临大事有静气
     * groupName : 有朋自远方来
     * hasJoin : true
     * icon : http://121.42.31.133:8201/m/face/faA056[1].bmp
     * joinCount : 999
     * trendCount : 10
     */

    private String groupId;
    private String groupInfo;
    private String groupName;
    private boolean hasJoin;
    private String icon;
    private int joinCount;
    private int trendCount;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(String groupInfo) {
        this.groupInfo = groupInfo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isHasJoin() {
        return hasJoin;
    }

    public void setHasJoin(boolean hasJoin) {
        this.hasJoin = hasJoin;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public int getTrendCount() {
        return trendCount;
    }

    public void setTrendCount(int trendCount) {
        this.trendCount = trendCount;
    }
}
