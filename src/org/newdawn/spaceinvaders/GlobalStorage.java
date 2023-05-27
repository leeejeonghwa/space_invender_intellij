package org.newdawn.spaceinvaders;

public class GlobalStorage {
    private static GlobalStorage globalStorage = null;

    private String userId = "";

    private String userBestScore = "";

    private GlobalStorage() {

    }

    public static GlobalStorage getInstance() {
        if (globalStorage == null) {
            globalStorage = new GlobalStorage();
        }

        return globalStorage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
    }

    public String getUserBestScore() {
        return userBestScore;
    }

    public void setUserBestScore(String userBestScore) {
        this.userBestScore = userBestScore;
    }
}