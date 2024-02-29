package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("realname")
    private String realname;

    @JsonProperty("password")
    private String password;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("country")
    private String country;

    @JsonProperty("description")
    private String description;

    @JsonProperty("avatar_id")
    private int avatarId;

    @JsonProperty("is_admin")
    private int isAdmin;

    @JsonProperty("authToken")
    private String authToken;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("article_cnt")
    private int articleCnt;
    // getters and setters...

    public int getArticleCnt() {
        return articleCnt;
    }

    public void setArticleCnt(int articleCnt) {
        this.articleCnt = articleCnt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }


    public boolean isAdmin() {
        return isAdmin == 1;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin ? 1 : 0;
    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}

