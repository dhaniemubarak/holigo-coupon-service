package id.holigo.services.common.model;

public enum UserGroupEnum {
    MEMBER(100), NETIZEN(200), BOSSQIU(300), SOELTAN(400), CRAZY_RICH(500);

    private final Integer code;

    UserGroupEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
