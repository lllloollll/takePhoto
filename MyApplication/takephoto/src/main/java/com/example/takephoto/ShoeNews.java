package com.example.takephoto;

/**
 * 新接口的跑鞋
 * Created by juan on 2019/07/15.
 */
public class ShoeNews {

    /**
     * id : 2
     * shoeId : 80
     * brandId : 1
     * content : kljsdfjklfdsjklfdsjklf发多少
     * photoPath : https://www.baidu.com/img/baidu_jgylogo3.gif
     * photoBgColor : #999999
     * buttonColor : #FFFFFF
     * canBuy : 1
     * jumpUrl : http://www.thejoyrun.com
     * ranking : 2
     * publicStartTime : 1560585533000
     * publicEndTime : 1571126333000
     * canOnline : 1
     * firstPublish : 1
     * createTime : 1563177532000
     * updateTime : 1563187832000
     * delFlag : 0
     */

    private int id;
    private int shoeId;
    private int brandId;
    private String content;
    private String photoPath;
    private String photoLogo;
    private String photoBgColor;
    private String buttonColor;
    private int canBuy;
    private String jumpUrl;
    private int ranking;
    private long publicStartTime;
    private long publicEndTime;
    private int canOnline;
    private int firstPublish;
    private long createTime;
    private long updateTime;
    private int delFlag;
    private float price;
    /**
     * 是否首发。不存进数据库，从本地获取or服务器获取时，根据{@linkplain ShoeStarting}改变数据
     */
    private int isStarting;
    /**
     * 评论总数
     */
    public int    commentCount;
    public long   createtime;
    public int    refPrice;
    public float  totalScore;
    /**
     * 星数
     */
    public float  avgScore;
    public int    addCount;
    /**
     * 多配色开关
     */
    public int    isMultiColor;
    public String brandName = "";
    /**
     * 跑鞋名称
     */
    public String shoeName  = "";
    /**
     * 跑鞋封面图
     */
    public String coverImg  = "";
    public String shoeIntro = "";
    public String shoeCoverImg = "";
    public int    brandUid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoeId() {
        return shoeId;
    }

    public void setShoeId(int shoeId) {
        this.shoeId = shoeId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoBgColor() {
        return photoBgColor;
    }

    public void setPhotoBgColor(String photoBgColor) {
        this.photoBgColor = photoBgColor;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    public int getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(int canBuy) {
        this.canBuy = canBuy;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public long getPublicStartTime() {
        return publicStartTime;
    }

    public void setPublicStartTime(long publicStartTime) {
        this.publicStartTime = publicStartTime;
    }

    public long getPublicEndTime() {
        return publicEndTime;
    }

    public void setPublicEndTime(long publicEndTime) {
        this.publicEndTime = publicEndTime;
    }

    public int getCanOnline() {
        return canOnline;
    }

    public void setCanOnline(int canOnline) {
        this.canOnline = canOnline;
    }

    public int getFirstPublish() {
        return firstPublish;
    }

    public void setFirstPublish(int firstPublish) {
        this.firstPublish = firstPublish;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getIsStarting() {
        return isStarting;
    }

    public void setIsStarting(int isStarting) {
        this.isStarting = isStarting;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(int refPrice) {
        this.refPrice = refPrice;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public int getIsMultiColor() {
        return isMultiColor;
    }

    public void setIsMultiColor(int isMultiColor) {
        this.isMultiColor = isMultiColor;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getShoeIntro() {
        return shoeIntro;
    }

    public void setShoeIntro(String shoeIntro) {
        this.shoeIntro = shoeIntro;
    }

    public String getPhotoLogo() {
        return photoLogo;
    }

    public void setPhotoLogo(String photoLogo) {
        this.photoLogo = photoLogo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getShoeCoverImg() {
        return shoeCoverImg;
    }

    public void setShoeCoverImg(String shoeCoverImg) {
        this.shoeCoverImg = shoeCoverImg;
    }

    public int getBrandUid() {
        return brandUid;
    }

    public void setBrandUid(int brandUid) {
        this.brandUid = brandUid;
    }
}
