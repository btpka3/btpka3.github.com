package my.gorm.hibernate;

/**
 * 用户性别。
 */
public enum UserGenderEnum {

    MALE("男"),
    FEMALE("女"),
    UNKONWN("保密");

    final String description

    private UserGenderEnum(String desp) {
        this.description = desp
    }
}
